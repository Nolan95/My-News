package com.example.mynews


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.roomdata.SharedArticle
import com.example.mynews.repository.roomdata.TopArticles
import com.example.mynews.repository.roomdata.TopArticlesAndMultimediaX
import com.example.mynews.utils.*
import com.example.mynews.viewmodel.NewsViewModel
import io.reactivex.disposables.Disposable


/**
 * A simple [Fragment] subclass.
 */
class TopStoriesFragment : Fragment(){

    private var linearLayoutManager: LinearLayoutManager? = null

    private var disposable: Disposable? = null

    private val apiCaller: ApiCaller =
        ApiCaller()

    private lateinit var recyclerView: RecyclerView

    var news: MutableList<TopArticles> = mutableListOf()

    private lateinit var newsAdapter: NewsAdapter

    private lateinit var sharedArticleAdapter: SharedArticleAdapter

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

        newsAdapter = NewsAdapter{ item -> onItemClick(item)}
        setTabContent()
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
                BUSINESS -> topStories("business")

            }
        }
    }



    private fun topStories(section: String){

        newsViewModel.allStoriesBySection(section).observe(this, Observer { newsAdapter.submitList(it) })

        //newsViewModel.newsRepository.saveFromApiToDb(section)
    }


    private fun mostPopular(period: Int){
        newsViewModel.mostPopular().observe(this, Observer {})

        newsViewModel.newsRepository.saveFromApiToDbMostPopular(period)
    }

     fun onItemClick(item: TopArticles) {

        context?.startActivity(Intent(context, DetailActivity::class.java)
            .putExtra(URL, item.url)
            .putExtra(TITLE, item.title))

    }

    fun replaceItems(items: List<TopArticles>) {
        if(items.isNotEmpty()){
            news.clear()
            news.addAll(items)
        }
        newsAdapter.notifyDataSetChanged()
    }

}
