package com.example.barnbook.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barn_items")
data class BarnItemDBModel (
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,
    val name:String,
    val count:Int,
    val price:Float=0.0F,
    val enabled:Boolean,

        )
