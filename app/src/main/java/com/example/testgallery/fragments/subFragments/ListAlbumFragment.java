package com.example.testgallery.fragments.subFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.models.Album;
import com.example.testgallery.adapters.ListAlbumAdapter;


public class ListAlbumFragment extends Fragment {
    private Album myAlbum;
    private RecyclerView ryc_album;
    private TextView txtName_list_album;
    private ImageButton btnBack;
    private RecyclerView ryc_list_album;
    public ListAlbumFragment(Album album) {
        myAlbum = album;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_album, container,false);
        ryc_list_album = view.findViewById(R.id.ryc_list_album);
        btnBack = view.findViewById(R.id.btnBack);
        txtName_list_album = view.findViewById(R.id.txtName_list_album);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closefragment();
            }
        });
        txtName_list_album.setText(myAlbum.getName());
        ryc_list_album.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        ryc_list_album.setAdapter(new ListAlbumAdapter(myAlbum));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.anim_open_fragment, R.anim.anim_close_fragment).remove(this).commit();
    }
}
