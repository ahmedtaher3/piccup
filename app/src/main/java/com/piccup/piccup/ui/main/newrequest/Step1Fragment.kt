package com.piccup.piccup.ui.main.newrequest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseFragment
import com.piccup.piccup.databinding.FragmentStep1Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class Step1Fragment : BaseFragment<FragmentStep1Binding>() {

    lateinit var binding: FragmentStep1Binding
    val viewModel: NewRequestViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.step.postValue(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!


        binding.studentName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.name.postValue(binding.studentName.text.toString())
            }
        })


        binding.phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.phone.postValue(binding.phoneNumber.text.toString())
            }
        })


        binding.address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.address.postValue(binding.address.text.toString())
            }
        })






        binding.location.setOnClickListener {

            val intent = Intent(baseActivity, SelectSingleLocation::class.java)
            resultLauncher.launch(intent)

        }

        binding.next.setOnClickListener {
            replaceFragment(Step2Fragment(), "Step2Fragment")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_step1
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                viewModel.lat.postValue(data?.getDoubleExtra("LAT" , 0.0)!!)
                viewModel.lng.postValue(data.getDoubleExtra("LONG" , 0.0))
                binding.location.setText(data.getStringExtra("ADDRESS"))
            }
        }

}