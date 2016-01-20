package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd interstitialAd;
    private final static String TAG = "FreeMainFragment";
    public boolean interstitialLoaded = false;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInterstitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
           //     .addTestDevice("46AB0275142A57459C7BF5472676AA8F")
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    private void initInterstitial() {
        Log.d(TAG, "initInterstitial");
        interstitialAd = new InterstitialAd(getContext());
        String adId = this.getString(R.string.interstital_add_unit_id);
        interstitialAd.setAdUnitId(adId);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        interstitialAd.loadAd(adRequestBuilder.build());

        // Set an AdListener.
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded");
                interstitialLoaded = true;
            }

            @Override
            public void onAdClosed() {
                // Proceed
                Log.d(TAG, "onAdClosed");
                ((MainActivity)getActivity()).ExecuteAsyncTask();
            }
        });
    }

    private void showInterstitial() {
        if(interstitialLoaded) {
            Log.d(TAG, "showing interstitialAd");
            interstitialAd.show();
        } else {
            Log.d(TAG, "interstitialAd not loaded");
            interstitialLoaded = false;
            ((MainActivity)getActivity()).ExecuteAsyncTask();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(showInterstitial);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        interstitialLoaded = false;
        IntentFilter iff= new IntentFilter(MainActivity.INTERSTITIAL_INTENT);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(showInterstitial, iff);
        initInterstitial();
    }

    private BroadcastReceiver showInterstitial= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            showInterstitial();
        }
    };

}
