package hoods.com.todoapp

import android.content.Context
import hoods.com.todoapp.data.todo.TodoDataSource
import hoods.com.todoapp.todo.data.room.TodoDatabase

object Graph {
    lateinit var database: TodoDatabase
        private set
    val todoRepo by lazy {
        TodoDataSource(database.todoDao())
    }

    fun provide(context: Context) {
        database = TodoDatabase.getDatabase(context)
    }
}