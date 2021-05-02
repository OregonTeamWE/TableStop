package com.example.tableStop.searchView

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tableStop.R
import com.example.tableStop.TableStopApp
import com.example.tableStop.dataClass.SearchResult
import com.example.tableStop.utils.NetworkUtils
import com.example.tableStop.utils.SearchUtils
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_shop_more.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ShopMoreFragment : Fragment(), MaterialSearchBar.OnSearchActionListener {
    lateinit var searchAdapter: SearchRecyclerAdapter
    lateinit var searchResult: String

    var searchData = SearchResult()
    var totalContentSize = 0

    var loading = true

    companion object {
        var viewPageNum: Int = 1
        lateinit var link: String
        private const val REQUEST_CODE_STT = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val visibleItemCount = mLayoutManager.childCount
        val pastVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition()

        val parameter = arguments?.getString("parameter")

//        val searchPage = getView()?.findViewById(R.id.search_page) as EditText
//        val showPage = getView()?.findViewById(R.id.show_page) as TextView
//        val jumpButton = getView()?.findViewById(R.id.jump_btn) as Button
//        val prevButton = getView()?.findViewById(R.id.prev_button) as Button
//        val nextButton = getView()?.findViewById(R.id.next_button) as Button

        val searchBar = getView()?.findViewById(R.id.search_bar) as MaterialSearchBar

        searchBar.setOnSearchActionListener(this)
        searchBar.setSpeechMode(false)
        searchBar.setPlaceHolder(parameter)

        println("Hey there! $parameter")
        link = SearchUtils.buildSearchURL(parameter, 12, (viewPageNum - 1) * 12)

        search_recycler_view.apply {
            search_recycler_view.layoutManager = mLayoutManager
            searchAdapter = SearchRecyclerAdapter()
            search_recycler_view.adapter = searchAdapter
        }

        resetSearch(link)

//        prevButton.setOnClickListener {
//            if (viewPageNum > 1) {
//                --viewPageNum
//                link = SearchUtils.buildSearchURL(parameter, 12, (viewPageNum - 1) * 12)
//                resetSearch(link)
//                Log.d("Prev Button", viewPageNum.toString())
//            }
//        }
//
//        nextButton.setOnClickListener {
//            ++viewPageNum
//            link = SearchUtils.buildSearchURL(parameter, 12, (viewPageNum - 1) * 12)
//            resetSearch(link)
//            Log.d("Next Button", viewPageNum.toString())
//        }
//
//        jumpButton.setOnClickListener {
//            viewPageNum = Integer.valueOf(searchPage.text.toString())
//            link = SearchUtils.buildSearchURL(parameter, 12, (viewPageNum - 1) * 12)
//            resetSearch(link)
//            Log.d("Jump Button", viewPageNum.toString())
//        }

        search_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val total = searchAdapter.itemCount

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        ++viewPageNum
                        link = SearchUtils.buildSearchURL(parameter, 12, (viewPageNum - 1) * 12)
                        resetSearch(link)
                    }


                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_more, container, false)
    }

    private fun resetSearch(link: String) {
        search_loading_indicator.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                searchResult = withContext(Dispatchers.IO) {
                    NetworkUtils.doHttpGet(
                        link,
                        TableStopApp.accessToken
                    )
                }
            } catch (e: NullPointerException) {
                println("ShopMoreFrag, 1$e")
            } finally {
                searchResult = withContext(Dispatchers.IO) {
                    NetworkUtils.doHttpGet(
                        link,
                        TableStopApp.accessToken
                    )
                }
            }

            try {
                while (searchData.itemSummaries.isEmpty() && SearchUtils.parseSearchResultJSON(
                        searchResult
                    ) != null
                )
                    searchData = SearchUtils.parseSearchResultJSON(searchResult)
            } catch (e: NullPointerException) {
                println("ShopMoreFrag, 2$e")
            } finally {
                if (SearchUtils.parseSearchResultJSON(searchResult) != null) {
                    searchData = SearchUtils.parseSearchResultJSON(searchResult)
                    totalContentSize = searchData.total
                } else {
                    totalContentSize = 0
                    error_message.visibility = View.VISIBLE
                    search_recycler_view.visibility = View.GONE
                }
                Log.d("ShopMoreFrag", totalContentSize.toString())
                searchAdapter.setInfo(searchData.itemSummaries)
//                showPage.text = "$viewPageNum/" + (totalContentSize / 12).toString()
                search_loading_indicator.visibility = View.GONE
                searchAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        val s: String = if (enabled) "enabled" else "disabled"
        Toast.makeText(activity, "Search $s", Toast.LENGTH_SHORT).show(); }

    override fun onSearchConfirmed(text: CharSequence?) {
        //TODO
        resetSearch(SearchUtils.buildSearchURL(text.toString(), 12, (viewPageNum - 1) * 12))
        search_bar.setPlaceHolder(text.toString())
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
            Toast.makeText(activity, "Your device does not support STT.", Toast.LENGTH_LONG)
                .show()
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
                        search_bar.openSearch()
                        search_bar.text = recognizedText
                    }
                }
            }
        }
    }
}