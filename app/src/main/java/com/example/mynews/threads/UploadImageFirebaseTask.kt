package com.example.mynews.threads

import android.os.AsyncTask
import android.util.Log

class UploadImageFirebaseTask : AsyncTask<Int, Int, String>() {

    override fun onPreExecute() {

    }

    override fun doInBackground(vararg params: Int?): String {

        val startId = params[0]

        var i = 0
        while (i <= 20) {
            try {
                Thread.sleep(10000)
                publishProgress(startId)
                i++
            }
            catch (e: Exception) {
                return(e.localizedMessage)
            }
        }
        return "Service complete $startId"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val counter = values.get(0)
        Log.i("Task", "Service Running $counter")
    }

    override fun onPostExecute(result: String) {
        Log.i("Task", result)
    }
}