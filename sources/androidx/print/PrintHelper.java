package androidx.print;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public final class PrintHelper {

    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_COLOR = 2;

    @SuppressLint({"InlinedApi"})
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelper";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;

    /* renamed from: g, reason: collision with root package name */
    static final boolean f5710g;

    /* renamed from: h, reason: collision with root package name */
    static final boolean f5711h;

    /* renamed from: a, reason: collision with root package name */
    final Context f5712a;

    /* renamed from: b, reason: collision with root package name */
    BitmapFactory.Options f5713b = null;

    /* renamed from: c, reason: collision with root package name */
    final Object f5714c = new Object();

    /* renamed from: d, reason: collision with root package name */
    int f5715d = 2;

    /* renamed from: e, reason: collision with root package name */
    int f5716e = 2;

    /* renamed from: f, reason: collision with root package name */
    int f5717f = 1;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    @RequiresApi(19)
    private class PrintBitmapAdapter extends PrintDocumentAdapter {
        private PrintAttributes mAttributes;
        private final Bitmap mBitmap;
        private final OnPrintFinishCallback mCallback;
        private final int mFittingMode;
        private final String mJobName;

        PrintBitmapAdapter(String str, int i2, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            this.mJobName = str;
            this.mFittingMode = i2;
            this.mBitmap = bitmap;
            this.mCallback = onPrintFinishCallback;
        }

        @Override // android.print.PrintDocumentAdapter
        public void onFinish() {
            OnPrintFinishCallback onPrintFinishCallback = this.mCallback;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback.onFinish();
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            this.mAttributes = printAttributes2;
            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
        }

        @Override // android.print.PrintDocumentAdapter
        public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.e(this.mAttributes, this.mFittingMode, this.mBitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
        }
    }

    @RequiresApi(19)
    private class PrintUriAdapter extends PrintDocumentAdapter {

        /* renamed from: a, reason: collision with root package name */
        final String f5727a;

        /* renamed from: b, reason: collision with root package name */
        final Uri f5728b;

        /* renamed from: c, reason: collision with root package name */
        final OnPrintFinishCallback f5729c;

        /* renamed from: d, reason: collision with root package name */
        final int f5730d;

        /* renamed from: e, reason: collision with root package name */
        PrintAttributes f5731e;

        /* renamed from: f, reason: collision with root package name */
        AsyncTask f5732f;

        /* renamed from: g, reason: collision with root package name */
        Bitmap f5733g = null;

        PrintUriAdapter(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback, int i2) {
            this.f5727a = str;
            this.f5728b = uri;
            this.f5729c = onPrintFinishCallback;
            this.f5730d = i2;
        }

        void a() {
            synchronized (PrintHelper.this.f5714c) {
                try {
                    BitmapFactory.Options options = PrintHelper.this.f5713b;
                    if (options != null) {
                        if (Build.VERSION.SDK_INT < 24) {
                            options.requestCancelDecode();
                        }
                        PrintHelper.this.f5713b = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onFinish() {
            super.onFinish();
            a();
            AsyncTask asyncTask = this.f5732f;
            if (asyncTask != null) {
                asyncTask.cancel(true);
            }
            OnPrintFinishCallback onPrintFinishCallback = this.f5729c;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback.onFinish();
            }
            Bitmap bitmap = this.f5733g;
            if (bitmap != null) {
                bitmap.recycle();
                this.f5733g = null;
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
            synchronized (this) {
                this.f5731e = printAttributes2;
            }
            if (cancellationSignal.isCanceled()) {
                layoutResultCallback.onLayoutCancelled();
            } else if (this.f5733g != null) {
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.f5727a).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
            } else {
                this.f5732f = new AsyncTask<Uri, Boolean, Bitmap>() { // from class: androidx.print.PrintHelper.PrintUriAdapter.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Bitmap doInBackground(Uri... uriArr) {
                        try {
                            PrintUriAdapter printUriAdapter = PrintUriAdapter.this;
                            return PrintHelper.this.d(printUriAdapter.f5728b);
                        } catch (FileNotFoundException unused) {
                            return null;
                        }
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    /* renamed from: b, reason: merged with bridge method [inline-methods] */
                    public void onCancelled(Bitmap bitmap) {
                        layoutResultCallback.onLayoutCancelled();
                        PrintUriAdapter.this.f5732f = null;
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    /* renamed from: c, reason: merged with bridge method [inline-methods] */
                    public void onPostExecute(Bitmap bitmap) {
                        PrintAttributes.MediaSize mediaSize;
                        super.onPostExecute(bitmap);
                        if (bitmap != null && (!PrintHelper.f5710g || PrintHelper.this.f5717f == 0)) {
                            synchronized (this) {
                                mediaSize = PrintUriAdapter.this.f5731e.getMediaSize();
                            }
                            if (mediaSize != null && mediaSize.isPortrait() != PrintHelper.c(bitmap)) {
                                Matrix matrix = new Matrix();
                                matrix.postRotate(90.0f);
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            }
                        }
                        PrintUriAdapter.this.f5733g = bitmap;
                        if (bitmap != null) {
                            layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(PrintUriAdapter.this.f5727a).setContentType(1).setPageCount(1).build(), true ^ printAttributes2.equals(printAttributes));
                        } else {
                            layoutResultCallback.onLayoutFailed(null);
                        }
                        PrintUriAdapter.this.f5732f = null;
                    }

                    @Override // android.os.AsyncTask
                    protected void onPreExecute() {
                        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.print.PrintHelper.PrintUriAdapter.1.1
                            @Override // android.os.CancellationSignal.OnCancelListener
                            public void onCancel() {
                                PrintUriAdapter.this.a();
                                cancel(false);
                            }
                        });
                    }
                }.execute(new Uri[0]);
            }
        }

        @Override // android.print.PrintDocumentAdapter
        public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            PrintHelper.this.e(this.f5731e, this.f5730d, this.f5733g, parcelFileDescriptor, cancellationSignal, writeResultCallback);
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        f5710g = i2 > 23;
        f5711h = i2 != 23;
    }

    public PrintHelper(@NonNull Context context) {
        this.f5712a = context;
    }

    static Bitmap a(Bitmap bitmap, int i2) {
        if (i2 != 1) {
            return bitmap;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    static Matrix b(int i2, int i3, RectF rectF, int i4) {
        Matrix matrix = new Matrix();
        float f2 = i2;
        float fWidth = rectF.width() / f2;
        float fMax = i4 == 2 ? Math.max(fWidth, rectF.height() / i3) : Math.min(fWidth, rectF.height() / i3);
        matrix.postScale(fMax, fMax);
        matrix.postTranslate((rectF.width() - (f2 * fMax)) / 2.0f, (rectF.height() - (i3 * fMax)) / 2.0f);
        return matrix;
    }

    static boolean c(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight();
    }

    @RequiresApi(19)
    private static PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder minMargins = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
        if (printAttributes.getColorMode() != 0) {
            minMargins.setColorMode(printAttributes.getColorMode());
        }
        if (printAttributes.getDuplexMode() != 0) {
            minMargins.setDuplexMode(printAttributes.getDuplexMode());
        }
        return minMargins;
    }

    private Bitmap loadBitmap(Uri uri, BitmapFactory.Options options) throws Throwable {
        Context context;
        InputStream inputStreamOpenInputStream;
        if (uri == null || (context = this.f5712a) == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream inputStream = null;
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        } catch (Throwable th) {
            th = th;
        }
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (IOException e2) {
                    Log.w(LOG_TAG, "close fail ", e2);
                }
            }
            return bitmapDecodeStream;
        } catch (Throwable th2) {
            th = th2;
            inputStream = inputStreamOpenInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    Log.w(LOG_TAG, "close fail ", e3);
                }
            }
            throw th;
        }
    }

    public static boolean systemSupportsPrint() {
        return true;
    }

    Bitmap d(Uri uri) throws Throwable {
        BitmapFactory.Options options;
        if (uri == null || this.f5712a == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = true;
        loadBitmap(uri, options2);
        int i2 = options2.outWidth;
        int i3 = options2.outHeight;
        if (i2 > 0 && i3 > 0) {
            int iMax = Math.max(i2, i3);
            int i4 = 1;
            while (iMax > MAX_PRINT_SIZE) {
                iMax >>>= 1;
                i4 <<= 1;
            }
            if (i4 > 0 && Math.min(i2, i3) / i4 > 0) {
                synchronized (this.f5714c) {
                    options = new BitmapFactory.Options();
                    this.f5713b = options;
                    options.inMutable = true;
                    options.inSampleSize = i4;
                }
                try {
                    Bitmap bitmapLoadBitmap = loadBitmap(uri, options);
                    synchronized (this.f5714c) {
                        this.f5713b = null;
                    }
                    return bitmapLoadBitmap;
                } catch (Throwable th) {
                    synchronized (this.f5714c) {
                        this.f5713b = null;
                        throw th;
                    }
                }
            }
        }
        return null;
    }

    void e(final PrintAttributes printAttributes, final int i2, final Bitmap bitmap, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        final PrintAttributes printAttributesBuild = f5711h ? printAttributes : copyAttributes(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
        new AsyncTask<Void, Void, Throwable>() { // from class: androidx.print.PrintHelper.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Throwable doInBackground(Void... voidArr) {
                RectF rectF;
                try {
                    if (cancellationSignal.isCanceled()) {
                        return null;
                    }
                    PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelper.this.f5712a, printAttributesBuild);
                    Bitmap bitmapA = PrintHelper.a(bitmap, printAttributesBuild.getColorMode());
                    if (cancellationSignal.isCanceled()) {
                        return null;
                    }
                    try {
                        PdfDocument.Page pageStartPage = printedPdfDocument.startPage(1);
                        boolean z2 = PrintHelper.f5711h;
                        if (z2) {
                            rectF = new RectF(pageStartPage.getInfo().getContentRect());
                        } else {
                            PrintedPdfDocument printedPdfDocument2 = new PrintedPdfDocument(PrintHelper.this.f5712a, printAttributes);
                            PdfDocument.Page pageStartPage2 = printedPdfDocument2.startPage(1);
                            RectF rectF2 = new RectF(pageStartPage2.getInfo().getContentRect());
                            printedPdfDocument2.finishPage(pageStartPage2);
                            printedPdfDocument2.close();
                            rectF = rectF2;
                        }
                        Matrix matrixB = PrintHelper.b(bitmapA.getWidth(), bitmapA.getHeight(), rectF, i2);
                        if (!z2) {
                            matrixB.postTranslate(rectF.left, rectF.top);
                            pageStartPage.getCanvas().clipRect(rectF);
                        }
                        pageStartPage.getCanvas().drawBitmap(bitmapA, matrixB, null);
                        printedPdfDocument.finishPage(pageStartPage);
                        if (cancellationSignal.isCanceled()) {
                            printedPdfDocument.close();
                            ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor;
                            if (parcelFileDescriptor2 != null) {
                                try {
                                    parcelFileDescriptor2.close();
                                } catch (IOException unused) {
                                }
                            }
                            if (bitmapA != bitmap) {
                                bitmapA.recycle();
                            }
                            return null;
                        }
                        printedPdfDocument.writeTo(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        printedPdfDocument.close();
                        ParcelFileDescriptor parcelFileDescriptor3 = parcelFileDescriptor;
                        if (parcelFileDescriptor3 != null) {
                            try {
                                parcelFileDescriptor3.close();
                            } catch (IOException unused2) {
                            }
                        }
                        if (bitmapA != bitmap) {
                            bitmapA.recycle();
                        }
                        return null;
                    } finally {
                    }
                } catch (Throwable th) {
                    return th;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            /* renamed from: b, reason: merged with bridge method [inline-methods] */
            public void onPostExecute(Throwable th) {
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                } else if (th == null) {
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } else {
                    Log.e(PrintHelper.LOG_TAG, "Error writing printed content", th);
                    writeResultCallback.onWriteFailed(null);
                }
            }
        }.execute(new Void[0]);
    }

    public int getColorMode() {
        return this.f5716e;
    }

    public int getOrientation() {
        int i2 = this.f5717f;
        if (i2 == 0) {
            return 1;
        }
        return i2;
    }

    public int getScaleMode() {
        return this.f5715d;
    }

    public void printBitmap(@NonNull String str, @NonNull Bitmap bitmap) {
        printBitmap(str, bitmap, (OnPrintFinishCallback) null);
    }

    public void setColorMode(int i2) {
        this.f5716e = i2;
    }

    public void setOrientation(int i2) {
        this.f5717f = i2;
    }

    public void setScaleMode(int i2) {
        this.f5715d = i2;
    }

    public void printBitmap(@NonNull String str, @NonNull Bitmap bitmap, @Nullable OnPrintFinishCallback onPrintFinishCallback) {
        if (bitmap == null) {
            return;
        }
        ((PrintManager) this.f5712a.getSystemService("print")).print(str, new PrintBitmapAdapter(str, this.f5715d, bitmap, onPrintFinishCallback), new PrintAttributes.Builder().setMediaSize(c(bitmap) ? PrintAttributes.MediaSize.UNKNOWN_PORTRAIT : PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE).setColorMode(this.f5716e).build());
    }

    public void printBitmap(@NonNull String str, @NonNull Uri uri) throws FileNotFoundException {
        printBitmap(str, uri, (OnPrintFinishCallback) null);
    }

    public void printBitmap(@NonNull String str, @NonNull Uri uri, @Nullable OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        PrintUriAdapter printUriAdapter = new PrintUriAdapter(str, uri, onPrintFinishCallback, this.f5715d);
        PrintManager printManager = (PrintManager) this.f5712a.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.f5716e);
        int i2 = this.f5717f;
        if (i2 == 1 || i2 == 0) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        } else if (i2 == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        printManager.print(str, printUriAdapter, builder.build());
    }
}
