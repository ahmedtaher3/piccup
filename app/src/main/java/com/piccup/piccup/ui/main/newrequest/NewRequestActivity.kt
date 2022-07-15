package com.piccup.piccup.ui.main.newrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivityNewRequestBinding
import com.piccup.piccup.util.extensions.greyLine
import com.piccup.piccup.util.extensions.yellowLine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRequestActivity : BaseActivity<ActivityNewRequestBinding>() {
    lateinit var binding: ActivityNewRequestBinding
    val viewModel: NewRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        replaceFragment(Step1Fragment(), "Step1Fragment")

        binding.back.setOnClickListener {
            onBackPressed()
        }

        viewModel.step.observe(this, Observer {

            when (it) {
                1 -> {
                    binding.step1.yellowLine()
                    binding.step2.greyLine()
                    binding.step3.greyLine()
                    binding.step4.greyLine()
                }
                2 -> {
                    binding.step1.yellowLine()
                    binding.step2.yellowLine()
                    binding.step3.greyLine()
                    binding.step4.greyLine()
                }
                3 -> {
                    binding.step1.yellowLine()
                    binding.step2.yellowLine()
                    binding.step3.yellowLine()
                    binding.step4.greyLine()
                }
                4 -> {
                    binding.step1.yellowLine()
                    binding.step2.yellowLine()
                    binding.step3.yellowLine()
                    binding.step4.yellowLine()
                }
            }

        })

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_new_request
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}