package androidx.vectordrawable.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class VectorDrawableCompat extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";

    /* renamed from: b, reason: collision with root package name */
    static final PorterDuff.Mode f6388b = PorterDuff.Mode.SRC_IN;
    private boolean mAllowCaching;
    private Drawable.ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;
    private VectorDrawableCompatState mVectorState;

    private static class VClipPath extends VPath {
        VClipPath() {
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.f6406b = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.f6405a = PathParser.createNodesFromPathData(string2);
            }
            this.f6407c = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 2, 0);
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.f6372d);
                updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser);
                typedArrayObtainAttributes.recycle();
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public boolean isClipPath() {
            return true;
        }

        VClipPath(VClipPath vClipPath) {
            super(vClipPath);
        }
    }

    private static abstract class VObject {
        private VObject() {
        }

        public boolean isStateful() {
            return false;
        }

        public boolean onStateChanged(int[] iArr) {
            return false;
        }
    }

    private static class VectorDrawableCompatState extends Drawable.ConstantState {

        /* renamed from: a, reason: collision with root package name */
        int f6420a;

        /* renamed from: b, reason: collision with root package name */
        VPathRenderer f6421b;

        /* renamed from: c, reason: collision with root package name */
        ColorStateList f6422c;

        /* renamed from: d, reason: collision with root package name */
        PorterDuff.Mode f6423d;

        /* renamed from: e, reason: collision with root package name */
        boolean f6424e;

        /* renamed from: f, reason: collision with root package name */
        Bitmap f6425f;

        /* renamed from: g, reason: collision with root package name */
        ColorStateList f6426g;

        /* renamed from: h, reason: collision with root package name */
        PorterDuff.Mode f6427h;

        /* renamed from: i, reason: collision with root package name */
        int f6428i;

        /* renamed from: j, reason: collision with root package name */
        boolean f6429j;

        /* renamed from: k, reason: collision with root package name */
        boolean f6430k;

        /* renamed from: l, reason: collision with root package name */
        Paint f6431l;

        public VectorDrawableCompatState(VectorDrawableCompatState vectorDrawableCompatState) {
            this.f6422c = null;
            this.f6423d = VectorDrawableCompat.f6388b;
            if (vectorDrawableCompatState != null) {
                this.f6420a = vectorDrawableCompatState.f6420a;
                VPathRenderer vPathRenderer = new VPathRenderer(vectorDrawableCompatState.f6421b);
                this.f6421b = vPathRenderer;
                if (vectorDrawableCompatState.f6421b.f6410b != null) {
                    vPathRenderer.f6410b = new Paint(vectorDrawableCompatState.f6421b.f6410b);
                }
                if (vectorDrawableCompatState.f6421b.f6409a != null) {
                    this.f6421b.f6409a = new Paint(vectorDrawableCompatState.f6421b.f6409a);
                }
                this.f6422c = vectorDrawableCompatState.f6422c;
                this.f6423d = vectorDrawableCompatState.f6423d;
                this.f6424e = vectorDrawableCompatState.f6424e;
            }
        }

        public boolean canReuseBitmap(int i2, int i3) {
            return i2 == this.f6425f.getWidth() && i3 == this.f6425f.getHeight();
        }

        public boolean canReuseCache() {
            return !this.f6430k && this.f6426g == this.f6422c && this.f6427h == this.f6423d && this.f6429j == this.f6424e && this.f6428i == this.f6421b.getRootAlpha();
        }

        public void createCachedBitmapIfNeeded(int i2, int i3) {
            if (this.f6425f == null || !canReuseBitmap(i2, i3)) {
                this.f6425f = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                this.f6430k = true;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f6425f, (Rect) null, rect, getPaint(colorFilter));
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.f6420a;
        }

        public Paint getPaint(ColorFilter colorFilter) {
            if (!hasTranslucentRoot() && colorFilter == null) {
                return null;
            }
            if (this.f6431l == null) {
                Paint paint = new Paint();
                this.f6431l = paint;
                paint.setFilterBitmap(true);
            }
            this.f6431l.setAlpha(this.f6421b.getRootAlpha());
            this.f6431l.setColorFilter(colorFilter);
            return this.f6431l;
        }

        public boolean hasTranslucentRoot() {
            return this.f6421b.getRootAlpha() < 255;
        }

        public boolean isStateful() {
            return this.f6421b.isStateful();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public boolean onStateChanged(int[] iArr) {
            boolean zOnStateChanged = this.f6421b.onStateChanged(iArr);
            this.f6430k |= zOnStateChanged;
            return zOnStateChanged;
        }

        public void updateCacheStates() {
            this.f6426g = this.f6422c;
            this.f6427h = this.f6423d;
            this.f6428i = this.f6421b.getRootAlpha();
            this.f6429j = this.f6424e;
            this.f6430k = false;
        }

        public void updateCachedBitmap(int i2, int i3) {
            this.f6425f.eraseColor(0);
            this.f6421b.draw(new Canvas(this.f6425f), i2, i3, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public VectorDrawableCompatState() {
            this.f6422c = null;
            this.f6423d = VectorDrawableCompat.f6388b;
            this.f6421b = new VPathRenderer();
        }
    }

    VectorDrawableCompat() {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    static int a(int i2, float f2) {
        return (i2 & ViewCompat.MEASURED_SIZE_MASK) | (((int) (Color.alpha(i2) * f2)) << 24);
    }

    @Nullable
    public static VectorDrawableCompat create(@NonNull Resources resources, @DrawableRes int i2, @Nullable Resources.Theme theme) {
        int next;
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.f6387a = ResourcesCompat.getDrawable(resources, i2, theme);
            vectorDrawableCompat.mCachedConstantStateDelegate = new VectorDrawableDelegateState(vectorDrawableCompat.f6387a.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xml = resources.getXml(i2);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return createFromXmlInner(resources, (XmlPullParser) xml, attributeSetAsAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException e2) {
            Log.e("VectorDrawableCompat", "parser error", e2);
            return null;
        } catch (XmlPullParserException e3) {
            Log.e("VectorDrawableCompat", "parser error", e3);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    private void inflateInternal(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.f6421b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(vPathRenderer.f6411c);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z2 = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                VGroup vGroup = (VGroup) arrayDeque.peek();
                if (SHAPE_PATH.equals(name)) {
                    VFullPath vFullPath = new VFullPath();
                    vFullPath.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.f6401b.add(vFullPath);
                    if (vFullPath.getPathName() != null) {
                        vPathRenderer.f6419k.put(vFullPath.getPathName(), vFullPath);
                    }
                    vectorDrawableCompatState.f6420a = vFullPath.f6408d | vectorDrawableCompatState.f6420a;
                    z2 = false;
                } else if (SHAPE_CLIP_PATH.equals(name)) {
                    VClipPath vClipPath = new VClipPath();
                    vClipPath.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.f6401b.add(vClipPath);
                    if (vClipPath.getPathName() != null) {
                        vPathRenderer.f6419k.put(vClipPath.getPathName(), vClipPath);
                    }
                    vectorDrawableCompatState.f6420a = vClipPath.f6408d | vectorDrawableCompatState.f6420a;
                } else if (SHAPE_GROUP.equals(name)) {
                    VGroup vGroup2 = new VGroup();
                    vGroup2.inflate(resources, attributeSet, theme, xmlPullParser);
                    vGroup.f6401b.add(vGroup2);
                    arrayDeque.push(vGroup2);
                    if (vGroup2.getGroupName() != null) {
                        vPathRenderer.f6419k.put(vGroup2.getGroupName(), vGroup2);
                    }
                    vectorDrawableCompatState.f6420a = vGroup2.f6404e | vectorDrawableCompatState.f6420a;
                }
            } else if (eventType == 3 && SHAPE_GROUP.equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z2) {
            throw new XmlPullParserException("no path defined");
        }
    }

    private boolean needMirroring() {
        return isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
    }

    private static PorterDuff.Mode parseTintModeCompat(int i2, PorterDuff.Mode mode) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    private void printGroupTree(VGroup vGroup, int i2) {
        String str = "";
        for (int i3 = 0; i3 < i2; i3++) {
            str = str + "    ";
        }
        Log.v("VectorDrawableCompat", str + "current group is :" + vGroup.getGroupName() + " rotation is " + vGroup.f6402c);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("matrix is :");
        sb.append(vGroup.getLocalMatrix().toString());
        Log.v("VectorDrawableCompat", sb.toString());
        for (int i4 = 0; i4 < vGroup.f6401b.size(); i4++) {
            VObject vObject = (VObject) vGroup.f6401b.get(i4);
            if (vObject instanceof VGroup) {
                printGroupTree((VGroup) vObject, i2 + 1);
            } else {
                ((VPath) vObject).printVPath(i2 + 1);
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException {
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        VPathRenderer vPathRenderer = vectorDrawableCompatState.f6421b;
        vectorDrawableCompatState.f6423d = parseTintModeCompat(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList namedColorStateList = TypedArrayUtils.getNamedColorStateList(typedArray, xmlPullParser, theme, "tint", 1);
        if (namedColorStateList != null) {
            vectorDrawableCompatState.f6422c = namedColorStateList;
        }
        vectorDrawableCompatState.f6424e = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.f6424e);
        vPathRenderer.f6414f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, vPathRenderer.f6414f);
        float namedFloat = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, vPathRenderer.f6415g);
        vPathRenderer.f6415g = namedFloat;
        if (vPathRenderer.f6414f <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (namedFloat <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
        vPathRenderer.f6412d = typedArray.getDimension(3, vPathRenderer.f6412d);
        float dimension = typedArray.getDimension(2, vPathRenderer.f6413e);
        vPathRenderer.f6413e = dimension;
        if (vPathRenderer.f6412d <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
        }
        if (dimension <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
        }
        vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, vPathRenderer.getAlpha()));
        String string = typedArray.getString(0);
        if (string != null) {
            vPathRenderer.f6417i = string;
            vPathRenderer.f6419k.put(string, vPathRenderer);
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    Object b(String str) {
        return this.mVectorState.f6421b.f6419k.get(str);
    }

    void c(boolean z2) {
        this.mAllowCaching = z2;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.f6387a;
        if (drawable == null) {
            return false;
        }
        DrawableCompat.canApplyTheme(drawable);
        return false;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    PorterDuffColorFilter d(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() <= 0 || this.mTmpBounds.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter == null) {
            colorFilter = this.mTintFilter;
        }
        canvas.getMatrix(this.mTmpMatrix);
        this.mTmpMatrix.getValues(this.mTmpFloats);
        float fAbs = Math.abs(this.mTmpFloats[0]);
        float fAbs2 = Math.abs(this.mTmpFloats[4]);
        float fAbs3 = Math.abs(this.mTmpFloats[1]);
        float fAbs4 = Math.abs(this.mTmpFloats[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iMin = Math.min(2048, (int) (this.mTmpBounds.width() * fAbs));
        int iMin2 = Math.min(2048, (int) (this.mTmpBounds.height() * fAbs2));
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        Rect rect = this.mTmpBounds;
        canvas.translate(rect.left, rect.top);
        if (needMirroring()) {
            canvas.translate(this.mTmpBounds.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mTmpBounds.offsetTo(0, 0);
        this.mVectorState.createCachedBitmapIfNeeded(iMin, iMin2);
        if (!this.mAllowCaching) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2);
        } else if (!this.mVectorState.canReuseCache()) {
            this.mVectorState.updateCachedBitmap(iMin, iMin2);
            this.mVectorState.updateCacheStates();
        }
        this.mVectorState.drawCachedBitmapWithRootAlpha(canvas, colorFilter, this.mTmpBounds);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.f6387a;
        return drawable != null ? DrawableCompat.getAlpha(drawable) : this.mVectorState.f6421b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.f6387a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        Drawable drawable = this.f6387a;
        return drawable != null ? DrawableCompat.getColorFilter(drawable) : this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.f6387a != null && Build.VERSION.SDK_INT >= 24) {
            return new VectorDrawableDelegateState(this.f6387a.getConstantState());
        }
        this.mVectorState.f6420a = getChangingConfigurations();
        return this.mVectorState;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.f6387a;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.mVectorState.f6421b.f6413e;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.f6387a;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.mVectorState.f6421b.f6412d;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public float getPixelSize() {
        VPathRenderer vPathRenderer;
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState == null || (vPathRenderer = vectorDrawableCompatState.f6421b) == null) {
            return 1.0f;
        }
        float f2 = vPathRenderer.f6412d;
        if (f2 == 0.0f) {
            return 1.0f;
        }
        float f3 = vPathRenderer.f6413e;
        if (f3 == 0.0f) {
            return 1.0f;
        }
        float f4 = vPathRenderer.f6415g;
        if (f4 == 0.0f) {
            return 1.0f;
        }
        float f5 = vPathRenderer.f6414f;
        if (f5 == 0.0f) {
            return 1.0f;
        }
        return Math.min(f5 / f2, f4 / f3);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.f6387a;
        return drawable != null ? DrawableCompat.isAutoMirrored(drawable) : this.mVectorState.f6424e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        VectorDrawableCompatState vectorDrawableCompatState;
        ColorStateList colorStateList;
        Drawable drawable = this.f6387a;
        return drawable != null ? drawable.isStateful() : super.isStateful() || ((vectorDrawableCompatState = this.mVectorState) != null && (vectorDrawableCompatState.isStateful() || ((colorStateList = this.mVectorState.f6422c) != null && colorStateList.isStateful())));
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean z2;
        PorterDuff.Mode mode;
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        ColorStateList colorStateList = vectorDrawableCompatState.f6422c;
        if (colorStateList == null || (mode = vectorDrawableCompatState.f6423d) == null) {
            z2 = false;
        } else {
            this.mTintFilter = d(this.mTintFilter, colorStateList, mode);
            invalidateSelf();
            z2 = true;
        }
        if (!vectorDrawableCompatState.isStateful() || !vectorDrawableCompatState.onStateChanged(iArr)) {
            return z2;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j2) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.setAlpha(i2);
        } else if (this.mVectorState.f6421b.getRootAlpha() != i2) {
            this.mVectorState.f6421b.setRootAlpha(i2);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z2) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            DrawableCompat.setAutoMirrored(drawable, z2);
        } else {
            this.mVectorState.f6424e = z2;
        }
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i2) {
        super.setChangingConfigurations(i2);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(int i2, PorterDuff.Mode mode) {
        super.setColorFilter(i2, mode);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z2) {
        super.setFilterBitmap(z2);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i2, int i3, int i4, int i5) {
        super.setHotspotBounds(i2, i3, i4, i5);
    }

    @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCommon, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTint(int i2) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            DrawableCompat.setTint(drawable, i2);
        } else {
            setTintList(ColorStateList.valueOf(i2));
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.f6422c != colorStateList) {
            vectorDrawableCompatState.f6422c = colorStateList;
            this.mTintFilter = d(this.mTintFilter, colorStateList, vectorDrawableCompatState.f6423d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            DrawableCompat.setTintMode(drawable, mode);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        if (vectorDrawableCompatState.f6423d != mode) {
            vectorDrawableCompatState.f6423d = mode;
            this.mTintFilter = d(this.mTintFilter, vectorDrawableCompatState.f6422c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z2, boolean z3) {
        Drawable drawable = this.f6387a;
        return drawable != null ? drawable.setVisible(z2, z3) : super.setVisible(z2, z3);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    @RequiresApi(24)
    private static class VectorDrawableDelegateState extends Drawable.ConstantState {
        private final Drawable.ConstantState mDelegateState;

        public VectorDrawableDelegateState(Drawable.ConstantState constantState) {
            this.mDelegateState = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mDelegateState.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mDelegateState.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.f6387a = (VectorDrawable) this.mDelegateState.newDrawable();
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.f6387a = (VectorDrawable) this.mDelegateState.newDrawable(resources);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.f6387a = (VectorDrawable) this.mDelegateState.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.mColorFilter = colorFilter;
            invalidateSelf();
        }
    }

    private static abstract class VPath extends VObject {

        /* renamed from: a, reason: collision with root package name */
        protected PathParser.PathDataNode[] f6405a;

        /* renamed from: b, reason: collision with root package name */
        String f6406b;

        /* renamed from: c, reason: collision with root package name */
        int f6407c;

        /* renamed from: d, reason: collision with root package name */
        int f6408d;

        public VPath() {
            super();
            this.f6405a = null;
            this.f6407c = 0;
        }

        public void applyTheme(Resources.Theme theme) {
        }

        public boolean canApplyTheme() {
            return false;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.f6405a;
        }

        public String getPathName() {
            return this.f6406b;
        }

        public boolean isClipPath() {
            return false;
        }

        public String nodesToString(PathParser.PathDataNode[] pathDataNodeArr) {
            String str = " ";
            for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
                str = str + pathDataNodeArr[i2].mType + ":";
                for (float f2 : pathDataNodeArr[i2].mParams) {
                    str = str + f2 + ",";
                }
            }
            return str;
        }

        public void printVPath(int i2) {
            String str = "";
            for (int i3 = 0; i3 < i2; i3++) {
                str = str + "    ";
            }
            Log.v("VectorDrawableCompat", str + "current path is :" + this.f6406b + " pathData is " + nodesToString(this.f6405a));
        }

        public void setPathData(PathParser.PathDataNode[] pathDataNodeArr) {
            if (PathParser.canMorph(this.f6405a, pathDataNodeArr)) {
                PathParser.updateNodes(this.f6405a, pathDataNodeArr);
            } else {
                this.f6405a = PathParser.deepCopyNodes(pathDataNodeArr);
            }
        }

        public void toPath(Path path) {
            path.reset();
            PathParser.PathDataNode[] pathDataNodeArr = this.f6405a;
            if (pathDataNodeArr != null) {
                PathParser.PathDataNode.nodesToPath(pathDataNodeArr, path);
            }
        }

        public VPath(VPath vPath) {
            super();
            this.f6405a = null;
            this.f6407c = 0;
            this.f6406b = vPath.f6406b;
            this.f6408d = vPath.f6408d;
            this.f6405a = PathParser.deepCopyNodes(vPath.f6405a);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Drawable drawable = this.f6387a;
        if (drawable != null) {
            DrawableCompat.inflate(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
        vectorDrawableCompatState.f6421b = new VPathRenderer();
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.f6369a);
        updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser, theme);
        typedArrayObtainAttributes.recycle();
        vectorDrawableCompatState.f6420a = getChangingConfigurations();
        vectorDrawableCompatState.f6430k = true;
        inflateInternal(resources, xmlPullParser, attributeSet, theme);
        this.mTintFilter = d(this.mTintFilter, vectorDrawableCompatState.f6422c, vectorDrawableCompatState.f6423d);
    }

    VectorDrawableCompat(VectorDrawableCompatState vectorDrawableCompatState) {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = vectorDrawableCompatState;
        this.mTintFilter = d(this.mTintFilter, vectorDrawableCompatState.f6422c, vectorDrawableCompatState.f6423d);
    }

    private static class VFullPath extends VPath {

        /* renamed from: e, reason: collision with root package name */
        ComplexColorCompat f6389e;

        /* renamed from: f, reason: collision with root package name */
        float f6390f;

        /* renamed from: g, reason: collision with root package name */
        ComplexColorCompat f6391g;

        /* renamed from: h, reason: collision with root package name */
        float f6392h;

        /* renamed from: i, reason: collision with root package name */
        float f6393i;

        /* renamed from: j, reason: collision with root package name */
        float f6394j;

        /* renamed from: k, reason: collision with root package name */
        float f6395k;

        /* renamed from: l, reason: collision with root package name */
        float f6396l;

        /* renamed from: m, reason: collision with root package name */
        Paint.Cap f6397m;
        private int[] mThemeAttrs;

        /* renamed from: n, reason: collision with root package name */
        Paint.Join f6398n;

        /* renamed from: o, reason: collision with root package name */
        float f6399o;

        VFullPath() {
            this.f6390f = 0.0f;
            this.f6392h = 1.0f;
            this.f6393i = 1.0f;
            this.f6394j = 0.0f;
            this.f6395k = 1.0f;
            this.f6396l = 0.0f;
            this.f6397m = Paint.Cap.BUTT;
            this.f6398n = Paint.Join.MITER;
            this.f6399o = 4.0f;
        }

        private Paint.Cap getStrokeLineCap(int i2, Paint.Cap cap) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? cap : Paint.Cap.SQUARE : Paint.Cap.ROUND : Paint.Cap.BUTT;
        }

        private Paint.Join getStrokeLineJoin(int i2, Paint.Join join) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? join : Paint.Join.BEVEL : Paint.Join.ROUND : Paint.Join.MITER;
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.mThemeAttrs = null;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.f6406b = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.f6405a = PathParser.createNodesFromPathData(string2);
                }
                this.f6391g = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
                this.f6393i = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "fillAlpha", 12, this.f6393i);
                this.f6397m = getStrokeLineCap(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.f6397m);
                this.f6398n = getStrokeLineJoin(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.f6398n);
                this.f6399o = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.f6399o);
                this.f6389e = TypedArrayUtils.getNamedComplexColor(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
                this.f6392h = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeAlpha", 11, this.f6392h);
                this.f6390f = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "strokeWidth", 4, this.f6390f);
                this.f6395k = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathEnd", 6, this.f6395k);
                this.f6396l = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathOffset", 7, this.f6396l);
                this.f6394j = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "trimPathStart", 5, this.f6394j);
                this.f6407c = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "fillType", 13, this.f6407c);
            }
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public void applyTheme(Resources.Theme theme) {
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VPath
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null;
        }

        float getFillAlpha() {
            return this.f6393i;
        }

        @ColorInt
        int getFillColor() {
            return this.f6391g.getColor();
        }

        float getStrokeAlpha() {
            return this.f6392h;
        }

        @ColorInt
        int getStrokeColor() {
            return this.f6389e.getColor();
        }

        float getStrokeWidth() {
            return this.f6390f;
        }

        float getTrimPathEnd() {
            return this.f6395k;
        }

        float getTrimPathOffset() {
            return this.f6396l;
        }

        float getTrimPathStart() {
            return this.f6394j;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.f6371c);
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser, theme);
            typedArrayObtainAttributes.recycle();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean isStateful() {
            return this.f6391g.isStateful() || this.f6389e.isStateful();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean onStateChanged(int[] iArr) {
            return this.f6389e.onStateChanged(iArr) | this.f6391g.onStateChanged(iArr);
        }

        void setFillAlpha(float f2) {
            this.f6393i = f2;
        }

        void setFillColor(int i2) {
            this.f6391g.setColor(i2);
        }

        void setStrokeAlpha(float f2) {
            this.f6392h = f2;
        }

        void setStrokeColor(int i2) {
            this.f6389e.setColor(i2);
        }

        void setStrokeWidth(float f2) {
            this.f6390f = f2;
        }

        void setTrimPathEnd(float f2) {
            this.f6395k = f2;
        }

        void setTrimPathOffset(float f2) {
            this.f6396l = f2;
        }

        void setTrimPathStart(float f2) {
            this.f6394j = f2;
        }

        VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.f6390f = 0.0f;
            this.f6392h = 1.0f;
            this.f6393i = 1.0f;
            this.f6394j = 0.0f;
            this.f6395k = 1.0f;
            this.f6396l = 0.0f;
            this.f6397m = Paint.Cap.BUTT;
            this.f6398n = Paint.Join.MITER;
            this.f6399o = 4.0f;
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.f6389e = vFullPath.f6389e;
            this.f6390f = vFullPath.f6390f;
            this.f6392h = vFullPath.f6392h;
            this.f6391g = vFullPath.f6391g;
            this.f6407c = vFullPath.f6407c;
            this.f6393i = vFullPath.f6393i;
            this.f6394j = vFullPath.f6394j;
            this.f6395k = vFullPath.f6395k;
            this.f6396l = vFullPath.f6396l;
            this.f6397m = vFullPath.f6397m;
            this.f6398n = vFullPath.f6398n;
            this.f6399o = vFullPath.f6399o;
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();

        /* renamed from: a, reason: collision with root package name */
        Paint f6409a;

        /* renamed from: b, reason: collision with root package name */
        Paint f6410b;

        /* renamed from: c, reason: collision with root package name */
        final VGroup f6411c;

        /* renamed from: d, reason: collision with root package name */
        float f6412d;

        /* renamed from: e, reason: collision with root package name */
        float f6413e;

        /* renamed from: f, reason: collision with root package name */
        float f6414f;

        /* renamed from: g, reason: collision with root package name */
        float f6415g;

        /* renamed from: h, reason: collision with root package name */
        int f6416h;

        /* renamed from: i, reason: collision with root package name */
        String f6417i;

        /* renamed from: j, reason: collision with root package name */
        Boolean f6418j;

        /* renamed from: k, reason: collision with root package name */
        final ArrayMap f6419k;
        private int mChangingConfigurations;
        private final Matrix mFinalPathMatrix;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;

        public VPathRenderer() {
            this.mFinalPathMatrix = new Matrix();
            this.f6412d = 0.0f;
            this.f6413e = 0.0f;
            this.f6414f = 0.0f;
            this.f6415g = 0.0f;
            this.f6416h = 255;
            this.f6417i = null;
            this.f6418j = null;
            this.f6419k = new ArrayMap();
            this.f6411c = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        private static float cross(float f2, float f3, float f4, float f5) {
            return (f2 * f5) - (f3 * f4);
        }

        private void drawGroupTree(VGroup vGroup, Matrix matrix, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            vGroup.f6400a.set(matrix);
            vGroup.f6400a.preConcat(vGroup.f6403d);
            canvas.save();
            for (int i4 = 0; i4 < vGroup.f6401b.size(); i4++) {
                VObject vObject = (VObject) vGroup.f6401b.get(i4);
                if (vObject instanceof VGroup) {
                    drawGroupTree((VGroup) vObject, vGroup.f6400a, canvas, i2, i3, colorFilter);
                } else if (vObject instanceof VPath) {
                    drawPath(vGroup, (VPath) vObject, canvas, i2, i3, colorFilter);
                }
            }
            canvas.restore();
        }

        private void drawPath(VGroup vGroup, VPath vPath, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            float f2 = i2 / this.f6414f;
            float f3 = i3 / this.f6415g;
            float fMin = Math.min(f2, f3);
            Matrix matrix = vGroup.f6400a;
            this.mFinalPathMatrix.set(matrix);
            this.mFinalPathMatrix.postScale(f2, f3);
            float matrixScale = getMatrixScale(matrix);
            if (matrixScale == 0.0f) {
                return;
            }
            vPath.toPath(this.mPath);
            Path path = this.mPath;
            this.mRenderPath.reset();
            if (vPath.isClipPath()) {
                this.mRenderPath.setFillType(vPath.f6407c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                this.mRenderPath.addPath(path, this.mFinalPathMatrix);
                canvas.clipPath(this.mRenderPath);
                return;
            }
            VFullPath vFullPath = (VFullPath) vPath;
            float f4 = vFullPath.f6394j;
            if (f4 != 0.0f || vFullPath.f6395k != 1.0f) {
                float f5 = vFullPath.f6396l;
                float f6 = (f4 + f5) % 1.0f;
                float f7 = (vFullPath.f6395k + f5) % 1.0f;
                if (this.mPathMeasure == null) {
                    this.mPathMeasure = new PathMeasure();
                }
                this.mPathMeasure.setPath(this.mPath, false);
                float length = this.mPathMeasure.getLength();
                float f8 = f6 * length;
                float f9 = f7 * length;
                path.reset();
                if (f8 > f9) {
                    this.mPathMeasure.getSegment(f8, length, path, true);
                    this.mPathMeasure.getSegment(0.0f, f9, path, true);
                } else {
                    this.mPathMeasure.getSegment(f8, f9, path, true);
                }
                path.rLineTo(0.0f, 0.0f);
            }
            this.mRenderPath.addPath(path, this.mFinalPathMatrix);
            if (vFullPath.f6391g.willDraw()) {
                ComplexColorCompat complexColorCompat = vFullPath.f6391g;
                if (this.f6410b == null) {
                    Paint paint = new Paint(1);
                    this.f6410b = paint;
                    paint.setStyle(Paint.Style.FILL);
                }
                Paint paint2 = this.f6410b;
                if (complexColorCompat.isGradient()) {
                    Shader shader = complexColorCompat.getShader();
                    shader.setLocalMatrix(this.mFinalPathMatrix);
                    paint2.setShader(shader);
                    paint2.setAlpha(Math.round(vFullPath.f6393i * 255.0f));
                } else {
                    paint2.setShader(null);
                    paint2.setAlpha(255);
                    paint2.setColor(VectorDrawableCompat.a(complexColorCompat.getColor(), vFullPath.f6393i));
                }
                paint2.setColorFilter(colorFilter);
                this.mRenderPath.setFillType(vFullPath.f6407c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                canvas.drawPath(this.mRenderPath, paint2);
            }
            if (vFullPath.f6389e.willDraw()) {
                ComplexColorCompat complexColorCompat2 = vFullPath.f6389e;
                if (this.f6409a == null) {
                    Paint paint3 = new Paint(1);
                    this.f6409a = paint3;
                    paint3.setStyle(Paint.Style.STROKE);
                }
                Paint paint4 = this.f6409a;
                Paint.Join join = vFullPath.f6398n;
                if (join != null) {
                    paint4.setStrokeJoin(join);
                }
                Paint.Cap cap = vFullPath.f6397m;
                if (cap != null) {
                    paint4.setStrokeCap(cap);
                }
                paint4.setStrokeMiter(vFullPath.f6399o);
                if (complexColorCompat2.isGradient()) {
                    Shader shader2 = complexColorCompat2.getShader();
                    shader2.setLocalMatrix(this.mFinalPathMatrix);
                    paint4.setShader(shader2);
                    paint4.setAlpha(Math.round(vFullPath.f6392h * 255.0f));
                } else {
                    paint4.setShader(null);
                    paint4.setAlpha(255);
                    paint4.setColor(VectorDrawableCompat.a(complexColorCompat2.getColor(), vFullPath.f6392h));
                }
                paint4.setColorFilter(colorFilter);
                paint4.setStrokeWidth(vFullPath.f6390f * fMin * matrixScale);
                canvas.drawPath(this.mRenderPath, paint4);
            }
        }

        private float getMatrixScale(Matrix matrix) {
            float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
            matrix.mapVectors(fArr);
            float fHypot = (float) Math.hypot(fArr[0], fArr[1]);
            float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
            float fCross = cross(fArr[0], fArr[1], fArr[2], fArr[3]);
            float fMax = Math.max(fHypot, fHypot2);
            if (fMax > 0.0f) {
                return Math.abs(fCross) / fMax;
            }
            return 0.0f;
        }

        public void draw(Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            drawGroupTree(this.f6411c, IDENTITY_MATRIX, canvas, i2, i3, colorFilter);
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.f6416h;
        }

        public boolean isStateful() {
            if (this.f6418j == null) {
                this.f6418j = Boolean.valueOf(this.f6411c.isStateful());
            }
            return this.f6418j.booleanValue();
        }

        public boolean onStateChanged(int[] iArr) {
            return this.f6411c.onStateChanged(iArr);
        }

        public void setAlpha(float f2) {
            setRootAlpha((int) (f2 * 255.0f));
        }

        public void setRootAlpha(int i2) {
            this.f6416h = i2;
        }

        public VPathRenderer(VPathRenderer vPathRenderer) {
            this.mFinalPathMatrix = new Matrix();
            this.f6412d = 0.0f;
            this.f6413e = 0.0f;
            this.f6414f = 0.0f;
            this.f6415g = 0.0f;
            this.f6416h = 255;
            this.f6417i = null;
            this.f6418j = null;
            ArrayMap arrayMap = new ArrayMap();
            this.f6419k = arrayMap;
            this.f6411c = new VGroup(vPathRenderer.f6411c, arrayMap);
            this.mPath = new Path(vPathRenderer.mPath);
            this.mRenderPath = new Path(vPathRenderer.mRenderPath);
            this.f6412d = vPathRenderer.f6412d;
            this.f6413e = vPathRenderer.f6413e;
            this.f6414f = vPathRenderer.f6414f;
            this.f6415g = vPathRenderer.f6415g;
            this.mChangingConfigurations = vPathRenderer.mChangingConfigurations;
            this.f6416h = vPathRenderer.f6416h;
            this.f6417i = vPathRenderer.f6417i;
            String str = vPathRenderer.f6417i;
            if (str != null) {
                arrayMap.put(str, this);
            }
            this.f6418j = vPathRenderer.f6418j;
        }
    }

    private static class VGroup extends VObject {

        /* renamed from: a, reason: collision with root package name */
        final Matrix f6400a;

        /* renamed from: b, reason: collision with root package name */
        final ArrayList f6401b;

        /* renamed from: c, reason: collision with root package name */
        float f6402c;

        /* renamed from: d, reason: collision with root package name */
        final Matrix f6403d;

        /* renamed from: e, reason: collision with root package name */
        int f6404e;
        private String mGroupName;
        private float mPivotX;
        private float mPivotY;
        private float mScaleX;
        private float mScaleY;
        private int[] mThemeAttrs;
        private float mTranslateX;
        private float mTranslateY;

        public VGroup(VGroup vGroup, ArrayMap<String, Object> arrayMap) {
            VPath vClipPath;
            super();
            this.f6400a = new Matrix();
            this.f6401b = new ArrayList();
            this.f6402c = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            Matrix matrix = new Matrix();
            this.f6403d = matrix;
            this.mGroupName = null;
            this.f6402c = vGroup.f6402c;
            this.mPivotX = vGroup.mPivotX;
            this.mPivotY = vGroup.mPivotY;
            this.mScaleX = vGroup.mScaleX;
            this.mScaleY = vGroup.mScaleY;
            this.mTranslateX = vGroup.mTranslateX;
            this.mTranslateY = vGroup.mTranslateY;
            this.mThemeAttrs = vGroup.mThemeAttrs;
            String str = vGroup.mGroupName;
            this.mGroupName = str;
            this.f6404e = vGroup.f6404e;
            if (str != null) {
                arrayMap.put(str, this);
            }
            matrix.set(vGroup.f6403d);
            ArrayList arrayList = vGroup.f6401b;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                Object obj = arrayList.get(i2);
                if (obj instanceof VGroup) {
                    this.f6401b.add(new VGroup((VGroup) obj, arrayMap));
                } else {
                    if (obj instanceof VFullPath) {
                        vClipPath = new VFullPath((VFullPath) obj);
                    } else {
                        if (!(obj instanceof VClipPath)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        vClipPath = new VClipPath((VClipPath) obj);
                    }
                    this.f6401b.add(vClipPath);
                    String str2 = vClipPath.f6406b;
                    if (str2 != null) {
                        arrayMap.put(str2, vClipPath);
                    }
                }
            }
        }

        private void updateLocalMatrix() {
            this.f6403d.reset();
            this.f6403d.postTranslate(-this.mPivotX, -this.mPivotY);
            this.f6403d.postScale(this.mScaleX, this.mScaleY);
            this.f6403d.postRotate(this.f6402c, 0.0f, 0.0f);
            this.f6403d.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        private void updateStateFromTypedArray(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.mThemeAttrs = null;
            this.f6402c = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, Key.ROTATION, 5, this.f6402c);
            this.mPivotX = typedArray.getFloat(1, this.mPivotX);
            this.mPivotY = typedArray.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "translateY", 7, this.mTranslateY);
            String string = typedArray.getString(0);
            if (string != null) {
                this.mGroupName = string;
            }
            updateLocalMatrix();
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() {
            return this.f6403d;
        }

        public float getPivotX() {
            return this.mPivotX;
        }

        public float getPivotY() {
            return this.mPivotY;
        }

        public float getRotation() {
            return this.f6402c;
        }

        public float getScaleX() {
            return this.mScaleX;
        }

        public float getScaleY() {
            return this.mScaleY;
        }

        public float getTranslateX() {
            return this.mTranslateX;
        }

        public float getTranslateY() {
            return this.mTranslateY;
        }

        public void inflate(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.f6370b);
            updateStateFromTypedArray(typedArrayObtainAttributes, xmlPullParser);
            typedArrayObtainAttributes.recycle();
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean isStateful() {
            for (int i2 = 0; i2 < this.f6401b.size(); i2++) {
                if (((VObject) this.f6401b.get(i2)).isStateful()) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.VObject
        public boolean onStateChanged(int[] iArr) {
            boolean zOnStateChanged = false;
            for (int i2 = 0; i2 < this.f6401b.size(); i2++) {
                zOnStateChanged |= ((VObject) this.f6401b.get(i2)).onStateChanged(iArr);
            }
            return zOnStateChanged;
        }

        public void setPivotX(float f2) {
            if (f2 != this.mPivotX) {
                this.mPivotX = f2;
                updateLocalMatrix();
            }
        }

        public void setPivotY(float f2) {
            if (f2 != this.mPivotY) {
                this.mPivotY = f2;
                updateLocalMatrix();
            }
        }

        public void setRotation(float f2) {
            if (f2 != this.f6402c) {
                this.f6402c = f2;
                updateLocalMatrix();
            }
        }

        public void setScaleX(float f2) {
            if (f2 != this.mScaleX) {
                this.mScaleX = f2;
                updateLocalMatrix();
            }
        }

        public void setScaleY(float f2) {
            if (f2 != this.mScaleY) {
                this.mScaleY = f2;
                updateLocalMatrix();
            }
        }

        public void setTranslateX(float f2) {
            if (f2 != this.mTranslateX) {
                this.mTranslateX = f2;
                updateLocalMatrix();
            }
        }

        public void setTranslateY(float f2) {
            if (f2 != this.mTranslateY) {
                this.mTranslateY = f2;
                updateLocalMatrix();
            }
        }

        public VGroup() {
            super();
            this.f6400a = new Matrix();
            this.f6401b = new ArrayList();
            this.f6402c = 0.0f;
            this.mPivotX = 0.0f;
            this.mPivotY = 0.0f;
            this.mScaleX = 1.0f;
            this.mScaleY = 1.0f;
            this.mTranslateX = 0.0f;
            this.mTranslateY = 0.0f;
            this.f6403d = new Matrix();
            this.mGroupName = null;
        }
    }
}
