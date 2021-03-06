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

@Entity(tableName = "Tbl_VehicleDetail")
data class VehicleDetailTbl(
    @PrimaryKey
    var id: Int,
    var plate_no: Int,
    var emirates_id: Int,
    var platecode: String,
    var fleet_id:Int?
)


@Entity(tableName = "Tbl_Fleet")
data class FleetTbl(
    @PrimaryKey
    var id: Int,
    var serialno:String,
    var km:Int,
    var active:Boolean
)


//data class Parts(var id:Int)
@Entity(tableName = "Tbl_VehiclePart")
data class VehiclePartTbl(
    @PrimaryKey
    var id: Int,
    var name: String,
    var selected :Boolean = true
)

@Entity(tableName = "Tbl_Emirates")
data class EmiratesTbl(
    @PrimaryKey
    var id: Int,
    var name: String,
    var code: String
)


@Entity(tableName = "Tbl_License")
data class LicenseTbl(
    @PrimaryKey
    var id: Int,
    var name: String,
    var code: String,
    var selected :Boolean = true
)

//@JsonClass(generateAdapter = true)
//@AutoGenerateConverter(using = ConverterType.MOSHI)