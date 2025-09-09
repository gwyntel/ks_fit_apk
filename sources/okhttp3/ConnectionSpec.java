package okhttp3;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

/* loaded from: classes5.dex */
public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES;
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS;
    private static final CipherSuite[] RESTRICTED_CIPHER_SUITES;
    public static final ConnectionSpec RESTRICTED_TLS;

    /* renamed from: a, reason: collision with root package name */
    final boolean f26113a;

    /* renamed from: b, reason: collision with root package name */
    final boolean f26114b;

    /* renamed from: c, reason: collision with root package name */
    final String[] f26115c;

    /* renamed from: d, reason: collision with root package name */
    final String[] f26116d;

    static {
        CipherSuite cipherSuite = CipherSuite.TLS_AES_128_GCM_SHA256;
        CipherSuite cipherSuite2 = CipherSuite.TLS_AES_256_GCM_SHA384;
        CipherSuite cipherSuite3 = CipherSuite.TLS_CHACHA20_POLY1305_SHA256;
        CipherSuite cipherSuite4 = CipherSuite.TLS_AES_128_CCM_SHA256;
        CipherSuite cipherSuite5 = CipherSuite.TLS_AES_256_CCM_8_SHA256;
        CipherSuite cipherSuite6 = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite7 = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
        CipherSuite cipherSuite8 = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite9 = CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384;
        CipherSuite cipherSuite10 = CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256;
        CipherSuite cipherSuite11 = CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256;
        CipherSuite[] cipherSuiteArr = {cipherSuite, cipherSuite2, cipherSuite3, cipherSuite4, cipherSuite5, cipherSuite6, cipherSuite7, cipherSuite8, cipherSuite9, cipherSuite10, cipherSuite11};
        RESTRICTED_CIPHER_SUITES = cipherSuiteArr;
        CipherSuite[] cipherSuiteArr2 = {cipherSuite, cipherSuite2, cipherSuite3, cipherSuite4, cipherSuite5, cipherSuite6, cipherSuite7, cipherSuite8, cipherSuite9, cipherSuite10, cipherSuite11, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
        APPROVED_CIPHER_SUITES = cipherSuiteArr2;
        Builder builderCipherSuites = new Builder(true).cipherSuites(cipherSuiteArr);
        TlsVersion tlsVersion = TlsVersion.TLS_1_3;
        TlsVersion tlsVersion2 = TlsVersion.TLS_1_2;
        RESTRICTED_TLS = builderCipherSuites.tlsVersions(tlsVersion, tlsVersion2).supportsTlsExtensions(true).build();
        Builder builderCipherSuites2 = new Builder(true).cipherSuites(cipherSuiteArr2);
        TlsVersion tlsVersion3 = TlsVersion.TLS_1_0;
        MODERN_TLS = builderCipherSuites2.tlsVersions(tlsVersion, tlsVersion2, TlsVersion.TLS_1_1, tlsVersion3).supportsTlsExtensions(true).build();
        COMPATIBLE_TLS = new Builder(true).cipherSuites(cipherSuiteArr2).tlsVersions(tlsVersion3).supportsTlsExtensions(true).build();
        CLEARTEXT = new Builder(false).build();
    }

    ConnectionSpec(Builder builder) {
        this.f26113a = builder.f26117a;
        this.f26115c = builder.f26118b;
        this.f26116d = builder.f26119c;
        this.f26114b = builder.f26120d;
    }

    private ConnectionSpec supportedSpec(SSLSocket sSLSocket, boolean z2) {
        String[] strArrIntersect = this.f26115c != null ? Util.intersect(CipherSuite.f26108b, sSLSocket.getEnabledCipherSuites(), this.f26115c) : sSLSocket.getEnabledCipherSuites();
        String[] strArrIntersect2 = this.f26116d != null ? Util.intersect(Util.NATURAL_ORDER, sSLSocket.getEnabledProtocols(), this.f26116d) : sSLSocket.getEnabledProtocols();
        String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
        int iIndexOf = Util.indexOf(CipherSuite.f26108b, supportedCipherSuites, "TLS_FALLBACK_SCSV");
        if (z2 && iIndexOf != -1) {
            strArrIntersect = Util.concat(strArrIntersect, supportedCipherSuites[iIndexOf]);
        }
        return new Builder(this).cipherSuites(strArrIntersect).tlsVersions(strArrIntersect2).build();
    }

    void a(SSLSocket sSLSocket, boolean z2) {
        ConnectionSpec connectionSpecSupportedSpec = supportedSpec(sSLSocket, z2);
        String[] strArr = connectionSpecSupportedSpec.f26116d;
        if (strArr != null) {
            sSLSocket.setEnabledProtocols(strArr);
        }
        String[] strArr2 = connectionSpecSupportedSpec.f26115c;
        if (strArr2 != null) {
            sSLSocket.setEnabledCipherSuites(strArr2);
        }
    }

    @Nullable
    public List<CipherSuite> cipherSuites() {
        String[] strArr = this.f26115c;
        if (strArr != null) {
            return CipherSuite.a(strArr);
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ConnectionSpec)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ConnectionSpec connectionSpec = (ConnectionSpec) obj;
        boolean z2 = this.f26113a;
        if (z2 != connectionSpec.f26113a) {
            return false;
        }
        return !z2 || (Arrays.equals(this.f26115c, connectionSpec.f26115c) && Arrays.equals(this.f26116d, connectionSpec.f26116d) && this.f26114b == connectionSpec.f26114b);
    }

    public int hashCode() {
        if (this.f26113a) {
            return ((((527 + Arrays.hashCode(this.f26115c)) * 31) + Arrays.hashCode(this.f26116d)) * 31) + (!this.f26114b ? 1 : 0);
        }
        return 17;
    }

    public boolean isCompatible(SSLSocket sSLSocket) {
        if (!this.f26113a) {
            return false;
        }
        String[] strArr = this.f26116d;
        if (strArr != null && !Util.nonEmptyIntersection(Util.NATURAL_ORDER, strArr, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        String[] strArr2 = this.f26115c;
        return strArr2 == null || Util.nonEmptyIntersection(CipherSuite.f26108b, strArr2, sSLSocket.getEnabledCipherSuites());
    }

    public boolean isTls() {
        return this.f26113a;
    }

    public boolean supportsTlsExtensions() {
        return this.f26114b;
    }

    @Nullable
    public List<TlsVersion> tlsVersions() {
        String[] strArr = this.f26116d;
        if (strArr != null) {
            return TlsVersion.forJavaNames(strArr);
        }
        return null;
    }

    public String toString() {
        if (!this.f26113a) {
            return "ConnectionSpec()";
        }
        return "ConnectionSpec(cipherSuites=" + (this.f26115c != null ? cipherSuites().toString() : "[all enabled]") + ", tlsVersions=" + (this.f26116d != null ? tlsVersions().toString() : "[all enabled]") + ", supportsTlsExtensions=" + this.f26114b + ")";
    }

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        boolean f26117a;

        /* renamed from: b, reason: collision with root package name */
        String[] f26118b;

        /* renamed from: c, reason: collision with root package name */
        String[] f26119c;

        /* renamed from: d, reason: collision with root package name */
        boolean f26120d;

        Builder(boolean z2) {
            this.f26117a = z2;
        }

        public Builder allEnabledCipherSuites() {
            if (!this.f26117a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            this.f26118b = null;
            return this;
        }

        public Builder allEnabledTlsVersions() {
            if (!this.f26117a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            this.f26119c = null;
            return this;
        }

        public ConnectionSpec build() {
            return new ConnectionSpec(this);
        }

        public Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (!this.f26117a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            String[] strArr = new String[cipherSuiteArr.length];
            for (int i2 = 0; i2 < cipherSuiteArr.length; i2++) {
                strArr[i2] = cipherSuiteArr[i2].f26109a;
            }
            return cipherSuites(strArr);
        }

        public Builder supportsTlsExtensions(boolean z2) {
            if (!this.f26117a) {
                throw new IllegalStateException("no TLS extensions for cleartext connections");
            }
            this.f26120d = z2;
            return this;
        }

        public Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (!this.f26117a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            String[] strArr = new String[tlsVersionArr.length];
            for (int i2 = 0; i2 < tlsVersionArr.length; i2++) {
                strArr[i2] = tlsVersionArr[i2].javaName;
            }
            return tlsVersions(strArr);
        }

        public Builder(ConnectionSpec connectionSpec) {
            this.f26117a = connectionSpec.f26113a;
            this.f26118b = connectionSpec.f26115c;
            this.f26119c = connectionSpec.f26116d;
            this.f26120d = connectionSpec.f26114b;
        }

        public Builder cipherSuites(String... strArr) {
            if (this.f26117a) {
                if (strArr.length != 0) {
                    this.f26118b = (String[]) strArr.clone();
                    return this;
                }
                throw new IllegalArgumentException("At least one cipher suite is required");
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public Builder tlsVersions(String... strArr) {
            if (this.f26117a) {
                if (strArr.length != 0) {
                    this.f26119c = (String[]) strArr.clone();
                    return this;
                }
                throw new IllegalArgumentException("At least one TLS version is required");
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
    }
}
