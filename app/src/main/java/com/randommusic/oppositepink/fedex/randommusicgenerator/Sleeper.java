package com.randommusic.oppositepink.fedex.randommusicgenerator;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by norbi on 7/7/17.
 */

public class Sleeper {
    private static final int SLEEP_OFFSET = 900;
    private static final int ONE_HOUR = 3600;
    private static final int THREE_HOURS = 10800;

    private Handler handler;
    private Runnable runnable;
    private int time;

    public Sleeper() {
        handler  = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
    }

    public void StartSleep(int wait){
        handler.removeCallbacks(runnable);
        if( time > 0){
            handler.postDelayed(runnable, MilisecToSec(wait));
        }
    }

    private int MilisecToSec(int mili){
        return mili * 1000;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void increaseTime() {
        if(time < ONE_HOUR ){
            time += SLEEP_OFFSET;
        }
        else {
            time += SLEEP_OFFSET * 2;
        }
        if(time > THREE_HOURS){
            time = 0;
        }
    }

    public void setSleep(Context context){
        increaseTime();
        StartSleep(time);
        Toast.makeText(context,"Sleep " + String.valueOf(time / 60) + "mins", Toast.LENGTH_SHORT).show();
    }

    public void setSleep2(final Context context){
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time = selectedHour * 60 + selectedMinute;
                int currTime = hour * 60 + minute;
                int n = time - currTime;
                if(time < currTime){
                    n = 24 * 60 - n;
                }
                StartSleep(n*60);
        Toast.makeText(context,"Sleep " + String.valueOf(n) + "mins", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
