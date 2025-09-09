package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class WifiResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public WifiParsedResult parse(Result result) {
        String strSubstring;
        String strE;
        String strA = ResultParser.a(result);
        if (!strA.startsWith("WIFI:") || (strE = ResultParser.e("S:", (strSubstring = strA.substring(5)), ';', false)) == null || strE.isEmpty()) {
            return null;
        }
        String strE2 = ResultParser.e("P:", strSubstring, ';', false);
        String strE3 = ResultParser.e("T:", strSubstring, ';', false);
        if (strE3 == null) {
            strE3 = "nopass";
        }
        return new WifiParsedResult(strE3, strE, strE2, Boolean.parseBoolean(ResultParser.e("H:", strSubstring, ';', false)), ResultParser.e("I:", strSubstring, ';', false), ResultParser.e("A:", strSubstring, ';', false), ResultParser.e("E:", strSubstring, ';', false), ResultParser.e("H:", strSubstring, ';', false));
    }
}
