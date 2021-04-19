package com.example.tableStop.socialView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tableStop.socialView.SocialRecyclerAdapter
import com.example.tableStop.DataSource
import com.example.tableStop.R

import kotlinx.android.synthetic.main.fragment_social.*

class SocialFragment : Fragment() {

    private lateinit var socialAdapter: SocialRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social, container, false)
    }

    private fun addDataSet() {
        val data = DataSource.createDataSet()
        socialAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            recycler_view.layoutManager = LinearLayoutManager(activity)
            val topSpacingDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecoration)
            socialAdapter = SocialRecyclerAdapter()
            recycler_view.adapter = socialAdapter
        }

    }

}