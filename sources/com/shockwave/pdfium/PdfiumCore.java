package com.shockwave.pdfium;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Surface;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.util.Size;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class PdfiumCore {
    private static final Class FD_CLASS = FileDescriptor.class;
    private static final String FD_FIELD_NAME = "descriptor";
    private static final String TAG = "com.shockwave.pdfium.PdfiumCore";
    private static final Object lock;
    private static Field mFdField;
    private int mCurrentDpi;

    static {
        try {
            System.loadLibrary("c++_shared");
            System.loadLibrary("modpng");
            System.loadLibrary("modft2");
            System.loadLibrary("modpdfium");
            System.loadLibrary("jniPdfium");
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "Native libraries failed to load - " + e2);
        }
        lock = new Object();
        mFdField = null;
    }

    public PdfiumCore(Context context) {
        this.mCurrentDpi = context.getResources().getDisplayMetrics().densityDpi;
        Log.d(TAG, "Starting PdfiumAndroid 1.9.2");
    }

    public static int getNumFd(ParcelFileDescriptor parcelFileDescriptor) throws NoSuchFieldException, SecurityException {
        try {
            if (mFdField == null) {
                Field declaredField = FD_CLASS.getDeclaredField(FD_FIELD_NAME);
                mFdField = declaredField;
                declaredField.setAccessible(true);
            }
            return mFdField.getInt(parcelFileDescriptor.getFileDescriptor());
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return -1;
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    private native void nativeCloseDocument(long j2);

    private native void nativeClosePage(long j2);

    private native void nativeClosePages(long[] jArr);

    private native long nativeGetBookmarkDestIndex(long j2, long j3);

    private native String nativeGetBookmarkTitle(long j2);

    private native Integer nativeGetDestPageIndex(long j2, long j3);

    private native String nativeGetDocumentMetaText(long j2, String str);

    private native Long nativeGetFirstChildBookmark(long j2, Long l2);

    private native RectF nativeGetLinkRect(long j2);

    private native String nativeGetLinkURI(long j2, long j3);

    private native int nativeGetPageCount(long j2);

    private native int nativeGetPageHeightPixel(long j2, int i2);

    private native int nativeGetPageHeightPoint(long j2);

    private native long[] nativeGetPageLinks(long j2);

    private native Size nativeGetPageSizeByIndex(long j2, int i2, int i3);

    private native int nativeGetPageWidthPixel(long j2, int i2);

    private native int nativeGetPageWidthPoint(long j2);

    private native Long nativeGetSiblingBookmark(long j2, long j3);

    private native long nativeLoadPage(long j2, int i2);

    private native long[] nativeLoadPages(long j2, int i2, int i3);

    private native long nativeOpenDocument(int i2, String str);

    private native long nativeOpenMemDocument(byte[] bArr, String str);

    private native Point nativePageCoordsToDevice(long j2, int i2, int i3, int i4, int i5, int i6, double d2, double d3);

    private native void nativeRenderPage(long j2, Surface surface, int i2, int i3, int i4, int i5, int i6, boolean z2);

    private native void nativeRenderPageBitmap(long j2, Bitmap bitmap, int i2, int i3, int i4, int i5, int i6, boolean z2);

    private void recursiveGetBookmark(List<PdfDocument.Bookmark> list, PdfDocument pdfDocument, long j2) {
        PdfDocument.Bookmark bookmark = new PdfDocument.Bookmark();
        bookmark.f19838c = j2;
        bookmark.f19836a = nativeGetBookmarkTitle(j2);
        bookmark.f19837b = nativeGetBookmarkDestIndex(pdfDocument.f19833a, j2);
        list.add(bookmark);
        Long lNativeGetFirstChildBookmark = nativeGetFirstChildBookmark(pdfDocument.f19833a, Long.valueOf(j2));
        if (lNativeGetFirstChildBookmark != null) {
            recursiveGetBookmark(bookmark.getChildren(), pdfDocument, lNativeGetFirstChildBookmark.longValue());
        }
        Long lNativeGetSiblingBookmark = nativeGetSiblingBookmark(pdfDocument.f19833a, j2);
        if (lNativeGetSiblingBookmark != null) {
            recursiveGetBookmark(list, pdfDocument, lNativeGetSiblingBookmark.longValue());
        }
    }

    public void closeDocument(PdfDocument pdfDocument) {
        synchronized (lock) {
            try {
                Iterator it = pdfDocument.f19835c.keySet().iterator();
                while (it.hasNext()) {
                    nativeClosePage(((Long) pdfDocument.f19835c.get((Integer) it.next())).longValue());
                }
                pdfDocument.f19835c.clear();
                nativeCloseDocument(pdfDocument.f19833a);
                ParcelFileDescriptor parcelFileDescriptor = pdfDocument.f19834b;
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException unused) {
                    }
                    pdfDocument.f19834b = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public PdfDocument.Meta getDocumentMeta(PdfDocument pdfDocument) {
        PdfDocument.Meta meta;
        synchronized (lock) {
            meta = new PdfDocument.Meta();
            meta.f19839a = nativeGetDocumentMetaText(pdfDocument.f19833a, "Title");
            meta.f19840b = nativeGetDocumentMetaText(pdfDocument.f19833a, "Author");
            meta.f19841c = nativeGetDocumentMetaText(pdfDocument.f19833a, "Subject");
            meta.f19842d = nativeGetDocumentMetaText(pdfDocument.f19833a, "Keywords");
            meta.f19843e = nativeGetDocumentMetaText(pdfDocument.f19833a, "Creator");
            meta.f19844f = nativeGetDocumentMetaText(pdfDocument.f19833a, "Producer");
            meta.f19845g = nativeGetDocumentMetaText(pdfDocument.f19833a, "CreationDate");
            meta.f19846h = nativeGetDocumentMetaText(pdfDocument.f19833a, "ModDate");
        }
        return meta;
    }

    public int getPageCount(PdfDocument pdfDocument) {
        int iNativeGetPageCount;
        synchronized (lock) {
            iNativeGetPageCount = nativeGetPageCount(pdfDocument.f19833a);
        }
        return iNativeGetPageCount;
    }

    public int getPageHeight(PdfDocument pdfDocument, int i2) {
        synchronized (lock) {
            try {
                Long l2 = (Long) pdfDocument.f19835c.get(Integer.valueOf(i2));
                if (l2 == null) {
                    return 0;
                }
                return nativeGetPageHeightPixel(l2.longValue(), this.mCurrentDpi);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getPageHeightPoint(PdfDocument pdfDocument, int i2) {
        synchronized (lock) {
            try {
                Long l2 = (Long) pdfDocument.f19835c.get(Integer.valueOf(i2));
                if (l2 == null) {
                    return 0;
                }
                return nativeGetPageHeightPoint(l2.longValue());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List<PdfDocument.Link> getPageLinks(PdfDocument pdfDocument, int i2) {
        synchronized (lock) {
            try {
                ArrayList arrayList = new ArrayList();
                Long l2 = (Long) pdfDocument.f19835c.get(Integer.valueOf(i2));
                if (l2 == null) {
                    return arrayList;
                }
                for (long j2 : nativeGetPageLinks(l2.longValue())) {
                    Integer numNativeGetDestPageIndex = nativeGetDestPageIndex(pdfDocument.f19833a, j2);
                    String strNativeGetLinkURI = nativeGetLinkURI(pdfDocument.f19833a, j2);
                    RectF rectFNativeGetLinkRect = nativeGetLinkRect(j2);
                    if (rectFNativeGetLinkRect != null && (numNativeGetDestPageIndex != null || strNativeGetLinkURI != null)) {
                        arrayList.add(new PdfDocument.Link(rectFNativeGetLinkRect, numNativeGetDestPageIndex, strNativeGetLinkURI));
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Size getPageSize(PdfDocument pdfDocument, int i2) {
        Size sizeNativeGetPageSizeByIndex;
        synchronized (lock) {
            sizeNativeGetPageSizeByIndex = nativeGetPageSizeByIndex(pdfDocument.f19833a, i2, this.mCurrentDpi);
        }
        return sizeNativeGetPageSizeByIndex;
    }

    public int getPageWidth(PdfDocument pdfDocument, int i2) {
        synchronized (lock) {
            try {
                Long l2 = (Long) pdfDocument.f19835c.get(Integer.valueOf(i2));
                if (l2 == null) {
                    return 0;
                }
                return nativeGetPageWidthPixel(l2.longValue(), this.mCurrentDpi);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getPageWidthPoint(PdfDocument pdfDocument, int i2) {
        synchronized (lock) {
            try {
                Long l2 = (Long) pdfDocument.f19835c.get(Integer.valueOf(i2));
                if (l2 == null) {
                    return 0;
                }
                return nativeGetPageWidthPoint(l2.longValue());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public List<PdfDocument.Bookmark> getTableOfContents(PdfDocument pdfDocument) {
        ArrayList arrayList;
        synchronized (lock) {
            try {
                arrayList = new ArrayList();
                Long lNativeGetFirstChildBookmark = nativeGetFirstChildBookmark(pdfDocument.f19833a, null);
                if (lNativeGetFirstChildBookmark != null) {
                    recursiveGetBookmark(arrayList, pdfDocument, lNativeGetFirstChildBookmark.longValue());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public Point mapPageCoordsToDevice(PdfDocument pdfDocument, int i2, int i3, int i4, int i5, int i6, int i7, double d2, double d3) {
        return nativePageCoordsToDevice(((Long) pdfDocument.f19835c.get(Integer.valueOf(i2))).longValue(), i3, i4, i5, i6, i7, d2, d3);
    }

    public RectF mapRectToDevice(PdfDocument pdfDocument, int i2, int i3, int i4, int i5, int i6, int i7, RectF rectF) {
        Point pointMapPageCoordsToDevice = mapPageCoordsToDevice(pdfDocument, i2, i3, i4, i5, i6, i7, rectF.left, rectF.top);
        Point pointMapPageCoordsToDevice2 = mapPageCoordsToDevice(pdfDocument, i2, i3, i4, i5, i6, i7, rectF.right, rectF.bottom);
        return new RectF(pointMapPageCoordsToDevice.x, pointMapPageCoordsToDevice.y, pointMapPageCoordsToDevice2.x, pointMapPageCoordsToDevice2.y);
    }

    public PdfDocument newDocument(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        return newDocument(parcelFileDescriptor, (String) null);
    }

    public long openPage(PdfDocument pdfDocument, int i2) {
        long jNativeLoadPage;
        synchronized (lock) {
            jNativeLoadPage = nativeLoadPage(pdfDocument.f19833a, i2);
            pdfDocument.f19835c.put(Integer.valueOf(i2), Long.valueOf(jNativeLoadPage));
        }
        return jNativeLoadPage;
    }

    public void renderPage(PdfDocument pdfDocument, Surface surface, int i2, int i3, int i4, int i5, int i6) throws Throwable {
        renderPage(pdfDocument, surface, i2, i3, i4, i5, i6, false);
    }

    public void renderPageBitmap(PdfDocument pdfDocument, Bitmap bitmap, int i2, int i3, int i4, int i5, int i6) throws Throwable {
        renderPageBitmap(pdfDocument, bitmap, i2, i3, i4, i5, i6, false);
    }

    public PdfDocument newDocument(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.f19834b = parcelFileDescriptor;
        synchronized (lock) {
            pdfDocument.f19833a = nativeOpenDocument(getNumFd(parcelFileDescriptor), str);
        }
        return pdfDocument;
    }

    public void renderPage(PdfDocument pdfDocument, Surface surface, int i2, int i3, int i4, int i5, int i6, boolean z2) throws Throwable {
        synchronized (lock) {
            try {
                try {
                } catch (NullPointerException e2) {
                    e = e2;
                } catch (Exception e3) {
                    e = e3;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
                try {
                    nativeRenderPage(((Long) pdfDocument.f19835c.get(Integer.valueOf(i2))).longValue(), surface, this.mCurrentDpi, i3, i4, i5, i6, z2);
                } catch (NullPointerException e4) {
                    e = e4;
                    Log.e(TAG, "mContext may be null");
                    e.printStackTrace();
                } catch (Exception e5) {
                    e = e5;
                    Log.e(TAG, "Exception throw from native");
                    e.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void renderPageBitmap(PdfDocument pdfDocument, Bitmap bitmap, int i2, int i3, int i4, int i5, int i6, boolean z2) throws Throwable {
        synchronized (lock) {
            try {
                try {
                } catch (NullPointerException e2) {
                    e = e2;
                } catch (Exception e3) {
                    e = e3;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
                try {
                    nativeRenderPageBitmap(((Long) pdfDocument.f19835c.get(Integer.valueOf(i2))).longValue(), bitmap, this.mCurrentDpi, i3, i4, i5, i6, z2);
                } catch (NullPointerException e4) {
                    e = e4;
                    Log.e(TAG, "mContext may be null");
                    e.printStackTrace();
                } catch (Exception e5) {
                    e = e5;
                    Log.e(TAG, "Exception throw from native");
                    e.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public long[] openPage(PdfDocument pdfDocument, int i2, int i3) {
        long[] jArrNativeLoadPages;
        synchronized (lock) {
            try {
                jArrNativeLoadPages = nativeLoadPages(pdfDocument.f19833a, i2, i3);
                for (long j2 : jArrNativeLoadPages) {
                    if (i2 <= i3) {
                        pdfDocument.f19835c.put(Integer.valueOf(i2), Long.valueOf(j2));
                        i2++;
                    }
                }
            } finally {
            }
        }
        return jArrNativeLoadPages;
    }

    public PdfDocument newDocument(byte[] bArr) throws IOException {
        return newDocument(bArr, (String) null);
    }

    public PdfDocument newDocument(byte[] bArr, String str) throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        synchronized (lock) {
            pdfDocument.f19833a = nativeOpenMemDocument(bArr, str);
        }
        return pdfDocument;
    }
}
