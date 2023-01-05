package com.ghosh.bitcointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.ghosh.bitcointracker.databinding.ActivityMainBinding
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        val view=mainBinding.root
        setContentView(view)

        var arrayAdapter=ArrayAdapter.createFromResource(this@MainActivity,R.array.currency_array,
        android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mainBinding.SpinnerCurrency.adapter=arrayAdapter

        mainBinding.SpinnerCurrency.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(parent!=null)
                {
                    var curr:String=parent.getItemAtPosition(position).toString()
                    mainBinding.currencySelected.text=curr
                    showAmount(curr)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun showAmount(curr: String)
    {
        val url: String="http://api.coinlayer.com/api/live?access_key=1a7c3fc2563537de8a1770322fc471fa&target="+curr+"&symbols=BTC,ETH"


        val requestQueue:RequestQueue=Volley.newRequestQueue(this@MainActivity)
        val jsonObjectRequest:JsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
            {response->
                try {
                    val value: String=response.getJSONObject("rates").getString("BTC")
                    mainBinding.TvAmount.text = value
                }
                catch(e:JSONException)
                {
                    e.printStackTrace()
                }
            },
            {
                Toast.makeText(this@MainActivity,"Please enter a proper currency.", Toast.LENGTH_LONG).show()
            })

        requestQueue.add(jsonObjectRequest)
    }



}