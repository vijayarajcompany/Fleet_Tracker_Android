package com.pepsidrc.fleet_tracker.fragment


import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.EmiratesAdapter
import com.pepsidrc.fleet_tracker.adapter.FuelTankArrayAdapter
import com.pepsidrc.fleet_tracker.adapter.PlateCodeAdapter
import com.pepsidrc.fleet_tracker.common.Common
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
import com.pepsidrc.fleet_tracker.databinding.FragmentHandOrTakeOverBinding
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.FuelTankModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.repository.HandorTakeOverRepository
import com.pepsidrc.fleet_tracker.viewModel.HandOrTakeOverViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "HandOrTakeOverFragment"

enum class ButtonType {
    REVIEW, CONTINUE, NONE
}
class HandOrTakeOverFragment : Fragment() {

    //    companion object {
//        fun newInstance() = HandOrTakeOverFragment()
//    }
    private lateinit var binding: FragmentHandOrTakeOverBinding
    private lateinit var viewModel: HandOrTakeOverViewModel
    private val args:HandOrTakeOverFragmentArgs by navArgs()
    private var taskid:Int? = null
    private var subtaskid:Int? = null

//    private var vehiclename:String? = null

    private var vehicle:VehicleModel? = null
    private var heading:String? = null
    private lateinit var currentButtonType:ButtonType


    private var bounce: Animation? = null
    private var shake: Animation? = null
    private var regshake: Animation? = null
    private var Progress_dialog: Dialog? = null
    private lateinit var handorTakeOverRepository: HandorTakeOverRepository

    var fuelTankList:MutableList<FuelTankModel>? = ArrayList()
    var selectedTank:FuelTankModel? = null
    private var strplateNumber:String? = null
    private var stremirates:String? = null
    private var strSelectedPlatecode:String? = null
    private var strSelectedEmirates:String? = null
    private var strSelectedEmirates_id:Int? = null
    private var strKM:String? = null
    private var strFuelTank:String? = null
    private var strDriverID:String? = null
    private var strDriverName:String? = null
    private var strContactNo:String? = null
    private var strSelectedDate:String? = null
    private var strSelectedTime:String? = null
    private var lastValidPlateNoEntered:Int? = null

    var currDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Data Binding
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_hand_or_take_over,container,false)
        val view = binding.root
        taskid = args.taskid
        subtaskid = args.subtaskid
        heading = args.heading
        vehicle = args.vehicle
//      vehiclename = args.vehicle.name


        clearControls()

        binding.HandOrTakeOverPgContinueButton.setOnClickListener {
            hideKeyboard()

            var ErrorMsg = ""
            if(binding.SubmissionPgKMEditText.text.isNullOrEmpty())
            {
                ErrorMsg = "Please enter the KiloMeters"
            }
            else if( selectedTank?.id  == fuelTankList?.get(0)?.id ){
                ErrorMsg = "Please select the Fuel Tank"
            }

            if (ErrorMsg.isEmpty()){
                currentButtonType = ButtonType.CONTINUE
                getEmployeeForID()
            }
            else
            {
                ShowValidationErrorDialog(ErrorMsg)
                return@setOnClickListener
            }

        }
        setup()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handorTakeOverRepository = activity?.let { HandorTakeOverRepository(it.application,requireContext()) }!!
        val factory = HandOrTakeOverViewModel.Factory(handorTakeOverRepository) // Factory
        viewModel = ViewModelProvider(this, factory)[HandOrTakeOverViewModel::class.java] // ViewModel
        setupFuelTank()
        binding.SubmissionPgPlateNoEditText.setText("")
        binding.keyboardDonePressed = false
        modelObserver()
        clearControls()
    }

    fun setup() {

        currentButtonType = ButtonType.NONE
        Progress_dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(R.layout.progress_bar_large)

        bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        regshake = AnimationUtils.loadAnimation(requireContext(), R.anim.regshake)

        val date = getCurrentDateTime()
//      val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        val dateInString = date.toString("dd-MM-yyyy")
//        val TimeInString = date.toString("HH:mm")
        val TimeInString = date.toString("HH:mm aa")


        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText(heading!!)

        showCalender()
        showCalender()
        showTimer()

        with(binding){

            HandorTakeOverPgDateButton.text = dateInString
            HandorTakeOverPgTimeButton.text = TimeInString

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
//                    val isEmpty = !binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()
//                    binding.validPlatNo = isEmpty
//                    if (isEmpty){
//                        getEmiratesForPlateNo()
//                    }

                }
            })

            SubmissionPgPlateNoEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
