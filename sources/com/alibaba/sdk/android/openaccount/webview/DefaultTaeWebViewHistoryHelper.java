package com.alibaba.sdk.android.openaccount.webview;

import android.text.TextUtils;
import android.webkit.WebBackForwardList;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.config.PropertyChangeListener;

/* loaded from: classes2.dex */
public class DefaultTaeWebViewHistoryHelper implements PropertyChangeListener {
    public static final DefaultTaeWebViewHistoryHelper INSTANCE = new DefaultTaeWebViewHistoryHelper();
    private static final String WEBVIEW_HISTORY_CAN_GO_BACK_IGNORE_URLS = "historyCanGoBackIgnoreUrls";
    private static final String WEBVIEW_HISTORY_GO_BACK_IGNORE_URLS = "historyGoBackIgnoreUrls";
    private String[] ignoreHistoryCanGoBackUrls;
    private String[] ignoreHistoryGoBackUrls;
    private int maxLoopProtectedCount;

    public DefaultTaeWebViewHistoryHelper() {
        this.ignoreHistoryGoBackUrls = new String[0];
        this.ignoreHistoryCanGoBackUrls = new String[0];
        this.maxLoopProtectedCount = 5;
        String stringProperty = ConfigManager.getInstance().getStringProperty(WEBVIEW_HISTORY_GO_BACK_IGNORE_URLS, "");
        if (!TextUtils.isEmpty(stringProperty)) {
            this.ignoreHistoryGoBackUrls = stringProperty.split("[,]");
        }
        String stringProperty2 = ConfigManager.getInstance().getStringProperty(WEBVIEW_HISTORY_CAN_GO_BACK_IGNORE_URLS, "");
        if (!TextUtils.isEmpty(stringProperty2)) {
            this.ignoreHistoryCanGoBackUrls = stringProperty2.split("[,]");
        }
        this.maxLoopProtectedCount = ConfigManager.getInstance().getIntProperty("historyHelperMaxLoopProtectedCount", 5);
        ConfigManager.getInstance().registerPropertyChangeListener(new String[]{WEBVIEW_HISTORY_GO_BACK_IGNORE_URLS, WEBVIEW_HISTORY_CAN_GO_BACK_IGNORE_URLS, "historyHelperMaxLoopProtectedCount"}, this);
    }

    public int getNextGoBackIndex(TaeWebView taeWebView, WebBackForwardList webBackForwardList) {
        int currentIndex = webBackForwardList.getCurrentIndex();
        if (currentIndex <= 0) {
            return -1;
        }
        int i2 = 1;
        boolean z2 = true;
        boolean z3 = true;
        while (true) {
            int i3 = i2 + 1;
            if (i2 > this.maxLoopProtectedCount) {
                break;
            }
            if (z2) {
                int iGoBack = goBack(webBackForwardList, currentIndex, this.ignoreHistoryGoBackUrls, 1);
                if (iGoBack != currentIndex) {
                    z3 = true;
                    currentIndex = iGoBack;
                }
                z2 = false;
            }
            if (currentIndex <= 0) {
                break;
            }
            if (z3) {
                int iGoBack2 = goBack(webBackForwardList, currentIndex, this.ignoreHistoryCanGoBackUrls, 2);
                if (iGoBack2 != currentIndex) {
                    z2 = true;
                    currentIndex = iGoBack2;
                }
                z3 = false;
            }
            if (currentIndex <= 0 || (!z3 && !z2)) {
                break;
            }
            i2 = i3;
        }
        if (currentIndex <= 0) {
            return -1;
        }
        return currentIndex - 1;
    }

    public boolean goBack(TaeWebView taeWebView) {
        WebBackForwardList webBackForwardListCopyBackForwardList = taeWebView.copyBackForwardList();
        int nextGoBackIndex = getNextGoBackIndex(taeWebView, webBackForwardListCopyBackForwardList);
        if (nextGoBackIndex < 0) {
            return false;
        }
        taeWebView.goBackOrForward(nextGoBackIndex - webBackForwardListCopyBackForwardList.getCurrentIndex());
        return true;
    }

    @Override // com.alibaba.sdk.android.openaccount.config.PropertyChangeListener
    public void propertyChanged(String str, String str2, String str3) {
        if (WEBVIEW_HISTORY_GO_BACK_IGNORE_URLS.equals(str)) {
            this.ignoreHistoryGoBackUrls = TextUtils.isEmpty(str3) ? new String[0] : str3.split("[,]");
        } else if (WEBVIEW_HISTORY_CAN_GO_BACK_IGNORE_URLS.equals(str)) {
            this.ignoreHistoryCanGoBackUrls = TextUtils.isEmpty(str3) ? new String[0] : str3.split("[,]");
        }
    }

    private int goBack(WebBackForwardList webBackForwardList, int i2, String[] strArr, int i3) {
        if (i2 <= 0) {
            return i2;
        }
        String url = webBackForwardList.getItemAtIndex(i3 == 1 ? i2 - 1 : i2).getUrl();
        if (url == null) {
            return i2;
        }
        for (String str : strArr) {
            if (url.indexOf(str) != -1) {
                return i2 - 1;
            }
        }
        return i2;
    }
}
