package com.example.hyun.clue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;

/**
 * Created by so98le on 2015-01-28.
 */
public class NewgameActivity extends Activity implements View.OnClickListener
{
    Intent getIntent = null;
    Button simpleBtn = null;
    Button customBtn = null;
    SendSocket send = null;
    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_newgame);

        getIntent = getIntent();
        simpleBtn = (Button) findViewById(R.id.newgame_simple);
        customBtn = (Button) findViewById(R.id.newgame_custom);
        simpleBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);
    }
    public void onClick(View view) {

        ReceiveSocket.setHandler(mHandler);
        switch (view.getId()) {
            case R.id.newgame_simple:
                sendSocketMessage(new TaskManager().clueMode());
                break;
            case R.id.newgame_custom:
                Intent intent2 = new Intent(getApplication(), CustomModeActivity.class);
                intent2.putExtra("id", getIntent.getStringExtra("id"));
                startActivity(intent2);
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(this)
                        .setTitle("뒤로가기")
                        .setMessage("방을 나가시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //send 보낼건데 무엇으로 보낼지 재연이형과 맞춰보기, 만약 뒤로가기 버튼을 누르면 대기중에 있던 사용자들 전부다 메인화면으로 탈출

                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null).show();

                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void sendSocketMessage(String s)
    {
        Log.d("send", s);
        send = new SendSocket(s);
        send.start();
        try {
            send.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 6:
                    Intent intent = new Intent(getApplication(), PlayGameActivity.class);
                    intent.putExtra("id","simple");
                    startActivity(intent);
            }

        }
    };


}
