package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowEmiratesBinding
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowLicenseBinding
import com.pepsidrc.fleet_tracker.model.LicenseModel

class LicenseAdapter(
    private val licensee: List<LicenseModel>,
    var context: Context,
    private val onItemClick: (LicenseModel) -> Unit

): RecyclerView.Adapter<LicenseAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowLicenseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowLicenseBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val licen = licensee[position]
        with(holder.binding){

            DistributionPgLicenseLabel.text = licen.name

//            HomePgTaskLabel.setOnClickListener{
//                onItemClick(task)
//            }
        }
        holder.itemView.setOnClickListener{
            onItemClick(licen)
        }
    }

    override fun getItemCount(): Int = licensee.size
}