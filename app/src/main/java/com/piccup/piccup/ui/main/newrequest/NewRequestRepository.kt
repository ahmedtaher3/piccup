package com.piccup.piccup.ui.main.newrequest

import aevapay.aevapay.data.shared.DataManager
import android.content.Context
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.myapplication.CreateSchoolRequestMutation
import com.example.myapplication.GetCitiesQuery
import com.example.myapplication.GetSchoolsQuery
import com.example.myapplication.PricePackagesQuery
import com.piccup.piccup.base.BaseRepository
import com.piccup.piccup.data.remote.ApolloConnector
import javax.inject.Inject

class NewRequestRepository @Inject constructor(
    private val context: Context,
    private val dataManager: DataManager
) : BaseRepository(dataManager, context) {


    suspend fun getPackages(): ApolloResponse<PricePackagesQuery.Data> {
        return ApolloConnector.setupApollo(dataManager).query(
            PricePackagesQuery()
        ).execute()
    }

    suspend fun getCities(): ApolloResponse<GetCitiesQuery.Data> {
        return ApolloConnector.setupApollo(dataManager).query(
            GetCitiesQuery()
        ).execute()
    }
    suspend fun getSchools(cityID: String): ApolloResponse<GetSchoolsQuery.Data> {
        return ApolloConnector.setupApollo(dataManager).query(
            GetSchoolsQuery(cityID)
        ).execute()
    }


    suspend fun sendSchoolRequest(
        school: String,
        grad: String,
        name: String,
        phone: String,
        lat: Double,
        lng: Double,
        address: String,
        pac: String,
        section: String,
        enter: String,
        exit: String,
        cooment: String?,
        city: String,
        status: String,
    ): ApolloResponse<CreateSchoolRequestMutation.Data> {
        return ApolloConnector.setupApollo(dataManager).mutation(
            CreateSchoolRequestMutation(
                dataManager.user.id!!,
                school,
                grad,
                name,
                phone,
                lat,
                lng,
                address,
                pac,
                section,
                enter,
                exit,
                Optional.presentIfNotNull(cooment),
                city,
                status
            )
        ).execute()
    }

}