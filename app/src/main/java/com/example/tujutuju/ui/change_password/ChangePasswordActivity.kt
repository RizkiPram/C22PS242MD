package com.example.tujutuju.ui.change_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.databinding.ActivityChangePasswordBinding
import com.example.tujutuju.ui.profile.ProfileActivity


class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private  val viewModel: ChangePasswordViewModel by viewModels()
    private lateinit var user:UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user=intent.getParcelableExtra(EXTRA_TOKEN)!!

        setupViewModel()
        setupButton()
    }
    private fun setupViewModel(){
        viewModel.isLoading.observe(this){
            showLoading(it)
            moveToMainActivity(it)
        }
        viewModel.message.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
    private fun setupButton(){
        with(binding){
            btnChangePassword.setOnClickListener {
                val oldPassword=etOldPassword.text.toString()
                val newPassword=etNewPassword.text.toString()
                viewModel.changePassword(user.token,oldPassword, newPassword)

            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun moveToMainActivity(value:Boolean){
        if (value){
            return
        }else{
            val intent=Intent(this@ChangePasswordActivity,ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_DATA,user)
            startActivity(intent)
            finish()
        }
    }
    companion object{
        const val EXTRA_TOKEN = "extra_token"
    }
}