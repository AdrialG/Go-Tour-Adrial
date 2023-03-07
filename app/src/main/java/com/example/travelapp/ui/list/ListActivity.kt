package com.example.travelapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.toList
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Const
import com.example.travelapp.data.Tour
import com.example.travelapp.databinding.ActivityListBinding
import com.example.travelapp.databinding.ListLayoutBinding
import com.example.travelapp.ui.detail.DetailListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class ListActivity : BaseActivity<ActivityListBinding, ListViewModel>(R.layout.activity_list) {
    private var tour = ArrayList<Tour?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Ask About Holder
        binding.recyclerView2.adapter =
            object : CoreListAdapter<ListLayoutBinding, Tour>(R.layout.list_layout) {
                override fun onBindViewHolder(
                    holder: ItemViewHolder<ListLayoutBinding, Tour>,
                    position: Int
                ) {
                    val data = tour[position]
                    holder.binding.data = data

                    holder.binding.buttonListDetail.setOnClickListener {
                        openActivity<DetailListActivity> {
                            putExtra(Const.TOUR.TOUR, data)

                        }
                    }
                }
            }.initItem(tour)

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }

        observe()

        val id = intent.getIntExtra(Const.CATEGORY.ID, 0)
        when (id) {
            1 -> {
                viewModel.tourList(1)
                binding.textView4.text = "Nature"
            }

            2 -> {
                viewModel.tourList(2)
                binding.textView4.text = "Park "
            }

            3 -> {
                viewModel.tourListRec()
                binding.textView4.text = "All"
            }

            4 -> {
                viewModel.tourListRec()
                binding.textView4.text = "All"
            }
        }

        binding.imageView3.setOnClickListener {
            onBackPressed()
        }
    }

//    private fun getTourListAll() {
//        viewModel.tourListAll()
//    }

//    private fun getData() {
//        val idCategory = intent.getIntExtra(Const.CATEGORY.ID, 0)
////        viewModel.tourListPath(if ( idCategory > 3) null else idCategory)
//        if (idCategory == 4) {
//            tourRec()
//        } else {
//            viewModel.tourList(idCategory)
//        }
//
//        binding.textView4.text = when (idCategory) {
//            1 -> "Nature"
//            2 -> "Park"
//            3 -> "All"
//            else -> "Recommendation"
//
//        }
//    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.tour.observe(this@ListActivity) {
                        tour.addAll(it)
                        binding.recyclerView2.adapter?.notifyDataSetChanged()
                        Log.d("check adapter", "list tour : $tour")
                        Log.d("check adapter", "check it : $it")

                    }
                }
            }
        }
    }

//    fun tourListPath( idTour : Int?) = viewModelScope.launch {
//        ApiObserver({ apiService.tourCategory(idTour) },
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

    private fun tourRec() {
        viewModel.tourListRec()
    }

}
