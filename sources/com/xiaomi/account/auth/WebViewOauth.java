package com.xiaomi.account.auth;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.huawei.hms.framework.common.ContainerUtils;
import com.meizu.cloud.pushsdk.notification.model.AdvertisementOption;
import com.xiaomi.account.IXiaomiAuthResponse;
import com.xiaomi.account.XiaomiOAuthResponse;
import com.xiaomi.account.http.HttpClient;
import com.xiaomi.account.http.Request;
import com.xiaomi.account.http.Response;
import com.xiaomi.account.http.UrlConnHttpFactory;
import com.xiaomi.account.openauth.AccountAuth;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.account.openauth.BuildConfig;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.account.openauth.XiaomiOAuthResults;
import com.xiaomi.account.utils.OAuthUrlPaser;
import com.xiaomi.accountsdk.diagnosis.DiagnosisLog;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes4.dex */
public class WebViewOauth implements XiaomiOAuth {
    private static final String AUTHORIZE_PATH = XiaomiOAuthConstants.OAUTH2_HOST + "/oauth2/authorize";
    private static final String LOCALE_KEY_IN_URL = "_locale";
    private static final String TAG = "WebViewOauth";
    private String mAppId;
    private Context mContext;
    private String mRedirectUrl;

    public WebViewOauth(Context context, String str, String str2) {
        this.mContext = context;
        this.mAppId = str;
        this.mRedirectUrl = str2;
    }

    private void addLocaleIfNeeded(Bundle bundle) {
        if (bundle == null || bundle.containsKey(LOCALE_KEY_IN_URL)) {
            return;
        }
        String localeString = getLocaleString(Locale.getDefault());
        if (TextUtils.isEmpty(localeString)) {
            return;
        }
        bundle.putString(LOCALE_KEY_IN_URL, localeString);
    }

    private String getCookie(String str, String str2) {
        return str + ContainerUtils.KEY_VALUE_DELIMITER + str2;
    }

