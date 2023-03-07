package com.example.travelapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.extension.toList
import com.example.travelapp.api.ApiService
import com.example.travelapp.base.BaseViewModel
import com.example.travelapp.data.Session
import com.example.travelapp.data.Tour
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val apiService: ApiService, private val gson: Gson, private val session: Session) : BaseViewModel() {
    var tour = MutableLiveData<List<Tour>>()

    fun tourList(idTour: Int) = viewModelScope.launch {
        ApiObserver({ apiService.tourCategory(idTour) },
            false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val status = response.getInt(ApiCode.STATUS)
                    if (status == ApiCode.SUCCESS) {
                        val data = response.getJSONArray(ApiCode.DATA).toList<Tour>(gson)
                        tour.postValue(data)

                    } else {
                        val message = response.getString(ApiCode.MESSAGE)
                    }
                }
            })
    }

//    fun tourListNature() = viewModelScope.launch {
//        ApiObserver({ apiService.tourCategoryNature() },
//            false, object : ApiObserver.ResponseListener {
//                override suspend fun onSuccess(response: JSONObject) {
//                    val status = response.getInt(ApiCode.STATUS)
//                    if (status == ApiCode.SUCCESS) {
//                        val data = response.getJSONArray(ApiCode.DATA).toList<Tour>(gson)
//                        tour.postValue(data)
//
//                    } else {
//                        val message = response.getString(ApiCode.MESSAGE)
//                    }
//                }
//            })
//    }
//
//    fun tourListPark() = viewModelScope.launch {
//        ApiObserver({ apiService.tourCategoryPark() },
//            false, object : ApiObserver.ResponseListener {
//                override suspend fun onSuccess(response: JSONObject) {
//                    val status = response.getInt(ApiCode.STATUS)
//                    if (status == ApiCode.SUCCESS) {
//                        val data = response.getJSONArray(ApiCode.DATA).toList<Tour>(gson)
//                        tour.postValue(data)
//
//                    } else {
//                        val message = response.getString(ApiCode.MESSAGE)
//                    }
//                }
//            })
//    }
//
//    //tour list All Function
//    fun tourListAll() = viewModelScope.launch {
//        ApiObserver({ apiService.tourCategoryAll() },
//            false, object : ApiObserver.ResponseListener {
//                override suspend fun onSuccess(response: JSONObject) {
//                    val status = response.getInt(ApiCode.STATUS)
//                    if (status == ApiCode.SUCCESS) {
//                        val data = response.getJSONArray(ApiCode.DATA).toList<Tour>(gson)
//                        tour.postValue(data)
//
//                    } else {
//                        val message = response.getString(ApiCode.MESSAGE)
//                    }
//                }
//            })
//    }
//
    fun tourListRec() = viewModelScope.launch {
        ApiObserver({ apiService.tourList() }, false, object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val status = response.getInt(ApiCode.STATUS)
                    if (status == ApiCode.SUCCESS) {
                        val data = response.getJSONArray(ApiCode.DATA).toList<Tour>(gson)
                        tour.postValue(data)

                    } else {
                        val message = response.getString(ApiCode.MESSAGE)
                    }
                }
            })
    }

}