package com.ozlem.foodbookkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ozlem.foodbookkotlin.R
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    // Ekledik:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Butona tıklayınca ne olacağını yazalım:
        foodListButtonID.setOnClickListener{
            // Bir action oluşturalım:
            // FoodListFragmentDirections bizim için otomatik olarak navigation framework'ü tarafından oluşturuldu.
            // Bizde action'ı o sınıftan oluşturacağız:
            // Bizden parametre olarak oluşturduğumuz argument için id isteniyor:
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(3)
            // Parametre olarak bir görünüm istiyor.
            // .navigate diyerek hangi action'a gideceğimi söyleyebilirim:
            Navigation.findNavController(it).navigate(action)
        }
    }
}