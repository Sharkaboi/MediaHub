package com.sharkaboi.mediahub.common.extensions

import android.view.View
import android.view.animation.Animation
import com.google.android.material.snackbar.Snackbar

internal fun View.startAnim(animation: Animation, onEnd: () -> Unit = {}) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            //onStart()
        }

        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) {
            //onRepeat()
        }

    })
    startAnimation(animation)
}


internal fun View.shortSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.longSnackBar(
    message: String, action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun View.indefiniteSnackBar(
    message: String,
    action: (Snackbar.() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    action?.let { snackbar.it() }
    snackbar.show()
}

internal fun Snackbar.action(message: String, action: (View) -> Unit) {
    this.setAction(message, action)
}