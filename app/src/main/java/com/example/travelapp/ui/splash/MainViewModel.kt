package com.example.travelapp.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.travelapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {
    fun splash(done: () -> Unit) = viewModelScope.launch {
        delay(2000)
        done()
    }
}