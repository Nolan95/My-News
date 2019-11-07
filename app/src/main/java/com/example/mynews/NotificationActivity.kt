package com.example.mynews

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import androidx.work.*
import com.example.mynews.utils.*
import com.example.mynews.workmanager.NewsFetchingWorker
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.query
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.notifications)

    }

    private fun handleClickListener(allCheckboxId: List<CheckBox>){

       if(switch3.isChecked){
           val checklist = getSelectedCheckbox(allCheckboxId)
           if(!query.text.toString().equals("")){
               if(checklist.isNotEmpty()){
                   val constraints = Constraints.Builder()
                       .setRequiredNetworkType(NetworkType.CONNECTED)
                       .build()
                   val query = workDataOf(QUERY to query.text)
                   val fq = workDataOf(FQ to checklist)

                   // Create periodic worker with contraints & 12 hours interval
                   val newsFetchingWorker = PeriodicWorkRequest.Builder(
                       NewsFetchingWorker::class.java, 12, TimeUnit.HOURS)
                       .setConstraints(constraints)
                       .setInputData(query)
                       .setInputData(fq)
                       .build()

                   WorkManager.getInstance().enqueue(newsFetchingWorker)
               }else{
                   Toast.makeText(this, "Select at least one category", Toast.LENGTH_LONG).show()
               }
           }else{
               Toast.makeText(this, "Query term can't be blank", Toast.LENGTH_LONG).show()
           }
       }


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
