package com.example.compose.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.compose.R
import com.example.compose.data.models.Category
import com.example.compose.databinding.CustomGridIvBinding
import com.google.android.material.card.MaterialCardView


class ivGridAdapter(): BaseAdapter() {
    private var imageList = intArrayOf()
    private lateinit var selectedView : MaterialCardView

    private var selected: Int = -1

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Any? {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        val itemBinding: CustomGridIvBinding =
            CustomGridIvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (view == null) {
            view = itemBinding.root
        }

//            itemBinding.gridIcon.setOnClickListener{
//                itemBinding.gridIcon.isChecked = !(itemBinding.gridIcon.isChecked)
//                if(selected == position){
//                    selected = -1
//                }else{
//                    if(selected != -1){
//                        selectedView.isChecked = false
//                    }
//                    selected = position
//                    selectedView = itemBinding.gridIcon
//                }
        var ivIcon = view.findViewById<ImageView>(R.id.ivIcon)
        var cvBtn = view.findViewById<MaterialCardView>(R.id.gridIcon)

        ivIcon.setImageResource(imageList[position])

        cvBtn.setOnClickListener{
            cvBtn.isChecked = !(cvBtn.isChecked)
            if(selected == position){
                selected = -1

            }else{
                if(selected != -1){
                    selectedView.isChecked = false
                }
                selected = position
                selectedView = cvBtn
            }
        }
        return view
    }

    private class ViewHolder internal constructor(binding: CustomGridIvBinding) {
        var view: View
        val binding: CustomGridIvBinding

        init {
            view = binding.root
            this.binding = binding
        }
    }


    fun setData(images: IntArray){
        this.imageList = images
        notifyDataSetChanged()
    }


    fun getSelectedImageId(): Int?{
        if(selected!=-1){
            return imageList[selected]
        }
        return null
    }


}