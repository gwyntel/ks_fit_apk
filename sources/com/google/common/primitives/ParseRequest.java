package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;

@ElementTypesAreNonnullByDefault
@GwtCompatible
/* loaded from: classes3.dex */
final class ParseRequest {

    /* renamed from: a, reason: collision with root package name */
    final String f14715a;

    /* renamed from: b, reason: collision with root package name */
    final int f14716b;

    private ParseRequest(String str, int i2) {
        this.f14715a = str;
        this.f14716b = i2;
    }

    static ParseRequest a(String str) {
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        char cCharAt = str.charAt(0);
        int i2 = 16;
        if (str.startsWith("0x") || str.startsWith("0X")) {
            str = str.substring(2);
        } else if (cCharAt == '#') {
            str = str.substring(1);
        } else if (cCharAt != '0' || str.length() <= 1) {
            i2 = 10;
        } else {
            str = str.substring(1);
            i2 = 8;
        }
        return new ParseRequest(str, i2);
    }
}
