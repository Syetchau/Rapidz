package io.rapidz.lib

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import io.rapidz.library.databinding.DialogTwoButtonWithIconBinding

object RapidzPopupUtils {

    fun showTwoButtonDialog(
        context: Context,
        title: CharSequence,
        msg: CharSequence?,
        btnPositiveLabel: CharSequence,
        btnNegativeLabel: CharSequence,
        @ColorRes btnPositiveColorResId: Int?,
        @ColorRes btnPositiveRippleColorResId: Int?,
        isCancelable: Boolean,
        isCanceledOnTouchOutside: Boolean,
        positiveButtonClick: () -> Unit,
        negativeButtonClick: (() -> Unit)? = null
    ): AlertDialog {

        //Inflate the dialog with custom view
        val dialogTwoButtonBinding = DialogTwoButtonWithIconBinding.inflate(LayoutInflater.from(context), null, false)

        //AlertDialogBuilder
        val builder = AlertDialog.Builder(context)
            .setView(dialogTwoButtonBinding.root)
            .setCancelable(isCancelable)

        //show dialog
        val alertDialogTwoButton = builder.create()
        alertDialogTwoButton.setCanceledOnTouchOutside(isCanceledOnTouchOutside)

        dialogTwoButtonBinding.apply {
            tvTitleTwoButtonWithIcon.isVisible = title.isNotEmpty()
            tvMsgTwoButtonWithIcon.isVisible = !msg.isNullOrBlank()

            tvTitleTwoButtonWithIcon.text = title
            tvMsgTwoButtonWithIcon.text = msg

            btnPositiveTwoButtonWithIcon.text = btnPositiveLabel
            btnNegativeTwoButtonWithIcon.text = btnNegativeLabel

            if (btnPositiveColorResId != null) {
                val btnPositiveColor = context.getColor(btnPositiveColorResId)
                btnPositiveTwoButtonWithIcon.backgroundTintList = ColorStateList.valueOf(btnPositiveColor)

            }
            if (btnPositiveRippleColorResId != null) {
                val btnPositiveRippleColor = context.getColor(btnPositiveRippleColorResId)
                btnPositiveTwoButtonWithIcon.rippleColor =
                    ColorStateList.valueOf(btnPositiveRippleColor)
            }

            val positiveBtnClick = View.OnClickListener {
                positiveButtonClick()
                alertDialogTwoButton.dismiss()
            }

            val negativeBtnClick = View.OnClickListener {
                negativeButtonClick?.invoke()
                alertDialogTwoButton.dismiss()
            }

            btnPositiveTwoButtonWithIcon.setOnClickListener(positiveBtnClick)
            btnNegativeTwoButtonWithIcon.setOnClickListener(negativeBtnClick)
        }

        try {
            alertDialogTwoButton.show()
        } catch (e: Throwable) {
            //LogUtils.w(e)
            e.printStackTrace()
        }

        return alertDialogTwoButton
    }
}