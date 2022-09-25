package com.ozlem.foodbookkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food

class FoodDetailViewModel : ViewModel() {

    // MutableliveData'nın içinde Food tutacağım:
    // Burada seçilen besini göstereceğim:
    val foodLiveData = MutableLiveData<Food>()

    // Room; SQLite'ı kullanmamız için geliştirilen bir kütüphane.
    fun getRoomData(){
        val muz = Food("muz" , "100" , "10" , "5" , "1" , "www.image.com")
        foodLiveData.value = muz
    }
}