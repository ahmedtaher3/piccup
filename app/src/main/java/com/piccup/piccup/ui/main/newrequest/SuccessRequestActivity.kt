package com.piccup.piccup.ui.main.newrequest

import android.content.Intent
import android.os.Bundle
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivitySuccessRequestBinding
import com.piccup.piccup.ui.main.MainActivity

class SuccessRequestActivity : BaseActivity<ActivitySuccessRequestBinding>() {
    lateinit var binding: ActivitySuccessRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        binding.finish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_success_request
    }
}