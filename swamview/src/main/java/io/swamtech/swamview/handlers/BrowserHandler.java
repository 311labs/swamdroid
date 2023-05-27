package io.swamtech.swamview.handlers;

import android.view.View;

import org.json.JSONObject;

import io.swamtech.swamview.SwamBaseHandler;
import io.swamtech.swamview.SwamBroker;

public class BrowserHandler extends SwamBaseHandler {
    public BrowserHandler(SwamBroker broker) {
        super(broker);
    }

    public void handle_event_clear_cache(JSONObject event) {
        this.broker.browser.clearCache();
        this.postSwamEvent("browser", "cache_cleared");
    }

    public void handle_event_set_url(JSONObject event) {
        String url = event.optString("url");
        if (url.length() > 20) {
            this.broker.setSetting("url", url);
            this.broker.browser.setURL(url);
        }
    }

    public void handle_event_reload(JSONObject event) {
        this.broker.browser.reload();
    }

    public void handle_event_hide_nav(JSONObject event) {
        View decorView = this.broker.browser.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
