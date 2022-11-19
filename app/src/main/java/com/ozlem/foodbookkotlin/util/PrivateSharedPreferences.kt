package com.ozlem.foodbookkotlin.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrivateSharedPreferences {

    companion object {

        private val TIME = "time"
        // İlk başta sharedpreferences'ın değeri null olsun:
        private var sharedPreferences : SharedPreferences? = null

        // PrivateSharedPreferences'a ait bir instance oluşturduk ve null olarak initialize ettik.
        @Volatile private var instance : PrivateSharedPreferences? = null
        private val lock = Any()

        // invoke fonksiyonu bize bir PrivateSharedPreferences döndürecek. (instance dönecek ama null mı değil mi kontrol edilecek.)
        // null değilse instance döndürülecek ama null ise senkronize olarak aşağıdaki işlemleri yapacak.
        operator fun invoke(context: Context) : PrivateSharedPreferences = instance ?: synchronized(lock) {
            // ?: instance null mı değil mi diye kontrol ettik.
            // null ise doPrivateSharedPreferences metodunu çalıştıracağım
            instance ?: doPrivateSharedPreferences(context).also {
                instance = it
            }
        }

        // Diğer versiyonda database oluşturmuştuk bunda ise sharedpreferences oluşturacak.
        private fun doPrivateSharedPreferences(context: Context): PrivateSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }

    }

    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            // TIME: key
            // time: value
            putLong(TIME,time)
        }
    }

    // Şu kadar zaman geçmişse şunu şu kadar zaman geçmişse bunu yap dediğimiz bir yapı kuracağız bu yüzden zamanı almak için
    // bir fonksiyon yazalım:
    // 0'ı default değer olarak verdik.
    fun getTime() = sharedPreferences?.getLong(TIME,0)
}
