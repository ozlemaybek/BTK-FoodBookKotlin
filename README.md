# BTK-FoodBookKotlin

## Kotlin İle Android Mobil Uygulama Geliştirme İleri Seviye (BTK AKADEMİ) Notlarım

## Projede Kullanılan Dosyalar:

- build.gradle Project
- build.gradle Module-app
- settings.gradle
- MainActivity.kt
- activity_main.xml
- FoodListFragment.kt
- fragment_food_list.sml
- FoodDetailFragment.kt
- fragment_food_detail.xml
- food_graph.xml
- food_recyler_row.xml (layout resources file)

> Projedeki Teknolojiler: MVVM, Retrofit, SQLite, Room, RxJava, DataBinding

> ROOM: SQLite ile çalışmak için kullandığımız bir JetPack ögesidir. 

## Proje Hakkında Bilgi

> Projede internetten indirdiğimiz verileri SQLite'a kaydedeceğiz böylece devamlı internetten veri indirmek zorundakalmayacağız. Verileri sonrasında SQLite'dan çekeceğiz. Veriler her analisteye döndüğümüzde Room'dan yani SQLite'dan çekilecek. Fakat anasayfaya döndüğümüzde 10 dk'dan daha fazla süre geçmişse otomatik olarak internetten çekilecek. 

> Ana listedeyken kullanıcı ekranı aşağı kaydırıp refresh ederek verilein tekrar internetten çekilmesini sağlayabilecek.  

> GitHub'da projeyi açtığımızda görünen ilk ekrandaki build.gradle project dediğimiz build.gradle'dır.

