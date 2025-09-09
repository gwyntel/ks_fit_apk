package com.huawei.secure.android.common.ssl.hostname;

import com.huawei.secure.android.common.ssl.util.c;
import com.huawei.secure.android.common.ssl.util.e;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public class BrowserCompatHostnameVerifier implements HostnameVerifier {
    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) throws CertificateParsingException {
        try {
            X509Certificate x509Certificate = (X509Certificate) sSLSession.getPeerCertificates()[0];
            e.c("", "verify: certificate is : " + x509Certificate.getSubjectDN().getName());
            b.a(str, x509Certificate, false);
            c.a();
            return true;
        } catch (SSLException e2) {
            e.b("", "SSLException : " + e2.getMessage());
            return false;
        }
    }
}
