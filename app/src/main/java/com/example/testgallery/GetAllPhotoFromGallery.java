package com.example.testgallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import image.Image;

public class GetAllPhotoFromGallery {
    public static final List<Image> getAllImageFromGallery(Context context) {
        Uri uri;
        Cursor cursor;
        int columnIndexData, thumb;
        List<Image> listImage = new ArrayList<>();

        String absolutePathImage = null;
        String thumbnail = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;


        cursor = context.getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        thumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathImage = cursor.getString(columnIndexData);
            thumbnail = cursor.getString(thumb);

            Image image = new Image();
            image.setPath(absolutePathImage);
            image.setThumb(thumbnail);

            listImage.add(image);
        }
        return listImage;
    }
}
