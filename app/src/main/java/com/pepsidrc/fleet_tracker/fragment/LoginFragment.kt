package com.pepsidrc.fleet_tracker.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.activity.LoginActivity
import com.pepsidrc.fleet_tracker.common.Common
import com.pepsidrc.fleet_tracker.common.Utility.Companion.hideKeyboard
import com.pepsidrc.fleet_tracker.data.DataStoreManager
import com.pepsidrc.fleet_tracker.databinding.FragmentLoginBinding
import com.pepsidrc.fleet_tracker.viewModel.LoginViewModel
import com.pepsidrc.fleet_tracker.model.UserModel
import com.pepsidrc.fleet_tracker.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


import androidx.lifecycle.lifecycleScope

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

 //    companion object {
//        fun newInstance() = LoginFragment()
//    }
    private var bounce: Animation? = null
    private var shake: Animation? = null
    private var regshake: Animation? = null
    var regshake_cycle: Animation? = null
    var shake_cycle: Animation? = null
    private var Progress_dialog: Dialog? = null
    private val emptyString = ""

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var userRepository: UserRepository

//    private val blogPosts = mutableListOf<Post>()

//    Lifecycle 1st call
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        Setup_UI()
        return view
    }

    //    Lifecycle 2nd call
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userRepository = activity?.let { UserRepository(it.application,requireContext()) }!!
//        userRepository = UserRepository(Application(),requireContext())
        val factory = LoginViewModel.Factory(userRepository) // Factory
//        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java) // ViewModel
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java] // ViewModel

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { Loading ->
            if (Loading) {
                Progress_dialog!!.show()
            } else {
                Progress_dialog!!.hide()
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errors ->
            if (errors != null) {
                if (errors.isNotEmpty()) {
                    Progress_dialog!!.hide()
                }
            }
        })

//        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
//        viewModel = ViewModelProvider(this,userRepository)[LoginViewModel::class.java]


