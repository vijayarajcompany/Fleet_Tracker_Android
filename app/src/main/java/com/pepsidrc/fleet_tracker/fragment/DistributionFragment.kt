package com.pepsidrc.fleet_tracker.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.MainActivity
import com.pepsidrc.fleet_tracker.adapter.LicenseAdapter
import com.pepsidrc.fleet_tracker.adapter.vehiclepartsAdapter
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
import com.pepsidrc.fleet_tracker.databinding.FragmentDistributionBinding
import com.pepsidrc.fleet_tracker.model.EmiratesModel
import com.pepsidrc.fleet_tracker.model.LicenseModel
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
import com.pepsidrc.fleet_tracker.viewModel.DistributionViewModel
import java.util.*
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources

import android.util.DisplayMetrics
import android.widget.Toast

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.InputType
//import android.R

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Layout
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ApplicationProvider

import androidx.test.core.app.ApplicationProvider.getApplicationContext

private const val TAG = "DistributionFragment"
class DistributionFragment : Fragment() {

//    companion object {
//        fun newInstance() = DistributionFragment()
//    }
    private lateinit var binding: FragmentDistributionBinding
    private lateinit var viewModel: DistributionViewModel
    private val args:DistributionFragmentArgs by navArgs()

//    var langStatus = View.TEXT_DIRECTION_LTR

    private var heading:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDistributionBinding.inflate(inflater, container, false)
        val view = binding.root
        heading = args.heading

        setup()
        binding.DistributionPgContinueButton.setOnClickListener{
            openSubmissionPage()
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DistributionViewModel::class.java)
        // TODO: Use the ViewModel
//        val img = ResourcesCompat.getDrawable(resources,R.drawable.home_icon,this.context?.theme)
//        binding.DistributionPgVehicleImageImageView.load(img)

        val _parts: List<VehiclePartsModel> = listOf(
            VehiclePartsModel(33, "Spare Tire"),
            VehiclePartsModel(33, "Triangle"),
            VehiclePartsModel(33, "Jack"),
            VehiclePartsModel(33, "Jack Rod"),
            VehiclePartsModel(33, "Spare Tire Handle"),
            VehiclePartsModel(33, "Fire Extinguisher"),
            VehiclePartsModel(33, "First Aid Box"),
            VehiclePartsModel(33, "Fuel Card"),
            VehiclePartsModel(33, "Registration Card"),
            VehiclePartsModel(33, "Salik"),
            VehiclePartsModel(33, "Driver's License"),
            VehiclePartsModel(33, "DRC Logo"),
            VehiclePartsModel(33, "DRC Telephone No"),
            VehiclePartsModel(33, "Interior Cleanliness"),
            VehiclePartsModel(33, "Exterior Cleanliness"),
            VehiclePartsModel(33, "Reﬂective Sticker"),
            VehiclePartsModel(33, "Cigaratte Ligher"),
            VehiclePartsModel(33, "Seat Condition "),
            VehiclePartsModel(33, "Windscreen")
        )

        binding.DistributionPgVehiclePartsRecyclerView.adapter = activity?.let{
           vehiclepartsAdapter(_parts,it.applicationContext,onPartsItemCodeClick)
        }


        val _license: List<LicenseModel> = listOf(
            LicenseModel(33, "DXB"),
            LicenseModel(33, "SHJ"),
            LicenseModel(33, "AJM"),
            LicenseModel(33, "RAK"),
            LicenseModel(33, "UAQ")
        )

        binding.DistributionPgDistributionRecyclerView.adapter = activity?.let{
            LicenseAdapter(_license,it.applicationContext,onDistribItemCodeClick)
        }

    }

    private val onPartsItemCodeClick: (VehiclePartsModel) -> Unit = { tsk ->
        Log.i(TAG, "this is task $tsk")
//        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment()
//        view?.findNavController()?.navigate(action)
    }

    private val onDistribItemCodeClick: (LicenseModel) -> Unit = { tsk ->
        Log.i(TAG, "this is task $tsk")
//        val action = VehicleFragmentDirections.actionVehicleFragmentToHandOrTakeOverFragment()
//        view?.findNavController()?.navigate(action)
    }

    private fun openSubmissionPage(){
        val action = DistributionFragmentDirections.actionDistributionFragmentToSubmissionFragment(heading!!)
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
    }

}