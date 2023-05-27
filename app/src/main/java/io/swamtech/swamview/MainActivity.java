package io.swamtech.swamview;

import io.swamtech.swamview.handlers.BrowserHandler;
import io.swamtech.swamview.handlers.SystemHandler;

public class MainActivity extends SwamActivity {

    @Override
    protected void initSettings() {
        this.url_active = this.broker.settings.optString("app_url", "file:///android_asset/apps/mobile/index.html");
        this.url_offline = "file:///android_asset/offline.html";
        // enable strict caching to save on network traffic
        this.strictCaching = false;

    }
    @Override
    protected void initHandlers() {

        this.broker.addHandler("browser", new BrowserHandler(this.broker));
        this.broker.addHandler("system", new SystemHandler(this.broker));

    }

}
