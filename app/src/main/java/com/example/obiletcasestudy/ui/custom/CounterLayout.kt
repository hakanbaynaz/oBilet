package com.example.obiletcasestudy.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.databinding.CounterLayoutBinding

class CounterLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val mViewBinding = CounterLayoutBinding.inflate(LayoutInflater.from(context), this)
    private val mActiveTintList = ColorStateList.valueOf(context.getColor(R.color.indigo_500))
    private val mPassiveTintList = ColorStateList.valueOf(context.getColor(R.color.textColor))

    init {
        orientation = HORIZONTAL
        with(mViewBinding) {
            imageButtonRemove.imageTintList = mPassiveTintList
            textView.text = "0"
            imageButtonAdd.setOnClickListener {
                var value = textView.text.toString().toInt()
                value++
                textView.text = value.toString()
                if (value == 1) {
                    imageButtonRemove.imageTintList = mActiveTintList
                }
            }
            imageButtonRemove.setOnClickListener {
                var value = textView.text.toString().toInt()
                if (value > 0) {
                    value--
                    textView.text = value.toString()
                }
                if (value == 0) {
                    imageButtonRemove.imageTintList = mPassiveTintList
                }
            }
        }
    }

    /**
     * Returns current value of counter
     */
    fun getValue() = mViewBinding.textView.text.toString().toInt()

    fun setValue(value: Int) {
        mViewBinding.textView.text = value.toString()
        if (value > 0) {
            mViewBinding.imageButtonRemove.imageTintList = mActiveTintList
        } else {
            mViewBinding.imageButtonRemove.imageTintList = mPassiveTintList
        }
    }
}