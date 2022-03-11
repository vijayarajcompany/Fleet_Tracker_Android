package com.pepsidrc.fleet_tracker.api

import androidx.lifecycle.LiveData
import com.pepsidrc.fleet_tracker.data.TaskTbl
import com.pepsidrc.fleet_tracker.data.UserTbl
import com.pepsidrc.fleet_tracker.model.*
import retrofit2.Response
import retrofit2.http.GET

interface FleetApi {

    @GET("users/")
    suspend fun getLoginDetails(): Response<List<UserTbl>>

    @GET("task/")
    suspend fun getTasks(): Response<List<TaskTbl>>









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