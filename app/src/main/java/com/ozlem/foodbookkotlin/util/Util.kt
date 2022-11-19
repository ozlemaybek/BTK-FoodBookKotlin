package com.ozlem.foodbookkotlin.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozlem.foodbookkotlin.R

/*ÖRNEK:
// String Class'ına ait bir eklenti yazdık:
// Artık String değerinde bir değişken için bu fonksiyonu kullanabiliriz
fun String.benimEklentim(parametre : String){
    println(parametre)
}
*/

// Yazdıktan sonra ImageView'un üzerine gelip alt  + enter ile import etmeliyiz:
fun ImageView.downloadImage(url : String? , placeholder: CircularProgressDrawable){

    /* Resimlerin internetten inmesi 1-2 saniye sürebiliyor bu aşamada boş bir ekran yerine progress bar gösterebiliriz.
    *  Bunun içinbir options oluşturacağız:
    *  RequestOptions : Seçenekleri koymamız için Glide'ın içinde oluşturulan bir yapı.
    *  placeholder : resim inene kadar gösterilecek şey. Bizden parametre olarak bir drawable ister.
    *  error : hata mesajı olursa ne koyacağını belirtiyoruz.  */
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    // with'in içine parametre olarak bir context vermeliyiz. Fakat this diyemeyiz çünkü imageView'a referans veriyor.
    // Bu yüzden context verdik.
    // imageView hangi context'te kullanılıyorsa onun içinde çağrılacak.
    // load: url'yi vereceğiz.
    // into: nereye yükleyeceğiz
    // this burada ImageView'a referans veriyor.
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

}

/* CircularProgressDrawable; swipe refresh layout'tan gelir.
*  Biz zaten ekranın ortasında kendi progress bar'ımız olduğu için swipe refresh layout'a ait progress bar'ı iptal etmiştik.
*  Fakat istediğimiz zaman aynısından oluşturabilmemiz için CircularProgressDrawable sınıfı var.
*  Bu fonksiyon sonunda direkt CircularProgressDrawable'ı döndüreceğiz ve kendimiz bunu oluşturabiliyoruz.
*  apply; bir işlem yapılırken bununla birlikte yapılacak diğer özellikleri yazmamıza olanak tanıyor.  */
fun doPlaceholder(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        // strokeWidth : Yaptığımız dönen şeyin kalınlığını gösteriyor.
        // centerRadius : Dönen şeyin yarıçapı
        // start() : dönen şeyi başlatır.
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
// aşağıdaki fonksiyonu xml'de databinding ile birlikte kullanabilmek için başına @BindingAdapter ekledik:
@BindingAdapter("android:downloadImageForBinding")
fun downloadImageForBinding(view: ImageView, url : String?){
    // yukarıda binding olmadığı durum için tanımladığımız downloadImage fonksiyonunu bunun için kuulandık:
    view.downloadImage(url, doPlaceholder(view.context))
}