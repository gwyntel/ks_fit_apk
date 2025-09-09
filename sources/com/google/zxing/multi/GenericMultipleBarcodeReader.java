package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i2, int i3, int i4) {
        float f2;
        float f3;
        float f4;
        int i5;
        if (i4 > 4) {
            return;
        }
        try {
            Result resultDecode = this.delegate.decode(binaryBitmap, map);
            Iterator<Result> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getText().equals(resultDecode.getText())) {
                        break;
                    }
                } else {
                    list.add(translateResultPoints(resultDecode, i2, i3));
                    break;
                }
            }
            ResultPoint[] resultPoints = resultDecode.getResultPoints();
            if (resultPoints == null || resultPoints.length == 0) {
                return;
            }
            int width = binaryBitmap.getWidth();
            int height = binaryBitmap.getHeight();
            float f5 = width;
            float f6 = 0.0f;
            float f7 = height;
            float f8 = 0.0f;
            for (ResultPoint resultPoint : resultPoints) {
                if (resultPoint != null) {
                    float x2 = resultPoint.getX();
                    float y2 = resultPoint.getY();
                    if (x2 < f5) {
                        f5 = x2;
                    }
                    if (y2 < f7) {
                        f7 = y2;
                    }
                    if (x2 > f6) {
                        f6 = x2;
                    }
                    if (y2 > f8) {
                        f8 = y2;
                    }
                }
            }
            if (f5 > 100.0f) {
                f2 = f8;
                f3 = f6;
                f4 = f7;
                i5 = 0;
                doDecodeMultiple(binaryBitmap.crop(0, 0, (int) f5, height), map, list, i2, i3, i4 + 1);
            } else {
                f2 = f8;
                f3 = f6;
                f4 = f7;
                i5 = 0;
            }
            if (f4 > 100.0f) {
                doDecodeMultiple(binaryBitmap.crop(i5, i5, width, (int) f4), map, list, i2, i3, i4 + 1);
            }
            if (f3 < width - 100) {
                int i6 = (int) f3;
                doDecodeMultiple(binaryBitmap.crop(i6, i5, width - i6, height), map, list, i2 + i6, i3, i4 + 1);
            }
            if (f2 < height - 100) {
                int i7 = (int) f2;
                doDecodeMultiple(binaryBitmap.crop(i5, i7, width, height - i7), map, list, i2, i3 + i7, i4 + 1);
            }
        } catch (ReaderException unused) {
        }
    }

    private static Result translateResultPoints(Result result, int i2, int i3) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i4 = 0; i4 < resultPoints.length; i4++) {
            ResultPoint resultPoint = resultPoints[i4];
            if (resultPoint != null) {
                resultPointArr[i4] = new ResultPoint(resultPoint.getX() + i2, resultPoint.getY() + i3);
            }
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), resultPointArr, result.getBarcodeFormat(), result.getTimestamp());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }
}
