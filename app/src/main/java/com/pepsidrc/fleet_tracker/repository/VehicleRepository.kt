package com.pepsidrc.fleet_tracker.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.pepsidrc.fleet_tracker.api.RetrofitInstance
import com.pepsidrc.fleet_tracker.data.FleetDatabase
import com.pepsidrc.fleet_tracker.data.TaskTbl
import com.pepsidrc.fleet_tracker.data.VehicleTbl
import com.pepsidrc.fleet_tracker.data.VehiclesDao
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VehicleRepository (application: Application, contxt: Context) {
    private val vehicleDao: VehiclesDao
    private var cntxt: Context

    init {
        val FleetDb = FleetDatabase.getDatabase(application)
        cntxt = contxt
        vehicleDao = FleetDb!!.vehicleDao()
    }

    suspend fun insertVehicleToDB(vehicles: List<VehicleTbl>) = withContext(Dispatchers.IO) {
        try {
            vehicleDao.deleteAllVehicle()
            vehicleDao.insertVehicle(vehicles)
        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun getAllVehicleFromDB():List<VehicleModel>? = withContext(Dispatchers.IO) {
        val tasks:List<VehicleModel>? = vehicleDao.getAllVehicle()
        return@withContext tasks
    }


    suspend fun getVehicleFromWebApi(): List<VehicleTbl>? {
        val response =  RetrofitInstance.api.getVehicles()
        response.isSuccessful.let {
            when (response.code()) {
                400 -> {
                    Toast.makeText(cntxt, "Please contact administrator", Toast.LENGTH_SHORT).show()
                }
                500 -> {
                    Toast.makeText(cntxt, "Internal Server Error", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    return response.body()
                }
            }
        }
        return emptyList()
    }

}