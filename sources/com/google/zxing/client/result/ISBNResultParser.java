package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class ISBNResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public ISBNParsedResult parse(Result result) {
        if (result.getBarcodeFormat() != BarcodeFormat.EAN_13) {
            return null;
        }
        String strA = ResultParser.a(result);
        if (strA.length() != 13) {
            return null;
        }
        if (strA.startsWith("978") || strA.startsWith("979")) {
            return new ISBNParsedResult(strA);
        }
        return null;
    }
}
