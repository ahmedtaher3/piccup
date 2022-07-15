package com.piccup.piccup.base;

import aevapay.aevapay.data.shared.DataManager
import com.piccup.piccup.data.shared.SharedPrefsHelper
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import com.piccup.piccup.util.LocaleUtils
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class BaseApplication : MultiDexApplication() {
    var dataManager: DataManager? = null

    private var mActivityTransitionTimer: Timer? = null
    private var mActivityTransitionTimerTask: TimerTask? = null
    var wasInBackground = false
    private val MAX_ACTIVITY_TRANSITION_TIME_MS: Long = 5000

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleUtils.updateConfig(this, newConfig)
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPrefsHelper = SharedPrefsHelper(applicationContext)
        dataManager = DataManager(sharedPrefsHelper)

    }


    fun startActivityTransitionTimer() {
        mActivityTransitionTimer = Timer()
        mActivityTransitionTimerTask = object : TimerTask() {
            override fun run() {
                this@BaseApplication.wasInBackground = true
            }
        }
        mActivityTransitionTimer?.schedule(
            mActivityTransitionTimerTask,
            MAX_ACTIVITY_TRANSITION_TIME_MS
        )
    }

    fun stopActivityTransitionTimer() {
        if (mActivityTransitionTimerTask != null) {
            mActivityTransitionTimerTask?.cancel()
        }
        if (mActivityTransitionTimer != null) {
            mActivityTransitionTimer?.cancel()
        }
        wasInBackground = false
    }
}