package com.xiaomi.accountsdk.diagnosis;

import android.util.Pair;
import com.facebook.internal.AnalyticsEvents;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* loaded from: classes4.dex */
class f implements DiagnosisLogInterface {

    /* renamed from: a, reason: collision with root package name */
    private static final Random f23317a = new Random();

    private enum a {
        GET,
        POST
    }

    f() {
    }

    private static String a() {
        return Integer.toHexString(f23317a.nextInt());
    }

    private static Pair<String, String> b(String str) throws UnknownHostException {
        String string;
        String string2;
        try {
            string = InetAddress.getByName(str).toString();
        } catch (UnknownHostException unused) {
            string = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        try {
            InetAddress[] allByName = InetAddress.getAllByName(str);
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (InetAddress inetAddress : allByName) {
                sb.append(inetAddress);
                sb.append(",");
            }
            sb.append(")");
            string2 = sb.toString();
        } catch (UnknownHostException unused2) {
            string2 = "(Unknown)";
        }
        return Pair.create(string, string2);
    }

    private static String c(String str) {
        try {
            return new URI(str).getHost();
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void log(String str) {
        a(str);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public String logGetRequest(String str, Map<String, String> map, String str2, Map<String, String> map2, Map<String, String> map3) {
        return a(a.GET, str, map, str2, null, map3, map2);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public String logPostRequest(String str, Map<String, String> map, String str2, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4) {
        return a(a.POST, str, map, str2, map2, map3, map4);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logRequestException(Exception exc) {
        a("RequestException: " + a(exc));
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponse(String str, String str2, Map<String, List<String>> map, Map<String, String> map2) {
        a("request id: " + str + "\nraw response body: " + str2 + "\nresponse headers: " + map + "\nresponse cookies: " + map2);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponseCode(String str, int i2) {
        a("Response code=" + i2 + ", request id=" + str);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponseDecryptedBody(String str) {
        a("DecryptedBody: " + str);
    }

    private static String a(a aVar, String str, Map<String, String> map, String str2, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4) throws UnknownHostException {
        String strA = a();
        Pair<String, String> pairB = b(c(str));
        String str3 = (String) pairB.first;
        String str4 = (String) pairB.second;
        StringBuilder sb = new StringBuilder();
        sb.append(aVar.toString());
        sb.append(" request id=");
        sb.append(strA);
        sb.append("\n");
        sb.append("fullUrl: ");
        sb.append(str2);
        sb.append("\n");
        sb.append("addr: ");
        sb.append(str3);
        sb.append(", addr list: ");
        sb.append(str4);
        sb.append("\n");
        sb.append("url: ");
        sb.append(str);
        sb.append("\n");
        sb.append("urlParams: ");
        sb.append(map);
        sb.append("\n");
        if (map2 != null) {
            sb.append("postParams: ");
            sb.append(map2);
            sb.append("\n");
        }
        sb.append("headers: ");
        sb.append(map4);
        sb.append("\n");
        sb.append("cookies: ");
        sb.append(map3);
        a(sb.toString());
        return strA;
    }

    private static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, false);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    private static void a(String str) {
        com.xiaomi.accountsdk.diagnosis.encrypt.d.a("AccountDiagnosisLogger", str);
    }
}
