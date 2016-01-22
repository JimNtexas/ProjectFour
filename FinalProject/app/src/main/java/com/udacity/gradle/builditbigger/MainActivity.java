package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grayraven.displayjokes.DisplayJokeActivity;

public class MainActivity extends ActionBarActivity implements JokeTransaction {

    private static final String TAG = "main.MainActivity";
    public static final String  INTERSTITIAL_INTENT =  "Interstitial_intent";
    private ProgressDialog dlg;
    private int interCnt = 0;

    private static final int interlimit = 4; //used by free version only

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
       Log.d(TAG, "DisplayJoke Is free version: " + BuildConfig.FREE_VERSION);
       if(BuildConfig.FREE_VERSION && interCnt == 0 ) {
           Log.d(TAG, "Send interstitial broadcast");
           Intent intent = new Intent(INTERSTITIAL_INTENT);
           LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
       } else {
           ExecuteAsyncTask();
       }

        if(BuildConfig.FREE_VERSION) {
            interCnt = interCnt > interlimit ? 0 : (interCnt + 1); //only show the interstitial periodically
        }
    }

    public void ExecuteAsyncTask() {
        new EndpointsAsyncTasks(this).execute();
        dlg = new ProgressDialog(this);
        dlg.setMessage(getString(R.string.progress_msg));
        dlg.setCancelable(false);
        dlg.show();
    }

    @Override
    public void jokeReady(String joke) {
        Log.d(TAG, "jokeReady");
        if(dlg != null) {
            dlg.dismiss();
            dlg = null;
        }
        Intent displayIntent = new Intent(this, DisplayJokeActivity.class);
        displayIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        displayIntent.putExtra("jokeText", joke);
        startActivity(displayIntent);
    }

}
