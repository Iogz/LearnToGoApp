package hoods.com.todoapp.data.todo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo(
    val todo: String,
    val time: String,
    val isComplete: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)
