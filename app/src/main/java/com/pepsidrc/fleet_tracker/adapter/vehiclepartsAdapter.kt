package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowVehiclePartsBinding
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel

class vehiclepartsAdapter(
    private val vehicleparts: List<VehiclePartsModel>,
    var context: Context,
    private val onItemClick: (VehiclePartsModel) -> Unit

): RecyclerView.Adapter<vehiclepartsAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowVehiclePartsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowVehiclePartsBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parts = vehicleparts[position]
//        holder.binding.DistributionPg_Parts_label
        with(holder.binding){
                DistributionPgPartsLabel.text = parts.name

        }
        holder.itemView.setOnClickListener{

            with(holder.binding){

                if(DistributionPgPartsImgButton.visibility == View.VISIBLE){
                    DistributionPgPartsImgButton.visibility = View.GONE
                }
                else
                {
                    DistributionPgPartsImgButton.visibility = View.VISIBLE
                }

                DistributionPgPartsImgButton.load(R.drawable.closeicon)


            }

            onItemClick(parts)
        }
    }

    override fun getItemCount(): Int = vehicleparts.size
}



