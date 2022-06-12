package com.example.tujutuju.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tujutuju.data.response.PlacesItem
import com.example.tujutuju.databinding.ItemRvBinding

class PlaceAdapter(private val list:ArrayList<PlacesItem>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    private var onItemClickCallback : OnItemClickCallback?=null
    inner class ViewHolder(private var binding:ItemRvBinding):RecyclerView.ViewHolder(binding.root){
        fun itemBind(listPlace:PlacesItem){
            binding.root.setOnClickListener { onItemClickCallback?.onItemClicked(listPlace) }
            with(binding){
                tvNamePlace.text=listPlace.name
                tvCity.text=listPlace.address
                Glide.with(itemView.context)
                    .load(listPlace.images)
                    .into(ivPlace)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        val itemRvBinding = ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemRvBinding)
    }

    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        val place=list[position]
        holder.itemBind(place)
    }
    override fun getItemCount()= list.size
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: PlacesItem)
    }
}