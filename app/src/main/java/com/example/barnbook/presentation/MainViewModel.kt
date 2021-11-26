package com.example.barnbook.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.DeleteBarnItemUseCase
import com.example.barnbook.domain.EditBarnItemUseCase
import com.example.barnbook.domain.GetBarnListUseCase

class MainViewModel(application: Application):AndroidViewModel(application) {

    private val repository=BarnListRepositoryImpl(application)
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