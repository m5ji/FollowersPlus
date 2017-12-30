package com.example.mingujee.followersplus.AsyncController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ListView;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by mingu.jee on 2016-08-06.
 */
public class GetUsersToFollow extends AsyncTask<URL, Void, UsersAdapter> {
    String hashtags;
    String access_token;
    Context context;
    double followers;
    double following;
    String followers_inequality;
    String following_inequality;
    ArrayList<User> UsersToFollow;
    UsersAdapter adapter;

    FollowingUsers followingUsers;

    public GetUsersToFollow(Context context,
                            FollowingUsers followingUsers,
                            double followers,
                            double following,
                            String followers_inequality,
                            String following_inequality,
                            String access_token,
                            String hashtags) {
        this.hashtags = hashtags;
        this.context = context;
        this.followingUsers = followingUsers;
        this.access_token = access_token;
        this.followers = followers;
        this.following = following;
        this.followers_inequality = followers_inequality;
        this.following_inequality = following_inequality;
        this.UsersToFollow = new ArrayList<User>();
    }

    protected UsersAdapter doInBackground(URL... urls) {
        for (String hashtag: hashtags.split(" ")) {
            try {
                hashtag = hashtag.replaceAll("#","");
                //Get a list of recently tagged media
                String urlString = BasePaths.APIURL + "/tags/" + hashtag + "/media/recent/?access_token=" + access_token;
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream inputStream = url.openConnection().getInputStream();
                String response = Utilities.streamToString(inputStream);
                JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray data = jsonObject.getJSONArray("data");
                for(int i = 0; i < data.length(); i++) {
                    JSONObject media = data.getJSONObject(i);
                    //Retrieve user info of each media
                    String id = media.getJSONObject("user").getString("id");
                    String username = media.getJSONObject("user").getString("username");
                    urlString = BasePaths.APIURL + "/users/" + id + "/?access_token=" + access_token;
                    url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    inputStream = url.openConnection().getInputStream();
                    response = Utilities.streamToString(inputStream);
                    jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                    JSONObject user = jsonObject.getJSONObject("data");

                    //Follow users meet the "followers", "following" conditions
                    if (following_inequality.equals("higher than") && followers_inequality.equals("higher than")) {
                        if(user.getJSONObject("counts").getDouble("follows") >= following
                                && user.getJSONObject("counts").getDouble("followed_by") >= followers) {

                            User follow = new User();
                            follow.setUserName(jsonObject.getJSONObject("data").getString("username"));
                            follow.setID(jsonObject.getJSONObject("data").getString("id"));
                            follow.setProfileURL(jsonObject.getJSONObject("data").getString("profile_picture"));
                            follow.setFullName(jsonObject.getJSONObject("data").getString("full_name"));
                            follow.setAccessToken(access_token);
                            UsersToFollow.add(follow);

                            /*
                            urlString = BasePaths.APIURL + "/users/" + id + "/relationship";
                            url = new URL(urlString);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("POST");
                            urlConnection.setDoInput(true);
                            urlConnection.setDoOutput(true);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                            outputStreamWriter.write("access_token="+ access_token + "&action=follow");
                            outputStreamWriter.flush();

                            response = Utilities.streamToString(urlConnection.getInputStream());
                            jsonObject = (JSONObject) new JSONTokener(response).nextValue();

                            User followed_user = new User();
                            followed_user.setID(id);
                            followed_user.setUserName(username);
                            followingUsers.addUser(followed_user);
                            */

                        }
                    }
                    else if(following_inequality.equals("lower than") && followers_inequality.equals("higher than")){
                        if(user.getJSONObject("counts").getDouble("follows") <= following
                                && user.getJSONObject("counts").getDouble("followed_by") >= followers) {

                            User follow = new User();
                            follow.setUserName(jsonObject.getJSONObject("data").getString("username"));
                            follow.setID(jsonObject.getJSONObject("data").getString("id"));
                            follow.setProfileURL(jsonObject.getJSONObject("data").getString("profile_picture"));
                            follow.setFullName(jsonObject.getJSONObject("data").getString("full_name"));
                            follow.setAccessToken(access_token);
                            UsersToFollow.add(follow);
                        }
                    }

                    else if(following_inequality.equals("higher than") && followers_inequality.equals("lower than")){
                        if(user.getJSONObject("counts").getDouble("follows") >= following
                                && user.getJSONObject("counts").getDouble("followed_by") <= followers) {

                            User follow = new User();
                            follow.setUserName(jsonObject.getJSONObject("data").getString("username"));
                            follow.setID(jsonObject.getJSONObject("data").getString("id"));
                            follow.setProfileURL(jsonObject.getJSONObject("data").getString("profile_picture"));
                            follow.setFullName(jsonObject.getJSONObject("data").getString("full_name"));
                            follow.setAccessToken(access_token);
                            UsersToFollow.add(follow);
                        }
                    }

                    else if(following_inequality.equals("lower than") && followers_inequality.equals("lower than")){
                        if(user.getJSONObject("counts").getDouble("follows") <= following
                                && user.getJSONObject("counts").getDouble("followed_by") <= followers) {

                            User follow = new User();
                            follow.setUserName(jsonObject.getJSONObject("data").getString("username"));
                            follow.setID(jsonObject.getJSONObject("data").getString("id"));
                            follow.setProfileURL(jsonObject.getJSONObject("data").getString("profile_picture"));
                            follow.setFullName(jsonObject.getJSONObject("data").getString("full_name"));
                            follow.setAccessToken(access_token);
                            UsersToFollow.add(follow);
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Create the adapter to convert the array to views
        adapter = new UsersAdapter(context, UsersToFollow, "follow");
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
