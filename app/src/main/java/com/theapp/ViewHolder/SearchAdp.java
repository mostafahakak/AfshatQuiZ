package com.theapp.afshatquiz.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theapp.afshatquiz.Common.Common;
import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

import java.util.ArrayList;


public class SearchAdp extends RecyclerView.Adapter<SearchAdp.SearchViewHolder> {

    Context context;
    ArrayList<String> namesearch;
    ArrayList<String> idsearch;
    ArrayList<String> imagesearch;

    FirebaseDatabase database;
    DatabaseReference challengedbs;



    public class SearchViewHolder extends RecyclerView.ViewHolder
    {
        public TextView challengename,challengeid;
        public ImageView friendimage;
        public Button addfriend,challengefriend;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            challengename = (TextView) itemView.findViewById(R.id.friendname);
            challengeid = (TextView) itemView.findViewById(R.id.friendid);

            friendimage = (ImageView) itemView.findViewById(R.id.friendimage);

            addfriend = (Button) itemView.findViewById(R.id.addfriend);
            challengefriend = (Button) itemView.findViewById(R.id.challenge);

            database = FirebaseDatabase.getInstance();
            challengedbs = database.getReference("Users");

        }
    }

    public SearchAdp(Context context, ArrayList <String> namesearch, ArrayList <String> idsearch, ArrayList <String> imagesearch) {
        this.context = context;
        this.namesearch = namesearch;
        this.idsearch = idsearch;
        this.imagesearch = imagesearch;
    }

    @NonNull
    @Override
    public SearchAdp.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.challengeview,viewGroup,false);
        return new SearchAdp.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder searchViewHolder, final int i) {

        searchViewHolder.challengename.setText(namesearch.get(i));
        searchViewHolder.challengeid.setText(idsearch.get(i));
        Glide.with(context).asBitmap().load(imagesearch.get(i)).into(searchViewHolder.friendimage);

        searchViewHolder.challengefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String a = searchViewHolder.challengename.getText().toString();
                final String b = searchViewHolder.challengeid.getText().toString();

                challengedbs.child(a+b).child("Challenges").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        challengedbs.child(a+b).child("Challenges").child(Common.CurrentUser.getName()).child("name").setValue(Common.CurrentUser.getName());
                        challengedbs.child(a+b).child("Challenges").child(Common.CurrentUser.getName()).child("id").setValue(Common.CurrentUser.getUserID());
                        challengedbs.child(a+b).child("Challenges").child(Common.CurrentUser.getName()).child("image").setValue(Common.CurrentUser.getImage());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        searchViewHolder.addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String a = searchViewHolder.challengename.getText().toString();
                final String b = searchViewHolder.challengeid.getText().toString();

                challengedbs.child(a+b).child("FriendReq").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        challengedbs.child(a+b).child("FriendReq").child(Common.CurrentUser.getName()).child("name").setValue(Common.CurrentUser.getName());
                        challengedbs.child(a+b).child("FriendReq").child(Common.CurrentUser.getName()).child("id").setValue(Common.CurrentUser.getUserID());
                        challengedbs.child(a+b).child("FriendReq").child(Common.CurrentUser.getName()).child("image").setValue(Common.CurrentUser.getImage());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }



    @Override
    public int getItemCount() {
        return namesearch.size();
    }
}
