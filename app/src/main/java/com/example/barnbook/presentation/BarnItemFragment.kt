package com.example.barnbook.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.barnbook.R
import com.example.barnbook.databinding.FragmentBarnItemBinding
import com.example.barnbook.domain.BarnItem
import com.google.android.material.textfield.TextInputLayout


class BarnItemFragment : Fragment() {

    private  var _binding: FragmentBarnItemBinding?=null
    private val binding:FragmentBarnItemBinding
        get() =_binding?:throw java.lang.RuntimeException("FragmentBarnItemBinding = null")
    private lateinit var viewModel: BarnItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var etPrice: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var barnItemId: Int = BarnItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener=context
        }else{
            throw RuntimeException("activity must implement OnEditingFinishedListener")
        }
    }
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            _binding= FragmentBarnItemBinding.inflate(inflater,container,false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel = ViewModelProvider(this)[BarnItemViewModel::class.java]
            initViews()
            addTextChangeListeners()
            launchRightMode()
            observeViewModel()
        }

        private fun observeViewModel() {
            viewModel.errorInputCount.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.error_input_count)
                } else {
                    null
                }
                tilCount.error = message
            }
            viewModel.errorInputName.observe(viewLifecycleOwner) {
                val message = if (it) {
                    getString(R.string.error_input_name)
                } else {
                    null
                }
                tilName.error = message
            }
            viewModel.closeScreen.observe(viewLifecycleOwner) {
               activity?.onBackPressed()
            }
        }

        private fun launchRightMode() {
            when (screenMode) {
                MODE_EDIT -> launchEditMode()
                MODE_ADD  -> launchAddMode()
            }
        }

        private fun addTextChangeListeners() {
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorInputName()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            etCount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorInputCount()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }


        private fun launchEditMode() {
            viewModel.getBarnItem(barnItemId)
            viewModel.barnItem.observe(viewLifecycleOwner) {
                etName.setText(it.name)
                etCount.setText(it.count.toString())
                etPrice.setText(it.price.toString())
            }
            buttonSave.setOnClickListener {
                viewModel.editBarnItem(etName.text?.toString(), etCount.text?.toString(),etPrice.text.toString())
            }
        }

        private fun launchAddMode() {
            buttonSave.setOnClickListener {
                viewModel.addBarnItem(etName.text?.toString(), etCount.text?.toString(),etPrice.text?.toString())
            }
        }

        private fun parseParams() {
            val args = requireArguments()
            if (!args.containsKey(SCREEN_MODE)) {
                throw RuntimeException("Param screen mode is absent")
            }
            val mode = args.getString(SCREEN_MODE)
            if (mode != MODE_EDIT && mode != MODE_ADD) {
                throw RuntimeException("Unknown screen mode $mode")
            }
            screenMode = mode
            if (screenMode == MODE_EDIT) {
                if (!args.containsKey(SHOP_ITEM_ID)) {
                    throw RuntimeException("Param shop item id is absent")
                }
                barnItemId = args.getInt(SHOP_ITEM_ID, BarnItem.UNDEFINED_ID)
            }
        }

        private fun initViews() {

           tilPrice=binding.tilPrice
            tilName = binding.tilName
            tilCount = binding.tilCount
            etPrice=binding.etPrice
            etName = binding.etName
            etCount = binding.etCount
            buttonSave = binding.saveButton
        }

        companion object {

            private const val SCREEN_MODE = "extra_mode"
            private const val SHOP_ITEM_ID = "extra_shop_item_id"
            private const val MODE_EDIT = "mode_edit"
            private const val MODE_ADD = "mode_add"
            private const val MODE_UNKNOWN = ""

            fun newInstanceAddItem(): BarnItemFragment {
                return BarnItemFragment().apply {
                    arguments=Bundle().apply {
                        putString(SCREEN_MODE, MODE_ADD)
                      }
                   }
                }


            fun newInstanceEditItem(shopItemId: Int): BarnItemFragment {
                return BarnItemFragment().apply {
                    arguments=Bundle().apply {
                        putString(SCREEN_MODE, MODE_EDIT)
                        putInt(SHOP_ITEM_ID, shopItemId)

                    }
                }
            }
        }
    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    }