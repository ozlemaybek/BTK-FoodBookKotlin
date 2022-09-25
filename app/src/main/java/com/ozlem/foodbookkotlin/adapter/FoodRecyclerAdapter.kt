package com.ozlem.foodbookkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ozlem.foodbookkotlin.R
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

// Food : Data sınıfımız
class FoodRecyclerAdapter (val FoodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {
    // ViewHolder'ı yazalım:
    class FoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // FoodRecyclerRow'u buraya inflater ile bağlayalım:
        // context'imizi parent'tan yani içerisine bağlı olduğumuz ana gruptan alabiliyoruz.
        val inflater = LayoutInflater.from(parent.context)
        // View'umuzu oluşturalım:
        val view  = inflater.inflate(R.layout.food_recycler_row , parent , false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        // name : Food.kt'deki name:
        holder.itemView.foodNameID.text = FoodList.get(position).name
        holder.itemView.foodCalorieID.text = FoodList.get(position).calorie
        // Görsel kısmı eklenecek.

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(0)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        // Kaç tane row oluşturulacağını belirtmiş olduk:
        return FoodList.size
    }

    // newFoodList bir liste olacak ve içinde Food'ları barındıracak:
    /*Bir yerde bir güncelleme yapmak istersem yeni listemi aldığım takdirde direkt recyclerView'un adapter'ünü
    oluşturduğum objeyi çağırıp direkt foodListUpdate fonksiyonunu çağırmak ve yeni listeyi vermek yeterli olacaktır. */
    fun foodListUpdate(newFoodList : List<Food>){
        // Şuanda bulunduğun besin listesini temizle:
        FoodList.clear()
        // yeni besin listesinin hepsini besin listesinin içine ekle:
        FoodList.addAll(newFoodList)
        // Aşağıdaki yazımı adapter.notifyDataSetChange() olarak öğrenmiştik fakat şimdi zaten adapter'ın içinde
        // olduğumuz için direkt çağırdık:
        notifyDataSetChanged()
    }
}