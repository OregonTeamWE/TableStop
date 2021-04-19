package com.example.tableStop.homeView

import android.net.Uri
import com.example.tableStop.TableStopApp
import com.example.tableStop.utils.NetworkUtils
import com.example.tableStop.utils.SearchUtils

class HomeDataFetch {
    private val dndLink: String = buildHomeSearchURL(6, 0, "44109")
    private val rpgLink: String = buildHomeSearchURL(6, 0, "2543")
    private val diceLink: String = buildOtherSearchURL("dice", 6, 0, "7317")
    private val miniatureLink: String = buildHomeSearchURL(6, 0, "16486")
    private val merchandiseLink: String = buildOtherSearchURL("merchandise", 6, 0, "233")

    fun fetchData() {
        try {
            HomeFragment.dndInfo = NetworkUtils.doHttpGet(dndLink, TableStopApp.accessToken)
            HomeFragment.rpgInfo = NetworkUtils.doHttpGet(rpgLink, TableStopApp.accessToken)
            HomeFragment.diceInfo = NetworkUtils.doHttpGet(diceLink, TableStopApp.accessToken)
            HomeFragment.bookInfo = NetworkUtils.doHttpGet(miniatureLink, TableStopApp.accessToken)
            HomeFragment.merchandiseInfo =
                NetworkUtils.doHttpGet(merchandiseLink, TableStopApp.accessToken)
        } catch (e: NullPointerException) {
            println(e)
        } finally {
            HomeFragment.dndInfo = NetworkUtils.doHttpGet(dndLink, TableStopApp.accessToken)
            HomeFragment.rpgInfo = NetworkUtils.doHttpGet(rpgLink, TableStopApp.accessToken)
            HomeFragment.diceInfo = NetworkUtils.doHttpGet(diceLink, TableStopApp.accessToken)
            HomeFragment.bookInfo = NetworkUtils.doHttpGet(miniatureLink, TableStopApp.accessToken)
            HomeFragment.merchandiseInfo =
                NetworkUtils.doHttpGet(merchandiseLink, TableStopApp.accessToken)
        }
    }

    private fun buildHomeSearchURL(limit: Int, offset: Int, category: String?): String {
        return Uri.parse(SearchUtils.EBAY_SEARCH_URL).buildUpon()
            .appendQueryParameter("category_ids", category.toString())
            .appendQueryParameter("limit", limit.toString())
            .appendQueryParameter("offset", offset.toString())
            .toString()
    }

    private fun buildOtherSearchURL(
        name: String?,
        limit: Int,
        offset: Int,
        category: String?
    ): String {
        return Uri.parse(SearchUtils.EBAY_SEARCH_URL).buildUpon()
            .appendQueryParameter("q", name)
            .appendQueryParameter("category_ids", category.toString())
            .appendQueryParameter("limit", limit.toString())
            .appendQueryParameter("offset", offset.toString())
            .toString()
    }
}