package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import model.ReservationModel;

public class SetTimeActivity extends AppCompatActivity {

    Context context;

    int usingTime = 0;
    int hour = 0;
    int min = 0;

    Calendar pickerTime = Calendar.getInstance();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    NumberPicker hpicker;
    NumberPicker mpicker;
    long now = System.currentTimeMillis();
    Date mdate = new Date(now);
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String currentTime = simpleDate.format(mdate);
    CheckBox alarmCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time);

        context = this;

        hpicker = (NumberPicker) findViewById(R.id.hpicker);
        hpicker.setMinValue(0);
        hpicker.setMaxValue(2);
        mpicker = (NumberPicker) findViewById(R.id.mpicker);
        mpicker.setMinValue(0);
        mpicker.setMaxValue(59);

        mpicker.setDisplayedValues(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                                                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                                                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                                                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"});
        mpicker.setWrapSelectorWheel(false);

        hpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
                if (newVal == 2) {
                    mpicker.setValue(0);
                    mpicker.setEnabled(false);
                } else mpicker.setEnabled(true);

                hour = newVal;
            }
        });

        mpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
            }
        });


        alarmCheck = findViewById(R.id.tenMinAlert);

        Button positive = (Button) findViewById(R.id.button_start);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent passedIntent = getIntent();
                hour = Integer.parseInt(String.valueOf(hpicker.getValue()));
                min = Integer.parseInt(String.valueOf(mpicker.getValue()));
                usingTime = hour*60 + min;
                addReservationData(usingTime, passedIntent);
                setSeatFlagTrue(passedIntent);
                setUserFlagTrue();
                setValue_usingSeatNum(passedIntent);

                Intent intent = new Intent(context, MainActivity.class);

                startActivity(intent);

                intent.putExtra("hours", hour);
                intent.putExtra("minutes", min);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 101);

            }
        });

        Button negative = (Button) findViewById(R.id.button_cancel);
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void addReservationData(int time, Intent intent) {
        int seatNum = intent.getIntExtra("seatId", 100);
        ReservationModel reservation = new ReservationModel();
        reservation.uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        reservation.startTime = now;
        reservation.setTime = time;
        if(alarmCheck.isChecked()){
            reservation.alert = true;
        } else reservation.alert = false;

        pickerTime.add(Calendar.MINUTE,time);
        reservation.endTime = pickerTime.getTimeInMillis();

        String seatId = String.valueOf(seatNum);
        FirebaseDatabase.getInstance().getReference().child("reservation").child(seatId).setValue(reservation);
    }

    //SeatFlag True로 변경 및 endTime추가
    public void setSeatFlagTrue(Intent intent) {
        String num = String.valueOf(intent.getIntExtra("seatId", 100));
        String seatNum = "seat" + num;
        FirebaseDatabase.getInstance().getReference().child("seat").child(seatNum).child("seatflag").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("seat").child(seatNum).child("endTime").setValue(pickerTime.getTimeInMillis());
    }

    public void setUserFlagTrue() {
        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("flag").setValue(true);
    }

    public void setValue_usingSeatNum(Intent intent) {
        int num = intent.getIntExtra("seatId", 100);
        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("usingSeatNum").setValue(num);
    }
}