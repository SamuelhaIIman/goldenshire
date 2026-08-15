package com.samuelhallman.goldenshire

import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calendar"

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val selectedDataText = findViewById<TextView>(R.id.selectedDateText)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val format = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
            val formattedDate = format.format(calendar.time)

            selectedDataText.text = "Selected: $formattedDate"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}