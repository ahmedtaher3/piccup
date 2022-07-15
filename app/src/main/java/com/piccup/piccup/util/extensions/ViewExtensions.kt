package com.piccup.piccup.util.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.piccup.piccup.R
import de.hdodenhof.circleimageview.CircleImageView


fun ImageView.loadImage(
    path: String?,
    @DrawableRes placeHolder: Int = R.drawable.ic_place_holder,
    success: () -> Unit = {},
    error: () -> Unit = {},
    setScale: Boolean = false,
    isReminder: Boolean = false
) {
    if (setScale && this !is CircleImageView)
        this.scaleType = ImageView.ScaleType.FIT_XY


    if (isReminder) {
        setImageResource(R.drawable.ic_place_holder)
        return
    }
    if (path.isNullOrEmpty())
        setImageResource(placeHolder)
    else {
        getRequestBuilder(context, path, placeHolder, success, error)
            .error(getRequestBuilder(context, path, placeHolder, success, error))
            .into(this)
    }
}


private fun getRequestBuilder(
    context: Context, path: String?,
    @DrawableRes placeHolder: Int = R.drawable.ic_place_holder,
    success: () -> Unit = {},
    error: () -> Unit = {}
) = Glide.with(context)
    .load(path)
    .placeholder(
        context.getDrawable(
            placeHolder
        )
    ).thumbnail(0.05f)
    .listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            error()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable>?,
            dataSource: com.bumptech.glide.load.DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            success()
            return false
        }
    })


fun View.visible() {
    this.isVisible = true
}

fun View.hide() {
    this.isVisible = false
}

fun View.greyLine() {
    this.setBackgroundColor(Color.parseColor("#727272"))
}

fun TextView.greyText() {
    this.setTextColor(Color.parseColor("#727272"))
}

fun View.yellowLine() {
    this.setBackgroundColor(Color.parseColor("#F8CD24"))
}

fun TextView.yellowText() {
    this.setTextColor(Color.parseColor("#F8CD24"))
}


/*
fun EditText.updatePasswordVisibility(visibilityImage: ImageView) {
    if (transformationMethod is PasswordTransformationMethod) {
        transformationMethod = null
        visibilityImage.setImageDrawable(context.getDrawableCompat(R.drawable.ic_eye_visible))
    } else {
        transformationMethod = PasswordTransformationMethod()
        visibilityImage.setImageDrawable(context.getDrawableCompat(R.drawable.ic_eye_invisible))
    }

    setSelection(length())
}

fun View.toggleCellVisibility(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
        this.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    } else {
        this.visibility = View.GONE
        this.layoutParams = RecyclerView.LayoutParams(0, 0)
    }
}



fun View.disableView() {
    this.isEnabled = false
}

fun View.enableView() {
    this.isEnabled = true
}

fun RecyclerView.scrollToCenterPosition(position: Int) {
    val rvWidth = this.width
    val itemWidth =
        resources.getDimensionPixelSize(R.dimen.home_category_image_width)
    val offset = (rvWidth - itemWidth) / 2
    (this.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
        position,
        offset
    )
}

fun ShimmerFrameLayout.showShimmerView() {
    this.visibility = View.VISIBLE
    this.startShimmer()
}

fun ShimmerFrameLayout.hideShimmerView() {
    this.visibility = View.GONE
    this.stopShimmer()
}

fun View.setDiscreteScrollViewItemWidth() {
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    val layoutParams = ViewGroup.LayoutParams(
        (width - (30 * context.resources.displayMetrics.density).toInt()),
        this.layoutParams.height
    )
    this.layoutParams = layoutParams
}

fun View.selectAddressType() {
    this.setBackgroundResource(R.drawable.bg_rectangle_rounded_corners_accent_color)
    iv_address_type.setColorFilter(
        context.getColorCompat(R.color.white),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
    tv_address_type.setTextColor(context.getColorCompat(R.color.white))
}

fun View.unSelectAddressType() {
    this.background = null
    iv_address_type.setColorFilter(
        context.getColorCompat(R.color.colorPrimary),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
    tv_address_type.setTextColor(context.getColorCompat(R.color.colorPrimary))
}

fun SwipeLayout.initSwipeDirection(context: Context) {
    if (context.isRtl())
        this.currentDirection = SwipeLayout.RIGHT
    else
        this.currentDirection = SwipeLayout.LEFT
}*/
