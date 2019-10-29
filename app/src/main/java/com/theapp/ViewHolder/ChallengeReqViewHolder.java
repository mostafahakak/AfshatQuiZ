package com.theapp.afshatquiz.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

public class ChallengeReqViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView challengerimage;
    public Button yes,no;
    public TextView challengerneme,challengerid;

    private ItemClickListner itemClickListner;


    public ChallengeReqViewHolder(@NonNull View itemView) {
        super(itemView);

        challengerimage = (ImageView) itemView.findViewById(R.id.challengerimage);

        challengerneme = (TextView) itemView.findViewById(R.id.challengername);
        challengerid = (TextView) itemView.findViewById(R.id.challengerid);


        yes = (Button) itemView.findViewById(R.id.accept);
        no = (Button) itemView.findViewById(R.id.reject);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

}
