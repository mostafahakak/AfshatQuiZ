package com.theapp.afshatquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Model.Category;
import com.theapp.afshatquiz.Model.Ranking;
import com.theapp.afshatquiz.Model.User;
import com.theapp.afshatquiz.ViewHolder.CategoryViewHolder;
import com.theapp.afshatquiz.ViewHolder.ChallengeViewHolder;
import com.theapp.afshatquiz.ViewHolder.RankingView;
import com.theapp.afshatquiz.ViewHolder.SearchAdp;

import java.util.ArrayList;

public class ChallengeFragment extends Fragment {

    View myFragment;

    SearchAdp searchAdp;

    Button challengereq,addreq,activecha;

    RecyclerView challengeRecycler;
    RecyclerView.LayoutManager layoutManager;

    MaterialSearchBar search_edit_text;
    ArrayList<String> namesearch;
    ArrayList<String> idsearch;
    ArrayList<String> imagesearch;


    FirebaseRecyclerAdapter<User,ChallengeViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference challengedb;

    public static ChallengeFragment newInstance(){
        ChallengeFragment challengeFragment = new ChallengeFragment();
        return challengeFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        challengedb = database.getReference("Users");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.challengefragment,container,false);


        challengeRecycler = (RecyclerView) myFragment.findViewById(R.id.challengerecycler);
        challengeRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        challengeRecycler.setLayoutManager(layoutManager);

        namesearch = new ArrayList <>();
        idsearch = new ArrayList <>();
        imagesearch = new ArrayList <>();

        challengereq = (Button) myFragment.findViewById(R.id.challengebtn);
        addreq = (Button) myFragment.findViewById(R.id.addbtn);
        activecha = (Button) myFragment.findViewById(R.id.activechallenges);


        search_edit_text = (MaterialSearchBar) myFragment.findViewById(R.id.searchedit);
        search_edit_text.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty())
                {
                    setAdapter(s.toString());
                }
                else
                {
                    namesearch.clear();
                    idsearch.clear();
                    imagesearch.clear();
                    challengeRecycler.removeAllViews();

                }
            }
        });

        if (Common.isConnectingToNet(getActivity()))
        {

            challengereq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startgame = new Intent(getActivity(),ChallengeReq.class);
                    startActivity(startgame);                }
            });

            addreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startgame = new Intent(getActivity(),ChallengeReq.class);
                    startActivity(startgame);

                    activecha.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent startgame = new Intent(getActivity(),ChallengeReq.class);
                            startActivity(startgame);
                        }
                    });
                }
            });
        }
        else
        {
            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }

        return myFragment;
    }

    private void setAdapter(final String searchedString) {

        challengedb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                namesearch.clear();
                idsearch.clear();
                imagesearch.clear();
                challengeRecycler.removeAllViews();

                int counters = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren() )
                {
                    String name = snapshot.child("name").getValue(String.class);
                    String id = snapshot.child("userID").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);

                    if (name.toLowerCase().contains(searchedString.toLowerCase()))
                    {
                        namesearch.add(name);
                        idsearch.add(id);
                        imagesearch.add(image);
                        counters++;
                    }
                    else
                    {

                    }
                }

                searchAdp = new SearchAdp(getContext(),namesearch,idsearch,imagesearch);
                challengeRecycler.setAdapter(searchAdp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
