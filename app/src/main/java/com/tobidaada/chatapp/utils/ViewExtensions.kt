package com.tobidaada.chatapp.utils

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun disableViews(views: Array<View>) {
    for (view in views) {
        view.disable()
    }
}

fun enableViews(views: Array<View>) {
    for (view in views) {
        view.enable()
    }
}

