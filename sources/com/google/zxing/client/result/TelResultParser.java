package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class TelResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public TelParsedResult parse(Result result) {
        String str;
        String strA = ResultParser.a(result);
        if (!strA.startsWith("tel:") && !strA.startsWith("TEL:")) {
            return null;
        }
        if (strA.startsWith("TEL:")) {
            str = "tel:" + strA.substring(4);
        } else {
            str = strA;
        }
        int iIndexOf = strA.indexOf(63, 4);
        return new TelParsedResult(iIndexOf < 0 ? strA.substring(4) : strA.substring(4, iIndexOf), str, null);
    }
}
