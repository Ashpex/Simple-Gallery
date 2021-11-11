package com.example.testgallery.fragments.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.testgallery.activities.mainActivities.CameraActivity;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.R;
import com.example.testgallery.models.Category;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.models.Image;

public class PhotoFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private androidx.appcompat.widget.Toolbar toolbar_photo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container,false);

        recyclerView = view.findViewById(R.id.rcv_category);
        toolbar_photo = view.findViewById(R.id.toolbar_photo);

        toolBarEvents();
        setRyc();

        return view;
    }

    private void setRyc() {
        categoryAdapter = new CategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(getListCategory());
        recyclerView.setAdapter(categoryAdapter);
    }

    private void toolBarEvents() {
        toolbar_photo.inflateMenu(R.menu.menu_top);
        toolbar_photo.setTitle("Photo");
        toolbar_photo.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuSearch:
                        //eventSearch(item);
                        break;
                    case R.id.menuCamera:
//                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                        startActivity(intent);
                        Intent myIntent = new Intent(getActivity().getApplicationContext(), CameraActivity.class);
                        getActivity().startActivity(myIntent);
                        break;
                    case R.id.menuAdd:
                        Toast.makeText(getContext(),"main",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void eventSearch(@NonNull MenuItem item) {
        Toast.makeText(getContext(),"Search",Toast.LENGTH_SHORT).show();

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @NonNull
    private List<Category> getListCategory() {
        List<Category> categoryList = new ArrayList<>();
        int categoryCount = 0;
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());

        Toast.makeText(getContext(), "" + imageList.size(), Toast.LENGTH_LONG).show();

        try {
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
        } catch (Exception e){
            return null;
        }

    }

}