package com.sharkaboi.mediahub.common.extensions

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

internal fun <VH : RecyclerView.ViewHolder?, VH2 : RecyclerView.ViewHolder?, T : RecyclerView.Adapter<VH2>> RecyclerView.Adapter<VH>.addFooter(
    adapter: () -> T
): ConcatAdapter {
    return ConcatAdapter(this, adapter())
}

internal fun <VH : RecyclerView.ViewHolder?, VH2 : RecyclerView.ViewHolder?, T : RecyclerView.Adapter<VH2>> RecyclerView.Adapter<VH>.addHeader(
    adapter: () -> RecyclerView.Adapter<RecyclerView.ViewHolder>
): ConcatAdapter {
    return ConcatAdapter(adapter(), this)
}