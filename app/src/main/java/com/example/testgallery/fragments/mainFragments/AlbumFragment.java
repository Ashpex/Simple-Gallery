package com.example.testgallery.fragments.mainFragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.activities.mainActivities.CreateAlbumActivity;
import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.adapters.AlbumAdapter;
import com.example.testgallery.models.Album;

import java.util.ArrayList;
import java.util.List;

import com.example.testgallery.models.Image;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class AlbumFragment extends Fragment {
    private RecyclerView ryc_album;
    private List<Image> listImage;
    private View view;
    private androidx.appcompat.widget.Toolbar toolbar_album;
    private List<Album> listAlbum;
    private LinearLayout layout_bottom;
    public AlbumFragment(List<Image> data) {
        this.listImage = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container,false);
        toolbar_album = view.findViewById(R.id.toolbar_album);
        layout_bottom = view.findViewById(R.id.layout_bottom);

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
                    case R.id.menuSlideshow:
                        Intent intentSlider = new Intent(view.getContext(), SlideShowActivity.class);
                        intentSlider.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getContext().startActivity(intentSlider);
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
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();


    }



    private void openCreateAlbumActivity() {
        Intent _intent = new Intent(view.getContext(), CreateAlbumActivity.class);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(_intent);
    }

    private void mappingControls() {
        ryc_album = view.findViewById(R.id.ryc_album);
    }

    private void setViewRyc() {
        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        AlbumAdapter albumAdapter = new AlbumAdapter(listAlbum, getContext());
        ryc_album.setAdapter(albumAdapter);
    }
    @NonNull
    private List<Album> getListAlbum(List<Image> listImage) {
        List<String> ref = new ArrayList<>();
        List<Album> listAlbum = new ArrayList<>();

        for(int i = 0; i< listImage.size();i++) {
            String[] _array = listImage.get(i).getThumb().split("/");
            String _name = _array[_array.length -2];
            if(!ref.contains(_name)) {
                ref.add(_name);
                Album token = new Album(listImage.get(i), _name);
                token.addItem(listImage.get(i));
                listAlbum.add(token);
            }
            else {
                listAlbum.get(ref.indexOf(_name)).addItem(listImage.get(i));
            }
        }

        return listAlbum;
    }
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            listAlbum = getListAlbum(listImage);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            setViewRyc();
        }
    }
}


