package com.samuelhallman.goldenshire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String
)

class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        val db = AppDatabase.getInstance(this)
        val noteDao = db.noteDao()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Notes"
            setBackgroundDrawable(resources.getDrawable(R.color.green, theme))
        }

        val list = findViewById<ListView>(R.id.yourListView)
        val input = findViewById<EditText>(R.id.addItemName)
        val addInput = findViewById<Button>(R.id.addInput)

        val items = noteDao.getAll().toMutableList()

        val adapter = object : ArrayAdapter<Note>(this, R.layout.list_item, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.list_item, parent, false)

                val itemText = view.findViewById<TextView>(R.id.itemText)
                val removeButton = view.findViewById<Button>(R.id.removeButton)

                val note = items[position]
                itemText.text = note.text

                removeButton.setOnClickListener {
                    noteDao.delete(note)
                    items.removeAt(position)
                    notifyDataSetChanged()
                }

                return view
            }
        }

        list.adapter = adapter

        addInput.setOnClickListener {
            val text = input.text.toString().trim()
            if (text.isNotEmpty()) {
                val note = Note(text = text)
                noteDao.insert(note)
                items.add(0, note)
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