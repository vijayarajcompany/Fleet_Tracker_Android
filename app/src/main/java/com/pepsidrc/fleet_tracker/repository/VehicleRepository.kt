package com.pepsidrc.fleet_tracker.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.pepsidrc.fleet_tracker.api.RetrofitInstance
import com.pepsidrc.fleet_tracker.data.*
import com.pepsidrc.fleet_tracker.model.LicenseModel
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
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

   suspend  fun getVehiclePartsFromDB(partsID:List<Int>): List<VehiclePartsModel> = withContext(Dispatchers.IO) {
        val vehicleParts = vehicleDao.getVehiclePart(partsID)
        return@withContext vehicleParts
    }


    suspend  fun getDistributionLicenseFromDB(): List<LicenseModel> = withContext(Dispatchers.IO) {
        val license = vehicleDao.getDistributionLicense()
        return@withContext license
    }

    suspend fun insertDistributionLicenseToDB(license: List<LicenseTbl>) = withContext(Dispatchers.IO) {
        try {
            vehicleDao.deleteAllLicenses()
            vehicleDao.insertLicense(license)
        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun insertFleetToDB(fleets: List<FleetTbl>) = withContext(Dispatchers.IO) {
        try {
            vehicleDao.deleteAllfleet()
            vehicleDao.insertFleet(fleets)
        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun insertVehicleToDB(vehicles: List<VehicleTbl>) = withContext(Dispatchers.IO) {
        try {
            vehicleDao.deleteAllVehicle()
            vehicleDao.insertVehicle(vehicles)
        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun insertVehicleDetailToDB(vehiclesDetail: List<VehicleDetailTbl>) = withContext(Dispatchers.IO) {
        try {
            vehicleDao.deleteAllVehicleDetail()
            vehicleDao.insertVehicleDetail(vehiclesDetail)

        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun getAllVehicleFromDB():List<VehicleModel>? = withContext(Dispatchers.IO) {
        val tasks:List<VehicleModel>? = vehicleDao.getAllVehicle()
        return@withContext tasks
    }


    suspend fun getFleetFromWebApi(): List<FleetTbl>? {
        val response =  RetrofitInstance.api.getFleet()
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

    suspend fun getLicenseFromWebApi(): List<LicenseTbl>? {

        val response =  RetrofitInstance.api.getLicenses()

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



    suspend fun GetVehicleDetailFromWebApi(): List<VehicleDetailTbl>? {
        val response =  RetrofitInstance.api.getVehiclesDetails()
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