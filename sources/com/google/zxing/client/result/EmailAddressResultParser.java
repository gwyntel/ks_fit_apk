package com.google.zxing.client.result;

import androidx.core.net.MailTo;
import com.google.zxing.Result;
import com.umeng.ccg.a;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class EmailAddressResultParser extends ResultParser {
    private static final Pattern COMMA = Pattern.compile(",");

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        String str;
        String str2;
        String str3;
        String strA = ResultParser.a(result);
        if (!strA.startsWith(MailTo.MAILTO_SCHEME) && !strA.startsWith("MAILTO:")) {
            if (EmailDoCoMoResultParser.m(strA)) {
                return new EmailAddressParsedResult(strA);
            }
            return null;
        }
        String strSubstring = strA.substring(7);
        int iIndexOf = strSubstring.indexOf(63);
        if (iIndexOf >= 0) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        try {
            String strJ = ResultParser.j(strSubstring);
            String[] strArrSplit = !strJ.isEmpty() ? COMMA.split(strJ) : null;
            Map mapH = ResultParser.h(strA);
            if (mapH != null) {
                if (strArrSplit == null && (str3 = (String) mapH.get("to")) != null) {
                    strArrSplit = COMMA.split(str3);
                }
                String str4 = (String) mapH.get(a.f21999a);
                String[] strArrSplit2 = str4 != null ? COMMA.split(str4) : null;
                String str5 = (String) mapH.get("bcc");
                String[] strArrSplit3 = str5 != null ? COMMA.split(str5) : null;
                String str6 = (String) mapH.get("subject");
                str2 = (String) mapH.get("body");
                strArr = strArrSplit;
                strArr3 = strArrSplit3;
                strArr2 = strArrSplit2;
                str = str6;
            } else {
                strArr = strArrSplit;
                strArr2 = null;
                strArr3 = null;
                str = null;
                str2 = null;
            }
            return new EmailAddressParsedResult(strArr, strArr2, strArr3, str, str2);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
