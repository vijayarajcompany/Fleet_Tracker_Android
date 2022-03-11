package com.pepsidrc.fleet_tracker.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.EmiratesAdapter
import com.pepsidrc.fleet_tracker.databinding.FragmentHandOrTakeOverBinding
import com.pepsidrc.fleet_tracker.databinding.FragmentSubmissionBinding
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
import com.pepsidrc.fleet_tracker.viewModel.SubmissionViewModel


private const val TAG = "SubmissionFragment"

class SubmissionFragment : Fragment() {

    companion object {
        fun newInstance() = SubmissionFragment()
    }

    private lateinit var viewModel: SubmissionViewModel
    private lateinit var binding: FragmentSubmissionBinding
    private val args:SubmissionFragmentArgs by navArgs()
    private var heading:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmissionBinding.inflate(inflater, container, false)
        val view = binding.root
        heading = args.heading
        setup()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubmissionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun setup(){
        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText(heading!!)

        with(binding){
            imgSubmissionDrivingFront.load(android.R.drawable.ic_menu_camera)
            imgSubmissionDrivingBack.load(android.R.drawable.ic_menu_camera)

            imgSubmissionEmiratesIDFront.load(android.R.drawable.ic_menu_camera)
            imgSubmissionEmiratesIDBack.load(android.R.drawable.ic_menu_camera)
            imgSubmissionMe.load(android.R.drawable.ic_menu_camera)

            imgSubmissionDriverSignature.load(R.drawable.signature_placeholder)
            imgSubmissionDRCSignature.load(R.drawable.signature_placeholder)

//            @android:drawable/ic_menu_camera
//            imgSubmissionMe.load(android.R.drawable.ic_menu_camera){
//                placeholder(android.R.drawable.ic_menu_camera)
//                build()
//            }

        }

    }






}