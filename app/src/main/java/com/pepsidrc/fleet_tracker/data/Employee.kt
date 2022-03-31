package com.pepsidrc.fleet_tracker.data
import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
//import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Tbl_Employee")
data class EmployeeTbl(
    @PrimaryKey
    @ColumnInfo(name = "empid") var emp_id: Int,
    var name: String,
    @ColumnInfo(name = "emiratesid") var emirates_id: Int,
    @ColumnInfo(name = "contactnumber") var contact_number: Int,
    var active: Boolean
)

//@Entity(tableName = "Tbl_Employee")
//data class EmployeeTbl(
//    @PrimaryKey
//    @ColumnInfo(name = "emp_id") var empid: Int,
//    var name: String,
//    @ColumnInfo(name = "emirates_id") var emiratesid: Int,
//    @ColumnInfo(name = "contact_number") var contactnumber: Int,
//    var active: Boolean
//)

//@JsonClass(generateAdapter = true)
//@AutoGenerateConverter(using = ConverterType.MOSHI)
