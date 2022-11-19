package com.ozlem.foodbookkotlin.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ozlem.foodbookkotlin.model.Food

// Bu şekilde parantez açtığımızda entity'leri yani içerisine koyduğumuz tabloları istiyor.
// Aynı zamanda tabloları array içinde istiyor. Dizi içinde olmasının sebebi birden fazla entity kullanabilmemiz.
// Fakat biz kendi örneğimizde sadece Food'u entity haline getirdik.
// Database'imizin versiyonunu bir yaptık eğer daha sonra database'de değişikliğe gidersek versiyonu 2 yapmamız ve artırmamız gerekir.
// RoomDatabase() diyerek veritabanımızın bir Room database'i olduğunu belirtmemiz gerekiyor.
@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase : RoomDatabase(){
    // FoodDao bir arayüz olduğu için sonuna () şeklinde bir constructor koymamıza gerek yok:
    abstract fun foodDao() : FoodDAO

    // Singleton
    companion object{

        // instance isminde bir değişken oluşturduk. Bu değişken FoodDatabase nullable oldu ve null'a eşitledik.
        // Bu companion object içerisinden herhangi bir instance yani obje ya da örnek nesne oluşturulmuş mu  bunu kontrol edeceğiz ve
        // başına @Volatile ekleyeceğiz. @Volatile kelimesini normalde coroutine'ler ve thread'lerle çalışmıyorken eklemeye gerek yok
        // fakat @Volatile eklediğim takdirde buradaki instancediğer thread'lere degörünür yapılıyor.
        @Volatile private var instance : FoodDatabase? = null

        // Aşağıda invoke fonksiyonunda synchronized'ın istediği kilit
        // Bu obje sadece kiliti takip etmek için kullandığımız bir nesne.
        private val lock = Any()

        // invoke ateşlemek ya da başlatmak demektir.
        // Eğer daha önce bu sınıftan bir nesne oluşturulduysa onu döndür
        // oluşturulmadıysa onu döndürme ve bu işlemleri senkronize yap demiş oluyoruz.
        // ?: elvis operatörü
        // Aşağıdaki satırın anlamı: daha önce bu sınıftan bir instance oluşturulduysa yani instance null değilse instance'ı döndür
        // eğer oluşturulmadıysa yani instance null ise synchronized deriz.
        // synchronized içine parametre olarak bir lock yani kilit istiyor.
        // synchronized yazarken içinde Any block vs yazanı seç.
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            // Şimdi bu kod bloğunun içinde instance varsa onu döndür yoksa senkronize olarak şuradaki işlemleri yap diyeceğim:
            // also: bunu yap üstüne şunuda yap demek:
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        // Yukarıda yapılanı kısaca özetleyelim:
        // Daha önce invoke fonksiyonu çağrıldımı çağrılmadımı bunu kontrol ediyorum
        // Eğer çağrıldıysa oluşturulmuş instance objesini döndür diyorum çağrılmadıysa yenisini oluştur diyorum.
        // Artık veritabanımızı çağrırırken invoke fonksiyonunu kullanacağız ve zaten bu fonksiyon bizim için tüm kontrolleri yapacak.

        // Şimdi database'i oluşturalım:
        // databaseBuilder bizden parametre olarak bir context isteyecek.
        // Bunu createDatabase fonksiyonu çağrıldığında parametre olarak istedik. Böylece direkt databaseBuilder içinde kullanabildik.
        // Fakat tdatabase'e uygulamanın her yerinden ulaşılabilmesi için applicationContext verdik.
        // databaseBuilder bizden ikinci parametre olarak sınıfı istiyor oda içinde bulunduğumuz sınıf.
        // 3. parametre : database'in ismi
        // En son .build() diyerek database'i oluşturuyoruz.
        // Şimdi istediğimiz yerde FoodDatabase sınıfından createDatabase fonksiyonunu çağırarak database'imizi oluşturabiliriz.
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, FoodDatabase::class.java, "fooddatabase").build()
    }

}