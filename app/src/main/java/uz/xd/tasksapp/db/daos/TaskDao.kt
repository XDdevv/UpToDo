package uz.xd.tasksapp.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.xd.tasksapp.db.entities.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE completed IS NOT true ORDER BY flag ASC")
    fun getAllTasksByFlag(): List<Task>

    @Query("SELECT * FROM tasks WHERE completed IS true")
    fun getAllTasksWhichCompleted(): List<Task>

    @Query("SELECT * FROM tasks WHERE date_day = :date_day and completed IS true ORDER BY flag ASC")
    fun getAllTasksByDay(date_day: String): List<Task>

    @Query("SELECT * FROM tasks WHERE date_day = :date_day and completed IS false ORDER BY flag ASC")
    fun getAllTasksByDayNOT(date_day: String): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteNote(task: Task)
}