
package id.taufikibrahim.themoviedb_visiprimanusantara.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: String): String {
        if (date.isNotEmpty()) {
            try {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                return formatter.format(parser.parse(date))
            } catch (e: Exception) {
                val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                return formatter.format(parser.parse(date))
                e.printStackTrace()
            }
        }
        return "-"
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: String, format: String): String {
        if (date.isNotEmpty()) {
            try {
                val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                return formatter.format(parser.parse(date))
            } catch (e: Exception) {
                val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                return formatter.format(parser.parse(date))
                e.printStackTrace()
            }
        }
        return "-"
    }
}