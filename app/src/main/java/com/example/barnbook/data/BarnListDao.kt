package com.example.barnbook.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barnbook.domain.BarnItem

@Dao
interface BarnListDao {

    @Query("SELECT * FROM barn_items")
fun getBarnItemList():LiveData<List<BarnItemDBModel>>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun addBarnItem(barnItemDBModel: BarnItemDBModel)

@Query("DELETE FROM barn_items WHERE itemId=:barnItemId")
fun deleteBarnItem(barnItemId:Int)

@Query("SELECT * FROM barn_items WHERE itemId=:barnItemId LIMIT 1")
fun getBarnItem(barnItemId:Int):BarnItemDBModel
}