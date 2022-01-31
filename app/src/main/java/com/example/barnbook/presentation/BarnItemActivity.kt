package com.example.barnbook.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.barnbook.R
import com.example.barnbook.domain.BarnItem

class BarnItemActivity : AppCompatActivity(),BarnItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var barnItemId = BarnItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barn_item)
        checkIntent()
        if(savedInstanceState==null){
        launchRightMode()}
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> BarnItemFragment.newInstanceEditItem(barnItemId)
            MODE_ADD  -> BarnItemFragment.newInstanceAddItem()
            else      -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.barn_item_container, fragment)
            .commit()
    }

    private fun checkIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_BARN_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            barnItemId = intent.getIntExtra(EXTRA_BARN_ITEM_ID, BarnItem.UNDEFINED_ID)
        }
    }



    companion object{
       private  const val EXTRA_MODE="extra_mode"
       const val MODE_ADD="mode_add"
       const val MODE_EDIT="mode_edit"
       private  const val EXTRA_BARN_ITEM_ID="extra_barn_item_id"
        const val MODE_UNKNOWN=""

       fun newIntentAddItem(context: Context):Intent{
           val intent= Intent(context,BarnItemActivity::class.java)
           intent.putExtra(EXTRA_MODE, MODE_ADD)
           return intent
       }

        fun newIntentEditItem(context: Context,barnItemId:Int):Intent{
            val intent= Intent(context,BarnItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_BARN_ITEM_ID,barnItemId)
            return intent
        }
    }

    override fun onEditingFinished() {
     finish()
    }
}