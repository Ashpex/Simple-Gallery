package com.example.testgallery.activities.subActivities;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.adapters.AlbumSheetAdapter;
import com.example.testgallery.adapters.ImageSelectAdapter;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.models.Album;
import com.example.testgallery.models.Category;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.utility.ListTransInterface;
import com.example.testgallery.utility.SubInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemAlbumMultiSelectActivity extends AppCompatActivity implements ListTransInterface, SubInterface {
    private ArrayList<String> myAlbum;
    private RecyclerView ryc_album;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private String album_name;
    private String path_folder;
    Toolbar toolbar_item_album;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList<Image> listImageSelected;
    private static int REQUEST_CODE_SLIDESHOW = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_album);
        intent = getIntent();
        setUpData();
        mappingControls();
        setData();
        setRyc();
        events();
    }

    private void setUpData() {
        listImageSelected = new ArrayList<>();
    }
    private void setRyc() {
        album_name = intent.getStringExtra("name_1");
        path_folder = intent.getStringExtra("path_folder");
        ryc_list_album.setLayoutManager(new GridLayoutManager(this, 3));
        ImageSelectAdapter imageSelectAdapter = new ImageSelectAdapter(ItemAlbumMultiSelectActivity.this);
        List<Image> listImg = new ArrayList<>();
        for(int i =0 ; i< myAlbum.size();i++) {
            Image img = new Image();
            img.setThumb(myAlbum.get(i));
            img.setPath(myAlbum.get(i));
            listImg.add(img);
        }
        imageSelectAdapter.setData(listImg);
        imageSelectAdapter.setListTransInterface(this);
        ryc_list_album.setAdapter(imageSelectAdapter);
    }



    private void events() {
        // Toolbar events
        toolbar_item_album.inflateMenu(R.menu.menu_top_multi_album);
        toolbar_item_album.setTitle(album_name);

        // Show back button
        toolbar_item_album.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar_item_album.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Toolbar options
        toolbar_item_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.menuMultiDelete:
                        deleteEvents();
                        break;
                    case R.id.menuSlideshow:
                        slideShowEvents();
                        break;
                    case R.id.menu_move_image:
                        moveEvent();
                        break;
                    case R.id.menuGif:
                        gifEvents();
                        break;
                }

                return true;
            }
        });
    }

    private void gifEvents() {
        Toast.makeText(getApplicationContext(),"App sẽ loại bỏ ảnh gif có trong danh sách chọn", Toast.LENGTH_SHORT).show();
        ArrayList<String> list_send_gif = new ArrayList<>();
        for(int i =0;i<listImageSelected.size();i++) {
            if(!listImageSelected.get(i).getPath().contains(".gif"))
                list_send_gif.add(listImageSelected.get(i).getPath());
        }
        if(list_send_gif.size()!=0) {
            inputDialog(list_send_gif);

        }
        else
            Toast.makeText(getApplicationContext(),"Danh sách trống", Toast.LENGTH_SHORT).show();
    }

    private void inputDialog(ArrayList<String> list_send_gif) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ItemAlbumMultiSelectActivity.this);
        alertDialog.setTitle("Nhập khoảng delay");
        alertDialog.setMessage("Delay: ");
        final EditText input = new EditText(ItemAlbumMultiSelectActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!TextUtils.isEmpty(input.getText())) {
                    Intent intent_gif = new Intent(ItemAlbumMultiSelectActivity.this, GifShowActivity.class);
                    intent_gif.putExtra("delay", Integer.valueOf(input.getText().toString()));
                    intent_gif.putStringArrayListExtra("list", list_send_gif);
                    startActivity(intent_gif);
                    dialogInterface.cancel();
                }
                else
                    Toast.makeText(getApplicationContext(),"Mời nhập đầy đủ", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    private void moveEvent() {
        openBottomDialog();
    }
    private void openBottomDialog() {
        View viewDialog = LayoutInflater.from(ItemAlbumMultiSelectActivity.this).inflate(R.layout.layout_bottom_sheet_add_to_album, null);
        ryc_album = viewDialog.findViewById(R.id.ryc_album);
        ryc_album.setLayoutManager(new GridLayoutManager(this, 2));

        bottomSheetDialog = new BottomSheetDialog(ItemAlbumMultiSelectActivity.this);
        bottomSheetDialog.setContentView(viewDialog);
        ItemAlbumMultiSelectActivity.MyAsyncTask myAsyncTask = new ItemAlbumMultiSelectActivity.MyAsyncTask();
        myAsyncTask.execute();

    }
    private void deleteEvents() {
        for(int i=0;i<listImageSelected.size();i++) {
            Uri targetUri = Uri.parse("file://" + listImageSelected.get(i).getPath());
            File file = new File(targetUri.getPath());
            if (file.exists()){
                file.delete();
            }
            if(i==listImageSelected.size()-1) {
                setResult(RESULT_OK);
                finish();
            };
        }
    }
    private void slideShowEvents() {
        Intent intent = new Intent(ItemAlbumMultiSelectActivity.this, SlideShowActivity.class);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<listImageSelected.size();i++) {
            list.add(listImageSelected.get(i).getThumb());
        }
        intent.putStringArrayListExtra("data_slide", list);
        intent.putExtra("name", "Slide Show");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, REQUEST_CODE_SLIDESHOW);
    }

    private void setData() {
        myAlbum = intent.getStringArrayListExtra("data_1");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        toolbar_item_album = findViewById(R.id.toolbar_item_album);

    }

    @Override
    public void addList(Image img) {
        listImageSelected.add(img);
    }
    public void removeList(Image img) {
        listImageSelected.remove(img);
    }

    @Override
    public void add(Album album) {
        ItemAlbumMultiSelectActivity.AddAlbumAsync addAlbumAsync = new ItemAlbumMultiSelectActivity.AddAlbumAsync();
        addAlbumAsync.setAlbum(album);
        addAlbumAsync.execute();
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        private AlbumSheetAdapter albumSheetAdapter;
        private List<Album> listAlbum;
        @Override
        protected Void doInBackground(Void... voids) {
            List<Image> listImage = GetAllPhotoFromGallery.getAllImageFromGallery(ItemAlbumMultiSelectActivity.this);
            listAlbum = getListAlbum(listImage);
            for(int i =0;i<listAlbum.size();i++) {
                if(path_folder.equals(listAlbum.get(i).getPathFolder())) {
                    listAlbum.remove(i);
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            albumSheetAdapter = new AlbumSheetAdapter(listAlbum, ItemAlbumMultiSelectActivity.this);
            albumSheetAdapter.setSubInterface(ItemAlbumMultiSelectActivity.this);
            ryc_album.setAdapter(albumSheetAdapter);
            bottomSheetDialog.show();
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
    }
    public class AddAlbumAsync extends AsyncTask<Void, Integer, Void> {
        Album album;
        ArrayList<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }
        public void setAlbum(Album album) {
            this.album = album;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] paths = new String[listImageSelected.size()];
            int i =0;
            for (Image img :listImageSelected){
                File imgFile = new File(img.getPath());
                File desImgFile = new File(album.getPathFolder(),album.getName()+"_"+imgFile.getName());
                list.add(desImgFile.getPath());
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
            bottomSheetDialog.cancel();
            Intent resultIntent = new Intent();

            resultIntent.putStringArrayListExtra("list_result", list);
            resultIntent.putExtra("move", 1);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
}
