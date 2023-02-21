package com.example.travelapp.ui.home

import android.os.Bundle
import android.util.Log
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.ImageSlideData
import com.example.travelapp.data.Session
import com.example.travelapp.databinding.ActivityHomeBinding
import com.example.travelapp.databinding.ActivityMainBinding
import com.example.travelapp.ui.login.LoginActivity
import com.example.travelapp.ui.splash.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageList = ArrayList<SlideModel>()

        observe()
        getImage()

        val homeImageSlider = findViewById<ImageSlider>(R.id.home_image_slider)
        homeImageSlider.setImageList(imageList)
    }

    fun getImage(){
        viewModel.imageSlider()
    }

    private fun observe(){

        viewModel.image.observe(this){
            Log.d("yes","imagesliders are here $it")
            if(it.isEmpty()){
                tos("empty la")
            } else {
                tos("ketemu")
            }
            initSlider(it)
        }
    }

    private fun initSlider(data: List<ImageSlideData>) {
        val imageList = ArrayList<SlideModel>()
        data.forEach{
            imageList.add(SlideModel(it.image))
        }
        binding.homeImageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }
}