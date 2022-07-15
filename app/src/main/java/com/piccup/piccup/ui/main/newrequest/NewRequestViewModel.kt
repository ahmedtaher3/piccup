package com.piccup.piccup.ui.main.newrequest

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
class NewRequestViewModel @Inject constructor(
    private val repository: NewRequestRepository
) : BaseViewModel(repository) {

    val step = MutableLiveData<Int>(1)
    val packageType = MutableLiveData<PackageModel?>(null)


    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val comment = MutableLiveData<String>()
    val lat = MutableLiveData<Double>(0.0)
    val lng = MutableLiveData<Double>(0.0)
    val enterTime = MutableLiveData<String>("07:00:00")
    val exitTime = MutableLiveData<String>("12:00:00")
    val section = MutableLiveData<String>("")
    val grade = MutableLiveData<String>("")
    val school = MutableLiveData<GetSchoolsQuery.School?>(null)
    val city = MutableLiveData<GetCitiesQuery.City?>(null)


    val packagesState = MutableLiveData<Status>()
    fun getPackages() {
        packagesState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                packagesState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.getPackages()
                packagesState.postValue(Status.Success(response))
            }
        }
    }

    val citiesState = MutableLiveData<Status>()
    fun getCities() {
        citiesState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                citiesState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.getCities()
                citiesState.postValue(Status.Success(response))
            }
        }
    }

    val schoolsState = MutableLiveData<Status>()
    fun getSchools(cityID: String) {
        schoolsState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                schoolsState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.getSchools(cityID)
                schoolsState.postValue(Status.Success(response))
            }
        }
    }

    val sendRequestState = MutableLiveData<Status>()
    fun sendSchoolRequest() {
        sendRequestState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                sendRequestState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.sendSchoolRequest(
                    school.value?.id!!,
                    grade.value!!,
                    name.value!!,
                    phone.value!!,
                    lat.value!!,
                    lng.value!!,
                    address.value!!,
                    packageType.value?.id!!,
                    section.value!!,
                    enterTime.value!!,
                    exitTime.value!!,
                    "",
                    city.value?.id!!,
                    "Pending"
                )
                sendRequestState.postValue(Status.Success(response))
            }
        }
    }
}