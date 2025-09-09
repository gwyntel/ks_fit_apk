package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class BookmarkDoCoMoResultParser extends AbstractDoCoMoResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public URIParsedResult parse(Result result) {
        String text = result.getText();
        if (!text.startsWith("MEBKM:")) {
            return null;
        }
        String strL = AbstractDoCoMoResultParser.l("TITLE:", text, true);
        String[] strArrK = AbstractDoCoMoResultParser.k("URL:", text, true);
        if (strArrK == null) {
            return null;
        }
        String str = strArrK[0];
        if (URIResultParser.k(str)) {
            return new URIParsedResult(str, strL);
        }
        return null;
    }
}
