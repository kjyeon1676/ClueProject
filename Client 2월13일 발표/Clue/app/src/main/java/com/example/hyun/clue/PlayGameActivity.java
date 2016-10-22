package com.example.hyun.clue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import Control.ReceiveSocket;
import Control.SendSocket;
import Control.TaskManager;
import Model.Data.Card;
import Model.Data.CardList;
import Model.Data.Coordinate;
import Model.Data.Player;

import static java.lang.Thread.sleep;

/**
 * Created by so98le on 2015-02-03.
 */
public class PlayGameActivity extends Activity implements android.widget.RadioGroup.OnCheckedChangeListener,View.OnClickListener {
    private Dialog mDialog = null;
    private SendSocket send = null;
    TaskManager tsk = null;
    private CardList cardList = null;
    private ArrayList<Card> myCards= null;
    private Player player = null;
    private Dialog inferenceStartDialog = null;
    private Dialog inferenceFinishDialog = null;
    private Dialog selectDialog = null;
    private Dialog checkDialog = null;


    ArrayList<String> playerList = null;
    ArrayList<String> roomList = null;
    ArrayList<String> weaponList = null;
    String inferenceRoom= null;
    String inferenceWeapon = null;
    String inferencePlayer = null;
    //    Button checkBtn = (Button) findViewById(R.id.playgame_checktable);
    Button nextBtn = null;
    Button myCardBtn = null;
    Button okBtn = null;
    Button upBtn = null;
    Button downBtn = null;
    Button leftBtn = null;
    Button rightBtn = null;
    Button checkBtn =null;
    Button transBtn = null;
    Button resultBtn = null;
    Button getCheckBtn = null;
    RadioButton radioBtn1 = null;
    RadioButton radioBtn2 = null;
    RadioButton radioBtn3 = null;

    TextView firstCard = null;
    TextView secondCard = null;
    TextView thirdCard = null;
    TextView fourthCard = null;
    TextView playerName =null;
    TextView currentXY = null;
    TextView diceCnt = null;
    TextView location = null;

    TextView inferenceRoomText = null;
    TextView inferencePlayerText = null;
    TextView inferenceWeaponText = null;
    TextView currentRoomText = null;
    TextView checkCardText1 = null;
    TextView checkCardText2 = null;
    TextView checkCardText3 = null;
    TextView checkPlayer1 = null;
    TextView checkPlayer2 = null;
    TextView checkPlayer3 = null;

    TextView selectCard1 = null;
    TextView selectCard2 = null;
    TextView selectCard3 = null;

    private ImageView image1 = null;
    private ImageView image2 = null;
    private ImageView image3 = null;


    private ImageView cImage1 = null;
    private ImageView cImage2 = null;
    private ImageView cImage3 = null;

    boolean flag3 = false;
    boolean winFlag = false;
    boolean loseFlag = false;
    boolean turnFlag = false;
    RadioGroup radio= null;

    private Coordinate xy= null;
    Spinner sPlayerSpinner = null;
    Spinner sWeaponSpineer = null;
    Spinner roomSpinner = null;
    Spinner weaponSpinner = null;
    Spinner playerSpinner = null;
    ArrayAdapter<String> adapter1 = null;
    ArrayAdapter<String> adapter2 = null;
    ArrayAdapter<String> adapter3 = null;
    Button inferenceStartBtn = null;
    String stream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        playerList = new ArrayList<String>();
        roomList = new ArrayList<String>();
        weaponList = new ArrayList<String>();
        winFlag =false;
        loseFlag= false;
        turnFlag =false;
        super.onCreate(savedInstanceState);
        xy = new Coordinate();
        setContentView(R.layout.activity_playgame);
        createDialog();
        createInferenceStartDialog();
        createInferenceFinishDialog();
        createSelectCardDialog();
        createCheckDialog();

