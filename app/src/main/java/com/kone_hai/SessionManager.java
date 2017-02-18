package com.kone_hai;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by imittal on 5/12/16.
 */
public class SessionManager {
    // User name (make variable public to access from outside)
    public static final String KEY_USER_TOKEN = "userToken";
    // Sharedpref file name
    private static final String PREF_NAME = "KoneHaiPrefName";
    // All Shared Preferences Keys
    private static final String USER_ID = "userId";
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * <p>
     * <p>
     * <p>
     * /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USER_TOKEN, pref.getString(KEY_USER_TOKEN, null));
        // return user
        return user;
    }

    public String getUserToken() {
        return pref.getString(KEY_USER_TOKEN, null);
    }


    public int getUserId() {
        return pref.getInt(USER_ID, 0);
    }
}
