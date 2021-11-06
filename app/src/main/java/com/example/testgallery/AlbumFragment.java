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

import com.example.testgallery.album.AlbumAdapter;
import com.example.testgallery.album.Album;

import java.util.ArrayList;
import java.util.List;

import image.Image;

public class AlbumFragment extends Fragment {
    private RecyclerView ryc_album;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container,false);

        ryc_album = view.findViewById(R.id.ryc_album);

        List<Album> listAlbum = new ArrayList<>();
        List<Image> listImg = new ArrayList<>();
        listImg.add(new Image(R.drawable.anh1));
        listImg.add(new Image(R.drawable.anh2));
        listImg.add(new Image(R.drawable.anh3));
        listImg.add(new Image(R.drawable.anh4));
        listImg.add(new Image(R.drawable.anh5));
        listImg.add(new Image(R.drawable.anh6));
        listImg.add(new Image(R.drawable.anh7));
        listImg.add(new Image(R.drawable.anh8));
        listAlbum.add(new Album(new Image(R.drawable.anh1), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh2), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh3), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh4), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh5), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh6), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh7), "Ex-girlfri", listImg));
        listAlbum.add(new Album(new Image(R.drawable.anh8), "Ex-girlfri", listImg));


        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ryc_album.setAdapter(new AlbumAdapter(listAlbum));

        return view;
    }
}
