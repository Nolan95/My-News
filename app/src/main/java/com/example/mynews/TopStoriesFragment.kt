package com.example.mynews


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynews.api.ApiCaller
import com.example.mynews.api.NewsApiService
import com.example.mynews.data.Result
import com.example.mynews.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tab.*


/**
 * A simple [Fragment] subclass.
 */
class TopStoriesFragment : Fragment(), NewsAdapter.OnItemClicked {

    private var linearLayoutManager: LinearLayoutManager? = null

    private var disposable: Disposable? = null

    private val apiCaller: ApiCaller = ApiCaller()

    private lateinit var recyclerView: RecyclerView

    var news: MutableList<Result> = mutableListOf()

    private lateinit var newsAdapter: NewsAdapter

    private lateinit var swipe: SwipeRefreshLayout

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
                BUSINESS -> topBusiness("business")

            }
        }
    }



    private fun topBusiness(section: String) {
        disposable = apiCaller.fetchTopStories(section)
            .subscribe(
                { replaceItems(it.results) },
                { Toast.makeText(context, "Error${it.message}", Toast.LENGTH_LONG).show() }
            )
    }


    private fun topStories(section: String){
        disposable = apiCaller.fetchTopStories(section)
            .subscribe(
                { replaceItems(it.results) },
                { Toast.makeText(context, "Error${it.message}", Toast.LENGTH_LONG).show() }
            )
    }

    private fun mostPopular(period: Int){
        disposable = apiCaller.fetchMostPopular(period)
            .subscribe(
                { replaceItems(it.results) },
                { Toast.makeText(context, "Error${it.message}", Toast.LENGTH_LONG).show() }
            )
    }


    override fun onItemClick(item: Result) {
        context?.startActivity(Intent(context, DetailActivity::class.java)
            .putExtra(URL, item.url)
            .putExtra(TITLE, item.title))
    }

    fun replaceItems(items: List<Result>) {
        if(items.isNotEmpty()){
            news.clear()
            news.addAll(items)
        }
        newsAdapter.notifyDataSetChanged()
    }

}
