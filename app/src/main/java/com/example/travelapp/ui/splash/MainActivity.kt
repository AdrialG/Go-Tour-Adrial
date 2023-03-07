package com.example.travelapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.crocodic.core.extension.openActivity
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Session
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.ui.home.HomeActivity
import com.example.travelapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val isUser = session.getUser()
            if (isUser == null){
                viewModel.splash {
                    openActivity<LoginActivity>()
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }

            }else{
                viewModel.splash {
                    openActivity<HomeActivity>()
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }

            }
        },2000)
    }
}