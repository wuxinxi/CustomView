package com.wxx.customview.fengche;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wxx.customview.R;

/**
 * 作者：Tangren_ on 2017/5/5 0005.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class RotationView extends View {

    private Bitmap bitmap;

    private int rad = 30;

    private int width;

    private int height;

    private Paint mPaint;

    private Matrix matrix;

    //手指抬起的时间
    private long upTime = 0;

    //抬起手指继续转动5S
    private final long stopTime = 5000;

    public RotationView(Context context) {
        this(context, null);
    }

    public RotationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_fengche);
        width = this.bitmap.getWidth();
        height = this.bitmap.getHeight();
        postInvalidate();
        mPaint = new Paint();
        matrix = new Matrix();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rad -= 3;
        canvas.rotate(rad, width / 2, height / 2);
        canvas.drawBitmap(bitmap, matrix, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                post(new Runnable() {
                    @Override
                    public void run() {
                        long duration = System.currentTimeMillis()-upTime;
                        if (upTime == stopTime)
                            return;
                        else if (duration < stopTime) {
                            post(this);
                        }

                        //在非UI线程更新
                        invalidate();
                    }
                });
                break;
        }
        return true;
    }
}
