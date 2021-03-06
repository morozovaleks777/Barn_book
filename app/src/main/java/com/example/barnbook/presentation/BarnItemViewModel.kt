package com.example.barnbook.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.AddBarnItemUseCase
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.EditBarnItemUseCase
import com.example.barnbook.domain.GetBarnItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class BarnItemViewModel(application: Application):AndroidViewModel(application) {

    private val repository= BarnListRepositoryImpl(application)

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
        viewModelScope.launch {
            val item = getBarnItemUseCase.getBarnItem(barnItemId)
            _barnItem.value=item
        }
    }

    fun addBarnItem(inputName: String?, inputCount: String?,inputPrice: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        val price = parseInputPrice(inputPrice )
        val fieldsValid = validateInput(name, count,price)
        if (fieldsValid) {
            viewModelScope.launch {
            val barnItem = BarnItem(name, count, price,true)

                addBarnItemUseCase.addBarnItem(barnItem)
            }
            finishWork()
        }
    }

    fun editBarnItem(inputName:String?,inputCount:String?,inputPrice: String?){
        val name=parseInputName(inputName)
        val count=parseInputCount(inputCount)
        val price=parseInputPrice(inputPrice )
        val fieldsValid=validateInput(name,count,price)
        if(fieldsValid){
            _barnItem.value?.let {
                viewModelScope.launch {
                val item = it.copy(name = name, count = count,price = price)
                    editBarnItemUseCase.editBarnItem(item)
                    finishWork()
                }
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
    private fun parseInputPrice(inputPrice: String?):Float{
        return  try {
            inputPrice?.trim()?.toFloat() ?:0F
        }
        catch (e:Exception){
            0F
        }
    }

    private fun validateInput(inputName:String,inputCount:Int,inputPrice: Float):Boolean{
        var result=true
        if(inputName.isBlank()) {
           _errorInputName.value=true
             result = false
        }
        if(inputCount<=0){
            _errorInputCount.value=true
             result =false
        }
        if(inputPrice<=0){
            _errorInputCount.value=true
            result =false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value=false
    }
    fun resetErrorInputCount(){
        _errorInputCount.value=false
    }
    private fun finishWork(){
        _closeScreen.value=Unit
    }


}