package com.theapp.afshatquiz.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category_name;
    public ImageView category_image;

    private ItemClickListner itemClickListner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        category_name = (TextView) itemView.findViewById(R.id.category_name);
        category_image = (ImageView) itemView.findViewById(R.id.category_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}
