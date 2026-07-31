package com.samuelhallman.goldenshire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView


class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Notes"
            setBackgroundDrawable(resources.getDrawable(R.color.green, theme))
        }

        val list = findViewById<ListView>(R.id.yourListView)
        val input = findViewById<EditText>(R.id.addItemName)
        val addInput = findViewById<Button>(R.id.addInput)

        val items = mutableListOf<String>()
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        list.adapter = adapter

        addInput.setOnClickListener {
            val text = input.text.toString().trim()

            if(text.isNotEmpty()) {
                items.add(text)
                adapter.notifyDataSetChanged()
                input.text.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}