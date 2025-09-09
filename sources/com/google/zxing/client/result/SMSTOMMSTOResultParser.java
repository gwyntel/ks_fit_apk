package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class SMSTOMMSTOResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public SMSParsedResult parse(Result result) {
        String strSubstring;
        String strA = ResultParser.a(result);
        if (!strA.startsWith("smsto:") && !strA.startsWith("SMSTO:") && !strA.startsWith("mmsto:") && !strA.startsWith("MMSTO:")) {
            return null;
        }
        String strSubstring2 = strA.substring(6);
        int iIndexOf = strSubstring2.indexOf(58);
        if (iIndexOf >= 0) {
            strSubstring = strSubstring2.substring(iIndexOf + 1);
            strSubstring2 = strSubstring2.substring(0, iIndexOf);
        } else {
            strSubstring = null;
        }
        return new SMSParsedResult(strSubstring2, (String) null, (String) null, strSubstring);
    }
}
