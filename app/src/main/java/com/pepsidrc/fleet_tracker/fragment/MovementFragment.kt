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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.HomeAdapter
import com.pepsidrc.fleet_tracker.adapter.MovementAdapter
import com.pepsidrc.fleet_tracker.common.Common
import com.pepsidrc.fleet_tracker.databinding.FragmentMovementBinding
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.model.TaskModel
import com.pepsidrc.fleet_tracker.repository.TaskRepository
import com.pepsidrc.fleet_tracker.viewModel.HomeViewModel
import com.pepsidrc.fleet_tracker.viewModel.MovementViewModel
import kotlinx.coroutines.launch

private const val TAG = "MovementFragment"

class MovementFragment : Fragment() {
//    companion object {
//        fun newInstance() = MovementFragment()
//    }
    val lgTg ="LOG TG"
    private lateinit var viewModel: MovementViewModel
    private lateinit var binding: FragmentMovementBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val args:MovementFragmentArgs by navArgs()
    private var taskid:Int? = null
    private var Progress_dialog: Dialog? = null
    private lateinit var taskRepository: TaskRepository

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val argument = args.taskID2
//        Toast.makeText(requireContext(),"${argument}", Toast.LENGTH_SHORT).show()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentMovementBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.i(lgTg,"Passing ARGUMENT ${args.taskid}")
        taskid = args.taskid
        return view
    }

    //    Lifecycle 1st call
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Setup_UI()
    }

    //    Lifecycle 2nd call
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProvider(this).get(MovementViewModel::class.java)

////        val tsk1 = SubTaskModel(1,"User To Fleet","title")
////        val tsk2 = SubTaskModel(2,"Fleet To User","title")
////        val tsk3 = SubTaskModel(3,"User To User","title")
////        val _tasks:List<SubTaskModel> = listOf(tsk1,tsk2,tsk3)
////
//        binding.MovementPgRecyclerView.adapter = MovementAdapter(_tasks,onItemClick)
////        linearLayoutManager = LinearLayoutManager(activity)
////        binding.MovementPgRecyclerView.layoutManager = linearLayoutManager
        // TODO: Use the ViewModel
        taskRepository = activity?.let { TaskRepository(it.application,requireContext()) }!!
        val factory = MovementViewModel.Factory(taskRepository) // Factory
        viewModel = ViewModelProvider(this, factory)[MovementViewModel::class.java] // ViewModel

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

        viewModel.subtask_details?.observe(viewLifecycleOwner) { subtask ->
            if (subtask != null) {
                if (subtask.isNotEmpty()) {
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

        GetSubTaskDetails()
//        Setup_Buisness()
    }
    private val onItemClick:(SubTaskModel) -> Unit = { subtsk ->
        Log.i(TAG,"tapped Subtask is $subtsk")
        val movementType = when (subtsk.name.lowercase().trim()) {
            "fleet to user" ->   "Hand Over"
            "user to fleet" ->  "Take Over"
            else -> {
                 "User To User"
            }
        }
            val action = MovementFragmentDirections.actionMovementFragmentToVehicleFragment(taskid!!,subtsk.id,movementType)
            view?.findNavController()?.navigate(action)
    }


    private fun Setup_Buisness() {
        lifecycleScope.launch {
            val  _subtaskModels = viewModel.getAllSubTasks()
            if(!_subtaskModels.isNullOrEmpty())
            {
                binding.MovementPgRecyclerView.adapter = MovementAdapter(_subtaskModels,onItemClick)
            }
        }
    }

    private fun Setup_UI() {

        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText("Movement CheckList")
        (activity as MainActivity).HideShowHomeButton(false)

        Progress_dialog = Dialog(requireContext(), R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(com.pepsidrc.fleet_tracker.R.layout.progress_bar_large)
    }

    private fun GetSubTaskDetails() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.GetSubTaskDetails()
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }


}