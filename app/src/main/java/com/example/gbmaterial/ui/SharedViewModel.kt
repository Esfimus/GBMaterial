package com.example.gbmaterial.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterial.data.api.LoadNasaApi
import com.example.gbmaterial.data.api.apod.Apod
import com.example.gbmaterial.data.api.epic.Epic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "retrofit_failure"
private const val DAYS = 5

class SharedViewModel : ViewModel() {

    private val _apodLive: MutableLiveData<Apod> = MutableLiveData()
    val apodLive: LiveData<Apod> = _apodLive
    private val _apodLiveList: MutableLiveData<List<Apod>> = MutableLiveData()
    val apodLiveList: LiveData<List<Apod>> = _apodLiveList
    val epicLive: MutableLiveData<Epic> = MutableLiveData()
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
    private val emptyEpic = Epic()

    fun loadNasaApod(date: String) {
        val callback = object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                val apod: Apod? = response.body()
                _apodLive.value = apod ?: emptyApod
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

    fun loadNasaApodList() {
        val apodList = MutableList(DAYS) { emptyApod }
        _apodLiveList.value = apodList
        val dateList = generateDates()
        for (i in dateList.indices) {
            val callback = object : Callback<Apod> {
                override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                    val apod: Apod? = response.body()
                    val dateIndex = dateList.indexOf(apod?.date)
                    if (apod != null) apodList.replaceApod(dateIndex, apod)
//                    _apodLiveList.value = apodList
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
            loadNasaApi.getApod(dateList[i], callback)
        }
    }

    private fun MutableList<Apod>.replaceApod(index: Int, apod: Apod) {
        this.apply {
            removeAt(index)
            add(index, apod)
        }
    }

    private fun generateDates(): List<String> {
        val date = LocalDate.now()
        val datesList = mutableListOf<String>()
        datesList.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        for (i in 1 until DAYS) {
            datesList.add(date.minusDays(i.toLong()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        }
        return datesList
    }

    fun setApodLive(apod: Apod) {
        _apodLive.value = apod
    }

    fun loadNasaEpic() {
        val callback = object : Callback<Epic> {
            override fun onResponse(call: Call<Epic>, response: Response<Epic>) {
                val epic: Epic? = response.body()
                epicLive.value = epic ?: emptyEpic
                when (response.code()) {
                    in 300 until 400 -> responseCodeLive.value = "Redirection"
                    in 400 until 500 -> responseCodeLive.value = "Client Error"
                    in 500 until 600 -> responseCodeLive.value = "Server Error"
                }
            }
            override fun onFailure(call: Call<Epic>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }
        }
        val loadNasaApi = LoadNasaApi()
        loadNasaApi.getEpic(callback)
    }

    fun currentDate(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun filterInputText(input: String) = input
            .trim()
            .replace("""\s+""".toRegex(), " ")
            .replace(" ", "%20")
}