package com.example.travelapp.ui.profile

import android.os.Bundle
import com.bumptech.glide.Glide
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Session
import com.example.travelapp.databinding.ActivityProfileBinding
import com.example.travelapp.ui.edit.EditProfileActivity
import com.example.travelapp.ui.home.HomeActivity
import com.example.travelapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = session.getUser()
        if (user != null) {
            binding.user = user
        }

        //Preview Profile Picture
        Glide
            .with(this)
            .load(user?.image)
            .into(binding.profileProfileImage)

        binding.editProfile.setOnClickListener {
            openActivity<EditProfileActivity>()
        }

        binding.logoutBox.setOnClickListener {
            viewModel.logout()
            openActivity<LoginActivity>()
            finishAffinity()
        }

        binding.profileBack.setOnClickListener {
            openActivity<HomeActivity> {  }
        }
    }
}