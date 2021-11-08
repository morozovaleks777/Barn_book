package com.example.barnbook.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.barnbook.R
import com.example.barnbook.presentation.BarnItemActivity.Companion.newIntentAddItem
import com.example.barnbook.presentation.BarnItemActivity.Companion.newIntentEditItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            barnListAdapter.submitList(it)
        }
        val buttonAddItem=findViewById<FloatingActionButton>(R.id.button_add_barn_item)
        buttonAddItem.setOnClickListener {
            val intent= newIntentAddItem(this)
            startActivity(intent)
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
                val item = barnListAdapter.currentList[viewHolder.adapterPosition]
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
            val intent= newIntentEditItem(this,it.itemId)
            startActivity(intent)
        }
    }
}