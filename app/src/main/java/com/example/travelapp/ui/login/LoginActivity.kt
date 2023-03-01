package com.example.travelapp.ui.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.*
import com.crocodic.core.helper.DateTimeHelper
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.base.SharedPreference
import com.example.travelapp.data.Const
import com.example.travelapp.databinding.ActivityLoginBinding
import com.example.travelapp.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginButton.setOnClickListener {
            val phone = binding.etPhone.textOf()
            val password = binding.etPassword.textOf()
            viewModel.login(phone, password)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Stealing Your Information...")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "we stole ur info, loser")
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            ApiStatus.ERROR -> {
                                loadingDialog.dismiss()
                                tos(it.message ?: "login Failed")
                            }
                            else -> loadingDialog.setResponse("Else")
                        }
                    }
                }
            }
        }
    }
}