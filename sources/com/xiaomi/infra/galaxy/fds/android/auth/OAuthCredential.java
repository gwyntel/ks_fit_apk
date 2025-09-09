package com.xiaomi.infra.galaxy.fds.android.auth;

import com.alipay.sdk.m.n.a;
import com.google.gson.Gson;
import com.huawei.hms.framework.common.ContainerUtils;
import com.xiaomi.infra.galaxy.fds.android.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.android.model.StorageAccessToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.text.Typography;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

/* loaded from: classes4.dex */
public class OAuthCredential implements GalaxyFDSCredential {
    private static final String APP_ID = "APP_ID";
    private static final String OAUTH_ACCESS_TOKEN = "OAUTH_ACCESS_TOKEN";
    private static final String OAUTH_APPID = "OAUTH_APPID";
    private static final String OAUTH_MAC_ALGORITHM = "OAUTH_MAC_ALGORITHM";
    private static final String OAUTH_MAC_KEY = "OAUTH_MAC_KEY";
    private static final String OAUTH_PROVIDER = "OAUTH_PROVIDER";
    private static final String STORAGE_ACCESS_TOKEN = "STORAGE_ACCESS_TOKEN";
    private final String HEADER_VALUE = "OAuth";
    private final String appId;
    private final StorageAccessToken storageAccessToken;

    public OAuthCredential(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws GalaxyFDSClientException {
        this.appId = str2;
        this.storageAccessToken = getStorageAccessToken(str, str2, str3, str4, str5, str6, str7);
    }

    private StorageAccessToken getStorageAccessToken(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws IllegalStateException, IOException, GalaxyFDSClientException {
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(str + "/?" + STORAGE_ACCESS_TOKEN + "&" + APP_ID + ContainerUtils.KEY_VALUE_DELIMITER + str2 + "&" + OAUTH_APPID + ContainerUtils.KEY_VALUE_DELIMITER + str3 + "&" + OAUTH_ACCESS_TOKEN + ContainerUtils.KEY_VALUE_DELIMITER + str4 + "&" + OAUTH_PROVIDER + ContainerUtils.KEY_VALUE_DELIMITER + str5 + "&" + OAUTH_MAC_ALGORITHM + ContainerUtils.KEY_VALUE_DELIMITER + str7 + "&" + OAUTH_MAC_KEY + ContainerUtils.KEY_VALUE_DELIMITER + str6);
            httpGet.setHeader("Authorization", "OAuth");
            HttpResponse httpResponseExecute = defaultHttpClient.execute(httpGet);
            if (httpResponseExecute.getStatusLine().getStatusCode() == 200) {
                InputStream content = httpResponseExecute.getEntity().getContent();
                StorageAccessToken storageAccessToken = (StorageAccessToken) new Gson().fromJson((Reader) new InputStreamReader(content), StorageAccessToken.class);
                content.close();
                return storageAccessToken;
            }
            throw new GalaxyFDSClientException("Failed to get the storage access token from FDS server. URI:" + httpGet.getURI().toString() + ".Reason:" + httpResponseExecute.getStatusLine().toString());
        } catch (IOException e2) {
            throw new GalaxyFDSClientException("Failed to get the storage access token", e2);
        }
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public void addHeader(HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader("Authorization", "OAuth");
    }

    @Override // com.xiaomi.infra.galaxy.fds.android.auth.GalaxyFDSCredential
    public String addParam(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (str.indexOf(63) == -1) {
            sb.append('?');
        } else {
            sb.append(Typography.amp);
        }
        sb.append(APP_ID);
        sb.append(a.f9565h);
        sb.append(this.appId);
        sb.append(Typography.amp);
        sb.append(STORAGE_ACCESS_TOKEN);
        sb.append(a.f9565h);
        sb.append(this.storageAccessToken.getToken());
        return sb.toString();
    }
}
