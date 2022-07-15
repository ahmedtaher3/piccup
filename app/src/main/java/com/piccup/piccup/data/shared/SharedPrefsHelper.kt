package com.piccup.piccup.data.shared

import android.content.Context
import android.content.SharedPreferences
import com.piccup.piccup.util.LocaleUtils

class SharedPrefsHelper(context: Context?) {
    var mSharedPreferences: SharedPreferences? = null
    fun clear() {
        mSharedPreferences!!.edit().clear().apply()
    }

    ////////////////////////////////////////////////////////////////////////////////
    fun putXApiKey(x: String?) {
        mSharedPreferences!!.edit().putString(XApiKey, x).apply()
    }

    val xApiKey: String?
        get() = mSharedPreferences!!.getString(XApiKey, "")

    ////////////////////////////////////////////////////////////////////////////////

    fun putMobile(x: String?) {
        mSharedPreferences!!.edit().putString(MOBILE, x).apply()
    }

    val mobile: String?
        get() = mSharedPreferences!!.getString(MOBILE, "")


    ////////////////////////////////////////////////////////////////////////////////

    fun putPasscode(x: String?) {
        mSharedPreferences!!.edit().putString(PASSCODE, x).apply()
    }

    val passcode: String?
        get() = mSharedPreferences!!.getString(PASSCODE, "")


    ////////////////////////////////////////////////////////////////////////////////
    fun putToken(x: String?) {
        mSharedPreferences!!.edit().putString(Token, x).apply()
    }

    val token: String?
        get() = mSharedPreferences!!.getString(Token, "")


    ////////////////////////////////////////////////////////////////////////////////
    fun putRefreshToken(x: String?) {
        mSharedPreferences!!.edit().putString(RefreshToken, x).apply()
    }

    val refreshToken: String?
        get() = mSharedPreferences!!.getString(RefreshToken, "")

    ////////////////////////////////////////////////////////////////////////////////
    fun putWallet(x: String?) {
        mSharedPreferences!!.edit().putString(Wallet, x).apply()
    }

    val wallet: String?
        get() = mSharedPreferences!!.getString(Wallet, "")

    ////////////////////////////////////////////////////////////////////////////////
    fun putUser(s: String?) {
        mSharedPreferences!!.edit().putString(USER, s).apply()
    }

    val user: String?
        get() = mSharedPreferences!!.getString(USER, null)

    ////////////////////////////////////////////////////////////////////////////////
    fun putFilterModel(s: String?) {
        mSharedPreferences!!.edit().putString(FilterModel, s).apply()
    }

    val filterModel: String?
        get() = mSharedPreferences!!.getString(FilterModel, null)

    ////////////////////////////////////////////////////////////////////////////////
    fun putFilter(s: String?) {
        mSharedPreferences!!.edit().putString(Filter, s).apply()
    }

    val filter: String?
        get() = mSharedPreferences!!.getString(Filter, null)

    ////////////////////////////////////////////////////////////////////////////////
    fun putIsLogin(b: Boolean) {
        mSharedPreferences!!.edit().putBoolean(IS_LOGGED, b).apply()
    }

    val isLogin: Boolean
        get() = mSharedPreferences!!.getBoolean(IS_LOGGED, false)

    ////////////////////////////////////////////////////////////////////////////////
    fun putFingerprint(b: Boolean) {
        mSharedPreferences!!.edit().putBoolean(Fingerprint, b).apply()
    }

    val fingerprint: Boolean
        get() = mSharedPreferences!!.getBoolean(Fingerprint, false)

    ////////////////////////////////////////////////////////////////////////////////
    fun putCounter(s: Int) {
        mSharedPreferences!!.edit().putInt(Counter, s).apply()
    }

    val counter: Int
        get() = mSharedPreferences!!.getInt(Counter, 0)

    //////////////////////////////////////////////////////////////////////////////////

    fun putHuaweiToken(s: String) {
        mSharedPreferences!!.edit().putString(HuaweiToken, s).apply()
    }

    val huaweiToken: String?
        get() = mSharedPreferences!!.getString(HuaweiToken, "")


    //////////////////////////////////////////////////////////////////////////////////


    fun putLang(name: String?) {
        mSharedPreferences!!.edit().putString(LANG, name).apply()
    }

    fun getLang(): String? {
        return mSharedPreferences!!.getString(LANG, LocaleUtils.LAN_ENGLISH)
    }
    //////////////////////////////////////////////////////////////////////////////////


    fun putServices(name: String?) {
        mSharedPreferences!!.edit().putString(Services, name).apply()
    }

    fun getServices(): String? {
        return mSharedPreferences!!.getString(Services, LocaleUtils.LAN_ENGLISH)
    }
    //////////////////////////////////////////////////////////////////////////////////


    fun putFingerprintDialog(b: Boolean) {
        mSharedPreferences!!.edit().putBoolean(FINGERPRINT_DIALOG, b).apply()
    }

    val fingerprintDialog: Boolean
        get() = mSharedPreferences!!.getBoolean(FINGERPRINT_DIALOG, false)

    //////////////////////////////////////////////////////////////////////////////////


    fun putRequestId(b: String?) {
        mSharedPreferences!!.edit().putString(RequestId, b).apply()
    }

    val requestId: String?
        get() = mSharedPreferences!!.getString(RequestId, "0")

    //////////////////////////////////////////////////////////////////////////////////


    fun putRecentPlaces(b: String?) {
        mSharedPreferences!!.edit().putString(RecentPlaces, b).apply()
    }

    val recentPlaces: String?
        get() = mSharedPreferences!!.getString(RecentPlaces, "")

    //////////////////////////////////////////////////////////////////////////////////


    fun putSavedPlaces(b: String?) {
        mSharedPreferences!!.edit().putString(SavedPlaces, b).apply()
    }

    val savedPlaces: String?
        get() = mSharedPreferences!!.getString(SavedPlaces, "")


    companion object {
        const val RequestId = "RequestId"
        const val SavedPlaces = "SavedPlaces"
        const val RecentPlaces = "RecentPlaces"
        const val MY_PREFS = "AEVAPAY_PREFS"
        const val XApiKey = "SavedData"
        const val Token = "Token"
        const val RefreshToken = "RefreshToken"
        const val Wallet = "Wallet"
        const val IS_LOGGED = "IS_LOGGED"
        const val Fingerprint = "Fingerprint"
        const val USER = "USER"
        const val FilterModel = "FilterModel"
        const val Filter = "Filter"
        const val Counter = "Counter"
        const val HuaweiToken = "HuaweiToken"
        const val LANG = "LANG"
        const val FINGERPRINT_DIALOG = "FINGERPRINT_DIALOG"
        const val MOBILE = "MOBILE"
        const val PASSCODE = "PASSCODE"
        const val Services = "Services"

    }

    init {

        mSharedPreferences = context?.getSharedPreferences(MY_PREFS, 0)

    }

}