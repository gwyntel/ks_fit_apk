package com.google.zxing.client.result;

import androidx.core.net.MailTo;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.stats.CodePackage;
import com.google.zxing.Result;
import java.util.List;

/* loaded from: classes3.dex */
public final class VEventResultParser extends ResultParser {
    private static String matchSingleVCardPrefixedField(CharSequence charSequence, String str, boolean z2) {
        List listK = VCardResultParser.k(charSequence, str, z2, false);
        if (listK == null || listK.isEmpty()) {
            return null;
        }
        return (String) listK.get(0);
    }

    private static String[] matchVCardPrefixedField(CharSequence charSequence, String str, boolean z2) {
        List listL = VCardResultParser.l(charSequence, str, z2, false);
        if (listL == null || listL.isEmpty()) {
            return null;
        }
        int size = listL.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr[i2] = (String) ((List) listL.get(i2)).get(0);
        }
        return strArr;
    }

    private static String stripMailto(String str) {
        return str != null ? (str.startsWith(MailTo.MAILTO_SCHEME) || str.startsWith("MAILTO:")) ? str.substring(7) : str : str;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public CalendarParsedResult parse(Result result) throws NumberFormatException {
        double d2;
        double d3;
        String strA = ResultParser.a(result);
        if (strA.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String strMatchSingleVCardPrefixedField = matchSingleVCardPrefixedField("SUMMARY", strA, true);
        String strMatchSingleVCardPrefixedField2 = matchSingleVCardPrefixedField("DTSTART", strA, true);
        if (strMatchSingleVCardPrefixedField2 == null) {
            return null;
        }
        String strMatchSingleVCardPrefixedField3 = matchSingleVCardPrefixedField("DTEND", strA, true);
        String strMatchSingleVCardPrefixedField4 = matchSingleVCardPrefixedField("DURATION", strA, true);
        String strMatchSingleVCardPrefixedField5 = matchSingleVCardPrefixedField(CodePackage.LOCATION, strA, true);
        String strStripMailto = stripMailto(matchSingleVCardPrefixedField("ORGANIZER", strA, true));
        String[] strArrMatchVCardPrefixedField = matchVCardPrefixedField("ATTENDEE", strA, true);
        if (strArrMatchVCardPrefixedField != null) {
            for (int i2 = 0; i2 < strArrMatchVCardPrefixedField.length; i2++) {
                strArrMatchVCardPrefixedField[i2] = stripMailto(strArrMatchVCardPrefixedField[i2]);
            }
        }
        String strMatchSingleVCardPrefixedField6 = matchSingleVCardPrefixedField(ShareConstants.DESCRIPTION, strA, true);
        String strMatchSingleVCardPrefixedField7 = matchSingleVCardPrefixedField("GEO", strA, true);
        if (strMatchSingleVCardPrefixedField7 == null) {
            d2 = Double.NaN;
            d3 = Double.NaN;
        } else {
            int iIndexOf = strMatchSingleVCardPrefixedField7.indexOf(59);
            if (iIndexOf < 0) {
                return null;
            }
            try {
                d2 = Double.parseDouble(strMatchSingleVCardPrefixedField7.substring(0, iIndexOf));
                d3 = Double.parseDouble(strMatchSingleVCardPrefixedField7.substring(iIndexOf + 1));
            } catch (NumberFormatException | IllegalArgumentException unused) {
                return null;
            }
        }
        return new CalendarParsedResult(strMatchSingleVCardPrefixedField, strMatchSingleVCardPrefixedField2, strMatchSingleVCardPrefixedField3, strMatchSingleVCardPrefixedField4, strMatchSingleVCardPrefixedField5, strStripMailto, strArrMatchVCardPrefixedField, strMatchSingleVCardPrefixedField6, d2, d3);
    }
}
