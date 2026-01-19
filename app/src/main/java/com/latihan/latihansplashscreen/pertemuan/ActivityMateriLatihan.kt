package com.latihan.latihansplashscreen.pertemuan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.latihan.latihansplashscreen.R

class ActivityMateriLatihan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi_latihan)

        findViewById<MaterialButton>(R.id.btn_p1).setOnClickListener {
            startActivity(Intent(this, Pertemuan1Activity::class.java))
        }

        findViewById<MaterialButton>(R.id.btn_p2).setOnClickListener {
            startActivity(Intent(this, Pertemuan2Activity::class.java))
        }

        findViewById<MaterialButton>(R.id.btn_p3).setOnClickListener {
            startActivity(Intent(this, Pertemuan3Activity::class.java))
        }

        findViewById<MaterialButton>(R.id.btn_p4).setOnClickListener {
            startActivity(Intent(this, Pertemuan4Activity::class.java))
        }
        
        findViewById<MaterialButton>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
}
