package com.example.mynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mynews.utils.MOSTPOPULAR
import com.example.mynews.utils.TOPSTORIES
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(getSupportFragmentManager())

        var firstFragmet: TopStoriesFragment = TopStoriesFragment.newInstance("First Fragment")
        var secondFragmet: TopStoriesFragment = TopStoriesFragment.newInstance("Second Fragment")

        adapter.addFragment(firstFragmet, TOPSTORIES)
        adapter.addFragment(secondFragmet, MOSTPOPULAR)

        viewpager.adapter = adapter

        sliding_tabs.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.app_bar_search -> {
                startActivity(Intent())
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
