package com.piccup.piccup.util


import android.content.ContentUris
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLConnection
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CommonUtilities"

object CommonUtilities {


    fun getFirstOfLastMonth(): Long {

        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0

        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        cal[Calendar.DAY_OF_MONTH] = 1
        System.out.println("Start of the month:       " + cal.time)
        System.out.println("... in milliseconds:      " + cal.timeInMillis)
        return cal.timeInMillis


    }

    fun getLastOfLastMonth(): Long {

        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0

        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        cal.add(Calendar.MONTH, -1)
        System.out.println("Start of the next month:  " + cal.time)
        System.out.println("... in milliseconds:      " + cal.timeInMillis)

        return cal.timeInMillis - 86400000
    }


    fun getNumberWithout0(number: String): String {

        return if (number.first().toString() == "0") {
            number.drop(1)
        } else {
            number
        }
    }


    fun convertToDate(milli: Long): String? {
        val fmt = SimpleDateFormat("MMM dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertToDay(milli: Long): String? {
        val fmt = SimpleDateFormat("MMM dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertToFullDate(milli: Long): String? {
        val fmt = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return fmt.format(milli)
    }


    fun convertToTime(milli: Long): String? {
        val fmt = SimpleDateFormat("hh:mm a", Locale.US)
        return fmt.format(milli)
    }


    fun convertToFullDateFormat(milli: Long): String? {
        val fmt = SimpleDateFormat("dd-MM-yyyy' - 'hh:mm a", Locale.US)
        return fmt.format(milli)
    }

    fun convertToDateSearch(milli: Long): String? {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(milli)
    }

    fun convertFullDateToMillisNew(oldTime: String?): Long? {

        val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(oldTime)
        val milliseconds = date.time
        return milliseconds
    }

    fun convertFullDateToMillis(oldTime: String?): Long? {
        val formatter =
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(oldTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis
    }

    fun hasNavBar(resources: Resources): Boolean {
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    fun getRandomNumber(): Int {


        val rand = Random()
        val n = rand.nextInt(3)
        return n
    }

    fun isVideoFile(path: String?): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType != null && mimeType.startsWith("video")
    }


    fun get_Date(myDate: String?): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm a")
        var date: Date? = null
        try {
            date = sdf.parse(myDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return DateUtils.getRelativeTimeSpanString(
            date!!.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun checkTwoDigits(n: String): String {
        return if (n.length == 1) {
            "0$n"
        } else {
            n
        }
    }

    fun convertStringToDate(text: String?): Date? {
        var date: Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        try {
            date = format.parse(text)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }


    fun getCustomImagePath(context: Context?, fileName: String?): File? {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            val filePath = Environment.getExternalStorageDirectory().path
            val myDir = File(filePath, "Devart")
            myDir.mkdirs()
            var fname: String? = null
            fname =
                if (fileName != null) "$fileName.png" else Calendar.getInstance().timeInMillis.toString() + ".png"
            val file = File(myDir, fname)
            if (file.exists()) file.delete()

            //return (file.getAbsolutePath());
            return file
        } else {
            Toast.makeText(context, "Sd Card is not mounted", Toast.LENGTH_LONG).show()
        }
        return null
    }

    fun DecodeFile(id: String, path: String, DESIREDWIDTH: Int, DESIREDHEIGHT: Int): String {
        var strMyImagePath: String? = null
        var scaledBitmap: Bitmap? = null
        try {
            // Part 1: Decode image
            val unscaledBitmap = ScalingUtilities.decodeFile(
                path,
                DESIREDWIDTH,
                DESIREDHEIGHT,
                ScalingUtilities.ScalingLogic.FIT
            )
            scaledBitmap =
                if (!(unscaledBitmap.width <= DESIREDWIDTH && unscaledBitmap.height <= DESIREDHEIGHT)) {
                    // Part 2: Scale image
                    ScalingUtilities.createScaledBitmap(
                        unscaledBitmap,
                        DESIREDWIDTH,
                        DESIREDHEIGHT,
                        ScalingUtilities.ScalingLogic.FIT
                    )
                } else {
                    unscaledBitmap.recycle()
                    return path
                }

            // Store to tmp file
            val extr = Environment.getExternalStorageDirectory().toString()
            val mFolder = File("$extr/TMMFOLDER")
            if (!mFolder.exists()) {
                mFolder.mkdir()
            }
            val s = id + "_" + System.currentTimeMillis().toString() + "_aeva.png"
            val f = File(mFolder.absolutePath, s)
            strMyImagePath = f.absolutePath
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(f)
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            scaledBitmap.recycle()
        } catch (e: Throwable) {
        }
        return strMyImagePath ?: path
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun GetPathFromUri(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun getCharacters(fullname: String): String {
        var initials = ""
        for (s in fullname.split(" ")) {
            initials += s[0]
        }
        println(initials)

        if (initials.length == 1) {
            return fullname.take(2)
        } else {
            return initials
        }


    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.parseColor("#200d54")
    }



    private fun saveImage(context: Context , bytes: ByteArray) {
        val outStream: FileOutputStream
        try {


            val name = System.currentTimeMillis().toString()
            val cw = ContextWrapper(context)
            // path to /data/data/yourapp/app_data/imageDir
            val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
            // Create imageDir
            val mypath = File(directory, name)
            outStream = FileOutputStream(mypath)
            outStream.write(bytes)
            outStream.close()

          //  binding.imageView.setImageURI(Uri.fromFile(mypath))
            // binding.imageView.scaleY = -1F;
          //  showSurface(false)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
