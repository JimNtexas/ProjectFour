package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.grayraven.jokes.backend.myApi.MyApi;

import java.io.IOException;

public class  EndpointsAsyncTasks extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context mContext;
    private JokeTransaction jokeTransaction;
    private static final String TAG = EndpointsAsyncTasks.class.getName();
    public EndpointsAsyncTasks(Context context)
        {
            mContext = context;
            jokeTransaction = (JokeTransaction)context;
        }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://hellogce-1172.appspot.com/_ah/api/");
            myApiService = builder.build();
        }

        try {
            String result =  myApiService.getJoke().execute().getData();
            Log.i(TAG, "myApiService returns: " + result);
            return result;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        Log.i(TAG, "onPostExecute returns: " + joke);
        jokeTransaction.jokeReady(joke);
    }
}