//                  showValidationDialog("Plate Number")
                    printMsg("IME_ACTION_DONE")
                    validEmirates = false
                    keyboardDonePressed = true
                    getEmiratesForPlateNoFromDB(false,0)

//                    binding.validPlatNo = !binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()
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
            SubmissionPgIDNoEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
                    printMsg("IME_ACTION_DONE")
                    currentButtonType = ButtonType.NONE
                    getEmployeeForID()
                    true
                }
                else
                {
                    false
                }
            }

            SubmissionPgKMEditText.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
                    printMsg("IME_ACTION_DONE")

                    if(binding.SubmissionPgKMEditText.text.isNullOrEmpty())
                    {
                        ShowValidationErrorDialog("Please enter the KM")
                    }
                    else
                    {
                        getKilometerFromDB()
                    }

                    true
                }
                else
                {
                    false
                }
            }


            HandOrTakeOverPgReviewButton.setOnClickListener{
                hideKeyboard()

                val ErrorMsg  = validateKMWhileReview()

                if (ErrorMsg.isEmpty())
                {
                    binding.driverError = true
                    currentButtonType = ButtonType.REVIEW
                    getEmployeeForID()
                }
                else
                {
                    ShowValidationErrorDialog(ErrorMsg)
                }

            }

        }
    }

    fun validateKMWhileReview():String{

        var ErrorMsg = ""
        if(binding.SubmissionPgKMEditText.text.isNullOrEmpty())
        {
            ErrorMsg = "Please enter the KM"
        }
        else if( selectedTank?.id  == fuelTankList?.get(0)?.id ){
            ErrorMsg = "Please select the Fuel Tank"
        }

        return  ErrorMsg
    }

    fun SelectedFuelCard(fuelcard: FuelTankModel) {
        this.selectedTank = fuelcard
    }

    private val onItemEmiratesClick: (EmiratesModel) -> Unit = { emirate ->
        Log.i(TAG, "this is task $emirate")
//        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment()
//        view?.findNavController()?.navigate(action)
//        binding.validPlatNo = true
        binding.validEmirates = true
        binding.validPlateCode = false
//        binding.validPlateCode = true
        strSelectedEmirates = emirate.name
        strSelectedEmirates_id = emirate.id
        getEmiratesForPlateNoFromDB(true,emirate.id)

    }

    private val onItemCodeClick: (String) -> Unit = { code ->
        Log.i(TAG, "this is task $code")
        strSelectedPlatecode = code
        val date = getCurrentDateTime()
        val dateInString = date.toString("dd-MM-yyyy")
        val TimeInString = date.toString("HH:mm aa")
        binding.HandorTakeOverPgDateButton.text = dateInString
        binding.HandorTakeOverPgTimeButton.text = TimeInString

        binding.validPlateCode = true
        binding.SubmissionPgIDNoEditText.setText("")
        binding.SubmissionPgKMEditText.setText("")
        binding.SubmissionPgFuelTankSpinner.setSelection(0)
        binding.validDriver = false

        getEmiratesForPlateNoFromDB(true,0)

    }

//    private val onItemCodeClick: (PlateCodeModel) -> Unit = { tsk ->
//        Log.i(TAG, "this is task $tsk")
//
//        binding.validPlateCode = true
//        binding.validDriver = false
//    }


    private fun clearControls()
    {
        with(binding){
            if(!fuelTankList.isNullOrEmpty()){
                SubmissionPgFuelTankSpinner.setSelection(0)
            }
            SubmissionPgPlateNoEditText.setText("")
            SubmissionPgKMEditText.setText("")
            SubmissionPgIDNoEditText.setText("")
        }

        binding.keyboardDonePressed = false
        binding.plateNoError      = false
        binding.validPlatNo       = false
        binding.validEmirates     = false
        binding.validPlateCode    = false
        binding.driverError       = false

    }



    private fun openDistributionPage(emirateid: Int) {
        currentButtonType = ButtonType.NONE
        clearControls()
        val action = HandOrTakeOverFragmentDirections.actionHandOrTakeOverFragmentToDistributionFragment(heading!!,vehicle!!,emirateid!!)
        view?.findNavController()?.navigate(action)
    }

fun printMsg(message:String)
{
    Log.i("TAG",message)
}

