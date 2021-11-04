package com.example.barnbook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.AddBarnItemUseCase
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.EditBarnItemUseCase
import com.example.barnbook.domain.GetBarnItemUseCase
import java.lang.Exception

class BarnItemViewModel:ViewModel() {

    private val repository= BarnListRepositoryImpl
    private val editBarnItemUseCase= EditBarnItemUseCase(repository)
    private val addBarnItemUseCase= AddBarnItemUseCase(repository)
    private val getBarnItemUseCase=GetBarnItemUseCase(repository)

    private val _errorInputName=MutableLiveData<Boolean>()
            val errorInputName: LiveData<Boolean>
                get() = _errorInputName

    private val _errorInputCount=MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _barnItem=MutableLiveData<BarnItem>()
    val barnItem: LiveData<BarnItem>
        get() = _barnItem

    private val _closeScreen=MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen



    fun getBarnItem(barnItemId:Int){
        val item=getBarnItemUseCase.getBarnItem(barnItemId)
        _barnItem.value=item
    }

    fun addBarnItem(inputName:String?,inputCount:String?){
        val name=parseInputName(inputName)
        val count=parseInputCount(inputCount)
        val fieldsValid=validateInput(name,count)
        if(fieldsValid){
            val barnItem=BarnItem(name,count,0F,true)
            addBarnItemUseCase.addBarnItem(barnItem)
            finishWork()
        }
    }

    fun editBarnItem(inputName:String?,inputCount:String?){
        val name=parseInputName(inputName)
        val count=parseInputCount(inputCount)
        val fieldsValid=validateInput(name,count)
        if(fieldsValid){
            _barnItem.value?.let {
                val item = it.copy(name = name, count = count)

                editBarnItemUseCase.editBarnItem(item)
                finishWork()
            }
        }
    }

   private fun parseInputName(inputName: String?):String{
         return inputName?.trim() ?: ""
}

    private fun parseInputCount(inputCount: String?):Int{
      return  try {
             inputCount?.trim()?.toInt() ?:0
        }
        catch (e:Exception){
            0
        }
    }

    private fun validateInput(inputName:String,inputCount:Int):Boolean{
        var result=true
        if(inputName.isBlank()) {
           _errorInputName.value=true
             result = false
        }
        if(inputCount<=0){
            _errorInputCount.value=true
             result =false
        }
        return result
    }
    public fun resetErrorInputName(){
        _errorInputName.value=false
    }
    public fun resetErrorInputCount(){
        _errorInputCount.value=false
    }
    private fun finishWork(){
        _closeScreen.value=Unit
    }
}