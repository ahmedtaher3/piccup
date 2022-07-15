package com.piccup.piccup.util.extensions

 import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
 import com.piccup.piccup.R
 import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


fun Any?.toJsonString(): String = Gson().toJson(this)


fun Any?.toJsonObject(): JsonObject = Gson().toJsonTree(this).asJsonObject

fun <T> Any?.toJsonArray(type: ArrayList<T>): JsonArray = Gson().toJsonTree(type).asJsonArray





inline fun <reified T> String.getListFromString(): ArrayList<T> {

    if (this.isNullOrEmpty())
        return ArrayList()

    return Gson().fromJson<ArrayList<T>>(this, object : TypeToken<ArrayList<T>>() {}.type)
}


fun <T> String?.toObjectFromJson(type: Type): T = Gson().fromJson(this, type)

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L?, body: (T) -> Unit) =
    liveData?.observe(this, Observer(body))

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Int.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%d", this)
}

fun Float.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%.2f", this)
}

fun String.getFormattedNumber(): String {
    val format = DecimalFormat("0.#")
    return try {
        format.format(this.toDouble()).toString()
    } catch (e: Exception) {
        this
    }

}


fun AlertDialog.changeButtonTheme() {
    val negButton = getButton(DialogInterface.BUTTON_NEGATIVE)
    negButton.setBackgroundColor(ContextCompat.getColor(this.context, android.R.color.transparent))
    negButton.setTextColor(ContextCompat.getColor(this.context, R.color.black))

    val posButton = getButton(DialogInterface.BUTTON_POSITIVE)
    posButton.setBackgroundColor(ContextCompat.getColor(this.context, android.R.color.transparent))
    posButton.setTextColor(ContextCompat.getColor(this.context, R.color.black))
}

fun Int.isColorDark(): Boolean {
    val darkness =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}

fun File.getImageFilePart(name :String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name,
        this.name,
        this.asRequestBody("image/*".toMediaType())
    )
}

fun String.getStringPart(): RequestBody {
    return this.toRequestBody("text/plain".toMediaType())
}


