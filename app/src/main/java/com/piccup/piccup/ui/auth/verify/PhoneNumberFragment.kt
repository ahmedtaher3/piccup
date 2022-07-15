package com.piccup.piccup.ui.auth.verify

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.UserPhoneVerificationMutation
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseFragment
import com.piccup.piccup.databinding.ActivityPhoneNumberBinding
import com.piccup.piccup.ui.auth.AuthViewModel
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberFragment(private val isResetPass :Boolean) : BaseFragment<ActivityPhoneNumberBinding>() {

    lateinit var binding: ActivityPhoneNumberBinding
    val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        binding.confirm.setOnClickListener {

            viewModel.verify(binding.phoneNumber.text.toString(), !isResetPass)
        }


        observe(viewModel.verifyState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<UserPhoneVerificationMutation.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        replaceFragment(
                            PinCodeFragment(binding.phoneNumber.text.toString(), response.data?.userPhoneVerification?.verificationCode!! , isResetPass),
                            "PinCodeFragment"
                        )

                    }

                }
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_phone_number
    }
}