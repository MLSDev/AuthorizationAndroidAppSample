package com.mlsdev.authorizationsample;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {

    public static String getSignInResponseData(Context context){
        return getJsonStringFromAssets(context, "sign_in_result.json");
    }

    public static String getSignInError(Context context) {
        return getJsonStringFromAssets(context, "sign_in_error.json");
    }

    private static String getJsonStringFromAssets(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}
