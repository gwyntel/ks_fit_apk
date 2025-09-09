package com.google.zxing.client.result;

import com.alibaba.fastjson.parser.JSONLexer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.HashMap;
import kotlin.text.Typography;
import org.android.agoo.common.AgooConstants;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes3.dex */
public final class ExpandedProductResultParser extends ResultParser {
    private static String findAIvalue(int i2, String str) {
        if (str.charAt(i2) != '(') {
            return null;
        }
        String strSubstring = str.substring(i2 + 1);
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < strSubstring.length(); i3++) {
            char cCharAt = strSubstring.charAt(i3);
            if (cCharAt == ')') {
                return sb.toString();
            }
            if (cCharAt < '0' || cCharAt > '9') {
                return null;
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static String findValue(int i2, String str) {
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(i2);
        for (int i3 = 0; i3 < strSubstring.length(); i3++) {
            char cCharAt = strSubstring.charAt(i3);
            if (cCharAt != '(') {
                sb.append(cCharAt);
            } else {
                if (findAIvalue(i3, strSubstring) != null) {
                    break;
                }
                sb.append('(');
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.zxing.client.result.ResultParser
    public ExpandedProductParsedResult parse(Result result) {
        if (result.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        String strA = ResultParser.a(result);
        HashMap map = new HashMap();
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String strSubstring = null;
        String strSubstring2 = null;
        String strSubstring3 = null;
        String strSubstring4 = null;
        int length = 0;
        while (length < strA.length()) {
            String strFindAIvalue = findAIvalue(length, strA);
            if (strFindAIvalue == null) {
                return null;
            }
            int length2 = length + strFindAIvalue.length() + 2;
            String strFindValue = findValue(length2, strA);
            length = length2 + strFindValue.length();
            char c2 = 65535;
            switch (strFindAIvalue.hashCode()) {
                case 1536:
                    if (strFindAIvalue.equals("00")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1537:
                    if (strFindAIvalue.equals(HiAnalyticsConstant.KeyAndValue.NUMBER_01)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 1567:
                    if (strFindAIvalue.equals(AgooConstants.ACK_REMOVE_PACKAGE)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 1568:
                    if (strFindAIvalue.equals(AgooConstants.ACK_BODY_NULL)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 1570:
                    if (strFindAIvalue.equals(AgooConstants.ACK_FLAG_NULL)) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 1572:
                    if (strFindAIvalue.equals(AgooConstants.ACK_PACK_ERROR)) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 1574:
                    if (strFindAIvalue.equals("17")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 1567966:
                    if (strFindAIvalue.equals("3100")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 1567967:
                    if (strFindAIvalue.equals("3101")) {
                        c2 = '\b';
                        break;
                    }
                    break;
                case 1567968:
                    if (strFindAIvalue.equals("3102")) {
                        c2 = '\t';
                        break;
                    }
                    break;
                case 1567969:
                    if (strFindAIvalue.equals("3103")) {
                        c2 = '\n';
                        break;
                    }
                    break;
                case 1567970:
                    if (strFindAIvalue.equals("3104")) {
                        c2 = 11;
                        break;
                    }
                    break;
                case 1567971:
                    if (strFindAIvalue.equals("3105")) {
                        c2 = '\f';
                        break;
                    }
                    break;
                case 1567972:
                    if (strFindAIvalue.equals("3106")) {
                        c2 = CharUtils.CR;
                        break;
                    }
                    break;
                case 1567973:
                    if (strFindAIvalue.equals("3107")) {
                        c2 = 14;
                        break;
                    }
                    break;
                case 1567974:
                    if (strFindAIvalue.equals("3108")) {
                        c2 = 15;
                        break;
                    }
                    break;
                case 1567975:
                    if (strFindAIvalue.equals("3109")) {
                        c2 = 16;
                        break;
                    }
                    break;
                case 1568927:
                    if (strFindAIvalue.equals("3200")) {
                        c2 = 17;
                        break;
                    }
                    break;
                case 1568928:
                    if (strFindAIvalue.equals("3201")) {
                        c2 = 18;
                        break;
                    }
                    break;
                case 1568929:
                    if (strFindAIvalue.equals("3202")) {
                        c2 = 19;
                        break;
                    }
                    break;
                case 1568930:
                    if (strFindAIvalue.equals("3203")) {
                        c2 = 20;
                        break;
                    }
                    break;
                case 1568931:
                    if (strFindAIvalue.equals("3204")) {
                        c2 = 21;
                        break;
                    }
                    break;
                case 1568932:
                    if (strFindAIvalue.equals("3205")) {
                        c2 = 22;
                        break;
                    }
                    break;
                case 1568933:
                    if (strFindAIvalue.equals("3206")) {
                        c2 = 23;
                        break;
                    }
                    break;
                case 1568934:
                    if (strFindAIvalue.equals("3207")) {
                        c2 = 24;
                        break;
                    }
                    break;
                case 1568935:
                    if (strFindAIvalue.equals("3208")) {
                        c2 = 25;
                        break;
                    }
                    break;
                case 1568936:
                    if (strFindAIvalue.equals("3209")) {
                        c2 = JSONLexer.EOI;
                        break;
                    }
                    break;
                case 1575716:
                    if (strFindAIvalue.equals("3920")) {
                        c2 = 27;
                        break;
                    }
                    break;
                case 1575717:
                    if (strFindAIvalue.equals("3921")) {
                        c2 = 28;
                        break;
                    }
                    break;
                case 1575718:
                    if (strFindAIvalue.equals("3922")) {
                        c2 = 29;
                        break;
                    }
                    break;
                case 1575719:
                    if (strFindAIvalue.equals("3923")) {
                        c2 = 30;
                        break;
                    }
                    break;
                case 1575747:
                    if (strFindAIvalue.equals("3930")) {
                        c2 = 31;
                        break;
                    }
                    break;
                case 1575748:
                    if (strFindAIvalue.equals("3931")) {
                        c2 = ' ';
                        break;
                    }
                    break;
                case 1575749:
                    if (strFindAIvalue.equals("3932")) {
                        c2 = '!';
                        break;
                    }
                    break;
                case 1575750:
                    if (strFindAIvalue.equals("3933")) {
                        c2 = Typography.quote;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    str2 = strFindValue;
                    continue;
                case 1:
                    str = strFindValue;
                    continue;
                case 2:
                    str3 = strFindValue;
                    continue;
                case 3:
                    str4 = strFindValue;
                    continue;
                case 4:
                    str5 = strFindValue;
                    continue;
                case 5:
                    str6 = strFindValue;
                    continue;
                case 6:
                    str7 = strFindValue;
                    continue;
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                    strSubstring = strFindAIvalue.substring(3);
                    str9 = ExpandedProductParsedResult.KILOGRAM;
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                    strSubstring = strFindAIvalue.substring(3);
                    str9 = ExpandedProductParsedResult.POUND;
                    break;
                case 27:
                case 28:
                case 29:
                case 30:
                    strSubstring3 = strFindAIvalue.substring(3);
                    strSubstring2 = strFindValue;
                    continue;
                case 31:
                case ' ':
                case '!':
                case '\"':
                    if (strFindValue.length() < 4) {
                        return null;
                    }
                    strSubstring2 = strFindValue.substring(3);
                    strSubstring4 = strFindValue.substring(0, 3);
                    strSubstring3 = strFindAIvalue.substring(3);
                    continue;
                default:
                    map.put(strFindAIvalue, strFindValue);
                    continue;
            }
            str8 = strFindValue;
        }
        return new ExpandedProductParsedResult(strA, str, str2, str3, str4, str5, str6, str7, str8, str9, strSubstring, strSubstring2, strSubstring3, strSubstring4, map);
    }
}
