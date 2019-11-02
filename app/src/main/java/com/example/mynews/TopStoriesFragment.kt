package com.example.mynews


import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.data.Quote
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * A simple [Fragment] subclass.
 */
class TopStoriesFragment : Fragment() {
    private var linearLayoutManager: LinearLayoutManager? = null
    val items = listOf(
        Quote("Titre1","Premature optimization is the root of all evil", null),
        Quote("Titre2", "Any sufficiently advanced technology is indistinguishable from magic.", "Arthur C. Clarke"),
        Quote("Titre3","Content 01", "Source"),
        Quote("Titre4","Content 02", "Source"),
        Quote("Titre5","Content 03", "Source"),
        Quote("Titre6","Content 04", "Source"),
        Quote("Titre7","Content 05", "Source")
    )

    companion object {
        fun newInstance(message: String): TopStoriesFragment {

            val f = TopStoriesFragment()

            val bdl = Bundle(1)

            bdl.putString(EXTRA_MESSAGE, message)

            f.setArguments(bdl)

            return f

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_tab, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NewsAdapter(items)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val items = listOf(
//            Quote("Titre1","Premature optimization is the root of all evil", null),
//            Quote("Titre2", "Any sufficiently advanced technology is indistinguishable from magic.", "Arthur C. Clarke"),
//            Quote("Titre3","Content 01", "Source"),
//            Quote("Titre4","Content 02", "Source"),
//            Quote("Titre5","Content 03", "Source"),
//            Quote("Titre6","Content 04", "Source"),
//            Quote("Titre7","Content 05", "Source")
//        )
//        linearLayoutManager = LinearLayoutManager(getActivity())
//        recycler?.layoutManager = linearLayoutManager
//        recycler?.adapter = NewsAdapter(items) // Your adapter
//        recycler?.setHasFixedSize(true);
//
//
//    }


}
