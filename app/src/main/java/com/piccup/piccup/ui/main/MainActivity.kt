package com.piccup.piccup.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.UserSchoolRequestsQuery
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivityHomeBinding
import com.piccup.piccup.ui.main.newrequest.NewRequestActivity
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import com.piccup.piccup.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityHomeBinding>(), MainAdapter.OnRequestSelect {
    lateinit var binding: ActivityHomeBinding
    lateinit var adapter: MainAdapter
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        adapter = MainAdapter(this, ArrayList(), this)
        binding.requests.adapter = adapter

        binding.newRequest.setOnClickListener {
            startActivity(Intent(this, NewRequestActivity::class.java))
        }

        viewModel.getRequests()
        observe(viewModel.requestsState)
        {

            when (it) {
                is Status.Loading -> {
                    showDialogLoading()
                }
                is Status.Success<*> -> {
                    hideDialogLoading()
                    val response = it.data as ApolloResponse<UserSchoolRequestsQuery.Data>
                    if (response.hasErrors()) {
                        showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        val list = response.data?.userSchoolRequests
                        if (list?.isEmpty()!!) {
                            binding.emptyLayout.visible()
                        }
                        adapter.setMyData(list as java.util.ArrayList<UserSchoolRequestsQuery.UserSchoolRequest?>)

                    }

                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setOnRequestSelect(model: UserSchoolRequestsQuery.UserSchoolRequest?) {


    }
}