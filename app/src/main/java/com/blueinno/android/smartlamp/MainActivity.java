package com.blueinno.android.smartlamp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.blueinno.android.smartlamp.fragment.TimePickerFragment;
import com.blueinno.android.smartlamp.task.ReservationTimerTask;

import java.util.Timer;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private CardView cardviewLamp;
    private CardView cardviewTimer;
    private CardView cardviewTimePicker;

    private SwitchCompat lampSwitchCompat;
    private SwitchCompat timerSwitchCompat;

    private AppCompatSeekBar appCompatSeekBar;
    private TextView timerField;


    private Timer timer;

    //  =======================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createChildren();
        setProperties();
        configureListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.d("rrobbie", "action setting : ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  ======================================================================================

    private void createChildren() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardviewLamp = (CardView) findViewById(R.id.cardviewLamp);
        cardviewTimer = (CardView) findViewById(R.id.cardviewTimer);
        cardviewTimePicker = (CardView) findViewById(R.id.cardviewTimePicker);

        lampSwitchCompat = (SwitchCompat) findViewById(R.id.lampSwitchCompat);
        timerSwitchCompat = (SwitchCompat) findViewById(R.id.timerSwitchCompat);

        appCompatSeekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        timerField = (TextView) findViewById(R.id.timerField);
    }

    private void setProperties() {
        timerField.setText("아직 설정된 예약 시간이 없습니다.");

        timer = new Timer();
        timer.schedule(new ReservationTimerTask(), 10000);

        enableMode(lampSwitchCompat.isChecked());
    }

    private void configureListener() {
        cardviewLamp.setOnClickListener(this);
        cardviewTimer.setOnClickListener(this);
        cardviewTimePicker.setOnClickListener(this);

        lampSwitchCompat.setOnCheckedChangeListener(this);
        timerSwitchCompat.setOnCheckedChangeListener(this);

        appCompatSeekBar.setOnSeekBarChangeListener(this);
    }


    //  ======================================================================================

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler.sendEmptyMessageDelayed(0,10000);

        }
    };

    private void off() {
        enableMode(false);

    }

    private void on() {
        enableMode(true);
    }

    private void enableMode(boolean flag) {
        timerSwitchCompat.setEnabled(flag);
        appCompatSeekBar.setEnabled(flag);

        timerPickerEnableMode(timerSwitchCompat.isChecked());
    }

    private void timerPickerEnableMode(boolean flag) {
        int color = flag ? android.R.color.holo_red_dark : android.R.color.darker_gray;

        cardviewTimePicker.setEnabled(flag);
        timerField.setTextColor(getResources().getColor(color));
    }

    //  ======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardviewLamp:
                lampSwitchCompat.setChecked(!lampSwitchCompat.isChecked());
                break;

            case R.id.cardviewTimer:
                timerSwitchCompat.setChecked(!timerSwitchCompat.isChecked());
                break;

            case R.id.cardviewTimePicker:
                AppCompatDialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(),"TimePicker");
                break;

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.lampSwitchCompat:
                enableMode(isChecked);
                break;

            case R.id.timerSwitchCompat:
                timerPickerEnableMode(isChecked);
                break;


        }
    }

    //  =========================================================================================

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("rrobbie", "progress : " + progress);
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("rrobbie", "stop : " + appCompatSeekBar );
    }
}
