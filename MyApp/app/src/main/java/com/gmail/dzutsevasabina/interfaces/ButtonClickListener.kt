package com.gmail.dzutsevasabina.interfaces

import android.widget.Button
import com.gmail.dzutsevasabina.model.DetailedContact

interface ButtonClickListener {
    fun onButtonClick(button: Button, contact: DetailedContact)
}
