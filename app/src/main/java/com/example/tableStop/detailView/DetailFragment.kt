package com.example.tableStop.detailView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tableStop.R
import com.example.tableStop.TableStopApp
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.dummy.DummyContent
import com.example.tableStop.utils.ItemUtils
import com.example.tableStop.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    private var productInfo: ProductInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productInfo = it.getSerializable(ARG_PARAM_ProductInfo) as? ProductInfo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get item detail
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("Details", "onViewCreated: " + productInfo?.itemId)
            val itemInfo = withContext(Dispatchers.IO) {
                NetworkUtils.doHttpGet(
                    ItemUtils.buildURL(productInfo?.itemId),
                    TableStopApp.accessToken
                )
            }
            Log.d("Information returned", itemInfo) //see Sample Output in ExampleOutput.json
            val itemData = ItemUtils.parseItemResultJSON(itemInfo)

            title.text = itemData?.title
            price.text = itemData?.price
            condition.text = itemData?.condition
            brand.text = itemData?.brand
            seller.text = itemData?.seller

//            Glide.with(view.context)
//                .load(productInfo?.image)
//                .into(image)
            viewPager.adapter = ViewPagerAdapter(context, itemData.images)

            button_buy.setOnClickListener {
                // val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                // startActivity(browserIntent)
                // val url = "http://www.google.com"
                // var url = "https://www.seeedstudio.com/LambdaChip-Alonzo-Standard-Version-p-4854.html"
                val url = itemData?.url

                Log.d("Url", "Clicking ${productInfo?.image}")
                Log.d("Url", "Clicking $url")

                if (url != null) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    ContextCompat.startActivity(it.context, intent, null)
                } else {
                    button_buy.visibility = View.GONE
                }
            }

            val scaleAnimation = ScaleAnimation(
                0.7f,
                1.0f,
                0.7f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.7f,
                Animation.RELATIVE_TO_SELF,
                0.7f
            )
            scaleAnimation.duration = 500
            val bounceInterpolator = BounceInterpolator()
            scaleAnimation.interpolator = bounceInterpolator

            button_favorite.isChecked =
                productInfo?.let { DummyContent.isProductInCollect(it.itemId) } == true

            button_favorite.setOnClickListener {
                it?.startAnimation(scaleAnimation)

                if ((it as ToggleButton).isChecked) {
                    productInfo?.let { it1 -> DummyContent.addProduct(it1) }
                } else {
                    productInfo?.let { it1 -> DummyContent.removeProduct(it1.itemId) }
                }
                DummyContent.save(it.context)
                // https://developer.android.com/training/data-storage/shared-preferences
                // val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                // with (sharedPref.edit()) {
                //     putInt(getString(R.string.saved_high_score_key), newHighScore)
                //     commit()
                // }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        // fun newInstance(param1: String, param2: String) =
        fun newInstance(productInfo: ProductInfo) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                    // putString(ARG_PARAM2, param2)
                    putSerializable(ARG_PARAM_ProductInfo, productInfo)
                }
            }

        private const val ARG_PARAM_ProductInfo = "productInfo"

    }
}