package com.example.travelapp.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toObject
import com.example.travelapp.api.ApiService
import com.example.travelapp.base.BaseViewModel
import com.example.travelapp.data.Session
import com.example.travelapp.data.Const
import com.example.travelapp.data.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val apiService: ApiService, private val gson: Gson, private val session: Session) : BaseViewModel() {

        fun login(
            phone: String,
            password: String,
        ) = viewModelScope.launch {
//        _apiResponse.send(ApiResponse().responseLoading())
            ApiObserver({ apiService.login(phone, password) },
                false, object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                        val token = response.getString("token")
                        session.setValue(Const.TOKEN.API_TOKEN,token)
                        session.saveUser(data)
                        _apiResponse.send(ApiResponse().responseSuccess())

                    }

                    override suspend fun onError(response: ApiResponse) {
                        super.onError(response)
                        _apiResponse.send(ApiResponse().responseError())

                    }
                })
        }

    }