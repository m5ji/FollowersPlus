package com.example.mingujee.followersplus.AsyncController;

/**
 * Created by mingu.jee on 2016-08-01.
 */

import java.lang.*;
import java.net.*;

import android.content.Intent;
import android.os.*;
import java.io.*;
import org.json.*;
import android.content.*;
import com.example.mingujee.followersplus.ConfigActivity;
import com.example.mingujee.followersplus.Model.*;
import com.example.mingujee.followersplus.Helper.Utilities;

public class GetAccessToken extends AsyncTask<URL, Void, String> {
    String token;
    String accessTokenString;
    String id;
    String username;
    User current_user;
    Context context;

    public GetAccessToken(Context context, String token, User user) {
        this.context = context;
        this.token = token; //request token
        current_user = user;
    }
    protected String doInBackground(URL... urls) {
        try
        {
            URL url = new URL(BasePaths.TOKENURL);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
            outputStreamWriter.write("client_id="+Authentication.CLIENT_ID+
                    "&client_secret="+ Authentication.CLIENT_SECRET +
                    "&grant_type=authorization_code" +
                    "&redirect_uri="+Authentication.CALLBACK_URL+
                    "&code=" + token);
            outputStreamWriter.flush();
            String response = Utilities.streamToString(httpsURLConnection.getInputStream());
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

            current_user.setAccessToken(jsonObject.getString("access_token")); //Here is your ACCESS TOKEN
            current_user.setID(jsonObject.getJSONObject("user").getString("id"));
            current_user.setUserName(jsonObject.getJSONObject("user").getString("username")); //This is how you can get the user info.
            //You can explore the JSON sent by Instagram as well to know what info you got in a response
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return current_user.getAccessToken();
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, ConfigActivity.class);
        intent.putExtra("CurrentUser", current_user);
        context.startActivity(intent);
    }
}
