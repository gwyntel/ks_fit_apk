package com.google.zxing.datamatrix.encoder;

/* loaded from: classes3.dex */
final class X12Encoder extends C40Encoder {
    X12Encoder() {
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    int a(char c2, StringBuilder sb) {
        if (c2 == '\r') {
            sb.append((char) 0);
        } else if (c2 == ' ') {
            sb.append((char) 3);
        } else if (c2 == '*') {
            sb.append((char) 1);
        } else if (c2 == '>') {
            sb.append((char) 2);
        } else if (c2 >= '0' && c2 <= '9') {
            sb.append((char) (c2 - ','));
        } else if (c2 < 'A' || c2 > 'Z') {
            HighLevelEncoder.a(c2);
        } else {
            sb.append((char) (c2 - '3'));
        }
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    void b(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
        encoderContext.f15410a -= sb.length();
        if (encoderContext.getRemainingCharacters() > 1 || dataCapacity > 1 || encoderContext.getRemainingCharacters() != dataCapacity) {
            encoderContext.writeCodeword((char) 254);
        }
        if (encoderContext.getNewEncoding() < 0) {
            encoderContext.signalEncoderChange(0);
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.f15410a++;
            a(currentChar, sb);
            if (sb.length() % 3 == 0) {
                C40Encoder.c(encoderContext, sb);
                if (HighLevelEncoder.d(encoderContext.getMessage(), encoderContext.f15410a, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            }
        }
        b(encoderContext, sb);
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 3;
    }
}
