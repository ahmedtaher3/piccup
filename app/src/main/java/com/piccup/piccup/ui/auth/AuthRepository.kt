package com.piccup.piccup.ui.auth

import aevapay.aevapay.data.shared.DataManager
import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.myapplication.*
import com.piccup.piccup.base.BaseRepository
import com.piccup.piccup.data.remote.ApolloConnector
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager
) : BaseRepository(dataManager, context) {


    suspend fun userLogin(
        email: String,
        password: String,
        deviceId: String
    ): ApolloResponse<UserLoginMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            UserLoginMutation(email, password, "android", Optional.presentIfNotNull(deviceId))
        ).execute()
    }

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        deviceId: String,
        refer: String
    ): ApolloResponse<CreateUserMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            CreateUserMutation(
                name,
                email,
                password,
                "android",
                Optional.presentIfNotNull(deviceId),
                Optional.presentIfNotNull(refer)
            )
        ).execute()
    }

    suspend fun verify(
        phone: String,
        verify: Boolean?
    ): ApolloResponse<UserPhoneVerificationMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            UserPhoneVerificationMutation(phone, Optional.presentIfNotNull(verify))
        ).execute()
    }

    suspend fun updateUser(
        id: String,
        name: String?,
        email: String?,
        phone: String?,
        device_id: String?,
        wallet: Double?,
    ): ApolloResponse<UpdateUserMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            UpdateUserMutation(
                id,
                Optional.presentIfNotNull(name),
                Optional.presentIfNotNull(email),
                Optional.presentIfNotNull(phone),
                Optional.presentIfNotNull(device_id),
                Optional.presentIfNotNull(wallet)
            )
        ).execute()
    }


    suspend fun resetPassword(
        phone: String,
        password: String,
    ): ApolloResponse<ResetPasswordWithOtpMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            ResetPasswordWithOtpMutation(phone, password)
        ).execute()
    }

}