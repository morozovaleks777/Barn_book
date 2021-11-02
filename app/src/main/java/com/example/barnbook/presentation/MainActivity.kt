package com.example.barnbook.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.barnbook.R

class MainActivity : AppCompatActivity() {

private lateinit var barnListAdapter : BarnListAdapter
   private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.barnList.observe(this){
            Log.d("Test","$it")
            barnListAdapter.barnList=it
        }

    }
    private fun setupRecyclerView(){
        val rvBarnList=findViewById<RecyclerView>(R.id.rv_barn_list)
        with(rvBarnList) {
            barnListAdapter = BarnListAdapter()
            adapter = barnListAdapter

            recycledViewPool.setMaxRecycledViews(
                BarnListAdapter.VIEW_TYPE_ENABLED,
                BarnListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                BarnListAdapter.VIEW_TYPE_DISABLED,
                BarnListAdapter.MAX_POOL_SIZE
            )
        }
       setupLongClickListener()
       setupClickListener()
        setupswipeListener(rvBarnList)
    }

    private fun setupswipeListener(rvBarnList: RecyclerView?) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = barnListAdapter.barnList[viewHolder.adapterPosition]
                viewModel.deleteBarnItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvBarnList)
    }

    private fun setupLongClickListener() {
        barnListAdapter.onBarnItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
    private fun setupClickListener() {
        barnListAdapter.onBarnItemClickListener = {
           Log.d("Test", it.toString())
        }
    }
}