package com.theapp.afshatquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theapp.afshatquiz.Common.Common;

import java.util.Random;

public class ChallengeRoom extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000; //1SEC
    final static long TIMEOUT = 20000; //20SEC
    int ProgressValve = 0;

    // Ads View
    private AdView mAdView;

    int Count = Common.questionsList.size();
    int randomNumber = 0;


    CountDownTimer mCounter;

    int index=0,score=0,thisQuestion,totalQuestion,correctAnswer;

    //FireBase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar progressBars;
    Button btnA,btnB,btnC,btnD;
    TextView challenge_question_text,myName,HisName,myScore,hisScore;
    ProgressDialog progressDialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_room);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        if (randomNumber < 0)
        {
            randomNumber = 1;

        }

        challenge_question_text = (TextView) findViewById(R.id.question_text);
        myName = (TextView) findViewById(R.id.playerone);
        myScore = (TextView) findViewById(R.id.myscore);
        hisScore = (TextView) findViewById(R.id.hisscore);
        HisName = (TextView) findViewById(R.id.playertwo);


        progressBars = (ProgressBar) findViewById(R.id.progressBar);

        btnA = (Button) findViewById(R.id.btnAnswera);
        btnB = (Button) findViewById(R.id.btnAnswerb);
        btnC = (Button) findViewById(R.id.btnAnswerc);
        btnD = (Button) findViewById(R.id.btnAnswerd);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        // Ads View
        MobileAds.initialize(this,
                             "ca-app-pub-1316548528485611/7976464347");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





    }

    @Override
    public void onClick(View v) {

        mCounter.cancel();
        Button ClickButton = (Button)v;


        if (ClickButton.getText().equals(Common.questionsList.get(randomNumber).getCorrectAnswer()))
        {
            score+=10;
            correctAnswer++;
            index++;

            randomNumber = new Random().nextInt(Count);

            if (randomNumber < 0)
            {
                randomNumber = 1;
                ShowQuestion(randomNumber);

            }
            else
            {
                ShowQuestion(randomNumber);
            }
        }

        else
        {

            progressDialogs = new ProgressDialog(getApplicationContext());
            progressDialogs.setMessage("Estana shewaya....");
            progressDialogs.show();
            progressDialogs.setCancelable(false);
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            progressDialogs.dismiss();
            startActivity(intent);
            finish();
        }

    }

    private void ShowQuestion(int randomNumber) {
        if (index <totalQuestion)
        {
            thisQuestion++;
            progressBars.setProgress(0);
            ProgressValve=0;


            challenge_question_text.setText(Common.questionsList.get(randomNumber).getQuestion());
            challenge_question_text.setVisibility(View.VISIBLE);


            btnA.setText(Common.questionsList.get(randomNumber).getAnswerA());
            btnB.setText(Common.questionsList.get(randomNumber).getAnswerB());
            btnC.setText(Common.questionsList.get(randomNumber).getAnswerC());
            btnD.setText(Common.questionsList.get(randomNumber).getAnswerD());

            mCounter.start(); // Start Timer
        }

        else
        {
            // If final Question
            Intent intent = new Intent(this,Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }


}
