package com.piccup.piccup.ui.main

import aevapay.aevapay.data.shared.DataManager
import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.UserSchoolRequestsQuery
import com.piccup.piccup.base.BaseRepository
import com.piccup.piccup.data.remote.ApolloConnector
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager
) : BaseRepository(dataManager, context) {


    suspend fun getRequests(): ApolloResponse<UserSchoolRequestsQuery.Data> {
        return ApolloConnector.setupApollo(dataManager).query(
            UserSchoolRequestsQuery(dataManager.user.id!!)
        ).execute()
    }


}