package com.example.hyun.clue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Data.CardList;

/**
 * Created by so98le on 2015-02-04.
 */
public class CheckTableActivity extends Activity
{
    ArrayList<String> arrayList;
    CardList cardList = null;
    TextView weapon1 = null;
    TextView weapon2 = null;
    TextView weapon3 = null;
    TextView weapon4 = null;
    TextView weapon5 = null;
    TextView weapon6 = null;
    TextView room1 = null;
    TextView room2 = null;
    TextView room3 = null;
    TextView room4 = null;
    TextView room5 = null;
    TextView room6 = null;
    TextView room7 = null;
    TextView room8 = null;
    TextView player1 = null;
    TextView player2 = null;
    TextView player3 = null;
    TextView player4 = null;
    TextView player5 = null;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_checktable);
        weapon1 = (TextView) findViewById(R.id.w1);
        weapon2 = (TextView) findViewById(R.id.w2);
        weapon3 = (TextView) findViewById(R.id.w3);
        weapon4 = (TextView) findViewById(R.id.w4);
        weapon5 = (TextView) findViewById(R.id.w5);
        weapon6 = (TextView) findViewById(R.id.w6);
        room1 = (TextView) findViewById(R.id.r1);
        room2 = (TextView) findViewById(R.id.r2);
        room3 = (TextView) findViewById(R.id.r3);
        room4 = (TextView) findViewById(R.id.r4);
        room5 = (TextView) findViewById(R.id.r5);
        room6 = (TextView) findViewById(R.id.r6);
        room7 = (TextView) findViewById(R.id.r7);
        room8 = (TextView) findViewById(R.id.r8);
        player1 = (TextView) findViewById(R.id.p1);
        player2 = (TextView) findViewById(R.id.p2);
        player3 = (TextView) findViewById(R.id.p3);
        player4 = (TextView) findViewById(R.id.p4);
        player5 = (TextView) findViewById(R.id.p5);

        Intent getIntent = getIntent();
        Serializable list = getIntent.getSerializableExtra("cardList");

        cardList = (CardList)list;
        setText();//

        arrayList = new ArrayList<String>();
        for(int i=0; i<4; i++) {
            arrayList.add(cardList.getPersonCard().get(i).getName());
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        Spinner sp1 = (Spinner) this.findViewById(R.id.checktable_spinner1);
        Spinner sp2 = (Spinner) this.findViewById(R.id.checktable_spinner2);
        Spinner sp3 = (Spinner) this.findViewById(R.id.checktable_spinner3);
        Spinner sp4 = (Spinner) this.findViewById(R.id.checktable_spinner4);
        Spinner sp5 = (Spinner) this.findViewById(R.id.checktable_spinner5);
       Spinner sp7 = (Spinner) this.findViewById(R.id.checktable_spinner7);
        Spinner sp8 = (Spinner) this.findViewById(R.id.checktable_spinner8);
        Spinner sp9 = (Spinner) this.findViewById(R.id.checktable_spinner9);
        Spinner sp10 = (Spinner) this.findViewById(R.id.checktable_spinner10);
        Spinner sp11 = (Spinner) this.findViewById(R.id.checktable_spinner11);
        Spinner sp12 = (Spinner) this.findViewById(R.id.checktable_spinner12);
        Spinner sp13 = (Spinner) this.findViewById(R.id.checktable_spinner13);
        Spinner sp14 = (Spinner) this.findViewById(R.id.checktable_spinner14);
        Spinner sp15 = (Spinner) this.findViewById(R.id.checktable_spinner15);
        Spinner sp16 = (Spinner) this.findViewById(R.id.checktable_spinner16);
        Spinner sp17 = (Spinner) this.findViewById(R.id.checktable_spinner17);
        Spinner sp18 = (Spinner) this.findViewById(R.id.checktable_spinner18);
        Spinner sp19 = (Spinner) this.findViewById(R.id.checktable_spinner19);
        Spinner sp20 = (Spinner) this.findViewById(R.id.checktable_spinner20);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp2.setAdapter(adapter);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp3.setAdapter(adapter);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp4.setAdapter(adapter);
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp5.setAdapter(adapter);
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });



        sp7.setAdapter(adapter);
        sp7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp8.setAdapter(adapter);
        sp8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp9.setAdapter(adapter);
        sp9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp10.setAdapter(adapter);
        sp10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp11.setAdapter(adapter);
        sp11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp12.setAdapter(adapter);
        sp12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp13.setAdapter(adapter);
        sp13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //   Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp14.setAdapter(adapter);
        sp14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp15.setAdapter(adapter);
        sp15.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp16.setAdapter(adapter);
        sp16.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp17.setAdapter(adapter);
        sp17.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp18.setAdapter(adapter);
        sp18.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp19.setAdapter(adapter);
        sp19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        sp20.setAdapter(adapter);
        sp20.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                //  Toast.makeText(CheckTableActivity.this, arrayList.get(arg2), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });
    }

    private void setText()
    {
        weapon1.setText(cardList.getWeaponCard().get(0).getName());
        weapon2.setText(cardList.getWeaponCard().get(1).getName());
        weapon3.setText(cardList.getWeaponCard().get(2).getName());
        weapon4.setText(cardList.getWeaponCard().get(3).getName());
        weapon5.setText(cardList.getWeaponCard().get(4).getName());
        weapon5.setText(cardList.getWeaponCard().get(5).getName());
        room1.setText(cardList.getRoomCard().get(0).getName());
        room2.setText(cardList.getRoomCard().get(1).getName());
        room3.setText(cardList.getRoomCard().get(2).getName());
        room4.setText(cardList.getRoomCard().get(3).getName());
        room5.setText(cardList.getRoomCard().get(4).getName());
        room6.setText(cardList.getRoomCard().get(5).getName());
        room7.setText(cardList.getRoomCard().get(6).getName());
        room8.setText(cardList.getRoomCard().get(7).getName());

        player1.setText(cardList.getPersonCard().get(0).getName());
        player2.setText(cardList.getPersonCard().get(1).getName());
        player3.setText(cardList.getPersonCard().get(2).getName());
        player4.setText(cardList.getPersonCard().get(3).getName());
        player5.setText(cardList.getPersonCard().get(4).getName());
    }
}