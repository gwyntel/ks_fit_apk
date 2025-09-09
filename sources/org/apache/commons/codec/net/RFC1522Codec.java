package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes5.dex */
abstract class RFC1522Codec {
    RFC1522Codec() {
    }

    protected String a(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        if (!str.startsWith("=?") || !str.endsWith("?=")) {
            throw new DecoderException("RFC 1522 violation: malformed encoded content");
        }
        int length = str.length() - 2;
        int iIndexOf = str.indexOf(63, 2);
        if (iIndexOf == length) {
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        String strSubstring = str.substring(2, iIndexOf);
        if (strSubstring.equals("")) {
            throw new DecoderException("RFC 1522 violation: charset not specified");
        }
        int i2 = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(63, i2);
        if (iIndexOf2 == length) {
            throw new DecoderException("RFC 1522 violation: encoding token not found");
        }
        String strSubstring2 = str.substring(i2, iIndexOf2);
        if (e().equalsIgnoreCase(strSubstring2)) {
            int i3 = iIndexOf2 + 1;
            return new String(b(StringUtils.getBytesUsAscii(str.substring(i3, str.indexOf(63, i3)))), strSubstring);
        }
        throw new DecoderException("This codec cannot decode " + strSubstring2 + " encoded content");
    }

    protected abstract byte[] b(byte[] bArr);

    protected abstract byte[] c(byte[] bArr);

    protected String d(String str, String str2) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("=?");
        stringBuffer.append(str2);
        stringBuffer.append('?');
        stringBuffer.append(e());
        stringBuffer.append('?');
        stringBuffer.append(StringUtils.newStringUsAscii(c(str.getBytes(str2))));
        stringBuffer.append("?=");
        return stringBuffer.toString();
    }

    protected abstract String e();
}
