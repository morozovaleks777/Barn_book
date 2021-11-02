package com.example.barnbook.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.barnbook.domain.BarnItem

class BarnListDiffCallback(
    private val oldList:List<BarnItem>,
    private val newList:List<BarnItem>

):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       val oldItem=oldList[oldItemPosition]
       val newItem=newList[newItemPosition]
        return oldItem.itemId==newItem.itemId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem=oldList[oldItemPosition]
        val newItem=newList[newItemPosition]
       return oldItem==newItem
    }
}