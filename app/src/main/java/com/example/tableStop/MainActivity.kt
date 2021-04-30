package com.example.tableStop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tableStop.gameToolView.GameToolsFragment
import com.example.tableStop.homeView.HomeFragment
import com.example.tableStop.profileView.ProfileFragment
import com.example.tableStop.socialView.SocialFragment
import com.example.tableStop.utils.NetworkUtils
import com.example.tableStop.utils.TokenUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val socialFragment = SocialFragment()
        val gameToolsFragment = GameToolsFragment()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                TableStopApp.tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
                TableStopApp.tokenInfo = TokenUtils.parseTokenJSON(TableStopApp.tokenJson)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                TableStopApp.accessToken = TableStopApp.tokenInfo?.access_token
                makeCurrentFragment(homeFragment)
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_shop -> makeCurrentFragment(homeFragment)
                R.id.nav_social -> makeCurrentFragment(socialFragment)
                R.id.nav_profile -> makeCurrentFragment(profileFragment)
                R.id.nav_gameTools -> makeCurrentFragment(gameToolsFragment)
            }
            true
        }
    }

    fun getToken(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                TableStopApp.tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
                TableStopApp.tokenInfo = TokenUtils.parseTokenJSON(TableStopApp.tokenJson)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                TableStopApp.accessToken = TableStopApp.tokenInfo?.access_token
            }
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
}

