package com.example.mingujee.followersplus.AsyncController;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mingujee.followersplus.Helper.Utilities;
import com.example.mingujee.followersplus.Model.BasePaths;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mingu.jee on 2017-01-07.
 */
public class FollowUser extends AsyncTask<URL, Void, Boolean> {

    String access_token;
    Context context;
    String followerID;

    public FollowUser(Context context,
                            String access_token,
                            String followerID){
        this.context = context;
        this.access_token = access_token;
        this.followerID = followerID;
    }

    protected Boolean doInBackground(URL... urls) {
        Boolean result;
        try {
            String urlString = BasePaths.APIURL + "/users/" + followerID + "/relationship";
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write("access_token="+ access_token + "&action=follow");
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
