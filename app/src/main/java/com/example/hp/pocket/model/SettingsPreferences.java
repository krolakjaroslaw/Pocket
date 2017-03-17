package com.example.hp.pocket.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPreferences {
    public static final String PREFS_NAME = "settings";
    public static final String PREF_SHOW_LINKS = "show_links";
    public static final String PREF_SHOW_PHONES = "show_phones";
    public static final String PREF_SORT = "sort";
    private SharedPreferences mPrefs;

    public SettingsPreferences(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isShowLinks() {
        return mPrefs.getBoolean(PREF_SHOW_LINKS, true);
    }

    public void setShowLinks(boolean showLinks) {
        mPrefs.edit().putBoolean(PREF_SHOW_LINKS, showLinks).apply();
    }

    public boolean isShowPhones() {
        return mPrefs.getBoolean(PREF_SHOW_PHONES, true);
    }

    public void setShowPhones(boolean showPhones) {
        mPrefs.edit().putBoolean(PREF_SHOW_PHONES, showPhones).apply();
    }

    public boolean isSort() {
        return mPrefs.getBoolean(PREF_SORT, true);
    }

    public void setSort(boolean sort) {
        mPrefs.edit().putBoolean(PREF_SORT, sort).apply();
    }
}
