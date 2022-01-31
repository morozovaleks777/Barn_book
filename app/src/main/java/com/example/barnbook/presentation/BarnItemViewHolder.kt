package com.example.barnbook.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barnbook.R

class BarnItemViewHolder( val view: View): RecyclerView.ViewHolder(view){
    val tvName: TextView =view.findViewById(R.id.tv_name)
    val tvCount: TextView =view.findViewById(R.id.tv_count)
    val tvPrice: TextView =view.findViewById(R.id.tv_price)
}