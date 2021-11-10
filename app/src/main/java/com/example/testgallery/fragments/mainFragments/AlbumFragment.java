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
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.activities.mainActivities.CreateAlbumActivity;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.R;
import com.example.testgallery.adapters.AlbumAdapter;
import com.example.testgallery.models.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.testgallery.models.Image;

public class AlbumFragment extends Fragment {
    private RecyclerView ryc_album;
    private List<Image> listImage;
    private View view;
    private androidx.appcompat.widget.Toolbar toolbar_album;

    public AlbumFragment(List<Image> data) {
        this.listImage = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container,false);
        toolbar_album = view.findViewById(R.id.toolbar_album);

        toolBarEvents();
        mappingControls();
        events();
        return view;
    }
    private void toolBarEvents() {
        toolbar_album.inflateMenu(R.menu.menu_top);
        toolbar_album.setTitle("Album");
        toolbar_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuSearch:
                        //eventSearch(item);
                        break;
                    case R.id.menuCamera:
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivity(intent);
                        break;
                    case R.id.menuAdd:
                        openCreateAlbumActivity();
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

    private void events() {
        setViewRyc();
    }


    private void openCreateAlbumActivity() {
        Intent intent = new Intent(view.getContext(), CreateAlbumActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(intent);
    }

    private void mappingControls() {
        ryc_album = view.findViewById(R.id.ryc_album);
    }

    private void setViewRyc() {
        List<Album> listAlbum = getListAlbum(listImage);

        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ryc_album.setAdapter(new AlbumAdapter(listAlbum, getContext()));
    }
    @NonNull
    private List<Album> getListAlbum(List<Image> listImage) {
        List<String> ref = new ArrayList<>();
        List<Album> listAlbum = new ArrayList<>();
        List<Image> list = new ArrayList<>();

        String[] _array = listImage.get(0).getThumb().split("/");
        String _name = _array[_array.length -2];

        ref.add(_name);
        list.add(listImage.get(0));

        for(int i = 1; i < listImage.size();i++) {

            String[] array = listImage.get(i).getThumb().split("/");
            String name = array[array.length -2];

            if(ref.contains(name)) {
                list.add(listImage.get(i));
            }
            else {
                if(list.size() !=0) {
                    Album token = new Album(list.get(0), ref.get(ref.size()-1));
                    token.addList(list);
                    listAlbum.add(token);
                }
                ref.add(name);
                list.clear();
                list.add(listImage.get(i));
            }

            if(i == listImage.size()- 1) {
                Album token = new Album(listImage.get(i-1), name);
                token.addList(list);
                listAlbum.add(token);
            }
        }
        return listAlbum;
    }
}