        tsk = new TaskManager();
        cardList = new CardList();
        player = new Player();
        myCards = new ArrayList<Card>();
        sendSocketMessage(tsk.gameStart()); //게임 시작을 서버에 알림
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, playerList);//스피너 어댑터1
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roomList);//스피너 어댑터2
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, weaponList);//스피너

        ReceiveSocket.setHandler(mHandler);//핸들러 설정


        radio = (RadioGroup) selectDialog.findViewById(R.id.radioGroup2);
        radio.setOnCheckedChangeListener(this);
        resultBtn = (Button) inferenceFinishDialog.findViewById(R.id.button3);
        transBtn = (Button) selectDialog.findViewById(R.id.transButton);
        myCardBtn = (Button) findViewById(R.id.playgame_mycard);
        okBtn = (Button) mDialog.findViewById(R.id.btn_ok1);
        upBtn =  (Button) findViewById(R.id.playgame_up);
        downBtn =  (Button) findViewById(R.id.playgame_down);
        leftBtn =  (Button) findViewById(R.id.playgame_left);
        rightBtn =  (Button) findViewById(R.id.playgame_right);
        checkBtn =  (Button) findViewById(R.id.playgame_checktable);
        nextBtn = (Button)findViewById(R.id.nextTurnBtn);
        playerName = (TextView) findViewById(R.id.playgame_chrname);
        currentXY = (TextView) findViewById(R.id.textView10);
        diceCnt = (TextView) findViewById(R.id.diceCountText);
        inferenceRoomText = (TextView) findViewById(R.id.textView31);
        inferencePlayerText = (TextView) findViewById(R.id.textView32);
        location = (TextView) findViewById(R.id.textView9);
        inferenceWeaponText = (TextView) findViewById(R.id.textView33);
        currentRoomText = (TextView) inferenceStartDialog.findViewById(R.id.roomText);
        inferenceStartBtn = (Button) inferenceStartDialog.findViewById(R.id.inferenceStartBtn);

        getCheckBtn = (Button) checkDialog.findViewById(R.id.button4);
        myCardBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        resultBtn.setOnClickListener(this);
        getCheckBtn.setOnClickListener(this);
        transBtn.setOnClickListener(this);
        inferenceStartBtn.setOnClickListener(this);
        inferencePlayerText.setVisibility(View.INVISIBLE);
        inferenceRoomText.setVisibility(View.INVISIBLE);
        inferenceWeaponText.setVisibility(View.INVISIBLE);
       // inferecneStartDialog =

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playgame_checktable:
                Intent intent = new Intent(this, CheckTableActivity.class);
                intent.putExtra("cardList",cardList);
                startActivity(intent);
                break;
            case R.id.playgame_mycard:
                mDialog.show();
                break;
            case R.id.btn_ok1:
                mDialog.dismiss();

                break;
            case R.id.playgame_up:
                if(xy.getCoordinate(player.getCurrentX()-1,player.getCurrentY()) && player.getDiceCount()!=0)
                {
                    sendSocketMessage(tsk.moveCoordinateTransfer(player.getCurrentX() - 1, player.getCurrentY()));
                    player.setCurrentX(player.getCurrentX()-1);
                    player.setCurrentY(player.getCurrentY());
                    player.setDiceCount(player.getDiceCount()-1);
                    diceCnt.setText(String.valueOf(player.getDiceCount()));
                }
                break;
            case R.id.playgame_left:
                if(xy.getCoordinate(player.getCurrentX(),player.getCurrentY()-1)&& player.getDiceCount()!=0)
                {
                    sendSocketMessage(tsk.moveCoordinateTransfer(player.getCurrentX(), player.getCurrentY() - 1));
                    player.setCurrentX(player.getCurrentX());
                    player.setCurrentY(player.getCurrentY()-1);
                    player.setDiceCount(player.getDiceCount()-1);
                    diceCnt.setText(String.valueOf(player.getDiceCount()));
                }
                break;
            case R.id.playgame_right:
                if(xy.getCoordinate(player.getCurrentX(),player.getCurrentY()+1)&& player.getDiceCount()!=0)
                {
                    sendSocketMessage(tsk.moveCoordinateTransfer(player.getCurrentX(), player.getCurrentY() + 1));
                    player.setCurrentX(player.getCurrentX());
                    player.setCurrentY(player.getCurrentY()+1);
                    player.setDiceCount(player.getDiceCount()-1);
                    diceCnt.setText(String.valueOf(player.getDiceCount()));
                }
                break;
            case R.id.playgame_down:
                if(xy.getCoordinate(player.getCurrentX()+1,player.getCurrentY())&& player.getDiceCount()!=0)
                {

                    sendSocketMessage(tsk.moveCoordinateTransfer(player.getCurrentX() + 1, player.getCurrentY()));
                    player.setCurrentX(player.getCurrentX() + 1);
                    player.setCurrentY(player.getCurrentY());
                    player.setDiceCount(player.getDiceCount() - 1);
                    diceCnt.setText(String.valueOf(player.getDiceCount()));
                }
                break;
            case R.id.button4:
                checkDialog.dismiss();
                break;
            case R.id.nextTurnBtn:
                if(turnFlag==false)
                {
                    Toast.makeText(getApplicationContext(), "내턴이 아닙니다.", Toast.LENGTH_LONG).show();
                    break;
                }
                if(player.getDiceCount()==0)
                {
                    inferencePlayerText.setText("  ");
                    inferenceRoomText.setText("  ");
                    inferenceWeaponText.setText("  ");
                    diceCnt.setText("");
                    sendSocketMessage(tsk.nextTurn());
                    turnFlag=false;
                }
                else
                {
                    new AlertDialog.Builder(this)
                        .setTitle("턴넘기기")
                        .setMessage("턴을 넘기시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                         diceCnt.setText("");
                         image1.setVisibility(View.INVISIBLE);
                         image2.setVisibility(View.INVISIBLE);
                         image3.setVisibility(View.INVISIBLE);
                           turnFlag= false;
                         selectCard1.setVisibility(View.INVISIBLE);
                         selectCard2.setVisibility(View.INVISIBLE);
                         selectCard3.setVisibility(View.INVISIBLE);
                         radioBtn1.setVisibility(View.INVISIBLE);
                         radioBtn2.setVisibility(View.INVISIBLE);
                         radioBtn3.setVisibility(View.INVISIBLE);
                         checkCardText1.setText("");
                         checkCardText2.setText("");
                         checkCardText3.setText("");
                         cImage1.setVisibility(View.INVISIBLE);
                         cImage2.setVisibility(View.INVISIBLE);
                         cImage3.setVisibility(View.INVISIBLE);
                         inferencePlayerText.setText("  ");
                         inferenceRoomText.setText("  ");
                         inferenceWeaponText.setText("  ");
                          sendSocketMessage(tsk.nextTurn());
                          diceCnt.setText("");
                            }
                        })
                        .setNegativeButton("아니오", null).show();
                }
                break;
            case R.id.inferenceStartBtn:

                sendSocketMessage("44$"+player.getName()+"$"+roomList.get(0)+"$"+inferenceWeapon+"$"+inferencePlayer);
                inferenceStartDialog.dismiss();
                break;
            case R.id.transButton:
                if(flag3==false)
                    Toast.makeText(getApplicationContext(), "카드를 선택하세요!!", Toast.LENGTH_LONG).show();
                else
                {
                    sendSocketMessage(player.getName()+"$"+ stream);
                    selectDialog.dismiss();
                }
                break;
            case R.id.button3:
                sendSocketMessage("45$"+inferenceRoom+"$"+inferencePlayer+"$"+inferenceWeapon);
                inferenceFinishDialog.dismiss();
                break;
            //라디오버튼 구현 및 버튼 눌럿을씨 invisible로 초기화 7
        }
        currentXY.setText("현재 좌표 : ("+player.getCurrentX()+","+player.getCurrentY()+")");
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        if(checkedId == R.id.radioButton1)
        {
           stream = selectCard1.getText().toString();
            flag3 = true;
        }
        if(checkedId == R.id.radioButton2)
        {
            stream = selectCard2.getText().toString();
            flag3 = true;
        }
        if(checkedId == R.id.radioButton3)
        {
             stream = selectCard3.getText().toString();
            flag3 = true;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            //하드웨어 뒤로가기 버튼에 따른 이벤트 설정
            case KeyEvent.KEYCODE_BACK:

                new AlertDialog.Builder(this)
                        .setTitle("게임 진행중")
                        .setMessage("게임 진행중 나가실 수 없습니다.")
                        .setNegativeButton("확인", null).show();
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void getSpinnerItem()
    {
        sPlayerSpinner = (Spinner) inferenceStartDialog.findViewById(R.id.sPlayerSpinner);

        sWeaponSpineer = (Spinner) inferenceStartDialog.findViewById(R.id.sWeaponSpinner);

        sPlayerSpinner.setAdapter(adapter1);
        sPlayerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                   inferencePlayer = playerList.get(arg2);
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sWeaponSpineer.setAdapter(adapter3);
        sWeaponSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                inferenceWeapon = weaponList.get(arg2);
                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        roomSpinner = (Spinner) inferenceFinishDialog.findViewById(R.id.roomSpinner);
        weaponSpinner = (Spinner) inferenceFinishDialog.findViewById(R.id.weaponSpinner);
        playerSpinner = (Spinner) inferenceFinishDialog.findViewById(R.id.playerSpinner);

        playerSpinner.setAdapter(adapter1);
        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                inferencePlayer = playerList.get(arg2);
                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        roomSpinner.setAdapter(adapter2);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                inferenceRoom = roomList.get(arg2);

                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        weaponSpinner.setAdapter(adapter3);
        weaponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                inferenceWeapon = weaponList.get(arg2);

                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });
    }
    private void createInferenceStartDialog()
    {
        final View innerView = getLayoutInflater().inflate(R.layout.inference_start, null);

        inferenceStartDialog = new Dialog(this);

        inferenceStartDialog.setContentView(innerView);
        inferenceStartDialog.setCancelable(true);
        inferenceStartDialog.setCanceledOnTouchOutside(true);
     //   Log.d("getID",String.valueOf(sPlayerSpinner.getId()));



        WindowManager.LayoutParams params = inferenceStartDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        inferenceStartDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }
    private void createInferenceFinishDialog()
    {
        final View innerView = getLayoutInflater().inflate(R.layout.inference_finish, null);
        inferenceFinishDialog = new Dialog(this);

        inferenceFinishDialog.setContentView(innerView);
        inferenceFinishDialog.setCancelable(true);
        inferenceFinishDialog.setCanceledOnTouchOutside(true);


        WindowManager.LayoutParams params = inferenceFinishDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        inferenceFinishDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
    private void createSelectCardDialog()
    {
        final View innerView = getLayoutInflater().inflate(R.layout.select_item, null);
        selectDialog = new Dialog(this);
        selectDialog.setContentView(innerView);
        selectDialog.setCancelable(true);
        selectDialog.setCanceledOnTouchOutside(true);

        radioBtn1 = (RadioButton) selectDialog.findViewById(R.id.radioButton1);
        radioBtn2 = (RadioButton) selectDialog.findViewById(R.id.radioButton2);
        radioBtn3 = (RadioButton) selectDialog.findViewById(R.id.radioButton3);
        selectCard1 = (TextView)selectDialog.findViewById(R.id.textView13);
        selectCard2 = (TextView)selectDialog.findViewById(R.id.textView14);
        selectCard3 = (TextView)selectDialog.findViewById(R.id.textView15);
        image1 = (ImageView)selectDialog.findViewById(R.id.imageView6);
        image2 = (ImageView)selectDialog.findViewById(R.id.imageView5);
        image3 = (ImageView)selectDialog.findViewById(R.id.imageView7);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
        radioBtn1.setVisibility(View.INVISIBLE);
        radioBtn2.setVisibility(View.INVISIBLE);
        radioBtn3.setVisibility(View.INVISIBLE);
        selectCard1.setVisibility(View.INVISIBLE);
        selectCard2.setVisibility(View.INVISIBLE);
        selectCard3.setVisibility(View.INVISIBLE);

        WindowManager.LayoutParams params = selectDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        selectDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private void createDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.card_item, null);

        mDialog = new Dialog(this);
        mDialog.setTitle("카드목록");
        mDialog.setContentView(innerView);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        firstCard = (TextView)  mDialog.findViewById(R.id.textView5);
        secondCard = (TextView) mDialog.findViewById(R.id.textView6);
        thirdCard = (TextView) mDialog.findViewById(R.id.textView7);
        fourthCard = (TextView) mDialog.findViewById(R.id.textView8);
        ReceiveSocket.setHandler(mHandler);
                // Dialog 사이즈 조절 하기

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);


    }
    private void createCheckDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.check_card, null);

        checkDialog = new Dialog(this);
        checkDialog.setTitle("상대방이 가지고 있는 카드");
        checkDialog.setContentView(innerView);
        checkDialog.setCancelable(true);
        checkDialog.setCanceledOnTouchOutside(true);
        // Dialog 사이즈절 조 하기

        checkCardText1 = (TextView) checkDialog.findViewById(R.id.textView26);
        checkCardText2 = (TextView) checkDialog.findViewById(R.id.textView17);
        checkCardText3 = (TextView) checkDialog.findViewById(R.id.textView24);
        checkPlayer1 = (TextView) checkDialog.findViewById(R.id.textView25);
        checkPlayer2 = (TextView) checkDialog.findViewById(R.id.textView20);
        checkPlayer3 = (TextView) checkDialog.findViewById(R.id.textView27);

        cImage1 = (ImageView)checkDialog.findViewById(R.id.imageView8);
        cImage2 = (ImageView)checkDialog.findViewById(R.id.imageView9);
        cImage3 = (ImageView)checkDialog.findViewById(R.id.imageView10);
        checkCardText1.setVisibility(View.INVISIBLE);
        checkCardText2.setVisibility(View.INVISIBLE);
        checkCardText3.setVisibility(View.INVISIBLE);
        cImage1.setVisibility(View.INVISIBLE);
        cImage2.setVisibility(View.INVISIBLE);
        cImage3.setVisibility(View.INVISIBLE);

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        checkDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);


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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            StringTokenizer token=null;
            String myInfoSet=null;
            switch (msg.what) {
                case 20:
                     myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        cardList.addWeaponCard(new Card(token.nextToken()));
                        weaponList.add(cardList.getWeaponCard().get(cardList.getWeaponCard().size()-1).getName());
                    }

                 break;
                case 21:
                    myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        cardList.addPersonCard(new Card(token.nextToken()));
                        playerList.add(cardList.getPersonCard().get(cardList.getPersonCard().size()-1).getName());
                    }


                    break;
                case 22:
                    myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        cardList.addRoomCard(new Card(token.nextToken()));
                        roomList.add(cardList.getRoomCard().get(cardList.getRoomCard().size() - 1).getName());
                    }
                    break;
                case 23:
                    myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");
                    while(token.hasMoreTokens())
                    {
                        myCards.add(new Card(token.nextToken()));

                    }
                    firstCard.setText(myCards.get(0).getName());
                    secondCard.setText(myCards.get(1).getName());
                    thirdCard.setText(myCards.get(2).getName());
                    fourthCard.setText(myCards.get(3).getName());

                    break;
                case 24:
                    myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");
                    player.setCurrentX(Integer.parseInt(token.nextToken()));
                    player.setCurrentY(Integer.parseInt(token.nextToken()));
                    player.setColor(token.nextToken()); // 이부분 체크!!!
                    player.setName(token.nextToken());

                    playerName.setText(player.getName());
                    location.setText("복도");
                    currentXY.setText("현재 좌표 : ("+player.getCurrentX()+","+player.getCurrentY()+")");
                    getSpinnerItem(); //스피너 어댑터 설정

                    break;

                case 31://1번방
                   String room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();
                    player.setDiceCount(0);
                    break;
                case 32://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();

                    player.setDiceCount(0);
                    break;
                case 33://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();

                    player.setDiceCount(0);
                    break;
                case 34://최종추리방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceFinishDialog.show();
                    player.setDiceCount(0);
                    break;
                case 35://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();
                    player.setDiceCount(0);
                    break;
                case 36://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();
                    player.setDiceCount(0);
                    break;
                case 37://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();
                    player.setDiceCount(0);
                    break;
                case 38://1번방
                    room = String.valueOf(msg.obj);
                    currentRoomText.setText(room);
                    location.setText(room);
                    inferenceStartDialog.show();
                    player.setDiceCount(0);
                    break;


                case 39:
                    sendSocketMessage("41$");
                    diceCnt.setText("");
                    break;
                case 40:
                    if(loseFlag==false)
                        sendSocketMessage(tsk.diceThorw());//주사위던지기
                    else
                        sendSocketMessage("49");
                    break;
                    // 기
                case 41:;
                    turnFlag = true;
                    player.setDiceCount(Integer.parseInt(String.valueOf(msg.obj)));
                    Intent intent1 = new Intent(getApplication(), DiceActivity.class);
                    intent1.putExtra("num", Integer.parseInt(String.valueOf(msg.obj)));
                    startActivity(intent1);
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    diceCnt.setText(String.valueOf(player.getDiceCount()));
                    break;
                case 42:
                    myInfoSet = String.valueOf(msg.obj);
                    token = new StringTokenizer(myInfoSet,"$");


                  checkCardText1.setVisibility(View.VISIBLE);
                   checkCardText2.setVisibility(View.VISIBLE);
                   checkCardText3.setVisibility(View.VISIBLE);

                    checkPlayer1.setText(token.nextToken());
                    String stream2 = token.nextToken();
                    if(!stream2.equals("없음"))
                    {
                        cImage1.setVisibility(View.VISIBLE);
                        checkCardText1.setText(stream2);
                    }

                    checkPlayer2.setText(token.nextToken());
                   stream2 = token.nextToken();
                    if(!stream2.equals("없음"))
                    {
                        cImage2.setVisibility(View.VISIBLE);
                        checkCardText2.setText(stream2);
                    }
                      checkPlayer3.setText(token.nextToken());

                    stream2 = token.nextToken();
                    if(!stream2.equals("없음"))
                    {
                        cImage3.setVisibility(View.VISIBLE);
                        checkCardText3.setText(stream2);
                    }



                    checkDialog.show();
                    break;
                case 43://바뀐부분 -> 총 몇명이 보여줫는지 형이 다시 쏴주여야대염
                    myInfoSet = String.valueOf(msg.obj);
                    Toast.makeText(getApplicationContext(), "총"+myInfoSet+"명이 카드를 보여주었습니다.", Toast.LENGTH_LONG).show();
                    break;
                case 44:
                    winFlag = true;
                    sendSocketMessage("46");
                    Intent win = new Intent(getApplication(), WinActivity.class);
                    startActivity(win);
                    finish();
                    //승리
                    //sendMessage(끈다는 표시)
                   break;
                case 45:
                    loseFlag = true;
                    //sendMessage(끈다는표시)
                    break;
                case 46:
                    if(winFlag != true) {
                        sendSocketMessage("46");
                        Intent lose = new Intent(getApplication(), LoseActivity.class);
                        startActivity(lose);
                        finish();
                    }
                    break;
                    //패배

                case 50:
                    myInfoSet = String.valueOf(msg.obj);
                    Toast.makeText(getApplicationContext(), "추리가 시작되었습니다!!!!", Toast.LENGTH_LONG).show();
                    token = new StringTokenizer(myInfoSet,"$");
                    token.nextToken();
                    inferenceRoomText.setText(token.nextToken()+"에서");
                    inferenceWeaponText.setText(token.nextToken()+"로 기절시켰다.");
                    inferencePlayerText.setText(token.nextToken()+"(이)가");
                    inferenceRoomText.setVisibility(View.VISIBLE);
                    inferencePlayerText.setVisibility(View.VISIBLE);
                     inferenceWeaponText.setVisibility(View.VISIBLE);


                    token = new StringTokenizer(myInfoSet,"$");
                    //if(token.nextToken().equals)
                    String mergeStream = "";
                    Log.d("myCard Size",String.valueOf(myCards.size()));
                    if(token.nextToken().equals(player.getName()))
                        break;
                      else
                     {
                    boolean flag = true;
                    while (token.hasMoreTokens()) {
                        String stream1 = token.nextToken();

                        for (int i = 0; i < myCards.size(); i++) {
                            if (myCards.get(i).getName().equals(stream1)) {
                                flag = false;
                                if (image1.getVisibility() == View.INVISIBLE) {
                                    image1.setVisibility(View.VISIBLE);
                                    selectCard1.setVisibility(View.VISIBLE);
                                    selectCard1.setText(myCards.get(i).getName());
                                    radioBtn1.setVisibility(View.VISIBLE);
                                }
                                else if (image2.getVisibility() == View.INVISIBLE) {
                                    image2.setVisibility(View.VISIBLE);

                                    selectCard2.setVisibility(View.VISIBLE);
                                    selectCard2.setText(myCards.get(i).getName());
                                    radioBtn2.setVisibility(View.VISIBLE);
                                }
                                else if (image3.getVisibility() == View.INVISIBLE) {
                                    image3.setVisibility(View.VISIBLE);

                                    selectCard3.setVisibility(View.VISIBLE);
                                    selectCard3.setText(myCards.get(i).getName());
                                    radioBtn3.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    if (flag == true) {
                        Toast.makeText(getApplicationContext(), "추리할 카드가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                        stream = "없음";
                        sendSocketMessage(player.getName() + "$" + stream);
                    } else
                        selectDialog.show();

                }
                    break;
            }


        }
    };

}