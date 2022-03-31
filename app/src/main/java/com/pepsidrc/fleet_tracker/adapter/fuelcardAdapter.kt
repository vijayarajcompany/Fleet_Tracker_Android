package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.pepsidrc.fleet_tracker.model.FuelTankModel

class FuelTankArrayAdapter (context: Context, @LayoutRes private val layoutResource: Int, private val fuelTankList: List<FuelTankModel>):
    ArrayAdapter<FuelTankModel>(context, layoutResource, fuelTankList) {

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val mView = createViewFromResource(position, convertView, parent)
////        var selectedMonth: payslipMonth? = null
//        return mView
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mView = super.getView(position, convertView, parent) as TextView
//      val view = mView as TextView
        val fuelcard = fuelTankList[position]
        val selectedFuel = fuelcard.id.toString()
        mView.text = selectedFuel
//      var selectedFuel: FuelCardModel? = null
        return mView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
        val fuelcard = fuelTankList[position]
        val card = fuelcard.id
        view.text = card
        return view
    }

}