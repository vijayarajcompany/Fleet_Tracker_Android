package com.pepsidrc.fleet_tracker.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pepsidrc.fleet_tracker.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun OpenMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
//      intent.putExtra("needCredentialsToSave", savecredentials)
        startActivity(intent)
    }



}
