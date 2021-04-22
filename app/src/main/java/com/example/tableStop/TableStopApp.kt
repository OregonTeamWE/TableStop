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
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

@Suppress("unused")
class TableStopApp : Application() {

    companion object {
        var accessToken: String? = null
        var tokenJson: String = ""
        lateinit var ClientID: String
        lateinit var ClientSecret: String
    }

    override fun onCreate() {
        super.onCreate()

        val jsonFileString: String = NetworkUtils.getJsonFromAssets(this, "config.json")
        Log.d("Data", jsonFileString)

        val appKey: Credentials = Gson().fromJson(jsonFileString, Credentials::class.java)
        ClientID = appKey.ClientID
        ClientSecret = appKey.ClientSecret

        Log.d("App Key", ClientID)

        suspend fun getTokenSuspend(): String? {
            val token = accessToken
            if(!token.isNullOrEmpty()) return token

            val newToken = withContext(Dispatchers.IO){
                try {
                    tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
                } catch (e: Exception){
                    e.printStackTrace()
                }
                tokenJson
            }
            accessToken = newToken
            return newToken
        }
    }

}
