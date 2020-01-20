package com.example.mynews

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.mynews.extensions.fromArrayToString
import com.example.mynews.utils.FQ
import com.example.mynews.utils.NOTIF
import com.example.mynews.utils.QUERY
import com.example.mynews.workmanager.NewsFetchingWorker
import com.example.mynews.workmanager.ReminderWorker
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.toolbar.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


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

                      ReminderWorker.runAt()

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


    fun getDuration(): Long {
        val dat = Date()//initializes to now
        Log.d("Date", "$dat")

        val calAlarm = Calendar.getInstance()
        Log.d("calAlarm", "$calAlarm")

        val calNow = Calendar.getInstance()
        Log.d("calNow", "$calNow")

        calNow.time = dat
        Log.d("calNow with date", "$calNow")

        calAlarm.time = dat
        Log.d("calAlarm with date", "$calAlarm")

        calAlarm.set(Calendar.HOUR_OF_DAY, 13)//set the alarm time
        calAlarm.set(Calendar.MINUTE, 30)
        Log.d("calAlarm with set", "$calAlarm")

        if (calAlarm.before(calNow)) {//if its in the past increment
            calAlarm.add(Calendar.DATE, 1)
        }

        return calAlarm.timeInMillis - calNow.timeInMillis

    }
}
