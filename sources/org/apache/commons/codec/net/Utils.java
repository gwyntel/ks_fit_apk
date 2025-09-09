package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

/* loaded from: classes5.dex */
class Utils {
    static int a(byte b2) throws DecoderException {
        int iDigit = Character.digit((char) b2, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + ((int) b2));
    }
}
