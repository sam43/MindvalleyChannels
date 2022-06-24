package com.sam43.mindvalleychannels.ui.adapters.viewholder

import java.util.*

enum class ViewType(var type: String) {
    SERIES("SERIES"),
    COURSE("COURSE"),
    CATEGORY("CATEGORY")
}
val answers: Array<ViewType> = ViewType.values()
val rand = Random()

fun getRandomType():String {
    val n = rand.nextInt(2)+1
    return answers[n].type
}