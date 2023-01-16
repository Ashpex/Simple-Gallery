package com.example.testgallery.fragments.mainFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.activities.mainActivities.CreateAlbumActivity;
import com.example.testgallery.R;
import com.example.testgallery.adapters.AlbumAdapter;
import com.example.testgallery.models.Album;

import java.util.ArrayList;
import java.util.List;

import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;


public class AlbumFragment extends Fragment {
    private RecyclerView ryc_album;
    private List<Image> listImage;
    private View view;
    private androidx.appcompat.widget.Toolbar toolbar_album;
    private List<Album> listAlbum;
    private LinearLayout layout_bottom;
    private AlbumAdapter albumAdapter;
    private ProgressDialog progressDialog;
    private static int REQUEST_CODE_CREATE = 100;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        listImage = GetAllPhotoFromGallery.getAllImageFromGallery(view.getContext());
        toolbar_album = view.findViewById(R.id.toolbar_album);
        layout_bottom = view.findViewById(R.id.layout_bottom);
        progressDialog = new ProgressDialog(getContext());

        toolBarEvents();
        mappingControls();
        eventsUpdateAlbum();
        setViewRyc();
        albumAdapter.setData(listAlbum);

        return view;
    }

    private void toolBarEvents() {
        toolbar_album.inflateMenu(R.menu.menu_top_album);
        toolbar_album.setTitle(getContext().getResources().getString(R.string.album));
        toolbar_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menuSearch:
                        eventSearch(item);
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
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<Album> lisAlbumSearch = new ArrayList<>();

                for (Album album : listAlbum) {
                    if (album.getName().toLowerCase().contains(s)) {
                        lisAlbumSearch.add(album);
                    }
                }

                if (lisAlbumSearch.size() != 0) {
                    albumAdapter.setData(lisAlbumSearch);
                    synchronized (AlbumFragment.this) {
                        AlbumFragment.this.notifyAll();
                    }
                } else {
                    Toast.makeText(getContext(), "Searched album not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                albumAdapter.setData(listAlbum);
                synchronized (AlbumFragment.this) {
                    AlbumFragment.this.notifyAll();
                }
                return true;
            }
        });

    }

    private void eventsUpdateAlbum() {
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void openCreateAlbumActivity() {
        Intent _intent = new Intent(view.getContext(), CreateAlbumActivity.class);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) view.getContext()).startActivityForResult(_intent, REQUEST_CODE_CREATE);
    }

    private void mappingControls() {
        ryc_album = view.findViewById(R.id.ryc_album);
    }

    private void setViewRyc() {
        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        albumAdapter = new AlbumAdapter(listAlbum, getContext());
        ryc_album.setAdapter(albumAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CREATE && resultCode == Activity.RESULT_OK) {
            eventsUpdateAlbum();
        }
    }

    @NonNull
    private List<Album> getListAlbum(List<Image> listImage) {
        List<String> ref = new ArrayList<>();
        List<Album> listAlbum = new ArrayList<>();

        for (int i = 0; i < listImage.size(); i++) {
            String[] _array = listImage.get(i).getThumb().split("/");
            String _pathFolder = listImage.get(i).getThumb().substring(0, listImage.get(i).getThumb().lastIndexOf("/"));
            String _name = _array[_array.length - 2];
            if (!ref.contains(_pathFolder)) {
                ref.add(_pathFolder);
                Album token = new Album(listImage.get(i), _name);
                token.setPathFolder(_pathFolder);
                token.addItem(listImage.get(i));
                listAlbum.add(token);
            } else {
                listAlbum.get(ref.indexOf(_pathFolder)).addItem(listImage.get(i));
            }
        }
        return listAlbum;
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listImage = GetAllPhotoFromGallery.getAllImageFromGallery(view.getContext());
            listAlbum = getListAlbum(listImage);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            albumAdapter.setData(listAlbum);
            progressDialog.cancel();
        }
    }
}


