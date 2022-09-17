# BTK-FoodBookKotlin

## Kotlin İle Android Mobil Uygulama Geliştirme İleri Seviye (BTK AKADEMİ) Notlarım

## Projede Kullnaılan Dosyalar:

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



