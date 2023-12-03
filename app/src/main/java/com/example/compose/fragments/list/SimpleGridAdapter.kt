package com.example.compose.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.compose.data.models.Category
import com.example.compose.databinding.CustomGridCatBinding
import com.google.android.material.card.MaterialCardView


class SimpleGridAdapter(): BaseAdapter() {
    private var categoryList = emptyList<Category>()
    private lateinit var selectedView : MaterialCardView

    private var selected: Int = -1

    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(position: Int): Any? {
        return categoryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: ViewHolder
        val itemBinding: CustomGridCatBinding =
            CustomGridCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (convertView == null) {

            holder = ViewHolder(itemBinding)
            holder.view = itemBinding.root
            holder.view.tag = holder
            itemBinding.gridCat.setOnClickListener{

            }
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


    fun getSelectedCategory(): Category?{
        if(selected!=-1){
            return categoryList[selected]
        }
        return null
    }

    fun clearSelected(){
        if(selected != -1){
            selectedView.isChecked = false
            selected = -1
        }
    }

}