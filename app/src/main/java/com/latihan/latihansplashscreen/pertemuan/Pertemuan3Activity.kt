package com.latihan.latihansplashscreen.pertemuan

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.latihan.latihansplashscreen.R

class Pertemuan3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pertemuan3)

        supportActionBar?.title = "Pertemuan 3: Widget Dasar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.btn_standard).setOnClickListener {
            Toast.makeText(this, "Standard Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btn_image).setOnClickListener {
            Toast.makeText(this, "Image Button Clicked!", Toast.LENGTH_SHORT).show()
        }

        val toggle = findViewById<ToggleButton>(R.id.btn_toggle)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            Toast.makeText(this, "Toggle status: $status", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
