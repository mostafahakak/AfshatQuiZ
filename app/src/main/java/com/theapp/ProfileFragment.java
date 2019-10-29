package com.theapp.afshatquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.theapp.afshatquiz.Common.Common;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    View myFragment;

    ImageView profimage;
    TextView txtpfname,txtpfid,txtpfscore,txtpfus,txtpflstscore,txtnrq,txtnps,txtpercentage;
    Button clickme;
    FirebaseDatabase database;
    DatabaseReference profiledata;



    public static ProfileFragment newInstance(){
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_profile,container,false);

        database = FirebaseDatabase.getInstance();
        profiledata = database.getReference("Scores");

        profimage = (ImageView) myFragment.findViewById(R.id.profileimage);
        txtpfname = (TextView) myFragment.findViewById(R.id.txtname);
        txtpfid = (TextView) myFragment.findViewById(R.id.txtid);
        txtpfscore = (TextView) myFragment.findViewById(R.id.txtscores);
        txtpfus = (TextView) myFragment.findViewById(R.id.txtust);
        txtpflstscore = (TextView) myFragment.findViewById(R.id.lasttimescore);
        txtnrq = (TextView) myFragment.findViewById(R.id.alltimescore);
        txtnps = (TextView) myFragment.findViewById(R.id.gamesnumber);
        txtpercentage = (TextView) myFragment.findViewById(R.id.txtperc);

        clickme = (Button) myFragment.findViewById(R.id.clickme);

        Picasso.with(getActivity()).load(Common.CurrentUser.getImage()).into(profimage);


        if (Common.isConnectingToNet(getActivity())) {

            txtpfname.setText(getText(R.string.name) + "" + Common.CurrentUser.getName());
            txtpfid.setText(getText(R.string.id) + "" + Common.CurrentUser.getUserID());
            txtpfus.setText(R.string.userstat);


            profiledata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String LS = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).
                            child("LastScore").getValue().toString();
                    txtpflstscore.setText(getText(R.string.lastscore) + "" + LS);

                    String RQS = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).
                            child("AllTimeQuestions").getValue().toString();
                    txtnrq.setText(getText(R.string.rightques) + "" + RQS);

                    String SCRE = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).
                            child("Score").getValue().toString();
                    txtpfscore.setText(getText(R.string.bestscore) + "" + SCRE);

                    String PTS = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).
                            child("PlayingTimes").getValue().toString();
                    txtnps.setText(getText(R.string.gamesplayed) + "" + PTS);


                    String WPS = dataSnapshot.child(Common.CurrentUser.getName() + Common.CurrentUser.getUserID()).
                            child("WiningRatio").getValue().toString();
                    txtpercentage.setText(getText(R.string.winperc) + "" + WPS);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            clickme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startgame = new Intent(getActivity(),ChallengeReq.class);
                    startActivity(startgame);
                }
            });

        }
        else {
            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }
        return myFragment;
    }
}
