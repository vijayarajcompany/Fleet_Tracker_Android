package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowVehicleBinding
import com.pepsidrc.fleet_tracker.model.VehicleModel


class VehicleAdapter(
    private val vehicle: List<VehicleModel>,
    var context: Context,
    private val onItemClick: (VehicleModel) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RecyclerRowVehicleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicleee = vehicle[position]
        with(holder.binding) {
            VehiclePgVehicleLabel.text = vehicleee.name

//            val img = ResourcesCompat.getDrawable(context.getResources(),R.drawable.home_icon,context.getTheme())
//            holder.binding.VehiclePgVehicleImageView.setImageDrawable(img)
//            COIL Image Handling
            when (vehicleee.id) {
                1 ->   VehiclePgVehicleImageView.load(R.drawable.car1)
                2 ->   VehiclePgVehicleImageView.load(R.drawable.truck2)
                3 ->   VehiclePgVehicleImageView.load(R.drawable.van2)
                4 ->   VehiclePgVehicleImageView.load(R.drawable.bus1)
                5 ->   VehiclePgVehicleImageView.load(R.drawable.lift1)
                else -> {
                    VehiclePgVehicleImageView.load(R.drawable.rectangle_background)
                }
            }
//            VehiclePgVehicleImageView.load(R.drawable.home_icon)


        }
        holder.itemView.setOnClickListener {
            onItemClick(vehicleee)
        }


    }

    override fun getItemCount(): Int = vehicle.size
}