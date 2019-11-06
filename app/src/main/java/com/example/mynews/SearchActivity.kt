package com.example.mynews

import android.app.ActionBar
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.mynews.utils.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() {
    private lateinit var picker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = findViewById<TextView>(R.id.query)

        toolBarConfig()

        title  = getString(R.string.search_articles)

        val allCheckboxId = listOf<CheckBox>(art, business, entrepreneurs,politics, sports,travel)

        handleClickListener(allCheckboxId)

        Log.i("date", "${end_date.text}")
    }



    private fun toolBarConfig() {
        //Toolbar configuration
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun handleClickListener(allCheckboxId: List<CheckBox>){
        begin_date.inputType = InputType.TYPE_NULL
        end_date.inputType = InputType.TYPE_NULL
        begin_date.setOnClickListener{
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    begin_date.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                }, year, month, day)

            picker.show()
        }

        end_date.setOnClickListener{
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->

                    end_date.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                }, year, month, day)

            picker.show()
        }

        search.setOnClickListener{
            val checklist = getSelectedCheckbox(allCheckboxId)
            if(!query.text.toString().equals("")){
                if(checklist.isNotEmpty()){
                    val intent = Intent(this, SearchResultActivity::class.java)
                    intent.putExtra(QUERY, query.text.toString())
                    intent.putExtra(FQ, checklist)
                    intent.putExtra(END_DATE, formatDate(end_date.text.toString()))
                    intent.putExtra(BEGIN_DATE, formatDate(begin_date.text.toString()))
                    Log.i("date", "${begin_date.text}")
                    startActivity(intent)
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
