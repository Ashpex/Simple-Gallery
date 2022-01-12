package com.example.testgallery.fragments.mainFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.SearchRVAdapter;
import com.example.testgallery.models.SearchRV;
import com.example.testgallery.utility.IClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private ArrayList<SearchRV> searchRVArrayList;
    private IClickListener iClickListener;

    public BottomSheetFragment(ArrayList<SearchRV> searchRVArrayList, IClickListener iClickListener) {
        this.searchRVArrayList = searchRVArrayList;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_search, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView rcvSearch = view.findViewById(R.id.rcv_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSearch.setLayoutManager(linearLayoutManager);
        SearchRVAdapter searchRVAdapter = new SearchRVAdapter(searchRVArrayList, getContext(), new IClickListener() {
            @Override
            public void clickItem(SearchRV searchRV) {
                iClickListener.clickItem(searchRV);
            }
        });

        rcvSearch.setAdapter(searchRVAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);
        rcvSearch.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }
}
