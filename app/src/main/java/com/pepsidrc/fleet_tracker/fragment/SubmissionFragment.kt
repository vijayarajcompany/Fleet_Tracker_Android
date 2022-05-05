package com.pepsidrc.fleet_tracker.fragment

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.EmiratesAdapter
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
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
    private var emiratesid:Int? = null


    private var bounce: Animation? = null
    private var shake: Animation? = null
    private var regshake: Animation? = null
    private var Progress_dialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmissionBinding.inflate(inflater, container, false)
        val view = binding.root
        heading = args.heading
        emiratesid = args.emiratesid
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
        Progress_dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(R.layout.progress_bar_large)

        bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        regshake = AnimationUtils.loadAnimation(requireContext(), R.anim.regshake)

        with(binding){
            validEmiratesID = true
            SubmissionPgEmiratesIDErrorLabel.text = ""
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


            SubmissionPgEmiratesTakePhotoButton.setOnClickListener{
                showPictureTypeDialog()
            }


            SubmissionPgEmiratesIDInfoEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
                    printMsg("IME_ACTION_DONE")
                    ValidateEmiratesID()
                    true
                }
                else
                {
                    false
                }
            }
        }
    }

    fun ValidateEmiratesID(){

        with(binding){

            if( !SubmissionPgEmiratesIDInfoEditText.text.isNullOrEmpty())
            {
                val userEnterEmiratesID = SubmissionPgEmiratesIDInfoEditText.text.toString()

                if(!userEnterEmiratesID.startsWith("786")){
                    validEmiratesID = false
                    SubmissionPgEmiratesIDErrorLabel.text = "Please Enter a valid Emirates ID"
                    SubmissionPgEmiratesIDErrorLabel.startAnimation(regshake)

                }
               else if(userEnterEmiratesID.toInt() != emiratesid){
                    validEmiratesID = false
                    SubmissionPgEmiratesIDErrorLabel.text = "EmiratesID is not Matching with our records"
                    SubmissionPgEmiratesIDErrorLabel.startAnimation(regshake)
                }
            }
          else
            {
                validEmiratesID = false
                SubmissionPgEmiratesIDErrorLabel.text = "EmiratesID should not be empty"
                SubmissionPgEmiratesIDErrorLabel.startAnimation(regshake)
            }
        }

    }




    fun printMsg(message:String)
    {
        Log.i("TAG",message)
    }

    //DIALOG
    private fun showPictureTypeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater.inflate(R.layout.dialog_picturetype_submission, null)
        builder.setView(inflater)
        builder.setCancelable(false)
        val dialogImagecancelbtn = inflater.findViewById<Button>(R.id.pictureTypeCancel_button)
        val frontsidebtn = inflater.findViewById<Button>(R.id.frontside_button)
        val backsidebtn = inflater.findViewById<Button>(R.id.backside_button)

        val dialog: AlertDialog = builder.create()
        dialogImagecancelbtn?.setOnClickListener {
            dialog.cancel()
        }

        frontsidebtn?.setOnClickListener {
            dialog.cancel()
        }
        backsidebtn?.setOnClickListener {
            dialog.cancel()
        }


        dialog.show()
    }
    private fun showReview() {
//        val builder = AlertDialog.Builder(requireContext())
//        val inflater = layoutInflater.inflate(R.layout.dialog_review_takehandover, null)
//        builder.setView(inflater)
//        builder.setCancelable(false)
//        val dialogImagecancelbtn = inflater.findViewById<Button>(R.id.SignCancel_button)
//        val dialog: AlertDialog = builder.create()
//        dialogImagecancelbtn?.setOnClickListener {
//            dialog.cancel()
//        }
//
//        val reviewHeading = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
//
//        dialog.show()
    }



}