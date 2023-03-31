package com.example.gbmaterial.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterial.data.api.LoadRetrofitPicture
import com.example.gbmaterial.data.api.PictureLoaded
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "retrofit_failure"

class SharedViewModel : ViewModel() {

    val pictureLive: MutableLiveData<PictureLoaded> = MutableLiveData()
    val responseCodeLive: MutableLiveData<String> = MutableLiveData()
    private val emptyPictureLoaded = PictureLoaded(
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
        val callback = object : Callback<PictureLoaded> {
            override fun onResponse(call: Call<PictureLoaded>, response: Response<PictureLoaded>) {
                val picture: PictureLoaded? = response.body()
                pictureLive.value = picture ?: emptyPictureLoaded
                when (response.code()) {
                    in 300 until 400 -> responseCodeLive.value = "Redirection"
                    in 400 until 500 -> responseCodeLive.value = "Client Error"
                    in 500 until 600 -> responseCodeLive.value = "Server Error"
                }
            }
            override fun onFailure(call: Call<PictureLoaded>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }
        }
        val loadRetrofitPicture = LoadRetrofitPicture()
        loadRetrofitPicture.getPicture(date, callback)
    }

    fun currentDate(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    fun filterInputText(input: String) = input
            .trim()
            .replace("""\s+""".toRegex(), " ")
            .replace(" ", "%20")
}