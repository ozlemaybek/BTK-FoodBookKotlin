package com.ozlem.foodbookkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozlem.foodbookkotlin.R

class FoodDetailFragment : Fragment() {

    // Defining:
    private var foodID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    // Biz ekledik:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // argument'ları alalım:
        // argument'lar varsa bize it içerisinde nullable olmadan veriliyor:
        arguments?.let {
            // FoodDetailFragmentArgs: navigation framework'ünün bizim için otomatik olarak oluşturduğu sınıf
            // fromBundle: Hangi bohçadan alacak (Bundle bohça/demet demek)
            foodID = FoodDetailFragmentArgs.fromBundle(it).foodArgumentID
            println(foodID)
        }

    }
}