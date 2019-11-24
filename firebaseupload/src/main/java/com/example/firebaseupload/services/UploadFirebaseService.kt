package com.example.firebaseupload.services

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.storage.FirebaseStorage
import id.zelory.compressor.Compressor
import java.io.File

val IMG_LINK = "IMG_LINK"
val ACTION = "import com.example.mynews.services.UploadImageFirebaseService"

class UploadFirebaseService : IntentService("UploadFirebaseService") {
    override fun onHandleIntent(intent: Intent?) {
        val link = intent?.getStringExtra(IMG_LINK)

        val filename = link?.substring(link.lastIndexOf("/")+1)?.split(".")?.first()



        val f = File(link)

        uploadToFirebaseStorage(f, filename)

        Log.i("UploadImageFirebase", "$link")
    }

    private fun uploadToFirebaseStorage(f: File, filename: String?) {

        val f = Compressor(this).compressToFile(f)

        val uri = Uri.fromFile(f)

        val storage = FirebaseStorage.getInstance()

        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to 'images/mountains.jpg'
        val fileRef = storageRef.child("images/$filename.jpg")

        var uploadTask = fileRef.putFile(uri)
        uploadTask.addOnFailureListener {
            Log.w("OnFailure", "Bad thing")
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            val intent = Intent(ACTION)
            intent.putExtra("result_code", Activity.RESULT_OK)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            Log.i("OnSuccess", "Good thing from another module")
        }.addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
            val intent = Intent(ACTION)
            intent.putExtra("progress", progress)
            sendBroadcast(intent)
            Log.i("OnProgress", "Upload is $progress% done")
        }

    }
}