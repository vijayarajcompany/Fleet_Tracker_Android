package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.data.EmployeeTbl
import com.pepsidrc.fleet_tracker.data.SubTaskTbl
import com.pepsidrc.fleet_tracker.model.EmployeeModel
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.repository.HandorTakeOverRepository
import com.pepsidrc.fleet_tracker.repository.VehicleRepository
import kotlinx.coroutines.launch

private const val TAG = "HandOrTakeOverViewModel"

class HandOrTakeOverViewModel(private val handorTakeOverRepository: HandorTakeOverRepository) : ViewModel()
{
    class Factory(private val handorTakeOverRepository: HandorTakeOverRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HandOrTakeOverViewModel(handorTakeOverRepository) as T
        }
    }

//    private val _empdetails: MutableLiveData<EmployeeTbl>? =  MutableLiveData()
//    var employee_details: LiveData<EmployeeTbl>? = null
//        get() = _empdetails

    private val _empdetails: MutableLiveData<EmployeeModel?> =  MutableLiveData()
    var employee_details: MutableLiveData<EmployeeModel?>? = null
        get() = _empdetails

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    private val _errorEmpMessage = MutableLiveData<String?>(null)
    val errorEmpMessage: LiveData<String?>
        get() = _errorEmpMessage

    // LOCAL DATABASE CALLS
     fun getEmployeeFromDB(empID:Int){
         viewModelScope.launch {
             _errorMessage.value = null
             _isLoading.value = true
             var employee: EmployeeModel? = null
             try {
                 _isLoading.value = false
                 employee = handorTakeOverRepository.getEmployeeFromDB(empID)
                 _empdetails?.value = employee
             } catch (e: Exception) {
                 _isLoading.value = false
                 _errorEmpMessage.value = e.message
                 Log.e(TAG, "Exception $e")
             }

         }
    }






}