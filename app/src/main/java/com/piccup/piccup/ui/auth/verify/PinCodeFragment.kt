package com.piccup.piccup.ui.auth.verify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.UpdateUserMutation
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseFragment
import com.piccup.piccup.databinding.ActivityPinCodeBinding
import com.piccup.piccup.ui.main.MainActivity
import com.piccup.piccup.ui.auth.AuthViewModel
import com.piccup.piccup.ui.auth.ResetPasswordActivity
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinCodeFragment(
    private val phone: String,
    private val code: String,
    private val isResetPass: Boolean
) : BaseFragment<ActivityPinCodeBinding>() {
    lateinit var binding: ActivityPinCodeBinding
    val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        binding.pinCode.setOnPinEnteredListener {
            if (it.toString() == code) {

                if (isResetPass) {

                    val intent = Intent(baseActivity, ResetPasswordActivity::class.java)
                    intent.putExtra("PHONE", phone)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    baseActivity.dataManager.saveIsLogin(true)
                    val user = baseActivity.dataManager.user
                    viewModel.updateUser(user.id!!, phone = phone)
                }

            } else {
                binding.pinCode.setText("")
            }

        }



        observe(viewModel.updateUserState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<UpdateUserMutation.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        if (isResetPass) {
                            baseActivity.dataManager.saveIsLogin(true)
                            val intent = Intent(baseActivity, ResetPasswordActivity::class.java)
                            intent.putExtra("PHONE", phone)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            baseActivity.dataManager.saveIsLogin(true)
                            val intent = Intent(baseActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }


                    }

                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_pin_code
    }
}