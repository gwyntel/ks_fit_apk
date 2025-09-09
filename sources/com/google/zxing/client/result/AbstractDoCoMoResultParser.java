package com.google.zxing.client.result;

/* loaded from: classes3.dex */
abstract class AbstractDoCoMoResultParser extends ResultParser {
    AbstractDoCoMoResultParser() {
    }

    static String[] k(String str, String str2, boolean z2) {
        return ResultParser.d(str, str2, ';', z2);
    }

    static String l(String str, String str2, boolean z2) {
        return ResultParser.e(str, str2, ';', z2);
    }
}
