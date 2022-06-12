package com.example.tujutuju.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.databinding.ActivityProfileBinding
import com.example.tujutuju.ui.change_password.ChangePasswordActivity

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

            myBtnChangePassword.setOnClickListener{
                val intent= Intent(this@ProfileActivity,ChangePasswordActivity::class.java)
                intent.putExtra(ChangePasswordActivity.EXTRA_TOKEN,user)
                startActivity(intent)
                finish()
            }
            btnChangePicture.setOnClickListener {
                val intent = Intent(this@ProfileActivity,ChangePictureActivity::class.java)
                intent.putExtra(ChangePictureActivity.EXTRA_USER,user)
                startActivity(intent)
                finish()
            }
        }
    }
    companion object{
        const val EXTRA_DATA="extra_data"
    }
}