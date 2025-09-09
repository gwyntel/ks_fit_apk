package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Map;

/* loaded from: classes3.dex */
public final class ByQuadrantReader implements Reader {
    private final Reader delegate;

    public ByQuadrantReader(Reader reader) {
        this.delegate = reader;
    }

    private static void makeAbsolute(ResultPoint[] resultPointArr, int i2, int i3) {
        if (resultPointArr != null) {
            for (int i4 = 0; i4 < resultPointArr.length; i4++) {
                ResultPoint resultPoint = resultPointArr[i4];
                resultPointArr[i4] = new ResultPoint(resultPoint.getX() + i2, resultPoint.getY() + i3);
            }
        }
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    @Override // com.google.zxing.Reader
    public void reset() {
        this.delegate.reset();
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int width = binaryBitmap.getWidth() / 2;
        int height = binaryBitmap.getHeight() / 2;
        try {
            try {
                try {
                    try {
                        return this.delegate.decode(binaryBitmap.crop(0, 0, width, height), map);
                    } catch (NotFoundException unused) {
                        Result resultDecode = this.delegate.decode(binaryBitmap.crop(width, 0, width, height), map);
                        makeAbsolute(resultDecode.getResultPoints(), width, 0);
                        return resultDecode;
                    }
                } catch (NotFoundException unused2) {
                    Result resultDecode2 = this.delegate.decode(binaryBitmap.crop(0, height, width, height), map);
                    makeAbsolute(resultDecode2.getResultPoints(), 0, height);
                    return resultDecode2;
                }
            } catch (NotFoundException unused3) {
                Result resultDecode3 = this.delegate.decode(binaryBitmap.crop(width, height, width, height), map);
                makeAbsolute(resultDecode3.getResultPoints(), width, height);
                return resultDecode3;
            }
        } catch (NotFoundException unused4) {
            int i2 = width / 2;
            int i3 = height / 2;
            Result resultDecode4 = this.delegate.decode(binaryBitmap.crop(i2, i3, width, height), map);
            makeAbsolute(resultDecode4.getResultPoints(), i2, i3);
            return resultDecode4;
        }
    }
}
