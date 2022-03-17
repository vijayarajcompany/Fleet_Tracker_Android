package com.pepsidrc.fleet_tracker.data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Tbl_Task")

data class TaskTbl(
    @PrimaryKey
    var id: Int,
    var name: String,
    var title: String
)

@Entity(tableName = "Tbl_SubTask", foreignKeys = [ForeignKey(entity = TaskTbl::class, parentColumns = ["id"], childColumns = ["task_id"], onDelete = CASCADE)])
data class SubTaskTbl(
    @PrimaryKey
    var id: Int,
    var task_id: Int,
    var name: String,
    )

