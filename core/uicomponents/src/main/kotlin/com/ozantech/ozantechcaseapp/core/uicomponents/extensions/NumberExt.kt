package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ozantech.ozantechcaseapp.core.uicomponents.R
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.TextViewExt.changeTextColor
import java.text.DecimalFormat
import kotlin.math.absoluteValue

object NumberExt {
    @BindingAdapter(
        "android:decimalFormat"
    )
    @JvmStatic
    fun TextView.decimalFormat(
        numberString: String? = null,
    ) {
        text = try {
            val number = numberString?.toDouble() ?: 0.0

            val formattedNumber = DecimalFormat("#,##0.00").format(number)

            context.getString(R.string.dollar_price, formattedNumber)
        } catch (e: Exception) {
            context.getString(R.string.dollar_price, numberString)
        }
    }

    @BindingAdapter(
        "android:btcPrice"
    )
    @JvmStatic
    fun TextView.btcPrice(
        numberString: String? = null
    ) {
        text = try {
            val number = numberString?.toDouble() ?: 0.0

            val formattedNumber = DecimalFormat("#,##0.00").format(number)

            context.getString(R.string.btc_price, formattedNumber)
        } catch (e: Exception) {
            context.getString(R.string.btc_price, numberString)
        }
    }

    @BindingAdapter(
        "android:showPercent"
    )
    @JvmStatic
    fun TextView.showPercent(
        numberString: String? = null
    ) {
        text = try {
            val number = numberString?.toDouble() ?: 0.0
            when {
                number > 0.0 -> {
                    changeTextColor(R.color.light_green)
                }

                number == 0.0 -> {
                    changeTextColor(R.color.white_100)
                }

                number < 0.0 -> {
                    changeTextColor(R.color.light_red)
                }
            }
            context.getString(R.string.daily_percent, number.absoluteValue.toString())
        } catch (e: Exception) {
            numberString
        }
    }
}
