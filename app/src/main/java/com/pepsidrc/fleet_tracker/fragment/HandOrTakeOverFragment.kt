package com.pepsidrc.fleet_tracker.fragment

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.EmiratesAdapter
import com.pepsidrc.fleet_tracker.adapter.PlateCodeAdapter
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
import com.pepsidrc.fleet_tracker.databinding.FragmentHandOrTakeOverBinding
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.PlateCodeModel
import com.pepsidrc.fleet_tracker.viewModel.HandOrTakeOverViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.util.Pair
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.pepsidrc.fleet_tracker.R

private const val TAG = "HandOrTakeOverFragment"

class HandOrTakeOverFragment : Fragment() {

    //    companion object {
//        fun newInstance() = HandOrTakeOverFragment()
//    }
    private lateinit var binding: FragmentHandOrTakeOverBinding
    private lateinit var viewModel: HandOrTakeOverViewModel
    private val args:HandOrTakeOverFragmentArgs by navArgs()
    private var taskid:Int? = null
    private var subtaskid:Int? = null
    private var vehicleid:Int? = null
    private var heading:String? = null


    private var strplateNumber:String? = null
    private var stremirates:String? = null
    private var strPlatecode:String? = null
    private var strKM:String? = null
    private var strFuelTank:String? = null
    private var strDriverID:String? = null
    private var strDriverName:String? = null
    private var strContactNo:String? = null




    var dateofBirth: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data Binding for activity
//        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_hand_or_take_over)
        //Data Binding
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_hand_or_take_over,container,false)
         val view = binding.root
        //        binding.HandOrTakeOverPgContinueButton.setOnClickListener {
        //            openDistributionPage()
        //        }
        taskid = args.taskid
        subtaskid = args.subtaskid
        vehicleid = args.vehicleid
        heading = args.heading
        binding.HandOrTakeOverPgContinueButton.setOnClickListener {
            hideKeyboard()
            openDistributionPage()
        }
        setup()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HandOrTakeOverViewModel::class.java)
        // TODO: Use the ViewModel

        val tsk1 = EmiratesModel(33, "Abu Dhabi")
        val tsk2 = EmiratesModel(33, "Ajman")
        val tsk3 = EmiratesModel(33, "Dubai")
        val tsk4 = EmiratesModel(33, "Sharjah")
        val _emirates: List<EmiratesModel> = listOf(tsk1, tsk2, tsk3, tsk4)

        binding.HandorTakeOverPgEmiratesRecyclerView.adapter =
            activity?.let { EmiratesAdapter(_emirates, it.applicationContext, onItemEmiratesClick) }


        val tsk11 = PlateCodeModel(33, "L")
        val tsk21 = PlateCodeModel(33, "H")
        val tsk31 = PlateCodeModel(33, "B")
        val tsk41 = PlateCodeModel(33, "C")
        val _code: List<PlateCodeModel> = listOf(tsk11, tsk21, tsk31, tsk41)
        binding.HandorTakeOverPgCodeRecyclerView.adapter =
            activity?.let { PlateCodeAdapter(_code, it.applicationContext, onItemCodeClick) }


    }

    fun setup() {

        val date = getCurrentDateTime()
//      val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        val dateInString = date.toString("HH:mm:ss")

        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText(heading!!)

//        DDate1()
          DDate2()
//        DDate3()

        with(binding){

            HandorTakeOverPgTimeButton.setText(dateInString)

            HandOrTakeOverPgInnerContainerConstraintLayout.setOnClickListener{
                hideKeyboard()
            }
            HandOrTakeOverPgContainerConstraintLayout.setOnClickListener{
                hideKeyboard()
            }

            SubmissionPgPlateNoEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    binding.validPlatNo = !binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()
                }
            })
            binding.SubmissionPgPlateNoEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
//                    showValidationDialog("Plate Number")
                    printMsg("IME_ACTION_DONE")

                    binding.validPlatNo = !binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()

                    //connect the data with binding
