package com.pepsidrc.fleet_tracker.fragment

//import android.R

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.test.core.app.ApplicationProvider
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.LicenseAdapter
import com.pepsidrc.fleet_tracker.adapter.vehiclepartsAdapter
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
import com.pepsidrc.fleet_tracker.data.LicenseTbl
import com.pepsidrc.fleet_tracker.databinding.FragmentDistributionBinding
import com.pepsidrc.fleet_tracker.model.LicenseModel
import com.pepsidrc.fleet_tracker.model.VehicleModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
import com.pepsidrc.fleet_tracker.repository.VehicleRepository
import com.pepsidrc.fleet_tracker.viewModel.DistributionViewModel
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "DistributionFragment"
class DistributionFragment : Fragment() {

//    companion object {
//        fun newInstance() = DistributionFragment()
//    }
    private lateinit var binding: FragmentDistributionBinding
    private lateinit var viewModel: DistributionViewModel
    private lateinit var vehicleRepository: VehicleRepository
    private val args:DistributionFragmentArgs by navArgs()
    var arrselectedParts: MutableList<VehiclePartsModel> = mutableListOf()
    var arrselectedLicense: MutableList<LicenseModel> = mutableListOf()
//    var langStatus = View.TEXT_DIRECTION_LTR

    private var heading:String? = null
    private var vehicle:VehicleModel? = null
    private var vehiclePart:List<Int>? = null
//    private var vehicleDetails:VehicleModel? = null
    private var emiratesId:Int? = null
    private var distributionLicense:List<Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDistributionBinding.inflate(inflater, container, false)
        val view = binding.root
        heading = args.heading
        vehiclePart = args.vehicle.part
        emiratesId = args.emirateid


        setup()
        binding.DistributionPgContinueButton.setOnClickListener{
            openSubmissionPage()
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        vehicleRepository = activity?.let { VehicleRepository(it.application,requireContext()) }!!
        val factory = DistributionViewModel.Factory(vehicleRepository) // Factory
        viewModel = ViewModelProvider(this, factory)[DistributionViewModel::class.java] // ViewModel

        setupParts()

        viewModel.getDistributionLicenseFromWebApi()

        viewModel.License?.observe(viewLifecycleOwner) { license ->

                        if(!license.isNullOrEmpty()){



                            setupLicense()
                        }
//            if(!license.isNullOrEmpty()){
//                        binding.DistributionPgDistributionRecyclerView.adapter = activity?.let{
//            LicenseAdapter(license,it.applicationContext,onDistribItemCodeClick)
//                }
//            }
        }

    }

    private val onPartsItemCodeClick: (VehiclePartsModel) -> Unit = { parts ->
        if(parts.selected)
        {
            arrselectedParts.add(parts)
        }
        else
        {
            arrselectedParts.remove(parts)
        }

    }

    private val onDistribItemCodeClick: (LicenseModel) -> Unit = { license ->
//        Log.i(TAG, "this is task $license")
//        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment()
//        view?.findNavController()?.navigate(action)
        if(license.selected)
        {
            arrselectedLicense.add(license)
        }
        else
        {
            arrselectedLicense.remove(license)
        }

    }

    private fun setupParts()
    {
        lifecycleScope.launch {
            val _parts = vehiclePart?.let { viewModel.getVehiclePartsFromDB(it) }


            if(!_parts.isNullOrEmpty())
            {
                arrselectedParts = _parts.toMutableList()
                binding.DistributionPgVehiclePartsRecyclerView.adapter = activity?.let{
                    vehiclepartsAdapter(_parts!!,it.applicationContext, onPartsItemCodeClick)
                }
            }
        }
    }

    private fun setupLicense()
    {
        lifecycleScope.launch {
//            val _license = distributionLicense?.let { viewModel.getDistributionLicenseFromDB() }
//            val _license = viewModel.getDistributionLicenseFromDB()
            val _license = viewModel.getDistributionLicenseFromDB()

            if(!_license.isNullOrEmpty())
            {
                arrselectedLicense = _license.toMutableList()
                binding.DistributionPgDistributionRecyclerView.adapter = activity?.let{
                    LicenseAdapter(_license,it.applicationContext,onDistribItemCodeClick)
                }
            }
        }
    }





    private fun openSubmissionPage(){
        val action = DistributionFragmentDirections.actionDistributionFragmentToSubmissionFragment(heading!!,emiratesId!!)
        view?.findNavController()?.navigate(action)
    }

