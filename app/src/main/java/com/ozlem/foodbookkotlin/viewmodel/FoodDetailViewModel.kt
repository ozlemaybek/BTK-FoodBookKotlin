package com.ozlem.foodbookkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application) : BaseViewModel(application) {

    // MutableliveData'nın içinde Food tutacağım:
    // Burada seçilen besini göstereceğim:
    val foodLiveData = MutableLiveData<Food>()

    // Room; SQLite'ı kullanmamız için geliştirilen bir kütüphane.
    fun getRoomData(uuid : Int){
        // direkt bir veri çektiğimiz yeri yoruma aldık:
        // val muz = Food("muz" , "100" , "10" , "5" , "1" , "www.image.com")
        // foodLiveData.value = muz

        // Şimdi veriyi otomatik olarak alacağız
        launch {
            // dao'yu oluşturalım:
            // context olarak getApplication() verdik.
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }
}