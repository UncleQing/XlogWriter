package com.example.xlogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;

import com.tencent.mars.xlog.Xlog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String logPath = getApplicationContext().getExternalFilesDir(null).getPath() + "/Log";
        XlogWriter writer = XlogWriter.createWriter(logPath, "MyLog", Xlog.AppednerModeSync, 2 * 1024, 2 * 24 * 3600);

        new Thread(() -> {
            while (true) {
                writer.print("write : " + System.currentTimeMillis());
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}