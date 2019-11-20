package com.example.mynews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.animation.AnimationUtils
import com.example.mynews.utils.*
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.floating_buttons.*
import android.widget.Toast




class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url = intent.getStringExtra(URL)
        val titleNew = intent.getStringExtra(TITLE)
        title = titleNew
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    webview.loadUrl(deepLink.toString())
                }else{
                    webview.loadUrl(url)
                }

            }
            .addOnFailureListener(this) { e -> Log.w("Dynamic Link", "getDynamicLink:onFailure", e) }

        fab_main.setOnClickListener{
            shortenLink(url)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDynamicUri(articleLink: String): Uri {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(articleLink))
            .setDomainUriPrefix(DYNAMIC_LINK_DOMAIN)
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .buildDynamicLink()
        return dynamicLink.uri
    }

    private fun shortenLink(articleLink: String) {
        val linkUri = createDynamicUri(articleLink)
        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLongLink(linkUri)
            .buildShortDynamicLink()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val shortLink = task.result?.shortLink
                    val msg = "Hey, check out this post: $shortLink"
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
                    sendIntent.type = "text/plain"
                    try {
                        startActivity(Intent.createChooser(sendIntent, "Share this post"))
                    } catch (ex: android.content.ActivityNotFoundException) {
                        Toast.makeText(
                            this,
                            "There are no applications installed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.w("Exception", task.exception)
                }
            }
    }
}
