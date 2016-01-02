package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grayraven.displayjokes.DisplayJokeActivity;
import com.grayraven.jokes.Joker;


public class MainActivity extends ActionBarActivity {

    private static Joker joker = new Joker();
    private static final String TAG = "app.MainActivity";

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
       Log.i(TAG, "starting DisplayJoke intent");
        Intent displayIntent = new Intent(this, DisplayJokeActivity.class);
        displayIntent.putExtra("joke", "joke here");
        startActivity(displayIntent);

        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Jim!"));
    }


}
