package aevapay.aevapay.data.shared


import com.example.myapplication.UserLoginMutation
import com.piccup.piccup.data.shared.SharedPrefsHelper
import com.piccup.piccup.util.extensions.toObjectFromJson

class DataManager(var mSharedPrefsHelper: SharedPrefsHelper) {
    fun clear() {


        val mobile = mobile
        val passcode = passcode
        val fingerprint = isFingerprint
        val isFingerprintDialog = isFingerprintDialog



        mSharedPrefsHelper.clear()


        savePasscode(passcode)
        saveMobile(mobile)
        saveIsFingerprint(fingerprint)
        saveIsFingerprintDialog(isFingerprintDialog)

    }

    fun saveXApiKey(x: String) {
        mSharedPrefsHelper.putXApiKey(x)
    }

    val xApiKey: String get() = mSharedPrefsHelper.xApiKey!!

    ////////////////////////////////////////////////////////////////////////////////
    fun saveMobile(x: String) {
        mSharedPrefsHelper.putMobile(x)
    }

    val mobile: String get() = mSharedPrefsHelper.mobile!!


    ////////////////////////////////////////////////////////////////////////////////
    fun savePasscode(x: String) {
        mSharedPrefsHelper.putPasscode(x)
    }

    val passcode: String get() = mSharedPrefsHelper.passcode!!


    ////////////////////////////////////////////////////////////////////////////////

    fun saveToken(x: String) {
        mSharedPrefsHelper.putToken(x)
    }

    val token: String get() = mSharedPrefsHelper.token!!


    ////////////////////////////////////////////////////////////////////////////////


    fun saveRefreshToken(x: String) {
        mSharedPrefsHelper.putRefreshToken(x)
    }

    val refreshToken: String get() = mSharedPrefsHelper.refreshToken!!




    ////////////////////////////////////////////////////////////////////////////////


    fun saveWallet(x: String) {
        mSharedPrefsHelper.putWallet(x)
    }

    val wallet: String get() = mSharedPrefsHelper.wallet!!


    ////////////////////////////////////////////////////////////////////////////////


    fun saveIsLogin(b: Boolean) {
        mSharedPrefsHelper.putIsLogin(b)
    }

    val isLogin: Boolean
        get() = mSharedPrefsHelper.isLogin


    ////////////////////////////////////////////////////////////////////////////////

    fun saveIsFingerprint(b: Boolean) {
        mSharedPrefsHelper.putFingerprint(b)
    }

    val isFingerprint: Boolean
        get() = mSharedPrefsHelper.fingerprint

    ////////////////////////////////////////////////////////////////////////////////

    fun saveIsFingerprintDialog(b: Boolean) {
        mSharedPrefsHelper.putFingerprintDialog(b)
    }

    val isFingerprintDialog: Boolean
        get() = mSharedPrefsHelper.fingerprintDialog


    ////////////////////////////////////////////////////////////////////////////////


    fun saveCounter(b: Int) {
        mSharedPrefsHelper.putCounter(b)
    }

    val counter: Int
        get() = mSharedPrefsHelper.counter

    ////////////////////////////////////////////////////////////////////////////////


    fun saveUser(b: String) {
        mSharedPrefsHelper.putUser(b)
    }

    val user: UserLoginMutation.User
        get() = mSharedPrefsHelper.user.toObjectFromJson(UserLoginMutation.User::class.java)



    ////////////////////////////////////////////////////////////////////////////////


    fun saveHuaweiToken(b: String) {
        mSharedPrefsHelper.putHuaweiToken(b)
    }

    val huaweiToken: String?
        get() = mSharedPrefsHelper.huaweiToken

    ////////////////////////////////////////////////////////////////////////////////

    fun saveLang(name: String?) {
        mSharedPrefsHelper.putLang(name)
    }

    val lang: String?
        get() = mSharedPrefsHelper.getLang()

    ////////////////////////////////////////////////////////////////////////////////

    fun saveServices(name: String?) {
        mSharedPrefsHelper.putServices(name)
    }

    val services: String?
        get() = mSharedPrefsHelper.getServices()

    ////////////////////////////////////////////////////////////////////////////////

    fun saveRequestId(requestId: String?) {
        mSharedPrefsHelper.putRequestId(requestId)
    }

    val requestId: String?
        get() = mSharedPrefsHelper.requestId

    ////////////////////////////////////////////////////////////////////////////////

    fun saveRecentPlaces(recentPlaces: String?) {
        mSharedPrefsHelper.putRecentPlaces(recentPlaces)
    }

    val recentPlaces: String?
        get() = mSharedPrefsHelper.recentPlaces

    ////////////////////////////////////////////////////////////////////////////////

    fun saveSavedPlaces(savedPlaces: String?) {
        mSharedPrefsHelper.putSavedPlaces(savedPlaces)
    }

    val savedPlaces: String?
        get() = mSharedPrefsHelper.savedPlaces

}