package com.example.covid19tracker.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.covid19tracker.R
import com.example.covid19tracker.adapter.NewsRecyclerAdapter
import com.example.covid19tracker.models.News
import com.example.covid19tracker.util.ConnectionManager
import org.json.JSONException

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    lateinit var recyclerNews: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerAdapter: NewsRecyclerAdapter

    val newsList = arrayListOf<News>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        recyclerNews = view.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(activity)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE


        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=f56265da815940c781ee2ac47e3f1109"

        if (ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

                try {
                    progressLayout.visibility = View.GONE

                        val articles = it.getJSONArray("articles")
                        for (i in 0 until articles.length()){
                            val newsJsonObject = articles.getJSONObject(i)
                            val newsArticles = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("publishedAt"),
                                newsJsonObject.getString("urlToImage")
                            )
                            newsList.add(newsArticles)
                            recyclerAdapter = NewsRecyclerAdapter(activity as Context, newsList)

                            recyclerNews.adapter = recyclerAdapter
                            recyclerNews.layoutManager = layoutManager
                        }
                } catch (e : JSONException){
                    Toast.makeText(activity as Context, "Error Occurred!", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                if (activity != null){
                    Toast.makeText(activity as Context, "Unexpected error occurred!", Toast.LENGTH_SHORT).show()
                }
            })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "f56265da815940c781ee2ac47e3f1109"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)

        } else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure!")
            dialog.setMessage("Internet not available")
            dialog.setPositiveButton("Open settings"){text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }


}
