package com.latihan.latihansplashscreen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketFragment : Fragment() {

    private lateinit var etNama: TextInputEditText
    private lateinit var etJumlah: TextInputEditText
    private lateinit var btnPesan: Button
    private lateinit var btnBack: Button
    private lateinit var layoutSelection: View
    private lateinit var cardForm: View
    private lateinit var formIcon: ImageView
    private lateinit var tvFormTitle: TextView
    private lateinit var tvSubtitle: TextView
    
    private lateinit var databaseHelper: DatabaseHelper
    private var selectedTicketType: String = ""

    companion object {
        const val CHANNEL_ID = "ticket_channel"
        const val PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        
        // Views
        etNama = view.findViewById(R.id.et_nama_pemesan)
        etJumlah = view.findViewById(R.id.et_jumlah_tiket)
        btnPesan = view.findViewById(R.id.btn_pesan_tiket)
        btnBack = view.findViewById(R.id.btn_back_selection)
        layoutSelection = view.findViewById(R.id.layout_selection)
        cardForm = view.findViewById(R.id.card_form)
        formIcon = view.findViewById(R.id.form_icon)
        tvFormTitle = view.findViewById(R.id.tv_form_title)
        tvSubtitle = view.findViewById(R.id.tv_subtitle)

        createNotificationChannel()
        setupSelectionListeners(view)

        btnPesan.setOnClickListener {
            handlePesanTiket()
        }
        
        btnBack.setOnClickListener {
            showSelection(true)
        }
    }

    private fun setupSelectionListeners(view: View) {
        view.findViewById<View>(R.id.btn_ticket_plane).setOnClickListener {
            showForm("Pesawat", R.drawable.ic_ticket_airplane)
        }
        view.findViewById<View>(R.id.btn_ticket_train).setOnClickListener {
            showForm("Kereta Api", R.drawable.ic_ticket_train)
        }
        view.findViewById<View>(R.id.btn_ticket_bus).setOnClickListener {
            showForm("Bus Travel", R.drawable.ic_ticket_bus)
        }
        view.findViewById<View>(R.id.btn_ticket_tourism).setOnClickListener {
            showForm("Wisata", R.drawable.ic_ticket_tourism)
        }
        view.findViewById<View>(R.id.btn_ticket_hotel).setOnClickListener {
            showForm("Hotel", R.drawable.ic_ticket_hotel)
        }
    }

    private fun showForm(type: String, iconRes: Int) {
        selectedTicketType = type
        tvFormTitle.text = "Pesan Tiket $type"
        formIcon.setImageResource(iconRes)
        tvSubtitle.text = "Silahkan isi detail pesanan $type"
        
        showSelection(false)
    }

    private fun showSelection(show: Boolean) {
        if (show) {
            layoutSelection.visibility = View.VISIBLE
            cardForm.visibility = View.GONE
            tvSubtitle.text = "Pilih jenis tiket perjalananmu"
        } else {
            layoutSelection.visibility = View.GONE
            cardForm.visibility = View.VISIBLE
        }
    }

    private fun handlePesanTiket() {
        val nama = etNama.text.toString().trim()
        val jumlahStr = etJumlah.text.toString().trim()

        if (nama.isEmpty()) {
            etNama.error = "Nama tidak boleh kosong"
            return
        }

        if (jumlahStr.isEmpty()) {
            etJumlah.error = "Jumlah tidak boleh kosong"
            return
        }

        val jumlah = jumlahStr.toIntOrNull()
        if (jumlah == null || jumlah <= 0) {
            etJumlah.error = "Jumlah tidak valid"
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
                return
            }
        }

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Sedang memproses pesanan $selectedTicketType...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
            simpanKeDatabase(nama, jumlah)
        }, 1500)
    }

    private fun simpanKeDatabase(nama: String, jumlah: Int) {
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        // Prefix with category for generic storage or update DB schema if needed. 
        // For now, let's just use the name column or similar.
        val categoryPrefix = "[$selectedTicketType] "
        val result = databaseHelper.tambahPesananTiket(categoryPrefix + nama, jumlah, currentDate)

        if (result != -1L) {
            showNotification(nama, jumlah)
            resetForm()
            showSelection(true) // Return to selection
        } else {
            Toast.makeText(requireContext(), "Gagal menyimpan pesanan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNotification(nama: String, jumlah: Int) {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("show_dialog", true)
            putExtra("nama_pemesan", "($selectedTicketType) $nama")
            putExtra("jumlah_tiket", jumlah)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_ticket)
            .setContentTitle("Pesanan $selectedTicketType Berhasil")
            .setContentText("Pesanan atas nama $nama berhasil dipesan")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            with(NotificationManagerCompat.from(requireContext())) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        } catch (e: SecurityException) {
            Log.e("TicketFragment", "No notify permission")
        }

        Toast.makeText(requireContext(), "Berhasil memesan $selectedTicketType!", Toast.LENGTH_LONG).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ticket Channel"
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun resetForm() {
        etNama.text?.clear()
        etJumlah.text?.clear()
        etNama.clearFocus()
        etJumlah.clearFocus()
    }
}
