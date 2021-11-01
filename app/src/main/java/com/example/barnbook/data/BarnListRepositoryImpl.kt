package com.example.barnbook.data

import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository
import java.lang.RuntimeException

object BarnListRepositoryImpl:BarnListRepository {

    private val barnList= mutableListOf<BarnItem>()
private var autoIncrementId=0
    override fun deleteBarnItem(barnItem: BarnItem) {
        barnList.remove(barnItem)
    }

    override fun editBarnItem(barnItem: BarnItem) {
        val oldBarnItem= getBarnItem(barnItem.itemId)
        barnList.remove(oldBarnItem)
        barnList.add(barnItem.itemId,barnItem)
    }

    override fun getAmount(list: List<BarnItem>): Float {
        TODO("Not yet implemented")
    }

    override fun getBarnItem(itemId: Int): BarnItem {
        return barnList.find {it.itemId == itemId }?:throw RuntimeException("element with id $itemId was not found")
    }

    override fun getBarnList(): List<BarnItem> {
       return barnList.toList()
    }

    override fun addBarnItem(barnItem: BarnItem) {
        if(barnItem.itemId==BarnItem.UndefindId){
        barnItem.itemId= autoIncrementId++ }
        barnList.add(barnItem)
    }

}