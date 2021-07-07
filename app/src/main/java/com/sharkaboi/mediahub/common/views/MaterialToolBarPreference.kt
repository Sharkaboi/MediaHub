package com.sharkaboi.mediahub.common.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder

class MaterialToolBarPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : Preference(context, attrs, defStyleAttr) {

    private var iconView: ImageView? = null
    private var listener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        iconView = holder.findViewById(android.R.id.icon) as ImageView
        iconView?.let {
            it.setPadding(48, 0, 0, 0)
            it.setOnClickListener {
                listener?.invoke()
            }
        }
    }

    override fun onDetached() {
        iconView = null
        listener = null
        super.onDetached()
    }

    fun setNavigationIconListener(action: () -> Unit) {
        listener = action
        iconView?.let {
            it.setOnClickListener {
                action()
            }
        }
    }
}
