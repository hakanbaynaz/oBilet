package com.example.obiletcasestudy.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import com.example.obiletcasestudy.databinding.ErrorDialogLayoutBinding
import com.example.obiletcasestudy.databinding.SimpleErrorDialogLayoutBinding

object AppDialog {

    fun showErrorDialog(
        context: Context,
        tryAgainClickListener: OnClickListener,
        dismissListener: OnClickListener? = null,
        message: String? = null
    ) {
        val viewBinding = ErrorDialogLayoutBinding.inflate(LayoutInflater.from(context))
        val alertDialog =
            AlertDialog.Builder(context).setView(viewBinding.root).setCancelable(false).create()
        viewBinding.buttonCancel.setOnClickListener {
            dismissListener?.onClick(it)
            alertDialog.cancel()
        }
        viewBinding.buttonRetry.setOnClickListener {
            tryAgainClickListener.onClick(it)
            alertDialog.cancel()
        }
        message?.let {
            viewBinding.textViewContent.text = it
        }
        alertDialog.show()
    }

    fun showSimpleErrorDialog(
        context: Context,
        message: String? = null
    ) {
        val viewBinding = SimpleErrorDialogLayoutBinding.inflate(LayoutInflater.from(context))
        val alertDialog =
            AlertDialog.Builder(context).setView(viewBinding.root).setCancelable(false).create()
        viewBinding.buttonClose.setOnClickListener {
            alertDialog.cancel()
        }
        message?.let {
            viewBinding.textViewContent.text = it
        }
        alertDialog.show()
    }


}