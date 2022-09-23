package com.ozlem.foodbookkotlin.model

// String nullable yapmamızın nedeni: çektiğimiz verilerde bazı bölümler boş olabilir.
// Verimizden gelecek isimlerle tamamen farklı isimleri seçtik.
// Farklı isimler seçtiğimizde birçok işlem kolaylaşıyor fakat bu kullanım şeklini görmek istedik:
data class Food (
    val name : String? ,
    val calorie : String? ,
    val carbohydrate : String? ,
    val protein : String? ,
    val oil : String? ,
    val image : String? ){
}