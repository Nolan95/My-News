package com.example.mynews

import android.content.BroadcastReceiver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.mynews.services.UploadImageFirebaseService
import com.example.mynews.utils.*
import kotlinx.android.synthetic.main.activity_camera.*
import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.IntentFilter
import android.text.TextUtils
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    var currentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.camera)

        upload.isEnabled = false

        take.setOnClickListener {
            launchCamera()
            val uploadBroadcastReceiver = UploadBroadcastReceiver()
            val filter = IntentFilter(ACTION)
            LocalBroadcastManager.getInstance(this).registerReceiver(uploadBroadcastReceiver, filter)
        }
        upload.setOnClickListener {

            startService(Intent(this, UploadImageFirebaseService::class.java).putExtra(IMG_LINK, currentPhotoPath))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageView.setImageURI(currentPhotoPath.createUri()!!)
            upload.isEnabled = true
        }
    }

    private fun launchCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, "Error When creating file", Toast.LENGTH_LONG).show()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.mynews.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }

            }
        }
    }

    //Create and return File name and his absolute path
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class UploadBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val resultCode = intent.getIntExtra("result_code", RESULT_CANCELED)
            Log.i("Broadcast", "I am here")
            if (resultCode == RESULT_OK) {
                Toast.makeText(context, "Upload terminé", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
