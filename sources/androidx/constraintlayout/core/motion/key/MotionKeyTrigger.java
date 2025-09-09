package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/* loaded from: classes.dex */
public class MotionKeyTrigger extends MotionKey {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final int TYPE_CROSS = 312;
    public static final int TYPE_NEGATIVE_CROSS = 310;
    public static final int TYPE_POSITIVE_CROSS = 309;
    public static final int TYPE_POST_LAYOUT = 304;
    public static final int TYPE_TRIGGER_COLLISION_ID = 307;
    public static final int TYPE_TRIGGER_COLLISION_VIEW = 306;
    public static final int TYPE_TRIGGER_ID = 308;
    public static final int TYPE_TRIGGER_RECEIVER = 311;
    public static final int TYPE_TRIGGER_SLACK = 305;
    public static final int TYPE_VIEW_TRANSITION_ON_CROSS = 301;
    public static final int TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS = 303;
    public static final int TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS = 302;
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";

    /* renamed from: c, reason: collision with root package name */
    float f2639c;

    /* renamed from: d, reason: collision with root package name */
    int f2640d;

    /* renamed from: e, reason: collision with root package name */
    int f2641e;

    /* renamed from: f, reason: collision with root package name */
    int f2642f;

    /* renamed from: g, reason: collision with root package name */
    FloatRect f2643g;

    /* renamed from: h, reason: collision with root package name */
    FloatRect f2644h;
    private boolean mFireCrossReset;
    private float mFireLastPos;
    private boolean mFireNegativeReset;
    private boolean mFirePositiveReset;
    private float mFireThreshold;
    private String mNegativeCross;
    private String mPositiveCross;
    private boolean mPostLayout;
    private int mTriggerCollisionId;
    private int mTriggerID;
    private int mTriggerReceiver;
    private int mCurveFit = -1;
    private String mCross = null;

    public MotionKeyTrigger() {
        int i2 = MotionKey.UNSET;
        this.mTriggerReceiver = i2;
        this.mNegativeCross = null;
        this.mPositiveCross = null;
        this.mTriggerID = i2;
        this.mTriggerCollisionId = i2;
        this.f2639c = 0.1f;
        this.mFireCrossReset = true;
        this.mFireNegativeReset = true;
        this.mFirePositiveReset = true;
        this.mFireThreshold = Float.NaN;
        this.mPostLayout = false;
        this.f2640d = i2;
        this.f2641e = i2;
        this.f2642f = i2;
        this.f2643g = new FloatRect();
        this.f2644h = new FloatRect();
        this.mType = 5;
        this.mCustom = new HashMap<>();
    }

