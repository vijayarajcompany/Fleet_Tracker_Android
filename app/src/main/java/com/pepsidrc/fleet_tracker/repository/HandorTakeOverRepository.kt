package com.pepsidrc.fleet_tracker.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.pepsidrc.fleet_tracker.api.RetrofitInstance
import com.pepsidrc.fleet_tracker.data.*
import com.pepsidrc.fleet_tracker.model.EmployeeModel
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HandorTakeOverRepository (application: Application, contxt: Context) {
    private val employeeDao: EmployeeDao
//    private val employeeDao: EmployeeDao
    private var cntxt: Context

    init {
        val FleetDb = FleetDatabase.getDatabase(application)
        cntxt = contxt
        employeeDao = FleetDb!!.employeeDao()
    }

    suspend fun getEmployeeFromDB(empID:Int): EmployeeModel = withContext(Dispatchers.IO) {
        val employee = employeeDao.getEmployee(empID, true )
        return@withContext employee
    }




}