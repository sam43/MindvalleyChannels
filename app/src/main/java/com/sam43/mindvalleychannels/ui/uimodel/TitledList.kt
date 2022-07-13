package com.sam43.mindvalleychannels.ui.uimodel

import com.sam43.mindvalleychannels.data.remote.model.common.IconAsset

data class TitledList(
    val title: String,
    val type: String,
    val mediaCount: String,
    val icon: IconAsset?,
    val list: MutableList<Any>
)
