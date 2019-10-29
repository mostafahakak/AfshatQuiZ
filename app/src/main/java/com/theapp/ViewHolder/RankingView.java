package com.theapp.afshatquiz.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

public class RankingView extends RecyclerView.ViewHolder {

    public TextView ranking_name,ranking_score;
    public ImageView ranking_image;


    public RankingView(@NonNull View itemView) {
        super(itemView);

        ranking_name = (TextView) itemView.findViewById(R.id.rankname);
        ranking_score = (TextView) itemView.findViewById(R.id.rankscore);
        ranking_image = (ImageView) itemView.findViewById(R.id.rankimage);

    }

}
