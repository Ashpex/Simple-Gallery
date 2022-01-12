package com.example.testgallery.fragments.mainFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.models.SearchRV;
import com.example.testgallery.utility.IClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private ArrayList<SearchRV> searchRVModals;
    private IClickListener iClickListener;

    public BottomSheetFragment(ArrayList<SearchRV> searchRVModals, IClickListener iClickListener) {
        this.searchRVModals = searchRVModals;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_search, null);
        bottomSheetDialog.setContentView(view);
        //RecyclerView rcvData = view.findViewById()
        return bottomSheetDialog;
    }
}
