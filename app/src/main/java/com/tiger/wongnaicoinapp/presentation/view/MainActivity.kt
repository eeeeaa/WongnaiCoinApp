package com.tiger.wongnaicoinapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tiger.wongnaicoinapp.R
import com.tiger.wongnaicoinapp.data.model.Coin
import com.tiger.wongnaicoinapp.presentation.adapter.CoinListAdapter
import com.tiger.wongnaicoinapp.presentation.adapter.EndlessRecyclerViewScrollListener
import com.tiger.wongnaicoinapp.presentation.factory.MainViewModelFactory
import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinError
import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinResponse
import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinResponseFailure
import com.tiger.wongnaicoinapp.presentation.interactor.PresCoinResponseSuccess
import com.tiger.wongnaicoinapp.presentation.viewmodel.MainViewModel
import io.reactivex.android.BuildConfig
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    /*
    * View related logic
    *
    * */
    private lateinit var mainModel:MainViewModel
    private lateinit var adapter:CoinListAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var coinListview:RecyclerView
    private lateinit var emptyView:TextView
    val TAG = "main activity"
    var filterWord:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewModel
        mainModel = ViewModelProvider(this,MainViewModelFactory()).get(MainViewModel::class.java)
        initialization()
        observeData(true)
    }

    fun initialization(){
        //refresh layout
        val refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        refreshLayout.setOnRefreshListener {
            //refresh content
            adapter.clear()
            adapter.notifyDataSetChanged()
            filterWord = null
            scrollListener.resetState()
            mainModel.getCoinData(10,null,null,null,null,null)
            observeData(true)
            refreshLayout.isRefreshing = false
        }

        //recyclerview
        coinListview = findViewById(R.id.main_coin_list)
        emptyView = findViewById(R.id.empty_view)
        emptyView.visibility = View.GONE

        adapter = CoinListAdapter(this)
        var linearLayoutManager = LinearLayoutManager(this)
        coinListview.layoutManager = linearLayoutManager
        coinListview.adapter = adapter
        scrollListener = object:EndlessRecyclerViewScrollListener(linearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if(BuildConfig.DEBUG) Log.d(TAG, "page: $page count: $totalItemsCount")
                checkFilterWord(filterWord,page*10)
                observeData(false)
            }
        }
        coinListview.addOnScrollListener(scrollListener)
        checkFilterWord(filterWord)
        //search bar
        var searchbar = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        searchbar.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filterWord = p0
                checkFilterWord(filterWord)
                scrollListener.resetState()
                observeData(true)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }
    fun checkFilterWord(filterInput:String?,offset:Int?=null){
        //Seem to crash when search with symbols,slugs,or id when result is null
        val filter = filterInput?.lowercase()
        if(filter?.startsWith("symbol:") == true){
            val sub_filter = filter?.split("symbol:".lowercase())[1]
            mainModel.getCoinData(10,offset,null,sub_filter,null,null)

        }else if(filter?.startsWith("slug:") == true){
            val sub_filter = filter?.split("slug:")[1]
            mainModel.getCoinData(10,offset,null,null,sub_filter,null)

        }else if(filter?.startsWith("id:") == true){
            val sub_filter = filter?.split("id:")[1]
            mainModel.getCoinData(10,offset,null,null,null,sub_filter)

        }else{
            mainModel.getCoinData(10,offset,filter,null,null,null)
        }
    }

    fun observeData(isFirstPage:Boolean){
        val observer = Observer<PresCoinResponse>{
            if(it != null){
                when(it){
                    is PresCoinResponseSuccess -> {
                        if(BuildConfig.DEBUG){
                            Log.d(TAG, "fetch success")
                        }
                        if(isFirstPage) {
                            coinListview.visibility = View.VISIBLE
                            emptyView.visibility = View.GONE
                            setData(it.data)
                        }else{
                            Log.d(TAG, "new data: ${it.data?.get(0)?.uuid} data in list: ${adapter.getItem(0)?.uuid}")
                            if(it.data.isNullOrEmpty() || it.data[0].uuid.equals(adapter.getItem(0)?.uuid)) {
                                if(BuildConfig.DEBUG) Log.d(TAG, "add empty list")
                                //addData(emptyList())
                            }else{
                                if(BuildConfig.DEBUG) Log.d(TAG, "load normally")
                                coinListview.visibility = View.VISIBLE
                                emptyView.visibility = View.GONE
                                addData(it.data)
                            }
                        }
                    }
                    is PresCoinResponseFailure -> {
                        if(BuildConfig.DEBUG){
                            if(BuildConfig.DEBUG) Log.d(TAG, "fetch failure")
                        }
                        when(it.error){
                            PresCoinError.HTTP_EXCEPTION -> {
                                toast("HTTP exception: " + it.e?.message)
                            }
                            PresCoinError.EMPTY_NULL -> {
                                if(BuildConfig.DEBUG) Log.d(TAG, "observeData: empty null")
                                coinListview.visibility = View.GONE
                                emptyView.visibility = View.VISIBLE
                                setData(emptyList())
                            }
                            PresCoinError.UNKNOWN -> {
                                toast("something went wrong!")
                            }
                        }
                    }
                }
            }
        }
        mainModel.currentData.observe(this,observer)
    }

    fun setData(coins:List<Coin>?){
        adapter.clear()
        adapter.addResults(coins)
        adapter.notifyDataSetChanged()
    }
    fun addData(coins:List<Coin>?){
        val oldSize = adapter.itemCount+1
        adapter.addResults(coins)
        adapter.notifyItemRangeInserted(oldSize,adapter.itemCount)
    }
    private fun toast(msg: String){
        val inflater = layoutInflater
        val layout: View =
            inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        val text: TextView = layout.findViewById(R.id.custom_toast_text)
        text.text = msg

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.BOTTOM, 0, 40)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}