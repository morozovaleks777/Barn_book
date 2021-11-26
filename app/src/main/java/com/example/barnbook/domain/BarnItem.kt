package com.example.barnbook.domain

data class BarnItem(
    val name:String,
    val count:Int,
    val price:Float=0.0F,
    val enabled:Boolean,
    var itemId: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID=0
    }
}
