package io.rapidz.library

import android.content.Context
import android.util.Patterns
import androidx.core.app.LocaleManagerCompat
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

class RapidzUtils {

    companion object {
        const val DEFAULT_FIAT_FORMAT = "#,##0.00"
        private const val DEFAULT_CRYPTO_FORMAT = "#,##0.########"

        private const val FORMAT_PW_LOWER_CASE = "[a-z]"
        private const val FORMAT_PW_UPPER_CASE = "[A-Z]"
        private const val FORMAT_PW_A_DIGIT = "\\d"  //"\\d"
        private const val FORMAT_PW_A_SPECIAL_CHAR = "[@$!%*#?&]"
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * @param fractionDigits : the number of decimal places
     * E.g : 3 decimal places (3.142)
     * @param isExtraZerosMandatory : if false, then it won't show meaningless zeros
     * E.g : 1.020 -> 1.02
     */
    fun getCurrencyFormat(fractionDigits: Int, isExtraZerosMandatory: Boolean = false): DecimalFormat {
        val formatStr = getCurrencyFormatString(fractionDigits, isExtraZerosMandatory)
        return DecimalFormat(formatStr).apply {
            roundingMode = RoundingMode.FLOOR
        }
    }

    fun getCurrencyFormatString(fractionDigits: Int, isExtraZerosMandatory: Boolean = false): String {
        var formatStr = "#,##0"
        if(fractionDigits > 0) {
            formatStr += "."
            val s = if(fractionDigits <= 2 || isExtraZerosMandatory) "0" else "#"
            repeat(fractionDigits) {
                formatStr += s
            }
        }
        return formatStr
    }

    private fun amtFormatWithThousandSeperator(
        context: Context,
        amt: BigDecimal,
        format: String,
        isNoDecimalForZero: Boolean = true
    ): String {
        // Default locale will be override during setup Phrase
        // LocaleManagerCompat able to retrieve current user phone language
        val locale = LocaleManagerCompat.getSystemLocales(context)[0]!!
        val formatter: DecimalFormat = NumberFormat.getInstance(locale) as DecimalFormat
        formatter.applyPattern(format)
        formatter.roundingMode = RoundingMode.FLOOR
        return if(isNoDecimalForZero && amt.compareTo(BigDecimal(0)) == 0) "0" else formatter.format(amt)
    }

}