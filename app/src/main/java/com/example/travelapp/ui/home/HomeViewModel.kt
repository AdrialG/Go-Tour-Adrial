package com.example.travelapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.extension.toList
import com.example.travelapp.api.ApiService
import com.example.travelapp.base.BaseViewModel
import com.example.travelapp.data.ImageSlideData
import com.example.travelapp.data.Session
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService, private val gson: Gson, private val session: Session): BaseViewModel() {

    var image = MutableLiveData<List<ImageSlideData>>()
//    var tour = MutableLiveData<List<Tour>>()

    fun imageSlider() = viewModelScope.launch {
        ApiObserver({ apiService.imageSlider()},false, object : ApiObserver.ResponseListener{
            override suspend fun onSuccess(response: JSONObject) {
                val status = response.getInt(ApiCode.STATUS)
                if (status == ApiCode.SUCCESS){

                    val dataImage = response.getJSONArray(ApiCode.DATA).toList<ImageSlideData>(gson)

                    image.postValue(dataImage)

                } else {
                    val message = response.getString(ApiCode.MESSAGE)
                }
            }
        })
    }

}