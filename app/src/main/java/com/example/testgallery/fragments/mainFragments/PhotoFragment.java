package com.example.testgallery.fragments.mainFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;
import com.example.testgallery.ml.MobilenetV110224Quant;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.example.testgallery.R;
import com.example.testgallery.models.Category;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.models.Image;
import com.google.android.material.textfield.TextInputLayout;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class PhotoFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private androidx.appcompat.widget.Toolbar toolbar_photo;
    private Boolean flag = false;
    private List<Category> listImg;
    private List<Image> imageList;
    private List<String> listLabel;
    private ArrayList<String> list_searchA;
    private ArrayList<String> imageListPath;

    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container,false);
        context = view.getContext();
        setUpListLabel(view.getContext());
        recyclerView = view.findViewById(R.id.rcv_category);
        toolbar_photo = view.findViewById(R.id.toolbar_photo);
        toolBarEvents();
        setRyc();

        return view;
    }

    private void setUpListLabel(Context context) {
        list_searchA = new ArrayList<>();
        try {
            listLabel = new ArrayList<>();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("label.txt")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                listLabel.add(line.toUpperCase());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRyc() {
        categoryAdapter = new CategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(getListCategory());
        recyclerView.setAdapter(categoryAdapter);

    }

    private void toolBarEvents() {
        toolbar_photo.inflateMenu(R.menu.menu_top);
        toolbar_photo.setTitle("Photo");
        toolbar_photo.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuSearch:
                        //eventSearch(item);
                        break;
                    case R.id.menuCamera:
                        takenImg();
                        break;
                    case R.id.menuAdd:
                        Toast.makeText(getContext(),"main",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSlideshow:
                        //slideShowEvents();
                        break;
                    case R.id.menuSearch_Advanced:
                        actionSearchAdvanced();
                        break;
                }
                return true;
            }
        });
    }

    private void actionSearchAdvanced() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_search_advanced, null);

        dialog.setView(view);
            dialog.setTitle("Advanced search");
        dialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText edt_search_view = view.findViewById(R.id.edt_search_view);
                //TextInputLayout searchViewField = view.findViewById(R.id.searchViewField);
                //searchViewField.setError("Vui lòng nhập từ khóa để tìm kiếm");
                LabelAsyncTask labelAsyncTask = new LabelAsyncTask();
                labelAsyncTask.setTitle(edt_search_view.getText().toString());
                labelAsyncTask.execute();
                dialogInterface.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        flag = true;
    }

    //Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICTURE_RESULT = 1;
    private Uri imageUri;
    private String imageurl;
    private Bitmap thumbnail;
    private void takenImg(){
        int permissionCheckStorage = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (permissionCheckStorage != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getActivity().getApplicationContext().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PICTURE_RESULT);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case PICTURE_RESULT:
                if (requestCode == PICTURE_RESULT){
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            thumbnail = MediaStore.Images.Media.getBitmap(
                                    getActivity().getApplicationContext().getContentResolver(), imageUri);

                            imageurl = getRealPathFromURI(imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private void eventSearch(@NonNull MenuItem item) {
        Toast.makeText(getContext(),"Search",Toast.LENGTH_SHORT).show();

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @NonNull
    private List<Category> getListCategory() {
        List<Category> categoryList = new ArrayList<>();
        int categoryCount = 0;
        imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());

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
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listImg = getListCategory();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            categoryAdapter.setData(listImg);
        }
    }

    private void slideShowEvents() {

        Intent intent = new Intent(getContext(), SlideShowActivity.class);
        imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());
        for(int i = 0; i < imageList.size(); i++){
            imageListPath.add((imageList.get(i)).getPath());
        }
        intent.putStringArrayListExtra("data_slide", imageListPath);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getView().getContext().startActivity(intent);
    }

    public class LabelAsyncTask extends AsyncTask<Void, Integer, Void> {
        private String title;
        private ProgressDialog mProgressDialog ;
        public void setTitle(String title) {
            this.title = title.toUpperCase();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(context);
            list_searchA.clear();
            for(int i =0;i<imageList.size();i++) {
                Bitmap bitmap = getBitmap(imageList.get(i).getPath());
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 224, 224,true);
                try {
                    MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(context);
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
                    ByteBuffer byteBuffer = TensorImage.fromBitmap(resized).getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);
                    TensorBuffer outputFeature0 = model.process(inputFeature0).getOutputFeature0AsTensorBuffer();
                    int max = getMax(outputFeature0.getFloatArray());
                    if(listLabel.get(max).toUpperCase().contains(title))
                    list_searchA.add(imageList.get(i).getPath());
                    model.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private int getMax(float[] arr) {
            int ind = 0;
            float min = 0.0f;
            for(int i = 0;i<1001;i++) {
                if(arr[i] > min) {
                    ind = i;
                    min = arr[i];
                }
            }
            return ind;
        }
        public Bitmap getBitmap(String path) {
            Bitmap bitmap=null;
            try {
                File f= new File(path);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap ;
        }


        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            mProgressDialog.cancel();
            Intent intent = new Intent(context, ItemAlbumActivity.class);
            intent.putStringArrayListExtra("data", list_searchA);
            intent.putExtra("name", title);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Loading, please wait...");
            mProgressDialog.show();
        }
    }
}