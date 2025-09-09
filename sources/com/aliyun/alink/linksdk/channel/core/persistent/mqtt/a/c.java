package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a;

import com.huawei.hms.feature.dynamic.f.e;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes2.dex */
public class c implements X509TrustManager {

    /* renamed from: a, reason: collision with root package name */
    private final X509TrustManager f10886a;

    /* renamed from: b, reason: collision with root package name */
    private final KeyStore f10887b;

    public c(InputStream inputStream) throws NoSuchAlgorithmException, KeyStoreException {
        this.f10887b = a(inputStream);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
        trustManagerFactory.init((KeyStore) null);
        this.f10886a = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];
    }

    private KeyStore a(InputStream inputStream) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance(e.f16169b).generateCertificate(inputStream);
        keyStore.setCertificateEntry(x509Certificate.getSubjectX500Principal().getName(), x509Certificate);
        return keyStore;
    }

    private X509Certificate b(X509Certificate x509Certificate, List<X509Certificate> list) {
        for (X509Certificate x509Certificate2 : list) {
            if (x509Certificate2.getSubjectDN().equals(x509Certificate.getIssuerDN())) {
                return x509Certificate2;
            }
        }
        return null;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws NoSuchAlgorithmException, CertificateException, CertPathValidatorException, InvalidAlgorithmParameterException {
        try {
            this.f10886a.checkServerTrusted(x509CertificateArr, str);
        } catch (CertificateException e2) {
            try {
                X509Certificate[] x509CertificateArrA = a(x509CertificateArr);
                CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
                CertPath certPathGenerateCertPath = CertificateFactory.getInstance("X509").generateCertPath(Arrays.asList(x509CertificateArrA));
                PKIXParameters pKIXParameters = new PKIXParameters(this.f10887b);
                pKIXParameters.setRevocationEnabled(false);
                certPathValidator.validate(certPathGenerateCertPath, pKIXParameters);
            } catch (CertificateNotYetValidException e3) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttTrustManager", "CertificateNotYetValidException " + e3);
            } catch (Exception e4) {
                if (e4.getCause() instanceof CertificateNotYetValidException) {
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttTrustManager", "validate cert failed.because system is early than cert valid . wsf will ignore this exception," + e4);
                    return;
                }
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttTrustManager", "checkServerTrusted faied." + e2);
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttTrustManager", "validate cert failed." + e4);
                throw e2;
            }
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private X509Certificate[] a(X509Certificate[] x509CertificateArr) {
        X509Certificate[] x509CertificateArr2 = new X509Certificate[x509CertificateArr.length];
        List<X509Certificate> listAsList = Arrays.asList(x509CertificateArr);
        int length = x509CertificateArr.length - 1;
        X509Certificate x509CertificateA = a(listAsList);
        x509CertificateArr2[length] = x509CertificateA;
        while (true) {
            x509CertificateA = a(x509CertificateA, listAsList);
            if (x509CertificateA == null || length <= 0) {
                break;
            }
            length--;
            x509CertificateArr2[length] = x509CertificateA;
        }
        return x509CertificateArr2;
    }

    private X509Certificate a(List<X509Certificate> list) {
        Iterator<X509Certificate> it = list.iterator();
        while (it.hasNext()) {
            X509Certificate next = it.next();
            X509Certificate x509CertificateB = b(next, list);
            if (x509CertificateB == null || x509CertificateB.equals(next)) {
                return next;
            }
        }
        return null;
    }

    private X509Certificate a(X509Certificate x509Certificate, List<X509Certificate> list) {
        for (X509Certificate x509Certificate2 : list) {
            if (x509Certificate2.getIssuerDN().equals(x509Certificate.getSubjectDN()) && !x509Certificate2.equals(x509Certificate)) {
                return x509Certificate2;
            }
        }
        return null;
    }
}
