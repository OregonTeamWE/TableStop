package com.example.tableStop.homeView

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tableStop.MainActivity
import com.example.tableStop.R
import com.example.tableStop.TableStopApp
import com.example.tableStop.dataClass.ProductInfo
import com.example.tableStop.searchView.ShopMoreFragment
import com.example.tableStop.utils.NetworkUtils
import com.example.tableStop.utils.TokenUtils
import com.example.tableStop.viewModel.ProductViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), MaterialSearchBar.OnSearchActionListener {
    companion object {
        lateinit var dndInfo: String
        lateinit var rpgInfo: String
        lateinit var diceInfo: String
        lateinit var bookInfo: String
        lateinit var merchandiseInfo: String

        private const val REQUEST_CODE_STT = 1

        var dndData = ArrayList<ProductInfo>()
        var rpgData = ArrayList<ProductInfo>()
        var diceData = ArrayList<ProductInfo>()
        var bookData = ArrayList<ProductInfo>()
        var merchandiseData = ArrayList<ProductInfo>()
    }

    private val mViewModel: ProductViewModel by activityViewModels()


    lateinit var dndRecyclerAdapter: HomeRecyclerAdapter
    lateinit var rpgRecyclerAdapter: HomeRecyclerAdapter
    lateinit var diceRecyclerAdapter: HomeRecyclerAdapter
    lateinit var bookRecyclerAdapter: HomeRecyclerAdapter
    lateinit var merchandiseRecyclerAdapter: HomeRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dndPB = getView()?.findViewById(R.id.dnd_loading_indicator) as ProgressBar
        val rpgPB = getView()?.findViewById(R.id.rpg_loading_indicator) as ProgressBar
        val dicePB = getView()?.findViewById(R.id.dice_loading_indicator) as ProgressBar
        val bookPB = getView()?.findViewById(R.id.book_loading_indicator) as ProgressBar
        val merchandisePB =
            getView()?.findViewById(R.id.merchandise_loading_indicator) as ProgressBar

        val btn1 = getView()?.findViewById(R.id.load_more_button) as ImageButton
        val btn2 = getView()?.findViewById(R.id.load_more_rpg) as ImageButton
        val btn3 = getView()?.findViewById(R.id.load_more_dice) as ImageButton
        val btn4 = getView()?.findViewById(R.id.load_more_books) as ImageButton
        val btn5 = getView()?.findViewById(R.id.load_more_merchandise) as ImageButton
        val searchBar = getView()?.findViewById(R.id.search_bar) as MaterialSearchBar

        val refreshBtn = getView()?.findViewById(R.id.refresh_button) as ImageButton

        searchBar.setOnSearchActionListener(this)
        if (searchBar.isSearchOpened) {
            //close search when return
            searchBar.closeSearch()
        }
        //view model using cached data
        mViewModel.getSearchResults()?.observe(viewLifecycleOwner,
            Observer<List<ProductInfo>> { productInfos: List<ProductInfo>? ->
                dndRecyclerAdapter.setInfo(productInfos as ArrayList<ProductInfo>)
            })

        shop_dnd_recycler_view.apply {
            shop_dnd_recycler_view.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            dndRecyclerAdapter = HomeRecyclerAdapter()
            shop_dnd_recycler_view.adapter = dndRecyclerAdapter
        }

        shop_rpg_recycler_view.apply {
            shop_rpg_recycler_view.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rpgRecyclerAdapter = HomeRecyclerAdapter()
            shop_rpg_recycler_view.adapter = rpgRecyclerAdapter
        }

        shop_dice_recycler_view.apply {
            shop_dice_recycler_view.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            diceRecyclerAdapter = HomeRecyclerAdapter()
            shop_dice_recycler_view.adapter = diceRecyclerAdapter
        }

        shop_book_recycler_view.apply {
            shop_book_recycler_view.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            bookRecyclerAdapter = HomeRecyclerAdapter()
            shop_book_recycler_view.adapter = bookRecyclerAdapter
        }

        shop_merchandise_recycler_view.apply {
            shop_merchandise_recycler_view.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            merchandiseRecyclerAdapter = HomeRecyclerAdapter()
            shop_merchandise_recycler_view.adapter = merchandiseRecyclerAdapter
        }

        btn1.setOnClickListener { transferView("DnD") }

        btn2.setOnClickListener { transferView("RPG") }

        btn3.setOnClickListener { transferView("Dice") }

        btn4.setOnClickListener { transferView("Miniatures") }

        btn5.setOnClickListener { transferView("Merchandise") }

        refreshBtn.setOnClickListener {
            val homeFragment = HomeFragment()
            val mainActivity = MainActivity()
            mainActivity.getToken()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, homeFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        Log.d("Token info in fragment", TableStopApp.accessToken.toString())

        viewLifecycleOwner.lifecycleScope.launch {
            //Check if the network is connected
            Log.d("HomeFragment", TableStopApp.accessToken.toString())

            try {
                TableStopApp.tokenJson = NetworkUtils.doHttpPost(TokenUtils.buildTokenURL())
                TableStopApp.tokenInfo = TokenUtils.parseTokenJSON(TableStopApp.tokenJson)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                TableStopApp.accessToken = TableStopApp.tokenInfo?.access_token
            }

            if (TableStopApp.accessToken != null) {
                val homeDataFetch = HomeDataFetch()
                try {
                    homeDataFetch.checkData()
                    homeDataFetch.fetchData()
                } catch (e: Exception) {
                    println(e)
                } finally {
                    homeDataFetch.setData()
                }
            } else {
                home_data.visibility = View.GONE
                error_message_home.visibility = View.VISIBLE
                refresh_button.visibility = View.VISIBLE
            }

            dndRecyclerAdapter.setInfo(dndData)
            dndPB.visibility = View.GONE

            rpgRecyclerAdapter.setInfo(rpgData)
            rpgPB.visibility = View.GONE

            diceRecyclerAdapter.setInfo(diceData)
            dicePB.visibility = View.GONE

            bookRecyclerAdapter.setInfo(bookData)
            bookPB.visibility = View.GONE

            merchandiseRecyclerAdapter.setInfo(merchandiseData)
            merchandisePB.visibility = View.GONE

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        val s: String = if (enabled) "enabled" else "disabled"
        Toast.makeText(activity, "Search $s", Toast.LENGTH_SHORT).show()
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        transferView(text.toString())
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_SPEECH -> {
                println("Using Speech")
                openSpeechRecognition()
            }
        }
    }

    private fun openSpeechRecognition() {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        // Language model defines the purpose, there are special models for other use cases, like search.
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Adding an extra language, you can use any language from the Locale class.
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        // Text that shows up on the Speech input prompt.
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
        try {
            // Start the intent for a result, and pass in our request code.
            startActivityForResult(sttIntent, REQUEST_CODE_STT)
        } catch (e: ActivityNotFoundException) {
            // Handling error when the service is not available.
            e.printStackTrace()
            Toast.makeText(activity, "Your device does not support STT.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        transferView(recognizedText)
                    }
                }
            }
        }
    }

    private fun transferView(parameter: String) {
        println("BUTTON clicked$parameter")
        val shopMoreFragment = ShopMoreFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        shopMoreFragment.apply { arguments = Bundle().apply { putString("parameter", parameter) } }
        fragmentTransaction.replace(R.id.fragment_container, shopMoreFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}