package com.pepsidrc.fleet_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.EmployeeModel

@Dao
interface EmployeeDao {
    @Insert
    fun insertEmployee(employees: List<EmployeeTbl>)

//    @Query("SELECT * FROM Tbl_Employee")
 //    @Query("SELECT * FROM Tbl_User WHERE name LIKE :username AND password LIKE :password")

    @Query("SELECT * FROM Tbl_Employee WHERE active = :active AND empid = :empID")
    fun getEmployee(empID:Int, active:Boolean): EmployeeModel



    @Query("DELETE  FROM Tbl_Employee")
    fun deleteAllEmployee()

}
