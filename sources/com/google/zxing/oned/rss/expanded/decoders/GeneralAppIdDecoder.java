package com.google.zxing.oned.rss.expanded.decoders;

import com.alipay.sdk.m.n.a;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.yc.utesdk.ble.close.KeyType;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
final class GeneralAppIdDecoder {
    private final BitArray information;
    private final CurrentParsingState current = new CurrentParsingState();
    private final StringBuilder buffer = new StringBuilder();

    GeneralAppIdDecoder(BitArray bitArray) {
        this.information = bitArray;
    }

    static int d(BitArray bitArray, int i2, int i3) {
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            if (bitArray.get(i2 + i5)) {
                i4 |= 1 << ((i3 - i5) - 1);
            }
        }
        return i4;
    }

    private DecodedChar decodeAlphanumeric(int i2) {
        char c2;
        int iC = c(i2, 5);
        if (iC == 15) {
            return new DecodedChar(i2 + 5, '$');
        }
        if (iC >= 5 && iC < 15) {
            return new DecodedChar(i2 + 5, (char) (iC + 43));
        }
        int iC2 = c(i2, 6);
        if (iC2 >= 32 && iC2 < 58) {
            return new DecodedChar(i2 + 6, (char) (iC2 + 33));
        }
        switch (iC2) {
            case 58:
                c2 = '*';
                break;
            case 59:
                c2 = ',';
                break;
            case 60:
                c2 = '-';
                break;
            case 61:
                c2 = '.';
                break;
            case 62:
                c2 = IOUtils.DIR_SEPARATOR_UNIX;
                break;
            default:
                throw new IllegalStateException("Decoding invalid alphanumeric value: ".concat(String.valueOf(iC2)));
        }
        return new DecodedChar(i2 + 6, c2);
    }

    private DecodedChar decodeIsoIec646(int i2) throws FormatException {
        int iC = c(i2, 5);
        if (iC == 15) {
            return new DecodedChar(i2 + 5, '$');
        }
        char c2 = '+';
        if (iC >= 5 && iC < 15) {
            return new DecodedChar(i2 + 5, (char) (iC + 43));
        }
        int iC2 = c(i2, 7);
        if (iC2 >= 64 && iC2 < 90) {
            return new DecodedChar(i2 + 7, (char) (iC2 + 1));
        }
        if (iC2 >= 90 && iC2 < 116) {
            return new DecodedChar(i2 + 7, (char) (iC2 + 7));
        }
        switch (c(i2, 8)) {
            case 232:
                c2 = '!';
                break;
            case 233:
                c2 = Typography.quote;
                break;
            case 234:
                c2 = '%';
                break;
            case 235:
                c2 = Typography.amp;
                break;
            case 236:
                c2 = '\'';
                break;
            case 237:
                c2 = '(';
                break;
            case 238:
                c2 = ')';
                break;
            case 239:
                c2 = '*';
                break;
            case 240:
                break;
            case 241:
                c2 = ',';
                break;
            case 242:
                c2 = '-';
                break;
            case 243:
                c2 = '.';
                break;
            case 244:
                c2 = IOUtils.DIR_SEPARATOR_UNIX;
                break;
            case 245:
                c2 = ':';
                break;
            case 246:
                c2 = ';';
                break;
            case 247:
                c2 = Typography.less;
                break;
            case KeyType.SET_COMMON_RATE_TEST_INTERVAL /* 248 */:
                c2 = a.f9565h;
                break;
            case 249:
                c2 = Typography.greater;
                break;
            case 250:
                c2 = '?';
                break;
            case 251:
                c2 = '_';
                break;
            case 252:
                c2 = ' ';
                break;
            default:
                throw FormatException.getFormatInstance();
        }
        return new DecodedChar(i2 + 8, c2);
    }

    private DecodedNumeric decodeNumeric(int i2) throws FormatException {
        int i3 = i2 + 7;
        if (i3 > this.information.getSize()) {
            int iC = c(i2, 4);
            return iC == 0 ? new DecodedNumeric(this.information.getSize(), 10, 10) : new DecodedNumeric(this.information.getSize(), iC - 1, 10);
        }
        int iC2 = c(i2, 7) - 8;
        return new DecodedNumeric(i3, iC2 / 11, iC2 % 11);
    }

    private boolean isAlphaOr646ToNumericLatch(int i2) {
        int i3 = i2 + 3;
        if (i3 > this.information.getSize()) {
            return false;
        }
        while (i2 < i3) {
            if (this.information.get(i2)) {
                return false;
            }
            i2++;
        }
        return true;
    }

    private boolean isAlphaTo646ToAlphaLatch(int i2) {
        int i3;
        if (i2 + 1 > this.information.getSize()) {
            return false;
        }
        for (int i4 = 0; i4 < 5 && (i3 = i4 + i2) < this.information.getSize(); i4++) {
            if (i4 == 2) {
                if (!this.information.get(i2 + 2)) {
                    return false;
                }
            } else if (this.information.get(i3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumericToAlphaNumericLatch(int i2) {
        int i3;
        if (i2 + 1 > this.information.getSize()) {
            return false;
        }
        for (int i4 = 0; i4 < 4 && (i3 = i4 + i2) < this.information.getSize(); i4++) {
            if (this.information.get(i3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isStillAlpha(int i2) {
        int iC;
        if (i2 + 5 > this.information.getSize()) {
            return false;
        }
        int iC2 = c(i2, 5);
        if (iC2 < 5 || iC2 >= 16) {
            return i2 + 6 <= this.information.getSize() && (iC = c(i2, 6)) >= 16 && iC < 63;
        }
        return true;
    }

    private boolean isStillIsoIec646(int i2) {
        int iC;
        if (i2 + 5 > this.information.getSize()) {
            return false;
        }
        int iC2 = c(i2, 5);
        if (iC2 >= 5 && iC2 < 16) {
            return true;
        }
        if (i2 + 7 > this.information.getSize()) {
            return false;
        }
        int iC3 = c(i2, 7);
        if (iC3 < 64 || iC3 >= 116) {
            return i2 + 8 <= this.information.getSize() && (iC = c(i2, 8)) >= 232 && iC < 253;
        }
        return true;
    }

    private boolean isStillNumeric(int i2) {
        if (i2 + 7 > this.information.getSize()) {
            return i2 + 4 <= this.information.getSize();
        }
        int i3 = i2;
        while (true) {
            int i4 = i2 + 3;
            if (i3 >= i4) {
                return this.information.get(i4);
            }
            if (this.information.get(i3)) {
                return true;
            }
            i3++;
        }
    }

    private BlockParsedResult parseAlphaBlock() {
        while (isStillAlpha(this.current.a())) {
            DecodedChar decodedCharDecodeAlphanumeric = decodeAlphanumeric(this.current.a());
            this.current.h(decodedCharDecodeAlphanumeric.a());
            if (decodedCharDecodeAlphanumeric.c()) {
                return new BlockParsedResult(new DecodedInformation(this.current.a(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedCharDecodeAlphanumeric.b());
        }
        if (isAlphaOr646ToNumericLatch(this.current.a())) {
            this.current.b(3);
            this.current.g();
        } else if (isAlphaTo646ToAlphaLatch(this.current.a())) {
            if (this.current.a() + 5 < this.information.getSize()) {
                this.current.b(5);
            } else {
                this.current.h(this.information.getSize());
            }
            this.current.f();
        }
        return new BlockParsedResult(false);
    }

    private DecodedInformation parseBlocks() throws FormatException {
        BlockParsedResult numericBlock;
        boolean zB;
        do {
            int iA = this.current.a();
            if (this.current.c()) {
                numericBlock = parseAlphaBlock();
                zB = numericBlock.b();
            } else if (this.current.d()) {
                numericBlock = parseIsoIec646Block();
                zB = numericBlock.b();
            } else {
                numericBlock = parseNumericBlock();
                zB = numericBlock.b();
            }
            if (iA == this.current.a() && !zB) {
                break;
            }
        } while (!zB);
        return numericBlock.a();
    }

    private BlockParsedResult parseIsoIec646Block() throws FormatException {
        while (isStillIsoIec646(this.current.a())) {
            DecodedChar decodedCharDecodeIsoIec646 = decodeIsoIec646(this.current.a());
            this.current.h(decodedCharDecodeIsoIec646.a());
            if (decodedCharDecodeIsoIec646.c()) {
                return new BlockParsedResult(new DecodedInformation(this.current.a(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedCharDecodeIsoIec646.b());
        }
        if (isAlphaOr646ToNumericLatch(this.current.a())) {
            this.current.b(3);
            this.current.g();
        } else if (isAlphaTo646ToAlphaLatch(this.current.a())) {
            if (this.current.a() + 5 < this.information.getSize()) {
                this.current.b(5);
            } else {
                this.current.h(this.information.getSize());
            }
            this.current.e();
        }
        return new BlockParsedResult(false);
    }

    private BlockParsedResult parseNumericBlock() throws FormatException {
        while (isStillNumeric(this.current.a())) {
            DecodedNumeric decodedNumericDecodeNumeric = decodeNumeric(this.current.a());
            this.current.h(decodedNumericDecodeNumeric.a());
            if (decodedNumericDecodeNumeric.d()) {
                return new BlockParsedResult(decodedNumericDecodeNumeric.e() ? new DecodedInformation(this.current.a(), this.buffer.toString()) : new DecodedInformation(this.current.a(), this.buffer.toString(), decodedNumericDecodeNumeric.c()), true);
            }
            this.buffer.append(decodedNumericDecodeNumeric.b());
            if (decodedNumericDecodeNumeric.e()) {
                return new BlockParsedResult(new DecodedInformation(this.current.a(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedNumericDecodeNumeric.c());
        }
        if (isNumericToAlphaNumericLatch(this.current.a())) {
            this.current.e();
            this.current.b(4);
        }
        return new BlockParsedResult(false);
    }

    String a(StringBuilder sb, int i2) throws NotFoundException, FormatException {
        String str = null;
        while (true) {
            DecodedInformation decodedInformationB = b(i2, str);
            String strA = FieldParser.a(decodedInformationB.b());
            if (strA != null) {
                sb.append(strA);
            }
            String strValueOf = decodedInformationB.d() ? String.valueOf(decodedInformationB.c()) : null;
            if (i2 == decodedInformationB.a()) {
                return sb.toString();
            }
            i2 = decodedInformationB.a();
            str = strValueOf;
        }
    }

    DecodedInformation b(int i2, String str) throws FormatException {
        this.buffer.setLength(0);
        if (str != null) {
            this.buffer.append(str);
        }
        this.current.h(i2);
        DecodedInformation blocks = parseBlocks();
        return (blocks == null || !blocks.d()) ? new DecodedInformation(this.current.a(), this.buffer.toString()) : new DecodedInformation(this.current.a(), this.buffer.toString(), blocks.c());
    }

    int c(int i2, int i3) {
        return d(this.information, i2, i3);
    }
}
