package com.example.testgallery.fragments.mainFragments;

import android.os.Bundle;
import android.util.Log;
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
    List<Image> listImage;
    public AlbumFragment(List<Image> data) {
        this.listImage = data;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container,false);

        RecyclerView ryc_album = view.findViewById(R.id.ryc_album);

        List<Album> listAlbum = getListAlbum(listImage);

        ryc_album.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        ryc_album.setAdapter(new AlbumAdapter(listAlbum, getContext()));

        return view;
    }

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
