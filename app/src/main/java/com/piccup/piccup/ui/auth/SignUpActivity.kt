package com.piccup.piccup.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.CreateUserMutation
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivitySignUpBinding
import com.piccup.piccup.ui.auth.verify.VerifyMobileActivity
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import com.piccup.piccup.util.extensions.toJsonString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    lateinit var binding: ActivitySignUpBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        binding.signup.setOnClickListener {

            viewModel.signUp(
                binding.username.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString(),
                "",
                binding.referralCode.text.toString(),
            )

        }



        observe(viewModel.signUpState)
        {

            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    val response = it.data as ApolloResponse<CreateUserMutation.Data>
                    if (response.hasErrors()) {
                        showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        dataManager.saveUser(response.data?.createUser?.user?.toJsonString()!!)
                        dataManager.saveToken(response.data?.createUser?.access_token!!)

                        val intent = Intent(this, VerifyMobileActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_sign_up
    }
}