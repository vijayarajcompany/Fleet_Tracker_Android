package com.pepsidrc.fleet_tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class TaskModel (var id: Int, var name: String, var title: String): Parcelable

@Parcelize
data class SubTaskModel(var id: Int, var task_id: Int, var name: String):Parcelable






//data class TaskModel (var id: Int, var name: String, var title: String): Serializable

//data class SubTaskModel (var id: Int, var name: String, var title: String): Serializable

data class DepartmentModel (var id: Int, var name: String): Serializable

data class BreachesModel (var Name: String, var Title: String, var Domain: String): Serializable

data class RestroomModel (var id: Int, var name: String, var street: String, var city: String): Serializable



