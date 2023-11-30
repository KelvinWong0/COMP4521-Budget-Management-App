package com.example.compose.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.R
import com.example.compose.data.models.Record
import com.example.compose.databinding.CustomRowBinding

class ListAdapter: RecyclerView.Adapter<com.example.compose.fragments.list.ListAdapter.MyViewHolder>() {

    private var recordList = emptyList<Record>()
    inner class MyViewHolder(val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recordList[position]
        holder.binding.record = currentItem
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    fun setData(records: List<Record>){
        this.recordList = records
        notifyDataSetChanged()
    }
}