package com.example.testgallery.activities.mainActivities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.ParcelFileDescriptor;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.bumptech.glide.Glide;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.example.testgallery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;




public class PictureActivity extends AppCompatActivity {
    ImageView imageView;


    Toolbar toolbar_picture;
    BottomNavigationView bottomNavigationView;
    String urlImg;
    String imgPath;
    String imageName;
    String thumb;

    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        viewMapping();

        Intent intent = getIntent();
        thumb = intent.getStringExtra("imgSrc");
        // Get image name
        imageName = thumb.substring(thumb.lastIndexOf('/') + 1);
        Glide.with(this).load(thumb).into(imageView);
        urlImg=thumb;
        imgPath = intent.getStringExtra("imgPath");

        // Toolbar events
        toolbar_picture.inflateMenu(R.menu.menu_top_picture);
        toolbar_picture.setTitle(imageName);

        // Show back button
        toolbar_picture.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar_picture.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Show info
        toolbar_picture.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.menuInfo:
                        Uri targetUri = Uri.parse("file://" + urlImg);
                        if(targetUri != null){
                            showExif(targetUri);
                        }
                        break;
                }

                return true;
            }
        });


//        imageView.setImageResource(src);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Uri targetUri = Uri.parse("file://" + urlImg);
                switch (item.getItemId()) {

                    case R.id.sharePic:
                        Drawable mDrawable = imageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                        Uri uri = Uri.parse("file://" + path);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(shareIntent, "Share Image"));
                        break;

                    case R.id.editPic:
                        Intent editIntent = new Intent(PictureActivity.this, DsPhotoEditorActivity.class);

                        // Set data
                        editIntent.setData(targetUri);

                        // Set output directory
                        editIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,"EditedImages");
                        // Set toolbar color
                        editIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#FF000000"));
                        // Set background color
                        editIntent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, Color.parseColor("#FF000000"));
                        // Start activity
                        startActivity(editIntent);

                        break;

                    case R.id.starPic:
                        Toast.makeText(PictureActivity.this, "Thêm ảnh yêu thích", Toast.LENGTH_SHORT).show();


                        break;

                    case R.id.deletePic: //4
                        File file = new File(targetUri.getPath());

                        if (file.exists()){
                            file.delete();
                            if(file.delete()){
                                Toast.makeText(PictureActivity.this, "Xóa thành công: " + targetUri.getPath(), Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(PictureActivity.this, "Xóa không thành công: " + targetUri.getPath(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        break;


                }
                return true;
            }

        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag){
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                    toolbar_picture.setVisibility(View.INVISIBLE);
                    flag = true;
                }else{
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    toolbar_picture.setVisibility(View.VISIBLE);
                    flag = false;
                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    void showExif(Uri photoUri){
        if(photoUri != null){

            ParcelFileDescriptor parcelFileDescriptor = null;

            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(photoUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                ExifInterface exifInterface = new ExifInterface(fileDescriptor);

                BottomSheetDialog infoDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogTheme);
                View infoDialogView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_info,
                                (LinearLayout) findViewById(R.id.infoContainer),
                                false
                        );
                TextView txtInfoProducer = (TextView) infoDialogView.findViewById(R.id.txtInfoProducer);
                TextView txtInfoSize = (TextView) infoDialogView.findViewById(R.id.txtInfoSize);
                TextView txtInfoModel = (TextView) infoDialogView.findViewById(R.id.txtInfoModel);
                TextView txtInfoFlash = (TextView) infoDialogView.findViewById(R.id.txtInfoFlash);
                TextView txtInfoFocalLength = (TextView) infoDialogView.findViewById(R.id.txtInfoFocalLength);
                TextView txtInfoAuthor = (TextView) infoDialogView.findViewById(R.id.txtInfoAuthor);
                TextView txtInfoTime = (TextView) infoDialogView.findViewById(R.id.txtInfoTime);
                TextView txtInfoName = (TextView) infoDialogView.findViewById(R.id.txtInfoName);

                txtInfoName.setText(imageName);
                txtInfoProducer.setText(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
                txtInfoSize.setText(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "x" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
                txtInfoModel.setText(exifInterface.getAttribute(ExifInterface.TAG_MODEL));
                txtInfoFlash.setText(exifInterface.getAttribute(ExifInterface.TAG_FLASH));
                txtInfoFocalLength.setText(exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
                txtInfoAuthor.setText(exifInterface.getAttribute(ExifInterface.TAG_ARTIST));
                txtInfoTime.setText(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));

                infoDialog.setContentView(infoDialogView);
                infoDialog.show();


                parcelFileDescriptor.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }



        }else{
            Toast.makeText(getApplicationContext(),
                    "photoUri == null",
                    Toast.LENGTH_LONG).show();
        }
    };

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_album:
                Toast.makeText(this,"test",Toast.LENGTH_SHORT);
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    void viewMapping(){
        imageView = findViewById(R.id.imgPicture);
        bottomNavigationView = findViewById(R.id.bottom_picture);
        toolbar_picture = findViewById(R.id.toolbar_picture);
    }


}