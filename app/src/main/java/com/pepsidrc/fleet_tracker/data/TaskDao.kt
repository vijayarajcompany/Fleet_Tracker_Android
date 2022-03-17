package com.pepsidrc.fleet_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.model.TaskModel

@Dao
interface TaskDao {
    @Insert
    fun insertTask(tasks: List<TaskTbl>)

    @Insert
    fun insertSubTask(subtasks: List<SubTaskTbl>)


    @Query("SELECT * FROM Tbl_Task")
    fun getAllTasks(): List<TaskModel>

    @Query("SELECT * FROM Tbl_SubTask")
    fun getAllSubTasks(): List<SubTaskModel>

//    @Query("SELECT * FROM Tbl_SubTask")
//    fun getAllSubTasks(): List<SubTaskTbl>

    @Query("DELETE  FROM Tbl_Task")
    fun deleteAllTasks()

    @Query("DELETE  FROM Tbl_SubTask")
    fun deleteAllSubTasks()
}
