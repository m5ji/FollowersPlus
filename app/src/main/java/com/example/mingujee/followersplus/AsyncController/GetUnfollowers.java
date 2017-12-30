package com.example.mingujee.followersplus.AsyncController;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.mingujee.followersplus.ConfigActivity;
import com.example.mingujee.followersplus.Helper.Utilities;
import com.example.mingujee.followersplus.Model.Authentication;
import com.example.mingujee.followersplus.Model.BasePaths;
import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.View.UsersAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

import android.content.*;
import android.widget.*;

/**
 * Created by mingu.jee on 2016-08-27.
 */
public class GetUnfollowers extends AsyncTask<URL, Void, UsersAdapter> {

    String access_token;
    Context context;
    ArrayList<User> unfollowers;

    public GetUnfollowers(Context context,
                          String access_token){
        this.context = context;
        this.access_token = access_token;
        unfollowers = new ArrayList<User>();
    }

    protected UsersAdapter doInBackground(URL... urls) {
        UsersAdapter adapter;
        try {
            //Get a list of following users
            String urlString = BasePaths.APIURL + "/users/self/follows?access_token=" + access_token;
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inputStream = url.openConnection().getInputStream();
            String response = Utilities.streamToString(inputStream);
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray data = jsonObject.getJSONArray("data");

            //Check the relationship with the other users that the current user follows
            for (int i = 0; i < data.length(); i++) {
                JSONObject user = data.getJSONObject(i);
                String id = user.getString("id");
                urlString = BasePaths.APIURL + "/users/" + id + "/relationship?access_token=" + access_token;
                url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                inputStream = url.openConnection().getInputStream();
                response = Utilities.streamToString(inputStream);
                jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                if(jsonObject.getJSONObject("data").getString("incoming_status") != "followed_by")
                {
                    //Get infromation of the unfollowers
                    urlString = BasePaths.APIURL + "/users/" + id + "/?access_token=" + access_token;
                    url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    inputStream = url.openConnection().getInputStream();
                    response = Utilities.streamToString(inputStream);
                    jsonObject = (JSONObject) new JSONTokener(response).nextValue();

                    User unfollower = new User();
                    unfollower.setUserName(jsonObject.getJSONObject("data").getString("username"));
                    unfollower.setID(jsonObject.getJSONObject("data").getString("id"));
                    unfollower.setProfileURL(jsonObject.getJSONObject("data").getString("profile_picture"));
                    unfollower.setFullName(jsonObject.getJSONObject("data").getString("full_name"));
                    unfollower.setAccessToken(access_token);
                    unfollowers.add(unfollower);
                }
            }
            // Create the adapter to convert the array to views
            adapter = new UsersAdapter(context, unfollowers, "unfollow");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            adapter = null;
        }
        return adapter;
    }

    protected void onPostExecute(UsersAdapter result) {
        Dialog alert = new Dialog(context);
        // Attach the adapter to a ListView
        ListView listView = new ListView((context));
        listView.setAdapter(result);
        alert.setContentView(listView);
        alert.show();
    }
}
