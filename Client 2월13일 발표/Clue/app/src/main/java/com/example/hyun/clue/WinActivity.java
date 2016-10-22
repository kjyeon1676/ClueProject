package com.example.hyun.clue;

import android.app.Activity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

/**
 * Created by hyun on 2015-02-13.
 */
public class WinActivity extends Activity{
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_win);

        new Thread(new Runnable() {
            public void run() {
                try {
                    sleep(2000);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
