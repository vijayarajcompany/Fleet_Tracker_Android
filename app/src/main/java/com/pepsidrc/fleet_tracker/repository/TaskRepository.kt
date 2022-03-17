package com.pepsidrc.fleet_tracker.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.pepsidrc.fleet_tracker.api.RetrofitInstance
import com.pepsidrc.fleet_tracker.data.*
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository (application: Application, contxt: Context){
    private val taskDao: TaskDao
    private var cntxt:Context

    init {
        val FleetDb = FleetDatabase.getDatabase(application)
        cntxt = contxt
        taskDao =  FleetDb!!.taskDao()
    }

    suspend fun insertTaskToDB(tsks: List<TaskTbl>) = withContext(Dispatchers.IO) {

        try {
            taskDao.deleteAllTasks()
            taskDao.insertTask(tsks)
        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun insertSubTaskToDB(subtsks: List<SubTaskTbl>) = withContext(Dispatchers.IO) {
        try {
            taskDao.deleteAllSubTasks()
            val kk =taskDao.insertSubTask(subtsks)
            val skdkk =2348

        } catch (e: Exception) {
            var errorMessage = e.message
        }
    }

    suspend fun getAllTasksFromDB():List<TaskModel>? = withContext(Dispatchers.IO) {
        val tasks:List<TaskModel>? = taskDao.getAllTasks()
        return@withContext tasks
    }

    suspend fun getAllSubTasksFromDB(): List<SubTaskModel>? = withContext(Dispatchers.IO) {
        val subtasks = taskDao.getAllSubTasks()
        var kk =2452
        return@withContext subtasks
    }

//    suspend fun getAllSubTasksFromDB(): List<SubTaskTbl> = withContext(Dispatchers.IO) {
//        val subtasks = taskDao.getAllSubTasks()
//        var kk =2452
//        return@withContext subtasks
//    }

    suspend fun getTasksFromWebApi(): List<TaskTbl>? {
        val response =  RetrofitInstance.api.getTasks()
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

    suspend fun getSubTasksFromWebApi(): List<SubTaskTbl>? {
        val response =  RetrofitInstance.api.getSubTasks()
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
