package com.example.covid19tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.models.Countries
import com.example.covid19tracker.models.Statistics
import kotlinx.android.synthetic.main.recycler_country_view.view.*

class CountriesRecyclerAdapter(val context: Context, val itemList: ArrayList<Countries>): RecyclerView.Adapter<CountriesRecyclerAdapter.CountriesViewHolder>()  {


    class CountriesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtCountryName: TextView = view.findViewById(R.id.txtCountryName)
        val txtCases: TextView = view.findViewById(R.id.txtCases)
        val txtNewCases: TextView = view.findViewById(R.id.txtNewCases)
        val txtDeaths: TextView = view.findViewById(R.id.txtDeaths)
        val txtNewDeaths: TextView = view.findViewById(R.id.txtNewDeaths)
        val txtRecovered: TextView = view.findViewById(R.id.txtRecovered)
        val txtNewRecovered: TextView = view.findViewById(R.id.txtNewRecovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_country_view, parent, false)

        return CountriesRecyclerAdapter.CountriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val stats = itemList[position]
        holder.txtCountryName.text = stats.country
        holder.txtCases.text = stats.cases.toString()
        holder.txtNewCases.text = stats.newCases.toString()
        holder.txtDeaths.text = stats.deaths.toString()
        holder.txtNewDeaths.text = stats.newDeaths.toString()
        holder.txtRecovered.text = stats.recovered.toString()
        holder.txtNewRecovered.text = stats.newRecovered.toString()
    }
}