package com.example.travelapp.ui.splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.splash {
            openActivity<LoginActivity>()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}