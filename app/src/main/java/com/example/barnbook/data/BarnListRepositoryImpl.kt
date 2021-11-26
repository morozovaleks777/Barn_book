package com.example.barnbook.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class BarnListRepositoryImpl(application: Application):BarnListRepository {
    private val barnList = sortedSetOf<BarnItem>({ o1, o2 -> o1.itemId.compareTo(o2.itemId) })
    private val barnListLD=MutableLiveData<List<BarnItem>>()
    private var autoIncrementId=0

private val barnListDao=AppDatabase.getInstance(application).barnListDao()
    private val mapper=BarnListMapper()

//init {
//    for (i in 0 until 20){
//        val item=BarnItem(" name ",0, 0.0F ,false)
//        addBarnItem(item)
//    }
//}

    override fun deleteBarnItem(barnItem: BarnItem) {
      barnListDao.deleteBarnItem(barnItem.itemId)
    }

    override fun editBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))

    }

    override fun getAmount(list: List<BarnItem>): Float {
        var sum=0F

        for (i in 0 until list.size-1){
           sum+= list[i].count*list[i].price

        }
        return sum
    }

    override fun getBarnItem(itemId: Int): BarnItem {
       val dbModel=barnListDao.getBarnItem(itemId)
        return mapper.mapDBModelToEntity(dbModel)
    }

    override fun getBarnList(): LiveData<List<BarnItem>> =MediatorLiveData<List<BarnItem>>().apply {
        addSource(barnListDao.getBarnItemList()){
           value=mapper.mapListDBModelToListEntity(it)
        }
    }



    override fun addBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))
    }



}