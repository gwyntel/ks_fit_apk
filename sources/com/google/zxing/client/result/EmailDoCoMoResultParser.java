package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class EmailDoCoMoResultParser extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    static boolean m(String str) {
        return str != null && ATEXT_ALPHANUMERIC.matcher(str).matches() && str.indexOf(64) >= 0;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String[] strArrK;
        String strA = ResultParser.a(result);
        if (!strA.startsWith("MATMSG:") || (strArrK = AbstractDoCoMoResultParser.k("TO:", strA, true)) == null) {
            return null;
        }
        for (String str : strArrK) {
            if (!m(str)) {
                return null;
            }
        }
        return new EmailAddressParsedResult(strArrK, null, null, AbstractDoCoMoResultParser.l("SUB:", strA, false), AbstractDoCoMoResultParser.l("BODY:", strA, false));
    }
}
