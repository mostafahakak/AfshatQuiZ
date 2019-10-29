package com.theapp.afshatquiz.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

public class ChallengerViewView extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView challengenames,challengeids;
    public ImageView friendimages;

    private ItemClickListner itemClickListner;

    public ChallengerViewView(@NonNull View itemView) {
        super(itemView);

        challengenames = (TextView) itemView.findViewById(R.id.friendnames);
        challengeids = (TextView) itemView.findViewById(R.id.friendids);

        friendimages = (ImageView) itemView.findViewById(R.id.friendimages);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}
