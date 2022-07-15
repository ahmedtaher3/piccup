package com.piccup.piccup.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.piccup.piccup.base.BaseViewModel
import com.piccup.piccup.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel(repository) {



    val signUpState = MutableLiveData<Status>()
    fun signUp(name: String, email: String, password: String, deviceId: String, refer: String) {
        signUpState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default + CoroutineExceptionHandler { _, _ ->
                signUpState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.signUp(name, email, password, deviceId, refer)
                signUpState.postValue(Status.Success(response))
            }
        }
    }

    val loginState = MutableLiveData<Status>()
    fun login(email: String, password: String, deviceId: String) {
        loginState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default+ CoroutineExceptionHandler { _, _ ->
                loginState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.userLogin(email, password, deviceId)
                loginState.postValue(Status.Success(response))
            }
        }
    }

    val verifyState = MutableLiveData<Status>()
    fun verify(phone: String, verify: Boolean?) {
        verifyState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default + CoroutineExceptionHandler { _, _ ->
                verifyState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.verify(phone, verify)
                verifyState.postValue(Status.Success(response))
            }
        }
    }

    val updateUserState = MutableLiveData<Status>()
    fun updateUser(
        id: String,
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        device_id: String? = null,
        wallet: Double? = null,
    ) {
        updateUserState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default + CoroutineExceptionHandler { _, _ ->
                updateUserState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.updateUser(id, name, email, phone, device_id, wallet)
                updateUserState.postValue(Status.Success(response))
            }
        }
    }


    val resetPasswordState = MutableLiveData<Status>()
    fun resetPassword(phone: String, password: String) {
        resetPasswordState.postValue(Status.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default + CoroutineExceptionHandler { _, _ ->
                resetPasswordState.postValue(Status.Error(0, "Connection Error"))
            }) {
                val response = repository.resetPassword(phone, password)
                resetPasswordState.postValue(Status.Success(response))
            }
        }
    }
}