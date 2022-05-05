package com.pepsidrc.fleet_tracker.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.data.FleetTbl
import com.pepsidrc.fleet_tracker.data.LicenseTbl
import com.pepsidrc.fleet_tracker.model.LicenseModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
import com.pepsidrc.fleet_tracker.repository.VehicleRepository
import kotlinx.coroutines.launch

private const val TAG = "DistributionViewModel"

class DistributionViewModel(private val vehicleRepository: VehicleRepository) : ViewModel()
{

    class Factory(private val vehicleRepository: VehicleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DistributionViewModel(vehicleRepository) as T
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    private val _license: MutableLiveData<List<LicenseTbl>>? =   MutableLiveData()
    var License: LiveData<List<LicenseTbl>>? = null
        get() = _license

    suspend fun getVehiclePartsFromDB(partsID:List<Int>):List<VehiclePartsModel>?{

        _errorMessage.value = null
        _isLoading.value = true

        var parts:List<VehiclePartsModel>? = null

        try {
            _isLoading.value = false
            parts = vehicleRepository.getVehiclePartsFromDB(partsID)

        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = e.message
            Log.e(TAG, "Exception $e")
        }

        return parts
    }



    suspend fun getDistributionLicenseFromDB():List<LicenseModel>?{
        _errorMessage.value = null
        _isLoading.value = true
        var license:List<LicenseModel>? = null
        try {
            _isLoading.value = false
            license = vehicleRepository.getDistributionLicenseFromDB()

        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = e.message
            Log.e(TAG, "Exception $e")
        }
        return license
    }





    fun getDistributionLicenseFromWebApi() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {

                val licenseList = vehicleRepository.getLicenseFromWebApi()

                if (!licenseList.isNullOrEmpty()) {
                    vehicleRepository.insertDistributionLicenseToDB(licenseList)
                    _license?.value = licenseList!!
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