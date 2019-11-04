package com.example.mynews

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.data.Result
import com.example.mynews.utils.URL
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat


class NewsAdapter(private var context: Context, private var items: List<Result> = listOf()) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        holder.section.text = item.section
        holder.title.text = item.title
        holder.date.text = formatDate(item.published_date)
        item.multimedia?.let {
            if(item.multimedia.isNotEmpty()) Picasso.get().load(item.multimedia.first().url).into(holder.image)
        }
        item.media?.let {
            if(item.media.isNotEmpty()) Picasso.get().load(item.media.first().mediaMetadata.first().url).into(holder.image)
        }

        holder.cardView.setOnClickListener(){
            val url = getUrl(position)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int {
        return items.size
    }

    fun replaceItems(items: List<Result>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addContext(context: Context?){}


    private fun formatDate(date: String): String {
        val dateFormat = listOf("yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ssZ")
        for (formatString in dateFormat) {
            try {
                val parser = SimpleDateFormat(formatString)
                val formatter = SimpleDateFormat("dd/MM/yy")
                val output = formatter.format(parser.parse(date))
                return output
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        return ""
    }

    fun getUrl(position: Int): String {
        return items[position].url
    }

}