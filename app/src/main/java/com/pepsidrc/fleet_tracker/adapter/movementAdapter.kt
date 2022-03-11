package com.pepsidrc.fleet_tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pepsidrc.fleet_tracker.databinding.RecyclerRowMovementBinding
import com.pepsidrc.fleet_tracker.model.SubTaskModel

class MovementAdapter(private val subtasks: List<SubTaskModel>, private val onItemClick: (SubTaskModel) -> Unit): RecyclerView.Adapter<MovementAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding: RecyclerRowMovementBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(


            RecyclerRowMovementBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subtask = subtasks[position]

        with(holder.binding){
            MovementPgSubTaskLabel.text = subtask.name
        }

        holder.itemView.setOnClickListener{
            onItemClick(subtask)
        }


    }

    override fun getItemCount(): Int = subtasks.size
}