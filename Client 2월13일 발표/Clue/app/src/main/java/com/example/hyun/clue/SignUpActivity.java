package com.example.hyun.clue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import Control.ReceiveSocket;
import Control.SendSocket;
import Control.SocketHandler;
import Control.TaskManager;

/**
 * Created by hyun on 2015-01-27.
 */
public class SignUpActivity extends Activity implements android.widget.RadioGroup.OnCheckedChangeListener,View.OnClickListener
{
    RadioGroup radio = null;
    String id;
    String password;
    String email;
    String gender;
    Button signUpBtn = null;
    Button repeatBtn = null;
    EditText idTextfield = null;
    EditText pwTextfield = null;
    EditText emailTextfield = null;
    EditText pwCheckText = null;
    boolean repeatCheck = false;
    boolean radioCheck = false;
    SendSocket send = null;
    TaskManager tsk = new TaskManager();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        signUpBtn = (Button) findViewById(R.id.signUpFinishButton);
        repeatBtn = (Button) findViewById(R.id.repeatButton);
        signUpBtn.setOnClickListener(this);
        repeatBtn.setOnClickListener(this);
        idTextfield = (EditText) findViewById(R.id.signText);
        pwTextfield = (EditText) findViewById(R.id.pwText);
        emailTextfield = (EditText) findViewById(R.id.emailText);
        pwCheckText = (EditText) findViewById(R.id.passwordCheckText);
        radio = (RadioGroup) findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(this);

        ReceiveSocket.setHandler(mHandler);
    }
        public void onClick(View view)
        {
             switch (view.getId()) {
                 case R.id.repeatButton:
                     id = idTextfield.getText().toString();
                     SendSocket send = new SendSocket("11$"+id);
                     send.start();
                     try {
                         send.join();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     //myAsyncTask.execute("11/"+id);
                        break;
                 case R.id.signUpFinishButton:

                     if(repeatCheck == true) {
                         id = idTextfield.getText().toString();
                         password = pwTextfield.getText().toString();
                         if (password.length() < 6) {
                             tsk.passwordError(getApplicationContext());//아이디 5자리 이하 오류
                             break;
                         }
                         if(!pwCheckText.getText().toString().equals(pwTextfield.getText().toString()))
                         {
                             tsk.passwordError3(getApplicationContext());
                             break;
                         }
                         if(radioCheck==false)
                         {
                            tsk.genderCheck(getApplicationContext());
                             break;
                         }
                         boolean flag = false;
                         for(int i=0;i<password.length();i++)
                         {
                             if(34<password.charAt(i) && password.charAt(i)<39)
                             {
                                 tsk.passwordError2(getApplicationContext());
                                 flag = true;
                                 break;
                             }
                         }
                         if(flag)
                             break;

                         email = emailTextfield.getText().toString();

                         sendSocketMessage(tsk.clientSignUp(id,password,gender,email));

                          tsk.signUpSuccess(getApplicationContext());
                         repeatCheck=false;
                         finish();
                     }
                     else
                     {
                         tsk.repeatCheck(getApplicationContext());//중복확인눌러주세요
                     }
                     break;
             }
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
                case 11:
                    if(msg.obj.equals("1"))
                    {
                        tsk.idSuccess(getApplicationContext());
                        repeatCheck = true;
                     }
                    else
                        tsk.idError(getApplicationContext());

                    break;
                case 1:
            }

        }
    };



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        if(checkedId == R.id.manButton)
        {
            radioCheck = true;
            gender = "남";
        }
        if(checkedId == R.id.womanButton)
        {
            radioCheck = true;
            gender = "여";
        }

    }
}
