package com.huawei.hms.scankit.p;

import com.aliyun.alink.business.devicecenter.api.share.DeviceShareManager;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.scan.HmsScan;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class x7 extends t6 {

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f18000b = Pattern.compile("\r?\n[ \t]");

    /* renamed from: c, reason: collision with root package name */
    private static final Pattern f18001c = Pattern.compile(ContainerUtils.KEY_VALUE_DELIMITER);

    /* renamed from: d, reason: collision with root package name */
    private static final Pattern f18002d = Pattern.compile(com.alipay.sdk.m.u.i.f9802b);

    /* renamed from: e, reason: collision with root package name */
    private static final Pattern f18003e = Pattern.compile("(?<!\\\\);+");

    /* renamed from: f, reason: collision with root package name */
    static final String[] f18004f = new String[0];

    protected static int a(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 'W';
        }
        if (c2 < 'A' || c2 > 'F') {
            return -1;
        }
        return c2 - '7';
    }

    private static HmsScan.TelPhoneNumber[] c(String[] strArr, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            return new HmsScan.TelPhoneNumber[0];
        }
        HmsScan.TelPhoneNumber[] telPhoneNumberArr = new HmsScan.TelPhoneNumber[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            int i3 = HmsScan.TelPhoneNumber.OTHER_USE_TYPE;
            HmsScan.TelPhoneNumber telPhoneNumber = new HmsScan.TelPhoneNumber(i3, strArr2[i2]);
            String str = strArr[i2];
            if (str != null) {
                if (str.equals("WORK")) {
                    telPhoneNumber.useType = HmsScan.TelPhoneNumber.OFFICE_USE_TYPE;
                } else if (strArr[i2].equals("HOME")) {
                    telPhoneNumber.useType = HmsScan.TelPhoneNumber.RESIDENTIAL_USE_TYPE;
                } else if (strArr[i2].equals("CELL")) {
                    telPhoneNumber.useType = HmsScan.TelPhoneNumber.CELLPHONE_NUMBER_USE_TYPE;
                } else if (strArr[i2].equals("FAX")) {
                    telPhoneNumber.useType = HmsScan.TelPhoneNumber.FAX_USE_TYPE;
                } else {
                    telPhoneNumber.useType = i3;
                }
            }
            telPhoneNumberArr[i2] = telPhoneNumber;
        }
        return telPhoneNumberArr;
    }

    @Override // com.huawei.hms.scankit.p.t6
    public HmsScan b(s6 s6Var) {
        String strA = t6.a(s6Var);
        if (!strA.startsWith("BEGIN:VCARD")) {
            return null;
        }
        String str = strA + "\n";
        String strA2 = a("N", str, true, false);
        if (strA2 == null || strA2.isEmpty() || strA2.split(com.alipay.sdk.m.u.i.f9802b).length == 0) {
            return null;
        }
        String strA3 = a("FN", str, true, false);
        if (strA3 == null || strA3.isEmpty()) {
            strA3 = c(strA2);
        }
        String str2 = strA3;
        List<List<String>> listB = b("TEL", str, true, false);
        List<List<String>> listB2 = b(DeviceShareManager.SHARE_DEVICE_ACCOUNT_ATTRTYPE_EMAIL, str, true, false);
        List<List<String>> listB3 = b("ADR", str, true, true);
        return new HmsScan(s6Var.k(), t6.a(s6Var.c()), str2, HmsScan.CONTACT_DETAIL_FORM, s6Var.i(), t6.a(s6Var.j()), null, new z6(new HmsScan.ContactDetail(a(strA2, str2), a(ShareConstants.TITLE, str, true, false), a("ORG", str, true, true), c(b(listB), a(listB)), b(b(listB2), a(listB2)), a(b(listB3), a(listB3)), a(b("URL", str, true, false)), null)));
    }

    static int a(int i2, String str, boolean z2) {
        int iIndexOf;
        while (true) {
            iIndexOf = str.indexOf(10, i2);
            if (iIndexOf < 0) {
                break;
            }
            if (iIndexOf < str.length() - 1) {
                int i3 = iIndexOf + 1;
                if (str.charAt(i3) == ' ' || str.charAt(i3) == '\t') {
                    i2 = iIndexOf + 2;
                }
            }
            if (!z2 || (!a(iIndexOf, 1, str) && !a(iIndexOf, 2, str))) {
                break;
            }
            i2 = iIndexOf + 1;
        }
        return iIndexOf;
    }

    static void a(String str, boolean z2, boolean z3, String str2, boolean z4, List<String> list, List<List<String>> list2) {
        String strReplaceAll;
        if (z2) {
            str = str.trim();
        }
        if (z3) {
            strReplaceAll = a((CharSequence) str, str2);
            if (z4) {
                strReplaceAll = f18003e.matcher(strReplaceAll).replaceAll(" ").trim();
            }
        } else {
            if (z4) {
                str = f18003e.matcher(str).replaceAll(" ").trim();
            }
            strReplaceAll = f18000b.matcher(str).replaceAll("");
        }
        if (list == null) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(strReplaceAll);
            list2.add(arrayList);
        } else {
            list.add(0, strReplaceAll);
            list2.add(list);
        }
    }

    private static String a(CharSequence charSequence, String str) {
        char cCharAt;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt2 = charSequence.charAt(i2);
            if (cCharAt2 != '\n' && cCharAt2 != '\r') {
                if (cCharAt2 != '=') {
                    a(byteArrayOutputStream, str, sb);
                    sb.append(cCharAt2);
                } else if (i2 < length - 2 && (cCharAt = charSequence.charAt(i2 + 1)) != '\r' && cCharAt != '\n') {
                    i2 += 2;
                    char cCharAt3 = charSequence.charAt(i2);
                    int iA = a(cCharAt);
                    int iA2 = a(cCharAt3);
                    if (iA >= 0 && iA2 >= 0) {
                        byteArrayOutputStream.write((iA << 4) + iA2);
                    }
                }
            }
            i2++;
        }
        a(byteArrayOutputStream, str, sb);
        return sb.toString();
    }

    private static String c(String str) {
        int iIndexOf;
        if (str == null || str.isEmpty()) {
            return null;
        }
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
        a(strArr, 3, sb);
        a(strArr, 1, sb);
        a(strArr, 2, sb);
        a(strArr, 0, sb);
        a(strArr, 4, sb);
        return sb.toString().trim();
    }

    static List<List<String>> b(CharSequence charSequence, String str, boolean z2, boolean z3) {
        boolean z4;
        String str2;
        ArrayList arrayList;
        int length = str.length();
        Pattern patternCompile = Pattern.compile("(?:^|\n)" + ((Object) charSequence) + "(?:;([^:\n(?![ |\t])]*))?:");
        int i2 = 0;
        ArrayList arrayList2 = null;
        while (i2 < length) {
            Matcher matcher = patternCompile.matcher(str);
            if (i2 > 0) {
                i2--;
            }
            if (!matcher.find(i2)) {
                break;
            }
            int iEnd = matcher.end(0);
            String strGroup = matcher.group(1);
            if (strGroup != null) {
                z4 = false;
                ArrayList arrayList3 = null;
                String str3 = null;
                for (String str4 : f18002d.split(strGroup)) {
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList(1);
                    }
                    arrayList3.add(str4);
                    String[] strArrSplit = f18001c.split(str4, 2);
                    if (strArrSplit.length > 1) {
                        if ("ENCODING".equalsIgnoreCase(strArrSplit[0]) && "QUOTED-PRINTABLE".equalsIgnoreCase(strArrSplit[1])) {
                            z4 = true;
                        } else if ("CHARSET".equalsIgnoreCase(strArrSplit[0])) {
                            str3 = strArrSplit[1];
                        }
                    }
                }
                arrayList = arrayList3;
                str2 = str3;
            } else {
                z4 = false;
                str2 = null;
                arrayList = null;
            }
            int iA = a(iEnd, str, z4);
            if (iA < 0) {
                iA = length;
            } else if (iA > iEnd) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                }
                if (iA >= 1 && str.charAt(iA - 1) == '\r') {
                    iA--;
                }
                a(str.substring(iEnd, iA), z2, z4, str2, z3, arrayList, arrayList2);
            }
            i2 = iA + 1;
        }
        return arrayList2;
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str, StringBuilder sb) {
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

    private static String a(CharSequence charSequence, String str, boolean z2, boolean z3) {
        List<List<String>> listB = b(charSequence, str, z2, z3);
        String str2 = "";
        if (listB != null && !listB.isEmpty()) {
            for (List<String> list : listB) {
                if (list.get(0) != null && !list.get(0).isEmpty()) {
                    str2 = list.get(0);
                }
            }
        }
        return str2;
    }

    private static String[] a(Collection<List<String>> collection) {
        if (collection != null && !collection.isEmpty()) {
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<List<String>> it = collection.iterator();
            while (it.hasNext()) {
                String str = it.next().get(0);
                if (str != null && !str.isEmpty()) {
                    arrayList.add(str);
                }
            }
            return (String[]) arrayList.toArray(f18004f);
        }
        return new String[0];
    }

    private static String[] b(Collection<List<String>> collection) {
        String strSubstring;
        if (collection != null && !collection.isEmpty()) {
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
                        if ("TYPE".equals(strSubstring.substring(0, iIndexOf))) {
                            strSubstring = strSubstring.substring(iIndexOf + 1);
                            break;
                        }
                        i2++;
                    }
                    arrayList.add(strSubstring);
                }
            }
            return (String[]) arrayList.toArray(f18004f);
        }
        return new String[0];
    }

    private static HmsScan.PeopleName a(String str, String str2) {
        HmsScan.PeopleName peopleName = new HmsScan.PeopleName("", "", "", "", "", "", "");
        if (str != null) {
            String[] strArrSplit = str.split(com.alipay.sdk.m.u.i.f9802b);
            if (strArrSplit.length > 0) {
                peopleName.familyName = strArrSplit[0];
            }
            if (strArrSplit.length > 1) {
                peopleName.givenName = strArrSplit[1];
            }
            if (strArrSplit.length > 2) {
                peopleName.middleName = strArrSplit[2];
            }
            if (strArrSplit.length > 3) {
                peopleName.namePrefix = strArrSplit[3];
            }
            if (strArrSplit.length > 4) {
                peopleName.nameSuffix = strArrSplit[4];
            }
        }
        if (str2 != null) {
            peopleName.fullName = str2;
        }
        return peopleName;
    }

    private static HmsScan.EmailContent[] b(String[] strArr, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            return new HmsScan.EmailContent[0];
        }
        HmsScan.EmailContent[] emailContentArr = new HmsScan.EmailContent[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            HmsScan.EmailContent emailContent = new HmsScan.EmailContent(strArr2[i2], "", "", HmsScan.EmailContent.OTHER_USE_TYPE);
            String str = strArr[i2];
            if (str != null) {
                if (str.equals("WORK")) {
                    emailContent.addressType = HmsScan.EmailContent.OFFICE_USE_TYPE;
                } else if (strArr[i2].equals("HOME")) {
                    emailContent.addressType = HmsScan.TelPhoneNumber.RESIDENTIAL_USE_TYPE;
                }
            }
            emailContentArr[i2] = emailContent;
        }
        return emailContentArr;
    }

    private static HmsScan.AddressInfo[] a(String[] strArr, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            return new HmsScan.AddressInfo[0];
        }
        HmsScan.AddressInfo[] addressInfoArr = new HmsScan.AddressInfo[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            HmsScan.AddressInfo addressInfo = new HmsScan.AddressInfo(new String[]{strArr2[i2]}, HmsScan.AddressInfo.OTHER_USE_TYPE);
            String str = strArr[i2];
            if (str != null) {
                if (str.equals("WORK")) {
                    addressInfo.addressType = HmsScan.AddressInfo.OFFICE_TYPE;
                } else if (strArr[i2].equals("HOME")) {
                    addressInfo.addressType = HmsScan.AddressInfo.RESIDENTIAL_USE_TYPE;
                }
            }
            addressInfoArr[i2] = addressInfo;
        }
        return addressInfoArr;
    }

    private static void a(String[] strArr, int i2, StringBuilder sb) {
        String str = strArr[i2];
        if (str == null || str.isEmpty()) {
            return;
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(strArr[i2]);
    }

    private static boolean a(int i2, int i3, String str) {
        return i2 >= i3 && str.charAt(i2 - i3) == '=';
    }
}
