package com.example.covid19tracker.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covid19tracker.R
import com.example.covid19tracker.adapter.CountriesRecyclerAdapter
import com.example.covid19tracker.models.Countries
import com.example.covid19tracker.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class GlobalFragment : Fragment() {

    lateinit var txtTotal: TextView
    lateinit var txtNewTotal: TextView
    lateinit var txtDeaths: TextView
    lateinit var txtNewDeaths: TextView
    lateinit var txtRecovered: TextView
    lateinit var txtNewRecovered: TextView

    lateinit var recyclerCountries: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    val countriesList = arrayListOf<Countries>()
    lateinit var recyclerAdapter: CountriesRecyclerAdapter
    lateinit var btnSort: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_global, container, false)

        txtTotal = view.findViewById(R.id.txtTotal)
        txtNewTotal = view.findViewById(R.id.txtNewTotal)
        txtDeaths = view.findViewById(R.id.txtDeaths)
        txtNewDeaths = view.findViewById(R.id.txtNewDeaths)
        txtRecovered = view.findViewById(R.id.txtRecovered)
        txtNewRecovered = view.findViewById(R.id.txtNewRecovered)

        recyclerCountries = view.findViewById(R.id.recyclerCountries)
        layoutManager = LinearLayoutManager(activity)

        recyclerCountries.isNestedScrollingEnabled = false

        btnSort = view.findViewById(R.id.btnSort)
        btnSort.setOnClickListener {
            Collections.sort(countriesList, Comparator<Countries> { country1, country2 ->
                if (country1.cases > country2.cases) {
                    -1
                } else {
                    1
                }
            })

            recyclerAdapter.notifyDataSetChanged()
        }

        getStatistics()

        return view
    }


    fun getStatistics() {
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://api.covid19api.com/summary"

        if (ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest = object: JsonObjectRequest(Method.GET, url, null, Response.Listener {

                try {

                    val global = it.getJSONObject("Global")
                    txtTotal.text = global.getString("TotalConfirmed")
                    txtNewTotal.text = global.getString("NewConfirmed")
                    txtDeaths.text = global.getString("TotalDeaths")
                    txtNewDeaths.text = global.getString("NewDeaths")
                    txtRecovered.text = global.getString("TotalRecovered")
                    txtNewRecovered.text = global.getString("NewRecovered")

                    val countries = it.getJSONArray("Countries")

                    for (i in 0 until countries.length()){
                        val countriesJsonObject = countries.getJSONObject(i)
                        val countriesObject = Countries(
                            countriesJsonObject.getString("Country"),
                            countriesJsonObject.getString("NewConfirmed").toInt(),
                            countriesJsonObject.getString("TotalConfirmed").toInt(),
                            countriesJsonObject.getString("NewDeaths").toInt(),
                            countriesJsonObject.getString("TotalDeaths").toInt(),
                            countriesJsonObject.getString("NewRecovered").toInt(),
                            countriesJsonObject.getString("TotalRecovered").toInt()
                        )
                        countriesList.add(countriesObject)
                        recyclerAdapter =
                            CountriesRecyclerAdapter(activity as Context, countriesList)

                        recyclerCountries.adapter = recyclerAdapter
                        recyclerCountries.layoutManager = layoutManager
                    }

                } catch (e: JSONException){
                    Toast.makeText(activity as Context, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(activity as Context, "Volley Error Occurred", Toast.LENGTH_SHORT).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "04c3f94096a169"
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

    }

}
