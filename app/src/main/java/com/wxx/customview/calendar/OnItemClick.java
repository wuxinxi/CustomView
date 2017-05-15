package com.wxx.customview.calendar;

import android.view.View;

import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：Tangren on 2017/5/15 09:59
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public interface OnItemClick {
    void setOnClick(ArrayList<Date> list,int postion);
}
