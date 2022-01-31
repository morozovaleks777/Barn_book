package com.example.barnbook.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.barnbook.domain.BarnItem
import com.example.barnbook.domain.BarnListRepository
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class BarnListRepositoryImpl(application: Application):BarnListRepository {

    private val barnListDao = AppDatabase.getInstance(application).barnListDao()
    private val mapper = BarnListMapper()

    override suspend fun deleteBarnItem(barnItem: BarnItem) {
        barnListDao.deleteBarnItem(barnItem.itemId)
    }

    override suspend fun editBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))

    }

    override fun getAmount(list: List<BarnItem>): Float {
        var sum = 0F

        for (i in list.indices) {
            sum += list[i].count * list[i].price

        }
        return sum
    }

    override suspend fun getBarnItem(itemId: Int): BarnItem {
        val dbModel = barnListDao.getBarnItem(itemId)
        return mapper.mapDBModelToEntity(dbModel)
    }

    override fun getBarnList(): LiveData<List<BarnItem>> =
        Transformations.map(barnListDao.getBarnItemList()) {
            mapper.mapListDBModelToListEntity(it)
        }

    override suspend fun addBarnItem(barnItem: BarnItem) {
        barnListDao.addBarnItem(mapper.mapEntityToDBModel(barnItem))
        Log.d("test"," base ${getBarnList().value}")
    }


    override suspend  fun getCurrentTime():String{

        // Текущее время
        val currentDate = Date()

// Форматирование времени как "день.месяц.год"
            val dateFormat= SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val dateText: String = dateFormat.format(currentDate)

// Форматирование времени как "часы:минуты:секунды"
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val timeText: String = timeFormat.format(currentDate)
        while (true) {
            delay(1000)
            return "$timeText \n $dateText"
        }
        }
    }
