package com.example.covid19tracker.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.models.News
import com.example.covid19tracker.models.Statistics
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter(val context: Context, val itemList: ArrayList<News>): RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_news_view, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news = itemList[position]
        holder.title.text = news.title
        holder.publishedDate.text = news.publishedDate

        Picasso.get().load(news.image).error(R.drawable.virus).into(holder.newsImage)
    }


    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.tvNewsTitle)
        val publishedDate: TextView = view.findViewById(R.id.tvPublishedDate)
        val newsImage: ImageView = view.findViewById(R.id.imgNews)
    }
}