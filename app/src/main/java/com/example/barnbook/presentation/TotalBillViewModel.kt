package com.example.barnbook.presentation

import android.app.Application
import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.*
import com.example.barnbook.data.BarnListRepositoryImpl
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.GetAmountOfExpensesUseCase
import com.example.barnbook.domain.GetBarnListUseCase
import com.example.barnbook.domain.GetCurrentTimeCase
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TotalBillViewModel(application: Application):AndroidViewModel(application) {

    private val repository = BarnListRepositoryImpl(application)
    private val getAmountOfExpensesUseCase = GetAmountOfExpensesUseCase(repository)
    private val getBarnListUseCase = GetBarnListUseCase(repository)
    private val getCurrentTimeCase = GetCurrentTimeCase(repository)
    private val _sum = MutableLiveData<Float>()
    val sum: LiveData<Float>
        get() = _sum

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time
    val barnList = getBarnListUseCase.getBarnList()

    fun getAmountOfExpenses(list: List<BarnItem>): Float {

        return getAmountOfExpensesUseCase.getAmount(list)

    }
 fun getTime():String {
     var time=""
      viewModelScope.launch {
    time=getCurrentTimeCase.getCurrentTime()

}

return time


}

}





