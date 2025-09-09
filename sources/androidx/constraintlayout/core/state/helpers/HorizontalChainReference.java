package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class HorizontalChainReference extends ChainReference {

    /* renamed from: androidx.constraintlayout.core.state.helpers.HorizontalChainReference$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2866a;

        static {
            int[] iArr = new int[State.Chain.values().length];
            f2866a = iArr;
            try {
                iArr[State.Chain.SPREAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2866a[State.Chain.SPREAD_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2866a[State.Chain.PACKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public HorizontalChainReference(State state) {
        super(state, State.Helper.HORIZONTAL_CHAIN);
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        Iterator it = this.f2838e0.iterator();
        while (it.hasNext()) {
            this.f2836c0.constraints(it.next()).clearHorizontal();
        }
        Iterator it2 = this.f2838e0.iterator();
        ConstraintReference constraintReference = null;
        ConstraintReference constraintReference2 = null;
        while (it2.hasNext()) {
            ConstraintReference constraintReferenceConstraints = this.f2836c0.constraints(it2.next());
            if (constraintReference2 == null) {
                Object obj = this.N;
                if (obj != null) {
                    constraintReferenceConstraints.startToStart(obj).margin(this.f2813l).marginGone(this.f2819r);
                } else {
                    Object obj2 = this.O;
                    if (obj2 != null) {
                        constraintReferenceConstraints.startToEnd(obj2).margin(this.f2813l).marginGone(this.f2819r);
                    } else {
                        Object obj3 = this.J;
                        if (obj3 != null) {
                            constraintReferenceConstraints.startToStart(obj3).margin(this.f2811j).marginGone(this.f2817p);
                        } else {
                            Object obj4 = this.K;
                            if (obj4 != null) {
                                constraintReferenceConstraints.startToEnd(obj4).margin(this.f2811j).marginGone(this.f2817p);
                            } else {
                                constraintReferenceConstraints.startToStart(State.PARENT);
                            }
                        }
                    }
                }
                constraintReference2 = constraintReferenceConstraints;
            }
            if (constraintReference != null) {
                constraintReference.endToStart(constraintReferenceConstraints.getKey());
                constraintReferenceConstraints.startToEnd(constraintReference.getKey());
            }
            constraintReference = constraintReferenceConstraints;
        }
        if (constraintReference != null) {
            Object obj5 = this.P;
            if (obj5 != null) {
                constraintReference.endToStart(obj5).margin(this.f2814m).marginGone(this.f2820s);
            } else {
                Object obj6 = this.Q;
                if (obj6 != null) {
                    constraintReference.endToEnd(obj6).margin(this.f2814m).marginGone(this.f2820s);
                } else {
                    Object obj7 = this.L;
                    if (obj7 != null) {
                        constraintReference.endToStart(obj7).margin(this.f2812k).marginGone(this.f2818q);
                    } else {
                        Object obj8 = this.M;
                        if (obj8 != null) {
                            constraintReference.endToEnd(obj8).margin(this.f2812k).marginGone(this.f2818q);
                        } else {
                            constraintReference.endToEnd(State.PARENT);
                        }
                    }
                }
            }
        }
        if (constraintReference2 == null) {
            return;
        }
        float f2 = this.f2863f0;
        if (f2 != 0.5f) {
            constraintReference2.horizontalBias(f2);
        }
        int i2 = AnonymousClass1.f2866a[this.f2864g0.ordinal()];
        if (i2 == 1) {
            constraintReference2.setHorizontalChainStyle(0);
        } else if (i2 == 2) {
            constraintReference2.setHorizontalChainStyle(1);
        } else {
            if (i2 != 3) {
                return;
            }
            constraintReference2.setHorizontalChainStyle(2);
        }
    }
}
