package com.ozlem.foodbookkotlin.model

import com.google.gson.annotations.SerializedName

// String nullable yapmamızın nedeni: çektiğimiz verilerde bazı bölümler boş olabilir.
// Verimizden gelecek isimlerle tamamen farklı isimleri seçtik.
// Farklı isimler seçtiğimizde birçok işlem kolaylaşıyor fakat bu kullanım şeklini görmek istedik:
data class Food (
    // Neye göre serileştirileceğini söylüyoruz:
    @SerializedName("isim")
    val name : String? ,
    @SerializedName("kalori")
    val calorie : String? ,
    @SerializedName("karbonhidrat")
    val carbohydrate : String? ,
    @SerializedName("protein")
    val protein : String? ,
    @SerializedName("yağ")
    val oil : String? ,
    @SerializedName("gorsel")
    val image : String? ){
}