package com.example.veb.jsondemo2;

/**
 * Created by VEB on 2016/9/5.
 */

public class Pictures {
    private String iconUrl;
    private String text;

    public Pictures(String iconUrl, String text){
        this.iconUrl = iconUrl;
        this.text = text;
    }

    public String getTexts() {
        return text;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
