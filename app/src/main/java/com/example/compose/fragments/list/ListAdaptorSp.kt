package com.example.compose.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.compose.DataViewModel
import com.example.compose.R
import com.example.compose.data.models.Record
import com.example.compose.databinding.CustomRowBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListAdapterSp(private val dataViewModel: DataViewModel,  private val deleteMode: Boolean): RecyclerView.Adapter<com.example.compose.fragments.list.ListAdapterSp.MyViewHolder>() {

    private var recordList = emptyList<Record>()
    inner class MyViewHolder(val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.compose.fragments.list.ListAdapterSp.MyViewHolder {
        return MyViewHolder(CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recordList[position]
        val view = holder.itemView
        if(deleteMode) {
            view.setOnClickListener {
                MaterialAlertDialogBuilder(view.context)
                    .setTitle("Warning")
                    .setMessage("Delete `${recordList[position].recordName}`?")
                    .setNeutralButton("Cancel") { dialog, which ->
                        // Respond to neutral button press

                    }
                    //                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    //                    // Respond to negative button press
                    //                }
                    .setPositiveButton("Proceed") { dialog, which ->
                        // Respond to positive button press

                        dataViewModel.deleteRecord(recordList[position])
                        Toast.makeText(
                            view.context,
                            "Category ${recordList[position].recordName} is removed!",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    .show()
            }
        }
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