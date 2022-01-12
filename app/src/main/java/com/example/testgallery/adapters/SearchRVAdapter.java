package com.example.testgallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.models.SearchRV;
import com.example.testgallery.utility.IClickListener;

import java.util.ArrayList;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.ViewHolder> {

    // arraylist for storing our data and context
    private ArrayList<SearchRV> searchRVModals;
    private Context context;
    private IClickListener iClickListener;

    // constructor for our variables.


    public SearchRVAdapter(ArrayList<SearchRV> searchRVModals,Context context, IClickListener iClickListener) {
        this.searchRVModals = searchRVModals;
        this.iClickListener = iClickListener;
        this.context = context;
    }

    public SearchRVAdapter(ArrayList<SearchRV> dataModalArrayList, Context context) {
        this.searchRVModals = dataModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inside on create view holder method we are inflating our layout file which we created.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // inside on bind view holder method we are setting
        // data to each item of recycler view.
        SearchRV modal = searchRVModals.get(position);
        holder.titleTV.setText(modal.getTitle());
        holder.snippetTV.setText(modal.getDisplayed_link());
        holder.descTV.setText(modal.getSnippet());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a link in your browser.
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse(modal.getLink()));
                //context.startActivity(intent);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(modal.getLink()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        if( searchRVModals != null){
            return searchRVModals.size();
        }
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text view.
        private TextView titleTV, descTV, snippetTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            titleTV = itemView.findViewById(R.id.idTVTitle);
            descTV = itemView.findViewById(R.id.idTVDescription);
            snippetTV = itemView.findViewById(R.id.idTVSnippet);

        }
    }
}

