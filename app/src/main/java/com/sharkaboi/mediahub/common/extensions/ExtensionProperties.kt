package com.sharkaboi.mediahub.common.extensions

import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.progressindicator.CircularProgressIndicator

inline var String.Companion.emptyString: String
    get() = ""
    private set(_) {}
