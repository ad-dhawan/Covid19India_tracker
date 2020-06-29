package com.example.covid19tracker.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covid19tracker.R
import com.example.covid19tracker.adapter.StatisticsRecyclerAdapter
import com.example.covid19tracker.models.Statistics
import com.example.covid19tracker.util.ConnectionManager
import org.json.JSONException
import kotlin.math.roundToInt
import android.widget.TextView as TextView1

@Suppress("UNREACHABLE_CODE")
class CasesFragment : Fragment() {

    lateinit var tvTotalCases: TextView1
    lateinit var tvDeaths: TextView1
    lateinit var tvRecovered: TextView1
    lateinit var tvActiveCases: TextView1
    lateinit var tvNewCases: TextView1
    lateinit var tvLastUpdated: TextView1
    lateinit var tvDeathsRate: TextView1
    lateinit var tvRecoveredRate: TextView1

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val casesInfoList = arrayListOf<Statistics>()

//    lateinit var progressLayout: RelativeLayout
//    lateinit var progressBar: ProgressBar

    lateinit var recyclerAdapter: StatisticsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cases, container, false)

        tvTotalCases = view.findViewById(R.id.tvTotalCases)
        tvDeaths = view.findViewById(R.id.tvDeaths)
        tvRecovered = view.findViewById(R.id.tvRecovered)
        tvActiveCases = view.findViewById(R.id.tvActiveCases)
        tvNewCases = view.findViewById(R.id.tvNewCases)
        tvLastUpdated = view.findViewById(R.id.tvLastUpdated)
        tvDeathsRate = view.findViewById(R.id.tvDeathsRate)
        tvRecoveredRate = view.findViewById(R.id.tvRecoveredRate)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)

        recyclerDashboard.isNestedScrollingEnabled = false

//        progressLayout = view.findViewById(R.id.progressLayout)
//        progressBar = view.findViewById(R.id.progressBar)
//
//        progressLayout.visibility = View.VISIBLE
        allStatistics()

        return view
        }


    fun allStatistics(){
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://api.covid19india.org/data.json"

        if (ConnectionManager().checkConnectivity(activity as Context)) {

            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

                    try {
//                        progressLayout.visibility = View.GONE

                        val statewise = it.getJSONArray("statewise")

                        val totalStatisticsObject = statewise.getJSONObject(0)
                        tvTotalCases.text = totalStatisticsObject.getString("confirmed")
                        tvDeaths.text = totalStatisticsObject.getString("deaths")
                        tvRecovered.text = totalStatisticsObject.getString("recovered")
                        tvActiveCases.text = totalStatisticsObject.getString("active")
                        tvLastUpdated.text = totalStatisticsObject.getString("lastupdatedtime")
                        tvNewCases.text = totalStatisticsObject.getString("deltaconfirmed")

                        val deaths: Double = totalStatisticsObject.getString("deaths").toDouble()
                        val recovered: Double = totalStatisticsObject.getString("recovered").toDouble()
                        val total: Double = totalStatisticsObject.getString("confirmed").toDouble()

                        val deathRate = (deaths / total) * 100
                        tvDeathsRate.text = ("%.2f".format(deathRate) + "%")

                        val recoveredRate = (recovered / total) * 100
                        tvRecoveredRate.text = ("%.2f".format(recoveredRate) + "%")


                        for (i in 1 until 33) {
                            val statisticsJsonObject = statewise.getJSONObject(i)
                            val statisticsObject = Statistics(
                                statisticsJsonObject.getString("state"),
                                statisticsJsonObject.getString("confirmed"),
                                statisticsJsonObject.getString("deaths"),
                                statisticsJsonObject.getString("active"),
                                statisticsJsonObject.getString("recovered")
                            )
                            casesInfoList.add(statisticsObject)
                            recyclerAdapter =
                                StatisticsRecyclerAdapter(activity as Context, casesInfoList)

                            recyclerDashboard.adapter = recyclerAdapter
                            recyclerDashboard.layoutManager = layoutManager
                        }

                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            "Unexpected Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }, Response.ErrorListener {
                    if (activity != null) {
                        Toast.makeText(
                            activity as Context,
                            "Volley error occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "04c3f94096a169"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        }else{
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
    }
    }
