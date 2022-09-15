# BTK-FoodBookKotlin

## Kotlin İle Android Mobil Uygulama Geliştirme İleri Seviye (BTK AKADEMİ) Notlarım

## Projede Kullnaılan Dosyalar:

- build.gradle Project
- build.gradle Module-app
- settings.gradle
- MainActivity.kt
- activity_main.xml

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




