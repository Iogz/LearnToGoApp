package hoods.com.todoapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hoods.com.todoapp.data.Todo

/** Der untere Code definiert eine Klasse mit dem Namen "TodoDatabase", die eine Abstraktion der RoomDatabase-Klasse von Android ist.
 * Room ist eine Android-Bibliothek, die eine Abstraktionsschicht über SQLite-Datenbanken bereitstellt, um die Arbeit mit Datenbanken in Android-Anwendungen zu vereinfachen.
 * Die Klasse "TodoDatabase" definiert eine Datenbank, die eine Tabelle enthält, die mit der Klasse "Todo"
 * korrespondiert. Die Annotation "@Database" gibt an, dass diese Klasse eine Datenbankklasse ist, die die Tabelle
 * "Todo" enthält. "entities" gibt an, welche Entitäten in der Datenbank enthalten sein sollen, in diesem
 * Fall nur die "Todo"-Entität. "version" gibt die Versionsnummer der Datenbank an, "exportSchema" gibt an,
 * ob das Schema beim Kompilieren der Anwendung exportiert werden soll.
 * Die Klasse enthält eine Methode namens "getDatabase", die ein Singleton-Muster implementiert,
 * um sicherzustellen, dass nur eine Instanz der Datenbankklasse existiert.
 * Die Methode gibt eine Instanz der Datenbank zurück, falls eine vorhanden ist, oder
 * erstellt eine neue Instanz der Datenbank, falls keine vorhanden ist.
 * Die Klasse enthält auch eine Methode namens "todoDao", die eine
 * DAO-Schnittstelle (Data Access Object) zurückgibt, die für die Ausführung von Datenbank-Operationen verwendet wird.
 * Die DAO-Schnittstelle ist eine abstrakte Klasse, die von Room implementiert wird, basierend auf der von Ihnen definierten Schnittstelle.
 * Insgesamt definiert dieser Code also eine SQLite-Datenbank, die eine Tabelle enthält, die mit der
 * "Todo"-Entität korrespondiert, und stellt eine Schnittstelle (DAO) bereit,
 * die für die Durchführung von Datenbank-Operationen verwendet werden kann. Der Singleton-Entwurf wird verwendet,
 * um sicherzustellen, dass nur eine Instanz der Datenbankklasse existiert und dass die Verwendung der Datenbank effizient ist. **/

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