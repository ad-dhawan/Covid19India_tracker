package com.example.covid19tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.models.Statistics

class StatisticsRecyclerAdapter(val context: Context, val itemList: ArrayList<Statistics>): RecyclerView.Adapter<StatisticsRecyclerAdapter.StatisticsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_statistic_view, parent, false)

        return StatisticsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val case = itemList[position]
        holder.state.text = case.state
        holder.totalCases.text = case.totalCases
        holder.deaths.text = case.deaths
        holder.activeCases.text = case.activeCases
        holder.recovered.text = case.recovered
    }


    class StatisticsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val state: TextView = view.findViewById(R.id.state)
        val totalCases: TextView = view.findViewById(R.id.totalCases)
        val deaths: TextView = view.findViewById(R.id.deaths)
        val recovered: TextView = view.findViewById(R.id.recovered)
        val activeCases: TextView = view.findViewById(R.id.activeCases)
    }
}

