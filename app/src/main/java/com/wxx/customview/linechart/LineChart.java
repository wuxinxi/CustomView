package com.wxx.customview.linechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxx.customview.util.Util;

import java.util.HashMap;

/**
 * 作者：Tangren_ on 2017/5/8 0008.
 * 邮箱：wu_tangren@163.com
 * TODO:自定义模拟的折线图（无实际意义,完全是为了练习自定义View）
 */


public class LineChart extends View {

    private String yItem[] = {"10K", "20K", "30K", "40K", "50K", "60K", "70K", ""};
    private String xItem[] = {"", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private HashMap<Integer, Integer> map;
    private int yColor = Color.parseColor("#0EB4CE");
    private int xColor = Color.parseColor("#0EB4CE");
    private int lineColor = Color.parseColor("#0EB4CE");
    private int pointColor = Color.parseColor("#0EB4CE");
    private int width = 500, height = 500;
    private Paint mPaint;
    private int pointRadius = 10;
    private int textSize = 16;
    private float strokeWidth = 6f;
    private static final String TAG = "LineChart";

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(Util.dip2px(context, 11));
        mPaint.setColor(Color.BLACK);

        map = new HashMap<>();
        for (int i = 0; i < xItem.length; i++) {
            int point = (int) (Math.random() * 5);
            Log.d(TAG, "参数：" + point);
            map.put(i, point);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        //首先画x坐标轴
        float startX = 0;
        float startY = height;
        //坐标间距
        int padding = width / xItem.length;
        //存放x轴坐标位置
        int xPoints[] = new int[xItem.length];
        Log.d(TAG, "padding:" + padding);
        for (int i = 0; i < xItem.length; i++) {
            canvas.drawText(xItem[i], 0, xItem[i].length(), padding * i, startY, mPaint);
            xPoints[i] = (int) (padding * i + mPaint.measureText(xItem[i]) / 2);
        }


        //Y坐标轴
        startX = 0;
        startY = 0;
        int yPoints[] = new int[yItem.length];
        padding = height / yItem.length;
        startY = padding;
        //测量Y轴文字的高度 用来画第一个数
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        int yItemHeight = (int) Math.ceil(fm.descent - fm.ascent);
        for (int i = 0; i < yItem.length; i++) {
            Log.d(TAG, "yItem[i]=" + yItem[i]);
            canvas.drawText(yItem[i], 0, yItem[i].length(), startX, startY, mPaint);
            startY = startY + padding;
            yPoints[i] = (int) startY + yItemHeight;
        }

        mPaint.setColor(Color.RED);
        //画点
        for (int i = 1; i < yItem.length; i++) {
            canvas.drawCircle(xPoints[i], yPoints[map.get(i)], pointRadius, mPaint);
            if (i > 1) {
                canvas.drawLine(xPoints[i - 1], yPoints[map.get(i - 1)], xPoints[i], yPoints[map.get(i)], mPaint);
            }
        }

    }
}
