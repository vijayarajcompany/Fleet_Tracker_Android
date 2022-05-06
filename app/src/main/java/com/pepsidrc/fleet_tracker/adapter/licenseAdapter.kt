package com.pepsidrc.fleet_tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.R
import com.pepsidrc.fleet_tracker.data.LicenseTbl
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

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowLicenseBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val license = licensee[position]
        with(holder.binding){
            DistributionPgLicenseLabel.text = license.name
            if(license.selected){
                imglicenseSelection.setImageResource(R.drawable.green_check_mark_transp)
            }
            else
            {
                imglicenseSelection.setImageResource(R.drawable.red_uncheck_mark_transp)
            }
//            HomePgTaskLabel.setOnClickListener{
//                onItemClick(task)
//            }


        }
        holder.itemView.setOnClickListener{
//            onItemClick(license)
            with(holder.binding){
                license.selected = !license.selected
                DistributionPgLicenseLabel.text = license.name

                if(license.selected){
                    imglicenseSelection.setImageResource(R.drawable.green_check_mark_transp)
                    onItemClick(license)
                    notifyDataSetChanged()
                }
                else
                {
                    imglicenseSelection.setImageResource(R.drawable.red_uncheck_mark_transp)
                    onItemClick(license)
                    notifyDataSetChanged()
                }
            }
        }


        holder.binding.imglicenseSelection.setOnClickListener{
//            onItemClick(license)
            with(holder.binding){
                license.selected = !license.selected
                DistributionPgLicenseLabel.text = license.name

                if(license.selected){
                    imglicenseSelection.setImageResource(R.drawable.green_check_mark_transp)
                    onItemClick(license)
                    notifyDataSetChanged()
                }
                else
                {
                    imglicenseSelection.setImageResource(R.drawable.red_uncheck_mark_transp)
                    onItemClick(license)
                    notifyDataSetChanged()
                }
            }
        }



    }

    override fun getItemCount(): Int = licensee.size
}