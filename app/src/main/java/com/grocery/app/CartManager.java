package com.grocery.app;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
public class CartManager {
    private static final String PREF_NAME = "cart_prefs";
    private static final String KEY_CART = "cart_items";
    public static void saveCart(Context context, ArrayList<Product> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_CART, new Gson().toJson(cart)).apply();
    }
    public static ArrayList<Product> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CART, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}