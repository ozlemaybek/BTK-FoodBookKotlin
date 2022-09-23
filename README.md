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

> Projedeki Teknolojiler: MVVM, Retrofit, Room, DataBinding

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

### PROGRESS BAR (Yükleniyor İkonu)

![image](https://user-images.githubusercontent.com/109730490/190859907-845ac830-3dc7-4291-8ae4-9fa258d8d18d.png)

## RecycleView tasarımı

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

DATA CLASS: İnternetten ya da bir sunucudan veri çekeceksek ve tek yapmak istediğimiz o verilerden bir model oluşturmaksa bizim için ideal bir yapı sunuyor. Bir data class oluşturduğumuzda primary constructor kullanmalıyız yoksa hata verir çünkü data class'ın amacı model oluşturmak. Primary constructor'ın içine isteyeceğimiz değişkenleri yazmalıyız. 

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







## KAYNAKLAR

- [BTK AKADEMİ](https://www.btkakademi.gov.tr/portal/course/kotlin-ile-android-mobil-uygulama-gelistirme-ileri-seviye-10359)
