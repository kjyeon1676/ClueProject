package com.example.hyun.clue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Control.SendSocket;
import Control.TaskManager;
import Model.Logic.MyCustom;

/**
 * Created by so98le on 2015-02-01.
 */
public class MyCustomActivity extends Activity
{
    SendSocket send = null;
    TextView title = null;
    TextView person1 = null;
    TextView person2 = null;
    TextView person3 = null;
    TextView person4 = null;
    TextView person5 = null;

    TextView weapon1 = null;
    TextView weapon2 = null;
    TextView weapon3 = null;
    TextView weapon4 = null;
    TextView weapon5 = null;
    TextView weapon6 = null;
    String id;
    TextView room1 = null;
    TextView room2 = null;
    TextView room3 = null;
    TextView room4 = null;
    TextView room5 = null;
    TextView room6 = null;
    TextView room7 = null;
    TextView room8 = null;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_mycustom);

        title = (TextView) findViewById(R.id.story_mode);
        person1 = (TextView) findViewById(R.id.suspect1);
        person2 = (TextView) findViewById(R.id.suspect2);
        person3 = (TextView) findViewById(R.id.suspect3);
        person4 = (TextView) findViewById(R.id.suspect4);
        person5 = (TextView) findViewById(R.id.suspect5);

        weapon1 = (TextView) findViewById(R.id.weapon1);
        weapon2 = (TextView) findViewById(R.id.weapon2);
        weapon3 = (TextView) findViewById(R.id.weapon3);
        weapon4 = (TextView) findViewById(R.id.weapon4);
        weapon5 = (TextView) findViewById(R.id.weapon5);
        weapon6 = (TextView) findViewById(R.id.weapon6);

        room1 = (TextView) findViewById(R.id.room1);
        room2 = (TextView) findViewById(R.id.room2);
        room3 = (TextView) findViewById(R.id.room3);
        room4 = (TextView) findViewById(R.id.room4);
        room5 = (TextView) findViewById(R.id.room5);
        room6 = (TextView) findViewById(R.id.room6);
        room7 = (TextView) findViewById(R.id.room7);
        room8 = (TextView) findViewById(R.id.room8);
        Intent idIntent = getIntent();
        id = idIntent.getStringExtra("id");
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
    public void onClickCustomComplete(View view)
    {
        if(title.getText().length()==0 || person1.getText().length()==0 || person2.getText().length()==0 || person3.getText().length()==0 || person4.getText().length()==0 || person5.getText().length()==0 ||
                weapon1.getText().length()==0 || weapon2.getText().length()==0 || weapon3.getText().length()==0 || weapon4.getText().length()==0 || weapon5.getText().length()==0 ||
                weapon6.getText().length()==0 || room1.getText().length()==0 || room2.getText().length()==0 || room3.getText().length()==0 || room4.getText().length()==0 || room5.getText().length()==0 ||
                room6.getText().length()==0 || room7.getText().length()==0 || room8.getText().length()==0 )
            Toast.makeText(getApplicationContext(), "빈칸이 존재합니다.", Toast.LENGTH_LONG).show();
        else
        {
            sendSocketMessage(new TaskManager().createCustom("B$"+id+"$"+title.getText()+"$"+person1.getText()+"$" +person2.getText()+"$"+person3.getText()+"$"+person4.getText()+"$"+person5.getText()+"$"
            +weapon1.getText()+"$"+weapon2.getText()+"$"+weapon3.getText()+"$"+weapon4.getText()+"$"+weapon5.getText()+"$"+weapon6.getText()+"$"+room1.getText()+"$"+room2.getText()+"$"
                    +room3.getText()+"$"+room4.getText()+"$"+room5.getText()+"$"+room6.getText()+"$"+room7.getText()+"$"+room8.getText()+"$"));

            finish();
        }
    }
}