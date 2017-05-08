package com.wxx.customview.waveview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxx.customview.R;
import com.wxx.customview.util.Util;

/**
 * 作者：Tangren_ on 2017/5/8 0008.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class WaveView extends View {

    private int width = 240;

    private int height = 170;

    private int rectWidth = 200;

    private int rectHeight = 130;

    private int waveWidth;

    private int waveCount;

    private int textColor;

    private String text;

    private int textSize;

    private Paint mPaint;

    private Context mContext;

    private int wave_bg_color = Color.parseColor("#6437B6");

    private Rect rect;

    private Rect textRect;

    private Paint textPaint;

    private Path mPath;

    private static final String TAG = "WaveView";

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveView, 0, R.style.WaveStyle);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.WaveView_wave_bg_color:
                    wave_bg_color = array.getColor(arr, 0);
                    break;
                case R.styleable.WaveView_wave_count:
                    waveCount = array.getInteger(arr, 0);
                    break;
                case R.styleable.WaveView_wave_width:
                    waveWidth = array.getInteger(arr, 0);
                    break;
                case R.styleable.WaveView_wave_text:
                    text = array.getString(arr);
                    break;
                case R.styleable.WaveView_wave_text_color:
                    textColor = array.getColor(arr, 0);
                    break;
                case R.styleable.WaveView_wave_text_size:
                    textSize = array.getDimensionPixelSize(arr, 0);
                    Log.d(TAG, "textSize:" + textSize);
                    break;
            }

        }

        array.recycle();
        mPaint = new Paint();
        mPaint.setColor(wave_bg_color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rect = new Rect();
        mPath = new Path();

        textRect = new Rect();
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

    }

    public void setWaveText(String text) {
        this.text = text;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST://warp_content时,默认View的宽度300,矩形的宽度为其的90%
                width = getPaddingLeft() + getPaddingRight() + Util.dip2px(mContext, 300);
                rectWidth = (int) (width * 0.9);
                break;
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                rectWidth = (int) (width * 0.9);
                break;
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                height = getPaddingBottom() + getPaddingTop() + Util.dip2px(mContext, 200);
                rectHeight = (int) (height * 0.9);
                break;
            case MeasureSpec.EXACTLY:
                height = getPaddingBottom() + getPaddingTop() + specSize;
                rectHeight = (int) (height * 0.9);
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //矩形的位置,在view的中间
        canvas.translate((width - rectWidth) / 2, (height - rectHeight) / 2);
        rect.set(0, 0, rectWidth, rectHeight);
        canvas.drawRect(rect, mPaint);
        float startX = 0;
        float startY = 0;
        //每个波浪的高度
        float mWaveHeight = rectHeight / waveCount;

        //左边波浪
        mPath.moveTo(startX, startY);
        for (int i = 0; i < waveCount; i++) {
            mPath.lineTo(startX - waveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
            mPath.lineTo(startX, startY + mWaveHeight * (i + 1));
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        startX = rectWidth;
        startY = 0;

        //右边波浪
        mPath.moveTo(startX, startY);
        for (int i = 0; i < waveCount; i++) {
            mPath.lineTo(startX + waveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
            mPath.lineTo(startX, startY + mWaveHeight * (i + 1));
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        //文字部分
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        canvas.drawText(text, rectWidth / 2 - textRect.width() / 2, rectHeight / 2 + textRect.height() / 2, textPaint);
    }
}
