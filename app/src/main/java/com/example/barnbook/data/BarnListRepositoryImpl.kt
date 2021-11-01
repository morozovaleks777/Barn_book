package com.example.barnbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository
import java.lang.RuntimeException

object BarnListRepositoryImpl:BarnListRepository {

    private val barnListLD=MutableLiveData<List<BarnItem>>()
    private val barnList= mutableListOf<BarnItem>()
    private var autoIncrementId=0
init {
    for (i in 0 until 10){
        val item=BarnItem("$i",i,i.toFloat(),true,i)
        addBarnItem(item)
    }
}

    override fun deleteBarnItem(barnItem: BarnItem) {
        barnList.remove(barnItem)
        updateList()
    }

    override fun editBarnItem(barnItem: BarnItem) {
        val oldBarnItem= getBarnItem(barnItem.itemId)
        barnList.remove(oldBarnItem)
        barnList.add(barnItem.itemId,barnItem)
    }

    override fun getAmount(list: List<BarnItem>): Float {
        TODO("Not yet implemented")
    }

    override fun getBarnItem(ItemId: Int): BarnItem {
        return barnList.find {it.itemId == ItemId }?:throw RuntimeException("element with id $ItemId was not found")
    }

    override fun getBarnList(): LiveData<List<BarnItem>> {
       return barnListLD
    }

    override fun addBarnItem(barnItem: BarnItem) {
        if(barnItem.itemId==BarnItem.UndefindId){
        barnItem.itemId= autoIncrementId++ }
        barnList.add(barnItem)
        updateList()
    }

    private fun updateList(){
        barnListLD.value= barnList.toList()
    }

}