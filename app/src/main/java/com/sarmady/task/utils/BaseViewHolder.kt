package com.sarmady.task.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sarmady.task.data.models.PhotoModel

abstract class BaseViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}
