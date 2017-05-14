package com.wxx.customview.circlebgtextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 作者：Tangren on 2017/5/14 17:10
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class CircleBgTextView extends TextView {

    private Paint mpaint;

    public boolean isToday = false;

    private float radius = 0;

    public CircleBgTextView(Context context) {
        this(context, null);
    }

    public CircleBgTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.parseColor("#FF4081"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            if (getWidth() >= getHeight())
                radius = getHeight();
            else radius = getWidth();
            canvas.drawCircle(0, 0, radius/ 2, mpaint);
        }
    }
}
