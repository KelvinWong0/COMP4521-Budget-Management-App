package com.example.compose.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.compose.R
import com.example.compose.data.models.Category
import com.example.compose.data.models.Record
import com.example.compose.databinding.CustomGridCatBinding
import com.example.compose.databinding.CustomGridIvBinding
import com.example.compose.databinding.CustomRowBinding
import com.google.android.material.card.MaterialCardView


class ListAdapterII(private val recordList: List<Record>): BaseAdapter() {


    override fun getCount(): Int {
        return recordList.size
    }

    override fun getItem(position: Int): Any? {
        return recordList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: ViewHolder
        val itemBinding: CustomRowBinding =
            CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (convertView == null) {

            holder = ViewHolder(itemBinding)
            holder.view = itemBinding.root
            holder.view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }


        holder.binding.record = recordList[position]
        return holder.view
    }


    private class ViewHolder internal constructor(binding: CustomRowBinding) {
        var view: View
        val binding: CustomRowBinding

        init {
            view = binding.root
            this.binding = binding
        }
    }





}