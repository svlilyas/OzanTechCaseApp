package com.ozantech.ozantechcaseapp.core.database.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.local.OrderType
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(context: Context) {
    var prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "AppSharedPref"
        private const val FILTER_TYPE = "filter_type"
    }

    var filterType: String
        get() = get(FILTER_TYPE, OrderType.Price.type) ?: OrderType.Price.type
        set(value) {
            set(FILTER_TYPE, value)
        }

    fun clear() {
        prefs.edit()?.clear()?.apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun set(key: String, value: Any?) =
        when (value) {
            is String? -> prefs.edit { it.putString(key, value) }
            is Int -> prefs.edit { it.putInt(key, value) }
            is Boolean -> prefs.edit { it.putBoolean(key, value) }
            is Float -> prefs.edit { it.putFloat(key, value) }
            is Long -> prefs.edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }

    inline operator fun <reified T : Any> get(
        key: String,
        defaultValue: T? = null
    ): T? =
        when (T::class) {
            String::class -> prefs.getString(
                key,
                defaultValue as String? ?: String.empty
            ) as T?

            Int::class -> prefs.getInt(key, defaultValue as? Int ?: -1) as T
            Boolean::class -> prefs.getBoolean(key, defaultValue as? Boolean ?: false) as T
            Float::class -> prefs.getFloat(key, defaultValue as? Float ?: -1f) as T
            Long::class -> prefs.getLong(key, defaultValue as? Long ?: -1) as T
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
}