package com.piccup.piccup.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.UserLoginMutation
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivityLoginBinding
import com.piccup.piccup.ui.main.MainActivity
import com.piccup.piccup.ui.auth.verify.VerifyMobileActivity
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import com.piccup.piccup.util.extensions.toJsonString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    lateinit var binding: ActivityLoginBinding
    val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!

        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, VerifyMobileActivity::class.java)
            intent.putExtra("RESET" , true)
            startActivity(intent)
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.login.setOnClickListener {
            viewModel.login(binding.username.text.toString(), binding.password.text.toString(), "")
        }

        observe(viewModel.loginState)
        {

            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    val response = it.data as ApolloResponse<UserLoginMutation.Data>
                    if (response.hasErrors()) {
                        showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        val user = response.data?.userLogin?.user
                        dataManager.saveUser(user?.toJsonString()!!)
                        dataManager.saveToken(response.data?.userLogin?.access_token!!)

                        if (user.phone != null){
                            dataManager.saveIsLogin(true)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else
                        {
                            val intent = Intent(this, VerifyMobileActivity::class.java)
                            startActivity(intent)
                        }


                    }

                }
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
}