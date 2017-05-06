package com.wxx.customview.taiji;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wxx.customview.R;

/**
 * 作者：Tangren_ on 2017/5/4 0004.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class TaijiDiagram extends View {

    //黑色画笔
    private Paint mBalckPaint;

    //白色画笔
    private Paint mWhitePaint;

    //半径
    private int radius;

    private int left_color;

    private int right_color;


    private int taiji_bg;

    private RectF mRectF;

    private int width;

    private int height;

    private static int rad = 30;

    private final long stopTime = 10000;

    private long upTime = 0;

    public TaijiDiagram(Context context) {
        this(context, null);
    }

    public TaijiDiagram(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TaijiDiagram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TaijiDiagram, 0, R.style.TaijiStyle);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.TaijiDiagram_taiji_left_color:
                    left_color = array.getColor(arr, 0);
                    break;
                case R.styleable.TaijiDiagram_taiji_right_color:
                    right_color = array.getColor(arr, 0);
                    break;

                case R.styleable.TaijiDiagram_taiji_radius:
                    radius = array.getInteger(arr, 0);
                    break;
                case R.styleable.TaijiDiagram_taiji_bg:
                    taiji_bg = array.getColor(arr, 0);
                    break;
                default:
                    break;
            }

        }

        array.recycle();
        mBalckPaint = new Paint();
        mBalckPaint.setColor(left_color);
        mBalckPaint.setAntiAlias(true);
        mWhitePaint = new Paint();
        mWhitePaint.setColor(right_color);
        mWhitePaint.setAntiAlias(true);
        mRectF = new RectF(-radius, -radius, radius, radius);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                width = (int) (getPaddingLeft() + getPaddingRight() + mRectF.width());
                break;
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                height = (int) (getPaddingBottom() + getPaddingTop() + mRectF.height());
                break;
            case MeasureSpec.EXACTLY:
                height = getPaddingBottom() + getPaddingTop() + specSize;
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(radius, radius);
        canvas.drawColor(taiji_bg);

        rad -= 3;
        canvas.rotate(rad, 0, 0);

        canvas.drawArc(mRectF, 90, 180, true, mBalckPaint);
        canvas.drawArc(mRectF, -90, 180, true, mWhitePaint);


        canvas.drawCircle(0, -radius / 2, radius / 2, mBalckPaint);
        canvas.drawCircle(0, radius / 2, radius / 2, mWhitePaint);
        canvas.drawCircle(0, -radius / 2, radius / 5, mWhitePaint);
        canvas.drawCircle(0, radius / 2, radius / 5, mBalckPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                post(new Runnable() {
                    @Override
                    public void run() {
//                        long duration = System.currentTimeMillis() - upTime;
//                        if (upTime == stopTime)
//                            return;
//                        else if (duration < stopTime)
                            post(this);

                        invalidate();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                postInvalidate();
                break;
        }
        return true;
    }
}
