package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button start, pause, reset, lap, pindah;
    long Millisecondtime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minute, MilliSeconds;
    ListView listView;
    String[] ListElements = new String[]{};
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        start = findViewById(R.id.button);
        pause = findViewById(R.id.button2);
        reset = findViewById(R.id.button3);
        lap = findViewById(R.id.button4);
        listView = findViewById(R.id.listView);
        pindah = findViewById(R.id.pindah);
        handler = new Handler();
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList);

        listView.setAdapter(adapter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0 );

                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff += Millisecondtime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Millisecondtime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minute = 0;
                MilliSeconds = 0;

                textView.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListElementsArrayList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahIntent = new Intent(MainActivity.this, SpeechToText.class);
                startActivity(pindahIntent);
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Millisecondtime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + Millisecondtime;
            Seconds = (int)(UpdateTime/1000);
            Minute = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int)(UpdateTime % 1000);
            textView.setText(""+Minute+":"
                    +String.format("%02d", Seconds) +":"
                    + String.format("%03d", MilliSeconds));
            handler.postDelayed(this,0);
        }
    };
}