![image](https://user-images.githubusercontent.com/109730490/190392882-c9bfafc6-8873-487f-a263-2bf65ab871f6.png)

## Projedeki build.gradle Eklemeleri

- build.gradle project eklemeleri 

> Normalde build.gradle project dosyası bu şekilde olacaktı ancak allProject kod bloğu hata verdi. Android Studio güncellendiğinden artık projenin bağımlılıklarını settings.gradle dosyasındaki dependencyResolutionManagament bloğunda belirtmeliymişiz. Bu yğzden allProjects bloğunu sildim ve jcenter() satırını settings.gradle'a ekledim.

![image](https://user-images.githubusercontent.com/109730490/190396170-53fad885-d7ed-494c-8527-59bd3c4e449a.png)

> Benim yaptığım build.gradle project dosyası bu şekilde:

```kotlin
/* Sonradan yaptığım eklemeler: */
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext.kotlin_version = "1.3.72"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.0.0"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        def nav_version = "2.3.0"
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
/* Sonradan yaptığım eklemeler buraya kadardı. */

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '7.2.2' apply false
    id 'com.android.library' version '7.2.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.7.10' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

> GitHub'da build.gradle module-app dosyasını bulmak:

- Önce projeyi açtığımız ekranda app klasörüne giriyoruz:

![image](https://user-images.githubusercontent.com/109730490/190396960-33a6bb45-aa4e-4238-ab2b-f62b25d69937.png)

- Burada görünen build.gradle dosyası build.gradle module-app dosyası:

![image](https://user-images.githubusercontent.com/109730490/190397223-a680aa1f-84e3-4137-835c-8e3ad717f1bf.png)

- build.gradle Module-App Eklemeleri

![image](https://user-images.githubusercontent.com/109730490/190404976-8826b644-59d0-466a-a7ca-385aa175210c.png)

![image](https://user-images.githubusercontent.com/109730490/190404559-8a4816ba-357e-4131-8835-5de2cd75c248.png)

![image](https://user-images.githubusercontent.com/109730490/190404606-f3ef3986-1a9c-4168-9c41-dda8fe62773d.png)

![image](https://user-images.githubusercontent.com/109730490/190404653-37d02e96-81a5-43ae-bcc5-6282e45472f8.png)

## PROJE DETAYLARI

> İki fragment ekledik. Birinci fragment'ta besin listesi ikinci fragment'ta ise besin detayı gösterilecek. 

> Fragment xml dosyasına constraintLayout'uda içine alacak bir <layout></layout> tag'i ekledik. "layout" dataBinding'te ve navigation'da kullanacağımız bir yapı. Bir layout tag'i açtıktan sonra xmlns yani xml name sppace'leri constraint layout'tan alıp layout tag'inin içine koyduk:

![image](https://user-images.githubusercontent.com/109730490/190409406-84efafb3-c1bf-4ad6-9c85-ba0351040826.png)

> Fragmentlar için tasarımları yaptıktan sonra navigation grafiği çizdik. Bunun için: res > sağ tık > Android Resource File

![image](https://user-images.githubusercontent.com/109730490/190410731-d367b039-a591-4135-aa49-8eadceb946de.png)

![image](https://user-images.githubusercontent.com/109730490/190411012-8ebc71bd-3939-46a6-aefd-5c812f8711ee.png)

## activity_main.xml'de yaptıklarımız:

> İçine bir navHostFragment attık ve çizdiğimiz food_graph'i verdik:

![image](https://user-images.githubusercontent.com/109730490/190411502-437ba1bd-fdcd-4afc-bede-c7cd737a53d6.png)

## Navigation ve argument

![image](https://user-images.githubusercontent.com/109730490/190853367-9c0e965f-7e4d-4c3e-a5ce-5399d3fc76f7.png)

> foodDetailFragment seçili iken arguments bölümündeki + butonuna bastık ve foodDetailFragment için bir argument ekledik. Bunu yaptığımızda argument graph'a ait xml dosyasınada ekleniyor:

![image](https://user-images.githubusercontent.com/109730490/190853529-e4ed23ad-36a3-46d7-8aee-3d10f6c40afb.png)

> Bu işlemi yaptıktan sonra Build > Rebuild Project yapmalıyız. Çünkü navigation framework'ü bizim için sınıfları oluşturmalı. 

> Sonrasında FoodListFragment.kt'ye aşağıdaki eklemeyi yaptık:

![image](https://user-images.githubusercontent.com/109730490/190856019-8c7f5427-a221-4c46-9567-35e69ad9e184.png)

> Ve FoodDetailsFragment.kt'ye aşağıdaki eklemeyi yaptık:

![image](https://user-images.githubusercontent.com/109730490/190856093-8d921706-9deb-4c93-88b6-813aa57b7e5e.png)

## Project Structure

![image](https://user-images.githubusercontent.com/109730490/190853905-c66b98ea-c188-4eb8-8d20-ea4302d73436.png)

> Compability uyumluluk demektir.

> Burada modules kısmına geldiğimizde sourcecompability ve target compability kısmının java 1.8 olduğundan emin olmalıyız:

![image](https://user-images.githubusercontent.com/109730490/190853941-2e8a03c5-e760-4d63-b9ca-ed21a53b3a38.png)

> Burada değiştirdiğimiz şeyler otomatik olarak build.gradle'a ekleniyor. Bu işlemi retrofit'in son versiyonlarını kullanabilelim diye yaptık.

## ARAYÜZ TASARIMI

### swipe refresh layout

> Ekranı aşağı doğru kaydırdığımızda yenileyen yapıdır. Tıpkı constraint layout gibi bu da bir layout çeşitidir. 

> swipe; sağa, sola, yukarı ve aşağı doğru çekme işlemine verilen isimdir. 

> Bu layout'u build.gradle'da dependencies kısmına eklemeliyiz. Sonra kullanabiliriz. 

> Swipe refresh layout'un kendine ait bir proggress bar'ı vardır. 

### PROGRESS BAR (Yükleniyor İkonu)

![image](https://user-images.githubusercontent.com/109730490/190859907-845ac830-3dc7-4291-8ae4-9fa258d8d18d.png)

## RecyclerView tasarımı

> Bu projede verileri çekeceğimiz link githubdan JSON formatında geliyor. JSON bir gösterim biçimidir. JSON'ı tüm modern programlama dilleri kendi kütüphaneleri ile işleyebilecek yeteneğe sahipler. 

>Kullanacağımız data bu şekilde;

![image](https://user-images.githubusercontent.com/109730490/190860377-73a59e2e-cf6d-45d0-91e9-5449f41009da.png)

> Ama biz sadece ismini, kalorisini ve resmini gösterecek şekilde kullanacağız. Ve alt alta gösterim için linear layout kullanacağız. 

### layout_weight

![image](https://user-images.githubusercontent.com/109730490/190863093-67117006-3876-4aa0-8e4f-ad727cdef2ae.png)

> 5'te 2'sini iamgeView'a 5'te 3'ünü diğer linear layout'a verdik.

### minHeight

![image](https://user-images.githubusercontent.com/109730490/190864063-602956dd-b04e-4269-9135-ef1107ae009e.png)

### scaleType

![image](https://user-images.githubusercontent.com/109730490/190864100-3bba28b8-dded-4d26-9bbd-442575aa4f36.png)

> fitCenter : Merkezde fit et.

> Resmin relative layout'un en başında görünmesini sağlamak:

![image](https://user-images.githubusercontent.com/109730490/190864195-026d660b-973b-487d-ab11-16f90f048c7b.png)

## MVVM (Model View View Model) MİMARİSİ

![image](https://user-images.githubusercontent.com/109730490/190901227-e5b6fd99-d4b2-4393-a3b7-39e04d39b1f5.png)

> MVVM; kodları nasıl yazmamız gerektiği ile ilgili bize rehberlik eder. 

> MVVM; sadece ANDROID'e özgü bir mimari değil. Fakat Kotlin tarafından desteklenen bir mimari.

> MVVM mimarisinde bir View var, bir model var model dediğimiz şey bizim class'ımız bir de View Model var. 

> Daha önceki mimarilerde verinin işlenmesi vs gibi işlemlerin hepsini View'lar içinde yapıyorduk yani bir fragment ya da activity içerisinde verileri çekiyorduk ve bu zamana kadarki projelerde de böyle yaptık. 

> Fakat MVVM mimarisinde model tamamen çekilecek verileri bulundursun, View'da ise sadece kullanıcı arayüzü ile ilgili şeyler olsun mesela hata mesajını ya da progress barı görünür yap, recylerView'u yenile gibi işlemleri View'da yani fragment içinde activity içinde yapabiliriz. Fakat verileri indirme, onları işleme işlemlerini ayrı bir sınıfta yap ve onun adıda View Model olsun. 

> MVVM; kullanıcının göreceği şeyleri "View" içerisinde, kullanıcının görmeyeceği şeyleri ise "View Model" içerisinde yap diyor. 

> Bu sayede artık fragment'lar içerisinde çok yüklü işlemler yapmıyoruz. Genel olarak business mantığı ile UI mantığını ayırmış oluyoruz ve bu da kod düzenini beraberinde getiriyor. 

> Eğer ayrı ayrı sınıflarda herkes kendi işini yaparsa bu bize clean code avantajı getiriyor. 

> Bir sınıfta bir işlem yapacaksak ve o sınıftan oluşturduğumuz objeleri başka bir yerde kullanacaksak çok büyük avantajlar elde ediyoruz. Aynı zamanda testlerimizi yapmak çok kolaylaşıyor. Örneğin View Model'da bir test yapacaksak sadece View Model'dakii değişkenleri değiştirerek işlemlerimi View'a dokunmadan yapabiliyorum. 

> MVVM; bir zorunluluk olmasa da mobil uygulama geliştirme endüstrisinde en güzel şekilde kod yazabileceğimiz yapı olarak adlandırılır. Fakat teknoloji sürekli geliştiğinde birkaç yıl sonra çok daha iyi bir mimari çıkabilir ve onu kullanmaya başlayabiliriz. 

## MODEL YAZMAK

> Verilerimizi bir model içinde alıp bir besin sınıfı oluşturacağız. Bunun için oluşturduğumuz dosyaları kolay bulmak adına MVVM paketleri oluşturacağız:

> Bir package oluşturalım:

![image](https://user-images.githubusercontent.com/109730490/190901866-c47377f2-4cfd-4814-afa1-bae2cc2ced02.png)

1)model package:

> package'ın isminin sonuna .model ekliyoruz:

![image](https://user-images.githubusercontent.com/109730490/190901880-8a416c3d-e07b-44f8-b4b1-412d0f13533b.png)

2)view package:

![image](https://user-images.githubusercontent.com/109730490/190902064-e3657d72-91de-4ab9-9a39-d57257fa4c9c.png)

3)view model package

![image](https://user-images.githubusercontent.com/109730490/190902041-43d1dfd1-cc0c-45bb-b419-68c557868c26.png)

> Sonunda böyle bir görünüm elde ettik:

![image](https://user-images.githubusercontent.com/109730490/190902085-6e68e006-b1e5-4e47-b5a2-fde93bce01da.png)

> Aslında bunu yapmamız şart değildi fakat "com.ozlem.foodbookkotlin" paketinin altında çok fazla sınıf olacak ve sonradan istediğimizi bulmak zorlaşacak bu yüzden ayrı paketler oluşturduk.

> Sonrasında proje içinde oluşturduğumuz activity ve fragment'lar bir view olduğu için onları view paketinin içine sürükleyip refactor diyoruz: 

![image](https://user-images.githubusercontent.com/109730490/190902232-5c5f68f1-7ccf-456a-96fc-afb4cf21d021.png)

![image](https://user-images.githubusercontent.com/109730490/190902266-4040f3ec-1847-4c17-a2e5-e5a0d7d65746.png)

> Şimdi model paketi içinde yeni bir kotlin sınıfı oluşturalım:

![image](https://user-images.githubusercontent.com/109730490/190902306-69805320-4176-4e8a-83da-8fe4ca8ccbe0.png)

> Sonra bunu bir data sınıfına çevirelim. 

## DATA CLASS
> İnternetten ya da bir sunucudan veri çekeceksek ve tek yapmak istediğimiz o verilerden bir model oluşturmaksa bizim için ideal bir yapı sunuyor. Bir data class oluşturduğumuzda primary constructor kullanmalıyız yoksa hata verir çünkü data class'ın amacı model oluşturmak. Primary constructor'ın içine isteyeceğimiz değişkenleri yazmalıyız. 

![image](https://user-images.githubusercontent.com/109730490/191914589-08fe2cba-7693-4fe7-92ac-2201a8982d69.png)

> data class içerisinde verimizde kullanılan isimleri kullanmak zorunda değiliz. Fakat bazı durumlarda farklı isim kullanmamız hata çıkartabiliyor. Bunların nasıl çözüleceğini göreceğiz. 

![image](https://user-images.githubusercontent.com/109730490/191914817-b9b2d452-a1f8-430e-894f-67169262a8db.png)

> Model sınıfını tamamladık:

![image](https://user-images.githubusercontent.com/109730490/191916479-91297100-a7a1-439a-8369-1fedaeca01cd.png)

## RECYCLERVIEW İÇİN ADAPTER YAZIMI

> Adapter ile ilgili kısımlar view paketinin içinde oluşturabiliriz çünkü görünümle ilgili kısımlar. Fakat biz ayrı bir pakete koyacağız. Birden fazla adapter oluşturduğumuzda kolaylık sağlıyor. 


## VIEW MODEL YAZMAK

> MVVM bizden her bir view için bir tane view model oluşturmamızı ister. Yani tek bir model view sınıfı oluşturup her şeyi orada yapmakta temiz koda girmez. Bu yüzden her bir view için ayrı ayrı view model oluşturmamız gerekir. 

> İlk olarak viewmodel paketinin içinde FoodListViewModel isimli bir kotlin class'ı oluşturuyorum. 

> ViewModel; görünümlerimiz ile model arasında bir yapı. İnternetten veri çekeceksek ya da kullanıcının görmediği arkaplan işleri ypaacaksak bunun içinde yapabiliyoruz. 

> ViewModel bir scope (fragment, activity) ile beraber oluşturulur ve fragment veya activity kapanana kadar devam ediyor. 

## Live Data

> Live Data veri tutan bir sınıftır. observer dediğimiz gözlemleyici bir yer var bir de observable dediğimiz gözlemlenebilir veriler var. Live Data gözlemlenebilir bir obje ve gözlemleyicilerimiz buradaki değişikliklere kayıt olabiliyor. 


- Live Data ve view model'ı her zaman birlikte kullanırız ki hem bize hem yaşam döngüsü açısından avantaj sağlasın hem de yaşam döngüsü içinde beraber çalıştığı view model ile beraber verilerin değişikliğini görünümlere bildirebilsin. Böylece günlerce uğraşıp yapabilceğimiz yapıları direkt olarak view model sınıfından ve live data sınıfından faydalanarak kolayca yapabiliyoruz. 

## API (Application Programming Interface - Uygulama Programlama Arayüzü) NEDİR?

> Bir arayüzdür ve iki sistem arasında iletişim kurar. Sunucudan programa veya programdan sunucuya veri akışı için kullandığımız yapılardır. 

> Örneğin google'a weather api yazdığımızda çıkan sonuçlardan birine girdiğimizde gördüğümüz üzere bu api'ı kullanarak internetten bu verileri çekerek bir weather app yapabiliriz. 

![image](https://user-images.githubusercontent.com/109730490/192239523-bcdafd88-0790-4fa6-aacf-4b590b262829.png)

> API'lar çeşitli platformlara ait olabilirler. Örneğin bir fotoğraf paylaşma ya da haber platformuyuz, API'ımız olabilir. Developerlar bu API'ı kullanarak verilerimizi kendi uygulamalarına ya da sitelerine entegre etsin isteyebiliriz. API'lar ücretsiz ya da ücretli olabilir. 

> Bu projede hazır olan ve değişmeyen bir veri kullanacağız. 

> API'lar bazen ücretsizken 3 ay sonra ücretli hale gelebilir. 

> Genelde sunucudan bir şey alıyorsak ya da sunucuya bir şey yolluyorsak bu JSON (Java Script Object Notation) formatında gelir. 

## RETROFIT

> İnternetten veri çekmek ve onunla bir işlem yapmak istiyorsak kullandığımız bir kütüphane. Android için HTTP isteği yapacağımız, web sitesine gideceğimiz, bilgi çekeceğimiz ya da yollayacağımız durumlarda kullanabiliriz. İstediğimizde projemize implemente ederek kullanabiliyoruz. 

## RxJava (reactivex)

> asenkron ve olay bazlı programları kullanmak ve gözlemlenebilir bazı yapılar oluşturmak için kullanıyoruz. 

> Asenkron maddesi Retrofit içinde geçerli.

> İnternetten veri indirirken bunu asenkron bir şekilde yapmalıyız. Yani senkron olmayan. Yani internetten veri indirirken kullanıcının arayüzünü bloklayıp verinin inmesini bekleyip diğer kodları sonra çalıştırmak giib bir durum söz konusu değil çünkü bu uygulamayı çökertir. 

> Firebase'de SDK'nın kendisi bize asenkron fonksiyonaliteler sağladığı için bu tar işlemlerle uğraşılmadı. Çünkü veriyi çektiğimizde zaten asenkron çekiliyordu ve veri geldikten sonra ne yapılacağını yazabildiğimiz kod ve fonksiyonlar vardı. Fakat şuanda hazır bir SDK kullanmıyoruz bu yüzden projede retrofit'i kullanacağız. 

> Projede arka planda veri çekilirken kullanıcı uygulamayı kullanmaya devam edebilecek ve uygulama kilitlenmeyecek veya çökmeyecek. 

> Sadece retrofit bütün veriyi indirmemiz ve işlememiz için yeterli olacak fakat proje büyüyüp komplikeleşmeye başladığında RxJava'ya ihtiyaç artar çünkü RxJava tüm bu işlemleri daha verimli yapmamızı sağlıyor. 

## PROJEYE İNTERNET İZNİ EKLEMEK

> İnternet izni manifest dosyasına eklenir:

![image](https://user-images.githubusercontent.com/109730490/192266443-db2ab777-c218-4c02-a593-76bfa922e6ac.png)

> İnternet izini dangerous kategorisine girmediği için kullanıcıya tekrar açık bir şekilde sormamıza gerek yoktur. 

> İnternet izinini ekledikten sonra uygulamayı emülatörden silip emülatörü tekrar çalıştırmalıyız. Böylece iziniteyit edebiliriz. 

## RETROFIT AŞAMALARI

> Retrofit'te gelen verinin hangi formatta geleceğini ve hangi değişkene atanacağınıda söylememiz gerekiyor. Gelen verideki değişken ismi ile bizim kodlarda kullandığımız isim farklı o yüzden bunu belirtmemiz gerekiyor.

![image](https://user-images.githubusercontent.com/109730490/192270702-565b9cb3-e3b7-4d63-bc10-55b940e1ab57.png)

![image](https://user-images.githubusercontent.com/109730490/192270769-02520333-de64-42ae-96b3-e6725e0b3f18.png)

> Verideki değişken isimlerini SerializedName olarak ekledik.

> Retrofit bizden arayüzle çalışmamızı istediği için birarayüz oluşturacağız. Bundan önce Retrofit servisi için bir paket oluşturacağız. Sonra içinde bir interface dosyası açıyoruz. (kotlin classfile > interface) ve FoodAPI ismini vereceğiz:

## REQUEST (GET - POST)

> Request'ler yani istekler birkaç farklı şekilde kategorilendirilebilir. 

- GET : Verileri çekeceksek, bir API'dan ya da sunucudan verileri çekip kendi uygulamamız içinde kullanacaksak bu GET isteği olur. 

- POST : Genelde sunucuya bir veri yollayacaksak POST isteği olur. 

> Bu işlemleri yapmak için başka yollarda kullanılabilir fakat genelde GET ve POST kullanılır. 

## RxJava OBSERVABLE TİPLERİ

![image](https://user-images.githubusercontent.com/109730490/192591727-e46aeffb-9fe1-4eff-9646-daf2b17f2834.png)

> Yukarıdakilerin hepsi observable yani gözlemlenebilir. Hepsi bir observable oluşturuyor ve bu observable'da aldığımız veriler gözlemlenebiliyor. 

> En çok kullanılanı observable. Observable 0 ya da birkaç eleman alır ve sonunda bir hata ya da başarı döndürür. 

> Single ise bir tane single item alır veriyi bir kez çeker ve durur sonuç olarak bir hata mesajı ya da verinin kendisini döndürür. Veriyi bir ekz çekip kullanmak istediğimizde ideal bir senaryodur. 

## Retrofit'in kendisini oluşturacağımız servisi yazma

> Bunun için service paketinin içinde yeni bir kotlin class'ı açıp FoodAPIService olarak isimlendiriyoruz:

![image](https://user-images.githubusercontent.com/109730490/192594607-fc501ad5-dec5-45da-808a-05e759fd4598.png)

- NOT: Genelde android içinde değerini bir daha değiştirmeyeceğimiz string değişkenlerinin ismini büyük harflerle yazarız. (BASE_URL)

![image](https://user-images.githubusercontent.com/109730490/192599062-e63e74ee-cdfa-4784-95c0-60a0569a0336.png)

## RxJAva ve Disposable

> disposable kullan at demektir.

> Aslında bizim burada yapacağımız her istek bir disposable olacak. Çünkü çok fazla istek yapacağımız uygulamalar olabilir. Belki aynı fragment içinde 10 tane istek yapabiliriz. Ve lifecycle'a göre arkaplanda bu isteklerin devamlı açık kalması ya da devam etmesi hafıza yönetimi açısından zorluklar yaratabilir. Belirli bir yerden sonra bunlardan kurtulmamız gerekiyor ve disposable ne zaman işimiz biterse o zaman çağırıp kurtulabileceğimiz bir yapı ve bu RxJava'da. 

## EKLENTİLER (EXTENSIONS)

- Oluşturduğumuz paketlerden hiçbirine uymayan ama uygulamanın her tarafında kullanılabilecek, her türlü sınıfımızdan çağrılabilecek dosyalara util ismini veririz. Bu yüzden bu tip dosyaları bir util paketi açıp onun içine koyabiliriz. 

> util isimli bir package açıp içine Util isimli bir kotlin file açtık. (Dikkat kotlin class değil kotlin file)

![image](https://user-images.githubusercontent.com/109730490/193585028-2e9eb54f-3d69-46f6-9b90-9b2c80a26f22.png)

> Eklenti; daha önce yazdığımız sınıflara yaptığımız eklentiler. Bunu sadece kendi sınıflarımıza değil herhangi bir yere yapabiliyoruz. Örneğin imageView sınıfına veya INT sınıfına ya da String sınıfına herhangi bir fonksiyon ekleyebiliriz. Yaptığım eklenti gidip String class'ının kodlarını değiştirmez sadece ona bir eklenti yazmış oluyorum. 

![image](https://user-images.githubusercontent.com/109730490/193587696-bda84f77-3fce-414e-8671-966c250e3673.png)

> Kotlin'İn bu özelliği sayesinde istediğimiz sınıf için bir eklenti yazabiliriz. 

> Bu özelliği kullanarak glide'ı imageView'lara bir eklenti olarak tanımlamaya çalışacağız böylece bütün imageView'lar içinde çağırabileceğiz. Böyle yapmak zorunda değiliz ama bu şekilde yapmak bize kolaylık sağlayacak. 

## Apply

> apply; bir işlem yapılırken bununla birlikte yapılacak diğer özellikleri yazmamıza olanak tanıyor. 

## Util.kt

```kotlin
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
    Glide.with(context).setDefaultRequestOptions(options).load(this)
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
```

> Sonrasında FoodRecyclerAdapter'a bunu ekledik:

![image](https://user-images.githubusercontent.com/109730490/193611539-f8132d59-efc7-4e5d-a236-15aa105731f9.png)

## ROOM

> Room; SQLite özelliklerini ve komutlarını kullanabilmemiz için geliştirilmiş bir kütüphanedir. Sadece SQLite ile uygulama yapabiliriz ama android eğer SQLite kullanıyorsak Room'uda entegre etmemizi öneriyor çünkü uygulamayı daha verimli hale getiriyor. 

> Bu bölümde internetten indirdiğimiz verileri SQLite'a kaydedip kendi oluşturduğumuz algoritmaya göre belli bir zaman sonra tekrar internetten çekilmesini aksi durumda SQLite'tan çekilmesini sağlayacağız. 

> SQLite'ın içerisinde tablolar, kolonlar ve satırlar var. Kolonların ne olacağını söylememiz gerekiyor, hangi değerlerin kolon olarak kaydedileceğini söylememiz gerekiyorki değerler ilgili kolonun altına yerleştirilebilsin. 

> Room'a ilgili data sınıfının tablo olarak kullanılacağını söylememiz gerekiyor. 

> Bunu yapmak için Food.kt isimli dosyayı açalım. Ve sınıfın başına @Entity yazalım. Bu androidx.room'dan geliyor. Bunu yaptığımızda bir sınıfı Entity olarak işaretlemiş oluyoruz ve bu sınıf artık SQLite'a bir tablo olarak kaydedilmek için hazır hale gelir. Ayrıca her Entity'de mutlaka primary key bulundurmamız gerekir. Primary key dediğimiz şey bir id yani kaydedeceğimiz verilere bir id vermemiz gerekiyor. 

> Çektiğimiz verilerde id yok ama biz oluşturabiliriz. Zaten SQLite id'leri otomatik olarak artırabiliyor. 

> Artık SQLite içindeki table'ımızın adı Food olacak. 

![image](https://user-images.githubusercontent.com/109730490/196647537-98925aff-5ac1-4e3f-94ef-b7277d5040b6.png)

> Fakat istersem eğer şekildeki gibi başka bir table adı girebilirim. 

![image](https://user-images.githubusercontent.com/109730490/196647631-ba44011e-0b46-4885-80b4-feb960491c83.png)

> Bazı durumlarda benimIsim değişkeninin ismi diğerlerinden farklı olduğu için hata verdiğini görebiliriz ve hata değişken ismini diğerleriyle aynı şekilde "isim" olarak güncellemeden gitmez. Hata verip değişken ismini bulamadım derse bu şekilde çözmeliyiz. Bunu yapmak için değişkenin üzerine tıklayıp refactor>rename yaparak değiştirebiliriz. 

![image](https://user-images.githubusercontent.com/109730490/196648661-abe4159a-b8c7-4971-a955-5c9cae7c891f.png)

> Primary key'i oluşturup parantez açtığımızda kendim otomatik olarak oluşturayım mı diye soruyor. Bunun için autoGenerate = true dememiz gerekiyor. 

> FoodListFragment'tan FoodDetailFragment'amutlaka bir id atamamız gerekecek. Çünkü tıkladığımız verinin hangi veri olduğunu id ile ayrıştıracağız. (uuid)

![image](https://user-images.githubusercontent.com/109730490/196652721-8622dfbc-df56-47a4-82e3-c62b371ccb87.png)


## KAYNAKLAR

- [BTK AKADEMİ](https://www.btkakademi.gov.tr/portal/course/kotlin-ile-android-mobil-uygulama-gelistirme-ileri-seviye-10359)
