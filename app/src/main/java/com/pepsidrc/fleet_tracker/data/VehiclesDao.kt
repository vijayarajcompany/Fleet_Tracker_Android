package com.pepsidrc.fleet_tracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.VehicleModel

@Dao
interface VehiclesDao {

    @Insert
    fun insertVehicle(users: List<VehicleTbl>)

//    @Query("SELECT * FROM Tbl_Vehicle")
//    fun getAllVehicle(): List<Tbl_Vehicle>


    @Query("SELECT * FROM Tbl_Vehicle")
    fun getAllVehicle(): List<VehicleModel>

    @Query("DELETE FROM Tbl_Vehicle")
    fun deleteAllVehicle()
}


