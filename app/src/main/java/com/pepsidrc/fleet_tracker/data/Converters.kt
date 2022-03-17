package com.pepsidrc.fleet_tracker.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

//@ProvidedTypeConverter
class Converters {


//    private val gson = Gson()
//    @TypeConverter
//    fun stringToList(data: String?): List<Int> {
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType = object : TypeToken<List<Int>>() {
//
//        }.type
//        return gson.fromJson(data, listType)
//    }
//
//    @TypeConverter
//    fun ListToString(someObjects: List<Int>): String {
//        return gson.toJson(someObjects)
//    }

    //WHILE READING FROM THE WEB API (returning value from WebAPI)
    @TypeConverter
    fun FromPartsListToString(PartsListObjects: List<Int>): String {
        val moshi = Moshi.Builder().build()
//        val type = Types.newParameterizedType(Parts::class.java,List::class.java)
        var strParts:String = ""
        try {
            val type = Types.newParameterizedType(List::class.java,Int::class.javaObjectType)
            val adapter = moshi.adapter<List<Int>>(type)
            strParts = adapter.toJson(PartsListObjects)
        } catch (e: Exception) {
            val ss =  e.message
            val ss2 =  e.message
        }
        return strParts
    }
//     // Serialize
////    @TypeConverter
////    fun FromPartsListToString(PartsListObjects: List<Parts>): String {
////        val moshi = Moshi.Builder().build()
//////        val type = Types.newParameterizedType(Parts::class.java,List::class.java)
////        val type = Types.newParameterizedType(List::class.java,Parts::class.java)
////        val adapter = moshi.adapter<List<Parts>>(type)
////        val strParts:String = adapter.toJson(PartsListObjects)
////        return strParts
////    }


    //WHILE READING FROM THE LOCAL DATABASE    // De-Serialize
    @TypeConverter
    fun FromStringToList(json: String): List<Int>? {

        val moshi = Moshi.Builder().build()
        var listParts:List<Int>? = null
        try {
//        val type = Types.newParameterizedType(List::class.java, Parts::class.java)
//        val type = Types.newParameterizedType(Int::class.java)
            val type = Types.newParameterizedType(List::class.java,Int::class.javaObjectType)

            val adapter = moshi.adapter<List<Int>>(type)
            listParts = adapter.fromJson(json)

    } catch (e: Exception) {
        val KK =  e.message
        val KK2 =  e.message
    }

    return listParts
    }
















//    var moshi = Moshi.Builder().build()
//    var jsonAdapter: JsonAdapter<BlackjackHand> =
//        moshi.adapter<BlackjackHand>(BlackjackHand::class.java)
//
//    var json = jsonAdapter.toJson(blackjackHand)

//    @TypeConverter
//    fun FromListToString(someObjects: List<Int>): String {
//        val moshi = Moshi.Builder().build()
//
//        val adapter = moshi.adapter<List<Int>>(type)
//        return adapter.fromJson(value)
//
//    }

//    @TypeConverter
//    fun FromStringToList(value: String): List<Int>? {
//        val moshi = Moshi.Builder().build()
//        val type = Types.newParameterizedType(List::class.java, Parts::class.java)
//        val adapter = moshi.adapter<List<Int>>(type)
//        return adapter.fromJson(value)
//    }


//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time?.toLong()
//    }


//    @TypeConverter
//    fun ListToInt(someObjects: List<Int>): Int {
//        val moshi = Moshi.Builder().build()
//        return gson.toJson(someObjects)
//    }





}