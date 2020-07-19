package com.brown.kaew.coinranking.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.brown.kaew.coinranking.Injector
import com.brown.kaew.coinranking.R
import com.brown.kaew.coinranking.api.CoinRankingSearchResponse
import com.brown.kaew.coinranking.ui.CoinAdapter
import com.google.gson.Gson
import com.google.gson.stream.JsonReader

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = javaClass.simpleName

    private lateinit var viewModel: MainViewModel

    private lateinit var list: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        list = view.findViewById(R.id.list)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //get view model
        viewModel = ViewModelProviders.of(this, Injector.provideMainViewModelFactory())
            .get(MainViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        setupScrollListener()

        val adapter = CoinAdapter()
        list.adapter = adapter

        // Test UI from local data
//        loadDataFromLocalJsonFile(adapter)


        viewModel.firstLoadCoins()
        subscribeUI(adapter)

    }

    private fun subscribeUI(adapter: CoinAdapter) {
        viewModel.getCoins()
            .observe(this,
                Observer { list ->
                    Log.d(TAG, "list size: ${list.size}")
                    adapter.submitList(list)
                    adapter.notifyDataSetChanged()
                })
    }

    private fun setupScrollListener() {
        val layoutManager = list.layoutManager as LinearLayoutManager

        list.addOnScrollListener(object : OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun loadDataFromLocalJsonFile(adapter: CoinAdapter) {
        activity?.applicationContext?.assets?.open("coins.json").use { inputStream ->
            JsonReader(inputStream?.reader()).use { jsonReader ->
                val res = Gson().fromJson<CoinRankingSearchResponse>(
                    jsonReader,
                    CoinRankingSearchResponse::class.java
                )
                adapter.submitList(res.data.coins)
            }
        }
    }

}