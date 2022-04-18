package com.pepsidrc.fleet_tracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.VehicleModel

@Dao
interface VehiclesDao {

    @Insert
    fun insertFleet(fleet: List<FleetTbl>)

    @Insert
    fun insertVehicle(users: List<VehicleTbl>)

    @Insert
    fun insertVehicleDetail(users: List<VehicleDetailTbl>)

    @Insert
    fun insertVehiclePart(users: List<VehiclePartTbl>)

    @Query("SELECT * FROM Tbl_Emirates WHERE id IN (SELECT emirates_id FROM Tbl_VehicleDetail WHERE plate_no = :plateNo)")
    fun getEmirates(plateNo:Int): List<EmiratesModel>


    @Query("SELECT platecode FROM Tbl_VehicleDetail WHERE plate_no = :plateNo AND emirates_id = :emirateid")
    fun getPlateCode(plateNo:Int,emirateid:Int): List<String>


    @Query("SELECT km FROM Tbl_Fleet WHERE id IN (SELECT fleet_id FROM Tbl_VehicleDetail WHERE plate_no = :plateNo AND emirates_id = :emirateid AND platecode = :platecode)")
    fun getKilometer(plateNo:Int,emirateid:Int, platecode:String): Int?


    @Query("SELECT * FROM Tbl_Vehicle")
    fun getAllVehicle(): List<VehicleModel>

    @Query("DELETE FROM Tbl_Fleet")
    fun deleteAllfleet()

    @Query("DELETE FROM Tbl_Vehicle")
    fun deleteAllVehicle()

    @Query("DELETE FROM Tbl_VehicleDetail")
    fun deleteAllVehicleDetail()
}


