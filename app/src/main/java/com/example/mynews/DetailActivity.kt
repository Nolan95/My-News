package com.example.mynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.mynews.utils.URL
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url = intent.getStringExtra(URL)
//        val encodedHtml = Base64.encodeToString(url.toByteArray(), Base64.NO_PADDING)
//        webview.loadData(encodedHtml, "text/html", "base64")
        //val webSettings = webview.getSettings()
        //webSettings.setJavaScriptEnabled(true)
        webview.loadUrl(url)
    }
}
