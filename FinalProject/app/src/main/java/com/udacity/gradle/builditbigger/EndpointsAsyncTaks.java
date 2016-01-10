package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.grayraven.jokes.backend.myApi.MyApi;

import java.io.IOException;

;

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private MainActivity mainActivity;
    private static final String TAG = EndpointsAsyncTask.class.getName();

    public EndpointsAsyncTask(MainActivity main) {
        mainActivity = main;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once

   /*          MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    }); */


            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://hellogce-1172.appspot.com/_ah/api/");
            // end options for devappserver
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
        mainActivity.displayJoke(joke);
    }

}
