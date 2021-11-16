package com.example.barnbook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.GetAmountOfExpensesUseCase
import com.example.barnbook.domain.GetBarnListUseCase

class TotalBillViewModel:ViewModel() {

    private val repository= BarnListRepositoryImpl
    private val getAmountOfExpensesUseCase=GetAmountOfExpensesUseCase(repository)
    private val getBarnListUseCase=GetBarnListUseCase(repository)
    private val _sum= MutableLiveData<Float>()
    val sum: LiveData<Float>
        get() = _sum

    val barnList=getBarnListUseCase.getBarnList()
    fun getAmountOfExpenses(list:List<BarnItem>):Float{
     return getAmountOfExpensesUseCase.getAmount(list)
    }
}