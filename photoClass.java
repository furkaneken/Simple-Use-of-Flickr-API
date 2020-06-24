package com.example.getrecentphotos;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class photoClass implements Serializable {

    //name of image in Drawable folder
    private String imagePath;
    private String id;
    private String serverid;
    private int farmid;
    private transient Bitmap bitmap;
    private String secret;


    public photoClass() {
    }

    public photoClass(String id, String serverid, int farmid ,  String secret) {
        this.farmid = farmid;
        this.serverid = serverid;
        this.imagePath = imagePath;
        this.secret = secret;
        this.id = id;
    }

    public String getImagePath() {

        // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        String url = "https://farm" + farmid + ".staticflickr.com/" +"/"+ serverid +"/"+ id + "_" + secret + ".jpg";
        return url;
    }

    public String getId() {
        return id;
    }

    public String getServerid() {
        return serverid;
    }

    public int getFarmid() {
        return farmid;
    }

    public String getSecret() {
        return secret;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public void setFarmid(int farmid) {
        this.farmid = farmid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }















}
