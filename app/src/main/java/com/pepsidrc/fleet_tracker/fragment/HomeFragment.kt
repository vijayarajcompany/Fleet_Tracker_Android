package com.pepsidrc.fleet_tracker.fragment

import android.R
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.HomeAdapter
import com.pepsidrc.fleet_tracker.common.Common
import com.pepsidrc.fleet_tracker.databinding.FragmentHomeBinding
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.repository.TaskRepository
import com.pepsidrc.fleet_tracker.repository.UserRepository
import com.pepsidrc.fleet_tracker.viewModel.HomeViewModel
import com.pepsidrc.fleet_tracker.viewModel.LoginViewModel
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskRepository: TaskRepository
    private var Progress_dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //    Lifecycle 1st call
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        Setup_UI()
    }

    //    Lifecycle 2nd call
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        taskRepository = activity?.let { TaskRepository(it.application,requireContext()) }!!
        val factory = HomeViewModel.Factory(taskRepository) // Factory
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java] // ViewModel

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

        viewModel.task_details?.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                if (task.isNotEmpty()) {
                    GetEmployeeFromWebApi()
                    Setup_Buisness()
                    Progress_dialog!!.hide()
                }
            }
        }


        viewModel.employee_details?.observe(viewLifecycleOwner) { employee ->
            if (employee != null) {
                if (employee.isNotEmpty()) {
                    GetVehiclePartsFromWebApi()
//                    Progress_dialog!!.hide()
                }
            }
        }

        viewModel.vehicleParts?.observe(viewLifecycleOwner) { parts ->
            if (parts != null) {
                if (parts.isNotEmpty()) {
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
                ).show()
            }
        }

        GetTaskDetails()
//      GetEmployeeFromWebApi()
    }

    private val onItemClick:(TaskModel) -> Unit = { tsk ->
        Log.i(TAG,"tapped task is $tsk")

        if (tsk.name.lowercase().contains("movement")){
            val action = HomeFragmentDirections.actionHomeFragmentToMovementFragment(tsk.id)
            view?.findNavController()?.navigate(action)
        }


//        val id = tsk.id
//        if (id<2){
//            val action = HomeFragmentDirections.actionHomeFragmentToMovementFragment(tsk.id)
//            view?.findNavController()?.navigate(action)
//        }

    }

    private fun Setup_Buisness() {
        lifecycleScope.launch {
            val  _taskModels = viewModel.getAllTasks()
            if(!_taskModels.isNullOrEmpty())
            {
                binding.HomePgRecyclerView.adapter = HomeAdapter(_taskModels,onItemClick)
            }
        }
    }


    private fun Setup_UI() {

        (activity as MainActivity).setHardwareBackPressedStatus(false)
        (activity as MainActivity).ChangeToolBarText("Fleet Tracker")
        (activity as MainActivity).HideShowHomeButton(true)

        Progress_dialog = Dialog(requireContext(), R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(com.pepsidrc.fleet_tracker.R.layout.progress_bar_large)
    }

    private fun GetTaskDetails() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.GetTaskDetails()
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun GetVehiclePartsFromWebApi() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.GetVehiclePartsFromWebApi()
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun GetEmployeeFromWebApi() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.GetEmployeeFromWebApi()
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }




}


