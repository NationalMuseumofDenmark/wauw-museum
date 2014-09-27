package com.dk.hack.hack4dk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String BeaconUUID = "UUID1234-4567";
    private String url = "http://www.google.de";
    private final String webAppInterfaceName = "Android";

    Context context;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Setup webView.
        webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new WebAppInterface(this), webAppInterfaceName);
        webView.getSettings().setJavaScriptEnabled(true);

        // Don't open this in a browser, but in the app.
        this.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        // Load the page.
        webView.loadUrl(url);

    }

    //Class to be injected in Web page
    public class WebAppInterface {
        Context context;

        /* Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            context = c;
        }

        /*
        <script type="text/javascript">
            function showAndroidToast() {
                Android.showToast();
            }
        </script>
         */
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(context, "Tååst mormor!", Toast.LENGTH_SHORT).show();
        }

        // Hardcoded beacon replace with beacon data.
        @JavascriptInterface
        public String getClosestBeacon() {
            return "UUID-18998-42019";
        }
    }

    // Not used delete?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
