package com.example.mingujee.followersplus.AsyncController;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.mingujee.followersplus.Helper.Utilities;
import com.example.mingujee.followersplus.Model.BasePaths;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.View.UsersAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mingu.jee on 2016-12-10.
 */
public class RemoveUnfollower extends AsyncTask<URL, Void, Boolean> {

    String access_token;
    Context context;
    String unfollowerID;

    public RemoveUnfollower(Context context,
                          String access_token,
                            String unfollowerID){
        this.context = context;
        this.access_token = access_token;
        this.unfollowerID = unfollowerID;
    }

    protected Boolean doInBackground(URL... urls) {
        Boolean result;
        try {
            String urlString = BasePaths.APIURL + "/users/" + unfollowerID + "/relationship";
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write("access_token="+ access_token + "&action=unfollow");
            outputStreamWriter.flush();

            String response = Utilities.streamToString(urlConnection.getInputStream());
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
