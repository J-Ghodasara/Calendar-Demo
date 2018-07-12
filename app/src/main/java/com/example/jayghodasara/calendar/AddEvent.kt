package com.example.jayghodasara.calendar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.ParseException
import java.text.SimpleDateFormat
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.ContentValues
import android.net.Uri
import android.widget.Toast
import java.util.*


class AddEvent : AppCompatActivity() {

    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var mHour: Int = 0
    var mMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        startdate.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> startdate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)
            datePickerDialog.show()

        })

        enddate.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> enddate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)
            datePickerDialog.show()

        })

        add.setOnClickListener(View.OnClickListener {

            var calendartitle:String = calendar_title.text.toString()
            var desc:String = description.text.toString()
            var location:String = location.text.toString()
            var startdate:String = startdate.text.toString()
            var enddate:String = enddate.text.toString()
            var startdate_ms:Long?=null
            var enddate_ms:Long?=null
            var eventID: Long = -1

            val df = DateFormat.getDateFormat(this)
            val f = SimpleDateFormat("dd-MM-yyyy")
            try {
                val d = f.parse(startdate)
                val dd= f.parse(enddate)
               startdate_ms  = d.time
                enddate_ms = dd.time

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            try{

                var eventUriString:String = "content://com.android.calendar/events"

                var eventvalues:ContentValues= ContentValues()
                eventvalues.put("calendar_id",1)
                eventvalues.put("title",calendartitle)
                eventvalues.put("description",desc)
                eventvalues.put("eventLocation",location)
                eventvalues.put("dtstart",startdate_ms)
                eventvalues.put("dtend",enddate_ms?.plus(600000))
                eventvalues.put("eventStatus",0)
                eventvalues.put("eventTimezone","UTC/GMT +5:30")
                eventvalues.put("hasAlarm",0)

                val eventUri = this.applicationContext
                        .contentResolver
                        .insert(Uri.parse(eventUriString), eventvalues)

                Toast.makeText(this,"Event Saved",Toast.LENGTH_LONG).show()
                eventID = java.lang.Long.parseLong(eventUri.lastPathSegment)

            }catch (e:Exception){
                e.printStackTrace()
            }




        })
    }
}
