package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.data.SubTaskTbl
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.repository.TaskRepository
import kotlinx.coroutines.launch

private const val TAG = "MovementViewModel"

class MovementViewModel(private val taskRepository: TaskRepository) : ViewModel()
{
    class Factory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovementViewModel(taskRepository) as T
        }
    }
    // TODO: Implement the ViewModel

//    private val _subtaskdetails: List<SubTaskTbl>? = null
//    var subtask_details: List<SubTaskTbl>? = null
//        get() = _subtaskdetails


    private val _subtaskdetails: MutableLiveData<List<SubTaskTbl>>? =   MutableLiveData()
    var subtask_details: LiveData<List<SubTaskTbl>>? = null
        get() = _subtaskdetails

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    // ROOM DATABASE
    suspend fun getAllSubTasks():List<SubTaskModel>?{
        _errorMessage.value = null
        _isLoading.value = true
        var subtasks:List<SubTaskModel>? = null
        try {
            _isLoading.value = false
            subtasks = taskRepository.getAllSubTasksFromDB()
            var ss =23
            var sd =234

        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = e.message
            Log.e(TAG, "Exception $e")
        }
        return subtasks
    }
//    suspend fun getAllSubTasks():List<SubTaskTbl>?{
//        _errorMessage.value = null
//        _isLoading.value = true
//        var subtasks:List<SubTaskTbl>? = null
//        try {
//            _isLoading.value = false
//            subtasks = taskRepository.getAllSubTasksFromDB()
//            var sd =234
//
//        } catch (e: Exception) {
//            _isLoading.value = false
//            _errorMessage.value = e.message
//            Log.e(TAG, "Exception $e")
//        }
//        return subtasks
//    }
    // NETWORK CALLS
    fun GetSubTaskDetails() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val subtskDetails = taskRepository.getSubTasksFromWebApi()

                if (!subtskDetails.isNullOrEmpty()) {
                    taskRepository.insertSubTaskToDB(subtskDetails)
                    _subtaskdetails?.value = subtskDetails!!
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

}