package com.huawei.secure.android.common.ssl.util;

import android.net.http.SslCertificate;
import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18482a = "b";

    /* renamed from: b, reason: collision with root package name */
    private static final int f18483b = 5;

    public static X509Certificate a(String str) {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(str.getBytes()));
        } catch (CertificateException e2) {
            e.b(f18482a, "generateX509FromStr: CertificateException" + e2.getMessage());
            return null;
        }
    }

    public static boolean b(X509Certificate[] x509CertificateArr) throws CertificateNotYetValidException, CertificateExpiredException {
        Date date = new Date();
        for (X509Certificate x509Certificate : x509CertificateArr) {
            try {
                x509Certificate.checkValidity(date);
            } catch (CertificateExpiredException e2) {
                e = e2;
                e.b(f18482a, "verifyCertificateDate: exception : " + e.getMessage());
                return false;
            } catch (CertificateNotYetValidException e3) {
                e = e3;
                e.b(f18482a, "verifyCertificateDate: exception : " + e.getMessage());
                return false;
            } catch (Exception e4) {
                e.b(f18482a, "verifyCertificateDate : exception : " + e4.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean a(X509Certificate x509Certificate, X509Certificate[] x509CertificateArr, X509CRL x509crl, String str) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        return !a(x509Certificate, x509CertificateArr) && !a(x509CertificateArr, x509crl) && a(x509CertificateArr[x509CertificateArr.length - 1], str) && b(x509CertificateArr);
    }

    public static boolean a(X509Certificate[] x509CertificateArr, X509CRL x509crl) {
        ArrayList arrayList = new ArrayList();
        for (X509Certificate x509Certificate : x509CertificateArr) {
            arrayList.add(x509Certificate.getSerialNumber());
        }
        if (x509crl == null) {
            return true;
        }
        try {
            Set<? extends X509CRLEntry> revokedCertificates = x509crl.getRevokedCertificates();
            if (revokedCertificates == null || revokedCertificates.isEmpty()) {
                return true;
            }
            Iterator<? extends X509CRLEntry> it = revokedCertificates.iterator();
            while (it.hasNext()) {
                if (arrayList.contains(it.next().getSerialNumber())) {
                    e.b(f18482a, "verify: certificate revoked");
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            e.b(f18482a, "verify: revoked verify exception : " + e2.getMessage());
            return false;
        }
    }

    public static boolean a(X509Certificate x509Certificate, String str) {
        if (str.equals(x509Certificate.getSubjectDN().getName())) {
            return true;
        }
        e.b(f18482a, "verify: subject name is error");
        return false;
    }

    public static boolean a(X509Certificate x509Certificate, X509Certificate x509Certificate2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        try {
            x509Certificate2.verify(x509Certificate.getPublicKey());
            if (b(new X509Certificate[]{x509Certificate, x509Certificate2})) {
                return true;
            }
            e.b(f18482a, "verify: date not right");
            return false;
        } catch (InvalidKeyException e2) {
            e.b(f18482a, "verify: publickey InvalidKeyException " + e2.getMessage());
            return false;
        } catch (NoSuchAlgorithmException e3) {
            e.b(f18482a, "verify: publickey NoSuchAlgorithmException " + e3.getMessage());
            return false;
        } catch (NoSuchProviderException e4) {
            e.b(f18482a, "verify: publickey NoSuchProviderException " + e4.getMessage());
            return false;
        } catch (SignatureException e5) {
            e.b(f18482a, "verify: publickey SignatureException " + e5.getMessage());
            return false;
        } catch (CertificateException e6) {
            e.b(f18482a, "verify: publickey CertificateException " + e6.getMessage());
            return false;
        } catch (Exception e7) {
            e.b(f18482a, "verify: Exception " + e7.getMessage());
            return false;
        }
    }

    public static boolean a(X509Certificate x509Certificate, X509Certificate[] x509CertificateArr) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        Principal principal = null;
        int i2 = 0;
        while (i2 < x509CertificateArr.length) {
            X509Certificate x509Certificate2 = x509CertificateArr[i2];
            Principal issuerDN = x509Certificate2.getIssuerDN();
            Principal subjectDN = x509Certificate2.getSubjectDN();
            if (principal != null) {
                if (issuerDN.equals(principal)) {
                    x509CertificateArr[i2].verify(x509CertificateArr[i2 - 1].getPublicKey());
                } else {
                    e.b(f18482a, "verify: principalIssuer not match");
                    return false;
                }
            }
            i2++;
            principal = subjectDN;
        }
        return a(x509Certificate, x509CertificateArr[0]) && b(x509CertificateArr) && a(x509Certificate) && a(x509CertificateArr);
    }

    public static X509Certificate a(SslCertificate sslCertificate) {
        byte[] byteArray = SslCertificate.saveState(sslCertificate).getByteArray("x509-certificate");
        if (byteArray != null) {
            try {
                return (X509Certificate) CertificateFactory.getInstance(com.huawei.hms.feature.dynamic.f.e.f16169b).generateCertificate(new ByteArrayInputStream(byteArray));
            } catch (CertificateException e2) {
                e.a(f18482a, "exception", e2);
            }
        }
        return null;
    }

    public static boolean a(X509Certificate[] x509CertificateArr) {
        for (int i2 = 0; i2 < x509CertificateArr.length - 1; i2++) {
            if (!a(x509CertificateArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(X509Certificate x509Certificate) {
        if (x509Certificate == null || x509Certificate.getBasicConstraints() == -1) {
            return false;
        }
        return x509Certificate.getKeyUsage()[5];
    }
}
