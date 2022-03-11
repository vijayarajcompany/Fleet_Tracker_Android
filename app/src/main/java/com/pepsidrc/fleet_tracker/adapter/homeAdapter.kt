package com.pepsidrc.fleet_tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowHomeBinding
import com.pepsidrc.fleet_tracker.model.TaskModel

class HomeAdapter(private val taskModels: List<TaskModel>, private val onItemClick: (TaskModel) -> Unit): RecyclerView.Adapter<HomeAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowHomeBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerRowHomeBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val task = taskModels[position]
//        holder.binding.HomePgTaskLabel.text = task.name
        with(holder.binding){
            HomePgTaskLabel.text = task.name
//            HomePgTaskLabel.setOnClickListener{
//                onItemClick(task)
//            }
        }
        holder.itemView.setOnClickListener{
            onItemClick(task)
        }
    }

    override fun getItemCount(): Int = taskModels.size
}