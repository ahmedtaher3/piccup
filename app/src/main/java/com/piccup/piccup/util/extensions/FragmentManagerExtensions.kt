package com.crycl.crycl.utils.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction?) {
    beginTransaction().func()?.commit()
}