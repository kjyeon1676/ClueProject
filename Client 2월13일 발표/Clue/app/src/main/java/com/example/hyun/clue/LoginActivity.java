package com.example.hyun.clue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.StringTokenizer;

import Control.ReceiveSocket;
import Control.SendSocket;
import Model.Data.User;

/**
 * Created by hyun on 2015-01-29.
 */
public class LoginActivity extends Activity implements View.OnClickListener
{
    Button newGameBtn = null;
    Button tutorialBtn = null;
    Button myinfoBtn = null;
    Button rankBtn = null;
    Button customBtn = null;
    SendSocket sendSocket = null;
    Intent getIntent = null;
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        newGameBtn = (Button) findViewById(R.id.startBtn);
        tutorialBtn = (Button) findViewById(R.id.tuBtn);
        myinfoBtn = (Button) findViewById(R.id.myinfoBtn);
        rankBtn = (Button) findViewById(R.id.rankBtn);
        customBtn = (Button) findViewById(R.id.customBtn);
        getIntent = getIntent();

        newGameBtn.setOnClickListener(this);
        tutorialBtn.setOnClickListener(this);
        myinfoBtn.setOnClickListener(this);
        rankBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);

   }
    public void onClick(View view) {

        ReceiveSocket.setHandler(mHandler);//핸들러 설정

        switch (view.getId())
        {
            case R.id.startBtn:
                sendSocket = new SendSocket("5");
                sendSocket.start();
                try {
                    sendSocket.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tuBtn:
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://igreenland.kr/goods/view.php?goodsno=1000117"));
                startActivity(myIntent);
                break;
            case R.id.myinfoBtn:
                Intent intent1 = new Intent (getApplication(),MyInfoActivity.class);
                intent1.putExtra("id",getIntent.getStringExtra("id"));
                Log.d("id:",getIntent.getStringExtra("id"));
                startActivity(intent1);
                break;
            case R.id.rankBtn:
                Intent intent2 = new Intent (getApplication(),RankActivity.class);
                startActivity(intent2);
                break;
            case R.id.customBtn:
                Intent intent3 = new Intent (getApplication(),MyCustomActivity.class);
                intent3.putExtra("id",getIntent.getStringExtra("id"));

                startActivity(intent3);
                break;


        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                 if(String.valueOf(msg.obj).equals("1"))
                 {
                     Intent intent = new Intent(getApplication(), NewgameActivity.class);
                     intent.putExtra("id",getIntent.getStringExtra("id"));
                     startActivity(intent);

                 }
                 else
                 {
                     Intent intent = new Intent (getApplication(),LoadingActivity.class);
                     startActivity(intent);

                 }

            }

        }
    };
}
