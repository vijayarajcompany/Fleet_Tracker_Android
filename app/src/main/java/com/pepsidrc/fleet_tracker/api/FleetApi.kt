package com.pepsidrc.fleet_tracker.api

import androidx.lifecycle.LiveData
import com.pepsidrc.fleet_tracker.data.*
import com.pepsidrc.fleet_tracker.model.*
import retrofit2.Response
import retrofit2.http.GET

interface FleetApi {


    @GET("users/")
    suspend fun getLoginDetails(): Response<List<UserTbl>>

    @GET("task/")
    suspend fun getTasks(): Response<List<TaskTbl>>

    @GET("subtask/")
    suspend fun getSubTasks(): Response<List<SubTaskTbl>>

    @GET("fleet/")
    suspend fun getFleet(): Response<List<FleetTbl>>

    @GET("vehicle/")
    suspend fun getVehicles(): Response<List<VehicleTbl>>

    @GET("vehicledetail/")
    suspend fun getVehiclesDetails(): Response<List<VehicleDetailTbl>>

    @GET("employees/")
    suspend fun getEmployees(): Response<List<EmployeeTbl>>

    @GET("parts/")
    suspend fun getVehicleParts(): Response<List<VehiclePartTbl>>

    @GET("emirates/")
    suspend fun getEmirates(): Response<List<EmiratesTbl>>

    @GET("department/")
    suspend fun getDepartment(): List<DepartmentModel>



//   below are some examples not using
    @GET("breaches")
    suspend fun getBreaches(): List<BreachesModel>

    @GET("restrooms")
    suspend fun getRestrooms(): List<RestroomModel>

    @GET("posts/")
    suspend fun getPosts(): List<Post>

}