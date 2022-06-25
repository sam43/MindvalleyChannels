package com.sam43.mindvalleychannels.data.remote

import android.content.Context
import android.widget.Toast

object ErrorEventHandler {

    fun Context.whenFailedConnection(event: ResponseEvent.ConnectionFailure) {

    }

    fun Context.whenLoading(event: ResponseEvent.Loading) {

    }

    fun Context.whenFailed(event: ResponseEvent.Failure) {

    }

    fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}