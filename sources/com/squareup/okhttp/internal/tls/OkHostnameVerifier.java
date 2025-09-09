package com.squareup.okhttp.internal.tls;

import androidx.webkit.ProxyConfig;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private OkHostnameVerifier() {
    }

    static boolean a(String str) {
        return VERIFY_AS_IP_ADDRESS.matcher(str).matches();
    }

    public static List<String> allSubjectAltNames(X509Certificate x509Certificate) throws CertificateParsingException {
        List<String> subjectAltNames = getSubjectAltNames(x509Certificate, 7);
        List<String> subjectAltNames2 = getSubjectAltNames(x509Certificate, 2);
        ArrayList arrayList = new ArrayList(subjectAltNames.size() + subjectAltNames2.size());
        arrayList.addAll(subjectAltNames);
        arrayList.addAll(subjectAltNames2);
        return arrayList;
    }

    private static List<String> getSubjectAltNames(X509Certificate x509Certificate, int i2) throws CertificateParsingException {
        Integer num;
        String str;
        ArrayList arrayList = new ArrayList();
        try {
            Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List<?> list : subjectAlternativeNames) {
                if (list != null && list.size() >= 2 && (num = (Integer) list.get(0)) != null && num.intValue() == i2 && (str = (String) list.get(1)) != null) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        } catch (CertificateParsingException unused) {
            return Collections.emptyList();
        }
    }

    private boolean verifyHostName(String str, X509Certificate x509Certificate) throws CertificateParsingException {
        String strFindMostSpecific;
        String lowerCase = str.toLowerCase(Locale.US);
        List<String> subjectAltNames = getSubjectAltNames(x509Certificate, 2);
        int size = subjectAltNames.size();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < size) {
            if (verifyHostName(lowerCase, subjectAltNames.get(i2))) {
                return true;
            }
            i2++;
            z2 = true;
        }
        if (z2 || (strFindMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific(AdvanceSetting.CLEAR_NOTIFICATION)) == null) {
            return false;
        }
        return verifyHostName(lowerCase, strFindMostSpecific);
    }

    private boolean verifyIpAddress(String str, X509Certificate x509Certificate) throws CertificateParsingException {
        List<String> subjectAltNames = getSubjectAltNames(x509Certificate, 7);
        int size = subjectAltNames.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (str.equalsIgnoreCase(subjectAltNames.get(i2))) {
                return true;
            }
        }
        return false;
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        try {
            return verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException unused) {
            return false;
        }
    }

    public boolean verify(String str, X509Certificate x509Certificate) {
        if (a(str)) {
            return verifyIpAddress(str, x509Certificate);
        }
        return verifyHostName(str, x509Certificate);
    }

    private boolean verifyHostName(String str, String str2) {
        if (str != null && str.length() != 0 && !str.startsWith(".") && !str.endsWith("..") && str2 != null && str2.length() != 0 && !str2.startsWith(".") && !str2.endsWith("..")) {
            if (!str.endsWith(".")) {
                str = str + '.';
            }
            if (!str2.endsWith(".")) {
                str2 = str2 + '.';
            }
            String lowerCase = str2.toLowerCase(Locale.US);
            if (!lowerCase.contains(ProxyConfig.MATCH_ALL_SCHEMES)) {
                return str.equals(lowerCase);
            }
            if (!lowerCase.startsWith("*.") || lowerCase.indexOf(42, 1) != -1 || str.length() < lowerCase.length() || "*.".equals(lowerCase)) {
                return false;
            }
            String strSubstring = lowerCase.substring(1);
            if (!str.endsWith(strSubstring)) {
                return false;
            }
            int length = str.length() - strSubstring.length();
            return length <= 0 || str.lastIndexOf(46, length - 1) == -1;
        }
        return false;
    }
}
