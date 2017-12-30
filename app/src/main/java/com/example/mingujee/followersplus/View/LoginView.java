package com.example.mingujee.followersplus.View;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.*;
import android.webkit.*;
import android.app.*;

import com.example.mingujee.followersplus.R;

import com.example.mingujee.followersplus.Model.*;
import com.example.mingujee.followersplus.AsyncController.GetAccessToken;

/**
 * Created by mingu.jee on 2016-07-24.
 */
public class LoginView extends LinearLayout {

    Button LoginButton;

    Context context;

    String authURLString;
    String tokenURLString;
    String request_token;

    User user;

    Dialog alert;

    public LoginView(Context context, User user) {
        super(context);
        //Set the login layout to this view
        View.inflate(context, R.layout.login, this);
        this.context = context;

        LoginButton = (Button)findViewById(R.id.loginButton);

        authURLString = BasePaths.AUTHURL + "?client_id=" + Authentication.CLIENT_ID
                + "&redirect_uri=" + Authentication.CALLBACK_URL
                + "&response_type=code&display=touch&scope=basic+public_content+follower_list";

        tokenURLString = BasePaths.TOKENURL + "?client_id=" + Authentication.CLIENT_ID
                + "&client_secret=" + Authentication.CLIENT_SECRET + "&redirect_uri="
                + Authentication.CALLBACK_URL + "&grant_type=authorization_code";

        this.user = user;

        alert = new Dialog(context);

        this.layoutView();
        this.registerControllers();

    }

    private void layoutView() {

    }

    public void registerControllers() {

        LoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Webview inside dialog
                WebView webView = new WebView(getContext());
                webView.setVerticalScrollBarEnabled(false);
                webView.setHorizontalScrollBarEnabled(false);
                webView.setFocusable(true);
                webView.setFocusableInTouchMode(true);
                webView.setWebViewClient(new AuthWebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.loadUrl(authURLString);

                alert.setContentView(webView);
                alert.show();
            }
        });
    }

    public class AuthWebViewClient extends WebViewClient
    {
        @Override
        public void onPageFinished(WebView view, String url)
        {
            if (url.startsWith(Authentication.CALLBACK_URL.toLowerCase()))
            {
                System.out.println(url);
                String parts[] = url.split("=");
                request_token = parts[1];  //This is your request token.
                alert.dismiss();
                new GetAccessToken(context, request_token, user).execute();
            }

        }
    }
}
