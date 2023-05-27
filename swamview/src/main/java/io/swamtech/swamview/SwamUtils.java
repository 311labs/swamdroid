package io.swamtech.swamview;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SwamUtils {
    public static String toZulu(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return df.format(date);
    }

    public static String toZulu(long ms) {
        return toZulu(new Date(ms - Calendar.getInstance().getTimeZone().getOffset(ms)));
    }

    public static long getEpoch() {
        return System.currentTimeMillis() / 1000;
    }

    static public ArrayList<String> getArrayList(JSONArray array) {
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < array.length(); i++){
            try {
                list.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    static public JSONObject getByKeys(ArrayList<String> keys, JSONObject src) {
        JSONObject output = new JSONObject();
        for (String key : keys) {
            try {
                if (src.has(key)) {
                    output.put(key, src.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    static public boolean saveFile(Context context, String path, JSONObject jsonObject) {
        String userString = jsonObject.toString();
        File file = new File(context.getFilesDir(), path);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    static public JSONObject fromFile(Context context, String path) {
        try {

            InputStream fis;
            if (path.startsWith("assets")) {
                String fname = path.substring(path.indexOf('/')+1);
                fis  = context.getAssets().open(fname);
            } else {
                fis = context.openFileInput(path);
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (FileNotFoundException fileNotFound) {
            return new JSONObject();
        } catch (IOException ioException) {
            return new JSONObject();
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public static NetworkInfo getActiveNetwork(Activity activity) {
        final ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo();
    }

    public static boolean isConnected(Activity activity) {
        final NetworkInfo activeNetwork = getActiveNetwork(activity);
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static String getActiveNetworkName(NetworkInfo activeNetwork) {
        if (activeNetwork == null) {
            return "none";
        }

        int networkType = activeNetwork.getType();
        switch (networkType) {
            case ConnectivityManager.TYPE_WIFI:
                return "wifi";
            case ConnectivityManager.TYPE_MOBILE:
                return "cellular";
            case ConnectivityManager.TYPE_ETHERNET:
                return "ethernet";
            case ConnectivityManager.TYPE_VPN:
                return "vpn";
            default:
                return "unknown";
        }
    }
}
