package com.piccup.piccup.data.remote

 import aevapay.aevapay.data.shared.DataManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
 import com.piccup.piccup.BuildConfig
 import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object ApolloConnector {

    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
    }

    private const val BASE_URL = "https://api.piccup.me/graphql/v1"
    fun setupApollo(dataManager: DataManager): ApolloClient {
        val token: String = dataManager.token
        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).addInterceptor(Interceptor { chain ->
                    val builder: Request.Builder = chain.request().newBuilder()
                    builder.header("Authorization", "Bearer $token")
                    var lang = dataManager.lang
                    builder.header("Accept-Language", lang!!)
                    chain.proceed(builder.build())
                }).build()
        return ApolloClient.Builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build()
    }
}