//                    binding.validPlatNo = binding.validPlatNo != true
//                    binding.validEmirates = binding.validEmirates != true
//                    binding.validEmirates = false
//                    binding.validPlateCode = false
//                    binding.validDriver = false

                    true
                }
                else
                {
                    printMsg("dfrwerqqewqrqwerqwer")
                    false

                }
            }
            binding.SubmissionPgIDNoEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
                    printMsg("IME_ACTION_DONE")
                    binding.validDriver = !binding.SubmissionPgIDNoEditText.text.isNullOrEmpty()
                    true
                }
                else
                {
                    false

                }
            }

            HandOrTakeOverPgReviewButton.setOnClickListener{
                hideKeyboard()
                showReview()
            }

        }
    }

    private val onItemEmiratesClick: (EmiratesModel) -> Unit = { tsk ->
        Log.i(TAG, "this is task $tsk")
//        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment()
//        view?.findNavController()?.navigate(action)
//        binding.validPlatNo = true
        binding.validEmirates = true
        binding.validPlateCode = false
        binding.validDriver = false

    }

    private val onItemCodeClick: (PlateCodeModel) -> Unit = { tsk ->
        Log.i(TAG, "this is task $tsk")

        binding.validPlateCode = true
        binding.validDriver = false
    }

    private fun showReview() {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater.inflate(R.layout.dialog_review_takehandover, null)
        builder.setView(inflater)
        builder.setCancelable(false)
        val dialogImagecancelbtn = inflater.findViewById<Button>(R.id.SignCancel_button)
        val dialog: AlertDialog = builder.create()
        dialogImagecancelbtn?.setOnClickListener {
            dialog.cancel()
        }


        val reviewHeading = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewPlatNo = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_PlateCode_TextView2)
        val reviewEmirates = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Emirates_TextView2)
        val reviewPlateCode = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_PlateCode_TextView2)
        val reviewKM= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewFuelTank= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewDate= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewTime= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewDriverID= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewDriverName= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val reviewContactNo= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)


        reviewHeading.text = heading!!
        reviewPlatNo.text = heading!!
        reviewEmirates.text = heading!!
        reviewPlateCode.text = heading!!
        reviewKM.text = heading!!
        reviewFuelTank.text = heading!!
        reviewDate.text = heading!!
        reviewTime.text = heading!!
        reviewDriverID.text = heading!!
        reviewDriverName.text = heading!!
        reviewContactNo.text = heading!!


        dialog.show()
    }

    private fun openDistributionPage() {
        val action = HandOrTakeOverFragmentDirections.actionHandOrTakeOverFragmentToDistributionFragment(heading!!)
        view?.findNavController()?.navigate(action)
    }


    fun DDate3() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        MaterialDatePicker.Builder.dateRangePicker().setSelection(
            Pair(   MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()
            ))

//        MaterialDatePicker.Builder().datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)

        datePicker.show(getParentFragmentManager(),"dsfs")
    }

    fun DDate1(){
            binding.HandorTakeOverPgDateButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater.inflate(R.layout.dialog_calendar1, null)
            builder.setView(inflater)
            val dialog: AlertDialog = builder.create()
            val calendarvw = inflater.findViewById<View>(R.id.customCalendarView)

//            calendar.setOnDayClickListener(OnDayClickListener { eventDay ->
//                val clickedDayCalendar = eventDay.calendar
//            })
//            dialog.getWindow()?.setLayout(30, 40)
//            dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            dialog.show()
        }
    }

    fun DDate2()
    {
        binding.HandorTakeOverPgDateButton.setOnClickListener {
            binding.HandorTakeOverPgDateButton.showSoftInputOnFocus = false
            val cal = Calendar.getInstance()
            val today = Date()
            cal.time = today
            val y = (cal.get(Calendar.YEAR))
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            var datePickerThemeResId = 4
            val datepickerdialog: DatePickerDialog =
                DatePickerDialog(
                  requireContext(),
                    datePickerThemeResId,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        var mnth = monthOfYear + 1
                        var monthOf_Year = (monthOfYear + 1).toString()
                        if (monthOf_Year.length < 2) {
                            monthOf_Year = "0" + monthOf_Year
                        }
                        // Display Selected date in textbox
                        val dtString = "" + dayOfMonth + "-" + monthOf_Year + "-" + year
                        binding.HandorTakeOverPgDateButton.text  = dtString

                        val sdf = SimpleDateFormat("dd-M-yyyy")
                        dateofBirth = sdf.parse(dtString)
                    },
                    y,
                    m,
                    d
                )
            val minCal = Calendar.getInstance()
            minCal.time = today
            minCal.add(Calendar.YEAR, -1)

            val maxCal = Calendar.getInstance()
            maxCal.time = today
            maxCal.add(Calendar.YEAR, 0)

            datepickerdialog.datePicker.minDate = minCal.timeInMillis
            datepickerdialog.datePicker.maxDate = maxCal.timeInMillis

            datepickerdialog.show()
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

fun printMsg(message:String)
{
    Log.i("TAG",message)
}

private fun showValidationDialog(message: String){
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(resources.getString(R.stri))
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(message)
        .setMessage(message)
        .setNeutralButton("Cancel"){ dialog, _ ->
            dialog.cancel()
        }
        .show()
}

}