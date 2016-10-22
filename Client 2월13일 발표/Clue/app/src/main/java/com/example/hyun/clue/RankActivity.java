package com.example.hyun.clue;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;
import Model.Data.User;

/**
 * Created by so98le on 2015-01-28.
 */
public class RankActivity extends Activity
{

    ArrayList<User> userList;
    SendSocket send = null;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_rank);
        ReceiveSocket.setHandler(mHandler);

        userList = new ArrayList<User>();

        sendSocketMessage(new TaskManager().ranking());
        RankAdapter adapter = new RankAdapter(this, R.layout.rank_item, userList);

        ListView list;
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4:
                    String myInfoSet = String.valueOf(msg.obj);
                    String arr[] = new String[3];
                    int i = 0;
                    StringTokenizer token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        arr[i++]=token.nextToken();
                    }
                    User myRank;
                    myRank = new User(arr[0], Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
                    userList.add(myRank);
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



class RankAdapter extends BaseAdapter{

    Context con;
    LayoutInflater inflater;
    ArrayList<User> arr;
    int layout;
    int count;
    public RankAdapter(Context context, int alayout, ArrayList<User> aarr){
        con = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arr = aarr;
        layout = alayout;
        count=0;
    }

    @Override
    public int getCount(){
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }


        TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
        txt1.setText(""+arr.get(position).getRank());

        TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
        txt2.setText(arr.get(position).getId());

        TextView txt3 = (TextView) convertView.findViewById(R.id.txt3);
        txt3.setText(""+arr.get(position).getScore());

        TextView txt4 = (TextView) convertView.findViewById(R.id.txt4);
        txt4.setText(""+arr.get(position).getGrade());
        return convertView;
    }
}