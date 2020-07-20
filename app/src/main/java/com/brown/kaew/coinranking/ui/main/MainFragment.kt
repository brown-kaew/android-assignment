package com.brown.kaew.coinranking.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brown.kaew.coinranking.Injector
import com.brown.kaew.coinranking.R
import com.brown.kaew.coinranking.api.CoinRankingSearchResponse
import com.brown.kaew.coinranking.ui.CoinAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.stream.JsonReader

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = javaClass.simpleName

    private lateinit var viewModel: MainViewModel

    private lateinit var list: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var search_filter: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        list = view.findViewById(R.id.list)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        search_filter = view.findViewById(R.id.search_filter)
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

        setupPullToRequest()
        setupSearchFilter()
    }

    private fun setupPullToRequest() {
        swipeContainer.setOnRefreshListener {
            search_filter.text?.clear()
            search_filter.clearFocus()
            viewModel.refresh()
        }
    }

    private fun subscribeUI(adapter: CoinAdapter) {
        viewModel.getCoins()
            .observe(this,
                Observer { list ->
                    Log.d(TAG, "list size: ${list.size}")
                    adapter.submitList(list)
                    adapter.notifyDataSetChanged()
                    swipeContainer.isRefreshing = false;
                })
    }

    private fun setupSearchFilter() {

        search_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                updateCoinListFromInput()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        search_filter.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val imm = context?.getSystemService(InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(view!!.windowToken, 0)
            }
        }
    }

    private fun updateCoinListFromInput() {
        search_filter.text.let {
            if (it != null) {
                if (it.isEmpty() && viewModel.state == MainViewModel.State.SEARCH) {
                    viewModel.state = MainViewModel.State.NORMAL
                    search_filter.clearFocus()
                    viewModel.clearSearch()
                } else if (it.trim().isNotBlank()) {
                    viewModel.state = MainViewModel.State.SEARCH
                    viewModel.searchFilter(it.trim().toString())
                    list.scrollToPosition(0)
                }
            }
        }
    }

    private fun setupScrollListener() {
        val layoutManager = list.layoutManager as LinearLayoutManager

        list.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (search_filter.text.isNullOrEmpty() &&
                    viewModel.state == MainViewModel.State.NORMAL
                ) {
                    viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
                }
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