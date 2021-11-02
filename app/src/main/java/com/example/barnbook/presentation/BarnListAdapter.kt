package com.example.barnbook.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.barnbook.R
import com.example.barnbook.domain.BarnItem

class BarnListAdapter : ListAdapter<BarnItem, BarnItemViewHolder>(BarnItemDiffCallback()){


var onBarnItemLongClickListener:((BarnItem) -> Unit)? =null
var onBarnItemClickListener:((BarnItem) -> Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarnItemViewHolder {
val layout=when(viewType){
    VIEW_TYPE_ENABLED ->R.layout.item_barn_enabled
    VIEW_TYPE_DISABLED ->R.layout.item_barn_disabled
    else ->throw RuntimeException("unknown view type : $viewType")
}

       val  view = LayoutInflater.from(parent.context).inflate(layout, parent,false)

        return BarnItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarnItemViewHolder, position: Int) {
        val barnItem=getItem(position)
        holder.tvName.text=barnItem.name
        holder.tvCount.text=barnItem.count.toString()

        holder.view.setOnLongClickListener {
            onBarnItemLongClickListener?.invoke(barnItem)
true
        }
        holder.view.setOnClickListener{
            onBarnItemClickListener?.invoke(barnItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item=getItem(position)
        return if (!item.enabled)  {VIEW_TYPE_ENABLED}
        else {VIEW_TYPE_DISABLED}



    }
companion object{
    const val VIEW_TYPE_DISABLED = -1
    const val VIEW_TYPE_ENABLED = 1
    const val MAX_POOL_SIZE=10
}
}