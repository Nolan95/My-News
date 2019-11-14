package com.example.mynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mynews.utils.*
import com.example.mynews.workmanager.DbPopulateWorker
import com.example.mynews.workmanager.NewsFetchingWorker
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TabAdapter


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
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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
}
