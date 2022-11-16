package gemastik.pendekar.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "search_history")
data class SearchHistoryEntity(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
    var id: Int?,

//    @ColumnInfo(name = "name")
    var searchName:String,

//    @ColumnInfo(name = "latitude")
    var lat:Float,

//    @ColumnInfo(name = "longitude")
    var lng:Float
)
