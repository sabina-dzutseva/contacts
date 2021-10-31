package com.gmail.dzutsevasabina.interfaces

import android.widget.Button
import com.gmail.dzutsevasabina.model.Contact

interface ButtonClickListener {
    fun onButtonClick(button: Button, contact: Contact)
}
