package com.example.tujutuju.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tujutuju.R
import com.example.tujutuju.adapter.PlaceAdapter
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.data.response.SearchItem
import com.example.tujutuju.databinding.ActivityMainBinding
import com.example.tujutuju.ui.ViewModelFactory
import com.example.tujutuju.ui.login.LoginActivity
import com.example.tujutuju.ui.profile.ProfileActivity
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
        val layoutManager = LinearLayoutManager(this)
        binding.rvPlace.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvPlace.addItemDecoration(itemDecoration)
        setupViewModel()

        val search = binding.searchView
        val searchManager=getSystemService(Context.SEARCH_SERVICE)  as SearchManager
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearch(query)
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }
        viewModel.place.observe(this){
            setSearchData(it)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.bottom_nav_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_profile ->{
                val intent=Intent(this@MainActivity,ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.EXTRA_DATA,user)
                startActivity(intent)
                finish()
            }
            R.id.logout -> {
                viewModel.logout()
                finish()
            }
            R.id.navigation_favourite -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setSearchData(data:List<SearchItem>){
        val  listPlace =ArrayList<SearchItem>()
        for (id in data){
            listPlace.add(id)
        }
        val adapter = PlaceAdapter(listPlace)
        binding.rvPlace.adapter=adapter

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    companion object{
        const val CAMERA_X_RESULT = 200
        const val EXTRA_USER="extra_user"
    }
}