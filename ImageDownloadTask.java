package com.example.getrecentphotos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.getrecentphotos.photoClass;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloadTask  extends AsyncTask<photoClass, Void , Bitmap> {


    ImageView img;

    public ImageDownloadTask(ImageView img) {
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(photoClass... photos) {

        photoClass current = photos[0];
        Bitmap bitmap = null;
        try {
            URL url = new URL(current.getImagePath());
            Log.i("DEV2", String.valueOf(url));
            InputStream is = new BufferedInputStream(url.openStream());
            bitmap = BitmapFactory.decodeStream(is);
            current.setBitmap(bitmap);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;


    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        img.setImageBitmap(bitmap);

    }


}
