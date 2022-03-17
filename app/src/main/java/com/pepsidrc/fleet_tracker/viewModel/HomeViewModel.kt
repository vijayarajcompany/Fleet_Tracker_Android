package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.api.RetrofitInstance
import com.pepsidrc.fleet_tracker.data.SubTaskTbl
import com.pepsidrc.fleet_tracker.data.TaskTbl
import com.pepsidrc.fleet_tracker.data.UserTbl
import com.pepsidrc.fleet_tracker.model.*
import com.pepsidrc.fleet_tracker.repository.TaskRepository
import com.pepsidrc.fleet_tracker.repository.UserRepository
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"
    class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel()
    {
        class Factory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(taskRepository) as T
            }
        }

        private val _taskdetails: MutableLiveData<List<TaskTbl>>? =   MutableLiveData()
        var task_details: LiveData<List<TaskTbl>>? = null
            get() = _taskdetails

//        private val _taskdetails: List<TaskTbl>? = null
//        var task_details: List<TaskTbl>? = null
//            get() = _taskdetails

        private val _isLoading = MutableLiveData(false)
        val isLoading: LiveData<Boolean>
            get() = _isLoading

        private val _errorMessage = MutableLiveData<String?>(null)
        val errorMessage: LiveData<String?>
            get() = _errorMessage


        // ROOM DATABASE
        suspend fun getAllTasks():List<TaskModel>?{
            _errorMessage.value = null
            _isLoading.value = true
            val tasks:List<TaskModel>? = listOf()
            try {
                _isLoading.value = false
                return taskRepository.getAllTasksFromDB()
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            }
            return tasks
        }

        // NETWORK CALLS
        fun GetTaskDetails() {
            viewModelScope.launch {
                _errorMessage.value = null
                _isLoading.value = true

                try {

                    val tskDetails = taskRepository.getTasksFromWebApi()

                    if (!tskDetails.isNullOrEmpty()) {
                        taskRepository.insertTaskToDB(tskDetails)
                        _taskdetails?.value = tskDetails!!
                    }
                    _isLoading.value = false
//                    task_details = tasks
//                    if (!tasks.isNullOrEmpty()) {
//                        taskRepository.insertTaskToDB(tskDetails)
//                    }

                } catch (e: Exception) {
                    _isLoading.value = false
                    _errorMessage.value = e.message
                    Log.e(TAG, "Exception $e")

                } finally {
                    _isLoading.value = false
                }
            }
        }



    fun getDepartment() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val allTasks = RetrofitInstance.api.getDepartment()
//                val currentPosts = _depart.value ?: emptyList()
//                _tasks.value = allTasks

                var tssst = 786234


            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getRestrooms() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val allTasks = RetrofitInstance.api.getRestrooms()
//                val currentPosts = _tasks.value ?: emptyList()
//                _tasks.value = allTasks

            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBreaches() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val allTasks = RetrofitInstance.api.getBreaches()
//                val currentPosts = _tasks.value ?: emptyList()
//                _tasks.value = allTasks

            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

}