package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ContextExt.getColorRes
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ContextExt.getDrawableRes
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ContextExt.toastMessage

object FragmentExt {
    fun Fragment.toastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) =
        requireContext().toastMessage(message = message, duration = duration)

    fun Fragment.toastMessage(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) =
        requireContext().toastMessage(message = message, duration = duration)

    fun Fragment.getDrawable(@DrawableRes drawableRes: Int) =
        requireContext().getDrawableRes(drawableRes = drawableRes)

    fun Fragment.getColor(@ColorRes colorRes: Int) =
        requireContext().getColorRes(colorRes = colorRes)
}