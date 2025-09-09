package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes3.dex */
public final class AddressBookAUResultParser extends ResultParser {
    private static String[] matchMultipleValuePrefix(String str, int i2, String str2, boolean z2) {
        ArrayList arrayList = null;
        for (int i3 = 1; i3 <= i2; i3++) {
            String strE = ResultParser.e(str + i3 + ':', str2, CharUtils.CR, z2);
            if (strE == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList(i2);
            }
            arrayList.add(strE);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String strA = ResultParser.a(result);
        if (!strA.contains("MEMORY") || !strA.contains(IOUtils.LINE_SEPARATOR_WINDOWS)) {
            return null;
        }
        String strE = ResultParser.e("NAME1:", strA, CharUtils.CR, true);
        String strE2 = ResultParser.e("NAME2:", strA, CharUtils.CR, true);
        String[] strArrMatchMultipleValuePrefix = matchMultipleValuePrefix("TEL", 3, strA, true);
        String[] strArrMatchMultipleValuePrefix2 = matchMultipleValuePrefix("MAIL", 3, strA, true);
        String strE3 = ResultParser.e("MEMORY:", strA, CharUtils.CR, false);
        String strE4 = ResultParser.e("ADD:", strA, CharUtils.CR, true);
        return new AddressBookParsedResult(ResultParser.f(strE), null, strE2, strArrMatchMultipleValuePrefix, null, strArrMatchMultipleValuePrefix2, null, null, strE3, strE4 != null ? new String[]{strE4} : null, null, null, null, null, null, null);
    }
}
