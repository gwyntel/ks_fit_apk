package com.google.zxing.datamatrix.encoder;

/* loaded from: classes3.dex */
final class ASCIIEncoder implements Encoder {
    ASCIIEncoder() {
    }

    private static char encodeASCIIDigits(char c2, char c3) {
        if (HighLevelEncoder.b(c2) && HighLevelEncoder.b(c3)) {
            return (char) (((c2 - '0') * 10) + (c3 - '0') + 130);
        }
        throw new IllegalArgumentException("not digits: " + c2 + c3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(encoderContext.getMessage(), encoderContext.f15410a) >= 2) {
            encoderContext.writeCodeword(encodeASCIIDigits(encoderContext.getMessage().charAt(encoderContext.f15410a), encoderContext.getMessage().charAt(encoderContext.f15410a + 1)));
            encoderContext.f15410a += 2;
            return;
        }
        char currentChar = encoderContext.getCurrentChar();
        int iD = HighLevelEncoder.d(encoderContext.getMessage(), encoderContext.f15410a, getEncodingMode());
        if (iD == getEncodingMode()) {
            if (!HighLevelEncoder.c(currentChar)) {
                encoderContext.writeCodeword((char) (currentChar + 1));
                encoderContext.f15410a++;
                return;
            } else {
                encoderContext.writeCodeword((char) 235);
                encoderContext.writeCodeword((char) (currentChar - 127));
                encoderContext.f15410a++;
                return;
            }
        }
        if (iD == 1) {
            encoderContext.writeCodeword((char) 230);
            encoderContext.signalEncoderChange(1);
            return;
        }
        if (iD == 2) {
            encoderContext.writeCodeword((char) 239);
            encoderContext.signalEncoderChange(2);
            return;
        }
        if (iD == 3) {
            encoderContext.writeCodeword((char) 238);
            encoderContext.signalEncoderChange(3);
        } else if (iD == 4) {
            encoderContext.writeCodeword((char) 240);
            encoderContext.signalEncoderChange(4);
        } else {
            if (iD != 5) {
                throw new IllegalStateException("Illegal mode: ".concat(String.valueOf(iD)));
            }
            encoderContext.writeCodeword((char) 231);
            encoderContext.signalEncoderChange(5);
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 0;
    }
}
