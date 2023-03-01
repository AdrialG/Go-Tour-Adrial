package com.example.travelapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Const
import com.example.travelapp.data.Tour
import com.example.travelapp.databinding.ActivityListBinding
import com.example.travelapp.databinding.ListLayoutBinding
import com.example.travelapp.ui.detail.DetailListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                viewModel.tourList(3)
                binding.textView4.text = "All"
            }

            4 -> {
                viewModel.tourList(4)
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

}
