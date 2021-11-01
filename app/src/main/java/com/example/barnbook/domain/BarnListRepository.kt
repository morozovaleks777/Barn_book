package com.example.barnbook.domain

interface BarnListRepository {
    fun deleteBarnItem(barnItem: BarnItem)
    fun editBarnItem(barnItem: BarnItem)
    fun getAmount(list: List<BarnItem>):Float
    fun getBarnItem(ItemId: Int):BarnItem
    fun getBarnList():List<BarnItem>
    fun addBarnItem(barnItem: BarnItem)
}