//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//        viewModel = ViewModelProvider((activity as LoginActivity))[LoginViewModel::class.java]
//        var ll = requireActivity()
//        var tttt = (activity as LoginActivity)
//        viewModel = ViewModelProvider(this,requireActivity())[LoginViewModel::class.java]


        Common.isInternetAvailable.observe(viewLifecycleOwner, Observer { available ->
            if (!available) {
                Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                    .show()
            }
        })

        Setup_Buisness()
    }

    private fun GetLoginDetails() {
        val connect = Common.checkConnectivity(requireContext())
        if (connect) {
//            Progress_dialog!!.show()
            viewModel.GetLoginDetails()

//            viewModel.login_details.observe(viewLifecycleOwner, Observer { logindetails ->
//                val sizzze = logindetails.size
//                Log.i(
//                    TAG, "Number of posts: ${logindetails.size}"
//                )
//            })
        } else {
            binding.LgPgErrorLabel.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), "There is no internet connection", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun Setup_Buisness() {
        dataStoreManager = DataStoreManager(requireContext())

        with(binding){
            LgPgRememberCheckBox.setOnClickListener{
                hideKeyboard()
                if(LgPgRememberCheckBox.isChecked){
                    //save credentials
                    if (basicValidation()) {

                        val usr = UserModel(
                            LgPgUsernameEditText.text.toString(),
                            LgPgPasswordEditText.text.toString()
                        )

                        lifecycleScope.launch {
                            dataStoreManager.saveCredentialstoDataStore(usr)
                        }
                    } else {
                        LgPgRememberCheckBox.isChecked = false
                    }
                }
                else {
                    //clear
                    lifecycleScope.launch {
                        val usr = UserModel( "", "")
                        dataStoreManager.saveCredentialstoDataStore(usr)
                    }
                }
            }

            lifecycleScope.launch {
                dataStoreManager.username.collect { username ->
                    if (username.isEmpty()){
                        LgPgRememberCheckBox.isChecked = false
                        LgPgUsernameEditText.setText(emptyString)
                    }
                    else
                    {
                        LgPgRememberCheckBox.isChecked = true
                        LgPgUsernameEditText.setText(username)
                    }

                }
            }

            lifecycleScope.launch {
                dataStoreManager.password.collect { password ->
                    if (password.isEmpty()){
                        LgPgRememberCheckBox.isChecked = false
                        LgPgPasswordEditText.setText(emptyString)
                    }
                    else
                    {
                        LgPgRememberCheckBox.isChecked = true
                        LgPgPasswordEditText.setText(password)
                    }
                }
            }

            if(LgPgRememberCheckBox.isChecked){
                lifecycleScope.launch {
                    dataStoreManager.username.collect { username ->
                        LgPgUsernameEditText.setText(username)
                    }
                }
                lifecycleScope.launch {
                    dataStoreManager.password.collect { password ->
                        LgPgPasswordEditText.setText(password)
                    }
                }
            }
        }



//      Login button click
        with(binding) {
            LgPgSigninButton.setOnClickListener {
                LgPgSigninButton.startAnimation(bounce)
                lifecycleScope.launch {
                    if (basicValidation()){
                        GetLoginDetails()
                        if (credentialsValidation()){
                            binding.LgPgErrorLabel.visibility = View.INVISIBLE
                            OpenMainActivity()
                        }
                    }
                }
            }
            LgPgContainerConstraintLayout.setOnClickListener {
                hideKeyboard()
            }
//            LgPgRememberCheckBox.setOnClickListener {
//                hideKeyboard()
//            }
        }// Login button click Ending here

        binding.LgPgErrorLabel.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), "Please Login", Toast.LENGTH_SHORT).show()
    }

    private fun Setup_UI() {

        Progress_dialog = Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        Progress_dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Progress_dialog!!.setCancelable(false)
        Progress_dialog!!.setContentView(R.layout.progress_bar_large)

        bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        regshake = AnimationUtils.loadAnimation(requireContext(), R.anim.regshake)

    }

    fun OpenMainActivity() {
//      val Prefusername = DataStoreManager(requireContext()).username
//        Progress_dialog!!.show()
//        Progress_dialog!!.dismiss()
        hideKeyboard()
        binding.LgPgSigninButton.clearAnimation()
        (activity as LoginActivity).OpenMainActivity()
    }

    private suspend fun credentialsValidation(): Boolean {
        with(binding) {
            val username = LgPgUsernameEditText.text.toString().trim()
            val password = LgPgPasswordEditText.text.toString().trim()
            //DATABASE OPERATION
//            viewModel.isLoading.observe(viewLifecycleOwner, Observer { Loading ->
//                if (Loading) {
//                    Progress_dialog!!.show()
//                } else {
//                    Progress_dialog!!.hide()
//                }
//            })
//            viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errors ->
//                if (errors != null) {
//                    if (errors.isNotEmpty()) {
//                        Progress_dialog!!.hide()
//                    }
//                }
//            })
//            Progress_dialog!!.show()

       if(!( viewModel.isAuthenticatedUser(UserModel(username, password)))) {
           binding.LgPgErrorLabel.text = "Username or Password is incorrect"
           binding.LgPgErrorLabel.visibility = View.VISIBLE
           LgPgErrorLabel.startAnimation(shake)
//           Progress_dialog!!.hide()
           return false
       }

           return  true
        }

//        binding.LgPgErrorLabel.text = "Username/Password is incorrect"
//        binding.LgPgErrorLabel.setVisibility(View.VISIBLE)
//        binding.LgPgErrorLabel.startAnimation(shake)
    }

    fun basicValidation(): Boolean {
        with(binding) {
            if (LgPgUsernameEditText.text.isNullOrEmpty() || LgPgPasswordEditText.text.isNullOrEmpty()) {
                binding.LgPgErrorLabel.text = "All the fields are mandatory"
                binding.LgPgErrorLabel.visibility = View.VISIBLE
                LgPgErrorLabel.startAnimation(shake)
                return false
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Progress_dialog!!.dismiss()
    }

}