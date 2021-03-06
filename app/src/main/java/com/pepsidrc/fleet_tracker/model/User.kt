package com.pepsidrc.fleet_tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//import java.io.Serializable
@Parcelize
data class UserModel
    (
    var name: String,
    val password: String
) : Parcelable
