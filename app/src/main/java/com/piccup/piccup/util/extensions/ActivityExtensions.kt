package com.crycl.crycl.utils.extensions

 import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.inTransaction { replace(frameId, fragment) }
}

