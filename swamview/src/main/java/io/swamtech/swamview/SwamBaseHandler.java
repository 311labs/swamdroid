package io.swamtech.swamview;

import org.json.JSONException;
import org.json.JSONObject;

public class SwamBaseHandler {

    public final SwamBroker broker;

    public SwamBaseHandler(SwamBroker broker) {
        this.broker = broker;
    }
    public void onEvent(JSONObject event) {
        try {
            Class[] args = new Class[]{String.class};
            String action = event.optString("action");
            this.getClass().getMethod("handle_event_" + action, args).invoke(this, event);
        } catch(NoSuchMethodException ignore) {
            // do nothing
        } catch(Exception err){
            err.printStackTrace();
        }
    }

    public void postSwamEvent(JSONObject event) {
        this.broker.browser.postEvent(event);
    }

    public void postSwamEvent(String name, String action) {
        this.broker.browser.postEvent(name, action);
    }

    protected void postSwamError(String name, String reason, int code) {
        JSONObject event = new JSONObject();
        try {
            event.put("name", name);
            event.put("action", "error");
            event.put("type", "response");
            event.put("reason", reason);
            event.put("code", code);
            this.broker.browser.postEvent(event);
        } catch (JSONException e) {

        }
    }
}
