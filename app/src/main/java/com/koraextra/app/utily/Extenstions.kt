package com.koraextra.app.utily

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.koraextra.app.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.changeLanguage() {
    var locale: Locale? = null
    when (Injector.getPreferenceHelper().language) {
        Constants.Language.ARABIC.value -> {
            locale = Locale("ar")
        }
        Constants.Language.ENGLISH.value -> {
            locale = Locale("en")
        }
    }
    Locale.setDefault(locale)
    val config = this.resources.configuration
    config.setLocale(locale)
    this.createConfigurationContext(config)
    Injector.getApplicationContext().createConfigurationContext(config)
}

fun Context.restartApplication() {
    val intent = Injector.getApplicationContext().packageManager.getLaunchIntentForPackage(
        Injector.getApplicationContext().packageName
    )
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
}

fun Context.saveLanguage(language: Constants.Language) {
    Injector.getPreferenceHelper().language = language.value
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(message: String?, rootView: View) {
    val snackBar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val textView = view.findViewById<View>(R.id.snackbar_text)
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackBar.show()
}

fun Context.snackBarWithAction(
    message: String,
    actionTitle: String,
    rootView: View,
    action: () -> Unit
) {
    val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val textView = view.findViewById<View>(R.id.snackbar_text)
    textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//    snackBar.view.setBackgroundColor(ContextCompat.getColor(rootView.context,android.R.color.white))
//    = ContextCompat.getColor(rootView.context,android.R.color.white)
    snackBar.duration = 10000
    snackBar.setAction(actionTitle) {
        action.invoke()
    }
    snackBar.show()
}

fun Context.getCurrentDate(): String {

    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val currentDate = sdf.format(Date())
    return currentDate
}

fun Context.getDateStringFromString(oldDate: String): String {
    var date = ""
    val oldFormat = "yyyy-MM-dd"
    val newFormat = "dd/MM/yyyy"
    val dateFormat = SimpleDateFormat(oldFormat, Locale("en"))
    val myDate = dateFormat.parse(oldDate)
    val timeFormat = SimpleDateFormat(newFormat, Locale("en"))
    date = timeFormat.format(myDate)
    return date
}

fun Context.getDayName(oldDate: String): String {
    var day = ""
    val oldFormat = "yyyy-MM-dd"
    val newFormat = "EEEE"
    val dateFormat = SimpleDateFormat(oldFormat, Locale("en"))
    val myDate = dateFormat.parse(oldDate)
    val timeFormat = SimpleDateFormat(newFormat, Locale(Injector.getPreferenceHelper().language))
    day = timeFormat.format(myDate)
    return day
}

fun Context.getDateFromString(oldDate: String): Date {
    var date = ""
    val oldFormat = "yyyy-MM-dd"
    val dateFormat = SimpleDateFormat(oldFormat, Locale("en"))
    val myDate = dateFormat.parse(oldDate)
    return myDate
}

fun Context.getTimeFromMills(mills: Long): String {
    val m = mills.toLong()
    val newFormat = "hh:mm a"
    val date = Date(m * 1000)
    val dateFormat = SimpleDateFormat(newFormat, Locale("en"))
    val myDate = dateFormat.format(date)
    return myDate
}

fun Context.getDateFromMills(mills: Long): String {
    val m = mills.toLong()
    val newFormat = "dd/MM/yyyy"
    val date = Date(m * 1000)
    val dateFormat = SimpleDateFormat(newFormat, Locale("en"))
    val myDate = dateFormat.format(date)
    return myDate
}

fun Context.getTimeAgoAsMills(time: Long): Long {
    var time = time
    if (time < 1000000000000L) {
        // if timestamp given in seconds, convert to millis
        time *= 1000
    }
    val now = System.currentTimeMillis()
    return now - time

}
