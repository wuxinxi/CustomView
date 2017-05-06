package com.wxx.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren_ on 2017/5/5 0005.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class MyGridView extends View {

    private Paint mPaint;

    private Paint textPaint;

    private Paint overPaint;

    private Path mPath;

    private Path overlyPath;

    private Paint.FontMetrics fm;

    private int centerX;

    private int centerY;

    private float maxRadius;

    private int overlayAlpha;//0~255

    private int textSize;

    private int textColor;

    private int lineColor;

    private int lineWidth;

    private int overlyColor;

    private List<Float> overlayFloat = new ArrayList<>();

    private List<String> name = new ArrayList<>();

    private int width;

    private int height;

    private int arryCount;

    private int rad = 0;
    private long stopTime = 5000;
    private long upTime = 0;

    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;


    private final static float RADIUS_SCALE = 0.6f;

    public MyGridView(Context context) {
        this(context, null);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyGridView, 0, R.style.MyGridViewStyle);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.MyGridView_gird_text_color:
                    textColor = array.getColor(arr, 0);
                    break;
                case R.styleable.MyGridView_gird_text_size:
                    textSize = array.getDimensionPixelSize(arr, 0);
                    break;
                case R.styleable.MyGridView_grid_line_color:
                    lineColor = array.getColor(arr, 0);
                    break;
                case R.styleable.MyGridView_grid_line_width:
                    lineWidth = array.getDimensionPixelOffset(arr, 0);
                    break;
                case R.styleable.MyGridView_gird_over_color:
                    overlyColor = array.getColor(arr, 0);
                    break;
                case R.styleable.MyGridView_grid_over_alpha:
                    overlayAlpha = array.getInteger(arr, 0);
                    if (overlayAlpha > 255 || overlayAlpha < 0)
                        overlayAlpha = 100;
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);

        overPaint = new Paint();
        overPaint.setAntiAlias(true);
        overPaint.setStyle(Paint.Style.FILL);
        overPaint.setColor(overlyColor);
        fm = textPaint.getFontMetrics();
        mPath = new Path();
        overlyPath = new Path();

        name.add("SqlServer");
        overlayFloat.add(0.9f);

        name.add("计算机导论");
        overlayFloat.add(0.8f);

        name.add("Web");
        overlayFloat.add(0.7f);

        name.add("jQuery");
        overlayFloat.add(0.9f);

        name.add("Java");
        overlayFloat.add(0.6f);

        name.add("Html5");
        overlayFloat.add(0.9f);

        name.add("Android");
        overlayFloat.add(1.0f);

        name.add("Js");
        overlayFloat.add(0.8f);

        arryCount = name.size();
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
                        long duration = System.currentTimeMillis() - upTime;
                        if (upTime == stopTime) return;
                        else if (duration < stopTime) {
                            post(this);
                        }

                        invalidate();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
//                if (x1 - x2 > 50) {//向左滑动
//                    rad += 3;
//                } else if (x2 - x1 > 50)
//                    rad -= 3;
//                else if (y2-y1>50) {
//                    rad -= 3;
//                } else if (y1-y2>50) {
//                    rad += 3;
//                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST://一般为warp_content
                width = getPaddingLeft() + getPaddingRight() + getDefaultWidth();
                break;
            case MeasureSpec.EXACTLY://明确指定大小的，或者最大
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                height = getPaddingBottom() + getPaddingTop() + getDefaultWidth();
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

        maxRadius = Math.min(width, height) / 2 * RADIUS_SCALE;
        centerX = width / 2;
        centerY = height / 2;
        canvas.rotate(rad, centerX, centerY);
        drawView(canvas);
        drawText(canvas);
        drawaOverly(canvas);

        rad += 3;

    }

    private void drawaOverly(Canvas canvas) {
        int angle = 360 / arryCount;
        for (int i = 0; i < arryCount; i++) {
            float x = (float) (centerX + maxRadius * Math.cos(Math.toRadians(angle * i)) * overlayFloat.get(i));
            float y = (float) (centerY + maxRadius * Math.sin(Math.toRadians(angle * i)) * overlayFloat.get(i));
            if (i == 0) {
                overlyPath.moveTo(x, centerY);
            } else {
                overlyPath.lineTo(x, y);
            }

            canvas.drawCircle(x, y, 5, overPaint);
        }

        overlyPath.close();
        overPaint.setAlpha(overlayAlpha);
        canvas.drawPath(overlyPath, overPaint);
    }

    private void drawText(Canvas canvas) {

        //文字的高度  baseline=0  所以 descent为正数  ascent为负数
        float textHeight = fm.descent - fm.ascent;
        int angle = 360 / arryCount;
        for (int i = 0; i < arryCount; i++) {
            float textWidth = textPaint.measureText(name.get(i));
            float x = (float) (centerX + (maxRadius + textHeight / 2) * Math.cos(Math.toRadians(angle * i)));
            float y = (float) (centerY + (maxRadius + textHeight / 2) * Math.sin(Math.toRadians(angle * i)));
            if (angle * i == 90 || angle * i == 270) {
                canvas.drawText(name.get(i), x - textWidth / 2, y + 8, textPaint);
            } else if (angle * i > 90 && angle * i < 270) {
                canvas.drawText(name.get(i), x - textWidth, y + 8, textPaint);
            } else if (angle * i > 45 && angle * i < 90) {
                canvas.drawText(name.get(i), x - textWidth / 2, y + 20, textPaint);
            } else {
                canvas.drawText(name.get(i), x, y, textPaint);
            }

        }
    }

    private void drawView(Canvas canvas) {
        int radius = 0;
        int angle = 360 / arryCount;
        for (int i = 0; i < arryCount; i++) {
            mPath.reset();
            radius = (int) ((maxRadius / arryCount) * (i + 1));
            for (int j = 0; j < arryCount + 1; j++) {
                if (j == 0) {
                    mPath.moveTo(centerX + radius, centerY);
                } else {
                    float x = (float) (centerX + radius * Math.cos(Math.toRadians(angle * j)));
                    float y = (float) (centerY + radius * Math.sin(Math.toRadians(angle * j)));
                    mPath.lineTo(x, y);
                }
            }

            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }


        //绘制轴线
        for (int i = 0; i < arryCount; i++) {
            mPath.reset();
            mPath.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(Math.toRadians(angle * i)));
            float y = (float) (centerY + radius * Math.sin(Math.toRadians(angle * i)));
            mPath.lineTo(x, y);
            canvas.drawPath(mPath, mPaint);
        }
    }

    public void setTextName(List<String> name) {
        this.name = name;
        arryCount = name.size();
    }

    public void setOverlayFloat(List<Float> overlayFloat) {
        this.overlayFloat = overlayFloat;
    }

    /**
     * 获得layout尺寸
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }


    /**
     * dp转px
     *
     * @param context
     * @param value
     * @return
     */
    private static int dip2px(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param value
     * @return
     */
    private static int px2dip(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value / scale + 0.5f);
    }

}
