package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class URLTOResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public URIParsedResult parse(Result result) {
        int iIndexOf;
        String strA = ResultParser.a(result);
        if ((strA.startsWith("urlto:") || strA.startsWith("URLTO:")) && (iIndexOf = strA.indexOf(58, 6)) >= 0) {
            return new URIParsedResult(strA.substring(iIndexOf + 1), iIndexOf > 6 ? strA.substring(6, iIndexOf) : null);
        }
        return null;
    }
}
