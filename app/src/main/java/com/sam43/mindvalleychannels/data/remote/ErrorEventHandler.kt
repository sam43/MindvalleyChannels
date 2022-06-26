package com.sam43.mindvalleychannels.data.remote

import android.content.Context
import android.widget.Toast

object ErrorEventHandler {

    fun Context.whenFailedConnection(event: EventState.ConnectionFailure) {

    }

    fun Context.whenLoading(event: EventState.Loading) {

    }

    fun Context.whenFailed(event: EventState.Failure) {

    }

    fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}