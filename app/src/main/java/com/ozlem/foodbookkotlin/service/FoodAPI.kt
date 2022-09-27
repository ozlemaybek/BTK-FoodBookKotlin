package com.ozlem.foodbookkotlin.service

import com.ozlem.foodbookkotlin.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {

    // Önce GET isteği yapacağımızı söylememiz gerekiyor:
    // GET bizden ilk parametre olarak gidilecek URL'i ister.

    /* > URL'i direkt bulunduğumuz siteden kopyalayıp buraya yapıştıramıyoruz, önce başka işlemler yapmamız gerekiyor.
       > Retrofit bütün olan URL'i ikiye bölmemizi ister.
       > Çünkü genelde büyük API'larda aynı linki kullanarak birden fazla istek yapmamız gerekebilir.
       > BASE_URL : Ana WEB sitemiz neyse onun URL'ini yazmamız istenir.
       > Bu URL'i bir defa Retrofit içinde tanımlarız ve hep o kullanılır.
       > BASE_URL'den sonraki kısmı ise GET içine URL olarak veriyoruz.
     */

    // URL'in tam hali:
    // https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // BASE_URL : https://raw.githubusercontent.com/
    // KALAN KISIM : atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // Sonrasında GET request'inin hangi fonksiyon içinde ele alınacağını söylememiz gerekiyor (getFood method).
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")

    fun getFood() : Single <List <Food>>

    /*NOT: Buraya istediğimiz kadar request yazabiliriz. */
}