package com.pepsidrc.fleet_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel

@Dao
interface TaskDao {

    @Insert
    fun insertTask(tasks: List<TaskTbl>)

    @Insert
    fun insertSubTask(subtasks: List<SubTaskTbl>)

    @Insert
    fun insertVehiclePart(parts: List<VehiclePartTbl>)

    @Insert
    fun insertEmirate(parts: List<EmiratesTbl>)


    @Query("SELECT * FROM Tbl_Task")
    fun getAllTasks(): List<TaskModel>


    @Query("SELECT * FROM Tbl_SubTask")
    fun getAllSubTasks(): List<SubTaskModel>

//    @Query("SELECT TOP(1) FROM Tbl_SubTask WHERE name = :name")
//    fun getVehiclePartByName(name:String): VehiclePartsModel

//    @Query("SELECT id, name FROM Tbl_VehiclePart WHERE id IN (SELECT id FROM Tbl_Vehicle WHERE LOWER(name) = :name)")
//    fun getVehiclePart(name:String): List<VehiclePartsModel>

//    @Query("SELECT id, name FROM Tbl_VehiclePart WHERE id IN (:partsID)")
//    fun getVehiclePart(partsID:List<Int>): List<VehiclePartsModel>

    @Query("DELETE  FROM Tbl_Task")
    fun deleteAllTasks()

    @Query("DELETE  FROM Tbl_SubTask")
    fun deleteAllSubTasks()

    @Query("DELETE  FROM Tbl_VehiclePart")
    fun deleteAllParts()

    @Query("DELETE  FROM Tbl_Emirates")
    fun deleteAllEmirates()

}
