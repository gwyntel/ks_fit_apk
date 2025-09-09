package com.huawei.hms.device;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.feature.dynamic.f.e;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.support.log.common.Base64;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.IOUtils;
import com.huawei.secure.android.common.ssl.util.h;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class a {
    public static boolean a(X509Certificate x509Certificate, List<X509Certificate> list) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateException, NoSuchProviderException {
        if (list == null || list.size() == 0) {
            return false;
        }
        if (x509Certificate == null) {
            HMSLog.e(e.f16168a, "rootCert is null,verify failed ");
            return false;
        }
        PublicKey publicKey = x509Certificate.getPublicKey();
        for (X509Certificate x509Certificate2 : list) {
            if (x509Certificate2 != null) {
                try {
                    x509Certificate2.checkValidity();
                    x509Certificate2.verify(publicKey);
                    publicKey = x509Certificate2.getPublicKey();
                } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException | CertificateException e2) {
                    HMSLog.e(e.f16168a, "verify failed " + e2.getMessage());
                }
            }
            return false;
        }
        return a(list);
    }

    public static List<X509Certificate> b(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(a(it.next()));
        }
        return arrayList;
    }

    private static List<String> c(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray.length() <= 1) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList(jSONArray.length());
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(jSONArray.getString(i2));
            }
            return arrayList;
        } catch (JSONException e2) {
            HMSLog.e(e.f16168a, "Failed to getCertChain: " + e2.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<X509Certificate> b(String str) {
        return b(c(str));
    }

    public static boolean b(X509Certificate x509Certificate, String str, String str2) {
        if (x509Certificate == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return str2.equals(a(x509Certificate.getSubjectDN().getName(), str));
    }

    public static boolean b(X509Certificate x509Certificate, String str) {
        return b(x509Certificate, e.f16171d, str);
    }

    public static X509Certificate a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return a(Base64.decode(str));
        } catch (IllegalArgumentException e2) {
            HMSLog.e(e.f16168a, "getCert failed : " + e2.getMessage());
            return null;
        }
    }

    public static X509Certificate a(byte[] bArr) {
        try {
            return (X509Certificate) CertificateFactory.getInstance(e.f16169b).generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e2) {
            HMSLog.e(e.f16168a, "Failed to get cert: " + e2.getMessage());
            return null;
        }
    }

    private static String a(String str, String str2) {
        int iIndexOf = str.toUpperCase(Locale.getDefault()).indexOf(str2 + ContainerUtils.KEY_VALUE_DELIMITER);
        if (iIndexOf == -1) {
            return null;
        }
        int iIndexOf2 = str.indexOf(",", iIndexOf);
        if (iIndexOf2 != -1) {
            return str.substring(iIndexOf + str2.length() + 1, iIndexOf2);
        }
        return str.substring(iIndexOf + str2.length() + 1);
    }

    public static boolean a(X509Certificate x509Certificate) {
        if (x509Certificate == null || x509Certificate.getBasicConstraints() == -1) {
            return false;
        }
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (5 < keyUsage.length) {
            return keyUsage[5];
        }
        return false;
    }

    public static boolean a(List<X509Certificate> list) {
        for (int i2 = 0; i2 < list.size() - 1; i2++) {
            if (!a(list.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(X509Certificate x509Certificate, String str) {
        return b(x509Certificate, "CN", str);
    }

    public static boolean a(X509Certificate x509Certificate, String str, String str2) {
        try {
            return a(x509Certificate, str.getBytes("UTF-8"), Base64.decode(str2));
        } catch (UnsupportedEncodingException | IllegalArgumentException e2) {
            HMSLog.e(e.f16168a, " plainText exception: " + e2.getMessage());
            return false;
        }
    }

    public static boolean a(X509Certificate x509Certificate, byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
            signature.initVerify(x509Certificate.getPublicKey());
            signature.update(bArr);
            return signature.verify(bArr2);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
            HMSLog.e(e.f16168a, "failed checkSignature : " + e2.getMessage());
            return false;
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x004d: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:14:0x004d */
    public static X509Certificate a(Context context, String str) throws Throwable {
        InputStream inputStream;
        InputStream inputStreamOpen;
        KeyStore keyStore;
        InputStream inputStream2 = null;
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        keyStore = KeyStore.getInstance(h.f18497e);
                        inputStreamOpen = context.getAssets().open("hmsrootcas.bks");
                    } catch (IOException e2) {
                        e = e2;
                        inputStreamOpen = null;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (KeyStoreException e3) {
                        e = e3;
                        inputStreamOpen = null;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (NoSuchAlgorithmException e4) {
                        e = e4;
                        inputStreamOpen = null;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (CertificateException e5) {
                        e = e5;
                        inputStreamOpen = null;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.closeQuietly(inputStream2);
                        throw th;
                    }
                    try {
                        keyStore.load(inputStreamOpen, "".toCharArray());
                    } catch (IOException e6) {
                        e = e6;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (KeyStoreException e7) {
                        e = e7;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (NoSuchAlgorithmException e8) {
                        e = e8;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    } catch (CertificateException e9) {
                        e = e9;
                        HMSLog.e(e.f16168a, "exception:" + e.getMessage());
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    }
                    if (!keyStore.containsAlias(str)) {
                        HMSLog.e(e.f16168a, "Not include alias " + str);
                        HMSPackageManager.getInstance(context).setUseOldCertificate(true);
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    }
                    Certificate certificate = keyStore.getCertificate(str);
                    if (!(certificate instanceof X509Certificate)) {
                        IOUtils.closeQuietly(inputStreamOpen);
                        return null;
                    }
                    X509Certificate x509Certificate = (X509Certificate) certificate;
                    x509Certificate.checkValidity();
                    IOUtils.closeQuietly(inputStreamOpen);
                    return x509Certificate;
                }
            } catch (Throwable th2) {
                th = th2;
                inputStream2 = inputStream;
            }
        }
        HMSLog.e(e.f16168a, "args are error");
        return null;
    }

    public static X509Certificate a(Context context) {
        return a(context, h.f18498f);
    }
}
