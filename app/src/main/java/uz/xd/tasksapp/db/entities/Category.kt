package uz.xd.tasksapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @ColumnInfo(name = "name")
    var title: String,
    @ColumnInfo(name = "color")
    var color: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}