package com.example.compose.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yml.charts.common.extensions.isNotNull
import com.example.compose.DataViewModel
import com.example.compose.EditRecordActivity
import com.example.compose.R
import com.example.compose.data.daos.RecordDAO
import com.example.compose.fragments.list.ListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentHome: Fragment(R.layout.fragment_home) {
    lateinit var dataViewModel: DataViewModel
    lateinit var tvIncome :  TextView
    lateinit var tvExpense :  TextView
    lateinit var tvBalance :  TextView

    lateinit var recordDao : RecordDAO
    private var balance:Int = 0
    private var expense:Int = 0
    private var income: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val BudgetsharedPref  = activity?.getSharedPreferences("BudgetPref", MODE_PRIVATE)
        val editor = BudgetsharedPref?.edit()
        var budget = 0f
        budget = BudgetsharedPref?.getFloat("amount", 0F).takeIf { BudgetsharedPref.isNotNull() }!!
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        tvIncome = view.findViewById(R.id.tv_income)
        tvExpense = view.findViewById(R.id.tv_expense)
        tvBalance = view.findViewById(R.id.tv_balance)
        val etBudget = view.findViewById<EditText>(R.id.etBuget)
        etBudget.setText(budget.toString())

        val fabAdd = view.findViewById<FloatingActionButton>(R.id.add_fab)
        fabAdd.setOnClickListener{
            Intent(requireContext(), EditRecordActivity::class.java).also{
                startActivity(it)
            }
        }

        etBudget.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    budget = etBudget.text.toString().toFloat()
                    editor?.apply {
                        putFloat("amount", budget!!)
                        apply()
                    }
                    if(expense >= budget){
                        etBudget.setTextColor(Color.parseColor("#ff0000"))
                    }else{
                        etBudget.setTextColor(Color.parseColor("#0F9D58"))
                    }
                }
            }
        })

//        income = dataViewModel.getIncome().toString()
//        expense = dataViewModel.getExpense().toString()



        dataViewModel.getTotalExpense.observe(viewLifecycleOwner, Observer{expense ->
            if(expense != null){
                this.expense = expense
                if(this.expense >= budget){
                    etBudget.setTextColor(Color.parseColor("#ff0000"))
                }else{
                    etBudget.setTextColor(Color.parseColor("#0F9D58"))
                }
            }else{
                this.expense = 0
            }
            tvExpense.text = "Expense:\n$"+ this.expense.toString()
            balance = this.income - this.expense
            tvBalance.text = "Balance:\n$" + balance
        })
        dataViewModel.getTotalIncome.observe(viewLifecycleOwner, Observer{income ->
            if(income != null){
                this.income = income
            }else{
                this.income = 0
            }
            tvIncome.text = "Income:\n$"+ this.income.toString()
            balance = this.income - this.expense
            tvBalance.text = "Balance:\n$" + balance
        })




        val rvRecord = view.findViewById<RecyclerView>(R.id.recordList)
        val adapter = ListAdapter()
        rvRecord.adapter = adapter
        rvRecord.layoutManager = LinearLayoutManager(requireContext())

        dataViewModel.readAllRecord.observe(viewLifecycleOwner, Observer{records ->
            adapter.setData(records.sortedBy { it.date }.reversed())
        })

    }

}