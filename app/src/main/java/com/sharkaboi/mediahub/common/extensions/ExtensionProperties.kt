package com.sharkaboi.mediahub.common.extensions

import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar

inline var String.Companion.emptyString: String
    get() = ""
    private set(_) {}

inline var ContentLoadingProgressBar.isShowing: Boolean
    get() = isVisible
    set(value) {
        if (value) show() else hide()
    }
