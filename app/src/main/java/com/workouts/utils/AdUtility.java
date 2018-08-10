package com.workouts.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.workouts.R;


/**
 * Created by adnan on 8/13/2017.
 */
public class AdUtility {

    public static final String TAG = "AdUtility";
    static Activity activity;
    private static int ADS_COUNTER = 0;
    private static InterstitialAd mInterstitialAd;
    private static InterstitialAd mInterstitialAdExit;

    public static void loadInterstitialAds(Activity activity) {
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(activity.getResources().getString(R.string.admob_interstitial_ad_id));
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG, "admob interst failed ");
                mInterstitialAd.loadAd(new AdRequest.Builder()/*.addTestDevice("D6AACD08AFB18E33BD670462B80BEBC8")*/.build());
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public static void loadInterstitialAdsExit(Activity activity) {
        mInterstitialAdExit = new InterstitialAd(activity);
        mInterstitialAdExit.setAdUnitId(activity.getResources().getString(R.string.admob_exit_interstitial_ad_id));
        mInterstitialAdExit.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG, "admob interst failed ");
                mInterstitialAdExit.loadAd(new AdRequest.Builder()/*.addTestDevice("D6AACD08AFB18E33BD670462B80BEBC8")*/.build());
            }
        });
        mInterstitialAdExit.loadAd(new AdRequest.Builder().build());
    }

    public static void showInterstitialAdExit() {
        if (mInterstitialAdExit.isLoaded()) {
            mInterstitialAdExit.show();
        } else
            mInterstitialAdExit.loadAd(new AdRequest.Builder().build());
    }

    public static void loadAndShowInterstitialAds(final Activity context) {
        activity = context;
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(activity.getResources().getString(R.string.admob_interstitial_ad_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "admob interst loaded ");
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG, "admob interst failed ");
                mInterstitialAd.loadAd(new AdRequest.Builder()/*.addTestDevice("D6AACD08AFB18E33BD670462B80BEBC8")*/.build());
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadInterstitialAds(context);
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder()/*.addTestDevice("D6AACD08AFB18E33BD670462B80BEBC8")*/.build());
    }


    public static void loadAdmobBannerActivity(Activity activity) {
        final AdView adView = (AdView) activity.findViewById(R.id.admob_banner);
        AdRequest request = new AdRequest.Builder().addTestDevice("D6AACD08AFB18E33BD670462B80BEBC8").build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "admob banner loaded: ");
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG, "admob banner failed:: "+i);
            }
        });
    }


    public static void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


}