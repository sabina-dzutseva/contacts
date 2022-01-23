package com.gmail.dzutsevasabina.view

import android.content.res.Resources
import android.widget.TextView
import androidx.core.view.isVisible

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun TextView.setTextOrChangeVisibility(text: String) {
    if (text.isEmpty()) {
        this.isVisible = false
    } else {
        this.text = text
    }
}
