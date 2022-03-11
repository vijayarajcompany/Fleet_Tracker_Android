package com.pepsidrc.fleet_tracker.data

import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room


@Database(entities = [UserTbl::class,TaskTbl::class], version = 3)
abstract class FleetDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var FleetInstance: FleetDatabase? = null
        fun getDatabase(context: Context): FleetDatabase? {
            if (FleetInstance == null) {
//                synchronized(FleetDatabase::class.java) {
                synchronized(this) {
                    if (FleetInstance == null) {
                        try {
                            FleetInstance = Room.databaseBuilder(
                                context.applicationContext,
                                FleetDatabase::class.java, "fleet_database"
                            ).fallbackToDestructiveMigration().build()
                        }
                        catch (e: Exception)
                        {
                            var errorMessage = e.message
                        }

                    }
                }
            }
            return FleetInstance
        }
    }


//    context.applicationContext,


}