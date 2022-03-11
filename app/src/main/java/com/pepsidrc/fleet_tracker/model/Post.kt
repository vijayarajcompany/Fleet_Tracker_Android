package com.pepsidrc.fleet_tracker.model

//import com.squareup.moshi.Json
//import com.squareup.moshi.JsonClass
import java.io.Serializable


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String):Serializable


//data class Post(
//    val userId: Int,
//    val id: Int,
//    val title: String,
//    @Json(name = "body") val content: String): Serializable