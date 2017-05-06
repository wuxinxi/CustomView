package com.wxx.customview.paopaochuang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxx.customview.R;

/**
 * 作者：Tangren_ on 2017/5/6 0006.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class PaoPao extends View {

    private Paint paint;

    private Paint textPaint;

    private Rect rect;

    private RectF rectF;

    private String str ;

    private int paopaoColor;

    private int textColor;

    private int textSize = 16;

    private int width = 0;

    private int height = 0;

    private Path path;

    private static final String TAG = "PaoPao";

    public PaoPao(Context context) {
        this(context, null);
    }

    public PaoPao(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaoPao(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PaoPao, 0, R.style.MypaoPaoStyle);
        int size = array.getIndexCount();
        for (int i = 0; i < size; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.PaoPao_paopao_color:
                    paopaoColor = array.getColor(arr, 0);
                    break;
                case R.styleable.PaoPao_paopao_text:
                    str = array.getString(arr);
                    break;
                case R.styleable.PaoPao_paopao_text_color:
                    textColor = array.getColor(arr, 0);
                    break;
                case R.styleable.PaoPao_paopao_text_size:
                    textSize = array.getDimensionPixelSize(arr, 0);
                    break;
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(paopaoColor);
        path = new Path();

        Log.d("TAG----", str);

        rect = new Rect();
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height + 30);
    }

    public void setString(String str) {
        this.str = str;
    }


    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        rectF = new RectF(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rectF != null) {

            canvas.drawRoundRect(rectF, 10, 10, paint);
            textPaint.getTextBounds(str, 0, str.length(), rect);
            canvas.drawText(str, width / 2 - rect.width() / 2, height / 2 + rect.height() / 2, textPaint);

            Log.d(TAG, "rect.width()=" + rect.width());
            Log.d(TAG, "getWidth()=" + getWidth());
            Log.d(TAG, "width=" + width);


            paint.setColor(paopaoColor);
            path.moveTo(width / 2 - 30, height);
            path.lineTo(width / 2, height + 30);
            path.lineTo(width / 2 + 30, height);
            path.close();
            canvas.drawPath(path, paint);
        } else {
            Log.e(TAG, "View的宽高不能为空");
        }
    }
}
