package com.wxx.customview.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxx.customview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：Tangren on 2017/5/14 10:42
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class TRCalendar extends LinearLayout implements View.OnClickListener, OnItemClick, OnLongItemClick {

    private ImageView prev;
    private ImageView next;
    private TextView mDate;
    private RecyclerView gridView;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String TAG = "TRCalendar";

    private Calendar calendar = Calendar.getInstance();

    private GridLayoutManager manager;

    private CalendarAdapter mAdapter;

    private CalendarListener listener;

    private int currentPostion = -1;

    private List<String> signList = new ArrayList<>();

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
        mAdapter = new CalendarAdapter(context);
        mAdapter.setOnClick(this);
        mAdapter.setOnLongClick(this);
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

        mAdapter.setAdd(cells, calendar.get(calendar.MONTH));
        gridView.setAdapter(mAdapter);
    }

    public void setListener(CalendarListener listener) {
        this.listener = listener;
    }


    @Override
    public void setOnClick(ArrayList<Date> list, int postion) {
        Log.d(TAG, sdf.format(list.get(postion)));
        if (listener != null) {
            if (postion == currentPostion)
                return;
            listener.setOnIteclick(list, postion);
            mAdapter.setItemChecked(postion);
            currentPostion = postion;
        }
    }

    @Override
    public void setOnLongItemClick(ArrayList<Date> list, int postion) {
        Log.d(TAG, sdf.format(list.get(postion)));

    }


    public interface CalendarListener {
        void setOnIteclick(ArrayList<Date> list, int postion);
    }


    public void setAddDateSign(List<String> dateSignList) {
        if (dateSignList == null || dateSignList.size() == 0) return;
        signList.addAll(dateSignList);
        if (mAdapter == null) return;
        mAdapter.setAddSign(signList);
    }

}
