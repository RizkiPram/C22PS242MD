package com.example.tujutuju.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.tujutuju.ui.main.MainActivity
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.databinding.ActivityLoginBinding
import com.example.tujutuju.ui.ViewModelFactory
import com.example.tujutuju.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myBtnMoveToRegister.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupViewModel()
        setupButtonLogin()
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]
        viewModel.getUser().observe(this){
            if (it.isLogin){
                val intent= Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_USER,it)
                startActivity(intent)
                finish()
            }
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }
    }
    private fun setupButtonLogin(){
        with(binding){
            myBtnLogin.setOnClickListener {
                val email=myEtEmailLogin.text.toString()
                val password=myEtPasswordLogin.text.toString()
                viewModel.login(email, password)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}