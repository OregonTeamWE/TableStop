package com.example.tableStop.searchView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tableStop.R
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.detailView.DetailFragment

class SearchRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var info: ArrayList<ProductInfo> = ArrayList()

    fun setInfo(parseInfo: ArrayList<ProductInfo>) {
        info = parseInfo
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ShopViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_layout, parent, false)
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder) {
            is ShopViewHolder -> {
                viewHolder.bind(info[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return info.size
    }

    class ShopViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val cardView: CardView = itemView.findViewById(R.id.search_recycler)
        private val textDescription: TextView = itemView.findViewById(R.id.shop_item_description)
        private val textPrice: TextView = itemView.findViewById(R.id.shop_item_price)
        private val itemImage: ImageButton = itemView.findViewById(R.id.shop_item_image)

        fun bind(itemInfo: ProductInfo) {

            textDescription.text = itemInfo.title
            textPrice.text = itemInfo.price

//            Log.d("Binding Image", itemInfo.image)

            cardView.setOnClickListener {
                // Toast.makeText(it.getContext(), "Item Clicked " + itemInfo.itemId, Toast.LENGTH_SHORT).show();
                val activity = it!!.context as AppCompatActivity
                val fragmentManager = activity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                // fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
                // fragmentTransaction.replace(R.id.fragment_container, DetailFragment.newInstance(itemInfo.itemId, ""))
                fragmentTransaction.replace(
                    R.id.fragment_container,
                    DetailFragment.newInstance(itemInfo)
                )

                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            itemImage.setOnClickListener {
                // Toast.makeText(it.getContext(), "Item Clicked " + itemInfo.itemId, Toast.LENGTH_SHORT).show();
                val activity = it!!.context as AppCompatActivity
                val fragmentManager = activity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                // fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
                // fragmentTransaction.replace(R.id.fragment_container, DetailFragment.newInstance(itemInfo.itemId, ""))
                fragmentTransaction.replace(
                    R.id.fragment_container,
                    DetailFragment.newInstance(itemInfo)
                )

                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            Glide.with(itemView.context)
                .load(itemInfo.image)
                .into(itemImage)
        }
    }
}