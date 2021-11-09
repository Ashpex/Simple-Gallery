package com.example.testgallery.fragments.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container,false);

        ryc_album = view.findViewById(R.id.ryc_album);

        List<Album> listAlbum = new ArrayList<>();
        List<Image> listImg = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());
//        listImg.add(new Image(R.drawable.anh1));
//        listImg.add(new Image(R.drawable.anh2));
//        listImg.add(new Image(R.drawable.anh3));
//        listImg.add(new Image(R.drawable.anh4));
//        listImg.add(new Image(R.drawable.anh5));
//        listImg.add(new Image(R.drawable.anh7));
//        listImg.add(new Image(R.drawable.anh8));
        Random rand = new Random();
        listAlbum.add(new Album( listImg.get(rand.nextInt(listImg.size())), "Album 1", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 2", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 3", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 4", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 5", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 6", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 7", listImg));
        listAlbum.add(new Album(listImg.get(rand.nextInt(listImg.size())), "Album 8", listImg));


        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ryc_album.setAdapter(new AlbumAdapter(listAlbum, getContext()));

        return view;
    }
}
