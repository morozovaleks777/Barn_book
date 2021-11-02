package com.example.barnbook.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.barnbook.R
import com.example.barnbook.domain.BarnItem
import java.lang.RuntimeException

class BarnListAdapter:RecyclerView.Adapter<BarnListAdapter.BarnItemViewHolder>() {

var barnList= listOf<BarnItem>()
   set(value) {
       val callback=BarnListDiffCallback(barnList,value)
       val diffResult=DiffUtil.calculateDiff(callback)
       diffResult.dispatchUpdatesTo(this)
    field=value
}
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
        val barnItem=barnList[position]
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
        var item=barnList[position]
        return if (!item.enabled)  {VIEW_TYPE_ENABLED}
        else {VIEW_TYPE_DISABLED}



    }
    override fun getItemCount(): Int {
        return barnList.size
    }


    class BarnItemViewHolder( val view: View):RecyclerView.ViewHolder(view){
        val tvName: TextView =view.findViewById(R.id.tv_name)
        val tvCount: TextView =view.findViewById(R.id.tv_count)
    }
companion object{
    const val VIEW_TYPE_DISABLED = -1
    const val VIEW_TYPE_ENABLED = 1
    const val MAX_POOL_SIZE=10
}
}