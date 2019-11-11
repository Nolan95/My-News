package com.example.mynews

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import androidx.work.*
import com.example.mynews.utils.*
import com.example.mynews.workmanager.NewsFetchingWorker
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import android.widget.CompoundButton
import com.example.mynews.extensions.fromArrayToString


class NotificationActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.notifications)

        sharedPreferences = getSharedPreferences(NOTIF, Context.MODE_PRIVATE)

        val allCheckboxId = listOf<CheckBox>(art, business, entrepreneurs,politics, sports,travel)

        handleClickListener(allCheckboxId)
    }

    private fun handleClickListener(allCheckboxId: List<CheckBox>){

        switch3.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {buttonView, isChecked ->
           if(isChecked){
               val checklist = getSelectedCheckbox(allCheckboxId)
               if(!query.text.toString().equals("")){
                   if(checklist.isNotEmpty()){
                       val constraints = Constraints.Builder()
                           .setRequiredNetworkType(NetworkType.CONNECTED)
                           .build()

                       sharedPreferences.edit().apply{
                            putString(QUERY, query.text.toString())
                            putString(FQ, checklist.fromArrayToString())
                       }.apply()

                       // Create periodic worker with contraints & 12 hours interval
                       val newsFetchingWorker = PeriodicWorkRequest.Builder(
                           NewsFetchingWorker::class.java, 2, TimeUnit.MINUTES)
                           .setConstraints(constraints)
                           .build()

                       WorkManager.getInstance().enqueue(newsFetchingWorker)
                   }else{
                       Toast.makeText(this, "Select at least one category", Toast.LENGTH_LONG).show()
                   }
               }else{
                   Toast.makeText(this, "Query term can't be blank", Toast.LENGTH_LONG).show()
               }
           }

       })

    }

    private fun getSelectedCheckbox(allCheckboxId: List<CheckBox>): ArrayList<String>{
        val selectedCheckbox = ArrayList<String>()
        for(idCheckbox: CheckBox in allCheckboxId){
            if(idCheckbox.isChecked){
                selectedCheckbox.add(idCheckbox.text.toString())
            }
        }

        return selectedCheckbox
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
