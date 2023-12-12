package uz.xd.tasksapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task (
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date_time")
    var dateTime: String,
    @ColumnInfo(name = "date_day")
    var dateDay: String,
    @ColumnInfo(name = "date_full")
    var dateFull: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "flag")
    var flag: Int,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "completed")
    var completed: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}