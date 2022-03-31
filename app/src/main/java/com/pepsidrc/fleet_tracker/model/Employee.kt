package com.pepsidrc.fleet_tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class EmployeeModel(var empid: Int, var name: String, var emiratesid: Int, var contactnumber: Int, var active: Boolean
):Parcelable

//var id: Int,