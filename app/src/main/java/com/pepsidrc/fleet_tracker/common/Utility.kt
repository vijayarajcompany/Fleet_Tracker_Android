package com.pepsidrc.fleet_tracker.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment


//http://127.0.0.1:8000/api/v1/users/?name=ajay&password=drc

class Utility {
    companion object {
        fun View.hideKeyboards() = ViewCompat.getWindowInsetsController(this)
            ?.hide(WindowInsetsCompat.Type.ime())
        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }
        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }
        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}