package com.sharkaboi.mediahub.common.extensions

import android.view.View
import android.view.animation.Animation
import com.google.android.material.snackbar.Snackbar

internal fun View.startAnim(animation: Animation, onEnd: () -> Unit = {}) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    startAnimation(animation)
}

internal fun View.shortSnackBar(
    message: String,
    action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}
