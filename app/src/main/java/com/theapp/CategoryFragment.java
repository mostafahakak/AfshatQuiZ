package com.theapp.afshatquiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.theapp.afshatquiz.Interface.ItemClickListner;
import com.theapp.afshatquiz.Model.Category;
import com.theapp.afshatquiz.ViewHolder.CategoryViewHolder;
import com.theapp.afshatquiz.Common.Common;


public class CategoryFragment extends Fragment {

    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;

    public static CategoryFragment newInstance(){
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    myFragment = inflater.inflate(R.layout.fragment_category,container,false);

    listCategory = (RecyclerView)myFragment.findViewById(R.id.listCategory);
    listCategory.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(container.getContext());
    listCategory.setLayoutManager(layoutManager);
        if (Common.isConnectingToNet(getActivity()))
        {

            loadCategories();
        }
        else
        {
            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();

        }

    return myFragment;
    }

    private void loadCategories() {
        adapter = new FirebaseRecyclerAdapter <Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, final int position) {

                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);

                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                      Intent startgame = new Intent(getActivity(),Start.class);
                        startgame.putExtra("CategoryID",adapter.getRef(position).getKey());
                      startActivity(startgame);

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
