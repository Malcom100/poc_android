package com.poc_nativex_2.model;

/**
 * Represents an Ad
 */

public class Ad {

    private String appId;
    private String category;
    private String provider;

    public Ad() {
    }

    public Ad(String appId, String category, String provider) {
        this.appId = appId;
        this.category = category;
        this.provider = provider;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String toString(){
        return this.appId+" - "+this.category+" "+this.provider;
    }
}
