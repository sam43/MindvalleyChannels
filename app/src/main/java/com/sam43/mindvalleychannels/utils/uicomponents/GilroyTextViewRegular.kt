package com.sam43.mindvalleychannels.utils.uicomponents

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class GilroyTextViewRegular : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setTypeface(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setTypeface(context)
    }

    constructor(context: Context) : super(context) {
        setTypeface(context)
    }

    private fun setTypeface(context: Context) {
        typeface = AssetController.getInstance(context)?.typeFaceRegular
    }
}
