package com.piccup.piccup.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.GetCitiesQuery
import com.example.myapplication.GetSchoolsQuery
import com.piccup.piccup.base.BaseViewModel
import com.piccup.piccup.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel(repository) {


    val requestsState = MutableLiveData<Status>()
    fun getRequests() {
        requestsState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                requestsState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.getRequests()
                requestsState.postValue(Status.Success(response))
            }
        }
    }

}