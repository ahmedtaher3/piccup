package com.piccup.piccup.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.ResetPasswordWithOtpMutation
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivityResetPasswordBinding
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>() {
    lateinit var binding: ActivityResetPasswordBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        binding.confirm.setOnClickListener {

            if (binding.password.text.toString() == binding.confirmPassword.text.toString()) {
                if (binding.password.text.toString().length >= 6) {
                    viewModel.resetPassword(
                        intent.getStringExtra("PHONE")!!,
                        binding.password.text.toString()
                    )
                } else {
                    showWarningSnackbar("password must be at least 6 characters")
                }
            } else {
                showWarningSnackbar("Passwords not matches")
            }
        }



        observe(viewModel.resetPasswordState)
        {

            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    val response = it.data as ApolloResponse<ResetPasswordWithOtpMutation.Data>
                    if (response.hasErrors()) {
                        showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        showToast(response.data?.resetPasswordWithOtp?.message!!)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }
}