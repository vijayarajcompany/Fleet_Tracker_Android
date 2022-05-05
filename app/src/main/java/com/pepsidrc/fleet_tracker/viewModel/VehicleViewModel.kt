package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.data.FleetTbl
import com.pepsidrc.fleet_tracker.data.VehicleTbl
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.repository.VehicleRepository
import kotlinx.coroutines.launch


private const val TAG = "VehicleViewModel"

class VehicleViewModel(private val vehicleRepository: VehicleRepository) : ViewModel()
{

    class Factory(private val vehicleRepository: VehicleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VehicleViewModel(vehicleRepository) as T
        }
    }

    private val _fleet: MutableLiveData<List<FleetTbl>>? =   MutableLiveData()
    var fleet: LiveData<List<FleetTbl>>? = null
        get() = _fleet

    private val _vehicle: MutableLiveData<List<VehicleTbl>>? =   MutableLiveData()
    var vehicle: LiveData<List<VehicleTbl>>? = null
        get() = _vehicle

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    // ROOM DATABASE
    suspend fun getAllVehicles():List<VehicleModel>?{
        _errorMessage.value = null
        _isLoading.value = true
        var vehicles:List<VehicleModel>? = null
        try {
            _isLoading.value = false
            vehicles = vehicleRepository.getAllVehicleFromDB()
            var sd =234

        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = e.message
            Log.e(TAG, "Exception $e")
        }
        return vehicles
    }


    // NETWORK CALLS
    fun getFleetFromWebApi() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val FleetList = vehicleRepository.getFleetFromWebApi()
                if (!FleetList.isNullOrEmpty()) {
                    vehicleRepository.insertFleetToDB(FleetList)
                    _fleet?.value = FleetList!!
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
    fun getVehicleFromWebApi() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val vehicleList = vehicleRepository.getVehicleFromWebApi()
                if (!vehicleList.isNullOrEmpty()) {
                    vehicleRepository.insertVehicleToDB(vehicleList)
                    _vehicle?.value = vehicleList!!
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
    fun GetVehicleDetailFromWebApi() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val vehicleDetailList = vehicleRepository.GetVehicleDetailFromWebApi()
                if (!vehicleDetailList.isNullOrEmpty()) {
                    vehicleRepository.insertVehicleDetailToDB(vehicleDetailList)
//                    _vehicle?.value = vehicleList!!
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
