package com.piccup.piccup.ui.auth.verify

import android.os.Bundle
import androidx.activity.viewModels
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivityVerifyMobileBinding
import com.piccup.piccup.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyMobileActivity : BaseActivity<ActivityVerifyMobileBinding>() {
    lateinit var binding: ActivityVerifyMobileBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        val isResetPass = intent.getBooleanExtra("RESET", false)

        replaceFragment(PhoneNumberFragment(isResetPass), "PhoneNumberFragment")

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verify_mobile
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}