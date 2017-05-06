package com.wxx.customview.verifcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxx.customview.R;
import com.wxx.customview.util.Util;

/**
 * 作者：Tangren_ on 2017/5/3 0003.
 * 邮箱：wu_tangrn@163.com
 * TODO:自定义验证码
 */


public class VerifCodeView extends View {

    private int textColor;
    private int textSize;
    private int textCodeLengh = 6;
    private String msg;
    private int textBg;
    private Rect mRect;
    private Paint mPaint;

    private int width = 0;

    private int height = 0;

    private static final String TAG = "VerifCodeView";

    public VerifCodeView(Context context) {
        this(context, null);
    }

    public VerifCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerifCodeView, 0, R.style.VerifyStyle);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.VerifCodeView_verify_Text_color:
                    textColor = array.getColor(arr, 0);
                    break;
                case R.styleable.VerifCodeView_verify_text_size:
                    textSize = array.getDimensionPixelSize(arr, 0);
                    break;
                case R.styleable.VerifCodeView_verify_text_bg:
                    textBg = array.getColor(arr, 0);
                    break;
                case R.styleable.VerifCodeView_verify_text_code_length:
                    textCodeLengh = array.getInt(arr, 0);
                    break;
                default:
                    break;
            }
        }
        array.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(textSize);

        mRect = new Rect();
        msg = Util.randomText(textCodeLengh);

        //获得文本的高
        mPaint.getTextBounds(msg, 0, msg.length(), mRect);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = Util.randomText(textCodeLengh);
                postInvalidate();//刷新UI
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int paddingLeft = getPaddingLeft() + 10;
        int paddingRight = getPaddingRight() + 10;
        int paddingBottom = getPaddingBottom() + 10;
        int paddingTop = getPaddingTop() + 10;
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                width = paddingLeft + paddingRight + mRect.width();
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingBottom() + getPaddingTop() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = paddingBottom + paddingTop + mRect.height();
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(textBg);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(textColor);
        canvas.drawText(msg, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);

        Log.d(TAG,"mRect.width="+mRect.width());
        Log.d(TAG,"getWidth="+getWidth());

        // 绘制小圆点
        int[] point;
        for (int i = 0; i < 100; i++) {//画点
            point = Util.getPoint(height, width);
            canvas.drawCircle(point[0], point[1], 1, mPaint);
        }
        int[] line;
        for (int i = 0; i < 10; i++) {//划线
            line = Util.getLine(height, width);
            canvas.drawLine(line[0], line[1], line[2], line[3], mPaint);
        }
    }
}
