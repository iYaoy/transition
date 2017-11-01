package com.iyao.transition

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.transition.Transition
import android.util.AttributeSet
import android.view.View

class RectView constructor(context: Context, attrs: AttributeSet? = null)
    : View(context, attrs) {

    var radius = 0f
        set(value) {
            field = value
            invalidate()
        }
    var color = 0
        set(value) {
            field = value
            mPaint.color = color
            invalidate()
        }

    private val mPaint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRect : RectF
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectView)
        radius = typedArray.getDimension(R.styleable.RectView_rectRadius, 0f)
        color = typedArray.getColor(R.styleable.RectView_rectColor, 0)
        typedArray.recycle()
        mPaint.style = Paint.Style.FILL
        mPaint.color = color
        mRect = RectF()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mRect.left = 0f
        mRect.top = 0f
        mRect.right = w.toFloat()
        mRect.bottom = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mRect, radius, radius, mPaint)
    }
}