//private fun showValidationDialog(message: String){
////    MaterialAlertDialogBuilder(requireContext())
////        .setTitle(resources.getString(R.stri))
//    MaterialAlertDialogBuilder(requireContext())
//        .setTitle(message)
//        .setMessage(message)
//        .setNeutralButton("Cancel"){ dialog, _ ->
//            dialog.cancel()
//        }
//        .show()
//}

    //BUISNESS LOGIC
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    //OBSERVER
    private fun modelObserver() {

        viewModel.ValidPlateNo?.observe(viewLifecycleOwner) { plateno ->
                lastValidPlateNoEntered = plateno
        }

        viewModel.plateCode?.observe(viewLifecycleOwner) { plateCode ->
            if(!plateCode.isNullOrEmpty()){
                binding.HandorTakeOverPgCodeRecyclerView.adapter = activity?.let { PlateCodeAdapter(plateCode, it.applicationContext, onItemCodeClick) }
            }
        }

        viewModel.KM?.observe(viewLifecycleOwner) { kilometer ->
                kilometer?.let {
                    val userEnteredKM = binding.SubmissionPgKMEditText.text.toString().toInt()
                    if(userEnteredKM <= kilometer){
                        ShowValidationErrorDialog("Kilometer should be greater than previous entry $kilometer")
                    }
                }
        }


        viewModel.emirates?.observe(viewLifecycleOwner) { emirates ->

            val iskeypress = binding.keyboardDonePressed!!
            if(!emirates.isNullOrEmpty() && (iskeypress)){

                binding.keyboardDonePressed = false
                binding.plateNoError = false
                binding.validPlatNo = true

                binding.HandorTakeOverPgEmiratesRecyclerView.adapter =
                    activity?.let { EmiratesAdapter(emirates, it.applicationContext, onItemEmiratesClick) }
            }
            else
            {
                binding.plateNoError = true
                binding.validEmirates = false
                binding.validPlatNo = false
                binding.HandOrTakeOverPgPlateNoErrorTextView.startAnimation(regshake)
            }
        }

        viewModel.employee_details?.observe(viewLifecycleOwner) { empdetails ->

            if (empdetails != null) {
                binding.driverName = empdetails.name
                binding.driverContact = empdetails.contactnumber.toString()
                with(binding) {
                    validDriver = true
                    driverError = false
                }

                when (currentButtonType) {
                    ButtonType.CONTINUE -> openDistributionPage(empdetails.emiratesid)
                    ButtonType.REVIEW -> showReview()
                    ButtonType.NONE -> ""
                }

            }
            else{
                with(binding) {
                    validDriver = false
                    driverError = true
                }

                binding.handOrTakeOverPgDriverNoErrorTextView.startAnimation(regshake)
            }
        }

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
                    binding.plateNoError = true
                    Progress_dialog!!.hide()
                }
            }
        }

        viewModel.errorEmpMessage.observe(viewLifecycleOwner) { errors ->
            if (errors != null) {
                if (errors.isNotEmpty()) {
                    Progress_dialog!!.hide()
                    with(binding) {
                        validDriver = false
                        driverError = true
                        handOrTakeOverPgDriverNoErrorTextView.startAnimation(regshake)
                    }
                }
            }
        }

    }


    private fun showInputMethodPicker() {
        val imeManager: InputMethodManager = getApplicationContext<Context>().getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (imeManager != null) {
            imeManager.showInputMethodPicker()
        } else {
//            Toast.makeText((), "not possible", Toast.LENGTH_LONG).show()
            val test = 894357

        }
    }



    //DIALOG
    private fun showReview() {
        currentButtonType = ButtonType.NONE
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
        val reviewPlatNo = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_PlateNo_TextView2)
        val reviewEmirates = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Emirates_TextView2)
        val reviewPlateCode = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_PlateCode_TextView2)
        val reviewKM= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_KM_TextView2)
        val reviewFuelTank= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_FuelTank_TextView2)
        val reviewDate= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Date_TextView2)
        val reviewTime= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Time_TextView2)
        val reviewDriverID= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_IDNo_TextView2)
        val reviewDriverName= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_DriverName_TextView2)
        val reviewContactNo= inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_ContactNo_TextView2)


