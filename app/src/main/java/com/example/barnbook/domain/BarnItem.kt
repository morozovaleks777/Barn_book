package com.example.barnbook.domain

data class BarnItem(
    val name:String,
    val count:Int,
    val price:Float,
    val enabled:Boolean,
    var itemId: Int = UndefindId
){
    companion object{
        const val UndefindId=-1
    }
}
