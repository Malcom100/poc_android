package com.poc_nativex_2.modules.nativex;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.poc_nativex_2.Utils.UtilsAds;

/**
 * Define the module here
 * This module will used by react native
 */

public class ActivityStarterModule extends ReactContextBaseJavaModule {

    public ActivityStarterModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override
    public String getName() {
        return UtilsAds.REACT_MODULE;
    }

    //launch another Activity
    @ReactMethod
    void redirectToActivity(String appID, String category,String provider) {
        Log.i("Test", "test launch another activity ");
        getCurrentActivity().startActivity(UtilsAds.createIntentFromProvider(appID,category,provider,getCurrentActivity()));
    }
}
