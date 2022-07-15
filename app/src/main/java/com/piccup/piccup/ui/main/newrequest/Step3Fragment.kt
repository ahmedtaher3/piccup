package com.piccup.piccup.ui.main.newrequest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.apollographql.apollo3.api.ApolloResponse
import com.bumptech.glide.Glide
import com.example.myapplication.CreateSchoolRequestMutation
import com.example.myapplication.PricePackagesQuery
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseFragment
import com.piccup.piccup.databinding.FragmentStep3Binding
import com.piccup.piccup.ui.main.newrequest.adapter.PackagesAdapter
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import com.piccup.piccup.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Step3Fragment : BaseFragment<FragmentStep3Binding>(), PackagesAdapter.OnPackageSelect {

    lateinit var binding: FragmentStep3Binding
    lateinit var adapter: PackagesAdapter
    val viewModel: NewRequestViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.step.postValue(3)
        viewModel.getPackages()
        adapter = PackagesAdapter(baseActivity, ArrayList(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!



        binding.send.setOnClickListener {
            viewModel.sendSchoolRequest()
        }


        observe(viewModel.packagesState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<PricePackagesQuery.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        binding.types.apply {
                            layoutManager = GridLayoutManager(
                                baseActivity,
                                response.data?.pricePackages?.size!!
                            )
                        }
                        binding.types.adapter = adapter

                        val list = ArrayList<PackageModel>()
                        for (i in response.data?.pricePackages!!) {
                            list.add(
                                PackageModel(
                                    i?.id,
                                    i?.name,
                                    i?.photo,
                                    i?.description,
                                    i?.per_month,
                                    i?.per_semester,
                                    false,
                                )
                            )
                        }
                        list.first().selected = true
                        adapter.setMyData(list)
                        viewModel.packageType.postValue(list.first())
                        binding.dataLayout.visible()


                    }

                }
            }
        }

        observe(viewModel.sendRequestState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<CreateSchoolRequestMutation.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        val intent = Intent(baseActivity, SuccessRequestActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }

                }
            }
        }

        viewModel.packageType.observe(baseActivity) {
            setData(it)
        }

    }

    fun setData(model: PackageModel) {
        Glide
            .with(baseActivity)
            .load(model.photo)
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .into(binding.image);

        binding.perMonth.text =
            "${model.per_month} ${getString(R.string.egp)}  ${getString(R.string.per_month)}"
        binding.perYear.text =
            "${model.per_semester} ${getString(R.string.egp)}  ${getString(R.string.per_semester)}"
        binding.desc.text = model.description
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_step3
    }

    override fun setOnPackageSelect(model: PackageModel) {
        viewModel.packageType.postValue(model)
    }


}