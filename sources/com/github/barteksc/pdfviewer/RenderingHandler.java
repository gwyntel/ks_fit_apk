package com.github.barteksc.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.github.barteksc.pdfviewer.exception.PageRenderingException;
import com.github.barteksc.pdfviewer.model.PagePart;

/* loaded from: classes3.dex */
class RenderingHandler extends Handler {
    private static final String TAG = "com.github.barteksc.pdfviewer.RenderingHandler";
    private PDFView pdfView;
    private RectF renderBounds;
    private Matrix renderMatrix;
    private Rect roundedRenderBounds;
    private boolean running;

    private class RenderingTask {

        /* renamed from: a, reason: collision with root package name */
        float f12492a;

        /* renamed from: b, reason: collision with root package name */
        float f12493b;

        /* renamed from: c, reason: collision with root package name */
        RectF f12494c;

        /* renamed from: d, reason: collision with root package name */
        int f12495d;

        /* renamed from: e, reason: collision with root package name */
        boolean f12496e;

        /* renamed from: f, reason: collision with root package name */
        int f12497f;

        /* renamed from: g, reason: collision with root package name */
        boolean f12498g;

        /* renamed from: h, reason: collision with root package name */
        boolean f12499h;

        RenderingTask(float f2, float f3, RectF rectF, int i2, boolean z2, int i3, boolean z3, boolean z4) {
            this.f12495d = i2;
            this.f12492a = f2;
            this.f12493b = f3;
            this.f12494c = rectF;
            this.f12496e = z2;
            this.f12497f = i3;
            this.f12498g = z3;
            this.f12499h = z4;
        }
    }

    RenderingHandler(Looper looper, PDFView pDFView) {
        super(looper);
        this.renderBounds = new RectF();
        this.roundedRenderBounds = new Rect();
        this.renderMatrix = new Matrix();
        this.running = false;
        this.pdfView = pDFView;
    }

    private void calculateBounds(int i2, int i3, RectF rectF) {
        this.renderMatrix.reset();
        float f2 = i2;
        float f3 = i3;
        this.renderMatrix.postTranslate((-rectF.left) * f2, (-rectF.top) * f3);
        this.renderMatrix.postScale(1.0f / rectF.width(), 1.0f / rectF.height());
        this.renderBounds.set(0.0f, 0.0f, f2, f3);
        this.renderMatrix.mapRect(this.renderBounds);
        this.renderBounds.round(this.roundedRenderBounds);
    }

    private PagePart proceed(RenderingTask renderingTask) throws Throwable {
        PdfFile pdfFile = this.pdfView.f12472b;
        pdfFile.openPage(renderingTask.f12495d);
        int iRound = Math.round(renderingTask.f12492a);
        int iRound2 = Math.round(renderingTask.f12493b);
        if (iRound != 0 && iRound2 != 0 && !pdfFile.pageHasError(renderingTask.f12495d)) {
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iRound, iRound2, renderingTask.f12498g ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                calculateBounds(iRound, iRound2, renderingTask.f12494c);
                pdfFile.renderPageBitmap(bitmapCreateBitmap, renderingTask.f12495d, this.roundedRenderBounds, renderingTask.f12499h);
                return new PagePart(renderingTask.f12495d, bitmapCreateBitmap, renderingTask.f12494c, renderingTask.f12496e, renderingTask.f12497f);
            } catch (IllegalArgumentException e2) {
                Log.e(TAG, "Cannot create bitmap", e2);
            }
        }
        return null;
    }

    void b(int i2, float f2, float f3, RectF rectF, boolean z2, int i3, boolean z3, boolean z4) {
        sendMessage(obtainMessage(1, new RenderingTask(f2, f3, rectF, i2, z2, i3, z3, z4)));
    }

    void c() {
        this.running = true;
    }

    void d() {
        this.running = false;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws Throwable {
        try {
            final PagePart pagePartProceed = proceed((RenderingTask) message.obj);
            if (pagePartProceed != null) {
                if (this.running) {
                    this.pdfView.post(new Runnable() { // from class: com.github.barteksc.pdfviewer.RenderingHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RenderingHandler.this.pdfView.onBitmapRendered(pagePartProceed);
                        }
                    });
                } else {
                    pagePartProceed.getRenderedBitmap().recycle();
                }
            }
        } catch (PageRenderingException e2) {
            this.pdfView.post(new Runnable() { // from class: com.github.barteksc.pdfviewer.RenderingHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    RenderingHandler.this.pdfView.t(e2);
                }
            });
        }
    }
}
