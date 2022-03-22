package com.udacity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.animation.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var frame:Rect
    private lateinit var secondframe:Rect



    private var widthSize = 0
    private var heightSize = 0
    private var rightSize = 0
    private var bottomSize = 0
    private var text = ""
    private var radius = 0f
    private var defaultViewColor = ResourcesCompat.getColor(resources,R.color.colorPrimary,null)
    private var defaultLoadingColor = ResourcesCompat.getColor(resources,R.color.colorPrimaryDark,null)
    private var accentColor = ResourcesCompat.getColor(resources,R.color.colorAccent,null)

    private var defaultCircleLoadingColor = 0
    private var secondRectright = 20

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 70.0f
        color = defaultViewColor
        typeface = Typeface.create("", Typeface.NORMAL)
        isAntiAlias = true
    }

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    init {
        context.withStyledAttributes(attrs,R.styleable.LoadingButtonView){
            text = resources.getString(R.string.download)
            defaultViewColor = getColor(R.styleable.LoadingButtonView_ButtonColor,0)
            defaultLoadingColor = getColor(R.styleable.LoadingButtonView_LoadingColor,0)
            defaultCircleLoadingColor = getColor(R.styleable.LoadingButtonView_CircleLoadingColor,0)
            secondRectright = getInt(R.styleable.LoadingButtonView_secondRectRight,20)
        }
    }
    override fun performClick(): Boolean {
        animation()
        invalidate()
        return true
    }

    private fun animation() {
        val animator = ValueAnimator.ofInt(secondRectright,rightSize)
        animator.apply {
            repeatMode = ValueAnimator.REVERSE
            duration = 3000
            addUpdateListener {
                val g = animator.animatedValue
                secondRectright = g as Int
                secondframe = Rect(20,0,secondRectright,140)
                invalidate()
            }

        }
        val circleAnimator = ValueAnimator.ofFloat(radius,30f)
        circleAnimator.apply {
            repeatMode = ValueAnimator.REVERSE
            duration = 3000
            addUpdateListener {
                val g = circleAnimator.animatedValue
                radius = g as Float
            }
        }
        circleAnimator.doOnEnd { radius = 0f  }
        circleAnimator.start()

        animator.doOnStart { text = resources.getString(R.string.process) }
        animator.doOnEnd {
            text = resources.getString(R.string.download)
            secondRectright = 20
            secondframe = Rect(20,0,secondRectright,140)
            invalidate()
        }
        animator.start()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val inset = 20
        bottomSize = w - inset
        rightSize = w - inset
        frame = Rect(20,0,w-inset,h-inset)
        secondframe = Rect(20,0,secondRectright,bottomSize)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRect(canvas)
        drawLoadingRect(canvas)
        drawTextOnButton(canvas)
        drawCircle(canvas)
    }
    private fun drawRect(canvas: Canvas){
        paint.color = defaultViewColor
        canvas.drawRect(frame,paint)
    }
    private fun drawLoadingRect(canvas: Canvas) {
        paint.color = defaultLoadingColor
        canvas.drawRect(secondframe,paint)
    }
    private fun drawTextOnButton(canvas: Canvas){
        paint.color = Color.WHITE
        val first = (height/2+20).toFloat()
        val second = (width/2).toFloat()
        val pointPosition = PointF(first,second)
        canvas.drawText(text,pointPosition.y,pointPosition.x,paint)
    }
    private fun drawCircle(canvas: Canvas){
        paint.color = accentColor
        val first = (height/2+20).toFloat()
        val second = (width/2).toFloat()
        val pointPosition = PointF(first,second)
        canvas.drawCircle(pointPosition.y + 400,pointPosition.x-30,radius,paint)

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}