package com.example.tujutuju.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user:UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user=intent.getParcelableExtra(EXTRA_DATA)!!

        with(binding){
            tvProfileName.text = user.name
            tvProfileEmail.text=user.email
            tvProfileHandphone.text=user.phone
        }
    }
    companion object{
        const val EXTRA_DATA="extra_data"
    }
}