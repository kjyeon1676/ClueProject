package com.example.hyun.clue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import Control.ReceiveSocket;

/**
 * Created by so98le on 2015-01-30.
 */
public class LoadingActivity extends Activity
{

    ProgressDialog pDlg=null;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_gameloading);
        ReceiveSocket.setHandler(mHandler);


        pDlg = new ProgressDialog(this);
        pDlg.show(LoadingActivity.this, "플레이어를 기다리는 중", "잠시만 기다려주세요!");

    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 6:
                    new Thread(new Runnable() {
                        public void run() {
                     pDlg.dismiss();
                    Intent intent = new Intent(getApplication(), PlayGameActivity.class);
                    startActivity(intent);

                    }
                }).start();

                    finish();
                    break;

            }

        }
    };
}
