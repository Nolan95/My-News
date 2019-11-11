package com.example.mynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.repository.data.Doc
import com.squareup.picasso.Picasso
import com.example.mynews.utils.*


class SearchResultAdapter(private var items: List<Doc> = listOf(),
                          val onItemClickListener: OnItemClicked) : RecyclerView.Adapter<SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return SearchResultViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = items[position]


        holder.section.text = item.section_name
        holder.title.text = item.snippet
        holder.date.text = formatDate(item.pub_date)

        item.multimedia?.let {
            if(item.multimedia.isNotEmpty()) Picasso.get().load(BASE_URL+item.multimedia.first().url).into(holder.image)
        }

        holder.cardView.setOnClickListener{
            onItemClickListener.onItemClick(item)
        }

    }



    override fun getItemCount(): Int {
        return items.size
    }


    interface OnItemClicked{
        fun onItemClick(item: Doc)
    }

}