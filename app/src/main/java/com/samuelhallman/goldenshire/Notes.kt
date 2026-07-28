package com.samuelhallman.goldenshire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)


        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Notes"
            setBackgroundDrawable(resources.getDrawable(R.color.green, theme))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}