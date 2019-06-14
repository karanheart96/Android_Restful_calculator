package com.karan.restcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.app.DownloadManager.Request
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import android.util.Log
import javax.security.auth.callback.Callback
import android.telecom.Call

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_plus.setOnClickListener {
            fetchJSON()
        }
    }
    fun fetchJSON() {

        // Full URL: http://restapidemo.hdvisionsec.com/api/addition?first_number=8&second_number=11

        val url = "http://restapidemo.hdvisionsec.com/api/addition?first_number="+number1.text+"&second_number="+number2.text
//            val url = "http://restapidemo.hdvisionsec.com/api/addition?first_number=8&second_number=11"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d("value = ","outside success response")
                if (response.isSuccessful) {
                    Log.d("value = ","inside response")
                    val body = response?.body?.string()
                    try {
                        val newJSON = JSONObject(body)
                        val message = newJSON.get("message").toString()

                        result_view.setText(message)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("Value = ","inside failure")
                e.printStackTrace()
            }

        })
    }
}
