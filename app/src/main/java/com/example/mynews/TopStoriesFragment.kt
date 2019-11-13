package com.example.mynews


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.Result
import com.example.mynews.repository.db.AppDatabase
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.TopArticles
import com.example.mynews.utils.*
import com.example.mynews.viewmodel.NewsViewModel
import com.example.mynews.workmanager.DbPopulateWorker
import com.google.android.gms.common.api.Api
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class TopStoriesFragment : Fragment(), NewsAdapter.OnItemClicked {

    private var linearLayoutManager: LinearLayoutManager? = null

    private var disposable: Disposable? = null

    private val apiCaller: ApiCaller =
        ApiCaller()

    private lateinit var recyclerView: RecyclerView

    var news: MutableList<Any> = mutableListOf()

    private lateinit var newsAdapter: NewsAdapter

    private lateinit var swipe: SwipeRefreshLayout

    private lateinit var newsViewModel: NewsViewModel

//    val db = AppDatabase.getDatabase(context!!)!!

    lateinit var newsRepository: NewsRepository

    companion object {
        fun newInstance(tabTitle: String): TopStoriesFragment {

            val f = TopStoriesFragment()

            val bdl = Bundle(1)

            bdl.putString(TABTITLE, tabTitle)

            f.setArguments(bdl)

            return f

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View =  inflater.inflate(R.layout.fragment_tab, container, false)
        recyclerView = view.findViewById(R.id.recycler)
        swipe = view.findViewById(R.id.swipe)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsRepository = NewsRepository(newsViewModel.db)


        setTabContent()
        newsAdapter = NewsAdapter(news, this)
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        onSwipeRefreshLayout()
        return view
    }

    private fun onSwipeRefreshLayout() {
        swipe.setOnRefreshListener{
            setTabContent()
            swipe.isRefreshing = false
        }
    }


    private fun setTabContent(){
        arguments?.let {
            val tabTitle = arguments!!.getString(TABTITLE)
            when(tabTitle){
                TOPSTORIES -> topStories("home")
                MOSTPOPULAR -> mostPopular(7)
                BUSINESS -> topStories("business")

            }
        }
    }



    private fun topStories(section: String){

        newsViewModel.allStoriesBySection(section).observe(this, Observer { stories ->
            // Update the cached copy of the words in the adapter.
            if(stories != null){
                replaceItems(stories)
            }else{
                Toast.makeText(context, "Stories vides", Toast.LENGTH_LONG).show()
            }
        })

        newsViewModel.newsRepository.saveFromApiToDb(section)
    }


    private fun mostPopular(period: Int){
        newsViewModel.mostPopular().observe(this, Observer { stories ->
            // Update the cached copy of the words in the adapter.
            if(stories != null){
                replaceItems(stories)
            }else{
                Toast.makeText(context, "Stories vides", Toast.LENGTH_LONG).show()
            }
        })

        newsViewModel.newsRepository.saveFromApiToDbMostPopular(period)
    }


    override fun onItemClick(item: Any) {
        when(item){
            is TopArticles -> {
                context?.startActivity(Intent(context, DetailActivity::class.java)
                    .putExtra(URL, item.url)
                    .putExtra(TITLE, item.title))
            }

            is SharedArticle -> {
                context?.startActivity(Intent(context, DetailActivity::class.java)
                    .putExtra(URL, item.url)
                    .putExtra(TITLE, item.title))
            }
        }

    }

    fun replaceItems(items: List<Any>) {
        if(items.isNotEmpty()){
            news.clear()
            news.addAll(items)
        }
        newsAdapter.notifyDataSetChanged()
    }

}
