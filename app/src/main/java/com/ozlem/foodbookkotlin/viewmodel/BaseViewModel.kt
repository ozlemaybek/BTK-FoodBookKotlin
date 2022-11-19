package com.ozlem.foodbookkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/* BaseViewModel olarak isimlendirdiğimiz bu sınıfta sadece coroutine fonksiyonlarını yazacağız,
 * coroutine temelini atacağız ve sonrada bunu diğer viewmodel'lara extend edeceğiz. */

// AndroidViewModel parametresi: uygulamanın kendisi
// (bu yüzden class'tan constructor içinde uygulamanın kendisini istedik)
open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope{

    // Job coroutine ile ilgilidir. Burada Coroutine içerisinde ne işi yapacağımızı belirtiyoruz.
    private val job = Job()

    // job + Dispatchers.Main dersem arka planda ne iş yapılıyorsa yapılacak sonrada main'e dönülecek.
    // Yani main içindede bu işlemler devam edecek çünkü kullanıcının arayüzünün olduğu thread'dede işlem yapacağız
    // örneğin verileri aldıktan sonra orada göstereceğiz.
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // İşlemler bittikten sonra job.cancel() yaparsak job'ı iptal ediyoruz.
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}