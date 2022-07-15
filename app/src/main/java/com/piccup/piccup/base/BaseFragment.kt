package com.piccup.piccup.base


import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.piccup.piccup.R

abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {
  lateinit  var baseActivity: BaseActivity<*>
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            val activity = context
            baseActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding?.root
        return mRootView
    }

    override fun onDetach() {
       // baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.lifecycleOwner = this
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected


    fun showToast(text: String) {
        Toast.makeText(baseActivity, text, Toast.LENGTH_SHORT).show()
    }
    fun replaceFragment(fragment: Fragment?, tag: String?) {
        baseActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    fun addFragment(fragment: Fragment?, tag: String?) {
        baseActivity.supportFragmentManager
            .beginTransaction()
            .add(
                R.id.container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor =
            baseActivity.getContentResolver().query(uri!!, projection, null, null, null)
                ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }

}