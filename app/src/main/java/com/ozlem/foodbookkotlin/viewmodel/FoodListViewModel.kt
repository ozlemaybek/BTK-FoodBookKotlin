package com.ozlem.foodbookkotlin.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.service.FoodAPIService
import com.ozlem.foodbookkotlin.service.FoodDatabase
import com.ozlem.foodbookkotlin.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    // Mutable: değiştirilebilir demek
    val foods = MutableLiveData<List<Food>>()
    // hata var mı yok mu:
    // Genellikle internetten veri çekilirken bununla birlikte çekilir:
    val foodErrorMessage = MutableLiveData<Boolean>()
    // Eğer besin yükleniyorsa besin listesi içindeki proggress bar'ı görünür hale getireceğiz yükleniyorsa görünmez yapacağız.
    val foodLoading = MutableLiveData<Boolean>()
    // 10 dk'yı nanoTime'a çevirelim: (nanotime saniye ve salise'dende küçük)
    // 1000L'nin L'si long'dan geliyor.
    // 10 dk'nın nanotime'a çevrilmesi:
    private var updateTime = 10 * 60 * 1000 * 1000 * 1000L

    private val foodApiService = FoodAPIService()
    // disposable kullan at demektir.
    /* Aslında bizim burada yapacağımız her istek bir disposable olacak. Çünkü çok fazla istek yapacağımız uygulamalar olabilir.
    Belki aynı fragment içinde 10 tane istek yapabiliriz. Ve lifecycle'a göre arkaplanda bu isteklerin devamlı açık kalması ya da
    devam etmesi hafıza yönetimi açısından zorluklar yaratabilir. Belirli bir yerden sonra bunlardan kurtulmamız gerekiyor ve
    disposable ne zaman işimiz biterse o zaman çağırıp kurtulabileceğimiz bir yapı ve bu RxJava'da.
     */
    private val disposable = CompositeDisposable()

    // Shared preferences objemizi oluşturalım:
    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())

    fun refreshData(){

        // Geçen zamanı alalım:
        val saveTime = privateSharedPreferences.getTime()
        // Geçen zamanı koşullara göre değerlendirelim:
        // 0L: 0'ın long olduğunu belirtmek için.
        // System.nanoTime(): şuanki zamanı verir. Örneğin saat 04.00 gibi
        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            //Sqlite'tan çek
            getDataFromSQLite()
        } else {
            // 10 dk'dan fazla zaman geçmiş demektir.
            // internetten çek:
            getDataFromInternet()
        }

    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromSQLite(){
        foodLoading.value = true

        launch {

            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(),"We get foods from ROOM",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromInternet(){
        foodLoading.value = true
        // Gözlemlenebilir bir şey yaptığımız için nerede izleyeceğiz, nerede gözlemleyeceğiz, nerede kayıt olacağız gibi şeyleri
        // subscribeOn()'da söylememiz gerekiyor.
        // disposable.add dediğimizde içine parametre olarak bir disposable isteyecek:
        // subscribeOn; asenkron bir şekilde Single'a subscribe olur. Yani oluşturulacak ve döndürülecek Single observable'ına,
        // Single gözlemlenebilir objesine subscribeOn ile kayıt oluyoruz.
        // subscribeOn() içinde de nerede kayıt olacağımızı belirtiyoruz.

        /* main thread'i bloklamadan arka planda farklı thread'lerde işlem yapabiliyoruz ve buradada bunu yapacağız.
        * Veri geldi mi, işlendi mi, gözlemlenebiliyor mu, değişti mi bunları arka planda yapmamız gerekiyor.
        * Bu yüzden Schedulers.newThread() ile yeni bir thread açıyoruz.
        * Fakat observOn yani gözlemleme kısmını mainThread yani ana thread'de yapmamız gerekiyor.
        * mainThread: Kullanıcınında kullandığı ana thread'dir. Farklı thread çeşitleri vardır.
        * En son .subscribeWith() diyereke bir disposable oluşturuyoruz. İçine parametre olarak bir observer isteyecek.   */
        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        // Başarılı olursa:
                        sqlHide(t)
                        Toast.makeText(getApplication(),"We get foods from Internet",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        // Hata alırsak:
                        foodErrorMessage.value = true
                        foodLoading.value = false
                        // Hata alırsak hatayılogcat'te görmemizi sağlayacak:
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showFoods(foodsList : List<Food>){
        foods.value = foodsList
        foodErrorMessage.value = false
        foodLoading.value = false
    }

    private fun sqlHide(foodList : List<Food>){
        // Burada insertAll fonksiyonunu direkt çağıramam çünkü o bir suspend fonksiyonu.
        // Ancak bir coroutine kapsamında çapırabilirim.

        launch {
            // Önce DAO'muzu oluşturalım:
            val dao = FoodDatabase(getApplication()).foodDao()
            // veritabanında daha önceden bir şey kaldıysa diye önce tüm verileri siliyoruz.
            dao.deleteAllFood()
            // Şimdi yeni verilen besin listesini saklayalım:
            // insertAll bizden verebildiğimiz kadar besin vermemizi ister
            // bizde foodList isimli listeyi verdik toTypedArray() ile liste içerisindeki işlemler tek tek besin haline gelecek.
            // hata veriyor tek birtane besin istedim ama array verdin diyor.
            // Başına * koyarak tek tek verilmesini sağlıyoruz.
            // Böylece foodList'teki besinleri tek tek dao içerisine verebiliyoruz.
            // insertAll bize Long Listesi döndürüyordu döndürdüğü şeylerde uuid'lerdi onlarıda alalım.
            // Yani eşitliğin sağ tarafında verilerimi veritabanına eklerken sol tarafındaad uuid'leri alıyoruz.
            val uuidList = dao.insertAll(*foodList.toTypedArray())

            //Şimdi aldığımız uuid'leri tek tek atayalım:
            //SQLite'ta oluşturulan veri uuid'lerini modelimizin içine koyduk böylece model içindede bu uuid'lere erişebileceğiz.
            var i = 0
            while(i<foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            showFoods(foodList)
        }
        // işlem bittikten sonraki güncel zamanı kaydedelim:
        privateSharedPreferences.saveTime(System.nanoTime())
    }
}