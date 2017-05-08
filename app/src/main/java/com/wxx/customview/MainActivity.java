package com.wxx.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.wxx.customview.animation.MyAnimation;
import com.wxx.customview.biaopan.BoardView;
import com.wxx.customview.paopaochuang.PaoPao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout activity_main;
    private MyAnimation animation;
    private static final String TAG = "MainActivity";
    private SeekBar seekBar;
    private BoardView panView;

    private PaoPao pao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        panView = (BoardView) findViewById(R.id.panView);
        activity_main.setOnClickListener(this);
        animation = new MyAnimation();
        animation.setDuration(1000);

        pao = (PaoPao) findViewById(R.id.pao);
        pao.setSize(240, 100);
        pao.setString("您有一个新消息");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                panView.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
//        activity_main.startAnimation(animation);
    }
}
