package com.theapp.afshatquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Model.ChallengerName;
import com.theapp.afshatquiz.Model.Questions;
import com.theapp.afshatquiz.Model.Ranking;
import com.theapp.afshatquiz.ViewHolder.ChallengeReqViewHolder;
import com.theapp.afshatquiz.ViewHolder.RankingView;

import java.util.Collections;

public class ChallengeReq extends AppCompatActivity {

    RecyclerView challengerrecyc;
    RecyclerView.LayoutManager layoutManagers;

    FirebaseRecyclerAdapter<ChallengerName,ChallengeReqViewHolder> adapters;

    FirebaseDatabase database;
    DatabaseReference challengerDB,acceptchallengeDB,oppenentDB,questionss;

    ProgressDialog progressDialogs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_req);

        database = FirebaseDatabase.getInstance();
        questionss=database.getReference("Questions");


        challengerDB = database.getReference("Users").child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("Challenges");
        acceptchallengeDB = database.getReference("Users").child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("ActiveChallenges");
        challengerrecyc = (RecyclerView) findViewById(R.id.challengereqrecycler);
        challengerrecyc.setHasFixedSize(true);
        layoutManagers = new LinearLayoutManager(this);
        challengerrecyc.setLayoutManager(layoutManagers);

        adapters = new FirebaseRecyclerAdapter <ChallengerName, ChallengeReqViewHolder>(
                ChallengerName.class,
                R.layout.challengereqitems,
                ChallengeReqViewHolder.class,
                challengerDB
        ) {
            @Override
            protected void populateViewHolder(final ChallengeReqViewHolder viewHolder, ChallengerName model, int position) {

                final String a = model.getName();
                final String b = model.getId();
                final String c = model.getImage();

                viewHolder.challengerneme.setText(model.getName());
                viewHolder.challengerid.setText(model.getId());
                Picasso.with(getApplicationContext()).load(model.getImage()).into(viewHolder.challengerimage);


                viewHolder.yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        acceptchallengeDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(a+b).exists())
                                {
                                    Toast.makeText(ChallengeReq.this, "Sorry but There is an active challenge", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    {

                                        oppenentDB = database.getReference("Users").child(a+b).child("ActiveChallenges");

                                        acceptchallengeDB.child(a+b).child("MyName").setValue(Common.CurrentUser.getName());
                                        acceptchallengeDB.child(a+b).child("HisName").setValue(a);
                                        acceptchallengeDB.child(a+b).child("MyScore").setValue("0");
                                        acceptchallengeDB.child(a+b).child("HisScore").setValue("0");
                                        acceptchallengeDB.child(a+b).child("Wait").setValue("1");
                                        acceptchallengeDB.child(a+b).child("HisImage").setValue(c);
                                        acceptchallengeDB.child(a+b).child("MyImage").setValue(Common.CurrentUser.getImage());

                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("MyName").setValue(a);
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("HisName").setValue(Common.CurrentUser.getName());
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("MyScore").setValue("0");
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("HisScore").setValue("0");
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("Wait").setValue("0");
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("HisImage").setValue(Common.CurrentUser.getImage());;
                                        oppenentDB.child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("MyImage").setValue(c);


                                        challengerDB.child(a+b).removeValue();

                                        viewHolder.yes.setVisibility(View.INVISIBLE);
                                        viewHolder.no.setVisibility(View.INVISIBLE);

                                        progressDialogs = new ProgressDialog(ChallengeReq.this);
                                        progressDialogs.setMessage("Estana shewaya....");
                                        progressDialogs.show();
                                        progressDialogs.setCancelable(false);
                                        loadQuestionss();


                                                                           }



                                    }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };

        challengerrecyc.setAdapter(adapters);


    }

    private void loadQuestionss() {

        if (Common.questionsList.size() > 0)
            Common.questionsList.clear();

        questionss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Questions ques = postSnapshot.getValue(Questions.class);
                    Common.questionsList.add(ques);
                }

                Intent start = new Intent(ChallengeReq.this,ChallengeRoom.class);

                progressDialogs.dismiss();
                startActivity(start);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Random List
        Collections.shuffle(Common.questionsList);

    }

}
