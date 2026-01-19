package com.latihan.latihansplashscreen.pertemuan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.latihan.latihansplashscreen.R

class Pertemuan1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pertemuan1)
        
        supportActionBar?.title = "Pertemuan 1"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
