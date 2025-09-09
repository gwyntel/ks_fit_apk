package okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

/* loaded from: classes5.dex */
public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();

    @Nullable
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        public Builder add(String str, String... strArr) {
            if (str == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String str2 : strArr) {
                this.pins.add(new Pin(str, str2));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet(this.pins), null);
        }
    }

    static final class Pin {
        private static final String WILDCARD = "*.";

        /* renamed from: a, reason: collision with root package name */
        final String f26104a;

        /* renamed from: b, reason: collision with root package name */
        final String f26105b;

        /* renamed from: c, reason: collision with root package name */
        final String f26106c;

        /* renamed from: d, reason: collision with root package name */
        final ByteString f26107d;

        Pin(String str, String str2) {
            String strHost;
            this.f26104a = str;
            if (str.startsWith(WILDCARD)) {
                strHost = HttpUrl.get("http://" + str.substring(2)).host();
            } else {
                strHost = HttpUrl.get("http://" + str).host();
            }
            this.f26105b = strHost;
            if (str2.startsWith("sha1/")) {
                this.f26106c = "sha1/";
                this.f26107d = ByteString.decodeBase64(str2.substring(5));
            } else {
                if (!str2.startsWith("sha256/")) {
                    throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + str2);
                }
                this.f26106c = "sha256/";
                this.f26107d = ByteString.decodeBase64(str2.substring(7));
            }
            if (this.f26107d != null) {
                return;
            }
            throw new IllegalArgumentException("pins must be base64: " + str2);
        }

        boolean a(String str) {
            if (!this.f26104a.startsWith(WILDCARD)) {
                return str.equals(this.f26105b);
            }
            int iIndexOf = str.indexOf(46);
            if ((str.length() - iIndexOf) - 1 == this.f26105b.length()) {
                String str2 = this.f26105b;
                if (str.regionMatches(false, iIndexOf + 1, str2, 0, str2.length())) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Pin) {
                Pin pin = (Pin) obj;
                if (this.f26104a.equals(pin.f26104a) && this.f26106c.equals(pin.f26106c) && this.f26107d.equals(pin.f26107d)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ((((527 + this.f26104a.hashCode()) * 31) + this.f26106c.hashCode()) * 31) + this.f26107d.hashCode();
        }

        public String toString() {
            return this.f26106c + this.f26107d.base64();
        }
    }

    CertificatePinner(Set set, CertificateChainCleaner certificateChainCleaner) {
        this.pins = set;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    static ByteString b(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }

    static ByteString c(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        return "sha256/" + c((X509Certificate) certificate).base64();
    }

    List a(String str) {
        List listEmptyList = Collections.emptyList();
        for (Pin pin : this.pins) {
            if (pin.a(str)) {
                if (listEmptyList.isEmpty()) {
                    listEmptyList = new ArrayList();
                }
                listEmptyList.add(pin);
            }
        }
        return listEmptyList;
    }

    public void check(String str, List<Certificate> list) throws SSLPeerUnverifiedException {
        List listA = a(str);
        if (listA.isEmpty()) {
            return;
        }
        CertificateChainCleaner certificateChainCleaner = this.certificateChainCleaner;
        if (certificateChainCleaner != null) {
            list = certificateChainCleaner.clean(list, str);
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            X509Certificate x509Certificate = (X509Certificate) list.get(i2);
            int size2 = listA.size();
            ByteString byteStringC = null;
            ByteString byteStringB = null;
            for (int i3 = 0; i3 < size2; i3++) {
                Pin pin = (Pin) listA.get(i3);
                if (pin.f26106c.equals("sha256/")) {
                    if (byteStringC == null) {
                        byteStringC = c(x509Certificate);
                    }
                    if (pin.f26107d.equals(byteStringC)) {
                        return;
                    }
                } else {
                    if (!pin.f26106c.equals("sha1/")) {
                        throw new AssertionError("unsupported hashAlgorithm: " + pin.f26106c);
                    }
                    if (byteStringB == null) {
                        byteStringB = b(x509Certificate);
                    }
                    if (pin.f26107d.equals(byteStringB)) {
                        return;
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate pinning failure!");
        sb.append("\n  Peer certificate chain:");
        int size3 = list.size();
        for (int i4 = 0; i4 < size3; i4++) {
            X509Certificate x509Certificate2 = (X509Certificate) list.get(i4);
            sb.append("\n    ");
            sb.append(pin(x509Certificate2));
            sb.append(": ");
            sb.append(x509Certificate2.getSubjectDN().getName());
        }
        sb.append("\n  Pinned certificates for ");
        sb.append(str);
        sb.append(":");
        int size4 = listA.size();
        for (int i5 = 0; i5 < size4; i5++) {
            Pin pin2 = (Pin) listA.get(i5);
            sb.append("\n    ");
            sb.append(pin2);
        }
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    CertificatePinner d(CertificateChainCleaner certificateChainCleaner) {
        return Util.equal(this.certificateChainCleaner, certificateChainCleaner) ? this : new CertificatePinner(this.pins, certificateChainCleaner);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CertificatePinner) {
            CertificatePinner certificatePinner = (CertificatePinner) obj;
            if (Util.equal(this.certificateChainCleaner, certificatePinner.certificateChainCleaner) && this.pins.equals(certificatePinner.pins)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        CertificateChainCleaner certificateChainCleaner = this.certificateChainCleaner;
        return ((certificateChainCleaner != null ? certificateChainCleaner.hashCode() : 0) * 31) + this.pins.hashCode();
    }

    public void check(String str, Certificate... certificateArr) throws SSLPeerUnverifiedException {
        check(str, Arrays.asList(certificateArr));
    }
}
