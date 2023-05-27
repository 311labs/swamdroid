package io.swamtech.swamview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.webkit.WebViewAssetLoader;

import org.json.JSONObject;

public class SwamActivity  extends Activity {
    static final int GO_OFFLINE_NETWORK_CODES[] = {
            WebViewClient.ERROR_HOST_LOOKUP,
            WebViewClient.ERROR_TIMEOUT,
            WebViewClient.ERROR_CONNECT,
            WebViewClient.ERROR_BAD_URL};

    public WebView webView;
    public boolean allowJS = true;
    public boolean allowStorage = true;
    public boolean allowFileAccess = true;
    public boolean allowContentAccess = true;
    public boolean overviewMode = true;
    public boolean wideViewPort = false;
    public boolean strictCaching = false;
    public String url_active;
    public String url_offline;
    public String brokerName = "broker";
    public SwamBroker broker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.broker = new SwamBroker(this);
        this.initSettings();
        this.createWebView();
        this.initHandlers();

        if (savedInstanceState == null) {
            webView.loadUrl(this.url_active);
        }
    }

    protected void initSettings() {

    }

    protected void initHandlers() {

    }

    public void reload() {
        if (this.url_active.startsWith("http")) {
            if (!SwamUtils.isConnected(this)) {
                setURL(this.url_offline);
                return;
            }
        }
        this.webView.loadUrl(this.url_active);
    }

    public void clearCache() {
        // Clear Everything from cache
        // first load blank page
        webView.loadUrl("about:blank");
        // next clear cache
        webView.clearCache(true);
        // now reload
        reload();
    }

    public void setURL(String newURL) {
        if (!url_active.equalsIgnoreCase((newURL))) {
            this.url_active = url_active;
            this.reload();
        }
    }

    protected void createWebView() {
        // create the generic webview
        this.webView = new WebView(this);
        this.setContentView(this.webView);

        // set the default settings
        WebSettings settings = this.webView.getSettings();

        // allow media with no gestures
        settings.setMediaPlaybackRequiresUserGesture(true);

        settings.setAllowContentAccess(allowJS);
        settings.setDomStorageEnabled(allowStorage);
        settings.setAllowFileAccess(allowFileAccess);
        settings.setAllowUniversalAccessFromFileURLs(allowFileAccess);
        settings.setAllowFileAccessFromFileURLs(allowFileAccess);
        settings.setLoadWithOverviewMode(overviewMode);
        settings.setUseWideViewPort(wideViewPort);
        if (strictCaching) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        createAssetHandler();
        webView.addJavascriptInterface(broker, brokerName);
    }

    protected void createAssetHandler() {
        // this method allows us to load our application locally
        WebViewAssetLoader assetLoader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();
        
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int code, String request, String error) {
                for (int i : GO_OFFLINE_NETWORK_CODES) {
                    if (i == code) {
                        setURL(url_offline);
                        return;
                    }
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("file:///android_asset") || (url.startsWith("http"))) {
                    return null;
                }
                if (url.startsWith("file:///")) {
                    String new_url = "https://appassets.androidplatform.net/assets/" + url.substring(8);
                    WebResourceResponse resp = assetLoader.shouldInterceptRequest(Uri.parse(new_url));
                    return resp;
                }
                return null;
            }

        });
    }

    public void postEvent(JSONObject event) {
        // post a JSON event to the browser
        webView.post(new Runnable() {
            @Override
            public void run() {
                String strEvent = event.toString();
                webView.loadUrl("javascript:onNativeEvent(" + strEvent + ");");
            }
        });
    }

    public void postEvent(String name, String action) {
        // post a JSON event to the browser
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:onNativeEvent({name:'" + name + "', action:'" + action + "', type:'response'});");
            }
        });
    }

}
