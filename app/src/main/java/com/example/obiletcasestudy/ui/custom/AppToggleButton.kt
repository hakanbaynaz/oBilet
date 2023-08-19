package com.example.obiletcasestudy.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.databinding.ToggleButtonLayoutBinding

class AppToggleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {


    private var mOnButtonCheckedListener: ((Int) -> Unit)? = null

    private var mLastSelectedButtonId = 0

    private val mViewBinding = ToggleButtonLayoutBinding.inflate(LayoutInflater.from(context), this)
    private val mColorPrimary = context.getColor(R.color.white)
    private val mColorSecondary = context.getColor(R.color.textColor)
    private val mSelectedBackgroundColor = context.getColor(R.color.indigo_700)

    init {
        isSaveEnabled = true
        mViewBinding.buttonBus.setOnClickListener {
            if (mLastSelectedButtonId != it.id) {
                checkButton(it.id)
            }
        }
        mViewBinding.buttonFlight.setOnClickListener {
            if (mLastSelectedButtonId != it.id) {
                checkButton(it.id)
            }
        }
    }

    private fun checkButton(id: Int) {
        mLastSelectedButtonId = id
        setStyle(id)
        mOnButtonCheckedListener?.invoke(id)
    }

    private fun setStyle(id: Int) {
        if (id == mViewBinding.buttonBus.id) {
            mViewBinding.buttonBus.setTextColor(mColorPrimary)
            mViewBinding.buttonBus.iconTint = ColorStateList.valueOf(mColorPrimary)
            mViewBinding.buttonBus.setBackgroundColor(mSelectedBackgroundColor)

            mViewBinding.buttonFlight.setTextColor(mColorSecondary)
            mViewBinding.buttonFlight.iconTint = ColorStateList.valueOf(mColorSecondary)
            mViewBinding.buttonFlight.setBackgroundColor(mColorPrimary)
        } else {
            mViewBinding.buttonBus.setTextColor(mColorSecondary)
            mViewBinding.buttonBus.iconTint = ColorStateList.valueOf(mColorSecondary)
            mViewBinding.buttonBus.setBackgroundColor(mColorPrimary)

            mViewBinding.buttonFlight.setTextColor(mColorPrimary)
            mViewBinding.buttonFlight.iconTint = ColorStateList.valueOf(mColorPrimary)
            mViewBinding.buttonFlight.setBackgroundColor(mSelectedBackgroundColor)
        }
    }

    /**
     * It returns id of button which is checked
     */
    fun setOnButtonCheckedListener(listener: ((Int) -> Unit)) {
        mOnButtonCheckedListener = listener
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val myState = SavedState(superState)
        myState.lastSelectedButtonId = this.mLastSelectedButtonId
        return myState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        this.mLastSelectedButtonId = savedState.lastSelectedButtonId
        checkButton(this.mLastSelectedButtonId)
    }

    private class SavedState : BaseSavedState {
        var lastSelectedButtonId = 1

        constructor(superState: Parcelable?) : super(superState) {}
        private constructor(`in`: Parcel) : super(`in`) {
            lastSelectedButtonId = `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(lastSelectedButtonId)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}