package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes3.dex */
public final class AddressBookDoCoMoResultParser extends AbstractDoCoMoResultParser {
    private static String parseName(String str) {
        int iIndexOf = str.indexOf(44);
        if (iIndexOf < 0) {
            return str;
        }
        return str.substring(iIndexOf + 1) + ' ' + str.substring(0, iIndexOf);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String[] strArrK;
        String strA = ResultParser.a(result);
        if (!strA.startsWith("MECARD:") || (strArrK = AbstractDoCoMoResultParser.k("N:", strA, true)) == null) {
            return null;
        }
        String name = parseName(strArrK[0]);
        String strL = AbstractDoCoMoResultParser.l("SOUND:", strA, true);
        String[] strArrK2 = AbstractDoCoMoResultParser.k("TEL:", strA, true);
        String[] strArrK3 = AbstractDoCoMoResultParser.k("EMAIL:", strA, true);
        String strL2 = AbstractDoCoMoResultParser.l("NOTE:", strA, false);
        String[] strArrK4 = AbstractDoCoMoResultParser.k("ADR:", strA, true);
        String strL3 = AbstractDoCoMoResultParser.l("BDAY:", strA, true);
        return new AddressBookParsedResult(ResultParser.f(name), null, strL, strArrK2, null, strArrK3, null, null, strL2, strArrK4, null, AbstractDoCoMoResultParser.l("ORG:", strA, true), !ResultParser.b(strL3, 8) ? null : strL3, null, AbstractDoCoMoResultParser.k("URL:", strA, true), null);
    }
}
