        package com.pepsidrc.fleet_tracker.repository
        //import com.pepsidrc.fleet_tracker.viewModel.TAG
        import android.app.Application
        import android.widget.Toast
        import com.pepsidrc.fleet_tracker.api.RetrofitInstance
        import com.pepsidrc.fleet_tracker.data.FleetDatabase
        import com.pepsidrc.fleet_tracker.data.UserDao
        import com.pepsidrc.fleet_tracker.data.UserTbl
        import android.content.Context
        import com.pepsidrc.fleet_tracker.model.UserModel
        import kotlinx.coroutines.Dispatchers
        import kotlinx.coroutines.withContext

        class UserRepository (application: Application, contxt:Context){

//          //LOCAL DATABASE CALLS
            private val userDao: UserDao
            private var cntxt:Context

            init {
                val FleetDb = FleetDatabase.getDatabase(application)
                cntxt = contxt
                userDao =  FleetDb!!.userDao()
            }


            suspend fun insertUserToDB(users: List<UserTbl>) = withContext(Dispatchers.IO) {
               try {
                   userDao.deleteAllUsers()
                   userDao.insertUser(users)
               } catch (e: Exception) {
                   var errorMessage = e.message
               }
            }

            suspend fun getUserFromDB(user:UserModel):Boolean = withContext(Dispatchers.IO) {
                val authUser = userDao.getUserbyName_Pass(user.name,user.password)
                if (authUser.isNullOrEmpty()) {
                    return@withContext false
                }
                return@withContext true
            }

            // REMOTE CALLS
//            suspend fun getLoginDetails() {
//                val response =  RetrofitInstance.api.getLoginDetails()
//                response.isSuccessful.let {
//                    when (response.code()) {
//                        400 -> {
//                            Toast.makeText(cntxt, "Please contact administrator", Toast.LENGTH_SHORT).show()
//                        }
//                        500 -> {
//                            Toast.makeText(cntxt, "Internal Server Error", Toast.LENGTH_SHORT).show()
//                        }
//                        else -> {
//
//
//                            response.body()?.let { it1 -> insertUserToDB(it1) }
////                            return response.body()
//                        }
//                    }
//                }
//
//            }

            suspend fun getLoginUsersFromWebApi(): List<UserTbl>? {
                val response =  RetrofitInstance.api.getLoginDetails()
                response.isSuccessful.let {
                    when (response.code()) {
                        400 -> {
                            Toast.makeText(cntxt, "Please contact administrator", Toast.LENGTH_SHORT).show()
                        }
                        500 -> {
                            Toast.makeText(cntxt, "Internal Server Error", Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                            return response.body()
                        }
                    }
                }
                return emptyList()
            }

//            suspend fun GetLoginDetails111111():List<UserTbl>{
//
//                var userList:List<UserTbl> = listOf()
//                try {
//                    userList = RetrofitInstance.api.getLoginDetails()
//                    return userList
//                } catch (e: Exception) {
//                    val errorMessage = e.message
//                }
//                return userList
//            }
//
//            suspend fun getLoginDetails():List<UserTbl>{
//
//               return  RetrofitInstance.api.getLoginDetails()
//
//            }






        }