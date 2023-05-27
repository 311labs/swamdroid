package io.swamtech.swamview;

import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SwamBroker {
    // our handlers
    Map<String, SwamBaseHandler> handlers = new HashMap<String, SwamBaseHandler>();
    public SwamActivity browser;
    public JSONObject settings;
    String settingsFile = "config.json";
    public SwamBroker(SwamActivity activity) {
        browser = activity;
        settings = SwamUtils.fromFile(browser, settingsFile);
    }

    public void addHandler(String name, SwamBaseHandler handler) {
        handlers.put(name, handler);
    }

    public JSONObject convertEventToObject(String event) {
        try {
            JSONObject jobj = new JSONObject(event);
            return jobj;
        } catch (JSONException e) {
        }
        return null;
    }

    @JavascriptInterface
    public void postEvent(String event) {
        JSONObject obj = convertEventToObject(event);
        if (obj == null) return;
        String name = obj.optString("name", "");
        if (this.handlers.containsKey(name)) {
            this.handlers.get(obj.optString("name")).onEvent(obj);
        }
    }

    @JavascriptInterface
    public void saveSettings(String data) {
        SwamUtils.saveFile(browser, settingsFile, settings);
    }

    @JavascriptInterface
    public String getSettings() {
        return settings.toString();
    }

    public void setSetting(String key, String value) {
        try {
            this.settings.put(key, value);
            SwamUtils.saveFile(browser, settingsFile, settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setSetting(String key, int value) {
        try {
            this.settings.put(key, value);
            SwamUtils.saveFile(browser, settingsFile, settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
