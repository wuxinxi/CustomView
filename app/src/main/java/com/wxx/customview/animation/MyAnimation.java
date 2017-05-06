package com.wxx.customview.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 作者：Tangren_ on 2017/5/6 0006.
 * 邮箱：wu_tangren@163.com
 * TODO:用一句话概括
 */


public class MyAnimation extends Animation {
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        t.getMatrix().setTranslate((float) Math.sin(interpolatedTime * 50) * 20, 0);
    }
}
