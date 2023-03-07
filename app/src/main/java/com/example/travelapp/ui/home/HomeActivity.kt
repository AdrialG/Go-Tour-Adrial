package com.example.travelapp.ui.home

import android.os.Bundle
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Const
import com.example.travelapp.data.ImageSlide
import com.example.travelapp.data.Session
import com.example.travelapp.data.Tour
import com.example.travelapp.databinding.ActivityHomeBinding
import com.example.travelapp.databinding.CardLayoutBinding
import com.example.travelapp.ui.detail.DetailListActivity
import com.example.travelapp.ui.list.ListActivity
import com.example.travelapp.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home),
    SearchView.OnQueryTextListener{

    @Inject
    lateinit var session: Session

    private var tour = ArrayList<Tour?>()
    private var tourAll = ArrayList<Tour?>()

    override fun onStart() {
        val user = session.getUser()
        if (user != null) {
            binding.user = user
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        val user = session.getUser()

        getImage()
        observe()
        getTourList()

        //SearchView Function
        binding.searchViewHome.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                val filter = tourAll.filter { it?.name?.contains("$text", true) == true }
                Timber.d("Keyword $text Data : $filter")
                tour.clear()
                filter.forEach {
                    tour.add(it)
                }
                binding.rvHome.adapter?.notifyDataSetChanged()
                binding.rvHome.adapter?.notifyItemInserted(0)

            } else {
                tour.clear()
                binding.rvHome.adapter?.notifyDataSetChanged()
                tour.addAll(tourAll)
                Timber.d("noteall : $tourAll")
                binding.rvHome.adapter?.notifyItemInserted(0)
            }
        }

        //Button Image View
        binding.homeProfile.setOnClickListener {
            openActivity<ProfileActivity>()
        }

        //Nature
        binding.buttonNature.setOnClickListener {
            openActivity<ListActivity> {
                putExtra(Const.CATEGORY.ID, 1)
            }
        }

        //Parks
        binding.buttonParks.setOnClickListener {
            openActivity<ListActivity> {
                putExtra(Const.CATEGORY.ID, 2)
            }
        }

        //All
        binding.buttonAll.setOnClickListener {
            openActivity<ListActivity> {
                putExtra(Const.CATEGORY.ID, 4)
            }
        }

        //View All
        binding.textViewAll.setOnClickListener {
            openActivity<ListActivity> {
                putExtra(Const.CATEGORY.ID, 4)
            }
        }

        //Adapter List Tour
        binding.rvHome.adapter = CoreListAdapter<CardLayoutBinding, Tour>(R.layout.card_layout)
            .initItem(tour) { position, data ->
                openActivity<DetailListActivity> {
                    putExtra(Const.TOUR.TOUR, data)
                }
            }

        //Preview Image Profile
        Glide
            .with(this)
            .load(user?.image)
            .into(binding.homeProfile)

//        //Preview Image Slider
//        imageList.add(SlideModel("https://magang.crocodic.net/ki/kelompok_3/tour-app/public/image/qacdaVHctsSD3xZfdpBoSq1lfVTZpFEYs55Uypzf.jpg"))
//        imageList.add(SlideModel("https://magang.crocodic.net/ki/kelompok_3/tour-app/public/image/fXl0LUhqAZ1jS4vOOGvQ7oX7fJ6LlwcRwbGB6HCI.jpg"))
//

        val imageSlider = findViewById<ImageSlider>(R.id.home_image_slider)
        imageSlider.setImageList(imageList)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getTourList()
                }
            }
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.d(newText)
        return false
    }

    //Get Note Function
    private fun getTourList() {
        viewModel.tourList()
    }

    private fun observe() {
        viewModel.tour.observe(this) {
            tour.clear()
            tourAll.clear()
            tour.addAll(it)
            tourAll.addAll(it)
            binding?.rvHome?.adapter?.notifyDataSetChanged()
            binding?.rvHome?.adapter?.notifyItemInserted(0)
        }

        viewModel.image.observe(this) {
            initSlider(it)
        }
    }

    fun getImage() {
        viewModel.imageSlider()
    }

    private fun initSlider(data: List<ImageSlide>) {
        val imageList = ArrayList<SlideModel>()
        data.forEach {
            imageList.add(SlideModel(it.image))
        }
        binding.homeImageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

}