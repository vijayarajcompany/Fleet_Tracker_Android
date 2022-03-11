package com.pepsidrc.fleet_tracker.data
import androidx.room.Entity
import androidx.room.ForeignKey
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

//@Entity(tableName = "Tbl_SubTask")
////@ForeignKey(entity = TaskTbl, parentColumns = ["id"], childColumns = ["task_id"], onDelete = NO_ACTION)
////@Entity(tableName = "Tbl_SubTask",foreignKeys = @ForeignKey(entity=TaskTbl.class, parentColumns="id", childColumns="task_id"))
//data class SubTaskTbl(
//    @PrimaryKey
//    var id: Int,
//    @Relation
//        (
//        parentColumn ="id",
//        entityColumn = "task_id"
//    )
//    var task_id: List<TaskTbl>,
//    var name: String,
//
//)

//var task_id: Int,
//@Entity(tableName = "tasks")
//data class Task (
//    @PrimaryKey(autoGenerate = true) val id:Int =0,
//
//        )
