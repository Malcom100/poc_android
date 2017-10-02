package com.poc_nativex_2.Utils;

import android.content.Context;
import android.content.Intent;

import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.poc_nativex_2.modules.nativex.NativexActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an util to manage the providers
 */

public class UtilsAds {

    public static final String BUNDLE_KEY_APP_ID = "App_ID";
    public static final String BUNDLE_KEY_CATEGORY = "Category";
    public static final String BUNDLE_KEY_PROVIDER = "Provider";

    public static final String REACT_MODULE = "ActivityStarter";

    public static Map<String,String> categories = new HashMap<>();

    public static final String ERROR_AD_NATIVEX = "There was no ad to show.";


    private static Context context;

    public static void initializeMapCategories(){
        categories.put("game", "Game Launch");
        categories.put("store", "Store Open");
        categories.put("failed", "Level Failed");
        categories.put("completed", "Level Completed");
        categories.put("up", "Player Levels Up");
        categories.put("menu", "Main Menu Screen");
        categories.put("event", "Player Generated Event");
        categories.put("won", "P2P competition won");
        categories.put("lost", "P2P competition lost");
    }


    /**
     * This method allows to create the good intent for the provider
     *
     * @param appID
     * @param category
     * @param provider
     * @param ctxt
     * @return The intent
     */
    public static Intent createIntentFromProvider(String appID, String category, String provider, Context ctxt){
        context = ctxt;
        Intent intent = null;
        if(provider.equalsIgnoreCase("nativex")){
            intent = intentNativex(appID, category, provider);
        }
        return intent;
    }


    /**
     * Allows to redirect to nativex activity
     *
     * @param appID
     * @param category
     * @param provider
     * @return intent for Nativex
     */
    private static Intent intentNativex(String appID, String category,String provider){
        Intent intent = new Intent(context, NativexActivity.class);
        intent.putExtra(UtilsAds.BUNDLE_KEY_APP_ID,appID);
        intent.putExtra(UtilsAds.BUNDLE_KEY_CATEGORY,category);
        intent.putExtra(UtilsAds.BUNDLE_KEY_PROVIDER,provider);
        return intent;
    }
}
