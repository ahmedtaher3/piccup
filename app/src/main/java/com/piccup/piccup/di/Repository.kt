package com.piccup.piccup.di


import aevapay.aevapay.data.shared.DataManager
import android.content.Context
import com.piccup.piccup.ui.auth.AuthRepository
import com.piccup.piccup.ui.main.MainRepository
import com.piccup.piccup.ui.main.newrequest.NewRequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Repository {

    @Singleton
    @Provides
    fun provideAuthRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager
    ): AuthRepository =
        AuthRepository(appContext, dataManager)

    @Singleton
    @Provides
    fun provideNewRequestRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager
    ): NewRequestRepository =
        NewRequestRepository(appContext, dataManager)

    @Singleton
    @Provides
    fun provideMainRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager
    ): MainRepository =
        MainRepository(appContext, dataManager)

}