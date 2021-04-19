package com.example.tableStop.viewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.dataClass.Status
import com.example.tableStop.utils.SearchUtils


class ProductSearchRepository {
    private lateinit var mProductInfos: MutableLiveData<List<ProductInfo>>
    private lateinit var mLoadingStatus: MutableLiveData<Status>

    private lateinit var mCurrentQuery: String

    fun productSearchRepository() {
        mProductInfos = MutableLiveData()
        mProductInfos.value = null

        mLoadingStatus = MutableLiveData()
        mLoadingStatus.value = Status.SUCCESS

        mCurrentQuery = ""
    }

    fun getSearchResults(): LiveData<List<ProductInfo>> {
        return mProductInfos
    }

    fun getLoadingStatus(): MutableLiveData<Status> {
        return mLoadingStatus
    }

    fun loadProduct(query: String) {
        if (shouldExecuteSearch(query)) {
            mCurrentQuery = query

            mProductInfos.value = null
            mLoadingStatus.value = Status.LOADING

            val url = SearchUtils.buildSearchURL(query, 18, 0) as String
            Log.d("ViewModel", "Searching: $url")
        }
    }

    private fun shouldExecuteSearch(query: String): Boolean {
        return !TextUtils.equals(query, mCurrentQuery)
    }
}