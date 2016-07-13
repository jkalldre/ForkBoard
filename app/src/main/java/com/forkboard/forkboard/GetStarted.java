package com.forkboard.forkboard;

/**
 * Created by JamJam on 7/12/16.
 */
public class GetStarted {
    WebView webView = new WebView(this);
    webView.getSettings().setJavaScriptEnabled(true);
    String urlDemo = "http://www.appdemostore.com/m/4934128529047552";
    webView.loadUrl(urlDemo);
}
