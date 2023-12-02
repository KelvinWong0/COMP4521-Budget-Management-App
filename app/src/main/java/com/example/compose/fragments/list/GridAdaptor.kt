package com.example.compose.fragments.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import com.example.compose.databinding.CustomGridCatBinding
import com.example.compose.databinding.CustomRowBinding


class GridAdapter: BaseAdapter() {
    private var categoryList = emptyList<Category>()


    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: ViewHolder

        if (convertView == null) {
            val itemBinding: CustomGridCatBinding =
                CustomGridCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            holder = ViewHolder(itemBinding)
            holder.view = itemBinding.root
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.binding.category = categoryList[position]
        return holder.view
    }

    private class ViewHolder internal constructor(binding: CustomGridCatBinding) {
        var view: View
        val binding: CustomGridCatBinding

        init {
            view = binding.root
            this.binding = binding
        }
    }
    fun setData(categories: List<Category>){
        this.categoryList = categories
        notifyDataSetChanged()
    }
}