package com.squareup.okhttp.internal.framed;

import okio.ByteString;

/* loaded from: classes4.dex */
public final class Header {

    /* renamed from: a, reason: collision with root package name */
    final int f19970a;
    public final ByteString name;
    public final ByteString value;
    public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
    public static final ByteString TARGET_METHOD = ByteString.encodeUtf8(okhttp3.internal.http2.Header.TARGET_METHOD_UTF8);
    public static final ByteString TARGET_PATH = ByteString.encodeUtf8(okhttp3.internal.http2.Header.TARGET_PATH_UTF8);
    public static final ByteString TARGET_SCHEME = ByteString.encodeUtf8(okhttp3.internal.http2.Header.TARGET_SCHEME_UTF8);
    public static final ByteString TARGET_AUTHORITY = ByteString.encodeUtf8(okhttp3.internal.http2.Header.TARGET_AUTHORITY_UTF8);
    public static final ByteString TARGET_HOST = ByteString.encodeUtf8(":host");
    public static final ByteString VERSION = ByteString.encodeUtf8(":version");

    public Header(String str, String str2) {
        this(ByteString.encodeUtf8(str), ByteString.encodeUtf8(str2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Header)) {
            return false;
        }
        Header header = (Header) obj;
        return this.name.equals(header.name) && this.value.equals(header.value);
    }

    public int hashCode() {
        return ((527 + this.name.hashCode()) * 31) + this.value.hashCode();
    }

    public String toString() {
        return String.format("%s: %s", this.name.utf8(), this.value.utf8());
    }

    public Header(ByteString byteString, String str) {
        this(byteString, ByteString.encodeUtf8(str));
    }

    public Header(ByteString byteString, ByteString byteString2) {
        this.name = byteString;
        this.value = byteString2;
        this.f19970a = byteString.size() + 32 + byteString2.size();
    }
}
