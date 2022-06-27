package com.sam43.mindvalleychannels.ui.model

data class TitledList(
    val title: String,
    val type: String,
    val mediaCount: String,
    val icon: String?,
    val cover: String?,
    val list: MutableList<Any>
)
