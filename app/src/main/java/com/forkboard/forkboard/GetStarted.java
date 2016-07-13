package com.forkboard.forkboard;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
/**
 * Created by JamJam on 7/12/16.
 */
public class GetStarted extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        String urlDemo = "http://www.appdemostore.com/m/4934128529047552";
        webView.loadUrl(urlDemo);

    }
}
