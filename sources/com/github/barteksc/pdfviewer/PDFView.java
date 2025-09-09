package com.github.barteksc.pdfviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.github.barteksc.pdfviewer.exception.PageRenderingException;
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler;
import com.github.barteksc.pdfviewer.link.LinkHandler;
import com.github.barteksc.pdfviewer.listener.Callbacks;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLongPressListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.model.PagePart;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.github.barteksc.pdfviewer.source.AssetSource;
import com.github.barteksc.pdfviewer.source.ByteArraySource;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.github.barteksc.pdfviewer.source.FileSource;
import com.github.barteksc.pdfviewer.source.InputStreamSource;
import com.github.barteksc.pdfviewer.source.UriSource;
import com.github.barteksc.pdfviewer.util.Constants;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.github.barteksc.pdfviewer.util.MathUtils;
import com.github.barteksc.pdfviewer.util.SnapEdge;
import com.github.barteksc.pdfviewer.util.Util;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.shockwave.pdfium.util.Size;
import com.shockwave.pdfium.util.SizeF;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PDFView extends RelativeLayout {
    public static final float DEFAULT_MAX_SCALE = 3.0f;
    public static final float DEFAULT_MID_SCALE = 1.75f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    private static final String TAG = "PDFView";

    /* renamed from: a, reason: collision with root package name */
    CacheManager f12471a;
    private AnimationManager animationManager;
    private boolean annotationRendering;
    private PaintFlagsDrawFilter antialiasFilter;
    private boolean autoSpacing;

    /* renamed from: b, reason: collision with root package name */
    PdfFile f12472b;
    private boolean bestQuality;

    /* renamed from: c, reason: collision with root package name */
    RenderingHandler f12473c;
    private int currentPage;
    private float currentXOffset;
    private float currentYOffset;

    /* renamed from: d, reason: collision with root package name */
    Callbacks f12474d;
    private Paint debugPaint;
    private DecodingAsyncTask decodingAsyncTask;
    private int defaultPage;
    private boolean doubletapEnabled;
    private DragPinchManager dragPinchManager;
    private boolean enableAntialiasing;
    private boolean enableSwipe;
    private boolean fitEachPage;
    private boolean hasSize;
    private boolean isScrollHandleInit;
    private float maxZoom;
    private float midZoom;
    private float minZoom;
    private boolean nightMode;
    private List<Integer> onDrawPagesNums;
    private FitPolicy pageFitPolicy;
    private boolean pageFling;
    private boolean pageSnap;
    private PagesLoader pagesLoader;
    private Paint paint;
    private PdfiumCore pdfiumCore;
    private boolean recycled;
    private boolean renderDuringScale;
    private HandlerThread renderingHandlerThread;
    private ScrollDir scrollDir;
    private ScrollHandle scrollHandle;
    private int spacingPx;
    private State state;
    private boolean swipeVertical;
    private Configurator waitingDocumentConfigurator;
    private float zoom;

    public class Configurator {
        private boolean annotationRendering;
        private boolean antialiasing;
        private boolean autoSpacing;
        private int defaultPage;
        private final DocumentSource documentSource;
        private boolean enableDoubletap;
        private boolean enableSwipe;
        private boolean fitEachPage;
        private LinkHandler linkHandler;
        private boolean nightMode;
        private OnDrawListener onDrawAllListener;
        private OnDrawListener onDrawListener;
        private OnErrorListener onErrorListener;
        private OnLoadCompleteListener onLoadCompleteListener;
        private OnLongPressListener onLongPressListener;
        private OnPageChangeListener onPageChangeListener;
        private OnPageErrorListener onPageErrorListener;
        private OnPageScrollListener onPageScrollListener;
        private OnRenderListener onRenderListener;
        private OnTapListener onTapListener;
        private FitPolicy pageFitPolicy;
        private boolean pageFling;
        private int[] pageNumbers;
        private boolean pageSnap;
        private String password;
        private ScrollHandle scrollHandle;
        private int spacing;
        private boolean swipeHorizontal;

        public Configurator autoSpacing(boolean z2) {
            this.autoSpacing = z2;
            return this;
        }

        public Configurator defaultPage(int i2) {
            this.defaultPage = i2;
            return this;
        }

        public Configurator disableLongpress() {
            PDFView.this.dragPinchManager.b();
            return this;
        }

        public Configurator enableAnnotationRendering(boolean z2) {
            this.annotationRendering = z2;
            return this;
        }

        public Configurator enableAntialiasing(boolean z2) {
            this.antialiasing = z2;
            return this;
        }

        public Configurator enableDoubletap(boolean z2) {
            this.enableDoubletap = z2;
            return this;
        }

        public Configurator enableSwipe(boolean z2) {
            this.enableSwipe = z2;
            return this;
        }

        public Configurator fitEachPage(boolean z2) {
            this.fitEachPage = z2;
            return this;
        }

        public Configurator linkHandler(LinkHandler linkHandler) {
            this.linkHandler = linkHandler;
            return this;
        }

        public void load() {
            if (!PDFView.this.hasSize) {
                PDFView.this.waitingDocumentConfigurator = this;
                return;
            }
            PDFView.this.recycle();
            PDFView.this.f12474d.setOnLoadComplete(this.onLoadCompleteListener);
            PDFView.this.f12474d.setOnError(this.onErrorListener);
            PDFView.this.f12474d.setOnDraw(this.onDrawListener);
            PDFView.this.f12474d.setOnDrawAll(this.onDrawAllListener);
            PDFView.this.f12474d.setOnPageChange(this.onPageChangeListener);
            PDFView.this.f12474d.setOnPageScroll(this.onPageScrollListener);
            PDFView.this.f12474d.setOnRender(this.onRenderListener);
            PDFView.this.f12474d.setOnTap(this.onTapListener);
            PDFView.this.f12474d.setOnLongPress(this.onLongPressListener);
            PDFView.this.f12474d.setOnPageError(this.onPageErrorListener);
            PDFView.this.f12474d.setLinkHandler(this.linkHandler);
            PDFView.this.setSwipeEnabled(this.enableSwipe);
            PDFView.this.setNightMode(this.nightMode);
            PDFView.this.m(this.enableDoubletap);
            PDFView.this.setDefaultPage(this.defaultPage);
            PDFView.this.setSwipeVertical(!this.swipeHorizontal);
            PDFView.this.enableAnnotationRendering(this.annotationRendering);
            PDFView.this.setScrollHandle(this.scrollHandle);
            PDFView.this.enableAntialiasing(this.antialiasing);
            PDFView.this.setSpacing(this.spacing);
            PDFView.this.setAutoSpacing(this.autoSpacing);
            PDFView.this.setPageFitPolicy(this.pageFitPolicy);
            PDFView.this.setFitEachPage(this.fitEachPage);
            PDFView.this.setPageSnap(this.pageSnap);
            PDFView.this.setPageFling(this.pageFling);
            int[] iArr = this.pageNumbers;
            if (iArr != null) {
                PDFView.this.load(this.documentSource, this.password, iArr);
            } else {
                PDFView.this.load(this.documentSource, this.password);
            }
        }

        public Configurator nightMode(boolean z2) {
            this.nightMode = z2;
            return this;
        }

        public Configurator onDraw(OnDrawListener onDrawListener) {
            this.onDrawListener = onDrawListener;
            return this;
        }

        public Configurator onDrawAll(OnDrawListener onDrawListener) {
            this.onDrawAllListener = onDrawListener;
            return this;
        }

        public Configurator onError(OnErrorListener onErrorListener) {
            this.onErrorListener = onErrorListener;
            return this;
        }

        public Configurator onLoad(OnLoadCompleteListener onLoadCompleteListener) {
            this.onLoadCompleteListener = onLoadCompleteListener;
            return this;
        }

        public Configurator onLongPress(OnLongPressListener onLongPressListener) {
            this.onLongPressListener = onLongPressListener;
            return this;
        }

        public Configurator onPageChange(OnPageChangeListener onPageChangeListener) {
            this.onPageChangeListener = onPageChangeListener;
            return this;
        }

        public Configurator onPageError(OnPageErrorListener onPageErrorListener) {
            this.onPageErrorListener = onPageErrorListener;
            return this;
        }

        public Configurator onPageScroll(OnPageScrollListener onPageScrollListener) {
            this.onPageScrollListener = onPageScrollListener;
            return this;
        }

        public Configurator onRender(OnRenderListener onRenderListener) {
            this.onRenderListener = onRenderListener;
            return this;
        }

        public Configurator onTap(OnTapListener onTapListener) {
            this.onTapListener = onTapListener;
            return this;
        }

        public Configurator pageFitPolicy(FitPolicy fitPolicy) {
            this.pageFitPolicy = fitPolicy;
            return this;
        }

        public Configurator pageFling(boolean z2) {
            this.pageFling = z2;
            return this;
        }

        public Configurator pageSnap(boolean z2) {
            this.pageSnap = z2;
            return this;
        }

        public Configurator pages(int... iArr) {
            this.pageNumbers = iArr;
            return this;
        }

        public Configurator password(String str) {
            this.password = str;
            return this;
        }

        public Configurator scrollHandle(ScrollHandle scrollHandle) {
            this.scrollHandle = scrollHandle;
            return this;
        }

        public Configurator spacing(int i2) {
            this.spacing = i2;
            return this;
        }

        public Configurator swipeHorizontal(boolean z2) {
            this.swipeHorizontal = z2;
            return this;
        }

        private Configurator(DocumentSource documentSource) {
            this.pageNumbers = null;
            this.enableSwipe = true;
            this.enableDoubletap = true;
            this.linkHandler = new DefaultLinkHandler(PDFView.this);
            this.defaultPage = 0;
            this.swipeHorizontal = false;
            this.annotationRendering = false;
            this.password = null;
            this.scrollHandle = null;
            this.antialiasing = true;
            this.spacing = 0;
            this.autoSpacing = false;
            this.pageFitPolicy = FitPolicy.WIDTH;
            this.fitEachPage = false;
            this.pageFling = false;
            this.pageSnap = false;
            this.nightMode = false;
            this.documentSource = documentSource;
        }
    }

    enum ScrollDir {
        NONE,
        START,
        END
    }

    private enum State {
        DEFAULT,
        LOADED,
        SHOWN,
        ERROR
    }

    public PDFView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.minZoom = 1.0f;
        this.midZoom = 1.75f;
        this.maxZoom = 3.0f;
        this.scrollDir = ScrollDir.NONE;
        this.currentXOffset = 0.0f;
        this.currentYOffset = 0.0f;
        this.zoom = 1.0f;
        this.recycled = true;
        this.state = State.DEFAULT;
        this.f12474d = new Callbacks();
        this.pageFitPolicy = FitPolicy.WIDTH;
        this.fitEachPage = false;
        this.defaultPage = 0;
        this.swipeVertical = true;
        this.enableSwipe = true;
        this.doubletapEnabled = true;
        this.nightMode = false;
        this.pageSnap = true;
        this.isScrollHandleInit = false;
        this.bestQuality = false;
        this.annotationRendering = false;
        this.renderDuringScale = false;
        this.enableAntialiasing = true;
        this.antialiasFilter = new PaintFlagsDrawFilter(0, 3);
        this.spacingPx = 0;
        this.autoSpacing = false;
        this.pageFling = true;
        this.onDrawPagesNums = new ArrayList(10);
        this.hasSize = false;
        if (isInEditMode()) {
            return;
        }
        this.f12471a = new CacheManager();
        AnimationManager animationManager = new AnimationManager(this);
        this.animationManager = animationManager;
        this.dragPinchManager = new DragPinchManager(this, animationManager);
        this.pagesLoader = new PagesLoader(this);
        this.paint = new Paint();
        Paint paint = new Paint();
        this.debugPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.pdfiumCore = new PdfiumCore(context);
        setWillNotDraw(false);
    }

    private void drawPart(Canvas canvas, PagePart pagePart) {
        float pageOffset;
        float currentScale;
        RectF pageRelativeBounds = pagePart.getPageRelativeBounds();
        Bitmap renderedBitmap = pagePart.getRenderedBitmap();
        if (renderedBitmap.isRecycled()) {
            return;
        }
        SizeF pageSize = this.f12472b.getPageSize(pagePart.getPage());
        if (this.swipeVertical) {
            currentScale = this.f12472b.getPageOffset(pagePart.getPage(), this.zoom);
            pageOffset = toCurrentScale(this.f12472b.getMaxPageWidth() - pageSize.getWidth()) / 2.0f;
        } else {
            pageOffset = this.f12472b.getPageOffset(pagePart.getPage(), this.zoom);
            currentScale = toCurrentScale(this.f12472b.getMaxPageHeight() - pageSize.getHeight()) / 2.0f;
        }
        canvas.translate(pageOffset, currentScale);
        Rect rect = new Rect(0, 0, renderedBitmap.getWidth(), renderedBitmap.getHeight());
        float currentScale2 = toCurrentScale(pageRelativeBounds.left * pageSize.getWidth());
        float currentScale3 = toCurrentScale(pageRelativeBounds.top * pageSize.getHeight());
        RectF rectF = new RectF((int) currentScale2, (int) currentScale3, (int) (currentScale2 + toCurrentScale(pageRelativeBounds.width() * pageSize.getWidth())), (int) (currentScale3 + toCurrentScale(pageRelativeBounds.height() * pageSize.getHeight())));
        float f2 = this.currentXOffset + pageOffset;
        float f3 = this.currentYOffset + currentScale;
        if (rectF.left + f2 >= getWidth() || f2 + rectF.right <= 0.0f || rectF.top + f3 >= getHeight() || f3 + rectF.bottom <= 0.0f) {
            canvas.translate(-pageOffset, -currentScale);
            return;
        }
        canvas.drawBitmap(renderedBitmap, rect, rectF, this.paint);
        if (Constants.DEBUG_MODE) {
            this.debugPaint.setColor(pagePart.getPage() % 2 == 0 ? SupportMenu.CATEGORY_MASK : -16776961);
            canvas.drawRect(rectF, this.debugPaint);
        }
        canvas.translate(-pageOffset, -currentScale);
    }

    private void drawWithListener(Canvas canvas, int i2, OnDrawListener onDrawListener) {
        float pageOffset;
        if (onDrawListener != null) {
            float pageOffset2 = 0.0f;
            if (this.swipeVertical) {
                pageOffset = this.f12472b.getPageOffset(i2, this.zoom);
            } else {
                pageOffset2 = this.f12472b.getPageOffset(i2, this.zoom);
                pageOffset = 0.0f;
            }
            canvas.translate(pageOffset2, pageOffset);
            SizeF pageSize = this.f12472b.getPageSize(i2);
            onDrawListener.onLayerDrawn(canvas, toCurrentScale(pageSize.getWidth()), toCurrentScale(pageSize.getHeight()), i2);
            canvas.translate(-pageOffset2, -pageOffset);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void load(DocumentSource documentSource, String str) {
        load(documentSource, str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAutoSpacing(boolean z2) {
        this.autoSpacing = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultPage(int i2) {
        this.defaultPage = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFitEachPage(boolean z2) {
        this.fitEachPage = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPageFitPolicy(FitPolicy fitPolicy) {
        this.pageFitPolicy = fitPolicy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScrollHandle(ScrollHandle scrollHandle) {
        this.scrollHandle = scrollHandle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSpacing(int i2) {
        this.spacingPx = Util.getDP(getContext(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSwipeVertical(boolean z2) {
        this.swipeVertical = z2;
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i2) {
        PdfFile pdfFile = this.f12472b;
        if (pdfFile == null) {
            return true;
        }
        if (this.swipeVertical) {
            if (i2 >= 0 || this.currentXOffset >= 0.0f) {
                return i2 > 0 && this.currentXOffset + toCurrentScale(pdfFile.getMaxPageWidth()) > ((float) getWidth());
            }
            return true;
        }
        if (i2 >= 0 || this.currentXOffset >= 0.0f) {
            return i2 > 0 && this.currentXOffset + pdfFile.getDocLen(this.zoom) > ((float) getWidth());
        }
        return true;
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i2) {
        PdfFile pdfFile = this.f12472b;
        if (pdfFile == null) {
            return true;
        }
        if (this.swipeVertical) {
            if (i2 >= 0 || this.currentYOffset >= 0.0f) {
                return i2 > 0 && this.currentYOffset + pdfFile.getDocLen(this.zoom) > ((float) getHeight());
            }
            return true;
        }
        if (i2 >= 0 || this.currentYOffset >= 0.0f) {
            return i2 > 0 && this.currentYOffset + toCurrentScale(pdfFile.getMaxPageHeight()) > ((float) getHeight());
        }
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (isInEditMode()) {
            return;
        }
        this.animationManager.d();
    }

    public boolean doRenderDuringScale() {
        return this.renderDuringScale;
    }

    public boolean documentFitsView() {
        float docLen = this.f12472b.getDocLen(1.0f);
        return this.swipeVertical ? docLen < ((float) getHeight()) : docLen < ((float) getWidth());
    }

    public void enableAnnotationRendering(boolean z2) {
        this.annotationRendering = z2;
    }

    public void enableAntialiasing(boolean z2) {
        this.enableAntialiasing = z2;
    }

    public void enableRenderDuringScale(boolean z2) {
        this.renderDuringScale = z2;
    }

    public void fitToWidth(int i2) {
        if (this.state != State.SHOWN) {
            Log.e(TAG, "Cannot fit, document not rendered yet");
        } else {
            zoomTo(getWidth() / this.f12472b.getPageSize(i2).getWidth());
            jumpTo(i2);
        }
    }

    public Configurator fromAsset(String str) {
        return new Configurator(new AssetSource(str));
    }

    public Configurator fromBytes(byte[] bArr) {
        return new Configurator(new ByteArraySource(bArr));
    }

    public Configurator fromFile(File file) {
        return new Configurator(new FileSource(file));
    }

    public Configurator fromSource(DocumentSource documentSource) {
        return new Configurator(documentSource);
    }

    public Configurator fromStream(InputStream inputStream) {
        return new Configurator(new InputStreamSource(inputStream));
    }

    public Configurator fromUri(Uri uri) {
        return new Configurator(new UriSource(uri));
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public float getCurrentXOffset() {
        return this.currentXOffset;
    }

    public float getCurrentYOffset() {
        return this.currentYOffset;
    }

    public PdfDocument.Meta getDocumentMeta() {
        PdfFile pdfFile = this.f12472b;
        if (pdfFile == null) {
            return null;
        }
        return pdfFile.getMetaData();
    }

    public List<PdfDocument.Link> getLinks(int i2) {
        PdfFile pdfFile = this.f12472b;
        return pdfFile == null ? Collections.emptyList() : pdfFile.getPageLinks(i2);
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public float getMidZoom() {
        return this.midZoom;
    }

    public float getMinZoom() {
        return this.minZoom;
    }

    public int getPageAtPositionOffset(float f2) {
        PdfFile pdfFile = this.f12472b;
        return pdfFile.getPageAtOffset(pdfFile.getDocLen(this.zoom) * f2, this.zoom);
    }

    public int getPageCount() {
        PdfFile pdfFile = this.f12472b;
        if (pdfFile == null) {
            return 0;
        }
        return pdfFile.getPagesCount();
    }

    public FitPolicy getPageFitPolicy() {
        return this.pageFitPolicy;
    }

    public SizeF getPageSize(int i2) {
        PdfFile pdfFile = this.f12472b;
        return pdfFile == null ? new SizeF(0.0f, 0.0f) : pdfFile.getPageSize(i2);
    }

    public float getPositionOffset() {
        float f2;
        float docLen;
        int width;
        if (this.swipeVertical) {
            f2 = -this.currentYOffset;
            docLen = this.f12472b.getDocLen(this.zoom);
            width = getHeight();
        } else {
            f2 = -this.currentXOffset;
            docLen = this.f12472b.getDocLen(this.zoom);
            width = getWidth();
        }
        return MathUtils.limit(f2 / (docLen - width), 0.0f, 1.0f);
    }

    ScrollHandle getScrollHandle() {
        return this.scrollHandle;
    }

    public int getSpacingPx() {
        return this.spacingPx;
    }

    public List<PdfDocument.Bookmark> getTableOfContents() {
        PdfFile pdfFile = this.f12472b;
        return pdfFile == null ? Collections.emptyList() : pdfFile.getBookmarks();
    }

    public float getZoom() {
        return this.zoom;
    }

    public boolean isAnnotationRendering() {
        return this.annotationRendering;
    }

    public boolean isAntialiasing() {
        return this.enableAntialiasing;
    }

    public boolean isAutoSpacingEnabled() {
        return this.autoSpacing;
    }

    public boolean isBestQuality() {
        return this.bestQuality;
    }

    public boolean isFitEachPage() {
        return this.fitEachPage;
    }

    public boolean isPageFlingEnabled() {
        return this.pageFling;
    }

    public boolean isPageSnap() {
        return this.pageSnap;
    }

    public boolean isRecycled() {
        return this.recycled;
    }

    public boolean isSwipeEnabled() {
        return this.enableSwipe;
    }

    public boolean isSwipeVertical() {
        return this.swipeVertical;
    }

    public boolean isZooming() {
        return this.zoom != this.minZoom;
    }

    public void jumpTo(int i2, boolean z2) {
        PdfFile pdfFile = this.f12472b;
        if (pdfFile == null) {
            return;
        }
        int iDetermineValidPageNumberFrom = pdfFile.determineValidPageNumberFrom(i2);
        float f2 = iDetermineValidPageNumberFrom == 0 ? 0.0f : -this.f12472b.getPageOffset(iDetermineValidPageNumberFrom, this.zoom);
        if (this.swipeVertical) {
            if (z2) {
                this.animationManager.startYAnimation(this.currentYOffset, f2);
            } else {
                moveTo(this.currentXOffset, f2);
            }
        } else if (z2) {
            this.animationManager.startXAnimation(this.currentXOffset, f2);
        } else {
            moveTo(f2, this.currentYOffset);
        }
        v(iDetermineValidPageNumberFrom);
    }

    public void loadPages() {
        RenderingHandler renderingHandler;
        if (this.f12472b == null || (renderingHandler = this.f12473c) == null) {
            return;
        }
        renderingHandler.removeMessages(1);
        this.f12471a.makeANewSet();
        this.pagesLoader.a();
        u();
    }

    void m(boolean z2) {
        this.doubletapEnabled = z2;
    }

    public void moveRelativeTo(float f2, float f3) {
        moveTo(this.currentXOffset + f2, this.currentYOffset + f3);
    }

    public void moveTo(float f2, float f3) {
        moveTo(f2, f3, true);
    }

    int n(float f2, float f3) {
        boolean z2 = this.swipeVertical;
        if (z2) {
            f2 = f3;
        }
        float height = z2 ? getHeight() : getWidth();
        if (f2 > -1.0f) {
            return 0;
        }
        if (f2 < (-this.f12472b.getDocLen(this.zoom)) + height + 1.0f) {
            return this.f12472b.getPagesCount() - 1;
        }
        return this.f12472b.getPageAtOffset(-(f2 - (height / 2.0f)), this.zoom);
    }

    SnapEdge o(int i2) {
        if (!this.pageSnap || i2 < 0) {
            return SnapEdge.NONE;
        }
        float f2 = this.swipeVertical ? this.currentYOffset : this.currentXOffset;
        float f3 = -this.f12472b.getPageOffset(i2, this.zoom);
        int height = this.swipeVertical ? getHeight() : getWidth();
        float pageLength = this.f12472b.getPageLength(i2, this.zoom);
        float f4 = height;
        return f4 >= pageLength ? SnapEdge.CENTER : f2 >= f3 ? SnapEdge.START : f3 - pageLength > f2 - f4 ? SnapEdge.END : SnapEdge.NONE;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.renderingHandlerThread == null) {
            this.renderingHandlerThread = new HandlerThread("PDF renderer");
        }
    }

    public void onBitmapRendered(PagePart pagePart) {
        if (this.state == State.LOADED) {
            this.state = State.SHOWN;
            this.f12474d.callOnRender(this.f12472b.getPagesCount());
        }
        if (pagePart.isThumbnail()) {
            this.f12471a.cacheThumbnail(pagePart);
        } else {
            this.f12471a.cachePart(pagePart);
        }
        u();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        recycle();
        HandlerThread handlerThread = this.renderingHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.renderingHandlerThread = null;
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            return;
        }
        if (this.enableAntialiasing) {
            canvas.setDrawFilter(this.antialiasFilter);
        }
        Drawable background = getBackground();
        if (background == null) {
            canvas.drawColor(this.nightMode ? ViewCompat.MEASURED_STATE_MASK : -1);
        } else {
            background.draw(canvas);
        }
        if (!this.recycled && this.state == State.SHOWN) {
            float f2 = this.currentXOffset;
            float f3 = this.currentYOffset;
            canvas.translate(f2, f3);
            Iterator<PagePart> it = this.f12471a.getThumbnails().iterator();
            while (it.hasNext()) {
                drawPart(canvas, it.next());
            }
            for (PagePart pagePart : this.f12471a.getPageParts()) {
                drawPart(canvas, pagePart);
                if (this.f12474d.getOnDrawAll() != null && !this.onDrawPagesNums.contains(Integer.valueOf(pagePart.getPage()))) {
                    this.onDrawPagesNums.add(Integer.valueOf(pagePart.getPage()));
                }
            }
            Iterator<Integer> it2 = this.onDrawPagesNums.iterator();
            while (it2.hasNext()) {
                drawWithListener(canvas, it2.next().intValue(), this.f12474d.getOnDrawAll());
            }
            this.onDrawPagesNums.clear();
            drawWithListener(canvas, this.currentPage, this.f12474d.getOnDraw());
            canvas.translate(-f2, -f3);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i2, int i3, int i4, int i5) {
        float docLen;
        float maxPageHeight;
        this.hasSize = true;
        Configurator configurator = this.waitingDocumentConfigurator;
        if (configurator != null) {
            configurator.load();
        }
        if (isInEditMode() || this.state != State.SHOWN) {
            return;
        }
        float f2 = (-this.currentXOffset) + (i4 * 0.5f);
        float f3 = (-this.currentYOffset) + (i5 * 0.5f);
        if (this.swipeVertical) {
            docLen = f2 / this.f12472b.getMaxPageWidth();
            maxPageHeight = this.f12472b.getDocLen(this.zoom);
        } else {
            docLen = f2 / this.f12472b.getDocLen(this.zoom);
            maxPageHeight = this.f12472b.getMaxPageHeight();
        }
        float f4 = f3 / maxPageHeight;
        this.animationManager.stopAll();
        this.f12472b.recalculatePageSizes(new Size(i2, i3));
        if (this.swipeVertical) {
            this.currentXOffset = ((-docLen) * this.f12472b.getMaxPageWidth()) + (i2 * 0.5f);
            this.currentYOffset = ((-f4) * this.f12472b.getDocLen(this.zoom)) + (i3 * 0.5f);
        } else {
            this.currentXOffset = ((-docLen) * this.f12472b.getDocLen(this.zoom)) + (i2 * 0.5f);
            this.currentYOffset = ((-f4) * this.f12472b.getMaxPageHeight()) + (i3 * 0.5f);
        }
        moveTo(this.currentXOffset, this.currentYOffset);
        s();
    }

    boolean p() {
        return this.doubletapEnabled;
    }

    public boolean pageFillsScreen() {
        float f2 = -this.f12472b.getPageOffset(this.currentPage, this.zoom);
        float pageLength = f2 - this.f12472b.getPageLength(this.currentPage, this.zoom);
        if (isSwipeVertical()) {
            float f3 = this.currentYOffset;
            return f2 > f3 && pageLength < f3 - ((float) getHeight());
        }
        float f4 = this.currentXOffset;
        return f2 > f4 && pageLength < f4 - ((float) getWidth());
    }

    public void performPageSnap() {
        PdfFile pdfFile;
        int iN;
        SnapEdge snapEdgeO;
        if (!this.pageSnap || (pdfFile = this.f12472b) == null || pdfFile.getPagesCount() == 0 || (snapEdgeO = o((iN = n(this.currentXOffset, this.currentYOffset)))) == SnapEdge.NONE) {
            return;
        }
        float fW = w(iN, snapEdgeO);
        if (this.swipeVertical) {
            this.animationManager.startYAnimation(this.currentYOffset, -fW);
        } else {
            this.animationManager.startXAnimation(this.currentXOffset, -fW);
        }
    }

    void q(PdfFile pdfFile) {
        this.state = State.LOADED;
        this.f12472b = pdfFile;
        HandlerThread handlerThread = this.renderingHandlerThread;
        if (handlerThread == null) {
            return;
        }
        if (!handlerThread.isAlive()) {
            this.renderingHandlerThread.start();
        }
        RenderingHandler renderingHandler = new RenderingHandler(this.renderingHandlerThread.getLooper(), this);
        this.f12473c = renderingHandler;
        renderingHandler.c();
        ScrollHandle scrollHandle = this.scrollHandle;
        if (scrollHandle != null) {
            scrollHandle.setupLayout(this);
            this.isScrollHandleInit = true;
        }
        this.dragPinchManager.c();
        this.f12474d.callOnLoadComplete(pdfFile.getPagesCount());
        jumpTo(this.defaultPage, false);
    }

    void r(Throwable th) {
        this.state = State.ERROR;
        OnErrorListener onError = this.f12474d.getOnError();
        recycle();
        invalidate();
        if (onError != null) {
            onError.onError(th);
        } else {
            Log.e("PDFView", "load pdf error", th);
        }
    }

    public void recycle() {
        this.waitingDocumentConfigurator = null;
        this.animationManager.stopAll();
        this.dragPinchManager.a();
        RenderingHandler renderingHandler = this.f12473c;
        if (renderingHandler != null) {
            renderingHandler.d();
            this.f12473c.removeMessages(1);
        }
        DecodingAsyncTask decodingAsyncTask = this.decodingAsyncTask;
        if (decodingAsyncTask != null) {
            decodingAsyncTask.cancel(true);
        }
        this.f12471a.recycle();
        ScrollHandle scrollHandle = this.scrollHandle;
        if (scrollHandle != null && this.isScrollHandleInit) {
            scrollHandle.destroyLayout();
        }
        PdfFile pdfFile = this.f12472b;
        if (pdfFile != null) {
            pdfFile.dispose();
            this.f12472b = null;
        }
        this.f12473c = null;
        this.scrollHandle = null;
        this.isScrollHandleInit = false;
        this.currentYOffset = 0.0f;
        this.currentXOffset = 0.0f;
        this.zoom = 1.0f;
        this.recycled = true;
        this.f12474d = new Callbacks();
        this.state = State.DEFAULT;
    }

    public void resetZoom() {
        zoomTo(this.minZoom);
    }

    public void resetZoomWithAnimation() {
        zoomWithAnimation(this.minZoom);
    }

    void s() {
        float f2;
        int width;
        if (this.f12472b.getPagesCount() == 0) {
            return;
        }
        if (this.swipeVertical) {
            f2 = this.currentYOffset;
            width = getHeight();
        } else {
            f2 = this.currentXOffset;
            width = getWidth();
        }
        int pageAtOffset = this.f12472b.getPageAtOffset(-(f2 - (width / 2.0f)), this.zoom);
        if (pageAtOffset < 0 || pageAtOffset > this.f12472b.getPagesCount() - 1 || pageAtOffset == getCurrentPage()) {
            loadPages();
        } else {
            v(pageAtOffset);
        }
    }

    public void setMaxZoom(float f2) {
        this.maxZoom = f2;
    }

    public void setMidZoom(float f2) {
        this.midZoom = f2;
    }

    public void setMinZoom(float f2) {
        this.minZoom = f2;
    }

    public void setNightMode(boolean z2) {
        this.nightMode = z2;
        if (!z2) {
            this.paint.setColorFilter(null);
        } else {
            this.paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f})));
        }
    }

    public void setPageFling(boolean z2) {
        this.pageFling = z2;
    }

    public void setPageSnap(boolean z2) {
        this.pageSnap = z2;
    }

    public void setPositionOffset(float f2, boolean z2) {
        if (this.swipeVertical) {
            moveTo(this.currentXOffset, ((-this.f12472b.getDocLen(this.zoom)) + getHeight()) * f2, z2);
        } else {
            moveTo(((-this.f12472b.getDocLen(this.zoom)) + getWidth()) * f2, this.currentYOffset, z2);
        }
        s();
    }

    public void setSwipeEnabled(boolean z2) {
        this.enableSwipe = z2;
    }

    public void stopFling() {
        this.animationManager.stopFling();
    }

    void t(PageRenderingException pageRenderingException) {
        if (this.f12474d.callOnPageError(pageRenderingException.getPage(), pageRenderingException.getCause())) {
            return;
        }
        Log.e(TAG, "Cannot open page " + pageRenderingException.getPage(), pageRenderingException.getCause());
    }

    public float toCurrentScale(float f2) {
        return f2 * this.zoom;
    }

    public float toRealScale(float f2) {
        return f2 / this.zoom;
    }

    void u() {
        invalidate();
    }

    public void useBestQuality(boolean z2) {
        this.bestQuality = z2;
    }

    void v(int i2) {
        if (this.recycled) {
            return;
        }
        this.currentPage = this.f12472b.determineValidPageNumberFrom(i2);
        loadPages();
        if (this.scrollHandle != null && !documentFitsView()) {
            this.scrollHandle.setPageNum(this.currentPage + 1);
        }
        this.f12474d.callOnPageChange(this.currentPage, this.f12472b.getPagesCount());
    }

    float w(int i2, SnapEdge snapEdge) {
        float f2;
        float pageOffset = this.f12472b.getPageOffset(i2, this.zoom);
        float height = this.swipeVertical ? getHeight() : getWidth();
        float pageLength = this.f12472b.getPageLength(i2, this.zoom);
        if (snapEdge == SnapEdge.CENTER) {
            f2 = pageOffset - (height / 2.0f);
            pageLength /= 2.0f;
        } else {
            if (snapEdge != SnapEdge.END) {
                return pageOffset;
            }
            f2 = pageOffset - height;
        }
        return f2 + pageLength;
    }

    public void zoomCenteredRelativeTo(float f2, PointF pointF) {
        zoomCenteredTo(this.zoom * f2, pointF);
    }

    public void zoomCenteredTo(float f2, PointF pointF) {
        float f3 = f2 / this.zoom;
        zoomTo(f2);
        float f4 = this.currentXOffset * f3;
        float f5 = this.currentYOffset * f3;
        float f6 = pointF.x;
        float f7 = pointF.y;
        moveTo(f4 + (f6 - (f6 * f3)), f5 + (f7 - (f3 * f7)));
    }

    public void zoomTo(float f2) {
        this.zoom = f2;
    }

    public void zoomWithAnimation(float f2, float f3, float f4) {
        this.animationManager.startZoomAnimation(f2, f3, this.zoom, f4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void load(DocumentSource documentSource, String str, int[] iArr) {
        if (!this.recycled) {
            throw new IllegalStateException("Don't call load on a PDF View without recycling it first.");
        }
        this.recycled = false;
        DecodingAsyncTask decodingAsyncTask = new DecodingAsyncTask(documentSource, str, iArr, this, this.pdfiumCore);
        this.decodingAsyncTask = decodingAsyncTask;
        decodingAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void moveTo(float r6, float r7, boolean r8) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.barteksc.pdfviewer.PDFView.moveTo(float, float, boolean):void");
    }

    public void zoomWithAnimation(float f2) {
        this.animationManager.startZoomAnimation(getWidth() / 2, getHeight() / 2, this.zoom, f2);
    }

    public void setPositionOffset(float f2) {
        setPositionOffset(f2, true);
    }

    public void jumpTo(int i2) {
        jumpTo(i2, false);
    }
}
