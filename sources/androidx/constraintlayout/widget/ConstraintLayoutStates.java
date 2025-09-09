package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";

    /* renamed from: a, reason: collision with root package name */
    ConstraintSet f3266a;
    private final ConstraintLayout mConstraintLayout;

    /* renamed from: b, reason: collision with root package name */
    int f3267b = -1;

    /* renamed from: c, reason: collision with root package name */
    int f3268c = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    static class State {

        /* renamed from: a, reason: collision with root package name */
        int f3269a;

        /* renamed from: b, reason: collision with root package name */
        ArrayList f3270b = new ArrayList();

        /* renamed from: c, reason: collision with root package name */
        int f3271c;

        /* renamed from: d, reason: collision with root package name */
        ConstraintSet f3272d;

        public State(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.f3271c = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.State_android_id) {
                    this.f3269a = typedArrayObtainStyledAttributes.getResourceId(index, this.f3269a);
                } else if (index == R.styleable.State_constraints) {
                    this.f3271c = typedArrayObtainStyledAttributes.getResourceId(index, this.f3271c);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.f3271c);
                    context.getResources().getResourceName(this.f3271c);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.f3272d = constraintSet;
                        constraintSet.clone(context, this.f3271c);
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        void a(Variant variant) {
            this.f3270b.add(variant);
        }

        public int findMatch(float f2, float f3) {
            for (int i2 = 0; i2 < this.f3270b.size(); i2++) {
                if (((Variant) this.f3270b.get(i2)).a(f2, f3)) {
                    return i2;
                }
            }
            return -1;
        }
    }

    static class Variant {

        /* renamed from: a, reason: collision with root package name */
        float f3273a;

        /* renamed from: b, reason: collision with root package name */
        float f3274b;

        /* renamed from: c, reason: collision with root package name */
        float f3275c;

        /* renamed from: d, reason: collision with root package name */
        float f3276d;

        /* renamed from: e, reason: collision with root package name */
        int f3277e;

        /* renamed from: f, reason: collision with root package name */
        ConstraintSet f3278f;

        public Variant(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.f3273a = Float.NaN;
            this.f3274b = Float.NaN;
            this.f3275c = Float.NaN;
            this.f3276d = Float.NaN;
            this.f3277e = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.Variant_constraints) {
                    this.f3277e = typedArrayObtainStyledAttributes.getResourceId(index, this.f3277e);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.f3277e);
                    context.getResources().getResourceName(this.f3277e);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.f3278f = constraintSet;
                        constraintSet.clone(context, this.f3277e);
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.f3276d = typedArrayObtainStyledAttributes.getDimension(index, this.f3276d);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.f3274b = typedArrayObtainStyledAttributes.getDimension(index, this.f3274b);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.f3275c = typedArrayObtainStyledAttributes.getDimension(index, this.f3275c);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.f3273a = typedArrayObtainStyledAttributes.getDimension(index, this.f3273a);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        boolean a(float f2, float f3) {
            if (!Float.isNaN(this.f3273a) && f2 < this.f3273a) {
                return false;
            }
            if (!Float.isNaN(this.f3274b) && f3 < this.f3274b) {
                return false;
            }
            if (Float.isNaN(this.f3275c) || f2 <= this.f3275c) {
                return Float.isNaN(this.f3276d) || f3 <= this.f3276d;
            }
            return false;
        }
    }

    ConstraintLayoutStates(Context context, ConstraintLayout constraintLayout, int i2) throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
        this.mConstraintLayout = constraintLayout;
        load(context, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r8, int r9) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException, java.lang.NumberFormatException {
        /*
            r7 = this;
            android.content.res.Resources r0 = r8.getResources()
            android.content.res.XmlResourceParser r9 = r0.getXml(r9)
            int r0 = r9.getEventType()     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            r1 = 0
        Ld:
            r2 = 1
            if (r0 == r2) goto L8d
            if (r0 == 0) goto L7e
            r3 = 2
            if (r0 == r3) goto L17
            goto L81
        L17:
            java.lang.String r0 = r9.getName()     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            int r4 = r0.hashCode()     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            r5 = 4
            r6 = 3
            switch(r4) {
                case -1349929691: goto L50;
                case 80204913: goto L46;
                case 1382829617: goto L3d;
                case 1657696882: goto L33;
                case 1901439077: goto L25;
                default: goto L24;
            }     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
        L24:
            goto L5a
        L25:
            java.lang.String r2 = "Variant"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r0 == 0) goto L5a
            r2 = r6
            goto L5b
        L2f:
            r8 = move-exception
            goto L86
        L31:
            r8 = move-exception
            goto L8a
        L33:
            java.lang.String r2 = "layoutDescription"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r0 == 0) goto L5a
            r2 = 0
            goto L5b
        L3d:
            java.lang.String r4 = "StateSet"
            boolean r0 = r0.equals(r4)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r0 == 0) goto L5a
            goto L5b
        L46:
            java.lang.String r2 = "State"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r0 == 0) goto L5a
            r2 = r3
            goto L5b
        L50:
            java.lang.String r2 = "ConstraintSet"
            boolean r0 = r0.equals(r2)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r0 == 0) goto L5a
            r2 = r5
            goto L5b
        L5a:
            r2 = -1
        L5b:
            if (r2 == r3) goto L71
            if (r2 == r6) goto L66
            if (r2 == r5) goto L62
            goto L81
        L62:
            r7.parseConstraintSet(r8, r9)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            goto L81
        L66:
            androidx.constraintlayout.widget.ConstraintLayoutStates$Variant r0 = new androidx.constraintlayout.widget.ConstraintLayoutStates$Variant     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            r0.<init>(r8, r9)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            if (r1 == 0) goto L81
            r1.a(r0)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            goto L81
        L71:
            androidx.constraintlayout.widget.ConstraintLayoutStates$State r1 = new androidx.constraintlayout.widget.ConstraintLayoutStates$State     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            r1.<init>(r8, r9)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintLayoutStates$State> r0 = r7.mStateList     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            int r2 = r1.f3269a     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            r0.put(r2, r1)     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            goto L81
        L7e:
            r9.getName()     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
        L81:
            int r0 = r9.next()     // Catch: java.io.IOException -> L2f org.xmlpull.v1.XmlPullParserException -> L31
            goto Ld
        L86:
            r8.printStackTrace()
            goto L8d
        L8a:
            r8.printStackTrace()
        L8d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayoutStates.load(android.content.Context, int):void");
    }

    private void parseConstraintSet(Context context, XmlPullParser xmlPullParser) throws NumberFormatException {
        ConstraintSet constraintSet = new ConstraintSet();
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String attributeValue = xmlPullParser.getAttributeValue(i2);
            if (attributeName != null && attributeValue != null && "id".equals(attributeName)) {
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1) {
                    if (attributeValue.length() > 1) {
                        identifier = Integer.parseInt(attributeValue.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                constraintSet.load(context, xmlPullParser);
                this.mConstraintSetMap.put(identifier, constraintSet);
                return;
            }
        }
    }

    public boolean needsToChange(int i2, float f2, float f3) {
        int i3 = this.f3267b;
        if (i3 != i2) {
            return true;
        }
        State stateValueAt = i2 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i3);
        int i4 = this.f3268c;
        return (i4 == -1 || !((Variant) stateValueAt.f3270b.get(i4)).a(f2, f3)) && this.f3268c != stateValueAt.findMatch(f2, f3);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public void updateConstraints(int i2, float f2, float f3) {
        int iFindMatch;
        int i3 = this.f3267b;
        if (i3 == i2) {
            State stateValueAt = i2 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i3);
            int i4 = this.f3268c;
            if ((i4 == -1 || !((Variant) stateValueAt.f3270b.get(i4)).a(f2, f3)) && this.f3268c != (iFindMatch = stateValueAt.findMatch(f2, f3))) {
                ConstraintSet constraintSet = iFindMatch == -1 ? this.f3266a : ((Variant) stateValueAt.f3270b.get(iFindMatch)).f3278f;
                int i5 = iFindMatch == -1 ? stateValueAt.f3271c : ((Variant) stateValueAt.f3270b.get(iFindMatch)).f3277e;
                if (constraintSet == null) {
                    return;
                }
                this.f3268c = iFindMatch;
                ConstraintsChangedListener constraintsChangedListener = this.mConstraintsChangedListener;
                if (constraintsChangedListener != null) {
                    constraintsChangedListener.preLayoutChange(-1, i5);
                }
                constraintSet.applyTo(this.mConstraintLayout);
                ConstraintsChangedListener constraintsChangedListener2 = this.mConstraintsChangedListener;
                if (constraintsChangedListener2 != null) {
                    constraintsChangedListener2.postLayoutChange(-1, i5);
                    return;
                }
                return;
            }
            return;
        }
        this.f3267b = i2;
        State state = this.mStateList.get(i2);
        int iFindMatch2 = state.findMatch(f2, f3);
        ConstraintSet constraintSet2 = iFindMatch2 == -1 ? state.f3272d : ((Variant) state.f3270b.get(iFindMatch2)).f3278f;
        int i6 = iFindMatch2 == -1 ? state.f3271c : ((Variant) state.f3270b.get(iFindMatch2)).f3277e;
        if (constraintSet2 == null) {
            Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + i2 + ", dim =" + f2 + ", " + f3);
            return;
        }
        this.f3268c = iFindMatch2;
        ConstraintsChangedListener constraintsChangedListener3 = this.mConstraintsChangedListener;
        if (constraintsChangedListener3 != null) {
            constraintsChangedListener3.preLayoutChange(i2, i6);
        }
        constraintSet2.applyTo(this.mConstraintLayout);
        ConstraintsChangedListener constraintsChangedListener4 = this.mConstraintsChangedListener;
        if (constraintsChangedListener4 != null) {
            constraintsChangedListener4.postLayoutChange(i2, i6);
        }
    }
}
