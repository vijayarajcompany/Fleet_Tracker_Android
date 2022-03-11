package com.pepsidrc.fleet_tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//data class Vehicle
//    (
//    var empid: Int,
//    var username: String,
//    var password: String
//) : Serializable


@Parcelize
data class VehicleModel (var id: Int, var name: String): Parcelable

@Parcelize
data class VehiclePartsModel (var id: Int, var name: String): Parcelable

@Parcelize
data class EmiratesModel (var id: Int, var name: String): Parcelable

@Parcelize
data class LicenseModel (var id: Int, var name: String): Parcelable

@Parcelize
data class PlateCodeModel (var id: Int, var name: String): Parcelable