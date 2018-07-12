package com.example.jayghodasara.calendar

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.text.format.DateFormat.getTimeFormat
import android.text.format.DateFormat.getDateFormat
import android.util.Log
import kotlin.collections.ArrayList

import java.text.ParseException
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list: ArrayList<CalendarPojo> = ArrayList()
        var permissioncheck: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR)
        var permissioncheck2: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR)
        if (permissioncheck2 != PackageManager.PERMISSION_GRANTED && permissioncheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR), 1)
        } else {
            Toast.makeText(applicationContext, " Permission granted", Toast.LENGTH_LONG).show()
        }

        var COLS = arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART)

        var cursor: Cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, COLS, null, null, null)

        get_Events.setOnClickListener(View.OnClickListener {
            var laymam: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            if (!cursor.isClosed) {


                val df = DateFormat.getDateFormat(this)
                val tf = DateFormat.getTimeFormat(this)

                while (cursor.moveToNext()) {


                    var title = cursor.getString(0)
                    var start: Long = cursor.getLong(1)
                    Log.i("fetched", start.toString())
                    var calendar: CalendarPojo = CalendarPojo(title, df.format(start) + " at " + tf.format(start))
                    list.add(calendar)
                }

                cursor.close()

                recycle.layoutManager = laymam
                recycle.adapter = adapter(this, list)
            } else {
                Toast.makeText(applicationContext, "Already Loaded", Toast.LENGTH_LONG).show()
            }
        })

        add_event.setOnClickListener(View.OnClickListener {

            var intent: Intent = Intent(this, AddEvent::class.java)
            startActivity(intent)
        })

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(applicationContext, "All Permissions Granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, " Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
