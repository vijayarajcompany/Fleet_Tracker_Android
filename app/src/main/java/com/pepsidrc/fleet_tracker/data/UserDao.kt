package com.pepsidrc.fleet_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pepsidrc.fleet_tracker.model.UserModel

@Dao
interface UserDao {

    @Insert
    fun insertUser(users: List<UserTbl>)

//    @Insert
//    fun insertUser(users: List<UserModel>)

    @Query("SELECT * FROM Tbl_User")
    fun getAllUsers(): List<UserModel>

//    @Query("SELECT * FROM Tbl_User WHERE name LIKE :username AND password LIKE :password")
//    fun getUserbyName_Pass(username: String, password: String): LiveData<List<UserModel>>

    @Query("SELECT * FROM Tbl_User WHERE name LIKE :username AND password LIKE :password")
    fun getUserbyName_Pass(username: String, password: String): List<UserModel>?

    @Query("DELETE  FROM Tbl_User")
    fun deleteAllUsers()
}


