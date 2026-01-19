package com.latihan.latihansplashscreen.pertemuan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.latihan.latihansplashscreen.R

class Pertemuan2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pertemuan2)

        supportActionBar?.title = "Pertemuan 2: Aritmatika"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etInput1 = findViewById<EditText>(R.id.et_input1)
        val etInput2 = findViewById<EditText>(R.id.et_input2)
        val tvHasil = findViewById<TextView>(R.id.tv_hasil)

        findViewById<Button>(R.id.btn_tambah).setOnClickListener { calculate(etInput1, etInput2, tvHasil, "+") }
        findViewById<Button>(R.id.btn_kurang).setOnClickListener { calculate(etInput1, etInput2, tvHasil, "-") }
        findViewById<Button>(R.id.btn_kali).setOnClickListener { calculate(etInput1, etInput2, tvHasil, "*") }
        findViewById<Button>(R.id.btn_bagi).setOnClickListener { calculate(etInput1, etInput2, tvHasil, "/") }
    }

    private fun calculate(et1: EditText, et2: EditText, tv: TextView, op: String) {
        val s1 = et1.text.toString()
        val s2 = et2.text.toString()

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val n1 = s1.toDouble()
        val n2 = s2.toDouble()
        var res = 0.0

        when (op) {
            "+" -> res = n1 + n2
            "-" -> res = n1 - n2
            "*" -> res = n1 * n2
            "/" -> {
                if (n2 == 0.0) {
                    Toast.makeText(this, "Tidak bisa bagi nol", Toast.LENGTH_SHORT).show()
                    return
                }
                res = n1 / n2
            }
        }
        tv.text = "Hasil: $res"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
