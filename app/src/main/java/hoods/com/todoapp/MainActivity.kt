package hoods.com.todoapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.*
import hoods.com.todoapp.ui.home.HomeViewModel
import hoods.com.todoapp.ui.theme.Teal200
import hoods.com.todoapp.ui.theme.TextWhite
import hoods.com.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {

    private var locationCallback: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequired = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val context = LocalContext.current
                    var currentLocation by remember {
                        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))
                    }

                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            for (lo in p0.locations) {
                                // Update UI with location data
                                currentLocation = LocationDetails(lo.latitude, lo.longitude)
                            }
                        }
                    }

                    val launcherMultiplePermissions = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions()
                    ) { permissionsMap ->
                        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                        if (areGranted) {
                            locationRequired = true
                            startLocationUpdates()
                            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                    }


                    TodoNavHost()
                    Column (modifier = Modifier,
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally){

                        val permissions = arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        Button(
                            onClick = {
                                if (permissions.all {
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            it
                                    ) == PackageManager.PERMISSION_GRANTED
                                }) {
                                // If Permission granted, get location
                                startLocationUpdates()
                                // Else launch PermissionRequest
                            } else {
                                launcherMultiplePermissions.launch(permissions)
                            }
                        },shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Teal200)) {
                            Text(text = "Get current location",
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = "Latitude: " + currentLocation.latitude,
                            fontSize = 22.sp,
                            fontFamily = FontFamily.SansSerif)
                        Text(text = "Longitude: " + currentLocation.longitude,
                            fontSize = 22.sp,
                            fontFamily = FontFamily.SansSerif)
                        Spacer(modifier = Modifier.size(10.dp))
                    }


                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }

}


data class LocationDetails(val latitude: Double, val longitude: Double)