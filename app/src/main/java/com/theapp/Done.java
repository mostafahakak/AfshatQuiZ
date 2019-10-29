package com.theapp.afshatquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.theapp.afshatquiz.Common.Common;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Done extends AppCompatActivity {

    Button btnTry;
    TextView txtResScore,getTextResQues;
    ProgressBar progressBars;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Scores");

        txtResScore = (TextView) findViewById(R.id.txtTotalScore);
        getTextResQues = (TextView) findViewById(R.id.txtTotalQues);

        progressBars = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnTry = (Button) findViewById(R.id.btnTry);



        if (Common.isConnectingToNet(getBaseContext())) {

            btnTry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Done.this,Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });

            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                final int score = extra.getInt("SCORE");
                final int totalQue = extra.getInt("TOTAL");
                final int correctAns = extra.getInt("CORRECT");

                txtResScore.setText(String.format("Score : %d", score));
                getTextResQues.setText(String.format("Passed : %d / %d", correctAns, totalQue));

                progressBars.setMax(totalQue);
                progressBars.setVisibility(correctAns);

                question_score.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String tqs = Integer.toString(correctAns);
                        String sc = Integer.toString(score);

                        question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Name").setValue(Common.CurrentUser.getName());
                        question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Image").setValue(Common.CurrentUser.getImage());
                        question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("AllTimeQuestions").setValue(tqs);
                        question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("LastScore").setValue(sc);


                        String xss = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("PlayingTimes").getValue().toString();
                        String xs = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("AllTimeQuestions").getValue().toString();

                        int totquess = 1 + Integer.parseInt(xss);
                        int foravg = Integer.parseInt(xs);
                        int totques = correctAns + Integer.parseInt(xs);


                        if (!dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Score").exists()) {

                            question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Score").setValue(sc);
                            question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("PlayingTimes").setValue("1");

                        } else if
                                (dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Score").exists())
                        {
                            String sx = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Score").getValue().toString();
                            int sco = Integer.parseInt((sx));

                            question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("AllTimeQuestions").setValue(Integer.toString(totques));
                            question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("PlayingTimes").setValue(Integer.toString(totquess));

                            int sumofdata = foravg + totquess;
                            float avrages = foravg * 100;
                            float avrage = avrages / sumofdata;
                            String avgs = Float.toString(avrage);


                            question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("WiningRatio").setValue(avgs);


                            if (sco > score) {

                            } else if (sco < score) {
                                question_score.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).child("Score").setValue(score);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


        }

        else {

            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }
}
