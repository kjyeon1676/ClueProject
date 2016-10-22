package com.example.hyun.clue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;
import Model.Data.CustomTitle;
import Model.Data.User;

/**
 * Created by hyun on 2015-02-05.
 */
public class CustomModeActivity extends Activity implements View.OnClickListener{

    Intent getIntent = null;
    private ListView mListView;
    String getNum;
    boolean btnFlag;
    Button ok = null;
    SendSocket send = null;
    ArrayList<CustomTitle> titleList = null;
    private CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_custommode);
        ReceiveSocket.setHandler(mHandler);

        ok = (Button) findViewById(R.id.customBtn);
        btnFlag = true;
        titleList = new ArrayList<CustomTitle>();

        getIntent = getIntent();
        sendSocketMessage("7$"+getIntent.getStringExtra("id"));
        mListView = (ListView) findViewById(R.id.listViewExample);
         // Xml에서 추가한 ListView 연결
        mAdapter = new CustomAdapter(getApplicationContext(),  R.layout.custom_item, titleList,mListView);
        // ListView에 어댑터 연결

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mItemClickListner);
        ok.setOnClickListener(this);
    }
    private AdapterView.OnItemClickListener mItemClickListner = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            getNum = (String)parent.getAdapter().getItem(position);
            btnFlag = false;
            Log.d("click",getNum);
        }
    };
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.customBtn:
                if(btnFlag)
                    Toast.makeText(getApplicationContext(), "모드를 선택하세요!!", Toast.LENGTH_LONG).show();
                else {
                    sendSocketMessage(new TaskManager().customMode(Integer.parseInt(getNum)));

                    finish();
                }
                break;
        }
    }
    private void sendSocketMessage(String s)
    {
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
                    intent.putExtra("id", getIntent.getStringExtra("id"));
                    startActivity(intent);

                case 7:
                    String myInfoSet = String.valueOf(msg.obj);
                    String arr[] = new String[2];
                    int i = 0;
                    StringTokenizer token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        arr[i++]=token.nextToken();
                    }
                    titleList.add(new CustomTitle(arr[0], arr[1]));
            }

        }
    };
}

class CustomAdapter extends BaseAdapter {

    Context con;
    LayoutInflater inflater;
    ArrayList<CustomTitle> arr;
    ListView listView;

    int layout;
    int count;
    public CustomAdapter(Context context, int alayout, ArrayList<CustomTitle> aarr,ListView listView){
        con = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arr = aarr;
        layout = alayout;
        count=0;
        this.listView = listView;
    }

    @Override
    public int getCount(){
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position).getNum();
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
        TextView txt1 = (TextView) convertView.findViewById(R.id.customItemText);
        txt1.setText(""+arr.get(position).getTitle());
        Log.d("getView: ",txt1.getText().toString());


        return convertView;
    }
}