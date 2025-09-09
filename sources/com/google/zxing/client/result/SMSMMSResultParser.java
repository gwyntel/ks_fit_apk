package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes3.dex */
public final class SMSMMSResultParser extends ResultParser {
    private static void addNumberVia(Collection<String> collection, Collection<String> collection2, String str) {
        int iIndexOf = str.indexOf(59);
        if (iIndexOf < 0) {
            collection.add(str);
            collection2.add(null);
        } else {
            collection.add(str.substring(0, iIndexOf));
            String strSubstring = str.substring(iIndexOf + 1);
            collection2.add(strSubstring.startsWith("via=") ? strSubstring.substring(4) : null);
        }
    }

    @Override // com.google.zxing.client.result.ResultParser
    public SMSParsedResult parse(Result result) {
        boolean z2;
        String str;
        String strA = ResultParser.a(result);
        String str2 = null;
        if (!strA.startsWith("sms:") && !strA.startsWith("SMS:") && !strA.startsWith("mms:") && !strA.startsWith("MMS:")) {
            return null;
        }
        Map mapH = ResultParser.h(strA);
        if (mapH == null || mapH.isEmpty()) {
            z2 = false;
            str = null;
        } else {
            str2 = (String) mapH.get("subject");
            str = (String) mapH.get("body");
            z2 = true;
        }
        int iIndexOf = strA.indexOf(63, 4);
        String strSubstring = (iIndexOf < 0 || !z2) ? strA.substring(4) : strA.substring(4, iIndexOf);
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        int i2 = -1;
        while (true) {
            int i3 = i2 + 1;
            int iIndexOf2 = strSubstring.indexOf(44, i3);
            if (iIndexOf2 <= i2) {
                addNumberVia(arrayList, arrayList2, strSubstring.substring(i3));
                return new SMSParsedResult((String[]) arrayList.toArray(new String[arrayList.size()]), (String[]) arrayList2.toArray(new String[arrayList2.size()]), str2, str);
            }
            addNumberVia(arrayList, arrayList2, strSubstring.substring(i3, iIndexOf2));
            i2 = iIndexOf2;
        }
    }
}
