package com.example.barnbook.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.barnbook.domain.BarnItem

class BarnItemDiffCallback:DiffUtil.ItemCallback<BarnItem>() {

    override fun areItemsTheSame(oldItem: BarnItem, newItem: BarnItem): Boolean {
       return oldItem.itemId==newItem.itemId
    }

    override fun areContentsTheSame(oldItem: BarnItem, newItem: BarnItem): Boolean {
        return oldItem==newItem
    }
}