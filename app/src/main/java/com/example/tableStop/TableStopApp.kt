package com.example.tableStop

import android.app.Application
import android.media.session.MediaSession
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
        var tokenInfo: TokenInfo? = null
    }

    override fun onCreate() {
        super.onCreate()

        val jsonFileString: String = NetworkUtils.getJsonFromAssets(this, "config.json")
        Log.d("Data", jsonFileString)

        val appKey: Credentials = Gson().fromJson(jsonFileString, Credentials::class.java)
        ClientID = appKey.ClientID
        ClientSecret = appKey.ClientSecret

        Log.d("App Key", ClientID)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
                tokenInfo = TokenUtils.parseTokenJSON(tokenJson)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                accessToken = tokenInfo?.access_token
            }
        }



//        suspend fun getTokenSuspend(): String? {
//            val token = accessToken
//            if (!token.isNullOrEmpty()) return token
//            val newToken = withContext(Dispatchers.IO) {
//                var tokenJson: String? = null
//                try {
//                    tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//                tokenJson
//            }
//            return newToken
//        }
    }

}
