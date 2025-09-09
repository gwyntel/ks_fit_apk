package com.google.zxing.client.result;

import com.alipay.sdk.m.u.i;
import com.aliyun.alink.business.devicecenter.api.share.DeviceShareManager;
import com.facebook.share.internal.ShareConstants;
import com.google.zxing.Result;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class VCardResultParser extends ResultParser {
    private static final Pattern BEGIN_VCARD = Pattern.compile("BEGIN:VCARD", 2);
    private static final Pattern VCARD_LIKE_DATE = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
    private static final Pattern CR_LF_SPACE_TAB = Pattern.compile("\r\n[ \t]");
    private static final Pattern NEWLINE_ESCAPE = Pattern.compile("\\\\[nN]");
    private static final Pattern VCARD_ESCAPES = Pattern.compile("\\\\([,;\\\\])");
    private static final Pattern EQUALS = Pattern.compile(ContainerUtils.KEY_VALUE_DELIMITER);
    private static final Pattern SEMICOLON = Pattern.compile(i.f9802b);
    private static final Pattern UNESCAPED_SEMICOLONS = Pattern.compile("(?<!\\\\);+");
    private static final Pattern COMMA = Pattern.compile(",");
    private static final Pattern SEMICOLON_OR_COMMA = Pattern.compile("[;,]");

    private static String decodeQuotedPrintable(CharSequence charSequence, String str) {
        char cCharAt;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt2 = charSequence.charAt(i2);
            if (cCharAt2 != '\n' && cCharAt2 != '\r') {
                if (cCharAt2 != '=') {
                    maybeAppendFragment(byteArrayOutputStream, str, sb);
                    sb.append(cCharAt2);
                } else if (i2 < length - 2 && (cCharAt = charSequence.charAt(i2 + 1)) != '\r' && cCharAt != '\n') {
                    i2 += 2;
                    char cCharAt3 = charSequence.charAt(i2);
                    int iG = ResultParser.g(cCharAt);
                    int iG2 = ResultParser.g(cCharAt3);
                    if (iG >= 0 && iG2 >= 0) {
                        byteArrayOutputStream.write((iG << 4) + iG2);
                    }
                }
            }
            i2++;
        }
        maybeAppendFragment(byteArrayOutputStream, str, sb);
        return sb.toString();
    }

    private static void formatNames(Iterable<List<String>> iterable) {
        int iIndexOf;
        if (iterable != null) {
            for (List<String> list : iterable) {
                String str = list.get(0);
                String[] strArr = new String[5];
                int i2 = 0;
                int i3 = 0;
                while (i2 < 4 && (iIndexOf = str.indexOf(59, i3)) >= 0) {
                    strArr[i2] = str.substring(i3, iIndexOf);
                    i2++;
                    i3 = iIndexOf + 1;
                }
                strArr[i2] = str.substring(i3);
                StringBuilder sb = new StringBuilder(100);
                maybeAppendComponent(strArr, 3, sb);
                maybeAppendComponent(strArr, 1, sb);
                maybeAppendComponent(strArr, 2, sb);
                maybeAppendComponent(strArr, 0, sb);
                maybeAppendComponent(strArr, 4, sb);
                list.set(0, sb.toString().trim());
            }
        }
    }

    private static boolean isLikeVCardDate(CharSequence charSequence) {
        return charSequence == null || VCARD_LIKE_DATE.matcher(charSequence).matches();
    }

    static List k(CharSequence charSequence, String str, boolean z2, boolean z3) {
        List listL = l(charSequence, str, z2, z3);
        if (listL == null || listL.isEmpty()) {
            return null;
        }
        return (List) listL.get(0);
    }

    static List l(CharSequence charSequence, String str, boolean z2, boolean z3) {
        ArrayList arrayList;
        int i2;
        String str2;
        String str3;
        int iIndexOf;
        int i3;
        String strReplaceAll;
        int length = str.length();
        int i4 = 0;
        int i5 = 0;
        ArrayList arrayList2 = null;
        while (i5 < length) {
            int i6 = 2;
            Matcher matcher = Pattern.compile("(?:^|\n)" + ((Object) charSequence) + "(?:;([^:]*))?:", 2).matcher(str);
            if (i5 > 0) {
                i5--;
            }
            if (!matcher.find(i5)) {
                break;
            }
            int iEnd = matcher.end(i4);
            String strGroup = matcher.group(1);
            if (strGroup != null) {
                String[] strArrSplit = SEMICOLON.split(strGroup);
                int length2 = strArrSplit.length;
                int i7 = i4;
                i2 = i7;
                arrayList = null;
                str2 = null;
                str3 = null;
                while (i7 < length2) {
                    String str4 = strArrSplit[i7];
                    if (arrayList == null) {
                        arrayList = new ArrayList(1);
                    }
                    arrayList.add(str4);
                    String[] strArrSplit2 = EQUALS.split(str4, i6);
                    if (strArrSplit2.length > 1) {
                        String str5 = strArrSplit2[0];
                        String str6 = strArrSplit2[1];
                        if ("ENCODING".equalsIgnoreCase(str5) && "QUOTED-PRINTABLE".equalsIgnoreCase(str6)) {
                            i2 = 1;
                        } else if ("CHARSET".equalsIgnoreCase(str5)) {
                            str2 = str6;
                        } else if ("VALUE".equalsIgnoreCase(str5)) {
                            str3 = str6;
                        }
                    }
                    i7++;
                    i6 = 2;
                }
            } else {
                arrayList = null;
                i2 = 0;
                str2 = null;
                str3 = null;
            }
            int i8 = iEnd;
            while (true) {
                iIndexOf = str.indexOf(10, i8);
                if (iIndexOf < 0) {
                    break;
                }
                if (iIndexOf < str.length() - 1) {
                    int i9 = iIndexOf + 1;
                    if (str.charAt(i9) == ' ' || str.charAt(i9) == '\t') {
                        i8 = iIndexOf + 2;
                    }
                }
                if (i2 == 0 || ((iIndexOf <= 0 || str.charAt(iIndexOf - 1) != '=') && (iIndexOf < 2 || str.charAt(iIndexOf - 2) != '='))) {
                    break;
                }
                i8 = iIndexOf + 1;
            }
            if (iIndexOf < 0) {
                i5 = length;
                i4 = 0;
            } else if (iIndexOf <= iEnd) {
                i3 = 0;
                i4 = i3;
                i5 = iIndexOf + 1;
            } else {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                }
                if (iIndexOf > 0 && str.charAt(iIndexOf - 1) == '\r') {
                    iIndexOf--;
                }
                String strSubstring = str.substring(iEnd, iIndexOf);
                if (z2) {
                    strSubstring = strSubstring.trim();
                }
                if (i2 != 0) {
                    strReplaceAll = decodeQuotedPrintable(strSubstring, str2);
                    if (z3) {
                        strReplaceAll = UNESCAPED_SEMICOLONS.matcher(strReplaceAll).replaceAll("\n").trim();
                    }
                } else {
                    if (z3) {
                        strSubstring = UNESCAPED_SEMICOLONS.matcher(strSubstring).replaceAll("\n").trim();
                    }
                    strReplaceAll = VCARD_ESCAPES.matcher(NEWLINE_ESCAPE.matcher(CR_LF_SPACE_TAB.matcher(strSubstring).replaceAll("")).replaceAll("\n")).replaceAll("$1");
                }
                if (ShareConstants.MEDIA_URI.equals(str3)) {
                    try {
                        strReplaceAll = URI.create(strReplaceAll).getSchemeSpecificPart();
                    } catch (IllegalArgumentException unused) {
                    }
                }
                if (arrayList == null) {
                    ArrayList arrayList3 = new ArrayList(1);
                    arrayList3.add(strReplaceAll);
                    arrayList2.add(arrayList3);
                    i3 = 0;
                    i4 = i3;
                    i5 = iIndexOf + 1;
                } else {
                    i3 = 0;
                    arrayList.add(0, strReplaceAll);
                    arrayList2.add(arrayList);
                    i4 = i3;
                    i5 = iIndexOf + 1;
                }
            }
        }
        return arrayList2;
    }

    private static void maybeAppendComponent(String[] strArr, int i2, StringBuilder sb) {
        String str = strArr[i2];
        if (str == null || str.isEmpty()) {
            return;
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(strArr[i2]);
    }

    private static void maybeAppendFragment(ByteArrayOutputStream byteArrayOutputStream, String str, StringBuilder sb) {
        String str2;
        if (byteArrayOutputStream.size() > 0) {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (str == null) {
                str2 = new String(byteArray, StandardCharsets.UTF_8);
            } else {
                try {
                    str2 = new String(byteArray, str);
                } catch (UnsupportedEncodingException unused) {
                    str2 = new String(byteArray, StandardCharsets.UTF_8);
                }
            }
            byteArrayOutputStream.reset();
            sb.append(str2);
        }
    }

    private static String toPrimaryValue(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static String[] toPrimaryValues(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<List<String>> it = collection.iterator();
        while (it.hasNext()) {
            String str = it.next().get(0);
            if (str != null && !str.isEmpty()) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static String[] toTypes(Collection<List<String>> collection) {
        String strSubstring;
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (List<String> list : collection) {
            String str = list.get(0);
            if (str != null && !str.isEmpty()) {
                int i2 = 1;
                while (true) {
                    if (i2 >= list.size()) {
                        strSubstring = null;
                        break;
                    }
                    strSubstring = list.get(i2);
                    int iIndexOf = strSubstring.indexOf(61);
                    if (iIndexOf < 0) {
                        break;
                    }
                    if ("TYPE".equalsIgnoreCase(strSubstring.substring(0, iIndexOf))) {
                        strSubstring = strSubstring.substring(iIndexOf + 1);
                        break;
                    }
                    i2++;
                }
                arrayList.add(strSubstring);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String strA = ResultParser.a(result);
        Matcher matcher = BEGIN_VCARD.matcher(strA);
        if (!matcher.find() || matcher.start() != 0) {
            return null;
        }
        List listL = l("FN", strA, true, false);
        if (listL == null) {
            listL = l("N", strA, true, false);
            formatNames(listL);
        }
        List listK = k("NICKNAME", strA, true, false);
        String[] strArrSplit = listK == null ? null : COMMA.split((CharSequence) listK.get(0));
        List listL2 = l("TEL", strA, true, false);
        List listL3 = l(DeviceShareManager.SHARE_DEVICE_ACCOUNT_ATTRTYPE_EMAIL, strA, true, false);
        List listK2 = k("NOTE", strA, false, false);
        List listL4 = l("ADR", strA, true, true);
        List listK3 = k("ORG", strA, true, true);
        List listK4 = k("BDAY", strA, true, false);
        List list = (listK4 == null || isLikeVCardDate((CharSequence) listK4.get(0))) ? listK4 : null;
        List listK5 = k(ShareConstants.TITLE, strA, true, false);
        List listL5 = l("URL", strA, true, false);
        List listK6 = k("IMPP", strA, true, false);
        List listK7 = k("GEO", strA, true, false);
        String[] strArrSplit2 = listK7 == null ? null : SEMICOLON_OR_COMMA.split((CharSequence) listK7.get(0));
        return new AddressBookParsedResult(toPrimaryValues(listL), strArrSplit, null, toPrimaryValues(listL2), toTypes(listL2), toPrimaryValues(listL3), toTypes(listL3), toPrimaryValue(listK6), toPrimaryValue(listK2), toPrimaryValues(listL4), toTypes(listL4), toPrimaryValue(listK3), toPrimaryValue(list), toPrimaryValue(listK5), toPrimaryValues(listL5), (strArrSplit2 == null || strArrSplit2.length == 2) ? strArrSplit2 : null);
    }
}
