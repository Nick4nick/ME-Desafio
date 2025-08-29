package com.example.medesafio;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

public class SessionManager {

    private static final String PREF_NAME = "ME_Desafio_Prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final DatabaseHelper dbHelper;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        dbHelper = new DatabaseHelper(context);
    }

    public boolean validateLogin(String userOrEmail, String password) {
        if (dbHelper.checkUser(userOrEmail, password)) {
            // salvar dados do usuário logado no SharedPreferences
            Cursor cursor = dbHelper.getUser(userOrEmail);
            if (cursor.moveToFirst()) {
                editor.putString(KEY_USERNAME, cursor.getString(cursor.getColumnIndexOrThrow("username")));
                editor.putString(KEY_EMAIL, cursor.getString(cursor.getColumnIndexOrThrow("email")));
                editor.apply();
            }
            cursor.close();
            return true;
        }
        return false;
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    public boolean saveUser(String username, String email, String password) {
        return dbHelper.addUser(username, email, password);
    }
}