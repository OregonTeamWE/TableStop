package com.example.tableStop.profileView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tableStop.R
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.detailView.DetailFragment
import com.example.tableStop.dummy.DummyContent.DummyItem


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProfileRecyclerViewAdapter(
//    private val values: List<DummyItem>
//     private val values: MutableList<DummyItem>
//     private val values: MutableList<String>
    private val values: List<ProductInfo>
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            // .inflate(R.layout.fragment_item, parent, false)
            .inflate(R.layout.layout_shop_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        // holder.idView.text = item.id
        // holder.contentView.text = item.content
        // holder.idView.text = item.price
        // holder.contentView.text = item.title
        //
        // Glide.with(holder.image.context)
        //         .load(item.image)
        //         .into(holder.image)

        holder.textDescription.text = item.title
        holder.textPrice.text = item.price

        holder.cardView.setOnClickListener {
            // Toast.makeText(it.getContext(), "Item Clicked " + itemInfo.itemId, Toast.LENGTH_SHORT).show();

            val activity = it!!.context as AppCompatActivity
            val fragmentManager =  activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
            // fragmentTransaction.replace(R.id.fragment_container, DetailFragment.newInstance(itemInfo.itemId, ""))
            fragmentTransaction.replace(R.id.fragment_container, DetailFragment.newInstance(item))

            // TODO: fix return button crash
            // var mFragment = DetailFragment.newInstance(item)
            // if (mFragment.isAdded) {
            //     fragmentTransaction.show(mFragment)
            // } else {
            //     fragmentTransaction.replace(R.id.fragment_container, mFragment)
            // }

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

//            Log.d("Binding Image", itemInfo.image)

        Glide.with(holder.itemImage.context)
                .load(item.image)
                .into(holder.itemImage)

        // holder.collectButton.isChecked = true

        // val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
        // scaleAnimation?.duration = 500
        // val bounceInterpolator = BounceInterpolator()
        // scaleAnimation?.interpolator = bounceInterpolator

        // holder.collectButton.setOnCheckedChangeListener(object:View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        //     override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        //         // p0?.startAnimation(scaleAnimation);
        //         // Log.d("fav", "am i here") //To change body of created functions use File | Settings | File Templates.
        //         Log.d("position", position.toString())
        //
        //         // TODO: remove it from list
        //         values.removeAt(position)
        //         // holder.run {
        //         //     itemView.post(Runnable() {
        //         //                 notifyDataSetChanged()
        //         //             })
        //         // }
        //         // holder.collectButton.post {
        //             // kotlin.run { notifyDataSetChanged() }
        //             // notifyDataSetChanged()
        //         // }
        //
        //         // notifyItemRemoved(position)
        //         // notifyDataSetChanged()
        //     }
        //
        //     override fun onClick(p0: View?) {
        //         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //         Log.d("click", position.toString())
        //     }
        // });

        /*// holder.collectButton.setOnClickListener {  }()
        holder.collectButton.setOnClickListener {
            values.removeAt(position);
            // notifyItemRemoved(position)
            notifyDataSetChanged()
        }

        holder.buyButton.setOnClickListener {
            // val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            // startActivity(browserIntent)
            // val url = "http://www.google.com"
            var url = "https://www.seeedstudio.com/LambdaChip-Alonzo-Standard-Version-p-4854.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(holder.buyButton.context, intent, null)
        }*/
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // val idView: TextView = view.findViewById(R.id.item_number)
        // val contentView: TextView = view.findViewById(R.id.content)
        // val image: ImageView = view.findViewById(R.id.image_collect)

        // val collectButton: ToggleButton = view.findViewById(R.id.button_favorite)
        // val collectButton: ImageButton = view.findViewById(R.id.button_favorite)

        // val buyButton: Button = view.findViewById(R.id.button_buy)


        val cardView: CardView = itemView.findViewById(R.id.recyclerview_item)
        val textDescription: TextView = itemView.findViewById(R.id.shop_item_description)
        val textPrice: TextView = itemView.findViewById(R.id.shop_item_price)
        val itemImage: ImageButton = itemView.findViewById(R.id.shop_item_image)


        override fun toString(): String {
            // return super.toString() + " '" + contentView.text + "'"
            return super.toString() + " '" + textDescription.text + "'"
        }
    }
}