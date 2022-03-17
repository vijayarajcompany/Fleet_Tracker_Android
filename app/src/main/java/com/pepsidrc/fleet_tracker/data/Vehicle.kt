package com.pepsidrc.fleet_tracker.data
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
import androidx.room.PrimaryKey
import androidx.room.Relation
//import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Tbl_Vehicle")
data class VehicleTbl(
    @PrimaryKey
    var name: String,
    var part: List<Int>
)

data class Parts(var id:Int)

//@JsonClass(generateAdapter = true)
//@AutoGenerateConverter(using = ConverterType.MOSHI)