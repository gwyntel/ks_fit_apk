package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class BizcardResultParser extends AbstractDoCoMoResultParser {
    private static String buildName(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        return str + ' ' + str2;
    }

    private static String[] buildPhoneNumbers(String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList(3);
        if (str != null) {
            arrayList.add(str);
        }
        if (str2 != null) {
            arrayList.add(str2);
        }
        if (str3 != null) {
            arrayList.add(str3);
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[size]);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String strA = ResultParser.a(result);
        if (!strA.startsWith("BIZCARD:")) {
            return null;
        }
        String strBuildName = buildName(AbstractDoCoMoResultParser.l("N:", strA, true), AbstractDoCoMoResultParser.l("X:", strA, true));
        String strL = AbstractDoCoMoResultParser.l("T:", strA, true);
        String strL2 = AbstractDoCoMoResultParser.l("C:", strA, true);
        return new AddressBookParsedResult(ResultParser.f(strBuildName), null, null, buildPhoneNumbers(AbstractDoCoMoResultParser.l("B:", strA, true), AbstractDoCoMoResultParser.l("M:", strA, true), AbstractDoCoMoResultParser.l("F:", strA, true)), null, ResultParser.f(AbstractDoCoMoResultParser.l("E:", strA, true)), null, null, null, AbstractDoCoMoResultParser.k("A:", strA, true), null, strL2, null, strL, null, null);
    }
}
