package com.umeng.commonsdk.statistics.internal;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.CoreProtocol;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bl;
import com.umeng.analytics.pro.n;
import com.umeng.analytics.pro.q;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: e, reason: collision with root package name */
    private static boolean f22417e = false;

    /* renamed from: f, reason: collision with root package name */
    private static boolean f22418f = false;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f22419g = false;

    /* renamed from: a, reason: collision with root package name */
    private String f22420a = "10.0.0.172";

    /* renamed from: b, reason: collision with root package name */
    private int f22421b = 80;

    /* renamed from: c, reason: collision with root package name */
    private Context f22422c;

    /* renamed from: d, reason: collision with root package name */
    private b f22423d;

    public c(Context context) {
        this.f22422c = context;
    }

    private void b() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f22422c, "domain_p", "");
        String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(this.f22422c, "domain_s", "");
        if (!TextUtils.isEmpty(strImprintProperty)) {
            UMServerURL.DEFAULT_URL = DataHelper.assembleURL(strImprintProperty);
        }
        if (!TextUtils.isEmpty(strImprintProperty2)) {
            UMServerURL.SECONDARY_URL = DataHelper.assembleURL(strImprintProperty2);
        }
        String strImprintProperty3 = UMEnvelopeBuild.imprintProperty(this.f22422c, "oversea_domain_p", "");
        String strImprintProperty4 = UMEnvelopeBuild.imprintProperty(this.f22422c, "oversea_domain_s", "");
        if (!TextUtils.isEmpty(strImprintProperty3)) {
            UMServerURL.OVERSEA_DEFAULT_URL = DataHelper.assembleURL(strImprintProperty3);
        }
        if (!TextUtils.isEmpty(strImprintProperty4)) {
            UMServerURL.OVERSEA_SECONDARY_URL = DataHelper.assembleURL(strImprintProperty4);
        }
        AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.OVERSEA_DEFAULT_URL, UMServerURL.OVERSEA_SECONDARY_URL};
        if (TextUtils.isEmpty(com.umeng.commonsdk.statistics.b.f22323b)) {
            return;
        }
        if (com.umeng.commonsdk.statistics.b.f22323b.startsWith("460") || com.umeng.commonsdk.statistics.b.f22323b.startsWith("461")) {
            AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.DEFAULT_URL, UMServerURL.SECONDARY_URL};
        }
    }

    private void c() {
        if (f22419g) {
            return;
        }
        ImprintHandler.getImprintService(this.f22422c).registImprintCallback(AnalyticsConstants.CFG_FIELD_KEY, new UMImprintChangeCallback() { // from class: com.umeng.commonsdk.statistics.internal.c.1
            @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
            public void onImprintValueChanged(String str, String str2) {
                if (FieldManager.b()) {
                    FieldManager.a().a(c.this.f22422c, str2);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> apply imprint change and exit: key=" + str + "; value= " + str2);
                    UMWorkDispatch.sendEvent(c.this.f22422c, com.umeng.commonsdk.internal.a.f22259w, com.umeng.commonsdk.internal.b.a(c.this.f22422c).a(), null);
                }
            }
        });
        f22419g = true;
    }

    private void d() {
        if (f22418f) {
            return;
        }
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 注册零号报文 imprint 应答处理回调。");
        ImprintHandler.getImprintService(this.f22422c).registPreProcessCallback(AnalyticsConstants.ZERO_RESPONSE_FLAG, new UMImprintPreProcessCallback() { // from class: com.umeng.commonsdk.statistics.internal.c.2
            @Override // com.umeng.commonsdk.statistics.internal.UMImprintPreProcessCallback
            public boolean onPreProcessImprintKey(String str, String str2) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> onImprintValueChanged: key=" + str + "; value= " + str2);
                FieldManager.a().a(c.this.f22422c);
                UMWorkDispatch.sendEvent(c.this.f22422c, com.umeng.commonsdk.internal.a.f22255s, com.umeng.commonsdk.internal.b.a(c.this.f22422c).a(), null);
                ImprintHandler.getImprintService(c.this.f22422c).a(AnalyticsConstants.ZERO_RESPONSE_FLAG);
                return true;
            }
        });
        ImprintHandler.getImprintService(this.f22422c).registImprintCallback(AnalyticsConstants.CFG_FIELD_KEY, new UMImprintChangeCallback() { // from class: com.umeng.commonsdk.statistics.internal.c.3
            @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
            public void onImprintValueChanged(String str, String str2) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> first onImprintValueChanged: key=" + str + "; value= " + str2);
                FieldManager.a().a(c.this.f22422c, str2);
                UMWorkDispatch.sendEvent(c.this.f22422c, com.umeng.commonsdk.internal.a.f22255s, com.umeng.commonsdk.internal.b.a(c.this.f22422c).a(), null);
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.E)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> recv zcfg response: foregound count timer enabled.");
                    if (!UMWorkDispatch.eventHasExist()) {
                        UMWorkDispatch.sendEventEx(c.this.f22422c, q.a.E, CoreProtocol.getInstance(c.this.f22422c), null, 0L);
                    }
                }
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.F)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> recv zcfg response: FirstResumeTrigger enabled.");
                    n.a(c.this.f22422c).b(c.this.f22422c);
                }
                ImprintHandler.getImprintService(c.this.f22422c).unregistImprintCallback(AnalyticsConstants.CFG_FIELD_KEY, this);
            }
        });
        f22418f = true;
    }

    public void a(b bVar) {
        this.f22423d = bVar;
    }

    private void a() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f22422c, "domain_p", "");
        String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(this.f22422c, "domain_s", "");
        if (!TextUtils.isEmpty(strImprintProperty)) {
            UMServerURL.DEFAULT_URL = DataHelper.assembleURL(strImprintProperty);
        }
        if (!TextUtils.isEmpty(strImprintProperty2)) {
            UMServerURL.SECONDARY_URL = DataHelper.assembleURL(strImprintProperty2);
        }
        AnalyticsConstants.APPLOG_URL_LIST = new String[]{UMServerURL.DEFAULT_URL, UMServerURL.SECONDARY_URL};
    }

    private byte[] c(byte[] bArr, String str) {
        if (bh.a().b()) {
            return a(bArr, str.replace("ulogs", "cnlogs"));
        }
        return a(bArr, str);
    }

    public byte[] a(byte[] bArr, boolean z2, boolean z3, String str) throws IOException {
        if (SdkVersion.SDK_TYPE == 0) {
            a();
        } else {
            UMServerURL.DEFAULT_URL = UMServerURL.OVERSEA_DEFAULT_URL;
            UMServerURL.SECONDARY_URL = UMServerURL.OVERSEA_SECONDARY_URL;
            b();
        }
        byte[] bArrA = null;
        int i2 = 0;
        while (true) {
            String[] strArr = AnalyticsConstants.APPLOG_URL_LIST;
            if (i2 >= strArr.length) {
                break;
            }
            String str2 = strArr[i2];
            if (z3) {
                d();
            } else {
                c();
            }
            String str3 = str2 + File.separator + str;
            if (SdkVersion.SDK_TYPE != 0) {
                bArrA = a(bArr, str3);
            } else if (i2 == 0) {
                bArrA = b(bArr, str3);
            } else {
                bArrA = c(bArr, str3);
            }
            if (bArrA != null) {
                b bVar = this.f22423d;
                if (bVar != null) {
                    bVar.onRequestSucceed(z2);
                }
            } else {
                b bVar2 = this.f22423d;
                if (bVar2 != null) {
                    bVar2.onRequestFailed();
                }
                i2++;
            }
        }
        return bArrA;
    }

    private byte[] b(byte[] bArr, String str) throws IOException {
        if (bh.a().b()) {
            if (!str.contains("preulogs")) {
                str = str.replace("ulogs", "cnlogs");
            }
            String strB = bh.b(str);
            String strC = bl.b().c();
            if (!TextUtils.isEmpty(strC)) {
                str = bh.a(strC, strB);
            }
            byte[] bArrA = a(bArr, str);
            if (bArrA != null) {
                return bArrA;
            }
            String strA = bh.a().a(strB);
            return (TextUtils.isEmpty(strA) || str.equalsIgnoreCase(strA)) ? bArrA : a(bArr, strA);
        }
        return a(bArr, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x0189: IF  (r6 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:85:0x0195, block:B:80:0x0189 */
    public byte[] a(byte[] bArr, String str) throws IOException {
        HttpsURLConnection httpsURLConnection;
        OutputStream outputStream;
        OutputStream outputStream2;
        boolean z2;
        UMRTLog.i(UMRTLog.RTLOG_TAG, "[有状态] 上报url: " + str);
        try {
            try {
                b bVar = this.f22423d;
                if (bVar != null) {
                    bVar.onRequestStart();
                }
                httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                try {
                    z2 = true;
                    if (!f22417e) {
                        httpsURLConnection.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                        SSLContext sSLContext = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
                        sSLContext.init(null, null, new SecureRandom());
                        httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
                        f22417e = true;
                    }
                    httpsURLConnection.setRequestProperty("X-Umeng-UTC", String.valueOf(System.currentTimeMillis()));
                    httpsURLConnection.setRequestProperty("X-Umeng-Sdk", a.a(this.f22422c).b());
                    httpsURLConnection.setRequestProperty("Content-Type", a.a(this.f22422c).a());
                    httpsURLConnection.setRequestProperty("Msg-Type", "envelope/json");
                    httpsURLConnection.setRequestProperty("X-Umeng-Pro-Ver", "1.0.0");
                    httpsURLConnection.setRequestProperty("SM-IMP", "1");
                    httpsURLConnection.setRequestProperty("User-Agent", DeviceConfig.getCustomAgt());
                    httpsURLConnection.setConnectTimeout(30000);
                    httpsURLConnection.setReadTimeout(30000);
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoOutput(true);
                    httpsURLConnection.setDoInput(true);
                    httpsURLConnection.setUseCaches(false);
                    outputStream = httpsURLConnection.getOutputStream();
                } catch (UnknownHostException unused) {
                    outputStream = null;
                } catch (SSLHandshakeException unused2) {
                    outputStream = null;
                } catch (Throwable unused3) {
                    outputStream = null;
                }
                try {
                    outputStream.write(bArr);
                    outputStream.flush();
                    httpsURLConnection.connect();
                    b bVar2 = this.f22423d;
                    if (bVar2 != null) {
                        bVar2.onRequestEnd();
                    }
                    int responseCode = httpsURLConnection.getResponseCode();
                    String headerField = httpsURLConnection.getHeaderField("Content-Type");
                    if (TextUtils.isEmpty(headerField) || !headerField.equalsIgnoreCase("application/thrift")) {
                        z2 = false;
                    }
                    String str2 = AnalyticsConstants.OS;
                    if (responseCode == 200 && z2) {
                        InputStream inputStream = httpsURLConnection.getInputStream();
                        try {
                            byte[] streamToByteArray = HelperUtils.readStreamToByteArray(inputStream);
                            try {
                                outputStream.close();
                            } catch (Exception e2) {
                                UMCrashManager.reportCrash(this.f22422c, e2);
                            }
                            try {
                                httpsURLConnection.getInputStream().close();
                            } catch (IOException unused4) {
                            }
                            httpsURLConnection.disconnect();
                            return streamToByteArray;
                        } finally {
                            HelperUtils.safeClose(inputStream);
                        }
                    }
                    try {
                        outputStream.close();
                    } catch (Exception e3) {
                        UMCrashManager.reportCrash(this.f22422c, e3);
                    }
                    try {
                        httpsURLConnection.getInputStream().close();
                    } catch (IOException unused5) {
                    }
                    httpsURLConnection.disconnect();
                    return null;
                } catch (UnknownHostException unused6) {
                    UMLog.aq("A_10200", 2, "\\|");
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception e4) {
                            UMCrashManager.reportCrash(this.f22422c, e4);
                        }
                    }
                    if (httpsURLConnection != null) {
                        try {
                            httpsURLConnection.getInputStream().close();
                        } catch (IOException unused7) {
                        }
                        httpsURLConnection.disconnect();
                    }
                    return null;
                } catch (SSLHandshakeException unused8) {
                    UMLog.aq("A_10201", 2, "\\|");
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception e5) {
                            UMCrashManager.reportCrash(this.f22422c, e5);
                        }
                    }
                    if (httpsURLConnection != null) {
                        try {
                            httpsURLConnection.getInputStream().close();
                        } catch (IOException unused9) {
                        }
                        httpsURLConnection.disconnect();
                    }
                    return null;
                } catch (Throwable unused10) {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception e6) {
                            UMCrashManager.reportCrash(this.f22422c, e6);
                        }
                    }
                    if (httpsURLConnection != null) {
                        try {
                            httpsURLConnection.getInputStream().close();
                        } catch (IOException unused11) {
                        }
                        httpsURLConnection.disconnect();
                    }
                    return null;
                }
            } catch (UnknownHostException unused12) {
                httpsURLConnection = null;
                outputStream = null;
            } catch (SSLHandshakeException unused13) {
                httpsURLConnection = null;
                outputStream = null;
            } catch (Throwable unused14) {
                httpsURLConnection = null;
                outputStream = null;
            }
        } catch (Throwable th) {
            if (outputStream2 != null) {
                try {
                    outputStream2.close();
                } catch (Exception e7) {
                    UMCrashManager.reportCrash(this.f22422c, e7);
                }
            }
            if (str != 0) {
                try {
                    str.getInputStream().close();
                } catch (IOException unused15) {
                }
                str.disconnect();
                throw th;
            }
            throw th;
        }
    }
}
