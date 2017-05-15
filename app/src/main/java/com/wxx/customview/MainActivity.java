package com.wxx.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.wxx.customview.animation.MyAnimation;
import com.wxx.customview.biaopan.BoardView;
import com.wxx.customview.calendar.OnItemClick;
import com.wxx.customview.calendar.OnLongItemClick;
import com.wxx.customview.calendar.TRCalendar;
import com.wxx.customview.paopaochuang.PaoPao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TRCalendar.CalendarListener {

    private LinearLayout activity_main;
    private MyAnimation animation;
    private static final String TAG = "MainActivity";
    private SeekBar seekBar;
    private BoardView panView;

    private PaoPao pao;
    private TRCalendar tRcalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        panView = (BoardView) findViewById(R.id.panView);
        tRcalendar = (TRCalendar) findViewById(R.id.tRcalendar);
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

        tRcalendar.setListener(this);

    }

    @Override
    public void onClick(View v) {
//        activity_main.startAnimation(animation);
    }


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void setOnIteclick(ArrayList<Date> list, int postion) {
        Toast.makeText(this, format.format(list.get(postion)), Toast.LENGTH_SHORT).show();
    }
}
