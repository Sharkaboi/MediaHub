package com.sharkaboi.mediahub.common.views

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.sharkaboi.mediahub.R

/**
 * Progress view with dimmed background
 * that shows only if hide isn't called within set time
 * and hides only if set delay is over
 */
class CustomProgressScreen : FrameLayout {
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.custom_progress, this, true)
    }

    private var startTime: Long = -1
    private var mPostedHide: Boolean = false
    private var mPostedShow: Boolean = false
    private var isDismissed: Boolean = false
    var isShowing: Boolean
        get() = isVisible
        set(value) {
            if (value) show() else hide()
        }

    private val mDelayedHide = Runnable {
        mPostedHide = false
        startTime = -1
        visibility = GONE
    }

    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!isDismissed) {
            startTime = System.currentTimeMillis()
            visibility = VISIBLE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(mDelayedHide)
        removeCallbacks(mDelayedShow)
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    @Synchronized
    fun hide() {
        isDismissed = true
        removeCallbacks(mDelayedShow)
        mPostedShow = false
        val diff: Long = System.currentTimeMillis() - startTime
        if (diff >= DELAY || startTime == -1L) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            visibility = GONE
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, DELAY - diff)
                mPostedHide = true
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    @Synchronized
    fun show() {
        // Reset the start time.
        startTime = -1
        isDismissed = false
        removeCallbacks(mDelayedHide)
        mPostedHide = false
        if (!mPostedShow) {
            postDelayed(mDelayedShow, DELAY.toLong())
            mPostedShow = true
        }
    }

    companion object {
        private const val DELAY = 500
    }
}
