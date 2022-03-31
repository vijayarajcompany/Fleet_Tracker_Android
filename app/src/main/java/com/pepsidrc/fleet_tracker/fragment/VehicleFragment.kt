package com.pepsidrc.fleet_tracker.fragment

import android.R
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.VehicleAdapter
import com.pepsidrc.fleet_tracker.common.Common
import com.pepsidrc.fleet_tracker.databinding.FragmentVehicleBinding
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.repository.VehicleRepository
import com.pepsidrc.fleet_tracker.viewModel.VehicleViewModel
import kotlinx.coroutines.launch


private const val TAG = "VehicleFragment"

class VehicleFragment : Fragment() {
    companion object {
        fun newInstance() = VehicleFragment()
    }

    private lateinit var viewModel: VehicleViewModel
    private lateinit var binding: FragmentVehicleBinding
    private val args:VehicleFragmentArgs by navArgs()
    private var taskid:Int? = null
    private var subtaskid:Int? = null
    private var movementType:String? = null
    private var vehicleid:Int? = null
    private var Progress_dialog: Dialog? = null
    private lateinit var vehicleRepository: VehicleRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVehicleBinding.inflate(inflater, container, false)
        val view = binding.root
        taskid = args.taskid
        subtaskid = args.subtaskid
        movementType = args.movementType
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Setup_UI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
        vehicleRepository = activity?.let { VehicleRepository(it.application,requireContext()) }!!
        val factory = VehicleViewModel.Factory(vehicleRepository) // Factory
        viewModel = ViewModelProvider(this, factory)[VehicleViewModel::class.java] // ViewModel

        viewModel.isLoading.observe(viewLifecycleOwner) { Loading ->
            if (Loading) {
                Progress_dialog!!.show()
            } else {
                Progress_dialog!!.hide()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errors ->
            if (errors != null) {
                if (errors.isNotEmpty()) {
                    Progress_dialog!!.hide()
                }
            }
        }

        viewModel.vehicle?.observe(viewLifecycleOwner) { vehicle ->
            if (vehicle != null) {
                if (vehicle.isNotEmpty()) {
                    Setup_Buisness()
                    Progress_dialog!!.hide()
                }
            }
        }

        Common.isInternetAvailable.observe(viewLifecycleOwner) { available ->
            if (!available) {
                Toast.makeText(
                    requireContext(),
                    "There is no internet connection",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        GetVehicle()

//        viewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
//
//        val _vehicle:List<VehicleModel> = mutableListOf(
//            VehicleModel(1,"CAR"),
//            VehicleModel(2,"TRUCK"),
//            VehicleModel(3,"VAN"),
//            VehicleModel(4,"BUS"),
//            VehicleModel(5,"FORK LIFT")
//        )

    }

    private fun GetVehicle() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.GetVehicles()
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun Setup_Buisness() {
        lifecycleScope.launch {
            val  _vehicleModels = viewModel.getAllVehicles()
            if(!_vehicleModels.isNullOrEmpty())
            {
                binding.VehiclePgRecyclerView.adapter = activity?.let { VehicleAdapter(_vehicleModels, it.applicationContext,onItemClick) }
            }
        }
    }


    private val onItemClick:(VehicleModel) -> Unit = { vehiclee ->
        Log.i(TAG,"this is task $vehiclee")
        var vehicleName = vehiclee.name.uppercase()
        val heading = vehicleName + " " + movementType

        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment(taskid!!,subtaskid!!,vehiclee.name,heading)
        view?.findNavController()?.navigate(action)

    }

    fun Setup_UI(){
        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText(movementType!!)
        (activity as MainActivity).HideShowHomeButton(false)

        Progress_dialog = Dialog(requireContext(), R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(com.pepsidrc.fleet_tracker.R.layout.progress_bar_large)
    }

}