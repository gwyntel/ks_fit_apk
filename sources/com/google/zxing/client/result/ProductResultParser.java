package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.oned.UPCEReader;

/* loaded from: classes3.dex */
public final class ProductResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public ProductParsedResult parse(Result result) {
        BarcodeFormat barcodeFormat = result.getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.UPC_A && barcodeFormat != BarcodeFormat.UPC_E && barcodeFormat != BarcodeFormat.EAN_8 && barcodeFormat != BarcodeFormat.EAN_13) {
            return null;
        }
        String strA = ResultParser.a(result);
        if (ResultParser.b(strA, strA.length())) {
            return new ProductParsedResult(strA, (barcodeFormat == BarcodeFormat.UPC_E && strA.length() == 8) ? UPCEReader.convertUPCEtoUPCA(strA) : strA);
        }
        return null;
    }
}
