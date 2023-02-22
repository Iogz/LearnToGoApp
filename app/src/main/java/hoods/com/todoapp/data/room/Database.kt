package hoods.com.todoapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hoods.com.todoapp.data.Todo

/**
 *
 *  "@Database" gibt an, dass diese Klasse eine Datenbankklasse ist, die die Tabelle
 * "Todo" enthält.
 * "entities" gibt an, welche Entitäten in der Datenbank enthalten sein sollen, nur "Todo"-Entität
 * "version" gibt die Versionsnummer der Datenbank an
 * "exportSchema" ob das Schema beim Kompilieren der Anwendung exportiert werden soll.
 *
 */

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}