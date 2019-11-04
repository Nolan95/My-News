package com.example.mynews


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.api.NewsApiService
import com.example.mynews.data.Result
import com.example.mynews.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers



/**
 * A simple [Fragment] subclass.
 */
class TopStoriesFragment : Fragment() {
    private var linearLayoutManager: LinearLayoutManager? = null

    private var disposable: Disposable? = null

    private val newsApiService by lazy {
        NewsApiService.create()
    }

    private lateinit var recyclerView: RecyclerView

    var news: MutableList<Result> = mutableListOf()

    private lateinit var newsAdapter: NewsAdapter

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
        setTabContent()
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        newsAdapter = NewsAdapter(container!!.context, news)
        recyclerView.adapter = newsAdapter
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        return view
    }

    private fun fetchTopStories(section: String){
        disposable = newsApiService.getTopStories(section,API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { newsAdapter.replaceItems(it.results) },
                { Log.i("Error","${it.message}") }
            )
    }

    private fun fetchMostPopular(period: Int) {
        disposable = newsApiService.getMostPopularNews(period, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { newsAdapter.replaceItems(it.results) },
                { Log.i("Error","${it.message}") }
            )
    }

    private fun setTabContent(){
        arguments?.let {
            val tabTitle = arguments!!.getString(TABTITLE)
            when(tabTitle){
                TOPSTORIES -> fetchTopStories("home")
                MOSTPOPULAR -> fetchMostPopular(7)
                BUSINESS -> fetchTopStories("business")
            }
        }
    }

}
