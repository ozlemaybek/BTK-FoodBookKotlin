package com.ozlem.foodbookkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.service.FoodAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FoodListViewModel : ViewModel() {
    // Mutable: değiştirilebilir demek
    val foods = MutableLiveData<List<Food>>()
    // hata var mı yok mu:
    // Genellikle internetten veri çekilirken bununla birlikte çekilir:
    val foodErrorMessage = MutableLiveData<Boolean>()
    // Eğer besin yükleniyorsa besin listesi içindeki proggress bar'ı görünür hale getireceğiz yükleniyorsa görünmez yapacağız.
    val foodLoading = MutableLiveData<Boolean>()
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodApiService = FoodAPIService()
    // disposable kullan at demektir.
    /* Aslında bizim burada yapacağımız her istek bir disposable olacak. Çünkü çok fazla istek yapacağımız uygulamalar olabilir.
    Belki aynı fragment içinde 10 tane istek yapabiliriz. Ve lifecycle'a göre arkaplanda bu isteklerin devamlı açık kalması ya da
    devam etmesi hafıza yönetimi açısından zorluklar yaratabilir. Belirli bir yerden sonra bunlardan kurtulmamız gerekiyor ve
    disposable ne zaman işimiz biterse o zaman çağırıp kurtulabileceğimiz bir yapı ve bu RxJava'da.
     */
    private val disposable = CompositeDisposable()

    fun refreshData(){
        getDataFromInternet()
    }

    private fun getDataFromInternet(){
        foodLoading.value = true
        // Gözlemlenebilir bir şey yaptığımız için nerede izleyeceğiz, nerede gözlemleyeceğiz, nerede kayıt olacağız gibi şeyleri
        // subscribeOn()'da söylememiz gerekiyor.
        // disposable.add dediğimizde içine parametre olarak bir disposable isteyecek:
        // subscribeOn; asenkron bir şekilde Single'a subscribe olur. Yani oluşturulacak ve döndürülecek Single observable'ına,
        // Single gözlemlenebilir objesine subscribeOn ile kayıt oluyoruz.
        // subscribeOn() içinde de nerede kayıt olacağımızı belirtiyoruz.

        /* main thread'i bloklamadan arka planda farklı thread'lerde işlem yapabiliyoruz ve buradada bunu yapacağız.
        * Veri geldi mi, işlendi mi, gözlemlenebiliyor mu, değişti mi bunları arka planda yapmamız gerekiyor.
        * Bu yüzden Schedulers.newThread() ile yeni bir thread açıyoruz.
        * Fakat observOn yani gözlemleme kısmını mainThread yani ana thread'de yapmamız gerekiyor.
        * mainThread: Kullanıcınında kullandığı ana thread'dir. Farklı thread çeşitleri vardır.
        * En son .subscribeWith() diyereke bir disposable oluşturuyoruz. İçine parametre olarak bir observer isteyecek.   */
        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        // Başarılı olursa:
                        foods.value = t
                        foodErrorMessage.value = false
                        foodLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Hata alırsak:
                        foodErrorMessage.value = true
                        foodLoading.value = false
                        // Hata alırsak hatayılogcat'te görmemizi sağlayacak:
                        e.printStackTrace()
                    }

                })
        )
    }
}