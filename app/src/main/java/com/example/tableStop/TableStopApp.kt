package com.example.tableStop

import android.app.Application
import android.util.Log
import com.example.tableStop.dataClass.Credentials
import com.example.tableStop.dataClass.TokenInfo
import com.example.tableStop.utils.NetworkUtils
import com.example.tableStop.utils.TokenUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("unused")
class TableStopApp : Application() {

    companion object {
        var accessToken: String? = null
        lateinit var ClientID: String
        lateinit var ClientSecret: String
    }

    fun getToken(): String? {
        return accessToken
    }

    override fun onCreate() {
        super.onCreate()

        val getToken: String = TokenUtils.buildTokenURL()
        val jsonFileString: String = NetworkUtils.getJsonFromAssets(this, "config.json")
        Log.d("Data", jsonFileString)


        val appKey: Credentials = Gson().fromJson(jsonFileString, Credentials::class.java)
        ClientID = appKey.ClientID
        ClientSecret = appKey.ClientSecret

        Log.d("App Key", ClientID)

        CoroutineScope(Dispatchers.IO).launch {
            val tokenJson = NetworkUtils.doHttpPost(getToken)
            Log.d("Token from app launch", tokenJson)

            val tokenInfo: TokenInfo = TokenUtils.parseTokenJSON(tokenJson)
            accessToken = tokenInfo.access_token

            Log.d("Token set to", getToken)
        }
    }

}
