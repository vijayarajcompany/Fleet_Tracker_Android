package com.pepsidrc.fleet_tracker.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var Progress_dialog: Dialog? = null
    var shake: Animation? = null
    var contxt: Context? = this
    var onBackPressedStatus = true

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: MaterialToolbar
    private lateinit var navController: NavController
    private lateinit var homeImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //region Navigation
        val navHostfrag = binding.navHostFragmentContainer
        toolbar = binding.customToolbar
        toolbar.setNavigationIcon(R.drawable.home_icon)
//      toolbar.setNavigationIconTint(resources.getColor(1, ))
//      android.support().setDisplayHomeAsUpEnabled(true);
//      android.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        initialSetup()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

//        val color =ContextCompat.getColor(
//            this,
//            R.color.white
//        )

//        toolbar.setNavigationIconTint(color)
//        toolbar.setNavigationIcon(R.drawable.ic_right_arrow_back_48)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        //endregion
        homeImageButton = binding.homeImageButton

    }

    fun OpenLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun ChangeToolBarText( title:String){
        binding.heading.text = title
    }

    fun HideShowHomeButton(hide:Boolean){
        if(hide){
            homeImageButton.visibility = View.INVISIBLE
        }
        else{
            homeImageButton.visibility = View.VISIBLE
        }
    }

    fun initialSetup(){

        binding.signOutImageButton.setOnClickListener {
//            OpenLoginActivity()
            SignOut()
        }

        binding.homeImageButton.setOnClickListener {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            navController = navHostFragment.navController
            navController.popBackStack(R.id.homeFragment, false)

        }
//        val intent = intent
//        val needCredentialsToSave = intent.getBooleanExtra("needCredentialsToSave", false)

    }



    fun SignOut() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater.inflate(R.layout.dialog_signout, null)
        builder.setView(inflater)
        builder.setCancelable(false)
        val dialog: AlertDialog = builder.create()
        val dialogokbtn = inflater!!.findViewById<Button>(R.id.Ok_button)
        val dialogcancelbtn = inflater.findViewById<Button>(R.id.Cancel_button)
        val dialogImagecancelbtn = inflater.findViewById<Button>(R.id.SignCancel_button)

        dialogokbtn?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
            startActivity(intent)
        }

        dialogcancelbtn?.setOnClickListener {
            dialog.cancel()
        }

        dialogImagecancelbtn?.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    fun setHardwareBackPressedStatus(isAllowHardwareBackPress:Boolean){
        onBackPressedStatus = isAllowHardwareBackPress
    }

    override fun onBackPressed() {

        if (onBackPressedStatus){
            super.onBackPressed()
        }

//        val navHost = supportFragmentManager.findFragmentById(R.id.nav_login_fragment)
//        navHost?.let { navFragment ->
//            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
//                if (fragment is LandingFragment) {
//                    finish()
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }


    }

}

