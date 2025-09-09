package com.syncfusion.flutter.pdfviewer;

import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;

/* loaded from: classes4.dex */
class PdfFileRenderer {
    public ParcelFileDescriptor fileDescriptor;
    public PdfRenderer renderer;

    PdfFileRenderer(ParcelFileDescriptor parcelFileDescriptor, PdfRenderer pdfRenderer) {
        this.renderer = pdfRenderer;
        this.fileDescriptor = parcelFileDescriptor;
    }
}
