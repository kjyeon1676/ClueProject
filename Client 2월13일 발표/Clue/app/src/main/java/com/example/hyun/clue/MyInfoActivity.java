package com.example.hyun.clue;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

//import Control.MyAsync;
import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;
import Model.Data.User;


/**
 * Created by hyun on 2015-01-31.
 */
public class MyInfoActivity extends Activity {

 //   MyAsync myAsync = null;
    TextView idText = null;
    TextView genderText = null;
    TextView scoreText = null;
    TextView gradeText = null;
    TextView emailText = null;
    SendSocket send = null;
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        TaskManager tsk = new TaskManager();
        setContentView(R.layout.activity_myidentify);

       // myAsync = new MyAsync();
        idText = (TextView)findViewById(R.id.identify_id);
        scoreText = (TextView)findViewById(R.id.identify_score);
        genderText = (TextView)findViewById(R.id.identify_gender);
        gradeText = (TextView)findViewById(R.id.identify_grade);
        emailText = (TextView)findViewById(R.id.identify_email);
        ReceiveSocket.setHandler(mHandler);
        Button finishBtn = (Button)findViewById(R.id.finishButton1);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent idIntent = getIntent();
        String id = idIntent.getStringExtra("id");
        sendSocketMessage(new TaskManager().clientMyInfo(id));

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    String myInfoSet = String.valueOf(msg.obj);
                    String arr[] = new String[4];
                    int i = 0;
                    StringTokenizer token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        arr[i++]=token.nextToken();
                    }
                    User user = new User(arr[0],Integer.valueOf(arr[2]),0);


                        idText.setText(user.getId());
                        scoreText.setText(String.valueOf(user.getScore()));
                        gradeText.setText(user.getGrade());
                        genderText.setText(arr[1]);
                        emailText.setText(arr[3]);

                    break;
            }

        }
    };

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

}
