package com.example.mynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mynews.utils.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        val adapter = TabAdapter(getSupportFragmentManager())

        var firstFragmet: TopStoriesFragment = TopStoriesFragment.newInstance(TOPSTORIES)
        var secondFragmet: TopStoriesFragment = TopStoriesFragment.newInstance(MOSTPOPULAR)
        var thirdFragment: TopStoriesFragment = TopStoriesFragment.newInstance(BUSINESS)

        adapter.addFragment(firstFragmet, TOPSTORIES)
        adapter.addFragment(secondFragmet, MOSTPOPULAR)
        adapter.addFragment(thirdFragment, BUSINESS)

        viewpager.adapter = adapter

        sliding_tabs.setupWithViewPager(viewpager)
        sliding_tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.app_bar_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }

            R.id.notif -> {

            }

            R.id.help -> {

            }

            R.id.about -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
