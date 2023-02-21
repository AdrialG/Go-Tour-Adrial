package com.example.travelapp.ui.list

import android.os.Bundle
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : BaseActivity<ActivityListBinding, ListViewModel>(R.layout.activity_list) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}