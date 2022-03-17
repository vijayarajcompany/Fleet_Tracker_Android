package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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

            val img = ResourcesCompat.getDrawable(context.getResources(),R.drawable.home_icon,context.getTheme())
            holder.binding.VehiclePgVehicleImageView.setImageDrawable(img)

            SetVehicleImage(vehicleee)


        }
        holder.itemView.setOnClickListener {
            onItemClick(vehicleee)
        }

    }

    private fun RecyclerRowVehicleBinding.SetVehicleImage(
        vehicleee: VehicleModel
    ) {
        //COIL Image Handling
        if (vehicleee.name.lowercase().trim().contains("car")) {
            VehiclePgVehicleImageView.load(R.drawable.car1)
        } else if (vehicleee.name.lowercase().trim().contains("truck")) {
            VehiclePgVehicleImageView.load(R.drawable.truck2)
        } else if (vehicleee.name.lowercase().trim().contains("van")) {
            VehiclePgVehicleImageView.load(R.drawable.van2)
        }
        else if (vehicleee.name.lowercase().trim().contains("bus")) {
            VehiclePgVehicleImageView.load(R.drawable.bus1)
        }
        else if (vehicleee.name.lowercase().trim().contains("lift")) {
            VehiclePgVehicleImageView.load(R.drawable.lift1)
        }
        else
        {
            VehiclePgVehicleImageView.load(R.drawable.car1)
        }
    }

    override fun getItemCount(): Int = vehicle.size
}