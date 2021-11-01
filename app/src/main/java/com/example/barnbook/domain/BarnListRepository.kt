package com.example.barnbook.domain

import androidx.lifecycle.LiveData

interface BarnListRepository {
    fun deleteBarnItem(barnItem: BarnItem)
    fun editBarnItem(barnItem: BarnItem)
    fun getAmount(list: List<BarnItem>):Float
    fun getBarnItem(ItemId: Int):BarnItem
    fun getBarnList():LiveData<List<BarnItem>>
    fun addBarnItem(barnItem: BarnItem)
}