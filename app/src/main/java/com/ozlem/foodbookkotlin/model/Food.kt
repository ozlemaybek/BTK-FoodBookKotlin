package com.ozlem.foodbookkotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// String nullable yapmamızın nedeni: çektiğimiz verilerde bazı bölümler boş olabilir.
// Verimizden gelecek isimlerle tamamen farklı isimleri seçtik.
// Farklı isimler seçtiğimizde birçok işlem kolaylaşıyor fakat bu kullanım şeklini görmek istedik:

@Entity
data class Food (
    // Neye göre serileştirileceğini söylüyoruz:
    // ColumnInfo ile bir alt satırdakinin kolonlarımızdan biri olacağını söylüyoruz.
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    val name : String? ,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    val calorie : String? ,
    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    val carbohydrate : String? ,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val protein : String? ,
    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    val oil : String? ,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    val image : String?
    ){

    // Yukarıdaki bilgileri primary constructor'a ekledik. Fakat primary key'i class'ın body'sine ekleyeceğiz:
    // oluşturduğumuz primary key'i uuid içine atadık.
    // Artık primary key otomatik olarak oluşturulacak ve eklediğimiz verilerin içerisine id olrak atanacak.
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}