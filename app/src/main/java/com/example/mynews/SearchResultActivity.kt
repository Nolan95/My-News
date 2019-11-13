package com.example.mynews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynews.repository.NewsRepository
import com.example.mynews.repository.api.ApiCaller
import com.example.mynews.repository.data.Doc
import com.example.mynews.repository.db.AppDatabase
import com.example.mynews.repository.roomdata.DocEntity
import com.example.mynews.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*

class SearchResultActivity : AppCompatActivity(), SearchResultAdapter.OnItemClicked {

    private var disposable: Disposable? = null

    private val apiCaller: ApiCaller =
        ApiCaller()

    private lateinit var recyclerView: RecyclerView

    var news: MutableList<Doc> = mutableListOf()

    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var swipe: SwipeRefreshLayout

    lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        swipe = findViewById(R.id.swipe)
        recyclerView = findViewById(R.id.recycler)
        title = getString(R.string.search_result)
        toolBarConfig()
        val db = AppDatabase.getDatabase(this)
        db?.let {
            newsRepository = NewsRepository(db)
        }
        setContent()
        searchResultAdapter = SearchResultAdapter(news, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter
        }
        onSwipeRefreshLayout()
        setContent()
    }


    override fun onItemClick(item: Doc) {
        startActivity(Intent(this, DetailActivity::class.java)
            .putExtra(URL, item.web_url)
            .putExtra(TITLE, item.snippet))
    }


    private fun onSwipeRefreshLayout() {
        swipe.setOnRefreshListener{
            setContent()
            swipe.isRefreshing = false
        }
    }

    private fun setContent() {
        val q = intent.getStringExtra(QUERY)
        val fq = intent.getStringArrayListExtra(FQ)
        val beginDate = intent.getStringExtra(BEGIN_DATE)
        val endDate = intent.getStringExtra(END_DATE)
        Log.i("Date", "${beginDate}")
        disposable = newsRepository.searchResultFromApi(q, fq)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                replaceItems(it.response.docs)
            },
                { Log.i("Stories", "${it.message}")}
            )
    }

    private fun replaceItems(docs: List<Doc>) {
        if(docs.isNotEmpty()){
            news.clear()
            news.addAll(docs)
        }
        searchResultAdapter.notifyDataSetChanged()
    }

    private fun toolBarConfig() {
        //Toolbar configuration
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}
