package com.example.tableStop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.dataClass.Status

class ProductViewModel : ViewModel() {
    private var mSearchResults: LiveData<List<ProductInfo>>? = null
    private var mLoadingStatus: LiveData<Status>? = null
    private var mRepository: ProductSearchRepository? = null

    fun searchViewModel() {
        mRepository = ProductSearchRepository()
        mSearchResults = mRepository!!.getSearchResults()
        mLoadingStatus = mRepository!!.getLoadingStatus()
    }

    fun getSearchResults(): LiveData<List<ProductInfo>>? {
        return mSearchResults
    }

    fun getLoadingStatus(): LiveData<Status>? {
        return mLoadingStatus
    }

    fun loadSearchResults(query: String?) {
        if (query != null) {
            mRepository!!.loadProduct(query)
        }
    }

}