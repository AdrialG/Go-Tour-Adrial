package com.example.travelapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import com.bumptech.glide.Glide
import com.example.travelapp.R
import com.example.travelapp.base.BaseActivity
import com.example.travelapp.data.Const
import com.example.travelapp.data.Tour
import com.example.travelapp.databinding.ActivityDetailListBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailListActivity : BaseActivity<ActivityDetailListBinding, DetailListViewModel>(R.layout.activity_detail_list) {
    private var tour: Tour? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)


        maplocation()

        //Reciving TourData
        tour = intent.getParcelableExtra(Const.TOUR.TOUR)
        binding.data = tour

        //Button Back
        binding.detailBack.setOnClickListener {
            onBackPressed()
        }
        binding.locationRoute.setOnClickListener {
            sendLocationIntent()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        binding.mapView.onSaveInstanceState(outState)

    }

    //get Location Mapview
    private fun maplocation() {
        binding.mapView.getMapAsync { map ->
            val lat = tour?.latitude?.toDouble()
            val long = tour?.longitude?.toDouble()

            if (lat != null && long !=null){
                val locationDestination = LatLng(lat,long)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationDestination,15f))
                map.addMarker(
                    MarkerOptions()
                        .position(locationDestination)
                        .title("Go Tour Destination")
                )
            }



        }
    }

    //Sending location to Open Google Maps
    private fun sendLocationIntent(){
        val intentUri = Uri.parse("geo:${tour?.latitude},${tour?.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW,intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}