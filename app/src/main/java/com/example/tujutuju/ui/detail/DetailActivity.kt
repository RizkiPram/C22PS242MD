package com.example.tujutuju.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.tujutuju.data.response.PlacesItem
import com.example.tujutuju.databinding.ActivityDetailBinding
import com.google.android.gms.maps.GoogleMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity(){
    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user=intent.getParcelableExtra<PlacesItem>(EXTRA_DETAIL) as PlacesItem

        with(binding){
            tvDetailNamePlace.text=user.name
            description.text=user.description
        }
    }
    companion object{
        const val EXTRA_DETAIL="extra_detail"

    }
}