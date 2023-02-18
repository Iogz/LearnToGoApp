package hoods.com.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.text.DateFormat
import java.util.*

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