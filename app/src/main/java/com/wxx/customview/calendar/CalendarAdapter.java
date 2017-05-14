package com.wxx.customview.calendar;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxx.customview.R;
import com.wxx.customview.circlebgtextview.CircleBgTextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：Tangren on 2017/5/14 15:27
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class CalendarAdapter extends RecyclerView.Adapter {

    private ArrayList<Date> list;

    private int currentPageMonth;

    private static final String TAG = "CalendarAdapter";

    public CalendarAdapter(ArrayList<Date> list, int currentPageMonth) {
        this.list = list;
        this.currentPageMonth = currentPageMonth;
    }

    private LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_item, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Date date = list.get(position);
        if (date == null)
            return;
        CalendarHolder calendarHolder = (CalendarHolder) holder;

        //判断是否是同月,如果不是则以灰色显示,是则正常色显示
        if (Util.isSameMonth(currentPageMonth, date)) {
            calendarHolder.textView.setTextColor(Color.parseColor("#000000"));
        } else {
            calendarHolder.textView.setTextColor(Color.parseColor("#C0C0C0"));
        }

        if (Util.isSameDate(date)) {
            calendarHolder.textView.setTextColor(Color.parseColor("#FF4081"));
            calendarHolder.textView.isToday = true;
        }

        calendarHolder.textView.setText(String.valueOf(date.getDate()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    private class CalendarHolder extends RecyclerView.ViewHolder {

        private CircleBgTextView textView;

        public CalendarHolder(View itemView) {
            super(itemView);
            textView = (CircleBgTextView) itemView.findViewById(R.id.days);
        }
    }
}
