package com.example.mynews.extensions

import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.NewsAdapter
import com.example.mynews.data.Result
import kotlinx.android.synthetic.main.list_item.view.*

fun RecyclerView.ViewHolder.addOnItemClickListener(onItemClickListener: NewsAdapter.OnItemClicked, item: Result){

    this.itemView.cardView.setOnClickListener(){
        onItemClickListener.onItemClick(item)
    }

}