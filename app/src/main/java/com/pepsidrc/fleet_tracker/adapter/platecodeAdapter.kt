package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowCodeBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pcode = code[position]
        with(holder.binding){
            HandOrTakeOverPgPlateCodeLabel.text = pcode.toString()
//            HomePgTaskLabel.setOnClickListener{
//                onItemClick(task)
//            }
        }

        holder.itemView.setOnClickListener{
            onItemCodeClick(pcode)
        }
    }

    override fun getItemCount(): Int = code.size
}