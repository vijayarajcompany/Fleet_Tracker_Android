package com.pepsidrc.fleet_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.TaskModel

@Dao
interface TaskDao {
    @Insert
    fun insertTask(tasks: List<TaskTbl>)

    @Query("SELECT * FROM Tbl_Task")
    fun getAllTasks(): List<TaskModel>

    @Query("DELETE  FROM Tbl_Task")
    fun deleteAllTasks()
}
