package com.piccup.piccup.base

import aevapay.aevapay.data.shared.DataManager

import android.content.Context
import com.piccup.piccup.util.ConnectivityUtils
import com.pusher.client.channel.User


abstract class BaseRepository(private val dataManager: DataManager, private var context: Context) {


    private val connectivityUtils: ConnectivityUtils = ConnectivityUtils(context)

    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }



    fun getString(i: Int): String {
        return context.getString(i)
    }

}