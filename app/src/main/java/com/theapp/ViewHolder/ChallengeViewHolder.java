package com.theapp.afshatquiz.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

public class ChallengeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView challengename,challengeid;
    public ImageView friendimage;
    public Button addfriend,challengefriend;



    private ItemClickListner itemClickListner;

    public ChallengeViewHolder(@NonNull View itemView) {
        super(itemView);



        challengename = (TextView) itemView.findViewById(R.id.friendname);
        challengeid = (TextView) itemView.findViewById(R.id.friendid);

        friendimage = (ImageView) itemView.findViewById(R.id.friendimage);

        addfriend = (Button) itemView.findViewById(R.id.addfriend);
        challengefriend = (Button) itemView.findViewById(R.id.challenge);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}
