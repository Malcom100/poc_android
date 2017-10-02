package com.poc_nativex_2.modules.nativex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.SessionListener;
import com.nativex.monetization.mraid.AdInfo;
import com.poc_nativex_2.BuildConfig;
import com.poc_nativex_2.R;
import com.poc_nativex_2.Utils.UtilsAds;
import com.poc_nativex_2.model.Ad;

/**
 * This class allows to manage nativex
 */

public class NativexActivity extends AppCompatActivity implements SessionListener, OnAdEventV2 {

    private Ad myAd;

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    private android.support.v7.widget.Toolbar toolbar;

    /**
     * Indicates if we can show ad
     */
    private boolean canShowAd = true;
    /**
     * Indicates if we can show toast
     */
    private boolean canShowToast = true;

    private LinearLayout layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new AdReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "Poc_nativex_2", null);

        setContentView(R.layout.layout_nativex_activity);
        layout = (LinearLayout)findViewById(R.id.layout_loading);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        canShowAd = true;
        canShowToast = true;
        createObjectAd();
        UtilsAds.initializeMapCategories();
    }

    private void createObjectAd() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            String appId = intent.getStringExtra(UtilsAds.BUNDLE_KEY_APP_ID);
            String category = intent.getStringExtra(UtilsAds.BUNDLE_KEY_CATEGORY);
            String provider = intent.getStringExtra(UtilsAds.BUNDLE_KEY_PROVIDER);
            myAd = new Ad(appId, category, provider);
            Log.i("Test", "object is " + myAd);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //create a session nativex
        MonetizationManager.createSession(getApplicationContext(), myAd.getAppId(), this);
    }

    //SessionListener,
    @Override
    public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, String message) {

        //session is created
        if (success) {

            //We are fetching all the ads here as this is a one scene app.
            //However, in a typical integration you will want to spread these calls out in your game.
            //It is recommended that you add these calls in an area that would allow around 5 seconds before attempting to show
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Game_Launch, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Main_Menu_Screen, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Level_Failed, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Store_Open, this);
            MonetizationManager.fetchAd(this, NativeXAdPlacement.Player_Generated_Event, this);
        } else {
            Log.i("Test", "Oh no! Something isn't set up correctly - re-read the documentation or ask customer support for help https://selfservice.nativex.com/Help");

        }
    }

    //listener OnAdEventV2
    @Override
    public void onEvent(AdEvent event, AdInfo adInfo, String message) {
        showtoastError(message, adInfo.getPlacement());

        switch (event) {
            case BEFORE_DISPLAY:
                Log.i("Test", "BEFORE DISPLAY");
                goodFetchAd(adInfo);
                break;
            case DOWNLOADING:
                Log.i("Test", "DOWNLOADING");
                break;
            case FETCHED:
                Log.i("Test", "FETCHED");
                goodShowAd(adInfo);
                break;
            case ALREADY_FETCHED:
                Log.i("Test", "ALREADY FETCHED");
                if (canShowAd) {
                    goodShowAd(adInfo);
                }
                break;
            case ALREADY_SHOWN:
                Log.i("Test", "ALREADY SHOWN");
                break;
            case DISMISSED:
                Log.i("Test", "DISMISSED");
                finishActivity();
                break;
        }
    }

    /**
     * Allows to fetch the ad associated to category
     *
     * @param adInfo
     */
    private void goodFetchAd(AdInfo adInfo) {
        String placement = UtilsAds.categories.get(myAd.getCategory());
        if (placement.equalsIgnoreCase(adInfo.getPlacement())) {
            MonetizationManager.fetchAd(this, adInfo.getPlacement(), this);
            layout.setVisibility(View.GONE);
        }
    }

    /**
     * Allows to display the ad associated to category
     * set @canShowAd to false because we are in the activity and to avoid the listener to show automatically the Ad
     *
     * @param adInfo
     */
    private void goodShowAd(AdInfo adInfo) {
        String placement = UtilsAds.categories.get(myAd.getCategory());
        if (placement.equalsIgnoreCase(adInfo.getPlacement())) {
            canShowAd = false;
            MonetizationManager.showReadyAd(this, adInfo.getPlacement(), this);
        }
    }

    private void showtoastError(String message, String placement) {
        if (message.equalsIgnoreCase(UtilsAds.ERROR_AD_NATIVEX) && canShowToast) {
            canShowToast = false;
            String strFormat = String.format("%s for %s", message, placement);
            Toast.makeText(this, strFormat, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivity();
                return true;
        }
        return true;
    }

    private void finishActivity(){
        setResult(RESULT_OK);
        finish();
    }

}
