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
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class StateSet {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";

    /* renamed from: a, reason: collision with root package name */
    int f3327a = -1;

    /* renamed from: b, reason: collision with root package name */
    int f3328b = -1;

    /* renamed from: c, reason: collision with root package name */
    int f3329c = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    static class State {

        /* renamed from: a, reason: collision with root package name */
        int f3330a;

        /* renamed from: b, reason: collision with root package name */
        ArrayList f3331b = new ArrayList();

        /* renamed from: c, reason: collision with root package name */
        int f3332c;

        /* renamed from: d, reason: collision with root package name */
        boolean f3333d;

        public State(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.f3332c = -1;
            this.f3333d = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.State_android_id) {
                    this.f3330a = typedArrayObtainStyledAttributes.getResourceId(index, this.f3330a);
                } else if (index == R.styleable.State_constraints) {
                    this.f3332c = typedArrayObtainStyledAttributes.getResourceId(index, this.f3332c);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.f3332c);
                    context.getResources().getResourceName(this.f3332c);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        this.f3333d = true;
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        void a(Variant variant) {
            this.f3331b.add(variant);
        }

        public int findMatch(float f2, float f3) {
            for (int i2 = 0; i2 < this.f3331b.size(); i2++) {
                if (((Variant) this.f3331b.get(i2)).a(f2, f3)) {
                    return i2;
                }
            }
            return -1;
        }
    }

    static class Variant {

        /* renamed from: a, reason: collision with root package name */
        float f3334a;

        /* renamed from: b, reason: collision with root package name */
        float f3335b;

        /* renamed from: c, reason: collision with root package name */
        float f3336c;

        /* renamed from: d, reason: collision with root package name */
        float f3337d;

        /* renamed from: e, reason: collision with root package name */
        int f3338e;

        /* renamed from: f, reason: collision with root package name */
        boolean f3339f;

        public Variant(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.f3334a = Float.NaN;
            this.f3335b = Float.NaN;
            this.f3336c = Float.NaN;
            this.f3337d = Float.NaN;
            this.f3338e = -1;
            this.f3339f = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.Variant_constraints) {
                    this.f3338e = typedArrayObtainStyledAttributes.getResourceId(index, this.f3338e);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.f3338e);
                    context.getResources().getResourceName(this.f3338e);
                    if (TtmlNode.TAG_LAYOUT.equals(resourceTypeName)) {
                        this.f3339f = true;
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.f3337d = typedArrayObtainStyledAttributes.getDimension(index, this.f3337d);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.f3335b = typedArrayObtainStyledAttributes.getDimension(index, this.f3335b);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.f3336c = typedArrayObtainStyledAttributes.getDimension(index, this.f3336c);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.f3334a = typedArrayObtainStyledAttributes.getDimension(index, this.f3334a);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        boolean a(float f2, float f3) {
            if (!Float.isNaN(this.f3334a) && f2 < this.f3334a) {
                return false;
            }
            if (!Float.isNaN(this.f3335b) && f3 < this.f3335b) {
                return false;
            }
            if (Float.isNaN(this.f3336c) || f2 <= this.f3336c) {
                return Float.isNaN(this.f3337d) || f3 <= this.f3337d;
            }
            return false;
        }
    }

    public StateSet(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        load(context, xmlPullParser);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r9, org.xmlpull.v1.XmlPullParser r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r8 = this;
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r10)
            int[] r1 = androidx.constraintlayout.widget.R.styleable.StateSet
            android.content.res.TypedArray r0 = r9.obtainStyledAttributes(r0, r1)
            int r1 = r0.getIndexCount()
            r2 = 0
            r3 = r2
        L10:
            if (r3 >= r1) goto L25
            int r4 = r0.getIndex(r3)
            int r5 = androidx.constraintlayout.widget.R.styleable.StateSet_defaultState
            if (r4 != r5) goto L22
            int r5 = r8.f3327a
            int r4 = r0.getResourceId(r4, r5)
            r8.f3327a = r4
        L22:
            int r3 = r3 + 1
            goto L10
        L25:
            r0.recycle()
            int r0 = r10.getEventType()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            r1 = 0
        L2d:
            r3 = 1
            if (r0 == r3) goto La9
            if (r0 == 0) goto L9a
            java.lang.String r4 = "StateSet"
            r5 = 3
            r6 = 2
            if (r0 == r6) goto L4b
            if (r0 == r5) goto L3c
            goto L9d
        L3c:
            java.lang.String r0 = r10.getName()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            boolean r0 = r4.equals(r0)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r0 == 0) goto L9d
            return
        L47:
            r9 = move-exception
            goto La2
        L49:
            r9 = move-exception
            goto La6
        L4b:
            java.lang.String r0 = r10.getName()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            int r7 = r0.hashCode()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            switch(r7) {
                case 80204913: goto L72;
                case 1301459538: goto L68;
                case 1382829617: goto L61;
                case 1901439077: goto L57;
                default: goto L56;
            }     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
        L56:
            goto L7c
        L57:
            java.lang.String r3 = "Variant"
            boolean r0 = r0.equals(r3)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r0 == 0) goto L7c
            r3 = r5
            goto L7d
        L61:
            boolean r0 = r0.equals(r4)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r0 == 0) goto L7c
            goto L7d
        L68:
            java.lang.String r3 = "LayoutDescription"
            boolean r0 = r0.equals(r3)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r0 == 0) goto L7c
            r3 = r2
            goto L7d
        L72:
            java.lang.String r3 = "State"
            boolean r0 = r0.equals(r3)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r0 == 0) goto L7c
            r3 = r6
            goto L7d
        L7c:
            r3 = -1
        L7d:
            if (r3 == r6) goto L8d
            if (r3 == r5) goto L82
            goto L9d
        L82:
            androidx.constraintlayout.widget.StateSet$Variant r0 = new androidx.constraintlayout.widget.StateSet$Variant     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            r0.<init>(r9, r10)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            if (r1 == 0) goto L9d
            r1.a(r0)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            goto L9d
        L8d:
            androidx.constraintlayout.widget.StateSet$State r1 = new androidx.constraintlayout.widget.StateSet$State     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            r1.<init>(r9, r10)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            android.util.SparseArray<androidx.constraintlayout.widget.StateSet$State> r0 = r8.mStateList     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            int r3 = r1.f3330a     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            r0.put(r3, r1)     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            goto L9d
        L9a:
            r10.getName()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
        L9d:
            int r0 = r10.next()     // Catch: java.io.IOException -> L47 org.xmlpull.v1.XmlPullParserException -> L49
            goto L2d
        La2:
            r9.printStackTrace()
            goto La9
        La6:
            r9.printStackTrace()
        La9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public int convertToConstraintSet(int i2, int i3, float f2, float f3) {
        State state = this.mStateList.get(i3);
        if (state == null) {
            return i3;
        }
        if (f2 == -1.0f || f3 == -1.0f) {
            if (state.f3332c == i2) {
                return i2;
            }
            Iterator it = state.f3331b.iterator();
            while (it.hasNext()) {
                if (i2 == ((Variant) it.next()).f3338e) {
                    return i2;
                }
            }
            return state.f3332c;
        }
        Iterator it2 = state.f3331b.iterator();
        Variant variant = null;
        while (it2.hasNext()) {
            Variant variant2 = (Variant) it2.next();
            if (variant2.a(f2, f3)) {
                if (i2 == variant2.f3338e) {
                    return i2;
                }
                variant = variant2;
            }
        }
        return variant != null ? variant.f3338e : state.f3332c;
    }

    public boolean needsToChange(int i2, float f2, float f3) {
        int i3 = this.f3328b;
        if (i3 != i2) {
            return true;
        }
        State stateValueAt = i2 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i3);
        int i4 = this.f3329c;
        return (i4 == -1 || !((Variant) stateValueAt.f3331b.get(i4)).a(f2, f3)) && this.f3329c != stateValueAt.findMatch(f2, f3);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public int stateGetConstraintID(int i2, int i3, int i4) {
        return updateConstraints(-1, i2, i3, i4);
    }

    public int updateConstraints(int i2, int i3, float f2, float f3) {
        int iFindMatch;
        if (i2 == i3) {
            State stateValueAt = i3 == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(this.f3328b);
            if (stateValueAt == null) {
                return -1;
            }
            return ((this.f3329c == -1 || !((Variant) stateValueAt.f3331b.get(i2)).a(f2, f3)) && i2 != (iFindMatch = stateValueAt.findMatch(f2, f3))) ? iFindMatch == -1 ? stateValueAt.f3332c : ((Variant) stateValueAt.f3331b.get(iFindMatch)).f3338e : i2;
        }
        State state = this.mStateList.get(i3);
        if (state == null) {
            return -1;
        }
        int iFindMatch2 = state.findMatch(f2, f3);
        return iFindMatch2 == -1 ? state.f3332c : ((Variant) state.f3331b.get(iFindMatch2)).f3338e;
    }
}
