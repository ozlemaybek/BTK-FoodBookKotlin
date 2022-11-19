package com.ozlem.foodbookkotlin.adapter

import android.view.View

// Burada listedeki herhangi bir bsine tıklanınca ne olacak bunu yazacağız ve bize bir view verecek yani nereden geldiğimizi verecek.
interface FoodClickListener {
    fun foodClicked(view : View)
}