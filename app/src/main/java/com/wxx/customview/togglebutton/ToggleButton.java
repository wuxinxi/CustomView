package com.wxx.customview.togglebutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 作者：Tangren_ on 2017/5/4 0004.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class ToggleButton extends ViewGroup {
    
    public ToggleButton(Context context) {
        this(context,null);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
