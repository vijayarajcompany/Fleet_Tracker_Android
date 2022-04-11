package com.pepsidrc.fleet_tracker.data

import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters


@Database(entities = [UserTbl::class,TaskTbl::class,SubTaskTbl::class,VehicleTbl::class,EmployeeTbl::class,VehiclePartTbl::class,EmiratesTbl::class,VehicleDetailTbl::class], version = 14)
@TypeConverters(Converters::class)
abstract class FleetDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun vehicleDao(): VehiclesDao
    abstract fun employeeDao(): EmployeeDao

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