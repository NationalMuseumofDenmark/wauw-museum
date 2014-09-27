package com.dk.hack.hack4dk;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.estimote.sdk.Utils;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

public class MainActivity extends Activity {

    private String url = "http://wauw.geekworld.dk";
    private final String webAppInterfaceName = "Android";
    private static final String TAG = "Beacon";

    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", null, null);

    private BeaconManager beaconManager;
    List<Beacon> allBeacons;
    Beacon closestBeacon;
    Beacon bestBeacon;
    Beacon previousBeacon;

    private final int beaconRepeats = 3;
    private int beaconCounter = 0;

    Context context;
    WebView webView;

    boolean logging = true;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = (Activity) context;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Setup webView.
        webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new WebAppInterface(this), webAppInterfaceName);
        webView.getSettings().setJavaScriptEnabled(true);


        // Don't open this in a browser, but in the app.
        this.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.clearCache(true);
                view.loadUrl(url);
                return true;
            }
        });

        // Configure BeaconManager.
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                // Note that results are not delivered on UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Note that beacons reported here are already sorted by estimated
                        // distance between device and beacon.
                        closestBeacon = getClosestBeacon(beacons);
                        bestBeacon = getBestBeacon(closestBeacon);

                        allBeacons = beacons;

                        getActionBar().setSubtitle("Found beacons: " + beacons.size());
                    }
                });
            }
        });

        // Load the page.
        webView.loadUrl(url);

    }

    private Beacon getBestBeacon(Beacon closestBeacon) {

        if(closestBeacon == null) {
            return null;
        }

        if(previousBeacon == null) {
            previousBeacon = closestBeacon;
            return null;
        }

        if((closestBeacon.getMajor() + "-" + closestBeacon.getMinor()).equals((previousBeacon.getMajor() + "-" + previousBeacon.getMinor()))) {
            beaconCounter++;
        }
        else {
            beaconCounter = 0;
        }

        if(beaconCounter == beaconRepeats || bestBeacon == null) {
            bestBeacon = closestBeacon;
            beaconCounter = 0;
        }

        previousBeacon = closestBeacon;

        Log.v(TAG, previousBeacon.getMajor() + " " + closestBeacon.getMajor() + " " + bestBeacon.getMajor() + " counter:" + beaconCounter);

        return bestBeacon;
    }

    private Beacon getClosestBeacon(List<Beacon> bList) {
        Beacon beacon = null;
        Beacon minBeacon = null;

        for(int i = 0; i < bList.size(); i++) {
            beacon = bList.get(i);

            if (minBeacon == null) {
                minBeacon = beacon;
            }

            if((Utils.computeAccuracy(beacon)) < (Utils.computeAccuracy(minBeacon))) {
                minBeacon = beacon;
            }
        }

        return minBeacon;
    }

    private String buildBeaconUUID(Beacon b) {
        return closestBeacon.getProximityUUID() + "-" + closestBeacon.getMajor() + "-" + closestBeacon.getMinor();
    }

    private String buildJSONBeaconObject(Beacon b) {

        if (b == null) {
            return null;
        }

        return "{\"" + buildBeaconUUID(b) + "\":" + Utils.computeAccuracy(b) + "}";
    }

    private String buildJSONBeaconArray(List<Beacon> beacons) {
        // {"UUID-1":1.7,"UUID-2":3.0,"UUID-3":2.2}

        if (beacons == null) {
            return null;
        }

        String retStr = "{";
        int i = 0;

        for(Beacon b: allBeacons) {
            retStr = buildBeaconUUID(b) + ":" + Utils.computeAccuracy(b) + ",";
            i++;
        }
        retStr.substring(0,retStr.length()-1);
        retStr += "}";

        Log.v(TAG, retStr);

        return retStr;
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
        public void showToast(String str) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }

        // Hardcoded beacon replace with beacon data.
        @JavascriptInterface
        public String getClosestBeacon() {
            return buildJSONBeaconObject(closestBeacon);
        }

        // Hardcoded beacons replace with beacon data.
        @JavascriptInterface
        public String getAllBeacons() {
            return buildJSONBeaconArray(allBeacons);
        }

        // Hardcoded beacons replace with beacon data.
        @JavascriptInterface
        public String getBestBeacon() {
            return buildJSONBeaconObject(bestBeacon);
        }

    }

    private void log(String str) {
        if(logging) {
            Log.v(TAG, str);
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

    protected void onStart() {
        super.onStart();

        // Check if device supports Bluetooth Low Energy.
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.
        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
            beaconManager.disconnect();
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping ranging", e);
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity.finish();
        System.gc();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                getActionBar().setSubtitle("Bluetooth not enabled");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connectToService() {
        getActionBar().setSubtitle("Scanning...");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.setForegroundScanPeriod(1500, 1500);
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }
}