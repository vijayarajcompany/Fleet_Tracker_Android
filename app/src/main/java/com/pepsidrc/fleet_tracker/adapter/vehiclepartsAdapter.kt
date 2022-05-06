package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowVehiclePartsBinding
import com.pepsidrc.fleet_tracker.model.VehiclePartsModel
//    private val onItemClick: (VehiclePartsModel,needToAdd:Boolean) -> Unit
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

        with(holder.binding){

            DistributionPgPartsLabel.text = parts.name

            if(parts.selected){
                DistributionPgPartsImgButton.setImageResource(R.drawable.green_check_mark_transp)
            }
            else
            {
                DistributionPgPartsImgButton.setImageResource(R.drawable.red_uncheck_mark_transp)
            }

        }

        holder.itemView.setOnClickListener{
            with(holder.binding) {
                parts.selected = !parts.selected

//                DistributionPgPartsImgButton.setImageResource(R.drawable.green_check_mark_transp)

                if(parts.selected){
                    DistributionPgPartsImgButton.setImageResource(R.drawable.green_check_mark_transp)
                    onItemClick(parts)
                    notifyDataSetChanged()
                }
                else
                {
                    DistributionPgPartsImgButton.setImageResource(R.drawable.red_uncheck_mark_transp)
                    onItemClick(parts)
                    notifyDataSetChanged()
                }
            }
        }



        holder.binding.DistributionPgPartsImgButton.setOnClickListener{
            with(holder.binding) {
                parts.selected = !parts.selected

//                DistributionPgPartsImgButton.setImageResource(R.drawable.green_check_mark_transp)

                if(parts.selected){
                    DistributionPgPartsImgButton.setImageResource(R.drawable.green_check_mark_transp)
                    onItemClick(parts)
                    notifyDataSetChanged()
                }
                else
                {
                    DistributionPgPartsImgButton.setImageResource(R.drawable.red_uncheck_mark_transp)
                    onItemClick(parts)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int = vehicleparts.size
}



