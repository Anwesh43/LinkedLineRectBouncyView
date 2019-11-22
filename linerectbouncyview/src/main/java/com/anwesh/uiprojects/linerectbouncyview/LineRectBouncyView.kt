package com.anwesh.uiprojects.linerectbouncyview

/**
 * Created by anweshmishra on 22/11/19.
 */

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val nodes : Int = 5
val lines : Int = 4
val scGap : Float = 0.02f
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val foreColor : Int = Color.parseColor("#1565C0")
val backColor : Int = Color.parseColor("#BDBDBD")
val rFactor : Float = 4f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = 1f - Math.sin(Math.PI / 2 + (Math.PI / 2) * this).toFloat()

fun Canvas.drawLineBouncyRect(i : Int, size : Float, scale : Float, paint : Paint) {
    val sci : Float = scale.divideScale(i, lines)
    val sf : Float = sci.sinify()
    val sc : Float = sci.divideScale(1, 2).cosify()
    val deg : Float = 360f / lines
    val rSize : Float = size / rFactor
    save()
    rotate(deg * i)
    drawLine(0f, 0f, 0f, -size * sf, paint)
    drawRect(RectF(-rSize / 2, -size, rSize / 2, -size + size * sc), paint)
    restore()
}

fun Canvas.drawLinesBouncyRect(size : Float, scale : Float, paint : Paint) {
    for (j in 0..(lines - 1)) {
        drawLineBouncyRect(j, size, scale, paint)
    }
}

fun Canvas.drawLRBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / sizeFactor
    paint.color = foreColor
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    save()
    translate(gap * (i + 1), h / 2)
    drawLinesBouncyRect(size, scale, paint)
    restore()
}