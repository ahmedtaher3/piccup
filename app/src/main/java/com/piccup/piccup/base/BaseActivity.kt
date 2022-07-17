package com.piccup.piccup.base

import aevapay.aevapay.data.shared.DataManager
import android.annotation.TargetApi
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.piccup.piccup.BuildConfig
import com.piccup.piccup.R
import com.piccup.piccup.util.LocaleUtils


private const val TAG = "BaseActivity"

const val APP_VERSION = "google"

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {
    val REQUEST_PHONE_CODE: Int = 500

    var viewDataBinding: T? = null
        private set


    private var pd: Dialog? = null

    /**
     * @return layout resource id
     */

    @LayoutRes
    abstract fun getLayoutId(): Int

    init {
        LocaleUtils.updateConfig(this)
    }


    lateinit var dataManager: DataManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.yellow)
        }

        createCustomProgressDialog()
        dataManager = (application as BaseApplication).dataManager!!
        Log.d(TAG, "onCreate: ${dataManager.lang}")
        if (dataManager.lang != null) {
            if (dataManager.lang.equals(LocaleUtils.LAN_ENGLISH)) {
                LocaleUtils.setLocale(java.util.Locale(LocaleUtils.LAN_ENGLISH))
                LocaleUtils.updateConfig(
                    application,
                    baseContext.resources.configuration
                )
            } else {
                LocaleUtils.setLocale(java.util.Locale(LocaleUtils.LAN_ARABIC))
                LocaleUtils.updateConfig(
                    application,
                    baseContext.resources.configuration
                )
            }
        }



        performDataBinding()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    //  return InternetConnectionDetector.IsInternetAvailable(getApplicationContext());
    val isNetworkConnected: Boolean
        get() =//  return InternetConnectionDetector.IsInternetAvailable(getApplicationContext());
            true

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding?.executePendingBindings()
    }

    private fun createCustomProgressDialog() {
        this.let {
            pd = Dialog(it, R.style.DialogCustomTheme)
            pd?.setContentView(R.layout.progress_layout)
            pd?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            pd?.setCancelable(false)
        }
    }

    fun showDialogLoading() {
        pd?.let {
            if (!it.isShowing)
                it.show()
        }
    }

    fun hideDialogLoading() {
        pd?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }


    fun showSuccessSnackbar(text: String) {

        val snack: Snackbar = Snackbar.make(viewDataBinding?.root!!, text, Snackbar.LENGTH_LONG)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.setBackgroundTint(resources.getColor(R.color.green)).show()
        val view = snack.view
        val params = view.getLayoutParams() as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.setLayoutParams(params)
        snack.show()

    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun showWarningSnackbar(text: String) {

        val snack: Snackbar = Snackbar.make(viewDataBinding?.root!!, text, Snackbar.LENGTH_LONG)
        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.setBackgroundTint(resources.getColor(R.color.orange)).show()
        val view = snack.view
        val params = view.getLayoutParams() as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.setLayoutParams(params)
        snack.show()
    }

    fun replaceFragment(fragment: Fragment, tag: String, bundle: Bundle = Bundle()) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            .add(
                R.id.container,
                fragment
            )
            .addToBackStack(tag)
            .commit()
    }
    fun replaceFragmentWithoutAnimation(fragment: Fragment, tag: String, bundle: Bundle = Bundle()) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.container,
                fragment
            )
            .addToBackStack(tag)
            .commit()
    }


}