package com.sam43.mindvalleychannels.utils.uicomponents

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.sam43.mindvalleychannels.R

object AssetController {
    private var assetController: AssetController? = null

    var typeFaceBold: Typeface? = null
    var typeFaceMedium: Typeface? = null
    var typeFaceRegular: Typeface? = null


    fun getInstance(context: Context?): AssetController? {
        if (assetController == null) {
            assetController = AssetController
            assetController?.initSettings(context)
            return assetController
        }
        return assetController
    }

    private fun initSettings(context: Context?) {
        typeFaceRegular = ResourcesCompat.getFont(context!!, R.font.gilroy_regular)
        typeFaceMedium = ResourcesCompat.getFont(context, R.font.gilroy_medium)
        typeFaceBold = ResourcesCompat.getFont(context, R.font.gilroy_extra_bold)
    }
}