    private int getDefaultSmsSlotId() {
        if (Build.VERSION.SDK_INT < 24) {
            return 0;
        }
        try {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            List<SubscriptionInfo> activeSubscriptionInfoList = SubscriptionManager.from(this.mContext).getActiveSubscriptionInfoList();
            if (activeSubscriptionInfoList == null) {
                return 0;
            }
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.getSubscriptionId() == defaultDataSubscriptionId) {
                    return subscriptionInfo.getSimSlotIndex();
                }
            }
            return 0;
        } catch (SecurityException e2) {
            Log.e(TAG, e2.toString());
            return 0;
        }
    }

    private Intent getIntent(Activity activity, OAuthConfig oAuthConfig, IXiaomiAuthResponse iXiaomiAuthResponse) {
        Intent intent = new Intent(activity, oAuthConfig.authorizeActivityClazz);
        intent.putExtra("url", getUrl(oAuthConfig));
        intent.putExtra("redirect_uri", this.mRedirectUrl);
        intent.putExtra(AuthorizeActivityBase.KEY_KEEP_COOKIES, oAuthConfig.keepCookies);
        intent.putExtra("extra_response", new XiaomiOAuthResponse(iXiaomiAuthResponse));
        AccountAuth accountAuth = oAuthConfig.accountAuth;
        if (accountAuth != null) {
            HashMap map = new HashMap();
            map.put("userId", accountAuth.getUserId());
            map.put(AuthorizeActivityBase.KEY_SERVICETOKEN, accountAuth.getServiceToken());
            intent.putExtra(AuthorizeActivityBase.KEY_USERID, "userId=" + accountAuth.getUserId());
            intent.putExtra(AuthorizeActivityBase.KEY_SERVICETOKEN, "serviceToken=" + accountAuth.getServiceToken());
        }
        PhoneInfo phoneInfo = oAuthConfig.phoneInfo;
        if (phoneInfo != null) {
            intent.putExtras(phoneInfo.blokingGetPhoneInfo(getDefaultSmsSlotId()));
        }
        return intent;
    }

    private static String getLocaleString(Locale locale) {
        if (locale == null) {
            return null;
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(country) ? language : String.format("%s_%s", language, country);
    }

    private String getUrl(OAuthConfig oAuthConfig) {
        Bundle bundle = new Bundle();
        bundle.putString("client_id", this.mAppId);
        bundle.putString("redirect_uri", this.mRedirectUrl);
        bundle.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, oAuthConfig.responseType);
        bundle.putString("scope", oAuthConfig.scopes);
        bundle.putString("state", oAuthConfig.state);
        Boolean bool = oAuthConfig.skipConfirm;
        if (bool != null) {
            bundle.putString("skip_confirm", String.valueOf(bool));
        }
        String str = oAuthConfig.loginType;
        if (str != null) {
            bundle.putString(XiaomiOAuthConstants.EXTRA_LOGIN_TYPE, str);
        }
        if (oAuthConfig.hideSwitch != null) {
            bundle.putString("_hideSwitch", "true");
        }
        bundle.putString(AdvertisementOption.PRIORITY_VALID_TIME, "" + oAuthConfig.platform);
        bundle.putString("device_id", oAuthConfig.deviceID);
        bundle.putString("display", oAuthConfig.display);
        addLocaleIfNeeded(bundle);
        return AUTHORIZE_PATH + "?" + parseBundle(bundle);
    }

    protected static String joinMap(Map<String, String> map, String str) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i2 > 0) {
                sb.append(str);
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(value);
            i2++;
        }
        return sb.toString();
    }

    private String parseBundle(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        for (String str : bundle.keySet()) {
            String string = bundle.getString(str);
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(string)) {
                arrayList.add(new BasicNameValuePair(str, string));
            }
        }
        return URLEncodedUtils.format(arrayList, "UTF-8");
    }

    private XiaomiOAuthResults quietOAuth(OAuthConfig oAuthConfig) throws IOException, XMAuthericationException, AuthenticatorException {
        Log.i(TAG, "WebViewOauth quietOAuth start...");
        AccountAuth accountAuth = oAuthConfig.accountAuth;
        if (accountAuth == null) {
            Log.i(TAG, "WebViewOauth..quietOAuth..accountAuth is null");
            throw new AuthenticatorException();
        }
        for (int i2 = 0; i2 < 2; i2++) {
            HttpClient httpClientCreateHttpClient = new UrlConnHttpFactory().createHttpClient();
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            map2.put("userId", accountAuth.getUserId());
            map2.put(AuthorizeActivityBase.KEY_SERVICETOKEN, accountAuth.getServiceToken());
            map.put("Cookie", joinMap(map2, "; "));
            map.put("User-Agent", (System.getProperty("http.agent") + " Passport/OAuthSDK/" + BuildConfig.VERSION_NAME) + " mi/OAuthSDK/VersionCode/90");
            Response responseExcute = httpClientCreateHttpClient.excute(new Request.Builder().url(getUrl(oAuthConfig)).followRedirects(false).headers(map).build());
            DiagnosisLog.get().log("quietOAuth.response.location=" + responseExcute.location);
            String str = responseExcute.location;
            if (str == null) {
                throw new AuthenticatorException();
            }
            if (str.startsWith(this.mRedirectUrl)) {
                Bundle bundle = OAuthUrlPaser.parse(responseExcute.location);
                if (bundle != null) {
                    Log.i(TAG, "WebViewOauth.quietOAuth.sucess");
                    return XiaomiOAuthResults.parseBundle(bundle);
                }
                Log.e(TAG, "location is null need user to Authorization");
                throw new XMAuthericationException("parse url fail:" + responseExcute.location);
            }
            accountAuth.invalideServiceToken();
        }
        throw new AuthenticatorException();
    }

    @Override // com.xiaomi.account.auth.XiaomiOAuth
    public XiaomiOAuthResults startOAuth(Activity activity, OAuthConfig oAuthConfig) throws InterruptedException, OperationCanceledException, IOException, XMAuthericationException, AuthenticatorException {
        WeakReference weakReference = new WeakReference(activity);
        try {
            return quietOAuth(oAuthConfig);
        } catch (AuthenticatorException unused) {
            Log.e(TAG, "quietOAuth failed");
            Activity activity2 = (Activity) weakReference.get();
            if (activity2 == null || activity2.isFinishing()) {
                Log.e(TAG, "activity is null");
                Bundle bundle = new Bundle();
                bundle.putInt(XiaomiOAuthConstants.EXTRA_ERROR_CODE, XiaomiOAuthConstants.ERROR_NEED_AUTHORIZE);
                bundle.putString(XiaomiOAuthConstants.EXTRA_ERROR_DESCRIPTION, "activity is null");
                return XiaomiOAuthResults.parseBundle(bundle);
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ArrayList arrayList = new ArrayList();
            activity2.startActivity(getIntent(activity2, oAuthConfig, new IXiaomiAuthResponse.Stub() { // from class: com.xiaomi.account.auth.WebViewOauth.1
                @Override // com.xiaomi.account.IXiaomiAuthResponse
                public void onCancel() throws RemoteException {
                    countDownLatch.countDown();
                }

                @Override // com.xiaomi.account.IXiaomiAuthResponse
                public void onResult(Bundle bundle2) throws RemoteException {
                    arrayList.add(XiaomiOAuthResults.parseBundle(bundle2));
                    countDownLatch.countDown();
                }
            }));
            countDownLatch.await();
            if (arrayList.size() > 0) {
                return (XiaomiOAuthResults) arrayList.get(0);
            }
            throw new OperationCanceledException();
        }
    }
}
