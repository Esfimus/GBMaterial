package com.example.gbmaterial.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterial.data.api.LoadNasaApi
import com.example.gbmaterial.data.api.apod.Apod
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "retrofit_failure"

class SharedViewModel : ViewModel() {

    val pictureLive: MutableLiveData<Apod> = MutableLiveData()
    val responseCodeLive: MutableLiveData<String> = MutableLiveData()
    private val emptyApod = Apod(
        "copyright",
        "date",
        "explanation",
        "hdurl",
        "media_type",
        "service_version",
        "title",
        "url"
    )

    fun loadPicture(date: String) {
        val callback = object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                val picture: Apod? = response.body()
                pictureLive.value = picture ?: emptyApod
                when (response.code()) {
                    in 300 until 400 -> responseCodeLive.value = "Redirection"
                    in 400 until 500 -> responseCodeLive.value = "Client Error"
                    in 500 until 600 -> responseCodeLive.value = "Server Error"
                }
            }
            override fun onFailure(call: Call<Apod>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }
        }
        val loadNasaApi = LoadNasaApi()
        loadNasaApi.getApod(date, callback)
    }

    fun currentDate(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun filterInputText(input: String) = input
            .trim()
            .replace("""\s+""".toRegex(), " ")
            .replace(" ", "%20")
}