//        private var strplateNumber:String? = null
//        private var stremirates:String? = null
//        private var strPlatecode:String? = null
//        private var strKM:String? = null
//        private var strFuelTank:String? = null
//        private var strDriverID:String? = null
//        private var strDriverName:String? = null
//        private var strContactNo:String? = null


        with(binding){

            strplateNumber = SubmissionPgPlateNoEditText.text.toString()
            strKM = SubmissionPgKMEditText.text.toString()
            strDriverID = SubmissionPgIDNoEditText.text.toString()

            strSelectedDate = HandorTakeOverPgDateButton.text.toString()
            strSelectedTime = HandorTakeOverPgTimeButton.text.toString()

            strDriverName = SubmissionPgDriverNameEditText.text.toString()
            strContactNo = SubmissionPgContactNoEditText.text.toString()

        }

        reviewHeading.text = heading!!
        reviewPlatNo.text = strplateNumber!!
        reviewKM.text = strKM!!
        reviewFuelTank.text = selectedTank?.name
        reviewDate.text = strSelectedDate
        reviewTime.text = strSelectedTime
        reviewDriverID.text = strDriverID!!

        reviewDriverName.text = strDriverName!!
        reviewContactNo.text = strContactNo!!
        reviewEmirates.text = strSelectedEmirates!!
        reviewPlateCode.text = strSelectedPlatecode!!

//        inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView).setText("hello vijayarajjjjjjjjjjj")

        dialog.show()
    }

    private fun ShowValidationErrorDialog(ErrorMsg:String) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater.inflate(R.layout.dialog_validate_hand_or_take_over, null)
        builder.setView(inflater)
        builder.setCancelable(false)

        val dialog: AlertDialog = builder.create()
        val dialogokbtn = inflater!!.findViewById<Button>(R.id.Ok_button)
        val dialogcancelbtn = inflater!!.findViewById<Button>(R.id.ErrorCancel_button)
        val dialogoErrorTxt = inflater!!.findViewById<TextView>(R.id.Errortxt)

        dialogoErrorTxt.text = ErrorMsg

        dialogokbtn?.setOnClickListener {
            dialog.cancel()
        }

        dialogcancelbtn?.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    fun showCalender()
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
                        currDate = sdf.parse(dtString)
                    },
                    y,
                    m,
                    d
                )
            val minCal = Calendar.getInstance()
            minCal.time = today
            minCal.add(Calendar.YEAR, -1)
//            minCal.add(Calendar.MONTH, 1)

            val maxCal = Calendar.getInstance()
            maxCal.time = today
            maxCal.add(Calendar.YEAR, 1)
//            maxCal.add(Calendar.MONTH, 2)

            datepickerdialog.datePicker.minDate = minCal.timeInMillis
            datepickerdialog.datePicker.maxDate = maxCal.timeInMillis

            datepickerdialog.show()
        }
    }

    private fun showTimer()
    {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                val AM_PM: String
                if (hourOfDay < 12) {
                    AM_PM = "AM"
                } else {
                    AM_PM = "PM"
                }

                binding.HandorTakeOverPgTimeButton.text = String.format("%d : %d %s", hourOfDay, minute,AM_PM)

            }
        }, hour, minute, false)


        binding.HandorTakeOverPgTimeButton.setOnClickListener {
            mTimePicker.show()
        }


    }

    //DATA
    private fun setupFuelTank(){

        fuelTankList?.clear()
        fuelTankList?.add(FuelTankModel("Fuel Tank","Half filled"))
        fuelTankList?.add(FuelTankModel("1","Empty"))
        fuelTankList?.add(FuelTankModel("2","Near Empty"))
        fuelTankList?.add(FuelTankModel("3","Near Half Tank"))
        fuelTankList?.add(FuelTankModel("4","Half Tank"))
        fuelTankList?.add(FuelTankModel("5","Above Half filled"))
        fuelTankList?.add(FuelTankModel("6","Near Full Tank"))
        fuelTankList?.add(FuelTankModel("7","Full Tank"))
//        fuelCardList?.add(FuelCardModel("8","Full Tank"))
        this.selectedTank =  fuelTankList?.get(0)

        val FuelTankAdapter = FuelTankArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            fuelTankList!!
        )

//      FuelAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.SubmissionPgFuelTankSpinner.adapter = FuelTankAdapter
        binding.SubmissionPgFuelTankSpinner.setSelection(0)
        binding.SubmissionPgFuelTankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing Selected")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                SelectedFuelCard(fuelTankList!![position])
            }
        }
