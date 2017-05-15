package com.wxx.customview.calendar;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
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

    private ArrayList<Date> list=new ArrayList<>();

    private int currentPageMonth;

    private OnItemClick itemClick;
    private OnLongItemClick longItemClick;

    private SparseBooleanArray mBooleanArray;

    private int mLastCheckedPostion = -1;

    private static final String TAG = "CalendarAdapter";

    public CalendarAdapter() {

    }

    public void setAdd(ArrayList<Date> list, int currentPageMonth) {
        this.list.clear();
        this.list.addAll(list);
        this.currentPageMonth = currentPageMonth;
        mBooleanArray = new SparseBooleanArray(list.size());
    }

    private LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_item_view, parent, false);
        return new CalendarHolder(view, itemClick, longItemClick, list);
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
            calendarHolder.textView.setTextColor(Color.parseColor("#E0E0E0"));
        }

        if (Util.isSameDate(date)) {
            calendarHolder.textView.setTextColor(Color.parseColor("#FFFFFF"));
            calendarHolder.textView.setBackgroundResource(R.drawable.circle_shape);
        }

        if (!mBooleanArray.get(position) && !Util.isSameDate(date)) {
            ((CalendarHolder) holder).textView.setBackgroundResource(0);
        } else if (mBooleanArray.get(position) && !Util.isSameDate(date)) {
            ((CalendarHolder) holder).textView.setBackgroundResource(R.drawable.circle_shape_checked);
        }

        calendarHolder.textView.setText(String.valueOf(date.getDate()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setOnLongClick(OnLongItemClick longItemClick) {
        this.longItemClick = longItemClick;
    }


    public void setItemChecked(int postion) {
        mBooleanArray.put(postion, true);
        if (mLastCheckedPostion > -1) {
            mBooleanArray.put(mLastCheckedPostion, false);
            notifyItemChanged(mLastCheckedPostion);
        }
        notifyDataSetChanged();
        mLastCheckedPostion = postion;
    }

    private class CalendarHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView;
        private OnItemClick itemClick;
        private OnLongItemClick longItemClick;
        private ArrayList<Date> list;

        public CalendarHolder(View itemView, OnItemClick itemClick, OnLongItemClick longItemClick, ArrayList<Date> list) {
            super(itemView);
            this.itemClick = itemClick;
            this.longItemClick = longItemClick;
            this.list = list;
            textView = (TextView) itemView.findViewById(R.id.days);
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.setOnClick(list, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (longItemClick != null) {
                longItemClick.setOnLongItemClick(list, getAdapterPosition());
            }
            return true;
        }
    }
}
