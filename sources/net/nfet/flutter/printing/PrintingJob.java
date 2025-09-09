package net.nfet.flutter.printing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PdfConvert;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RequiresApi(api = 19)
/* loaded from: classes5.dex */
public class PrintingJob extends PrintDocumentAdapter {
    private static PrintManager printManager;

    /* renamed from: a, reason: collision with root package name */
    int f26041a;
    private PrintDocumentAdapter.LayoutResultCallback callback;
    private final Context context;
    private byte[] documentData;
    private String jobName;
    private PrintJob printJob;
    private final PrintingHandler printing;

    PrintingJob(Context context, PrintingHandler printingHandler, int i2) {
        this.context = context;
        this.printing = printingHandler;
        this.f26041a = i2;
        printManager = (PrintManager) context.getSystemService("print");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinish$0(boolean[] zArr) {
        PrintJob printJob = this.printJob;
        int state = printJob == null ? 6 : printJob.getInfo().getState();
        if (state == 5) {
            this.printing.a(this, true, null);
            zArr[0] = false;
        } else if (state == 7) {
            this.printing.a(this, false, null);
            zArr[0] = false;
        } else if (state == 6) {
            this.printing.a(this, false, "Unable to print");
            zArr[0] = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinish$1(Exception exc) {
        PrintingHandler printingHandler = this.printing;
        PrintJob printJob = this.printJob;
        printingHandler.a(this, printJob != null && printJob.isCompleted(), exc.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinish$2() throws Exception {
        try {
            final boolean[] zArr = {true};
            int i2 = 3000;
            while (zArr[0]) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: net.nfet.flutter.printing.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f26061a.lambda$onFinish$0(zArr);
                    }
                });
                i2--;
                if (i2 <= 0) {
                    throw new Exception("Timeout waiting for the job to finish");
                }
                if (zArr[0]) {
                    Thread.sleep(200L);
                }
            }
        } catch (Exception e2) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: net.nfet.flutter.printing.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26063a.lambda$onFinish$1(e2);
                }
            });
        }
        this.printJob = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rasterPdf$3(ByteBuffer byteBuffer, int i2, int i3) {
        this.printing.f(this, byteBuffer.array(), i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rasterPdf$4(String str) {
        this.printing.e(this, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rasterPdf$5(byte[] bArr, ArrayList arrayList, Double d2) throws IOException {
        final String message;
        try {
            message = null;
            File fileCreateTempFile = File.createTempFile("printing", null, this.context.getCacheDir());
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            FileInputStream fileInputStream = new FileInputStream(fileCreateTempFile);
            PdfRenderer pdfRenderer = new PdfRenderer(ParcelFileDescriptor.dup(fileInputStream.getFD()));
            if (!fileCreateTempFile.delete()) {
                Log.e("PDF", "Unable to delete temporary file");
            }
            int size = arrayList != null ? arrayList.size() : pdfRenderer.getPageCount();
            for (int i2 = 0; i2 < size; i2++) {
                PdfRenderer.Page pageOpenPage = pdfRenderer.openPage(arrayList == null ? i2 : ((Integer) arrayList.get(i2)).intValue());
                final int iIntValue = Double.valueOf(pageOpenPage.getWidth() * d2.doubleValue()).intValue();
                final int iIntValue2 = Double.valueOf(pageOpenPage.getHeight() * d2.doubleValue()).intValue();
                Matrix matrix = new Matrix();
                matrix.setScale(d2.floatValue(), d2.floatValue());
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iIntValue, iIntValue2, Bitmap.Config.ARGB_8888);
                pageOpenPage.render(bitmapCreateBitmap, null, matrix, 1);
                pageOpenPage.close();
                final ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iIntValue * 4 * iIntValue2);
                bitmapCreateBitmap.copyPixelsToBuffer(byteBufferAllocate);
                bitmapCreateBitmap.recycle();
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: net.nfet.flutter.printing.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f26047a.lambda$rasterPdf$3(byteBufferAllocate, iIntValue, iIntValue2);
                    }
                });
            }
            pdfRenderer.close();
            fileInputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
            message = e2.getMessage();
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: net.nfet.flutter.printing.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f26051a.lambda$rasterPdf$4(message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rasterPdf$6(String str) {
        this.printing.e(this, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rasterPdf$7(Thread thread, Throwable th) {
        final String message = th.getMessage();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: net.nfet.flutter.printing.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f26054a.lambda$rasterPdf$6(message);
            }
        });
    }

    static HashMap o() {
        HashMap map = new HashMap();
        map.put("directPrint", Boolean.FALSE);
        map.put("dynamicLayout", true);
        map.put("canPrint", true);
        map.put("canShare", Boolean.TRUE);
        map.put("canRaster", true);
        return map;
    }

    static void r(Context context, byte[] bArr, String str, String str2, String str3, ArrayList arrayList) throws IOException {
        try {
            File file = new File(context.getCacheDir(), "share");
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException("Unable to create cache directory");
            }
            File file2 = new File(file, str);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".flutter.printing", file2);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("application/pdf");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.addFlags(1);
            intent.putExtra("android.intent.extra.SUBJECT", str2);
            intent.putExtra("android.intent.extra.TEXT", str3);
            intent.putExtra("android.intent.extra.EMAIL", arrayList != null ? (String[]) arrayList.toArray(new String[0]) : null);
            Intent intentCreateChooser = Intent.createChooser(intent, null);
            Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intentCreateChooser, 65536).iterator();
            while (it.hasNext()) {
                context.grantUriPermission(it.next().activityInfo.packageName, uriForFile, 3);
            }
            context.startActivity(intentCreateChooser);
            file2.deleteOnExit();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    void k(String str) {
        PrintDocumentAdapter.LayoutResultCallback layoutResultCallback = this.callback;
        if (layoutResultCallback != null) {
            layoutResultCallback.onLayoutCancelled();
        }
        PrintJob printJob = this.printJob;
        if (printJob != null) {
            printJob.cancel();
        }
        this.printing.a(this, false, str);
    }

    void l(String str, final PrintAttributes.MediaSize mediaSize, final PrintAttributes.Margins margins, String str2) {
        Configuration configuration = this.context.getResources().getConfiguration();
        configuration.fontScale = 1.0f;
        final WebView webView = new WebView(this.context.createConfigurationContext(configuration));
        webView.loadDataWithBaseURL(str2, str, "text/HTML", "UTF-8", null);
        webView.setWebViewClient(new WebViewClient() { // from class: net.nfet.flutter.printing.PrintingJob.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str3) {
                super.onPageFinished(webView2, str3);
                PrintAttributes printAttributesBuild = new PrintAttributes.Builder().setMediaSize(mediaSize).setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600)).setMinMargins(margins).build();
                PdfConvert.print(PrintingJob.this.context, webView.createPrintDocumentAdapter("printing"), printAttributesBuild, new PdfConvert.Result() { // from class: net.nfet.flutter.printing.PrintingJob.1.1
                    @Override // android.print.PdfConvert.Result
                    public void onError(String str4) {
                        PrintingJob.this.printing.b(PrintingJob.this, str4);
                    }

                    @Override // android.print.PdfConvert.Result
                    public void onSuccess(File file) {
                        try {
                            PrintingJob.this.printing.c(PrintingJob.this, PdfConvert.readFile(file));
                        } catch (IOException e2) {
                            onError(e2.getMessage());
                        }
                    }
                });
            }
        });
    }

    List m() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PrintAttributes.MediaSize.ISO_A0);
        arrayList.add(PrintAttributes.MediaSize.ISO_A1);
        arrayList.add(PrintAttributes.MediaSize.ISO_A2);
        arrayList.add(PrintAttributes.MediaSize.ISO_A3);
        arrayList.add(PrintAttributes.MediaSize.ISO_A4);
        arrayList.add(PrintAttributes.MediaSize.ISO_A5);
        arrayList.add(PrintAttributes.MediaSize.ISO_A6);
        arrayList.add(PrintAttributes.MediaSize.ISO_A7);
        arrayList.add(PrintAttributes.MediaSize.ISO_A8);
        arrayList.add(PrintAttributes.MediaSize.ISO_A9);
        arrayList.add(PrintAttributes.MediaSize.ISO_A10);
        arrayList.add(PrintAttributes.MediaSize.ISO_B0);
        arrayList.add(PrintAttributes.MediaSize.ISO_B1);
        arrayList.add(PrintAttributes.MediaSize.ISO_B2);
        arrayList.add(PrintAttributes.MediaSize.ISO_B3);
        arrayList.add(PrintAttributes.MediaSize.ISO_B4);
        arrayList.add(PrintAttributes.MediaSize.ISO_B5);
        arrayList.add(PrintAttributes.MediaSize.ISO_B6);
        arrayList.add(PrintAttributes.MediaSize.ISO_B7);
        arrayList.add(PrintAttributes.MediaSize.ISO_B8);
        arrayList.add(PrintAttributes.MediaSize.ISO_B9);
        arrayList.add(PrintAttributes.MediaSize.ISO_B10);
        arrayList.add(PrintAttributes.MediaSize.ISO_C0);
        arrayList.add(PrintAttributes.MediaSize.ISO_C1);
        arrayList.add(PrintAttributes.MediaSize.ISO_C2);
        arrayList.add(PrintAttributes.MediaSize.ISO_C3);
        arrayList.add(PrintAttributes.MediaSize.ISO_C4);
        arrayList.add(PrintAttributes.MediaSize.ISO_C5);
        arrayList.add(PrintAttributes.MediaSize.ISO_C6);
        arrayList.add(PrintAttributes.MediaSize.ISO_C7);
        arrayList.add(PrintAttributes.MediaSize.ISO_C8);
        arrayList.add(PrintAttributes.MediaSize.ISO_C9);
        arrayList.add(PrintAttributes.MediaSize.ISO_C10);
        arrayList.add(PrintAttributes.MediaSize.NA_LETTER);
        arrayList.add(PrintAttributes.MediaSize.NA_GOVT_LETTER);
        arrayList.add(PrintAttributes.MediaSize.NA_LEGAL);
        arrayList.add(PrintAttributes.MediaSize.NA_JUNIOR_LEGAL);
        arrayList.add(PrintAttributes.MediaSize.NA_LEDGER);
        arrayList.add(PrintAttributes.MediaSize.NA_TABLOID);
        arrayList.add(PrintAttributes.MediaSize.NA_INDEX_3X5);
        arrayList.add(PrintAttributes.MediaSize.NA_INDEX_4X6);
        arrayList.add(PrintAttributes.MediaSize.NA_INDEX_5X8);
        arrayList.add(PrintAttributes.MediaSize.NA_MONARCH);
        arrayList.add(PrintAttributes.MediaSize.NA_QUARTO);
        arrayList.add(PrintAttributes.MediaSize.NA_FOOLSCAP);
        arrayList.add(PrintAttributes.MediaSize.ROC_8K);
        arrayList.add(PrintAttributes.MediaSize.ROC_16K);
        arrayList.add(PrintAttributes.MediaSize.PRC_1);
        arrayList.add(PrintAttributes.MediaSize.PRC_2);
        arrayList.add(PrintAttributes.MediaSize.PRC_3);
        arrayList.add(PrintAttributes.MediaSize.PRC_4);
        arrayList.add(PrintAttributes.MediaSize.PRC_5);
        arrayList.add(PrintAttributes.MediaSize.PRC_6);
        arrayList.add(PrintAttributes.MediaSize.PRC_7);
        arrayList.add(PrintAttributes.MediaSize.PRC_8);
        arrayList.add(PrintAttributes.MediaSize.PRC_9);
        arrayList.add(PrintAttributes.MediaSize.PRC_10);
        arrayList.add(PrintAttributes.MediaSize.PRC_16K);
        arrayList.add(PrintAttributes.MediaSize.OM_PA_KAI);
        arrayList.add(PrintAttributes.MediaSize.OM_DAI_PA_KAI);
        arrayList.add(PrintAttributes.MediaSize.OM_JUURO_KU_KAI);
        arrayList.add(PrintAttributes.MediaSize.JIS_B10);
        arrayList.add(PrintAttributes.MediaSize.JIS_B9);
        arrayList.add(PrintAttributes.MediaSize.JIS_B8);
        arrayList.add(PrintAttributes.MediaSize.JIS_B7);
        arrayList.add(PrintAttributes.MediaSize.JIS_B6);
        arrayList.add(PrintAttributes.MediaSize.JIS_B5);
        arrayList.add(PrintAttributes.MediaSize.JIS_B4);
        arrayList.add(PrintAttributes.MediaSize.JIS_B3);
        arrayList.add(PrintAttributes.MediaSize.JIS_B2);
        arrayList.add(PrintAttributes.MediaSize.JIS_B1);
        arrayList.add(PrintAttributes.MediaSize.JIS_B0);
        arrayList.add(PrintAttributes.MediaSize.JIS_EXEC);
        arrayList.add(PrintAttributes.MediaSize.JPN_CHOU4);
        arrayList.add(PrintAttributes.MediaSize.JPN_CHOU3);
        arrayList.add(PrintAttributes.MediaSize.JPN_CHOU2);
        arrayList.add(PrintAttributes.MediaSize.JPN_HAGAKI);
        arrayList.add(PrintAttributes.MediaSize.JPN_OUFUKU);
        arrayList.add(PrintAttributes.MediaSize.JPN_KAHU);
        arrayList.add(PrintAttributes.MediaSize.JPN_KAKU2);
        arrayList.add(PrintAttributes.MediaSize.JPN_YOU4);
        return arrayList;
    }

    void n(String str, Double d2, Double d3) {
        PrintAttributes.MediaSize mediaSizeAsPortrait;
        this.jobName = str;
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        int iIntValue = Double.valueOf((d2.doubleValue() * 1000.0d) / 72.0d).intValue();
        int iIntValue2 = Double.valueOf((d3.doubleValue() * 1000.0d) / 72.0d).intValue();
        boolean z2 = iIntValue2 >= iIntValue;
        Iterator it = m().iterator();
        while (true) {
            if (!it.hasNext()) {
                mediaSizeAsPortrait = null;
                break;
            }
            PrintAttributes.MediaSize mediaSize = (PrintAttributes.MediaSize) it.next();
            mediaSizeAsPortrait = z2 ? mediaSize.asPortrait() : mediaSize.asLandscape();
            if (iIntValue + 20 >= mediaSizeAsPortrait.getWidthMils() && iIntValue - 20 <= mediaSizeAsPortrait.getWidthMils() && iIntValue2 + 20 >= mediaSizeAsPortrait.getHeightMils() && iIntValue2 - 20 <= mediaSizeAsPortrait.getHeightMils()) {
                break;
            }
        }
        if (mediaSizeAsPortrait == null) {
            mediaSizeAsPortrait = z2 ? PrintAttributes.MediaSize.UNKNOWN_PORTRAIT : PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        }
        builder.setMediaSize(mediaSizeAsPortrait);
        this.printJob = printManager.print(str, this, builder.build());
    }

    @Override // android.print.PrintDocumentAdapter
    public void onFinish() {
        new Thread(new Runnable() { // from class: net.nfet.flutter.printing.c
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f26053a.lambda$onFinish$2();
            }
        }).start();
    }

    @Override // android.print.PrintDocumentAdapter
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        if (cancellationSignal.isCanceled()) {
            layoutResultCallback.onLayoutCancelled();
            return;
        }
        this.callback = layoutResultCallback;
        PrintAttributes.MediaSize mediaSize = printAttributes2.getMediaSize();
        PrintAttributes.Margins minMargins = printAttributes2.getMinMargins();
        this.printing.d(this, Double.valueOf((mediaSize.getWidthMils() * 72.0d) / 1000.0d), (mediaSize.getHeightMils() * 72.0d) / 1000.0d, (minMargins.getLeftMils() * 72.0d) / 1000.0d, (minMargins.getTopMils() * 72.0d) / 1000.0d, (minMargins.getRightMils() * 72.0d) / 1000.0d, (minMargins.getBottomMils() * 72.0d) / 1000.0d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    @Override // android.print.PrintDocumentAdapter
    public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) throws Throwable {
        ?? r4 = 0;
        r4 = 0;
        r4 = 0;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                    try {
                        byte[] bArr = this.documentData;
                        fileOutputStream.write(bArr, 0, bArr.length);
                        PageRange pageRange = PageRange.ALL_PAGES;
                        writeResultCallback.onWriteFinished(new PageRange[]{pageRange});
                        fileOutputStream.close();
                        r4 = pageRange;
                    } catch (IOException e2) {
                        e = e2;
                        r4 = fileOutputStream;
                        e.printStackTrace();
                        if (r4 != 0) {
                            r4.close();
                            r4 = r4;
                        }
                    } catch (Throwable th) {
                        th = th;
                        r4 = fileOutputStream;
                        if (r4 != 0) {
                            try {
                                r4.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    void p(final byte[] bArr, final ArrayList arrayList, final Double d2) {
        Thread thread = new Thread(new Runnable() { // from class: net.nfet.flutter.printing.e
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f26056a.lambda$rasterPdf$5(bArr, arrayList, d2);
            }
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: net.nfet.flutter.printing.f
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread2, Throwable th) {
                this.f26060a.lambda$rasterPdf$7(thread2, th);
            }
        });
        thread.start();
    }

    void q(byte[] bArr) {
        this.documentData = bArr;
        this.callback.onLayoutFinished(new PrintDocumentInfo.Builder(this.jobName).setContentType(0).build(), true);
    }
}
