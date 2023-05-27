package io.swamtech.swamview.handlers;

import android.os.Build;
import android.os.SystemClock;

import org.json.JSONException;
import org.json.JSONObject;

import io.swamtech.swamview.SwamBaseHandler;
import io.swamtech.swamview.SwamBroker;

public class SystemHandler extends SwamBaseHandler {
    private String sn;
    private String version;

    public SystemHandler(SwamBroker broker) {
        super(broker);
    }

    public void handle_event_info(JSONObject event) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("name", "system");
        response.put("type", "response");
        response.put("action", "info");

        JSONObject data = new JSONObject();
        response.put("data", data);

        data.put("make", Build.MANUFACTURER);
        data.put("model", Build.MODEL);
        data.put("sn", this.sn);
        data.put("version", this.version);
        data.put("uptime", SystemClock.uptimeMillis());
        data.put("android", Build.VERSION.SDK_INT);
        this.postSwamEvent(response);
    }
}
