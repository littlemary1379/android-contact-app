package com.mary.contactapp.util;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


import com.mary.contactapp.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageUpload {

    public static final int PICK_FROM_ALBUM = 1;

    // 앨범으로 이동
    public static void goToAlbum(MainActivity mainActivity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        mainActivity.startActivityForResult(intent, ImageUpload.PICK_FROM_ALBUM);
    }

    // 이미지 채우기
    public static void setImage(String profileURL, CircleImageView ivProfile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(profileURL, options);
        ivProfile.setImageBitmap(originalBm);
    }

    // URI로 이미지 실제 경로 가져오기
    public static String getRealPathFromURI(Uri contentURI, MainActivity mainActivity) {
        String result;
        Cursor cursor = mainActivity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}

