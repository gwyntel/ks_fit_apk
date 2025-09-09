package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class WheelView extends View {
    private static final int CLICK_DISTANCE = 2;
    private static final int GO_ON_MOVE_END = 10011;
    private static final int GO_ON_MOVE_INTERRUPTED = 10012;
    private static final int GO_ON_MOVE_REFRESH = 10010;
    private static final int GO_ON_REFRESH_INTERVAL_MILLIS = 10;
    private static final int MOVE_NUMBER = 1;
    private static final int REFRESH_VIEW = 1;
    private static final int SHOWTIME = 200;
    private static final int SLOW_MOVE_SPEED = 1;
    private static final String TAG = "WheelView";
    private boolean _isCyclic;
    private Handler callbackHandler;
    private int clickDistance;
    private int clickTimeout;
    private float controlHeight;
    private float controlWidth;
    private ArrayList<String> dataList;
    private int defaultIndex;
    private float density;
    private long downTime;
    private int downY;
    private int goOnDistance;
    int goOnLimit;
    private int goOnMove;
    Interpolator goonInterpolator;
    private boolean isClearing;
    private boolean isCyclic;
    private boolean isEnable;
    private boolean isGoOnMove;
    private boolean isScrolling;
    private ArrayList<ItemObject> itemList;
    private int itemNumber;
    private float lastMeasuredHeight;
    private int lastY;
    private int lineColor;
    private float lineHeight;
    private Paint linePaint;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private String mSuffix;
    private float mSuffixPadding;
    private TextPaint mSuffixPaint;
    private VelocityTracker mVelocityTracker;
    private int maxTextWidth;
    private String maxWidthText;
    private int moveDistance;
    private Handler moveHandler;
    private HandlerThread moveHandlerThread;
    private boolean noEmpty;
    private int normalColor;
    private float normalFont;
    private OnSelectListener onSelectListener;
    private boolean scale;
    private int selectedColor;
    private float selectedFont;
    private int showTime;
    private int slowMoveSpeed;
    private ItemObject[] toShowItems;
    private float unitHeight;

    private class GoOnHandler extends Handler {
        GoOnHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2 = WheelView.this.goOnDistance;
            switch (message.what) {
                case 10010:
                    WheelView.access$108(WheelView.this);
                    WheelView wheelView = WheelView.this;
                    wheelView.goOnDistance = (int) (wheelView.goonInterpolator.getInterpolation(wheelView.showTime / 200.0f) * WheelView.this.goOnLimit);
                    WheelView wheelView2 = WheelView.this;
                    wheelView2.actionThreadMove(wheelView2.goOnMove > 0 ? WheelView.this.goOnDistance - i2 : (WheelView.this.goOnDistance - i2) * (-1));
                    if (WheelView.this.showTime < 200 && WheelView.this.isGoOnMove && (WheelView.this.showTime < 40 || Math.abs(i2 - WheelView.this.goOnDistance) >= WheelView.this.slowMoveSpeed)) {
                        WheelView.this.moveHandler.sendEmptyMessageDelayed(10010, 10L);
                        break;
                    } else {
                        WheelView.this.isGoOnMove = false;
                        WheelView.this.moveHandler.sendEmptyMessage(10011);
                        break;
                    }
                    break;
                case 10011:
                    WheelView wheelView3 = WheelView.this;
                    wheelView3.slowMove(wheelView3.goOnMove > 0 ? WheelView.this.slowMoveSpeed : WheelView.this.slowMoveSpeed * (-1));
                    WheelView.this.isScrolling = false;
                    WheelView.this.isGoOnMove = false;
                    WheelView.this.goOnDistance = 0;
                    WheelView.this.goOnLimit = 0;
                    break;
                case 10012:
                    WheelView.this.moveDistance += WheelView.this.goOnMove > 0 ? WheelView.this.goOnDistance - i2 : (WheelView.this.goOnDistance - i2) * (-1);
                    WheelView.this.goOnDistance = 0;
                    WheelView.this.isScrolling = false;
                    WheelView.this.isGoOnMove = false;
                    WheelView.this.findItemsToShow();
                    WheelView.this.postInvalidate();
                    break;
            }
        }
    }

    private class ItemObject {
        int id;
        private String itemText;
        int move;
        private boolean shouldRefreshTextPaint;
        private TextPaint textPaint;
        private Rect textRect;

        /* renamed from: x, reason: collision with root package name */
        int f12053x;

        /* renamed from: y, reason: collision with root package name */
        int f12054y;

        private ItemObject() {
            this.id = 0;
            this.itemText = "";
            this.f12053x = 0;
            this.f12054y = 0;
            this.move = 0;
            this.shouldRefreshTextPaint = true;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x004c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public synchronized boolean couldSelected() {
            /*
                r4 = this;
                monitor-enter(r4)
                int r0 = r4.f12054y     // Catch: java.lang.Throwable -> L4a
                int r1 = r4.move     // Catch: java.lang.Throwable -> L4a
                int r0 = r0 + r1
                float r0 = (float) r0     // Catch: java.lang.Throwable -> L4a
                com.aliyun.iot.link.ui.component.WheelView r1 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                int r1 = com.aliyun.iot.link.ui.component.WheelView.access$1400(r1)     // Catch: java.lang.Throwable -> L4a
                int r1 = r1 / 2
                float r1 = (float) r1     // Catch: java.lang.Throwable -> L4a
                com.aliyun.iot.link.ui.component.WheelView r2 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                float r2 = com.aliyun.iot.link.ui.component.WheelView.access$2100(r2)     // Catch: java.lang.Throwable -> L4a
                float r1 = r1 * r2
                com.aliyun.iot.link.ui.component.WheelView r2 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                float r2 = com.aliyun.iot.link.ui.component.WheelView.access$2100(r2)     // Catch: java.lang.Throwable -> L4a
                r3 = 1073741824(0x40000000, float:2.0)
                float r2 = r2 / r3
                float r1 = r1 - r2
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 <= 0) goto L4c
                int r0 = r4.f12054y     // Catch: java.lang.Throwable -> L4a
                int r1 = r4.move     // Catch: java.lang.Throwable -> L4a
                int r0 = r0 + r1
                float r0 = (float) r0     // Catch: java.lang.Throwable -> L4a
                com.aliyun.iot.link.ui.component.WheelView r1 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                int r1 = com.aliyun.iot.link.ui.component.WheelView.access$1400(r1)     // Catch: java.lang.Throwable -> L4a
                int r1 = r1 / 2
                float r1 = (float) r1     // Catch: java.lang.Throwable -> L4a
                com.aliyun.iot.link.ui.component.WheelView r2 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                float r2 = com.aliyun.iot.link.ui.component.WheelView.access$2100(r2)     // Catch: java.lang.Throwable -> L4a
                float r1 = r1 * r2
                com.aliyun.iot.link.ui.component.WheelView r2 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L4a
                float r2 = com.aliyun.iot.link.ui.component.WheelView.access$2100(r2)     // Catch: java.lang.Throwable -> L4a
                float r2 = r2 / r3
                float r1 = r1 + r2
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 < 0) goto L48
                goto L4c
            L48:
                r0 = 1
                goto L4d
            L4a:
                r0 = move-exception
                goto L4f
            L4c:
                r0 = 0
            L4d:
                monitor-exit(r4)
                return r0
            L4f:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L4a
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.link.ui.component.WheelView.ItemObject.couldSelected():boolean");
        }

        public void drawSelf(Canvas canvas, int i2) {
            if (isInView()) {
                if (this.textPaint == null) {
                    TextPaint textPaint = new TextPaint();
                    this.textPaint = textPaint;
                    textPaint.setAntiAlias(true);
                }
                if (this.textRect == null) {
                    this.textRect = new Rect();
                }
                if (couldSelected()) {
                    this.textPaint.setColor(WheelView.this.selectedColor);
                    float fMoveToSelected = moveToSelected();
                    if (fMoveToSelected <= 0.0f) {
                        fMoveToSelected *= -1.0f;
                    }
                    if (WheelView.this.scale) {
                        this.textPaint.setTextSize(WheelView.this.normalFont + ((WheelView.this.selectedFont - WheelView.this.normalFont) * (1.0f - (fMoveToSelected / WheelView.this.unitHeight))));
                    } else {
                        this.textPaint.setTextSize(WheelView.this.normalFont);
                    }
                } else {
                    this.textPaint.setColor(WheelView.this.normalColor);
                    this.textPaint.setTextSize(WheelView.this.normalFont);
                }
                if (WheelView.this.scale && WheelView.this.unitHeight < Math.max(WheelView.this.selectedFont, WheelView.this.normalFont)) {
                    this.textPaint.setTextSize(WheelView.this.unitHeight - (WheelView.this.lineHeight * 2.0f));
                }
                if (this.shouldRefreshTextPaint) {
                    String str = (String) TextUtils.ellipsize(this.itemText, this.textPaint, i2, TextUtils.TruncateAt.END);
                    this.itemText = str;
                    this.textPaint.getTextBounds(str, 0, str.length(), this.textRect);
                    if (WheelView.this.selectedFont == WheelView.this.normalFont) {
                        this.shouldRefreshTextPaint = false;
                    }
                }
                if (isInView()) {
                    canvas.drawText(this.itemText, (this.f12053x + (WheelView.this.controlWidth / 2.0f)) - (this.textRect.width() / 2.0f), this.f12054y + this.move + (WheelView.this.unitHeight / 2.0f) + (this.textRect.height() / 2.0f), this.textPaint);
                }
            }
        }

        public String getItemText() {
            return this.itemText;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0028  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public synchronized boolean isInView() {
            /*
                r2 = this;
                monitor-enter(r2)
                int r0 = r2.f12054y     // Catch: java.lang.Throwable -> L26
                int r1 = r2.move     // Catch: java.lang.Throwable -> L26
                int r0 = r0 + r1
                float r0 = (float) r0     // Catch: java.lang.Throwable -> L26
                com.aliyun.iot.link.ui.component.WheelView r1 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L26
                float r1 = com.aliyun.iot.link.ui.component.WheelView.access$2500(r1)     // Catch: java.lang.Throwable -> L26
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 > 0) goto L28
                int r0 = r2.f12054y     // Catch: java.lang.Throwable -> L26
                float r0 = (float) r0     // Catch: java.lang.Throwable -> L26
                int r1 = r2.move     // Catch: java.lang.Throwable -> L26
                float r1 = (float) r1     // Catch: java.lang.Throwable -> L26
                float r0 = r0 + r1
                com.aliyun.iot.link.ui.component.WheelView r1 = com.aliyun.iot.link.ui.component.WheelView.this     // Catch: java.lang.Throwable -> L26
                float r1 = com.aliyun.iot.link.ui.component.WheelView.access$2100(r1)     // Catch: java.lang.Throwable -> L26
                float r0 = r0 + r1
                r1 = 0
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 < 0) goto L28
                r0 = 1
                goto L29
            L26:
                r0 = move-exception
                goto L2b
            L28:
                r0 = 0
            L29:
                monitor-exit(r2)
                return r0
            L2b:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L26
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.link.ui.component.WheelView.ItemObject.isInView():boolean");
        }

        public synchronized void move(int i2) {
            this.move = i2;
        }

        public synchronized float moveToSelected() {
            return ((WheelView.this.controlHeight / 2.0f) - (WheelView.this.unitHeight / 2.0f)) - (this.f12054y + this.move);
        }

        public synchronized boolean selected() {
            boolean z2 = false;
            if (this.textRect == null) {
                return false;
            }
            if (this.f12054y + this.move >= (((WheelView.this.itemNumber / 2) * WheelView.this.unitHeight) - (WheelView.this.unitHeight / 2.0f)) + (this.textRect.height() / 2.0f)) {
                if (this.f12054y + this.move <= (((WheelView.this.itemNumber / 2) * WheelView.this.unitHeight) + (WheelView.this.unitHeight / 2.0f)) - (this.textRect.height() / 2.0f)) {
                    z2 = true;
                }
            }
            return z2;
        }

        public void setItemText(String str) {
            this.shouldRefreshTextPaint = true;
            this.itemText = str;
        }
    }

    public interface OnSelectListener {
        void endSelect(int i2, String str);

        void selecting(int i2, String str);
    }

    public WheelView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isScrolling = false;
        this.itemList = new ArrayList<>();
        this.dataList = new ArrayList<>();
        this.downTime = 0L;
        this.density = 1.0f;
        this.slowMoveSpeed = 1;
        this.clickDistance = 2;
        this.clickTimeout = 100;
        this.lineColor = -1644826;
        this.lineHeight = 2.0f;
        this.normalFont = 14.0f;
        this.selectedFont = 22.0f;
        this.unitHeight = 50.0f;
        this.itemNumber = 5;
        this.scale = true;
        this.normalColor = -13421773;
        this.selectedColor = -13421773;
        this.isEnable = true;
        this.noEmpty = true;
        this.isCyclic = true;
        this._isCyclic = true;
        this.isClearing = false;
        this.goonInterpolator = new DecelerateInterpolator(2.0f);
        this.showTime = 0;
        this.isGoOnMove = false;
        this.mSuffix = "";
        init(context, attributeSet);
        initData();
    }

    private void _setIsCyclic(boolean z2) {
        if (this.dataList.size() < this.itemNumber + 2) {
            this._isCyclic = false;
        } else {
            this._isCyclic = z2;
        }
    }

    static /* synthetic */ int access$108(WheelView wheelView) {
        int i2 = wheelView.showTime;
        wheelView.showTime = i2 + 1;
        return i2;
    }

    private void actionMove(int i2) {
        this.moveDistance -= i2;
        findItemsToShow();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void actionThreadMove(int i2) {
        this.moveDistance -= i2;
        findItemsToShow();
        postInvalidate();
    }

    private void defaultMove(int i2) {
        this.moveDistance -= i2;
        findItemsToShow();
        postInvalidate();
    }

    private void drawLine(Canvas canvas) {
        if (this.linePaint == null) {
            Paint paint = new Paint();
            this.linePaint = paint;
            paint.setColor(this.lineColor);
            this.linePaint.setAntiAlias(true);
            this.linePaint.setStrokeWidth(this.lineHeight);
        }
        float f2 = this.controlHeight;
        float f3 = this.unitHeight;
        float f4 = this.lineHeight;
        canvas.drawLine(0.0f, ((f2 / 2.0f) - (f3 / 2.0f)) + f4, this.controlWidth, ((f2 / 2.0f) - (f3 / 2.0f)) + f4, this.linePaint);
        float f5 = this.controlHeight;
        float f6 = this.unitHeight;
        float f7 = this.lineHeight;
        canvas.drawLine(0.0f, ((f5 / 2.0f) + (f6 / 2.0f)) - f7, this.controlWidth, ((f5 / 2.0f) + (f6 / 2.0f)) - f7, this.linePaint);
    }

    private synchronized void drawList(Canvas canvas) {
        if (this.isClearing) {
            return;
        }
        synchronized (this.toShowItems) {
            try {
                for (ItemObject itemObject : this.toShowItems) {
                    if (itemObject != null) {
                        itemObject.drawSelf(canvas, getMeasuredWidth());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void drawMask(Canvas canvas) {
        float f2 = this.controlHeight / 2.0f;
        Shader.TileMode tileMode = Shader.TileMode.MIRROR;
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, f2, -1, ViewCompat.MEASURED_SIZE_MASK, tileMode);
        Paint paint = new Paint();
        paint.setShader(linearGradient);
        canvas.drawRect(0.0f, 0.0f, this.controlWidth, (this.itemNumber / 2.0f) * this.unitHeight, paint);
        float f3 = this.controlHeight;
        LinearGradient linearGradient2 = new LinearGradient(0.0f, f3 / 2.0f, 0.0f, f3, ViewCompat.MEASURED_SIZE_MASK, -1, tileMode);
        Paint paint2 = new Paint();
        paint2.setShader(linearGradient2);
        float f4 = this.controlHeight;
        canvas.drawRect(0.0f, f4 - ((this.itemNumber / 2) * this.unitHeight), this.controlWidth, f4, paint2);
    }

    private void drawSuffix(Canvas canvas) {
        this.mSuffixPaint.setColor(this.selectedColor);
        this.mSuffixPaint.setTextSize(this.selectedFont);
        Rect rect = new Rect();
        TextPaint textPaint = this.mSuffixPaint;
        String str = this.mSuffix;
        textPaint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(this.mSuffix, (this.controlWidth / 2.0f) + this.maxTextWidth + this.mSuffixPadding, (getHeight() / 2.0f) + (rect.height() / 2.0f), this.mSuffixPaint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findItemsToShow() {
        int i2 = 0;
        if (this._isCyclic) {
            if (this.moveDistance > this.unitHeight * this.itemList.size()) {
                this.moveDistance %= ((int) this.unitHeight) * this.itemList.size();
            } else {
                int i3 = this.moveDistance;
                if (i3 < 0) {
                    this.moveDistance = (i3 % (((int) this.unitHeight) * this.itemList.size())) + (((int) this.unitHeight) * this.itemList.size());
                }
            }
            int i4 = this.moveDistance;
            if (this.itemList.size() <= 0) {
                return;
            }
            float f2 = this.itemList.get(0).f12054y + i4;
            int iAbs = (int) Math.abs(f2 / this.unitHeight);
            int i5 = (int) (f2 - (this.unitHeight * iAbs));
            synchronized (this.toShowItems) {
                while (i2 < this.toShowItems.length) {
                    try {
                        int size = iAbs + i2;
                        if (size < 0) {
                            size += this.itemList.size();
                        } else if (size >= this.itemList.size()) {
                            size -= this.itemList.size();
                        }
                        if (size < this.itemList.size()) {
                            this.toShowItems[i2] = this.itemList.get(size);
                            this.toShowItems[i2].move(((int) (this.unitHeight * ((i2 - size) % this.itemList.size()))) - i5);
                        }
                        i2++;
                    } finally {
                    }
                }
            }
        } else {
            float f3 = this.moveDistance;
            float size2 = this.unitHeight * this.itemList.size();
            int i6 = this.itemNumber;
            float f4 = this.unitHeight;
            if (f3 > (size2 - ((i6 / 2) * f4)) - f4) {
                float f5 = this.itemNumber / 2;
                float f6 = this.unitHeight;
                this.moveDistance = (int) (((f4 * this.itemList.size()) - (f5 * f6)) - f6);
                this.moveHandler.removeMessages(10010);
                this.moveHandler.sendEmptyMessage(10012);
            } else if (this.moveDistance < ((-i6) / 2) * f4) {
                this.moveDistance = (int) (((-i6) / 2) * f4);
                this.moveHandler.removeMessages(10010);
                this.moveHandler.sendEmptyMessage(10012);
            }
            int i7 = this.moveDistance;
            if (this.itemList.size() <= 0) {
                return;
            }
            float f7 = this.itemList.get(0).f12054y + i7;
            float f8 = this.unitHeight;
            int i8 = (int) (f7 / f8);
            int i9 = (int) (f7 - (f8 * i8));
            synchronized (this.toShowItems) {
                while (i2 < this.toShowItems.length) {
                    try {
                        int i10 = i8 + i2;
                        if (i10 < 0 || i10 >= this.itemList.size()) {
                            i10 = -1;
                        }
                        if (i10 == -1) {
                            this.toShowItems[i2] = null;
                        } else {
                            this.toShowItems[i2] = this.itemList.get(i10);
                            this.toShowItems[i2].move(((int) (this.unitHeight * (i2 - i10))) - i9);
                        }
                        i2++;
                    } finally {
                    }
                }
            }
        }
        if (this.onSelectListener == null || this.toShowItems[this.itemNumber / 2] == null) {
            return;
        }
        this.callbackHandler.post(new Runnable() { // from class: com.aliyun.iot.link.ui.component.WheelView.2
            @Override // java.lang.Runnable
            public void run() {
                WheelView.this.onSelectListener.selecting(WheelView.this.toShowItems[WheelView.this.itemNumber / 2].id, WheelView.this.toShowItems[WheelView.this.itemNumber / 2].getItemText());
            }
        });
    }

    private synchronized void goonMove(int i2, long j2) {
        try {
            this.showTime = 0;
            int iAbs = Math.abs(i2 / 10);
            if (this.goOnMove * j2 > 0) {
                this.goOnLimit += iAbs;
            } else {
                this.goOnLimit = iAbs;
            }
            this.goOnMove = (int) j2;
            this.isGoOnMove = true;
            this.moveHandler.sendEmptyMessage(10010);
        } catch (Throwable th) {
            throw th;
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.WheelView);
        this.unitHeight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.WheelView_unitHeight, this.unitHeight);
        this.itemNumber = typedArrayObtainStyledAttributes.getInt(R.styleable.WheelView_itemNumber, this.itemNumber);
        this.scale = typedArrayObtainStyledAttributes.getBoolean(R.styleable.WheelView_scale, true);
        this.normalFont = typedArrayObtainStyledAttributes.getDimension(R.styleable.WheelView_normalTextSize, this.normalFont);
        this.selectedFont = typedArrayObtainStyledAttributes.getDimension(R.styleable.WheelView_selectedTextSize, this.selectedFont);
        this.normalColor = typedArrayObtainStyledAttributes.getColor(R.styleable.WheelView_normalTextColor, this.normalColor);
        this.selectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.WheelView_selectedTextColor, this.selectedColor);
        this.lineColor = typedArrayObtainStyledAttributes.getColor(R.styleable.WheelView_lineColor, this.lineColor);
        this.lineHeight = typedArrayObtainStyledAttributes.getDimension(R.styleable.WheelView_lineHeight, this.lineHeight);
        this.noEmpty = typedArrayObtainStyledAttributes.getBoolean(R.styleable.WheelView_noEmpty, true);
        this.isEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.WheelView_isEnable, true);
        this.isCyclic = typedArrayObtainStyledAttributes.getBoolean(R.styleable.WheelView_isCyclic, true);
        this.mSuffix = typedArrayObtainStyledAttributes.getString(R.styleable.WheelView_suffix);
        this.mSuffixPadding = typedArrayObtainStyledAttributes.getDimension(R.styleable.WheelView_suffixPadding, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        if (TextUtils.isEmpty(this.mSuffix)) {
            this.mSuffix = "";
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        this.density = f2;
        this.slowMoveSpeed = (int) (1.0f * f2);
        this.clickDistance = (int) (f2 * 2.0f);
        int i2 = this.itemNumber;
        this.controlHeight = i2 * this.unitHeight;
        this.toShowItems = new ItemObject[i2 + 2];
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.clickTimeout = ViewConfiguration.getTapTimeout();
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.callbackHandler = new Handler(Looper.getMainLooper());
        TextPaint textPaint = new TextPaint(1);
        this.mSuffixPaint = textPaint;
        textPaint.setTextSize(this.selectedFont);
    }

    private void initData() {
        this.isClearing = true;
        this.itemList.clear();
        this.maxWidthText = "";
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(this.selectedFont);
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            ItemObject itemObject = new ItemObject();
            itemObject.id = i2;
            itemObject.setItemText(this.dataList.get(i2));
            itemObject.f12053x = 0;
            itemObject.f12054y = (int) (i2 * this.unitHeight);
            this.itemList.add(itemObject);
            Rect rect = new Rect();
            textPaint.getTextBounds(this.dataList.get(i2), 0, this.dataList.get(i2).length(), rect);
            if (rect.width() > this.maxTextWidth) {
                this.maxWidthText = this.dataList.get(i2);
                this.maxTextWidth = rect.width();
            }
        }
        this.isClearing = false;
        _setIsCyclic(this.isCyclic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noEmpty(int i2) {
        if (this.noEmpty) {
            synchronized (this.toShowItems) {
                try {
                    findItemsToShow();
                    for (ItemObject itemObject : this.toShowItems) {
                        if (itemObject != null && itemObject.selected()) {
                            int iMoveToSelected = (int) itemObject.moveToSelected();
                            onEndSelecting(itemObject);
                            defaultMove(iMoveToSelected);
                            return;
                        }
                    }
                    if (i2 > 0) {
                        for (ItemObject itemObject2 : this.toShowItems) {
                            if (itemObject2 != null && itemObject2.couldSelected()) {
                                int iMoveToSelected2 = (int) itemObject2.moveToSelected();
                                onEndSelecting(itemObject2);
                                defaultMove(iMoveToSelected2);
                                return;
                            }
                        }
                    } else {
                        for (int length = this.toShowItems.length - 1; length >= 0; length--) {
                            ItemObject itemObject3 = this.toShowItems[length];
                            if (itemObject3 != null && itemObject3.couldSelected()) {
                                int iMoveToSelected3 = (int) this.toShowItems[length].moveToSelected();
                                onEndSelecting(this.toShowItems[length]);
                                defaultMove(iMoveToSelected3);
                                return;
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void onEndSelecting(final ItemObject itemObject) {
        if (this.onSelectListener != null) {
            this.callbackHandler.post(new Runnable() { // from class: com.aliyun.iot.link.ui.component.WheelView.1
                @Override // java.lang.Runnable
                public void run() {
                    OnSelectListener onSelectListener = WheelView.this.onSelectListener;
                    ItemObject itemObject2 = itemObject;
                    onSelectListener.endSelect(itemObject2.id, itemObject2.getItemText());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void slowMove(final int i2) {
        Handler handler = this.moveHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.aliyun.iot.link.ui.component.WheelView.3
            @Override // java.lang.Runnable
            public void run() throws InterruptedException {
                int iMoveToSelected;
                int iMoveToSelected2;
                WheelView.this.findItemsToShow();
                int selected = WheelView.this.getSelected();
                if (selected != -1) {
                    iMoveToSelected2 = (int) ((ItemObject) WheelView.this.itemList.get(selected)).moveToSelected();
                } else {
                    synchronized (WheelView.this.toShowItems) {
                        try {
                            iMoveToSelected = 0;
                            if (i2 <= 0) {
                                int length = WheelView.this.toShowItems.length - 1;
                                while (true) {
                                    if (length >= 0) {
                                        if (WheelView.this.toShowItems[length] != null && WheelView.this.toShowItems[length].couldSelected()) {
                                            iMoveToSelected = (int) WheelView.this.toShowItems[length].moveToSelected();
                                            break;
                                        }
                                        length--;
                                    } else {
                                        break;
                                    }
                                }
                            } else {
                                ItemObject[] itemObjectArr = WheelView.this.toShowItems;
                                int length2 = itemObjectArr.length;
                                int i3 = 0;
                                while (true) {
                                    if (i3 < length2) {
                                        ItemObject itemObject = itemObjectArr[i3];
                                        if (itemObject != null && itemObject.couldSelected()) {
                                            iMoveToSelected = (int) itemObject.moveToSelected();
                                            break;
                                        }
                                        i3++;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    iMoveToSelected2 = iMoveToSelected;
                }
                int i4 = iMoveToSelected2 > 0 ? iMoveToSelected2 : iMoveToSelected2 * (-1);
                int i5 = iMoveToSelected2 <= 0 ? -1 : 1;
                int i6 = WheelView.this.slowMoveSpeed;
                while (true) {
                    if (i4 == 0) {
                        break;
                    }
                    i4 -= i6;
                    if (i4 < 0) {
                        WheelView.this.moveDistance -= i4 * i5;
                        WheelView.this.findItemsToShow();
                        WheelView.this.postInvalidate();
                        try {
                            Thread.sleep(10L);
                            break;
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        WheelView.this.moveDistance -= i6 * i5;
                        WheelView.this.findItemsToShow();
                        WheelView.this.postInvalidate();
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                    }
                }
                WheelView.this.noEmpty(i2);
            }
        });
    }

    public int getItemNumber() {
        return this.itemNumber;
    }

    public String getItemText(int i2) {
        ArrayList<ItemObject> arrayList = this.itemList;
        return arrayList == null ? "" : arrayList.get(i2).getItemText();
    }

    public int getListSize() {
        ArrayList<ItemObject> arrayList = this.itemList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public int getSelected() {
        synchronized (this.toShowItems) {
            try {
                for (ItemObject itemObject : this.toShowItems) {
                    if (itemObject != null && itemObject.selected()) {
                        return itemObject.id;
                    }
                }
                return -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public String getSelectedText() {
        synchronized (this.toShowItems) {
            try {
                for (ItemObject itemObject : this.toShowItems) {
                    if (itemObject != null && itemObject.selected()) {
                        return itemObject.getItemText();
                    }
                }
                return "";
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public boolean isEnable() {
        return this.isEnable;
    }

    public boolean isScrolling() {
        return this.isScrolling;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.moveHandler = new GoOnHandler(Looper.getMainLooper());
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.controlWidth = getWidth();
        drawLine(canvas);
        drawList(canvas);
        drawSuffix(canvas);
        drawMask(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i3);
        int selected = getSelected();
        if (mode == Integer.MIN_VALUE) {
            int size = View.MeasureSpec.getSize(i3);
            float f2 = size;
            if (f2 < this.controlHeight && size != 0) {
                this.controlHeight = f2;
                float f3 = (int) (f2 / this.itemNumber);
                this.unitHeight = f3;
                this.unitHeight = Math.max(1.0f, f3);
            }
        } else if (mode == 1073741824) {
            float size2 = View.MeasureSpec.getSize(i3);
            this.controlHeight = size2;
            float f4 = (int) (size2 / this.itemNumber);
            this.unitHeight = f4;
            this.unitHeight = Math.max(1.0f, f4);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i2), (int) this.controlHeight);
        if (Math.abs(this.lastMeasuredHeight - this.controlHeight) > 0.1d) {
            initData();
            if (selected != -1) {
                setDefault(selected);
            } else {
                setDefault(this.defaultIndex);
            }
            this.lastMeasuredHeight = this.controlHeight;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isEnable) {
            return true;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int y2 = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.isScrolling) {
                this.isGoOnMove = false;
                Handler handler = this.moveHandler;
                if (handler != null) {
                    handler.removeMessages(10010);
                    this.moveHandler.sendEmptyMessage(10012);
                }
            }
            this.isScrolling = true;
            this.downY = (int) motionEvent.getY();
            this.lastY = (int) motionEvent.getY();
            this.downTime = System.currentTimeMillis();
        } else if (action == 1) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.downTime;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            int yVelocity = (int) velocityTracker.getYVelocity();
            if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                goonMove(yVelocity, y2 - this.downY);
            } else {
                if (Math.abs(y2 - this.downY) > this.clickDistance || jCurrentTimeMillis > this.clickTimeout) {
                    slowMove(y2 - this.downY);
                } else {
                    int i2 = this.downY;
                    float f2 = i2;
                    float f3 = this.unitHeight;
                    int i3 = this.itemNumber;
                    if (f2 >= ((i3 / 2) * f3) + ((f3 * 1.0f) / 3.0f) || i2 <= 0) {
                        float f4 = i2;
                        float f5 = this.controlHeight;
                        if (f4 <= (f5 - ((i3 / 2) * f3)) - ((1.0f * f3) / 3.0f) || i2 >= f5) {
                            noEmpty(y2 - i2);
                        } else {
                            actionMove(-((int) (f3 / 3.0f)));
                            slowMove((-((int) this.unitHeight)) / 3);
                        }
                    } else {
                        actionMove((int) (f3 / 3.0f));
                        slowMove(((int) this.unitHeight) / 3);
                    }
                }
                this.isScrolling = false;
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        } else if (action == 2) {
            this.isGoOnMove = false;
            this.isScrolling = true;
            actionMove(y2 - this.lastY);
            this.lastY = y2;
        }
        return true;
    }

    public void refreshData(ArrayList<String> arrayList) {
        setData(arrayList);
        findItemsToShow();
        invalidate();
    }

    public void setCyclic(boolean z2) {
        this.isCyclic = z2;
        _setIsCyclic(z2);
    }

    public void setData(ArrayList<String> arrayList) {
        if (arrayList != null) {
            this.dataList.clear();
            this.dataList.addAll(arrayList);
        }
        initData();
    }

    public void setDefault(int i2) {
        this.defaultIndex = i2;
        if (i2 > this.itemList.size() - 1) {
            return;
        }
        this.moveDistance = 0;
        Iterator<ItemObject> it = this.itemList.iterator();
        while (it.hasNext()) {
            it.next().move = 0;
        }
        findItemsToShow();
        defaultMove((int) this.itemList.get(i2).moveToSelected());
    }

    public void setEnable(boolean z2) {
        this.isEnable = z2;
    }

    public void setItemNumber(int i2) {
        this.itemNumber = i2;
        this.controlHeight = i2 * this.unitHeight;
        this.toShowItems = new ItemObject[i2 + 2];
        requestLayout();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mSuffix = str;
        postInvalidate();
    }

    public void stopScroll() {
        Handler handler = this.moveHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            slowMove(0);
        }
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isScrolling = false;
        this.itemList = new ArrayList<>();
        this.dataList = new ArrayList<>();
        this.downTime = 0L;
        this.density = 1.0f;
        this.slowMoveSpeed = 1;
        this.clickDistance = 2;
        this.clickTimeout = 100;
        this.lineColor = -1644826;
        this.lineHeight = 2.0f;
        this.normalFont = 14.0f;
        this.selectedFont = 22.0f;
        this.unitHeight = 50.0f;
        this.itemNumber = 5;
        this.scale = true;
        this.normalColor = -13421773;
        this.selectedColor = -13421773;
        this.isEnable = true;
        this.noEmpty = true;
        this.isCyclic = true;
        this._isCyclic = true;
        this.isClearing = false;
        this.goonInterpolator = new DecelerateInterpolator(2.0f);
        this.showTime = 0;
        this.isGoOnMove = false;
        this.mSuffix = "";
        init(context, attributeSet);
        initData();
    }

    public WheelView(Context context) {
        super(context);
        this.isScrolling = false;
        this.itemList = new ArrayList<>();
        this.dataList = new ArrayList<>();
        this.downTime = 0L;
        this.density = 1.0f;
        this.slowMoveSpeed = 1;
        this.clickDistance = 2;
        this.clickTimeout = 100;
        this.lineColor = -1644826;
        this.lineHeight = 2.0f;
        this.normalFont = 14.0f;
        this.selectedFont = 22.0f;
        this.unitHeight = 50.0f;
        this.itemNumber = 5;
        this.scale = true;
        this.normalColor = -13421773;
        this.selectedColor = -13421773;
        this.isEnable = true;
        this.noEmpty = true;
        this.isCyclic = true;
        this._isCyclic = true;
        this.isClearing = false;
        this.goonInterpolator = new DecelerateInterpolator(2.0f);
        this.showTime = 0;
        this.isGoOnMove = false;
        this.mSuffix = "";
        initData();
    }
}
