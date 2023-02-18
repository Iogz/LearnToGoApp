package hoods.com.todoapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import hoods.com.todoapp.NavRoute
import hoods.com.todoapp.R
import hoods.com.todoapp.data.Todo
import hoods.com.todoapp.ui.detail.DetailScreen
import hoods.com.todoapp.ui.home.components.TodoItem
import hoods.com.todoapp.ui.theme.Blue_
import java.text.DateFormat
import java.util.*


@Composable
fun HomeScreen(onNavigate: (Todo?) -> Unit) {

    val navController = rememberNavController()

    val viewModel = viewModel(HomeViewModel::class.java)
    val state by viewModel.state.collectAsState()
    val bottomState by remember { mutableStateOf("LearnToGo" ) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onNavigate(null) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    },
        /** Weiterleitung führt bei allen zu New Note, muss überarbeitet werden*/
    bottomBar = {
        BottomNavigation(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            backgroundColor = Blue_
           ) {
            BottomNavigationItem(
                selected = bottomState == "Home",
                onClick = { onNavigate(null) },
                label = { Text(text = "Home") },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
            )

            BottomNavigationItem(
                selected = bottomState == "Account",
                onClick = { NavRoute.Location},
                label = { Text(text = "Account") },
                icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null
                    )
                }
            )

            BottomNavigationItem(
                selected = bottomState == "Location",
                onClick = {onNavigate(null)},
                label = { Text(text = "Location") },
                icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null
                    )
                }
            )
        }
    }




    ) {
        Column (modifier = Modifier
            .background(Blue_)
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
            ) {
            Spacer(modifier = Modifier.size(10.dp))
            GetLogo()
            Spacer(modifier = Modifier.size(10.dp))
            GetDateAndTime()
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ) {
                items(state.todoList) { todo ->
                    TodoItem(
                        todo = todo,
                        onChecked = { viewModel.updateTodo(it, todo.id) },
                        onDelete = { viewModel.delete(it) },
                        onNavigation = { onNavigate(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun GetDateAndTime () {
    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

    Column() {
        Text(text = "$dateFormat", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "$timeFormat", fontSize = 20.sp)
    }

}

@Composable
fun GetLogo () {
    Image(painter = painterResource(id = R.drawable.untitled), contentDescription = "logo")
}

@Composable
fun LocationScreen(onNavigate: () -> Unit) {
    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar)

    Column() {
        Text(text = "$dateFormat", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "$timeFormat", fontSize = 20.sp)
    }
}