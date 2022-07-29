package com.example.animenews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animenews.R
import com.example.animenews.data.entidades.database.AnimeItem
import com.example.animenews.databinding.RvNewsItemsBinding
import com.squareup.picasso.Picasso

class NewsItemAdapter(
    private val itemClick: (item: AnimeItem) -> Unit
) :
    RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder>() {

    var dataList: List<AnimeItem> = emptyList()

    class NewsViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private var binding: RvNewsItemsBinding = RvNewsItemsBinding.bind(item)

        fun render(item: AnimeItem, itemClick: (AnimeItem) -> Unit) {
            Picasso.get().load(item.photo).into(binding.imgAnime)
            binding.imgAnime
            binding.txtDescAnime.text = item.description.toString()
            binding.txtTitleAnime.text = item.name
            itemView.setOnClickListener() {
                itemClick(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater.inflate(R.layout.rv_news_items, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.render(dataList[position], itemClick)
    }

    override fun getItemCount(): Int = dataList.size

}