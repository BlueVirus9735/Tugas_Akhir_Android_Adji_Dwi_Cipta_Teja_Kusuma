package com.latihan.latihansplashscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.latihan.latihansplashscreen.pertemuan.Pertemuan1Activity
import com.latihan.latihansplashscreen.pertemuan.Pertemuan2Activity
import com.latihan.latihansplashscreen.pertemuan.Pertemuan3Activity
import com.latihan.latihansplashscreen.pertemuan.Pertemuan4Activity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var etPanjang: TextInputEditText
    private lateinit var etLebar: TextInputEditText
    private lateinit var btnHitung: Button
    private lateinit var tvHasil: TextView
    private lateinit var cardCalculator: View
    private lateinit var sessionManager: SessionManager
    
    // Header Greeting Views
    private lateinit var tvGreetingTime: TextView
    private lateinit var tvGreetingName: TextView
    
    // Feature Buttons
    private lateinit var btnMaps: View
    private lateinit var btnTicket: View
    private lateinit var btnCalculator: View
    private lateinit var btnMateri: View

    // Weather Views
    private lateinit var spinnerCity: Spinner
    private lateinit var tvTemp: TextView
    private lateinit var tvDesc: TextView
    private lateinit var pbWeather: ProgressBar
    private lateinit var btnRefreshWeather: ImageView

    private val weatherCities = listOf(
        City("Cirebon", -6.7063, 108.5570),
        City("Jakarta", -6.2088, 106.8456),
        City("Surabaya", -7.2575, 112.7521),
        City("Bandung", -6.9175, 107.6191),
        City("Bekasi", -6.2383, 106.9756),
        City("Tangerang", -6.1783, 106.6319),
        City("Tegal", -6.8676, 109.1370),
        City("Semarang", -6.9667, 110.4167),
        City("Tuban", -6.8976, 112.0640),
        City("Malang", -7.9839, 112.6214),
        City("Madiun", -7.6298, 111.5239)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Calculator Views
        etPanjang = view.findViewById(R.id.et_panjang)
        etLebar = view.findViewById(R.id.et_lebar)
        btnHitung = view.findViewById(R.id.btn_hitung)
        tvHasil = view.findViewById(R.id.tv_hasil)
        cardCalculator = view.findViewById(R.id.card_calculator)

        // Initialize Feature Buttons
        btnMaps = view.findViewById(R.id.btn_feature_maps)
        btnTicket = view.findViewById(R.id.btn_feature_ticket)
        btnCalculator = view.findViewById(R.id.btn_feature_calc)
        btnMateri = view.findViewById(R.id.btn_feature_materi)

        // Initialize Weather Views
        spinnerCity = view.findViewById(R.id.spinner_weather_city)
        tvTemp = view.findViewById(R.id.tv_temp_home)
        tvDesc = view.findViewById(R.id.tv_desc_home)
        pbWeather = view.findViewById(R.id.pb_weather_home)
        btnRefreshWeather = view.findViewById(R.id.btn_refresh_weather)
        
        // Header Initialization
        tvGreetingTime = view.findViewById(R.id.tv_greeting_time)
        tvGreetingName = view.findViewById(R.id.tv_greeting_name)
        sessionManager = SessionManager(requireContext())

        updateGreeting()
        setupWeatherUI()
        setupListeners()
    }

    private fun setupWeatherUI() {
        val cityNames = weatherCities.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cityNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val city = weatherCities[position]
                fetchWeather(city.lat, city.lon)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun setupListeners() {
        // Calculator Logic
        btnHitung.setOnClickListener {
            hitungLuas()
        }
        
        // Feature Logic
        btnCalculator.setOnClickListener {
            if (cardCalculator.visibility == View.VISIBLE) {
                cardCalculator.visibility = View.GONE
            } else {
                cardCalculator.visibility = View.VISIBLE
            }
        }
        
        btnMaps.setOnClickListener {
            (activity as? MainActivity)?.let { mainActivity ->
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MapsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        
        btnTicket.setOnClickListener {
             (activity as? MainActivity)?.let { mainActivity ->
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TicketFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        btnMateri.setOnClickListener {
            showMateriMenu(it)
        }

        btnRefreshWeather.setOnClickListener {
            val city = weatherCities[spinnerCity.selectedItemPosition]
            fetchWeather(city.lat, city.lon)
        }
    }

    private fun updateGreeting() {
        val username = sessionManager.getUsername()
        tvGreetingName.text = if (username.isNotEmpty()) username else "Pengguna"
        
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val greetingTime = when (hour) {
            in 0..11 -> "Selamat Pagi,"
            in 12..15 -> "Selamat Siang,"
            in 16..18 -> "Selamat Sore,"
            else -> "Selamat Malam,"
        }
        tvGreetingTime.text = greetingTime
        
        // Bonus: Simple fade in animation for header
        tvGreetingTime.alpha = 0f
        tvGreetingName.alpha = 0f
        tvGreetingTime.animate().alpha(1f).setDuration(800).start()
        tvGreetingName.animate().alpha(1f).setDuration(800).setStartDelay(200).start()
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        pbWeather.visibility = View.VISIBLE
        tvTemp.animate().alpha(0.5f).setDuration(200).start()
        
        RetrofitClient.weatherApiService.getCurrentWeather(lat, lon)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (!isAdded) return
                    pbWeather.visibility = View.GONE
                    tvTemp.animate().alpha(1f).setDuration(200).start()
                    
                    if (response.isSuccessful) {
                        val weather = response.body()
                        weather?.let {
                            tvTemp.text = "${it.current.temperature}°C"
                            tvDesc.text = "Kelembaban: ${it.current.humidity}% | Angin: ${it.current.windSpeed}km/h"
                        }
                    } else {
                        tvTemp.text = "Error"
                        tvDesc.text = "Gagal memuat data cuaca"
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    if (!isAdded) return
                    pbWeather.visibility = View.GONE
                    tvTemp.animate().alpha(1f).setDuration(200).start()
                    tvTemp.text = "Off"
                    tvDesc.text = "Pastikan koneksi internet aktif"
                    Log.e("HomeFragment", "Weather error: ${t.message}")
                }
            })
    }

    private fun showMateriMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menu.add(0, 1, 0, "Materi 1")
        popup.menu.add(0, 2, 0, "Materi 2")
        popup.menu.add(0, 3, 0, "Materi 3")
        popup.menu.add(0, 4, 0, "Materi 4")

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> startActivity(Intent(context, Pertemuan1Activity::class.java))
                2 -> startActivity(Intent(context, Pertemuan2Activity::class.java))
                3 -> startActivity(Intent(context, Pertemuan3Activity::class.java))
                4 -> startActivity(Intent(context, Pertemuan4Activity::class.java))
            }
            true
        }
        popup.show()
    }
    
    private fun hitungLuas() {
        val panjangStr = etPanjang.text.toString()
        val lebarStr = etLebar.text.toString()

        if (panjangStr.isEmpty()) {
            etPanjang.error = "Panjang tidak boleh kosong"
            etPanjang.requestFocus()
            return
        }
        
        if (lebarStr.isEmpty()) {
            etLebar.error = "Lebar tidak boleh kosong"
            etLebar.requestFocus()
            return
        }

        val panjang = panjangStr.toDoubleOrNull()
        val lebar = lebarStr.toDoubleOrNull()
        
        if (panjang == null || lebar == null) {
            tvHasil.text = "Error"
            return
        }
        
        val luas = panjang * lebar

        val luasFormatted = if (luas % 1.0 == 0.0) {
            String.format("%.0f", luas)
        } else {
            String.format("%.2f", luas)
        }
        
        tvHasil.text = "$luasFormatted cm²"
    }
}
