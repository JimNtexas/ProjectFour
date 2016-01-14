package com.udacity.gradle.builditbigger;

import android.os.ConditionVariable;
import android.support.v7.app.ActionBarActivity;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by Jim on 1/9/2016.
 */
public class appAsyncTaskTester extends AndroidTestCase  {

    private final static String TAG = "appAsyncTaskTester";
    private TestActivity activity;
    private ConditionVariable pause;

    public appAsyncTaskTester() {
    }

    @Override
    protected void setUp() throws Exception {
        Log.d(TAG, "setup");
        pause = new ConditionVariable();
        activity = new TestActivity();
        activity.Start();
        long timeout = 30L * 1000;
        boolean normalExit = pause.block(timeout); //true if the condition was opened, false if the call returns because of the timeout.
        Log.d(TAG, "setUp timeout: " + normalExit);
        assertEquals(normalExit,true);
    }

    @Override
    protected void tearDown() throws Exception {
        Log.d(TAG, "teardown");
    }

    public class TestActivity extends ActionBarActivity implements JokeTransaction {
        private EndpointsAsyncTask task;
        private static final String LOG_TAG = "TestActivity";
        public void Start() {
            task = new EndpointsAsyncTask(this);
            task.execute();
        }

        private static final String TAG = "TestActivity";
        @Override
        public void jokeReady(String joke) {
            Log.d(LOG_TAG, "jokeReady: " + joke);
            assertEquals(true, !joke.isEmpty());
            pause.open();
        }
    }

}
