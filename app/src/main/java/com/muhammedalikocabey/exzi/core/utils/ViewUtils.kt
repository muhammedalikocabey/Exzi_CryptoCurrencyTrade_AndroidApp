package com.muhammedalikocabey.exzi.core.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isNetworkAvailable(context: Context?): Boolean {
    val connectivityManger = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return run {
        val activeNetworkInfo = connectivityManger.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun openNetworkSettings(context: Context) {
    try {
        val i = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(i)
    } catch (e: Exception) {
        val i = Intent(Settings.ACTION_SETTINGS).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(i)
    }
}
