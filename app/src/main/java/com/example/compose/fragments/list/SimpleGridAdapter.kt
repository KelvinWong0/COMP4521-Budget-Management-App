package com.example.compose.fragments.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.compose.DataViewModel
import com.example.compose.data.models.Category
import com.example.compose.databinding.CustomGridCatBinding
import com.example.compose.CatRecordActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SimpleGridAdapter(private val context: Context ,private val dataViewModel: DataViewModel,  private val deleteMode: Boolean): BaseAdapter() {
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
            holder.binding.category = itemBinding.category

            if(deleteMode){
                val view  =  holder.view   as MaterialCardView

                view.setOnClickListener {

                    MaterialAlertDialogBuilder(view.context)
                        .setTitle("Warning")
                        .setMessage("Delete `${holder.binding.category!!.name}` and it's records?")
                        .setNeutralButton("Cancel") { dialog, which ->
                            // Respond to neutral button press

                        }
//                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
//                    // Respond to negative button press
//                }
                        .setPositiveButton("Proceed") { dialog, which ->
                            // Respond to positive button press
                            if(holder.binding.category != null){
                                dataViewModel.deleteCategory(holder.binding.category!!)
                                Toast.makeText(holder.view.context, "Category ${holder.binding.category!!.name} is removed!", Toast.LENGTH_LONG).show()
                            }
                        }
                        .show()
                }
            }else{
                val view  =  holder.view   as MaterialCardView

                view.setOnClickListener {
                    val intent = Intent(context, CatRecordActivity::class.java)
                    intent.putExtra("categoryId", holder.binding.category!!.categoryId)
                    context.startActivity(intent)
                }

//                holder.view.setOnClickListener {
//                    Toast.makeText(holder.view.context, "${holder.binding.category}", Toast.LENGTH_LONG).show()
////                    Toast.makeText(holder.view.context, "${itemBinding.category}", Toast.LENGTH_LONG).show()
//                    val view = it as MaterialCardView
//                    view.isChecked = !(view.isChecked)
//                    if(selected == position){
//                        selected = -1
//                    }else{
//                        if(selected != -1){
//                            selectedView.isChecked = false
//                        }
//                        selected = position
//                        selectedView = view
//                    }
//
//                }
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