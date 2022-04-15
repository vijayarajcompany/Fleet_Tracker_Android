package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowCodeBinding
import com.pepsidrc.fleet_tracker.model.PlateCodeModel

class PlateCodeAdapter(
    private val code: List<String>,
    var context: Context,
    private val onItemCodeClick: (String) -> Unit

): RecyclerView.Adapter<PlateCodeAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowCodeBinding): RecyclerView.ViewHolder(binding.root)

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowCodeBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pcode = code[position]
        val strNonSelectedColor = "#e3e4e5"
        val strSelectedColor = "#4a6d87"

        with(holder.binding){
            HandOrTakeOverPgPlateCodeLabel.text = pcode
            holder.itemView.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandTakeOverPgRowPlateCodeConstraintLayout.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandOrTakeOverPgPlateCodeLabel.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandOrTakeOverPgPlateCodeLabel.setTypeface(null, Typeface.NORMAL)


            if(selectedPosition==position) {
                holder.itemView.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandTakeOverPgRowPlateCodeConstraintLayout.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandOrTakeOverPgPlateCodeLabel.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandOrTakeOverPgPlateCodeLabel.setTypeface(null, Typeface.BOLD)
            }


        }

        holder.itemView.setOnClickListener{
            this.selectedPosition = position
            onItemCodeClick(pcode)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int = code.size
}