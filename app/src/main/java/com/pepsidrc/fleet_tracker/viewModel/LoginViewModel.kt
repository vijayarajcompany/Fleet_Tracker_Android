package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.data.UserTbl
import com.pepsidrc.fleet_tracker.model.UserModel
import com.pepsidrc.fleet_tracker.repository.UserRepository
import kotlinx.coroutines.launch


private const val TAG = "LoginViewModel"

//class MyViewModelFactory (private val arg: Int) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(LoginViewModel: Class<T>): T{
//        return LoginViewModel.getConstructor(UserRepository::class.java).newInstance(arg)
//    }
//}

//class BasicMainViewModel(private val repository: Repository) : ViewModel()
//class LoginViewModel(application: Application): ViewModel (){

class LoginViewModel(private val userRepository: UserRepository) : ViewModel()
{
    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(userRepository) as T
        }
    }
//class LoginViewModel(): ViewModel (){
//    private val allUserModel: List<UserModel>
//    private val userRepository = UserRepository(Application())
//    private val userRepository = UserRepository(this.)
//    init {
//        allUserModel = userRepository.allUserModel
//    }

    // TODO: Implement the ViewModel
//    private val _logindetails: MutableLiveData<List<UserModel>> = MutableLiveData()
//    val login_details: LiveData<List<UserModel>>
//        get() = _logindetails

//    private val _logindetails: List<UserModel>? = null
//    var login_details: List<UserModel>? = null
//        get() = _logindetails

    private val _logindetails: List<UserTbl>? = null
    var login_details: List<UserTbl>? = null
        get() = _logindetails

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    // ROOM DATABASE
    suspend fun isAuthenticatedUser(user:UserModel):Boolean{

            _errorMessage.value = null
            _isLoading.value = true

            try {
                _isLoading.value = false
//                Thread.sleep(10000)
               return (userRepository.getUserFromDB(user))

            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            }

        return false
}
//    fun isAuthenticatedUser(user:UserModel):Boolean{
//
//        viewModelScope.launch {
//            _errorMessage.value = null
//            _isLoading.value = true
//            try {
//
//                _isLoading.value = false
//                val isauth = (userRepository.getUser(user))
//
//            } catch (e: Exception) {
//                _isLoading.value = false
//                _errorMessage.value = e.message
//                Log.e(TAG, "Exception $e")
//            }
//        }
//
//        return true
//    }

    // NETWORK CALLS
    fun GetLoginDetails() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
//            Thread.sleep(10000)
            try {
                val lgnDetails = userRepository.getLoginUsersFromWebApi()
//                val currentPosts = _logindetails.value ?: emptyList()
//                _logindetails.value = lgnDetails
                login_details = lgnDetails
                if (!lgnDetails.isNullOrEmpty()) {
                    userRepository.insertUserToDB(lgnDetails)
                }

                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")

            } finally {
                _isLoading.value = false
            }
        }
    }


//    fun GetLoginDetails() {
//        viewModelScope.launch {
//            _errorMessage.value = null
//            _isLoading.value = true
//            try {
//                val loginDetails = RetrofitInstance.api.getLoginDetails()
////              val currentPosts = _logindetails.value ?: emptyList()
//                _logindetails.value = loginDetails
//                //userRepository.insertUserToDB()
//                _isLoading.value = false
//
//            } catch (e: Exception) {
//                _isLoading.value = false
//                _errorMessage.value = e.message
//                Log.e(TAG, "Exception $e")
//
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

}


