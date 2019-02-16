package com.acontinue.continueparacorretores.androidExtensions

import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import java.net.HttpURLConnection
import java.net.URL


fun String?.drawableFromUrl(): Drawable {
    val x: Bitmap

    val connection = URL(this).openConnection() as HttpURLConnection
    connection.connect()
    val input = connection.inputStream

    x = BitmapFactory.decodeStream(input)
    return BitmapDrawable(x)
}