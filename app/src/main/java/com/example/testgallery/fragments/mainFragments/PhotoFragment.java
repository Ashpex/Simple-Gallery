package com.example.testgallery.fragments.mainFragments;

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

import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.R;
import com.example.testgallery.models.Category;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.models.Image;

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
        Log.d("test", GetAllPhotoFromGallery.getAllImageFromGallery(getContext()).get(1).getThumb());
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
        return categoryList;
    }
}