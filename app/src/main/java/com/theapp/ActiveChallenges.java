package com.theapp.afshatquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Model.ChallengeInfo;
import com.theapp.afshatquiz.Model.ChallengerName;
import com.theapp.afshatquiz.ViewHolder.ChallengeReqViewHolder;
import com.theapp.afshatquiz.ViewHolder.ChallengerViewView;

public class ActiveChallenges extends AppCompatActivity {

    RecyclerView Activechallengerrecyc;
    RecyclerView.LayoutManager layoutManagers;

    FirebaseRecyclerAdapter<ChallengeInfo,ChallengerViewView> adapters;

    FirebaseDatabase database;
    DatabaseReference ActivechallengerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_challenges);

        database = FirebaseDatabase.getInstance();
        ActivechallengerDB = database.getReference("Users").child(Common.CurrentUser.getName()+Common.CurrentUser.getUserID()).child("ActiveChallenges");

        Activechallengerrecyc = (RecyclerView) findViewById(R.id.activechallengesrcy);

        Activechallengerrecyc.setHasFixedSize(true);
        layoutManagers = new LinearLayoutManager(this);
        Activechallengerrecyc.setLayoutManager(layoutManagers);

        adapters = new FirebaseRecyclerAdapter <ChallengeInfo, ChallengerViewView>(ChallengeInfo.class,
                                                                                        R.layout.challengeview,
                                                                                        ChallengerViewView.class,
                                                                                        ActivechallengerDB) {
            @Override
            protected void populateViewHolder(ChallengerViewView viewHolder, ChallengeInfo model, int position) {


            }
        };

    }
}
