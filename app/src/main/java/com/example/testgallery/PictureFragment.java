package com.example.testgallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import category.Category;
import category.CategoryAdapter;
import girl.Girl;

public class PictureFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container,false);

        recyclerView = view.findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(getListCategory());
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }

    private List<Category> getListCategory() {
        List<Category> categoryList = new ArrayList<>();

        List<Girl> girlList = new ArrayList<>();
        girlList.add(new Girl(R.drawable.anh1));
        girlList.add(new Girl(R.drawable.anh2));
        girlList.add(new Girl(R.drawable.anh3));
        girlList.add(new Girl(R.drawable.anh4));
        girlList.add(new Girl(R.drawable.anh5));
        girlList.add(new Girl(R.drawable.anh6));
        girlList.add(new Girl(R.drawable.anh7));
        girlList.add(new Girl(R.drawable.anh8));
        girlList.add(new Girl(R.drawable.anh9));
        girlList.add(new Girl(R.drawable.anh10));
        girlList.add(new Girl(R.drawable.anh11));
        girlList.add(new Girl(R.drawable.anh12));

        categoryList.add(new Category("Category 1", girlList));
        categoryList.add(new Category("Category 2", girlList));
        categoryList.add(new Category("Category 3", girlList));
        categoryList.add(new Category("Category 4", girlList));
        categoryList.add(new Category("Category 5", girlList));
        categoryList.add(new Category("Category 6", girlList));
        categoryList.add(new Category("Category 7", girlList));
        categoryList.add(new Category("Category 8", girlList));
        return categoryList;
    }
}