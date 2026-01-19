# 📱 Proyek Android Ecosystem - Adji Dwi Cipta

> **Koleksi Tugas & Implementasi Fitur Modern Android (Kotlin)**

[![Android SDK](https://img.shields.io/badge/SDK-24+-00C853?style=for-the-badge&logo=android)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-7F52FF?style=for-the-badge&logo=kotlin)](https://kotlinlang.org/)
[![Material 3](https://img.shields.io/badge/Material--Design-3.0-0288D1?style=for-the-badge&logo=materialdesign)](https://m3.material.io/)

Selamat datang di repository proyek Android saya. Aplikasi ini dirancang sebagai Tugas akhir mata kuliah **PEMOGRAMAN APLIKASI PERANGKAT BERGERAK** yang mengintegrasikan berbagai fitur fundamental dan lanjutan dalam ekosistem Android menggunakan bahasa **Kotlin** dan **Material Design**.

---

## ✨ Fitur Utama (Full Implementation)

Aplikasi ini mencakup berbagai modul fungsional yang dikelompokkan sebagai berikut:

### 🛠️ Core & Base Modules

| Fitur                  | Deskripsi                                                 | Komponen                            |
| :--------------------- | :-------------------------------------------------------- | :---------------------------------- |
| **Authentication**     | Sistem login aman dengan validasi input.                  | `LoginActivity`, `SessionManager`   |
| **Profile Management** | Pengaturan informasi pengguna dan tampilan profil modern. | `ProfileFragment`, `activity_about` |
| **Splash Screen**      | Animasi pembuka aplikasi yang rapi.                       | `SplashActivity`, `activity_splash` |

### 🌐 Network & Intelligence

| Fitur              | Deskripsi                                                      | Komponen                               |
| :----------------- | :------------------------------------------------------------- | :------------------------------------- |
| ☁️ **Weather API** | Cek cuaca real-time menggunakan OpenWeatherMap API & Retrofit. | `WeatherFragment`, `WeatherApiService` |
| 📍 **Google Maps** | Integrasi peta interaktif dengan marker lokasi.                | `MapsFragment`, `fragment_maps`        |
| 📡 **Remote Data** | Mengambil data user dari public API (ReqRes).                  | `UserApiService`, `UsersFragment`      |

### 💾 Data & Optimization

| Fitur                   | Deskripsi                                                       | Komponen                            |
| :---------------------- | :-------------------------------------------------------------- | :---------------------------------- |
| 🗄️ **CRUD SQLite**      | Manajemen data lokal (Tambah, Lihat, Ubah, Hapus).              | `DatabaseHelper`, `DataFragment`    |
| 🎫 **Booking System**   | Alur simulasi pemesanan tiket dengan UI kompleks.               | `TicketFragment`, `fragment_ticket` |
| 🔔 **FCM Notification** | Sistem Push Notifications menggunakan Firebase Cloud Messaging. | `MyFirebaseMessagingService`        |

### 📚 Materi Perkuliahan (P1 - P4)

Modul khusus untuk latihan dasar yang dapat diakses di aplikasi:

- **P1:** Dasar UI & Activity Lifecycle.
- **P2:** Kalkulator Aritmatika & Logic Logic.
- **P3:** Widget, Toast, & Event Handling.
- **P4:** Firebase Authentication & Basic Database.

---

## 🏗️ Arsitektur & Teknologi

| Kategori           | Teknologi                               |
| :----------------- | :-------------------------------------- |
| **Language**       | Kotlin (1.9+)                           |
| **UI Framework**   | Material Components for Android (M3)    |
| **Networking**     | Retrofit 2, OkHttp 3, GSON              |
| **Async Process**  | Coroutines & Lifecycle Aware Components |
| **Navigation**     | Fragment Management & Bottom Navigation |
| **Local Storage**  | SQLite (DatabaseHelper)                 |
| **Cloud Services** | Firebase Cloud Messaging (FCM)          |

---

## 🚀 Panduan Instalasi (Setup Guide)

1.  **Clone Repository:**
    ```bash
    git clone https://github.com/BlueVirus9735/Tugas_Anndroid_Adji_Dwi_Cipta_Teja_kusuma.git
    ```
2.  **Konfigurasi Firebase:**
    Masukkan file `google-services.json` ke dalam direktori `app/`.
3.  **Sync Gradle:**
    Buka di Android Studio dan klik **Sync Project with Gradle Files**.
4.  **Run:**
    Gunakan emulator atau device fisik dengan **API 24+**.

---

<div align="center">
  <p>Dibuat oleh <b>Adji Dwi Cipta Teja Kusuma</b></p>
  <p><i>© 2026 STIKOM Poltek Cirebon - Sistem Informasi</i></p>
</div>
