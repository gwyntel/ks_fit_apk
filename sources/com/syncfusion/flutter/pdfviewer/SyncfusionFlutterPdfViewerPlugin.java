package com.syncfusion.flutter.pdfviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class SyncfusionFlutterPdfViewerPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    /* renamed from: a, reason: collision with root package name */
    Map f20004a = new HashMap();
    private MethodChannel channel;
    private Context context;
    private double[] pageHeight;
    private double[] pageWidth;
    private MethodChannel.Result resultPdf;

    boolean a(String str) throws IOException {
        try {
            PdfFileRenderer pdfFileRenderer = (PdfFileRenderer) this.f20004a.get(str);
            Objects.requireNonNull(pdfFileRenderer);
            pdfFileRenderer.renderer.close();
            PdfFileRenderer pdfFileRenderer2 = (PdfFileRenderer) this.f20004a.get(str);
            Objects.requireNonNull(pdfFileRenderer2);
            pdfFileRenderer2.fileDescriptor.close();
            this.f20004a.remove(str);
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            return true;
        } catch (IllegalStateException e3) {
            e3.printStackTrace();
            return true;
        }
    }

    void b(int i2, int i3, int i4, String str) {
        try {
            PdfFileRenderer pdfFileRenderer = (PdfFileRenderer) this.f20004a.get(str);
            Objects.requireNonNull(pdfFileRenderer);
            int i5 = i2 - 1;
            PdfRenderer.Page pageOpenPage = pdfFileRenderer.renderer.openPage(i5);
            Math.min(i3 / this.pageWidth[i5], i4 / this.pageHeight[i5]);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.eraseColor(-1);
            pageOpenPage.render(bitmapCreateBitmap, new Rect(0, 0, i3, i4), null, 1);
            pageOpenPage.close();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmapCreateBitmap.getByteCount());
            bitmapCreateBitmap.copyPixelsToBuffer(byteBufferAllocate);
            bitmapCreateBitmap.recycle();
            byte[] bArrArray = byteBufferAllocate.array();
            byteBufferAllocate.clear();
            this.resultPdf.success(bArrArray);
        } catch (Exception e2) {
            this.resultPdf.error(e2.getMessage(), e2.getLocalizedMessage(), e2.getMessage());
        }
    }

    double[] c(String str) {
        try {
            PdfFileRenderer pdfFileRenderer = (PdfFileRenderer) this.f20004a.get(str);
            Objects.requireNonNull(pdfFileRenderer);
            int pageCount = pdfFileRenderer.renderer.getPageCount();
            this.pageHeight = new double[pageCount];
            this.pageWidth = new double[pageCount];
            for (int i2 = 0; i2 < pageCount; i2++) {
                PdfFileRenderer pdfFileRenderer2 = (PdfFileRenderer) this.f20004a.get(str);
                Objects.requireNonNull(pdfFileRenderer2);
                PdfRenderer.Page pageOpenPage = pdfFileRenderer2.renderer.openPage(i2);
                this.pageHeight[i2] = pageOpenPage.getHeight();
                this.pageWidth[i2] = pageOpenPage.getWidth();
                pageOpenPage.close();
            }
            return this.pageHeight;
        } catch (Exception unused) {
            return null;
        }
    }

    double[] d(String str) {
        try {
            if (this.pageWidth == null) {
                PdfFileRenderer pdfFileRenderer = (PdfFileRenderer) this.f20004a.get(str);
                Objects.requireNonNull(pdfFileRenderer);
                int pageCount = pdfFileRenderer.renderer.getPageCount();
                this.pageWidth = new double[pageCount];
                for (int i2 = 0; i2 < pageCount; i2++) {
                    PdfFileRenderer pdfFileRenderer2 = (PdfFileRenderer) this.f20004a.get(str);
                    Objects.requireNonNull(pdfFileRenderer2);
                    PdfRenderer.Page pageOpenPage = pdfFileRenderer2.renderer.openPage(i2);
                    this.pageWidth[i2] = pageOpenPage.getWidth();
                    pageOpenPage.close();
                }
            }
            return this.pageWidth;
        } catch (Exception unused) {
            return null;
        }
    }

    void e(int i2, double d2, double d3, double d4, double d5, double d6, String str) {
        try {
            PdfFileRenderer pdfFileRenderer = (PdfFileRenderer) this.f20004a.get(str);
            Objects.requireNonNull(pdfFileRenderer);
            PdfRenderer.Page pageOpenPage = pdfFileRenderer.renderer.openPage(i2 - 1);
            int i3 = (int) d5;
            int i4 = (int) d6;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.eraseColor(-1);
            Matrix matrix = new Matrix();
            matrix.postTranslate((float) (-d3), (float) (-d4));
            float f2 = (float) d2;
            matrix.postScale(f2, f2);
            pageOpenPage.render(bitmapCreateBitmap, new Rect(0, 0, i3, i4), matrix, 1);
            pageOpenPage.close();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmapCreateBitmap.getByteCount());
            bitmapCreateBitmap.copyPixelsToBuffer(byteBufferAllocate);
            bitmapCreateBitmap.recycle();
            byte[] bArrArray = byteBufferAllocate.array();
            byteBufferAllocate.clear();
            this.resultPdf.success(bArrArray);
        } catch (Exception e2) {
            this.resultPdf.error(e2.getMessage(), e2.getLocalizedMessage(), e2.getMessage());
        }
    }

    String f(byte[] bArr, String str) throws IOException {
        try {
            File fileCreateTempFile = File.createTempFile(".syncfusion", ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(fileCreateTempFile, 268435456);
            PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptorOpen);
            this.f20004a.put(str, new PdfFileRenderer(parcelFileDescriptorOpen, pdfRenderer));
            int pageCount = pdfRenderer.getPageCount();
            fileCreateTempFile.delete();
            return String.valueOf(pageCount);
        } catch (Exception e2) {
            return e2.toString();
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "syncfusion_flutter_pdfviewer");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context = flutterPluginBinding.getApplicationContext();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.channel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    @RequiresApi(api = 21)
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) throws NumberFormatException {
        this.resultPdf = result;
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "getTileImage":
                Object objArgument = methodCall.argument("pageNumber");
                Objects.requireNonNull(objArgument);
                int i2 = Integer.parseInt(objArgument.toString());
                Object objArgument2 = methodCall.argument("scale");
                Objects.requireNonNull(objArgument2);
                double d2 = Double.parseDouble(objArgument2.toString());
                Object objArgument3 = methodCall.argument("x");
                Objects.requireNonNull(objArgument3);
                double d3 = Double.parseDouble(objArgument3.toString());
                Object objArgument4 = methodCall.argument("y");
                Objects.requireNonNull(objArgument4);
                double d4 = Double.parseDouble(objArgument4.toString());
                Object objArgument5 = methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
                Objects.requireNonNull(objArgument5);
                double d5 = Double.parseDouble(objArgument5.toString());
                Object objArgument6 = methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
                Objects.requireNonNull(objArgument6);
                e(i2, d2, d3, d4, d5, Double.parseDouble(objArgument6.toString()), (String) methodCall.argument("documentID"));
                break;
            case "initializePdfRenderer":
                result.success(f((byte[]) methodCall.argument("documentBytes"), (String) methodCall.argument("documentID")));
                break;
            case "getPage":
                Object objArgument7 = methodCall.argument(FirebaseAnalytics.Param.INDEX);
                Objects.requireNonNull(objArgument7);
                int i3 = Integer.parseInt(objArgument7.toString());
                Object objArgument8 = methodCall.argument(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
                Objects.requireNonNull(objArgument8);
                int i4 = Integer.parseInt(objArgument8.toString());
                Object objArgument9 = methodCall.argument(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
                Objects.requireNonNull(objArgument9);
                b(i3, i4, Integer.parseInt(objArgument9.toString()), (String) methodCall.argument("documentID"));
                break;
            case "getPagesWidth":
                result.success(d((String) methodCall.arguments));
                break;
            case "getPagesHeight":
                result.success(c((String) methodCall.arguments));
                break;
            case "closeDocument":
                result.success(Boolean.valueOf(a((String) methodCall.arguments)));
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
