package com.piccup.piccup.ui.auth

import android.content.Intent
import android.os.Bundle
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivitySplashBinding
import com.piccup.piccup.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.piccup.piccup.R
import android.os.Handler
import android.os.Looper


@AndroidEntryPoint
class Splash : BaseActivity<ActivitySplashBinding>() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!



        Handler(Looper.getMainLooper()).postDelayed({
            if (dataManager.isLogin) {
                startActivity(Intent(this@Splash, MainActivity::class.java))
                finish()

            } else {
                startActivity(Intent(this@Splash, LoginActivity::class.java))
                finish()


            }
        }, 3000)





    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
}