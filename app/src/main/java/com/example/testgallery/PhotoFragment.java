package com.example.testgallery;

import android.os.Bundle;
import android.util.Log;
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
import image.Image;

public class PhotoFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container,false);

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
        int categoryCount = 0;
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());
        categoryList.add(new Category(imageList.get(0).getDateTaken(),new ArrayList<>()));
        categoryList.get(categoryCount).addListGirl(imageList.get(0));
        for(int i=1;i<imageList.size();i++){
            if(!imageList.get(i).getDateTaken().equals(imageList.get(i-1).getDateTaken())){
                categoryList.add(new Category(imageList.get(i).getDateTaken(),new ArrayList<>()));
                categoryCount++;
            }
            categoryList.get(categoryCount).addListGirl(imageList.get(i));
        }
//        imageList.add(new Image(R.drawable.anh1));
//        imageList.add(new Image(R.drawable.anh2));
//        imageList.add(new Image(R.drawable.anh3));
//        imageList.add(new Image(R.drawable.anh4));
//        imageList.add(new Image(R.drawable.anh5));
//        imageList.add(new Image(R.drawable.anh6));
//        imageList.add(new Image(R.drawable.anh7));
//        imageList.add(new Image(R.drawable.anh8));
//        imageList.add(new Image(R.drawable.anh9));
//        imageList.add(new Image(R.drawable.anh10));
//        imageList.add(new Image(R.drawable.anh11));
//        imageList.add(new Image(R.drawable.anh12));
      //  categoryList.add(new Category("Category 1 (" + imageList.size() + " ảnh)", imageList));
//        categoryList.add(new Category("Category 2", imageList));
//        categoryList.add(new Category("Category 3", imageList));
//        categoryList.add(new Category("Category 4", imageList));
//        categoryList.add(new Category("Category 5", imageList));
//        categoryList.add(new Category("Category 6", imageList));
//        categoryList.add(new Category("Category 7", imageList));
//        categoryList.add(new Category("Category 8", imageList));
        return categoryList;
    }
}