package com.example.tableStop.dummy

import android.content.Context
import com.example.tableStop.R
import com.example.tableStop.dataClass.ProductInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
//    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
//    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    var PRODUCTS_MAP: MutableMap<String, ProductInfo> = HashMap()

//    private val COUNT = 25

    init {
//        // Add some sample items.
//        for (i in 1..COUNT) {
//            addItem(createDummyItem(i))
//        }
    }

//    private fun addItem(item: DummyItem) {
//        ITEMS.add(item)
//        ITEM_MAP.put(item.id, item)
//    }

    // private fun addProduct(id: String) {
    fun addProduct(productInfo: ProductInfo) {
        // PRODUCTS.add(id)
        // PRODUCTS.add(productInfo.itemId)
        PRODUCTS_MAP.put(productInfo.itemId, productInfo)
    }
    fun removeProduct(id: String) {
        // PRODUCTS.remove(id)
        PRODUCTS_MAP.remove(id)
    }

    fun save(context: Context) {
        var jsonString = Gson().toJson(PRODUCTS_MAP)
        // Log.d("save collects", jsonString)

        // val type: Type = object : TypeToken<MutableMap<String?, ProductInfo?>?>() {}.type
        // val clonedMap: MutableMap<String, ProductInfo> = Gson().fromJson(jsonString, type)
        // Log.d("parse json string", clonedMap.count().toString())
        // clonedMap.toList().firstOrNull()?.second?.let { Log.d("parse json string", it.itemId) }

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(context.getString(R.string.preference_put_string_key), jsonString)
            apply()
        }
    }

    fun read(context: Context) {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return

        val defaultValue = ""
        val jsonString = sharedPref.getString(context.getString(R.string.preference_put_string_key), defaultValue)

        val type: Type = object : TypeToken<MutableMap<String?, ProductInfo?>?>() {}.type
        val clonedMap: MutableMap<String, ProductInfo> = Gson().fromJson(jsonString, type)
        // Log.d("read saved collects", clonedMap.count().toString())
        // clonedMap.toList().firstOrNull()?.second?.let { Log.d("first collect id", it.itemId) }
        PRODUCTS_MAP = clonedMap
    }

    fun getProducts(context: Context): List<ProductInfo> {
        read(context)
        return PRODUCTS_MAP.values.toList()
    }
    fun isProductInCollect(productId: String):Boolean {
        return PRODUCTS_MAP.containsKey(productId)
    }

//    private fun createDummyItem(position: Int): DummyItem {
//        // return DummyItem(position.toString(), "Item " + position, makeDetails(position))
//        return DummyItem(position.toString(), "Item " + position + ": LambdaChip Alonzo Standard Version", makeDetails(position))
//    }
//
//    private fun makeDetails(position: Int): String {
//        val builder = StringBuilder()
//        builder.append("Details about Item: ").append(position)
//        for (i in 0..position - 1) {
//            builder.append("\nMore details information here.")
//        }
//        return builder.toString()
//    }

    /**
     * A dummy item representing a piece of content.
     */
//    data class DummyItem(val id: String, val content: String, val details: String) {
//        override fun toString(): String = content
//    }
}