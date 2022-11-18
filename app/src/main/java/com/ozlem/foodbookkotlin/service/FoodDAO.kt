package com.ozlem.foodbookkotlin.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ozlem.foodbookkotlin.model.Food

@Dao
interface FoodDAO {

    // Data Access Object (DAO)

    // VERİ EKLEME İŞLEMİ :

    /* Bu her şeyi ekleyeceğimiz fonksiyon.
    *  Bütün verileri çekip sonrasında bu fonksiyona liste olarak vereceğiz ve bunun içerisine eklenecek.
    *  vararg: Aldığımız argument birden fazla ve istediğimiz kadar olabiliyor.
    *  Aynı anda bir tane değil birden fazla besin yollayacağımız için vararg kullandık.
    *  Fonskyon bize en son bir liste döndürecek ve bu liste Long tipinde olacak.
    *  Çünkü liste içinde uuid'leri yani otomatik olarak oluşturulan id'leri döndürecek.
    *  Eklemeler Food.kt'de oluşturduğumuz entity'ye yani Food tablosuna eklenecek.
    *  Artık sadece bu fonksiyonu çağırarak ve bütün listemi vererek ekleme işlemini yapabiliyoruz.
    *  Ve bunu ROOM sayesinde yaptık. */
    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>

    // VERİ ÇEKME İŞLEMİ :

    // Bize tüm besinleri geri vereceği için fonksiyonun ismini getAllFood koyduk.
    // food tablosundan çek demiş olduk.
    // Eğer FoodListFragment'a çekiyorsak bu fonksiyonu kullanırız
    @Query("SELECT * FROM food")
    suspend fun getAllFood() : List<Food>

    // food tablosundan uuid'nin foodID'ye eşit olduklarını çek demiş olduk.
    // Fakat FoodListDetail'a çekiyorsak o zaman tek bir tanesini çekeriz:
    @Query("SELECT * FROM food WHERE uuid = :foodID")
    suspend fun getFood(foodID : Int) : Food

    // VERİ SİLME İŞLEMİ :

    // food tablosundaki tüm besinleri sil demiş olduk.
    // Be şekilde yazıp filtrelemezsek direkt tüm veritabanını siler.
    @Query("DELETE FROM food")
    suspend fun deleteAllFood()
}