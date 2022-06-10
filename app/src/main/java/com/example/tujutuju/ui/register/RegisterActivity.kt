package com.example.tujutuju.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tujutuju.databinding.ActivityRegisterBinding
import com.example.tujutuju.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.isLoading.observe(this) {
            showLoading(it)
            moveToLogin(it)
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        setupButton()
    }
    private fun setupButton() {
        with(binding) {
            myBtnRegister.setOnClickListener {
                val name = myEtNameRegister.text.toString()
                val email = myEtEmailRegister.text.toString()
                val password = myEtPasswordRegister.text.toString()
                viewModel.register(name, email, password)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun moveToLogin(value: Boolean) {
        if (value) {
            return
        } else {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}