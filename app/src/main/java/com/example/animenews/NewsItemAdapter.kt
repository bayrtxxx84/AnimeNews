package com.example.animenews

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.animenews.databinding.NewsItemsRvBinding

class NewsItemAdapter(var dataList: List<ItemDataClass>,
                      var itemClick: (item: ItemDataClass) -> Unit) :
    RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder>() {

    class NewsViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private var binding: NewsItemsRvBinding = NewsItemsRvBinding.bind(item)

        fun render(item: ItemDataClass, itemClick: (item: ItemDataClass) -> Unit) {
            binding.imgAnime.setImageResource(item.photo)
            binding.txtDescAnime.text = item.id.toString()
            binding.txtTitleAnime.text = item.name

            itemView.setOnClickListener() {
                itemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater.inflate(R.layout.news_items_rv, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.render(dataList[position], itemClick)
    }

    override fun getItemCount(): Int = dataList.size

}