    private void fireCustom(String str, MotionWidget motionWidget) {
        boolean z2 = str.length() == 1;
        if (!z2) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String str2 : this.mCustom.keySet()) {
            String lowerCase = str2.toLowerCase(Locale.ROOT);
            if (z2 || lowerCase.matches(str)) {
                CustomVariable customVariable = this.mCustom.get(str2);
                if (customVariable != null) {
                    customVariable.applyToWidget(motionWidget);
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> map) {
    }

    public void conditionallyFire(float f2, MotionWidget motionWidget) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x000b  */
    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getId(java.lang.String r3) {
        /*
            r2 = this;
            r3.hashCode()
            r0 = -1
            int r1 = r3.hashCode()
            switch(r1) {
                case -1594793529: goto L82;
                case -966421266: goto L77;
                case -786670827: goto L6c;
                case -648752941: goto L61;
                case -638126837: goto L56;
                case -76025313: goto L4b;
                case -9754574: goto L40;
                case 364489912: goto L35;
                case 1301930599: goto L28;
                case 1401391082: goto L1b;
                case 1535404999: goto Le;
                default: goto Lb;
            }
        Lb:
            r3 = r0
            goto L8c
        Le:
            java.lang.String r1 = "triggerReceiver"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L17
            goto Lb
        L17:
            r3 = 10
            goto L8c
        L1b:
            java.lang.String r1 = "postLayout"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L24
            goto Lb
        L24:
            r3 = 9
            goto L8c
        L28:
            java.lang.String r1 = "viewTransitionOnCross"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L31
            goto Lb
        L31:
            r3 = 8
            goto L8c
        L35:
            java.lang.String r1 = "triggerSlack"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L3e
            goto Lb
        L3e:
            r3 = 7
            goto L8c
        L40:
            java.lang.String r1 = "viewTransitionOnNegativeCross"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L49
            goto Lb
        L49:
            r3 = 6
            goto L8c
        L4b:
            java.lang.String r1 = "triggerCollisionView"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L54
            goto Lb
        L54:
            r3 = 5
            goto L8c
        L56:
            java.lang.String r1 = "negativeCross"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L5f
            goto Lb
        L5f:
            r3 = 4
            goto L8c
        L61:
            java.lang.String r1 = "triggerID"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L6a
            goto Lb
        L6a:
            r3 = 3
            goto L8c
        L6c:
            java.lang.String r1 = "triggerCollisionId"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L75
            goto Lb
        L75:
            r3 = 2
            goto L8c
        L77:
            java.lang.String r1 = "viewTransitionOnPositiveCross"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L80
            goto Lb
        L80:
            r3 = 1
            goto L8c
        L82:
            java.lang.String r1 = "positiveCross"
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L8b
            goto Lb
        L8b:
            r3 = 0
        L8c:
            switch(r3) {
                case 0: goto Lae;
                case 1: goto Lab;
                case 2: goto La8;
                case 3: goto La5;
                case 4: goto La2;
                case 5: goto L9f;
                case 6: goto L9c;
                case 7: goto L99;
                case 8: goto L96;
                case 9: goto L93;
                case 10: goto L90;
                default: goto L8f;
            }
        L8f:
            return r0
        L90:
            r3 = 311(0x137, float:4.36E-43)
            return r3
        L93:
            r3 = 304(0x130, float:4.26E-43)
            return r3
        L96:
            r3 = 301(0x12d, float:4.22E-43)
            return r3
        L99:
            r3 = 305(0x131, float:4.27E-43)
            return r3
        L9c:
            r3 = 303(0x12f, float:4.25E-43)
            return r3
        L9f:
            r3 = 306(0x132, float:4.29E-43)
            return r3
        La2:
            r3 = 310(0x136, float:4.34E-43)
            return r3
        La5:
            r3 = 308(0x134, float:4.32E-43)
            return r3
        La8:
            r3 = 307(0x133, float:4.3E-43)
            return r3
        Lab:
            r3 = 302(0x12e, float:4.23E-43)
            return r3
        Lae:
            r3 = 309(0x135, float:4.33E-43)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.key.MotionKeyTrigger.getId(java.lang.String):int");
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, int i3) {
        if (i2 == 307) {
            this.mTriggerCollisionId = i3;
            return true;
        }
        if (i2 == 308) {
            this.mTriggerID = b(Integer.valueOf(i3));
            return true;
        }
        if (i2 == 311) {
            this.mTriggerReceiver = i3;
            return true;
        }
        switch (i2) {
            case 301:
                this.f2642f = i3;
                return true;
            case 302:
                this.f2641e = i3;
                return true;
            case 303:
                this.f2640d = i3;
                return true;
            default:
                return super.setValue(i2, i3);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* renamed from: clone */
    public MotionKey mo3clone() {
        return new MotionKeyTrigger().copy((MotionKey) this);
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public MotionKeyTrigger copy(MotionKey motionKey) {
        super.copy(motionKey);
        MotionKeyTrigger motionKeyTrigger = (MotionKeyTrigger) motionKey;
        this.mCurveFit = motionKeyTrigger.mCurveFit;
        this.mCross = motionKeyTrigger.mCross;
        this.mTriggerReceiver = motionKeyTrigger.mTriggerReceiver;
        this.mNegativeCross = motionKeyTrigger.mNegativeCross;
        this.mPositiveCross = motionKeyTrigger.mPositiveCross;
        this.mTriggerID = motionKeyTrigger.mTriggerID;
        this.mTriggerCollisionId = motionKeyTrigger.mTriggerCollisionId;
        this.f2639c = motionKeyTrigger.f2639c;
        this.mFireCrossReset = motionKeyTrigger.mFireCrossReset;
        this.mFireNegativeReset = motionKeyTrigger.mFireNegativeReset;
        this.mFirePositiveReset = motionKeyTrigger.mFirePositiveReset;
        this.mFireThreshold = motionKeyTrigger.mFireThreshold;
        this.mFireLastPos = motionKeyTrigger.mFireLastPos;
        this.mPostLayout = motionKeyTrigger.mPostLayout;
        this.f2643g = motionKeyTrigger.f2643g;
        this.f2644h = motionKeyTrigger.f2644h;
        return this;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, float f2) {
        if (i2 != 305) {
            return super.setValue(i2, f2);
        }
        this.f2639c = f2;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, String str) {
        if (i2 == 309) {
            this.mPositiveCross = str;
            return true;
        }
        if (i2 == 310) {
            this.mNegativeCross = str;
            return true;
        }
        if (i2 != 312) {
            return super.setValue(i2, str);
        }
        this.mCross = str;
        return true;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int i2, boolean z2) {
        if (i2 != 304) {
            return super.setValue(i2, z2);
        }
        this.mPostLayout = z2;
        return true;
    }
}
