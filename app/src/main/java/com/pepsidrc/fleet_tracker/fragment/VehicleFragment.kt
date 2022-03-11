package com.pepsidrc.fleet_tracker.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.VehicleAdapter
import com.pepsidrc.fleet_tracker.databinding.FragmentVehicleBinding
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.viewModel.VehicleViewModel


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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleBinding.inflate(inflater, container, false)
        val view = binding.root
        taskid = args.taskid
        subtaskid = args.subtaskid
        movementType = args.movementType
        setup()
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
        // TODO: Use the ViewModel
        val _vehicle:List<VehicleModel> = mutableListOf(
            VehicleModel(1,"CAR"),
            VehicleModel(2,"TRUCK"),
            VehicleModel(3,"VAN"),
            VehicleModel(4,"BUS"),
            VehicleModel(5,"FORK LIFT")
        )
        binding.VehiclePgRecyclerView.adapter = activity?.let { VehicleAdapter(_vehicle, it.applicationContext,onItemClick) }
    }

    private val onItemClick:(VehicleModel) -> Unit = {vehiclee ->
        Log.i(TAG,"this is task $vehiclee")

        var heading = ""
        when (vehiclee.id) {
            1 ->  heading = "CAR"
            2 ->   heading = "TRUCK"
            3 ->   heading = "VAN"
            4 ->   heading = "BUS"
            else -> {
                heading = "FORK LIFT"
            }
        }

        heading = heading + " " + movementType


        if(vehiclee.id<5){
            val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment(taskid!!,subtaskid!!,vehiclee.id,heading)
            view?.findNavController()?.navigate(action)
        }
    }

    fun setup(){
        (activity as MainActivity).setHardwareBackPressedStatus(true)

        (activity as MainActivity).ChangeToolBarText(movementType!!)
        (activity as MainActivity).HideShowHomeButton(false)

    }

}