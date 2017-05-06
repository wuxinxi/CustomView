package com.wxx.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Tangren_ on 2017/5/5 0005.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class PieChatView extends View {

    private Paint mPaint;

    private Paint mPaint_2;
    private Paint mPaint_3;

    private Paint textPaint;
    private Rect textRect;

    private int textColor = Color.WHITE;

    private int color_1 = Color.GREEN;
    private int color_2 = Color.RED;
    private int color_3 = Color.BLUE;

    private String str_1 = "180%";
    private String str_2 = "90%";
    private String str_3 = "90";

    private int width = 350;
    private int height = 350;

    private RectF rectF;

    public PieChatView(Context context) {
        this(context, null);
    }

    public PieChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint_2 = new Paint();
        mPaint_3 = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color_1);
        rectF = new RectF(-width / 2, -width / 2, width / 2, width / 2);
        mPaint_2.setAntiAlias(true);
        mPaint_2.setColor(color_2);
        mPaint_3.setAntiAlias(true);
        mPaint_3.setColor(color_3);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);

        textRect = new Rect();
        textPaint.getTextBounds(str_1, 0, str_1.length(), textRect);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //定位到中心点，即圆心
        canvas.translate(width / 2, width / 2);
        canvas.drawCircle(0, 0, width / 2, mPaint);
        canvas.drawArc(rectF, 0, 90, true, mPaint_2);

        canvas.drawArc(rectF, 90, 90, true, mPaint_3);
    }

}
