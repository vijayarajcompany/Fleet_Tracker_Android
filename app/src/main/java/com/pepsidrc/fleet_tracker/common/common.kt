package com.pepsidrc.fleet_tracker.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object UserDetails {
    var empID: Int = 0
    var userName: String = ""
    var authenticationToken: String = ""
}

object Common {

    private var _isInternetAvailable = MutableLiveData(false)
    val isInternetAvailable: LiveData<Boolean>
        get() = _isInternetAvailable



    fun checkConnectivity(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(activeNetwork?.isConnected!=null) {
            _isInternetAvailable.value = true
            return activeNetwork.isConnected
        }
        _isInternetAvailable.value = false
        return false
    }


}