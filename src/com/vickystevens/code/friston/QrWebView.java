package com.vickystevens.code.friston;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


/**
 * Created by IntelliJ IDEA.
 * User: vicks
 * Date: 20/03/12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class QrWebView extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        
        webView.loadUrl("http://slashdot.org");


               
    }
}