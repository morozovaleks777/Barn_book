package com.example.barnbook.presentation

import androidx.lifecycle.ViewModel
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.DeleteBarnItemUseCase
import com.example.barnbook.domain.EditBarnItemUseCase
import com.example.barnbook.domain.GetBarnListUseCase

class MainViewModel:ViewModel() {

    private val repository=BarnListRepositoryImpl
    private val getBarnListUseCase=GetBarnListUseCase(repository)
    private val deleteBarnItemUseCase=DeleteBarnItemUseCase(repository)
    private val editBarnItemUseCase=EditBarnItemUseCase(repository)


    val barnList=getBarnListUseCase.getBarnList()

    fun deleteBarnItem(barnItem: BarnItem){
        deleteBarnItemUseCase.deleteBarnItem(barnItem)
    }
    fun changeEnableState(barnItem: BarnItem){
        val newItem=barnItem.copy(enabled = !barnItem.enabled)
        editBarnItemUseCase.editBarnItem(newItem)
    }
}