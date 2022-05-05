package com.pepsidrc.fleet_tracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.LicenseModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel

@Dao
interface VehiclesDao {

    @Query("SELECT id, name, selected FROM Tbl_VehiclePart WHERE id IN (:partsID)")
    fun getVehiclePart(partsID:List<Int>): List<VehiclePartsModel>

    @Query("SELECT id, name, code, selected FROM Tbl_License")
    fun getDistributionLicense(): List<LicenseModel>

    @Insert
    fun insertFleet(fleet: List<FleetTbl>)

    @Insert
    fun insertLicense(license: List<LicenseTbl>)

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

    @Query("DELETE FROM Tbl_License")
    fun deleteAllLicenses()

    @Query("DELETE FROM Tbl_Vehicle")
    fun deleteAllVehicle()

    @Query("DELETE FROM Tbl_VehicleDetail")
    fun deleteAllVehicleDetail()
}


