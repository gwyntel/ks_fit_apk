package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;

/* loaded from: classes5.dex */
public class Base64 {
    private static final Base64Encoder encoder;
    private static final Base64 instance;

    public class Base64Encoder extends AbstractPreferences {
        private String base64String;

        public Base64Encoder() {
            super(null, "");
            this.base64String = null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected AbstractPreferences childSpi(String str) {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String[] childrenNamesSpi() {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void flushSpi() {
        }

        public String getBase64String() {
            return this.base64String;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String getSpi(String str) {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String[] keysSpi() {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void putSpi(String str, String str2) {
            this.base64String = str2;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void removeNodeSpi() {
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void removeSpi(String str) {
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void syncSpi() {
        }
    }

    static {
        Base64 base64 = new Base64();
        instance = base64;
        encoder = base64.new Base64Encoder();
    }

    public static String encode(String str) {
        Base64Encoder base64Encoder = encoder;
        base64Encoder.putByteArray("akey", str.getBytes());
        return base64Encoder.getBase64String();
    }

    public static String encodeBytes(byte[] bArr) {
        Base64Encoder base64Encoder = encoder;
        base64Encoder.putByteArray("aKey", bArr);
        return base64Encoder.getBase64String();
    }
}
