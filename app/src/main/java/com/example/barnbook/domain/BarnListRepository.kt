package com.example.barnbook.domain

import androidx.lifecycle.LiveData
import java.time.LocalDateTime
import java.util.*

interface BarnListRepository {
    suspend fun deleteBarnItem(barnItem: BarnItem)
    suspend fun editBarnItem(barnItem: BarnItem)
    fun getAmount(list: List<BarnItem>):Float
    suspend fun getBarnItem(ItemId: Int):BarnItem
    fun getBarnList():LiveData<List<BarnItem>>
    suspend fun addBarnItem(barnItem: BarnItem)
  suspend fun getCurrentTime():String
}