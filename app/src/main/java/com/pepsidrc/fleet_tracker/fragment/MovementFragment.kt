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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.MovementAdapter
import com.pepsidrc.fleet_tracker.databinding.FragmentMovementBinding
import com.pepsidrc.fleet_tracker.model.SubTaskModel
import com.pepsidrc.fleet_tracker.viewModel.MovementViewModel

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
        taskid = args.taskid
        setup()
        /* Log.i(lgTg,"Passing ARGUMENT ${args.tid}") */
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovementViewModel::class.java)
        // TODO: Use the ViewModel
        val tsk1 = SubTaskModel(1,"User To Fleet","title")
        val tsk2 = SubTaskModel(2,"Fleet To User","title")
        val tsk3 = SubTaskModel(3,"User To User","title")
        val _tasks:List<SubTaskModel> = listOf(tsk1,tsk2,tsk3)

        binding.MovementPgRecyclerView.adapter = MovementAdapter(_tasks,onItemClick)
//        linearLayoutManager = LinearLayoutManager(activity)
//        binding.MovementPgRecyclerView.layoutManager = linearLayoutManager

    }

    private val onItemClick:(SubTaskModel) -> Unit = {subtsk ->
        Log.i(lgTg,"this is task $subtsk")
        var movementType = ""

        when (subtsk.id) {
            1 ->  movementType = "Hand Over"
            2 ->   movementType = "Take Over"
            else -> {
                movementType = "User To User"
            }
        }

        if(subtsk.id <3){
            val action = MovementFragmentDirections.actionMovementFragmentToVehicleFragment(taskid!!,subtsk.id,movementType)
            view?.findNavController()?.navigate(action)
        }

    }

    fun setup(){
        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText("Movement CheckList")
        (activity as MainActivity).HideShowHomeButton(false)
   }





}