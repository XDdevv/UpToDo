package uz.xd.tasksapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.xd.tasksapp.db.daos.CategoryDao
import uz.xd.tasksapp.db.daos.TaskDao
import uz.xd.tasksapp.db.entities.Category
import uz.xd.tasksapp.db.entities.Task

@Database(entities = [Task::class, Category::class], version = 5)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDatabase(): TaskDao
    abstract fun categoryDatabase(): CategoryDao

    companion object {
        private var instance: TasksDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TasksDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    TasksDatabase::class.java,
                    "tasks_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}