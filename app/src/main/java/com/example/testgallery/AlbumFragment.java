package com.example.testgallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.Album.AlbumAdapter;
import com.example.testgallery.model.Album;

import java.util.ArrayList;
import java.util.List;

import category.Category;
import girl.Girl;

public class AlbumFragment extends Fragment {
    private RecyclerView ryc_album;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container,false);

        ryc_album = view.findViewById(R.id.ryc_album);

        List<Album> listAlbum = new ArrayList<>();
        listAlbum.add(new Album(R.drawable.anh1, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh2, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh3, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh4, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh5, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh6, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh7, "Bạn gái tao", 0));
        listAlbum.add(new Album(R.drawable.anh8, "Bạn gái tao", 0));
        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ryc_album.setAdapter(new AlbumAdapter(listAlbum));

        return view;
    }
}
