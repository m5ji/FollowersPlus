package com.example.mingujee.followersplus.AsyncController;

/**
 * Created by mingu.jee on 2016-12-10.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.AsyncTask;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetProfilePic extends AsyncTask<URL, Void, Bitmap> {

    Context context;
    String PicURL;
    Bitmap profilePic;

    public GetProfilePic(Context context, String PicURL) {
        this.context = context;
        this.PicURL = PicURL;
    }
    protected Bitmap doInBackground(URL... urls) {
        try
        {
            URL url = new URL(PicURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            profilePic= BitmapFactory.decodeStream(input);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return profilePic;
    }
}