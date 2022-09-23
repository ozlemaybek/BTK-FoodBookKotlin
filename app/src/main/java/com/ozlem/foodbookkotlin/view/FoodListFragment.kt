package com.ozlem.foodbookkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozlem.foodbookkotlin.R
import com.ozlem.foodbookkotlin.adapter.FoodRecyclerAdapter
import com.ozlem.foodbookkotlin.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    // View Model sınıfımızın objesini oluşturalım:
    private lateinit var viewModel : FoodListViewModel
    private val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

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
        // View Model initialize işlemini tamamlamak için ViewModelProviders isimli bir sınıf kullanıyoruz.
        // Bunun içinde bu fragment ile FoodListViewModel'ı bağlama işlemi yapacağız:
        // get içine hangi sınıf bizim view model sınıfımız ise onu yazıyoruz.
        viewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)
        // Eski kullanım:
        // ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        recyclerViewID.layoutManager = LinearLayoutManager(context)
        recyclerViewID.adapter = recyclerFoodAdapter

    }

    // gözlem yapıp güncel verileri alalım:


    fun observeLiveData(){

        // FoodListViewModel'da bulunan Live Data'lardan foods'u gözlemlemek için:
        // owner : bu yaşam döngüsünün sahibi kim.
        // observer : gözlemleyici. Bizesonuç olarak gözlemlenen veriyi verecek
        viewModel.foods.observe(this , Observer {  foods ->
            foods.let {
                recyclerViewID.visibility = View.VISIBLE
                recyclerFoodAdapter.foodListUpdate(foods)
            }
        } )

        // FoodListViewModel'da bulunan Live Data'lardan foodErrorMessage'ı gözlemlemek için:
        // viewLifecycleOwner: lifecycle owner kimse direkt bize onu getirir.
        viewModel.foodErrorMessage.observe(viewLifecycleOwner , Observer { error ->
            error?.let {
                if(it){
                    // foodErrorMessage'ın göründüğü textView'un ID'si : textViewID
                    textViewID.visibility = View.VISIBLE
                }else{
                    // hata mesajı gösterilmesin:
                    textViewID.visibility = View.GONE
                }
            }
        })
    }

}