package com.theapp.afshatquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Model.Questions;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button btnPlay;

    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database = FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
        btnPlay = (Button) findViewById(R.id.btnplay);

        final String categoryIds = getIntent().getStringExtra("CategoryID");
        Common.CategoryId = categoryIds;


        if (Common.isConnectingToNet(getBaseContext())) {


            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(Start.this);
                    progressDialog.setMessage("Estana shewaya....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    loadQuestions(Common.CategoryId);

                }
            });
        }
        else {

            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void loadQuestions(String categoryId) {

        if (Common.questionsList.size() > 0)
            Common.questionsList.clear();

        questions.orderByChild("CategoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Questions ques = postSnapshot.getValue(Questions.class);
                    Common.questionsList.add(ques);
                }

                Intent start = new Intent(Start.this,Playing.class);
                final String categoryIdss = getIntent().getStringExtra("CategoryID");
                start.putExtra("CategoryId",categoryIdss);


                progressDialog.dismiss();
                startActivity(start);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Random List
        Collections.shuffle(Common.questionsList);
    }
}
