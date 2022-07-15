package com.piccup.piccup.di

import aevapay.aevapay.data.shared.DataManager
import android.content.Context
import com.piccup.piccup.base.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    @Singleton
    @Provides
    fun provideDataManager(@ApplicationContext context: Context): DataManager =
        (Contexts.getApplication(context) as BaseApplication).dataManager!!


}