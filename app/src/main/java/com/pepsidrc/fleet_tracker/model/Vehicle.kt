package com.pepsidrc.fleet_tracker.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

//data class Vehicle
//    (
//    var empid: Int,
//    var username: String,
//    var password: String
//) : Serializable

//@Parcelize
//data class VehicleModel (var id: Int, var name: String): Parcelable

@Parcelize
data class VehicleModel (var name: String,
                         var part:  @RawValue List<Int>): Parcelable
//@Parcelize
//data class VehiclePartsModel (var id: Int, var name: String): Parcelable

@Parcelize
data class VehiclePartsModel (var id: Int, var name: String,var selected:Boolean = true): Parcelable

@Parcelize
data class LicenseModel (var id: Int, var name: String,var selected:Boolean = true): Parcelable


@Parcelize
data class EmiratesModel (var id: Int, var name: String, var code: String): Parcelable




@Parcelize
data class PlateCodeModel (var id: Int, var name: String): Parcelable

//@Parcelize
//data class FuelCardModel(var id:Int, var name:String): Parcelable

@Parcelize
data class FuelTankModel(var id:String, var name:String): Parcelable

////Custom Parcelers
//
////class ExternalClass(val value: Int)
//
////object ExternalClassParceler : Parceler<VehicleModel> {
//
//object VehicleModelClassParceler : Parceler<VehicleModel> {
//    override fun create(parcel: Parcel) = VehicleModel(parcel.readString(), parcel.readList(Parts))
//
//    override fun VehicleModel.write(parcel: Parcel, flags: Int) {
//        parcel.write(name,part)
//    }
//}