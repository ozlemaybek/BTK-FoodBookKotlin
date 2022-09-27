package com.ozlem.foodbookkotlin.service

import com.ozlem.foodbookkotlin.model.Food
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodAPIService {

    // URL'in tam hali:
    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // BASE_URL : https://raw.githubusercontent.com/
    // KALAN KISIM : atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private val BASE_URL = "https://raw.githubusercontent.com/"

    // Retrofit.Builder Retrofit objesini oluşturabilmemiz için geliştirilmiş bir sınıf:
    /* addConverterFactory: Converter çevirmek demektir. Bu metod bir JSON dosyasını bir modele çevirmeye yarar
       ve bizden parametre olarak bir converter ister. Bunun içinde GsonConverterFactory'yi kullanacağız. */
    /* .addCallAdapterFactory diyerek RxJava kullanacağımı belirtmem gerekiyor. RXJava kullanmasaydık eğer bu metodu eklememize
    gerek yoktu. */
    /* En sonunda build ile inşa edip create ile oluşturuyoruz.create içinde hangi sınıftan oluşturacağımızı belirtiyoruz,
    Yani oluşturduğumuz arayüzü create içinde belirtmemizi istiyor. */
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FoodAPI::class.java)

    // Bu fonksiyon içinde besin listesi olan bir single observable döndürecek.
    fun getData() : Single<List<Food>> {
        return api.getFood()
    }
}