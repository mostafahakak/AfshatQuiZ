package com.theapp.afshatquiz;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.theapp.afshatquiz.Model.Ranking;
import com.theapp.afshatquiz.ViewHolder.RankingView;


public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView RankRecy;
    RecyclerView.LayoutManager layoutManagers;

    FirebaseRecyclerAdapter<Ranking,RankingView> adapters;

    FirebaseDatabase scoredatabase;
    DatabaseReference score;


    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoredatabase = FirebaseDatabase.getInstance();
        score = scoredatabase.getReference("Scores");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking,container,false);

        RankRecy = (RecyclerView) myFragment.findViewById(R.id.rankingRecy);
        RankRecy.setHasFixedSize(true);
        layoutManagers = new LinearLayoutManager(container.getContext());
        ((LinearLayoutManager) layoutManagers).setReverseLayout(true);
        ((LinearLayoutManager) layoutManagers).setStackFromEnd(true);

        RankRecy.setLayoutManager(layoutManagers);
        upDateScore();

        return myFragment;
    }

    private void upDateScore() {

    adapters = new FirebaseRecyclerAdapter <Ranking, RankingView>(
            Ranking.class,
            R.layout.rankinglayout,
            RankingView.class,
            score.orderByChild("Score").limitToLast(10)) {
        @Override
        protected void populateViewHolder(RankingView viewHolder, Ranking model, int position) {

            viewHolder.ranking_name.setText(model.getName());
            Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.ranking_image);
            viewHolder.ranking_score.setText(String.valueOf(model.getScore()));

        }

    };
        RankRecy.setAdapter(adapters);
    }

}
