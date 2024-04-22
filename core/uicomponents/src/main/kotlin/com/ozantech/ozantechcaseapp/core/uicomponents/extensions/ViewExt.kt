package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewExt {
    fun View.hideKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.showKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun View.visible(isVisible: Boolean = true) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.gone(isGone: Boolean = true) {
        visibility = if (isGone) View.GONE else View.VISIBLE
    }

    fun View.invisible(isInvisible: Boolean = true) {
        visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
    }

    fun View.setOnDebouncedClickListener(action: () -> Unit) {
        val actionDebouncer = ActionDebouncer(action)

        // This is the only place in the project where we should actually use setOnClickListener
        setOnClickListener {
            actionDebouncer.notifyAction()
        }
    }

    fun View.removeOnDebouncedClickListener() {
        setOnClickListener(null)
        isClickable = false
    }

    private class ActionDebouncer(private val action: () -> Unit) {
        companion object {
            const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
        }

        private var lastActionTime = 0L

        fun notifyAction() {
            val now = SystemClock.elapsedRealtime()

            val millisecondsPassed = now - lastActionTime
            val actionAllowed = millisecondsPassed > DEBOUNCE_INTERVAL_MILLISECONDS
            lastActionTime = now

            if (actionAllowed) {
                action.invoke()
            }
        }
    }
}
