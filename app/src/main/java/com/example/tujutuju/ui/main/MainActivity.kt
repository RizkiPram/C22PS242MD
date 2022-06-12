package com.example.tujutuju.ui.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.tujutuju.R
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.databinding.ActivityMainBinding
import com.example.tujutuju.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user= intent.getParcelableExtra(EXTRA_USER)!!

        setupViewModel()
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.bottom_nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_profile ->{

            }
            R.id.logout -> {
                viewModel.logout()
                finish()
            }
            R.id.navigation_favourite -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        const val EXTRA_USER="extra_user"
    }
}