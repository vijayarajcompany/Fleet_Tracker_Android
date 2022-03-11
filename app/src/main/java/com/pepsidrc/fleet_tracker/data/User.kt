package com.pepsidrc.fleet_tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tbl_User")
data class UserTbl(
    @PrimaryKey
    var name: String,
    var password: String
)

//@PrimaryKey var id: Int,