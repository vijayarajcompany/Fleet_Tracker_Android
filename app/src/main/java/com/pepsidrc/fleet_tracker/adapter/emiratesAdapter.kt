package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowEmiratesBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emiratee = emirates[position]
        with(holder.binding){
//            DistributionPgPartsLabel.text = emiratee.name
            DistributionPgEmiratesLabel.text = emiratee.name
//            HomePgTaskLabel.setOnClickListener{
//                onItemClick(task)
//            }
        }
        holder.itemView.setOnClickListener{
            onItemClick(emiratee)
        }
    }

    override fun getItemCount(): Int = emirates.size
}