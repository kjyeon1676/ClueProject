package com.example.hyun.clue;

/**
 * Created by hyun on 2015-02-07.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Control.SendSocket;

import static java.lang.Thread.sleep;

public class DiceActivity extends Activity {
    ImageView dice_picture;
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound_id;
    private TextView text1 = null;
    private TextView text2 = null;
    boolean rolling=false;
    TextView diceCnt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        dice_picture = (ImageView) findViewById(R.id.imageView1);
        text1 = (TextView) findViewById(R.id.textView11);
        text2 = (TextView) findViewById(R.id.textView12);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼 막기
            case KeyEvent.KEYCODE_BACK:
                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void HandleClick(View arg0) {
        if (!rolling) {


            rolling = true;
            dice_picture.setImageResource(R.drawable.dice3droll);
            dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
            resultValue();

        }
    }
    protected void resultValue()
    {
        Intent intent = getIntent();
        int num = intent.getExtras().getInt("num");

        switch (num) {
            case 1:
                dice_picture.setImageResource(R.drawable.one);
                break;
            case 2:
                dice_picture.setImageResource(R.drawable.two);
                break;
            case 3:
                dice_picture.setImageResource(R.drawable.three);
                break;
            case 4:
                dice_picture.setImageResource(R.drawable.four);
                break;
            case 5:
                dice_picture.setImageResource(R.drawable.five);
                break;
            case 6:
                dice_picture.setImageResource(R.drawable.six);
                break;
            default:


        }
        text1.setText("");
        text2.setText("나온 숫자");

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


    //Clean up
    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
}