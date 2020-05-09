package com.coc.cocmanager.Utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.coc.cocmanager.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String PREFERENCE_PERSONAL = "personal";

    public static Object parseResponse(String result, Class class_object) {
        Gson gson = new Gson();
        Object object = gson.fromJson(result, class_object);
        return object;
    }

    //For InterNet Checking
    public static boolean isOnline(Context _Context) {
        ConnectivityManager cm = (ConnectivityManager) _Context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @SuppressLint("ResourceType")
    public static Dialog showImageDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        AnimatorSet set;
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.image_progress_bar);
        ImageView profileIV = (ImageView) dialog.findViewById(R.id.progressIV);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.flip);
        set.setTarget(profileIV);
        set.start();
        dialog.show();
        dialog.setCancelable(false);
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        int width = display.getWidth() - 80;
        int height = display.getHeight() - 200;
        Window window = dialog.getWindow();
        window.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT);

        return dialog;
    }

    public static void showToast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String get_yyyy_mm_dd_HMS(String timeZoneDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = originalFormat.parse(timeZoneDate);
            System.out.println("Old Format :   " + originalFormat.format(date));
            System.out.println("New Format :   " + targetFormat.format(date));

        } catch (ParseException ex) {
            // Handle Exception.
        }

        return targetFormat.format(date);
    }
}
