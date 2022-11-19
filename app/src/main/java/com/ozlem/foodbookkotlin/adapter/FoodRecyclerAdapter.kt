package com.ozlem.foodbookkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ozlem.foodbookkotlin.R
import com.ozlem.foodbookkotlin.databinding.FoodRecyclerRowBinding
import com.ozlem.foodbookkotlin.model.Food
import com.ozlem.foodbookkotlin.util.doPlaceholder
import com.ozlem.foodbookkotlin.util.downloadImage
import com.ozlem.foodbookkotlin.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

// Food : Data sınıfımız
class FoodRecyclerAdapter (val FoodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(), FoodClickListener {
    // ViewHolder'ı yazalım:
    // FoodViewHolder'da normal bir görünüm kullanıyoruz: (itemView : View)
    // Ama viewbinding kullanırken dataBinding view dediğimiz veri bağlama görünümü kullanacağımızı söylememiz gerekiyor.
    // Bu isim içinde bulunduğumuz dosyay göre oluşturuluyor.
    // Örneğin bu sınıf için FoodRecyclerRowBinding. FoodRecyclerRowBinding sınıfının hazır olarak oluşturulmuş olması gerekiyor.
    // Eğer çıkmadıysa build > rebuild yapıp tekrar denemeliyiz.
    class FoodViewHolder(var view : FoodRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // FoodRecyclerRow'u buraya inflater ile bağlayalım:
        // context'imizi parent'tan yani içerisine bağlı olduğumuz ana gruptan alabiliyoruz.
        val inflater = LayoutInflater.from(parent.context)
        // View'umuzu oluşturalım:
        // databinding yokken:
        //val view  = inflater.inflate(R.layout.food_recycler_row , parent , false)
        // databinding ile:
        val view = DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater, R.layout.food_recycler_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        // databinding yokken:
        /*
        // name : Food.kt'deki name:
        holder.itemView.foodNameID.text = FoodList.get(position).name
        holder.itemView.foodCalorieID.text = FoodList.get(position).calorie

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(FoodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        // Görsel kısmı eklemesi:
        // downloadImage() : url istiyor:
        holder.itemView.imageViewID.downloadImage(FoodList.get(position).image, doPlaceholder(holder.itemView.context))*/

        // DATABINDING İLE:
        holder.view.food = FoodList[position]
        // kendi listener'ımıza eşitlemek için this diyebiliriz:
        holder.view.listener = this

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

    override fun foodClicked(view: View) {
        // Besinlerden birine tıklanınca ne olacak burada yazacağız.
        val uuid = view.food_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}