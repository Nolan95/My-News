package com.example.mynews

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
import android.widget.CheckBox
import android.widget.DatePicker
import com.example.mynews.utils.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class SearchActivity : AppCompatActivity() {
    private lateinit var picker: DatePickerDialog
    private var selectedCheckbox = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val allCheckboxId = listOf<CheckBox>(art, business, entrepreneurs,politics, sports,travel)
        getSelectedCheckbox(allCheckboxId)

        handleClickListener()

        Log.i("date", "${end_date.text.toString()}")
    }

    private fun handleClickListener(){
        begin_date.inputType = InputType.TYPE_NULL
        end_date.inputType = InputType.TYPE_NULL
        begin_date.setOnClickListener(){
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this@SearchActivity,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    begin_date.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                }, year, month, day)

            picker.show()
        }

        end_date.setOnClickListener(){
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this@SearchActivity,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->

                    end_date.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                }, year, month, day)

            picker.show()
        }

        search.setOnClickListener(){
            if(!query.text.toString().equals("")){
                if(selectedCheckbox.isNotEmpty()){
                    val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
                    intent.putExtra(QUERY, query.text)
                    intent.putExtra(FQ, selectedCheckbox)
                }
            }
        }
    }

    private fun getSelectedCheckbox(allCheckboxId: List<CheckBox>){
        for(idCheckbox: CheckBox in allCheckboxId){
            if(idCheckbox.isChecked){
                selectedCheckbox.add(idCheckbox.text.toString())
            }
        }
    }


}
