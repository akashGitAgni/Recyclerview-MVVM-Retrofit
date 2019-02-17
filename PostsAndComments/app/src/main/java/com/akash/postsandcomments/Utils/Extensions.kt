package com.akash.postsandcomments.Utils

import android.content.Context
import android.widget.Toast

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this, duration).apply { show() }
}
