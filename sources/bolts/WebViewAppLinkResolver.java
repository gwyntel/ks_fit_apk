package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.m.u.i;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class WebViewAppLinkResolver implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    private final Context context;

    public WebViewAppLinkResolver(Context context) {
        this.context = context;
    }

    private static List<Map<String, Object>> getAlList(Map<String, Object> map, String str) {
        List<Map<String, Object>> list = (List) map.get(str);
        return list == null ? Collections.emptyList() : list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static bolts.AppLink makeAppLinkFromAlData(java.util.Map<java.lang.String, java.lang.Object> r16, android.net.Uri r17) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.WebViewAppLinkResolver.makeAppLinkFromAlData(java.util.Map, android.net.Uri):bolts.AppLink");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.util.Map] */
    public static Map<String, Object> parseAlData(JSONArray jSONArray) throws JSONException {
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i2);
            String[] strArrSplit = jSONObject.getString("property").split(":");
            if (strArrSplit[0].equals(META_TAG_PREFIX)) {
                HashMap map2 = map;
                int i3 = 1;
                while (true) {
                    if (i3 >= strArrSplit.length) {
                        break;
                    }
                    List arrayList = (List) map2.get(strArrSplit[i3]);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        map2.put(strArrSplit[i3], arrayList);
                    }
                    HashMap map3 = arrayList.size() > 0 ? (Map) arrayList.get(arrayList.size() - 1) : null;
                    if (map3 == null || i3 == strArrSplit.length - 1) {
                        map2 = new HashMap();
                        arrayList.add(map2);
                    } else {
                        map2 = map3;
                    }
                    i3++;
                }
                if (jSONObject.has("content")) {
                    if (jSONObject.isNull("content")) {
                        map2.put("value", null);
                    } else {
                        map2.put("value", jSONObject.getString("content"));
                    }
                }
            }
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String readFromConnection(URLConnection uRLConnection) throws IOException {
        InputStream inputStream;
        int i2;
        if (uRLConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnection;
            try {
                inputStream = uRLConnection.getInputStream();
            } catch (Exception unused) {
                inputStream = httpURLConnection.getErrorStream();
            }
        } else {
            inputStream = uRLConnection.getInputStream();
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i3 = inputStream.read(bArr);
                i2 = 0;
                if (i3 == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i3);
            }
            String contentEncoding = uRLConnection.getContentEncoding();
            if (contentEncoding == null) {
                String[] strArrSplit = uRLConnection.getContentType().split(i.f9802b);
                int length = strArrSplit.length;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    String strTrim = strArrSplit[i2].trim();
                    if (strTrim.startsWith("charset=")) {
                        contentEncoding = strTrim.substring(8);
                        break;
                    }
                    i2++;
                }
                if (contentEncoding == null) {
                    contentEncoding = "UTF-8";
                }
            }
            String str = new String(byteArrayOutputStream.toByteArray(), contentEncoding);
            inputStream.close();
            return str;
        } catch (Throwable th) {
            inputStream.close();
            throw th;
        }
    }

    private static Uri tryCreateUrl(String str) {
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }

    @Override // bolts.AppLinkResolver
    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        final Capture capture = new Capture();
        final Capture capture2 = new Capture();
        return Task.callInBackground(new Callable<Void>() { // from class: bolts.WebViewAppLinkResolver.3
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                URL url = new URL(uri.toString());
                URLConnection uRLConnectionOpenConnection = null;
                while (url != null) {
                    uRLConnectionOpenConnection = url.openConnection();
                    if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                        ((HttpURLConnection) uRLConnectionOpenConnection).setInstanceFollowRedirects(true);
                    }
                    uRLConnectionOpenConnection.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    uRLConnectionOpenConnection.connect();
                    if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                        if (httpURLConnection.getResponseCode() >= 300 && httpURLConnection.getResponseCode() < 400) {
                            URL url2 = new URL(httpURLConnection.getHeaderField("Location"));
                            httpURLConnection.disconnect();
                            url = url2;
                        }
                    }
                    url = null;
                }
                try {
                    capture.set(WebViewAppLinkResolver.readFromConnection(uRLConnectionOpenConnection));
                    capture2.set(uRLConnectionOpenConnection.getContentType());
                    return null;
                } finally {
                    if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                        ((HttpURLConnection) uRLConnectionOpenConnection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() { // from class: bolts.WebViewAppLinkResolver.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // bolts.Continuation
            public Task<JSONArray> then(Task<Void> task) throws Exception {
                final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
                WebView webView = new WebView(WebViewAppLinkResolver.this.context);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new WebViewClient() { // from class: bolts.WebViewAppLinkResolver.2.1
                    private boolean loaded = false;

                    private void runJavaScript(WebView webView2) {
                        if (this.loaded) {
                            return;
                        }
                        this.loaded = true;
                        webView2.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onLoadResource(WebView webView2, String str) {
                        super.onLoadResource(webView2, str);
                        runJavaScript(webView2);
                    }

                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView webView2, String str) {
                        super.onPageFinished(webView2, str);
                        runJavaScript(webView2);
                    }
                });
                webView.addJavascriptInterface(new Object() { // from class: bolts.WebViewAppLinkResolver.2.2
                    @JavascriptInterface
                    public void setValue(String str) {
                        try {
                            taskCompletionSource.trySetResult(new JSONArray(str));
                        } catch (JSONException e2) {
                            taskCompletionSource.trySetError(e2);
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                webView.loadDataWithBaseURL(uri.toString(), (String) capture.get(), capture2.get() != null ? ((String) capture2.get()).split(i.f9802b)[0] : null, null, null);
                return taskCompletionSource.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() { // from class: bolts.WebViewAppLinkResolver.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // bolts.Continuation
            public AppLink then(Task<JSONArray> task) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData(task.getResult()), uri);
            }
        });
    }
}
