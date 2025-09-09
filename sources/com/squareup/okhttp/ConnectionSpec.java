package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

/* loaded from: classes4.dex */
public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES;
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS;

    /* renamed from: a, reason: collision with root package name */
    final boolean f19884a;

    /* renamed from: b, reason: collision with root package name */
    final boolean f19885b;
    private final String[] cipherSuites;
    private final String[] tlsVersions;

    static {
        CipherSuite[] cipherSuiteArr = {CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        APPROVED_CIPHER_SUITES = cipherSuiteArr;
        Builder builderCipherSuites = new Builder(true).cipherSuites(cipherSuiteArr);
        TlsVersion tlsVersion = TlsVersion.TLS_1_0;
        ConnectionSpec connectionSpecBuild = builderCipherSuites.tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, tlsVersion).supportsTlsExtensions(true).build();
        MODERN_TLS = connectionSpecBuild;
        COMPATIBLE_TLS = new Builder(connectionSpecBuild).tlsVersions(tlsVersion).supportsTlsExtensions(true).build();
        CLEARTEXT = new Builder(false).build();
    }

    private static <T> boolean contains(T[] tArr, T t2) {
        for (T t3 : tArr) {
            if (Util.equal(t2, t3)) {
                return true;
            }
        }
        return false;
    }

    private static boolean nonEmptyIntersection(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length != 0 && strArr2.length != 0) {
            for (String str : strArr) {
                if (contains(strArr2, str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ConnectionSpec supportedSpec(SSLSocket sSLSocket, boolean z2) {
        String[] enabledCipherSuites;
        if (this.cipherSuites != null) {
            enabledCipherSuites = (String[]) Util.intersect(String.class, this.cipherSuites, sSLSocket.getEnabledCipherSuites());
        } else {
            enabledCipherSuites = null;
        }
        if (z2 && Arrays.asList(sSLSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV")) {
            if (enabledCipherSuites == null) {
                enabledCipherSuites = sSLSocket.getEnabledCipherSuites();
            }
            int length = enabledCipherSuites.length;
            String[] strArr = new String[length + 1];
            System.arraycopy(enabledCipherSuites, 0, strArr, 0, enabledCipherSuites.length);
            strArr[length] = "TLS_FALLBACK_SCSV";
            enabledCipherSuites = strArr;
        }
        return new Builder(this).cipherSuites(enabledCipherSuites).tlsVersions((String[]) Util.intersect(String.class, this.tlsVersions, sSLSocket.getEnabledProtocols())).build();
    }

    void c(SSLSocket sSLSocket, boolean z2) {
        ConnectionSpec connectionSpecSupportedSpec = supportedSpec(sSLSocket, z2);
        sSLSocket.setEnabledProtocols(connectionSpecSupportedSpec.tlsVersions);
        String[] strArr = connectionSpecSupportedSpec.cipherSuites;
        if (strArr != null) {
            sSLSocket.setEnabledCipherSuites(strArr);
        }
    }

    public List<CipherSuite> cipherSuites() {
        String[] strArr = this.cipherSuites;
        if (strArr == null) {
            return null;
        }
        CipherSuite[] cipherSuiteArr = new CipherSuite[strArr.length];
        int i2 = 0;
        while (true) {
            String[] strArr2 = this.cipherSuites;
            if (i2 >= strArr2.length) {
                return Util.immutableList(cipherSuiteArr);
            }
            cipherSuiteArr[i2] = CipherSuite.forJavaName(strArr2[i2]);
            i2++;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConnectionSpec)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ConnectionSpec connectionSpec = (ConnectionSpec) obj;
        boolean z2 = this.f19884a;
        if (z2 != connectionSpec.f19884a) {
            return false;
        }
        return !z2 || (Arrays.equals(this.cipherSuites, connectionSpec.cipherSuites) && Arrays.equals(this.tlsVersions, connectionSpec.tlsVersions) && this.f19885b == connectionSpec.f19885b);
    }

    public int hashCode() {
        if (this.f19884a) {
            return ((((527 + Arrays.hashCode(this.cipherSuites)) * 31) + Arrays.hashCode(this.tlsVersions)) * 31) + (!this.f19885b ? 1 : 0);
        }
        return 17;
    }

    public boolean isCompatible(SSLSocket sSLSocket) {
        if (!this.f19884a) {
            return false;
        }
        if (!nonEmptyIntersection(this.tlsVersions, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        if (this.cipherSuites == null) {
            return sSLSocket.getEnabledCipherSuites().length > 0;
        }
        return nonEmptyIntersection(this.cipherSuites, sSLSocket.getEnabledCipherSuites());
    }

    public boolean isTls() {
        return this.f19884a;
    }

    public boolean supportsTlsExtensions() {
        return this.f19885b;
    }

    public List<TlsVersion> tlsVersions() {
        TlsVersion[] tlsVersionArr = new TlsVersion[this.tlsVersions.length];
        int i2 = 0;
        while (true) {
            String[] strArr = this.tlsVersions;
            if (i2 >= strArr.length) {
                return Util.immutableList(tlsVersionArr);
            }
            tlsVersionArr[i2] = TlsVersion.forJavaName(strArr[i2]);
            i2++;
        }
    }

    public String toString() {
        if (!this.f19884a) {
            return "ConnectionSpec()";
        }
        List<CipherSuite> listCipherSuites = cipherSuites();
        return "ConnectionSpec(cipherSuites=" + (listCipherSuites == null ? "[use default]" : listCipherSuites.toString()) + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.f19885b + ")";
    }

    public static final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        Builder(boolean z2) {
            this.tls = z2;
        }

        public ConnectionSpec build() {
            return new ConnectionSpec(this);
        }

        public Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (!this.tls) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            String[] strArr = new String[cipherSuiteArr.length];
            for (int i2 = 0; i2 < cipherSuiteArr.length; i2++) {
                strArr[i2] = cipherSuiteArr[i2].javaName;
            }
            this.cipherSuites = strArr;
            return this;
        }

        public Builder supportsTlsExtensions(boolean z2) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS extensions for cleartext connections");
            }
            this.supportsTlsExtensions = z2;
            return this;
        }

        public Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            if (tlsVersionArr.length == 0) {
                throw new IllegalArgumentException("At least one TlsVersion is required");
            }
            String[] strArr = new String[tlsVersionArr.length];
            for (int i2 = 0; i2 < tlsVersionArr.length; i2++) {
                strArr[i2] = tlsVersionArr[i2].javaName;
            }
            this.tlsVersions = strArr;
            return this;
        }

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.f19884a;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.f19885b;
        }

        public Builder cipherSuites(String... strArr) {
            if (!this.tls) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            if (strArr == null) {
                this.cipherSuites = null;
            } else {
                this.cipherSuites = (String[]) strArr.clone();
            }
            return this;
        }

        public Builder tlsVersions(String... strArr) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            if (strArr == null) {
                this.tlsVersions = null;
            } else {
                this.tlsVersions = (String[]) strArr.clone();
            }
            return this;
        }
    }

    private ConnectionSpec(Builder builder) {
        this.f19884a = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.f19885b = builder.supportsTlsExtensions;
    }
}
