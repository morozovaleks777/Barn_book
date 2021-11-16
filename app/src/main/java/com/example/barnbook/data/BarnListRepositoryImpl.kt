package com.example.barnbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object BarnListRepositoryImpl:BarnListRepository {
    private val barnList = sortedSetOf<BarnItem>({ o1, o2 -> o1.itemId.compareTo(o2.itemId) })
    private val barnListLD=MutableLiveData<List<BarnItem>>()
    private var autoIncrementId=0
init {
    for (i in 0 until 20){
        val item=BarnItem(" name ",0, 0.0F ,false)
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
       addBarnItem(barnItem)

    }

    override fun getAmount(list: List<BarnItem>): Float {
        var sum=0F

        for (i in 0 until list.size-1){
           sum+= list[i].count*list[i].price

        }
        return sum
    }

    override fun getBarnItem(ItemId: Int): BarnItem {
        return barnList.find {it.itemId == ItemId }?:throw RuntimeException("element with id $ItemId was not found")
    }

    override fun getBarnList(): LiveData<List<BarnItem>> {
       return barnListLD
    }

    override fun addBarnItem(barnItem: BarnItem) {
        if(barnItem.itemId==BarnItem.UNDEFINED_ID){
        barnItem.itemId= autoIncrementId++ }
        barnList.add(barnItem)
        updateList()
    }

    private fun updateList(){
        barnListLD.value= barnList.toList()
    }

}