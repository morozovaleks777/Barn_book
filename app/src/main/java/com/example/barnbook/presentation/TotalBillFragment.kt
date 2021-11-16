package com.example.barnbook.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.barnbook.R
import com.example.barnbook.databinding.FragmentTotalBillBinding


class TotalBillFragment : Fragment() {

    private val viewBinding: FragmentTotalBillBinding by viewBinding()
    private lateinit var viewModel: TotalBillViewModel
private lateinit var textButton:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_total_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalSumText=viewBinding.textView
        textButton=viewBinding.tvButton
        textButton.setOnClickListener{
            Toast.makeText(requireContext(),"go to next fragment",Toast.LENGTH_SHORT).show()
        }
        viewModel = ViewModelProvider(this)[TotalBillViewModel::class.java]
 viewModel.barnList.observe(viewLifecycleOwner,{

   val sum=  viewModel.getAmountOfExpenses(it)
     ("total cost : " +sum.toString()).also { totalSumText.text = it }
 })




    }

}