package com.example.testgallery.activities.mainActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testgallery.R;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.adapters.CategoryMultiAdapter;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.models.Album;
import com.example.testgallery.models.Category;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.utility.ListTransInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiSelectImage extends AppCompatActivity implements ListTransInterface {
    private RecyclerView ryc_list_album;
    private Toolbar toolbar_item_album;
    private List<Category> listImg;
    private List<Image> imageList;
    private CategoryMultiAdapter categoryMultiAdapter;
    private ArrayList<Image> listImageSelected;
    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_image);
        setUpData();
        mappingControls();
        addEvents();
    }

    private void setUpData() {
        listImageSelected = new ArrayList<>();
    }

    private void addEvents() {
        setRyc();
        // Toolbar events
        eventToolBar();
    }

    private void setRyc() {
        categoryMultiAdapter = new CategoryMultiAdapter(MultiSelectImage.this);
        categoryMultiAdapter.setListTransInterface(MultiSelectImage.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MultiSelectImage.this, RecyclerView.VERTICAL, false);
        ryc_list_album.setLayoutManager(linearLayoutManager);

        //Set adapter
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void eventToolBar() {
        toolbar_item_album.inflateMenu(R.menu.menu_multi_select);
        toolbar_item_album.setTitle("Multi Select");

        toolbar_item_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuCreateAlbum:
                        AlertDialog.Builder alert = new AlertDialog.Builder(MultiSelectImage.this);
                        edittext = new EditText(MultiSelectImage.this);
                        alert.setMessage("Enter name album");
                        alert.setView(edittext);
                        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(!TextUtils.isEmpty(edittext.getText())) {
                                    MultiSelectImage.CreateAlbumAsyncTask createAlbumAsyncTask = new MultiSelectImage.CreateAlbumAsyncTask();
                                    createAlbumAsyncTask.execute();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Title null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alert.show();

                        break;
                    case R.id.menuMultiDelete:
                        deleteEvents();
                        break;
                    case R.id.menuSlideshow:
                        slideShowEvents();
                        break;
                }
                return true;
            }
        });
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        toolbar_item_album = findViewById(R.id.toolbar_item_album);
    }

    private List<Category> getListCategory() {
        List<Category> categoryList = new ArrayList<>();
        int categoryCount = 0;
        imageList = GetAllPhotoFromGallery.getAllImageFromGallery(MultiSelectImage.this);

        try {
            categoryList.add(new Category(imageList.get(0).getDateTaken(),new ArrayList<>()));
            categoryList.get(categoryCount).addListGirl(imageList.get(0));
            for(int i=1;i<imageList.size();i++){
                if(!imageList.get(i).getDateTaken().equals(imageList.get(i-1).getDateTaken())){
                    categoryList.add(new Category(imageList.get(i).getDateTaken(),new ArrayList<>()));
                    categoryCount++;
                }
                categoryList.get(categoryCount).addListGirl(imageList.get(i));
            }
            return categoryList;
        } catch (Exception e){
            return null;
        }

    }
    private void deleteEvents() {
        for(int i=0;i<listImageSelected.size();i++) {
            Uri targetUri = Uri.parse("file://" + listImageSelected.get(i).getPath());
            File file = new File(targetUri.getPath());
            if (file.exists()){
                file.delete();
            }
            if(i==listImageSelected.size()-1) {
                finish();
            };
        }
    }
    private void slideShowEvents() {
        Intent intent = new Intent(MultiSelectImage.this, SlideShowActivity.class);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<listImageSelected.size();i++) {
            list.add(listImageSelected.get(i).getThumb());
        }
        intent.putStringArrayListExtra("data_slide", list);
        intent.putExtra("name", "Slide Show");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void addList(Image img) {
        listImageSelected.add(img);
    }
    public void removeList(Image img) {
        listImageSelected.remove(img);
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listImg = getListCategory();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            categoryMultiAdapter.setData(listImg);
            ryc_list_album.setAdapter(categoryMultiAdapter);
        }
    }
    public class CreateAlbumAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String albumName = edittext.getText().toString();
            String albumPath = Environment.getExternalStorageDirectory()+ File.separator+"Pictures" + File.separator +albumName;
            File directtory = new File(albumPath);
            if(!directtory.exists()){
                directtory.mkdirs();
                Log.e("File-no-exist",directtory.getPath());
            }
            String[] paths = new String[listImageSelected.size()];
            int i =0;
            for (Image img :listImageSelected){
                File imgFile = new File(img.getPath());
                File desImgFile = new File(albumPath,albumName+"_"+imgFile.getName());
                imgFile.renameTo(desImgFile);
                imgFile.deleteOnExit();
                paths[i] = desImgFile.getPath();
                i++;
            }
            MediaScannerConnection.scanFile(getApplicationContext(),paths, null, null);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            finish();
        }
    }
}