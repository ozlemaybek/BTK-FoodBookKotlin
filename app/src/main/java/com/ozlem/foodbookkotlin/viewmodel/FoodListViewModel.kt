package com.ozlem.foodbookkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.service.FoodAPIService
import io.reactivex.disposables.CompositeDisposable

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
        disposable.add(
            foodApiService.getData().subscribeOn()
        )
    }
}