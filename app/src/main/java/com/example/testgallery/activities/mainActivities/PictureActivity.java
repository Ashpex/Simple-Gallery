package com.example.testgallery.activities.mainActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.ParcelFileDescriptor;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.bumptech.glide.Glide;
import com.example.testgallery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;


public class PictureActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imgViewBack;
    ImageView imgViewInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        viewMapping();


        Intent intent = getIntent();
        String thumb = intent.getStringExtra("imgSrc");
        Glide.with(this).load(thumb).into(imageView);



        imgViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri targetUri = Uri.parse("file://" + thumb);
                if(targetUri != null){
                    showExif(targetUri);
                }
            }
        });

//        imageView.setImageResource(src);

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    void showExif(Uri photoUri){
        if(photoUri != null){

            ParcelFileDescriptor parcelFileDescriptor = null;



            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(photoUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                ExifInterface exifInterface = new ExifInterface(fileDescriptor);
                //String exif="Exif: " + fileDescriptor.toString();

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
        imgViewBack = findViewById(R.id.imgViewBack);
        imgViewInfo = findViewById(R.id.imgViewInfo);

    }
}