    fun setup() {
        (activity as MainActivity).setHardwareBackPressedStatus(true)
        (activity as MainActivity).ChangeToolBarText(heading!!)
        binding.DistributionPgVehicleImageImageView.load(R.drawable.van_large)
        binding.DistributionPgReviewButton.setOnClickListener{hideKeyboard()
            showReview()
        }
        binding.DistributionPgVehicleImageImageView.setOnClickListener{
            hideKeyboard()
        }

//        binding.DistributionPgVehiclePartsRecyclerView.setOnClickListener{hideKeyboard()}
//        binding.DistributionPgDistributionRecyclerView.setOnClickListener{hideKeyboard()}


        with(binding) {
//            setLocale("fr")
//            var lll = Locale.JAPAN
//            DistributionPgCommentsInfoTextView.textLocale = lll
//            DistributionPgCommentsInfoTextView.imeHintLocales = LocaleList(Locale("zh", "CN"))
            DistributionPgContainerConstraintLayout.setOnClickListener {
                hideKeyboard()
            }

            DistributionPgEnglishButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground_clicked)
            DistributionPgArabicButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground)

            DistributionPgEnglishButton.setOnClickListener {
                langStatus  = View.TEXT_DIRECTION_LTR
//                DistributionPgCommentsInfoTextView.textDirection = View.TEXT_DIRECTION_LTR
                DistributionPgEnglishButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground_clicked)
                DistributionPgArabicButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground)
            }

            DistributionPgArabicButton.setOnClickListener {
                langStatus = View.TEXT_DIRECTION_RTL
//                DistributionPgCommentsInfoTextView.textDirection = View.TEXT_DIRECTION_RTL
//                DistributionPgCommentsInfoTextView.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                DistributionPgArabicButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground_clicked)
                DistributionPgEnglishButton.setBackgroundResource(R.color.colorPepsi_ButtonBackground)
                DistributionPgCommentsEditText.setImeHintLocales(LocaleList(Locale("zh", "CN")))
            }
        }
    }

//    fun setLocale(lang: String?) {
//        var myLocale = Locale(lang)
//        val res: Resources = resources
//        val dm: DisplayMetrics = res.getDisplayMetrics()
//        val conf: Configuration = res.getConfiguration()
//        conf.locale = myLocale
//        res.updateConfiguration(conf, dm)
//
////        val refresh = Intent(this, AndroidLocalize::class.java)
////        startActivity(refresh)
//    }

    private fun showInputMethodPicker() {
        val imeManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imeManager.showInputMethodPicker()
    }

    fun showReview() {


        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater.inflate(R.layout.dialog_review_distribution, null)
        builder.setView(inflater)
        builder.setCancelable(false)
        val dialogImagecancelbtn = inflater.findViewById<Button>(R.id.SignCancel_button)
        val dialog: AlertDialog = builder.create()
        dialogImagecancelbtn?.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
        val reviewHeading = inflater!!.findViewById<TextView>(R.id.HandOrTakeOverPg_Review_Heading_TextView)
        val comments = inflater!!.findViewById<TextView>(R.id.DistributionPg_Comments_TextView2)
        val TxtParts = inflater!!.findViewById<TextView>(R.id.DistributionPg_VehicleParts_TextView2)
        val TxtLicense = inflater!!.findViewById<TextView>(R.id.DistributionPg_License_TextView2)


        val parts:Set<VehiclePartsModel>
        var strSelectedParts:String = ""
        TxtParts.text = strSelectedParts
        if(!arrselectedParts.isNullOrEmpty())
        {

            parts = arrselectedParts.toSet()
            parts.forEach {
                strSelectedParts += it.name
                strSelectedParts += ", "
            }

            strSelectedParts = strSelectedParts.substring(0, strSelectedParts.lastIndexOf(","))
            TxtParts.text = strSelectedParts
        }
        else
        {
            TxtParts.text = "No Parts Selected"
        }


        val license:Set<LicenseModel>
        var strSelectedLicense:String = ""
        TxtLicense.text = strSelectedLicense
        if(!arrselectedLicense.isNullOrEmpty())
        {
            license = arrselectedLicense.toSet()
            license.forEach {
                strSelectedLicense += it.name
                strSelectedLicense += ", "

            }

            strSelectedLicense = strSelectedLicense.substring(0, strSelectedLicense.lastIndexOf(","))
            TxtLicense.text = strSelectedLicense
        }
        else
        {
            TxtLicense.text = "No License Selected"
        }



        reviewHeading.text = heading!!
//        TxtParts.text = strSelectedParts
        if(!binding.DistributionPgCommentsEditText.text.isNullOrEmpty())
        {
            comments.text = binding.DistributionPgCommentsEditText.text
        }
        else
        {
            comments.text = "No Comments"
        }




    }

}