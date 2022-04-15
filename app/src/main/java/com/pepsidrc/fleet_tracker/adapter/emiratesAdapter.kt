package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowEmiratesBinding
import com.pepsidrc.fleet_tracker.model.EmiratesModel

class EmiratesAdapter(
    private val emirates: List<EmiratesModel>,
    var context: Context,
    private val onItemClick: (EmiratesModel) -> Unit

): RecyclerView.Adapter<EmiratesAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowEmiratesBinding): RecyclerView.ViewHolder(binding.root)

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowEmiratesBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emiratee = emirates[position]
        val strNonSelectedColor = "#e3e4e5"
//          val strSelectedColor = "#0091f4"
        val strSelectedColor = "#4a6d87"

        with(holder.binding){
            HandTakeOverPgEmiratesLabel.text = emiratee.name
            holder.itemView.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandTakeOverPgRowConstraintLayout.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandTakeOverPgEmiratesLabel.setBackgroundColor(Color.parseColor(strNonSelectedColor))
            HandTakeOverPgEmiratesLabel.setTypeface(null, Typeface.NORMAL)

            if(selectedPosition==position) {
                holder.itemView.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandTakeOverPgRowConstraintLayout.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandTakeOverPgEmiratesLabel.setBackgroundColor(Color.parseColor(strSelectedColor))
                HandTakeOverPgEmiratesLabel.setTypeface(null, Typeface.BOLD)
            }
        }

        holder.itemView.setOnClickListener{
            this.selectedPosition = position
            onItemClick(emiratee)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = emirates.size
}