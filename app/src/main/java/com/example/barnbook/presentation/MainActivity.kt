package com.example.barnbook.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.barnbook.R
import com.example.barnbook.databinding.ActivityMainBinding
import com.example.barnbook.presentation.BarnItemActivity.Companion.newIntentAddItem
import com.example.barnbook.presentation.BarnItemActivity.Companion.newIntentEditItem

class MainActivity : AppCompatActivity(),BarnItemFragment.OnEditingFinishedListener {
    private val viewBinding: ActivityMainBinding by viewBinding()
    private var barnItemContainer: FragmentContainerView? = null

    private lateinit var barnListAdapter: BarnListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barnItemContainer = viewBinding.itemContainer
        setupRecyclerView()
        setupTotalFragment()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.barnList.observe(this) {
            Log.d("Test", "$it")
            barnListAdapter.submitList(it)
        }
        val buttonAddItem = viewBinding.buttonAddBarnItem
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = newIntentAddItem(this)
                startActivity(intent)
            } else launchFragment(BarnItemFragment.newInstanceAddItem())
        }

    }

    private fun isOnePaneMode(): Boolean {

        return barnItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvBarnList = viewBinding.rvBarnList
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
        setupSwipeListener(rvBarnList)
    }

    private fun setupSwipeListener(rvBarnList: RecyclerView?) {
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
            if (isOnePaneMode()) {
                val intent = newIntentEditItem(this, it.itemId)
                startActivity(intent)
            } else {
                launchFragment(BarnItemFragment.newInstanceEditItem(it.itemId))
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
    private fun setupTotalFragment(){
        if(isOnePaneMode()){
        supportFragmentManager.beginTransaction()
            .replace(R.id.total_fragment_container,TotalBillFragment())
            .addToBackStack(null)
            .commit()
      }
    }
 }