package com.example.tujutuju.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.response.LoginResponse
import com.example.tujutuju.databinding.ActivityChangePictureBinding
import com.example.tujutuju.helper.reduceFileImage
import com.example.tujutuju.helper.rotateBitmap
import com.example.tujutuju.helper.uriToFile
import com.example.tujutuju.ui.camera.CameraActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ChangePictureActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChangePictureBinding
    private lateinit var user: UserModel
    private var getFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangePictureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user=intent.getParcelableExtra(EXTRA_USER)!!

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        with(binding){
            cameraButton.setOnClickListener { startCameraX() }
            galleryButton.setOnClickListener { startGallery() }
            btnUpload.setOnClickListener { upload() }
        }
    }
    private fun upload(){
        if (getFile !=null){
            val file= reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val client=ApiConfig.getApiService().changeProfilePicture("Bearer ${user.token}",imageMultipart)
            client.enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if (response.isSuccessful){
                        val intent = Intent(this@ChangePictureActivity,ProfileActivity::class.java)
                        intent.putExtra(ProfileActivity.EXTRA_DATA,user)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@ChangePictureActivity,response.message(),Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("ChangePictureActivity","onFailure ${t.message}")
                }
            })
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ChangePictureActivity)
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }
    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )
            binding.previewImageView.setImageBitmap(result)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    companion object{
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val EXTRA_USER="extra_user"
    }
}