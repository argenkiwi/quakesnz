package com.androideas.quakesnz.app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BarView extends View {

    private float mValue;

    private Paint mPaint;

    private RectF mBounds;

    private ColorStateList mColor;

    private int mCurrentColor;

    public BarView(Context context) {
        super(context);

        mCurrentColor = Color.GRAY;
        mValue = 1;

        init();
    }

    public BarView(Context context, AttributeSet attrs) {

        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BarView, 0, 0);

        mColor = a.getColorStateList(R.styleable.BarView_color);
        mCurrentColor = mColor != null ? mColor.getDefaultColor() : Color.GRAY;
        mValue = a.getFloat(R.styleable.BarView_value, 1);
        a.recycle();

        init();
    }

    public BarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BarView, 0, 0);

        mColor = a.getColorStateList(R.styleable.BarView_color);
        mCurrentColor = mColor != null ? mColor.getDefaultColor() : Color.GRAY;
        mValue = a.getFloat(R.styleable.BarView_value, 1);

        a.recycle();

        init();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mColor != null && mColor.isStateful())
            updateColor();
    }

    public int getColor() {
        if (mColor != null)
            return mColor.getDefaultColor();
        else
            return mCurrentColor;
    }

    public float getValue() {
        return mValue;
    }

    private void init() {

        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mBounds, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resize(w, h);
    }

    private void resize(int w, int h) {
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        mBounds = new RectF(0, hh * (1 - mValue), ww, hh);
        mBounds.offset(getPaddingLeft(), getPaddingTop());
    }

    public void setColor(ColorStateList color) {
        mColor = color;
        updateColor();
    }

    public void setColor(int color) {
        mColor = ColorStateList.valueOf(color);
        updateColor();
    }

    public void setValue(float value) {
        mValue = value;
        resize(getWidth(), getHeight());
        invalidate();
    }

    private void updateColor() {
        boolean inval = false;

        int color = mColor.getColorForState(getDrawableState(), 0);

        if (color != mCurrentColor) {
            mCurrentColor = color;
            mPaint.setColor(mCurrentColor);
            inval = true;
        }

        if (inval) {
            invalidate();
        }
    }

}