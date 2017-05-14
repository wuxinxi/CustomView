package com.wxx.customview.calendar;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxx.customview.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：Tangren on 2017/5/14 10:42
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class TRCalendar extends LinearLayout implements View.OnClickListener {

    private ImageView prev;
    private ImageView next;
    private TextView mDate;
    private RecyclerView gridView;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String TAG = "TRCalendar";

    private Calendar calendar = Calendar.getInstance();

    private GridLayoutManager manager;

    public TRCalendar(Context context) {
        this(context, null);
    }

    public TRCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
        renderCalendar();
    }

    private void initControl(Context context) {

        LayoutInflater infalter = LayoutInflater.from(context);
        View view = infalter.inflate(R.layout.calendar_view, this);
        prev = (ImageView) view.findViewById(R.id.prev);
        next = (ImageView) view.findViewById(R.id.next);
        mDate = (TextView) view.findViewById(R.id.mDate);
        gridView = (RecyclerView) view.findViewById(R.id.myGridView);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        manager = new GridLayoutManager(context, 7);
        gridView.setLayoutManager(manager);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prev:
                calendar.add(Calendar.MONTH, -1);
                renderCalendar();
                break;
            case R.id.next:
                calendar.add(Calendar.MONTH, +1);
                renderCalendar();
                break;
            default:
                break;
        }
    }

    private void renderCalendar() {
        mDate.setText(sdf.format(calendar.getTime()));
        ArrayList<Date> cells = new ArrayList<>();
        Calendar cld = (Calendar) calendar.clone();
        cld.set(Calendar.DAY_OF_MONTH, 1);

        //当月之前有多少天在同一行显示
        int preDays = cld.get(Calendar.DAY_OF_WEEK) - 2;
        cld.set(Calendar.DAY_OF_MONTH, -preDays);

        int maxCount = 6 * 7;
        while (cells.size() < maxCount) {
            cells.add(cld.getTime());
            cld.add(Calendar.DAY_OF_MONTH, 1);
        }

        Log.d(TAG, cells.size() + "的个数");
        gridView.setAdapter(new CalendarAdapter(cells, calendar.get(calendar.MONTH)));
    }

}
