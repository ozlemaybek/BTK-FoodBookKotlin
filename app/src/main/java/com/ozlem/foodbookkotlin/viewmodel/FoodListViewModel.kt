package com.ozlem.foodbookkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food

class FoodListViewModel : ViewModel() {
    // Mutable: değiştirilebilir demek
    val foods = MutableLiveData<List<Food>>()
    // hata var mı yok mu:
    // Genellikle internetten veri çekilirken bununla birlikte çekilir:
    val foodErrorMessage = MutableLiveData<Boolean>()
    // Eğer besin yükleniyorsa besin listesi içindeki proggress bar'ı görünür hale getireceğiz yükleniyorsa görünmez yapacağız.
    val foodLoading = MutableLiveData<Boolean>()
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val muz = Food("muz" , "100" , "10" , "5" , "1" , "www.image.com")
        val cilek = Food("cilek" , "100" , "10" , "5" , "1" , "www.image.com")
        val elma = Food("elma" , "100" , "10" , "5" , "1" , "www.image.com")

        val besinListesi = arrayListOf<Food>(muz, cilek , elma)

        foods.value = besinListesi
        foodErrorMessage.value = false
        foodLoading.value = false
    }
}