package com.wxx.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.wxx.customview.animation.MyAnimation;
import com.wxx.customview.paopaochuang.PaoPao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout activity_main;
    private MyAnimation animation;
    private static final String TAG = "MainActivity";

    private PaoPao pao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        activity_main.setOnClickListener(this);
        animation = new MyAnimation();
        animation.setDuration(1000);

        pao= (PaoPao) findViewById(R.id.pao);
        pao.setSize(240,100);
        pao.setString("您有一个新消息");
    }

    @Override
    public void onClick(View v) {
        activity_main.startAnimation(animation);
    }
}
