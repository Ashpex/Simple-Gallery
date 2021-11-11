package com.example.testgallery.utility;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.testgallery.models.Image;

public class GetAllPhotoFromGallery {
    public static final List<Image> getAllImageFromGallery(Context context) {
        Uri uri;
        Cursor cursor;
        int columnIndexData, thumb, dateIndex;
        List<Image> listImage = new ArrayList<>();

        String absolutePathImage = null;
        String thumbnail = null;
        Long dateTaken = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;


        cursor = context.getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        thumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
        dateIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
        Calendar myCal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd-MM");
        while (cursor.moveToNext()) {
            try {
                absolutePathImage = cursor.getString(columnIndexData);
                File file = new File(absolutePathImage);
                if(!file.canRead()){
                    continue;
                }
            }catch (Exception e){
                continue;
            }

            thumbnail = cursor.getString(thumb);
            dateTaken = cursor.getLong(dateIndex);
            myCal.setTimeInMillis(dateTaken);
            String dateText = formatter.format(myCal.getTime());
            Image image = new Image();
            image.setPath(absolutePathImage);

            image.setThumb(thumbnail);
            image.setDateTaken(dateText);
            if(image.getPath()==""){
                continue;
            }
            Log.d("Path",image.getPath());
            Log.d("Path",listImage.size()+"");
            listImage.add(image);
        }
        return listImage;
    }
}