//        val languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")
//        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.SubmissionPgFuelTankSpinner.adapter = aa

    }

    private fun setupEmiratesPlateCode()
    {

//        val tsk1 = EmiratesModel(33, "Abu Dhabi","Sharjah")
//        val tsk2 = EmiratesModel(33, "Ajman","Sharjah")
//        val tsk3 = EmiratesModel(33, "Dubai","Sharjah")
//        val tsk4 = EmiratesModel(33,"Sharjah","Sharjah")
//        val _emirates: List<EmiratesModel> = listOf(tsk1, tsk2, tsk3, tsk4)




//        binding.HandorTakeOverPgEmiratesRecyclerView.adapter =
//            activity?.let { EmiratesAdapter(_emirates, it.applicationContext, onItemEmiratesClick) }


//        val tsk11 = PlateCodeModel(33, "L")
//        val tsk21 = PlateCodeModel(33, "H")
//        val tsk31 = PlateCodeModel(33, "B")
//        val tsk41 = PlateCodeModel(33, "C")
//        val _code: List<PlateCodeModel> = listOf(tsk11, tsk21, tsk31, tsk41)
//        binding.HandorTakeOverPgCodeRecyclerView.adapter = activity?.let { PlateCodeAdapter(_code, it.applicationContext, onItemCodeClick) }


    }

    //DATABASE CALLS

    private fun getKilometerFromDB()
    {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {

            val plateno = binding.SubmissionPgPlateNoEditText.text.toString().toInt()
            val emirateid = strSelectedEmirates_id!!.toInt()
            val platecode = strSelectedPlatecode.toString()

            viewModel.getKmFromDB(plateno,emirateid,platecode)

        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getEmployeeForID()
    {
        val isNotEmpty = !binding.SubmissionPgIDNoEditText.text.isNullOrEmpty()
        binding.validDriver = isNotEmpty
        if(isNotEmpty){
            binding.driverError = false
            getEmployeeFromDB( binding.SubmissionPgIDNoEditText.text.toString().trim().toInt())
        }
        else
        {
            binding.handOrTakeOverPgDriverNoErrorTextView.startAnimation(regshake)
            binding.driverError = true
        }
    }




    private fun getEmiratesForPlateNoFromDB(isRecycleClicked:Boolean, emirateid: Int)
    {
        binding.plateNoError      = false
        binding.validPlatNo       = false
        val isEmpty = binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()
        binding.validPlatNo = !isEmpty

        if (!isEmpty){
            //binding.validPlatNo = false
            val plateno = binding.SubmissionPgPlateNoEditText.text.toString().trim().toInt()
            if(isRecycleClicked && lastValidPlateNoEntered == plateno){
//                  HideFromPlateCodeControl()
                    binding.keyboardDonePressed = true
                    getPlateCodeForPlateNo_EmirateFromDB(plateno,emirateid)
            }
            else if(isRecycleClicked && lastValidPlateNoEntered != plateno){
                binding.keyboardDonePressed = true
                getEmiratesFromDB(plateno)            }
            else{
//              HideFromEmiratesControl()
                getEmiratesFromDB(plateno)
            }
        }
        else
        {
            binding.plateNoError = true
            binding.HandOrTakeOverPgPlateNoErrorTextView.startAnimation(regshake)
        }




    }

    private fun getPlateCodeForPlateNo_EmirateFromDB(plateno:Int,emirateid:Int)
    {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.getPlateCodeForPlateNo_EmirateFromDB(plateno,emirateid)
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }
//        binding.plateNoError = false
//        val isEmpty = binding.SubmissionPgPlateNoEditText.text.isNullOrEmpty()
//        binding.validPlatNo = !isEmpty
//
//        if (!isEmpty){
//            binding.validPlatNo = false
//            getEmiratesFromDB(binding.SubmissionPgPlateNoEditText.text.toString().trim().toInt())
//        }
//        else
//        {
//
//            binding.plateNoError = true
//            binding.HandOrTakeOverPgPlateNoErrorTextView.startAnimation(regshake)
//        }






//    private fun  getKMFromDB(empID:Int) {
//        val connect = Common.checkConnectivity(requireContext())
//        if (connect) {
//            viewModel.getEmployeeFromDB(empID)
//        } else {
//            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
//                .show()
//        }
//    }

    private fun  getEmployeeFromDB(empID:Int) {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.getEmployeeFromDB(empID)
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun  getEmiratesFromDB(PlateNo:Int) {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
            viewModel.getEmiratesFromDB(PlateNo)
        } else {
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }

//    private fun  getEmployeeFromDB(empID:Int) {
//        val connect = Common.checkConnectivity(requireContext())
//        if (connect) {
//            viewModel.getEmployeeFromDB(empID)
//        } else {
//            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
//                .show()
//        }
//    }





}