package com.example.mynews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import com.example.mynews.services.UploadImageFirebaseService
import com.example.mynews.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {

    lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }


        val adapter = TabAdapter(getSupportFragmentManager())


        var firstFragmet: TopStoriesFragment = TopStoriesFragment.newInstance(TOPSTORIES)
        var secondFragmet: TopStoriesFragment = TopStoriesFragment.newInstance(MOSTPOPULAR)
        var thirdFragment: TopStoriesFragment = TopStoriesFragment.newInstance(BUSINESS)

        adapter.addFragment(firstFragmet, TOPSTORIES)
        adapter.addFragment(secondFragmet, MOSTPOPULAR)
        adapter.addFragment(thirdFragment, BUSINESS)

        viewpager.offscreenPageLimit = 3

        viewpager.adapter = adapter

        sliding_tabs.setupWithViewPager(viewpager)

        nav_view.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked = true

            when(menuItem.itemId){
                R.id.notif -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                }
                R.id.help -> {
                    Toast.makeText(this, "Wallet", Toast.LENGTH_LONG).show()
                }
                R.id.about -> {
                    Toast.makeText(this, "Offer", Toast.LENGTH_LONG).show()
                }
                R.id.camera -> {
                    startActivity(Intent(this, CameraActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Instance", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d("TokenMain", msg)
            })

        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.setConfigSettingsAsync(configSettings)

        fecthFromFirebaseRemoteConfig()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return when(id){
            R.id.app_bar_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }

            R.id.notif -> {
                startActivity(Intent(this, NotificationActivity::class.java))
                true
            }

            R.id.help -> {
                true
            }

            R.id.about -> {
                true
            }
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fecthFromFirebaseRemoteConfig(){
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("Updated", "Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }

                displayWelcomeMessage()
            }
    }

    private fun displayWelcomeMessage() {
        val menuNav = nav_view.menu
        val nav_item2 = menuNav.findItem(R.id.camera)
        Toast.makeText(this, "${remoteConfig.getBoolean("camera")}", Toast.LENGTH_LONG).show()
        nav_item2.setEnabled(remoteConfig.getBoolean("camera"))
    }


    override fun onPrepareOptionsMenu (menu: Menu): Boolean {
        if (isFinishing) {
            menu.getItem(1).setEnabled(false);
            // You can also use something like:
            // menu.findItem(R.id.example_foobar).setEnabled(false);
        }
        return true
    }
}
