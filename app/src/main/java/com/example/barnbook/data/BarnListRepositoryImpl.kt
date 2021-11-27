package com.example.barnbook.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository

class BarnListRepositoryImpl(application: Application):BarnListRepository {

private val barnListDao=AppDatabase.getInstance(application).barnListDao()
    private val mapper=BarnListMapper()

    override fun deleteBarnItem(barnItem: BarnItem) {
      barnListDao.deleteBarnItem(barnItem.itemId)
    }

    override fun editBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))

    }

    override fun getAmount(list: List<BarnItem>): Float {
        var sum=0F

        for (i in list.indices){
           sum+= list[i].count*list[i].price

        }
        return sum
    }

    override fun getBarnItem(itemId: Int): BarnItem {
       val dbModel=barnListDao.getBarnItem(itemId)
        return mapper.mapDBModelToEntity(dbModel)
    }

    override fun getBarnList(): LiveData<List<BarnItem>> =Transformations.map(barnListDao.getBarnItemList()){
        mapper.mapListDBModelToListEntity(it)
    }

    override fun addBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))
    }



}