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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;


public class MainActivity extends Activity implements View.OnClickListener{
    EditText idText = null;
    EditText pwText = null;
    TaskManager tsk = null;
    Button signUpBtn = null;
    Button loginBtn = null;
    ReceiveSocket receiveSocket = null;
    SendSocket send = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isRunIntro = getIntent().getBooleanExtra("intro", true);
        if(isRunIntro) {
            beforeIntro();
        } else {
            afterIntro(savedInstanceState);
        }


    }

    // 인트로 화면
    private void beforeIntro() {
        // 약 2초간 인트로 화면을 출력.
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("intro", false);
                startActivity(intent);

                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 3000);
    }

    // 인트로 화면 이후.
    private void afterIntro(Bundle savedInstanceState) {
        // 기본 테마를 지정한다.
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        receiveSocket = new ReceiveSocket();
        receiveSocket.start();
        signUpBtn = (Button) findViewById(R.id.signUpButton);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        tsk = new TaskManager();
        idText = (EditText) findViewById(R.id.EditText1);
        pwText = (EditText) findViewById(R.id.EditText2);
        ReceiveSocket.setHandler(mHandler);



    }


    public void onClick(View view) {
        ReceiveSocket.setHandler(mHandler);
        switch (view.getId()) {

            case R.id.loginButton:
                String id = String.valueOf(idText.getText());
                String pw = String.valueOf(pwText.getText());
                sendSocketMessage(tsk.clientLogin(id,pw));
                break;
            case R.id.signUpButton:
                Intent intent = new Intent(getApplication(), SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void sendSocketMessage(String s)
    {
        Log.d("send",s);
        send = new SendSocket(s);
        send.start();
        try {
            send.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(this)
                        .setTitle("프로그램 종료")
                        .setMessage("프로그램을 종료 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 프로세스 종료.
                                 receiveSocket.receiveCancel();
                                sendSocketMessage(new TaskManager().exit());
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("아니오", null).show();

                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
     Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if(msg.obj.toString().equals("1"))
                    {
                        Log.d("Handler intent" , String.valueOf(msg.obj));
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.putExtra("id",String.valueOf(idText.getText()));
                        Log.d("ff",String.valueOf(idText.getText()));
                        startActivity(intent);
                    }
                    else
                        tsk.loginError(getApplicationContext());
                    break;

            }

        }
    };


}
