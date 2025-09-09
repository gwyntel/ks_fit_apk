package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

/* loaded from: classes.dex */
public class VerticalChainReference extends ChainReference {

    /* renamed from: androidx.constraintlayout.core.state.helpers.VerticalChainReference$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2867a;

        static {
            int[] iArr = new int[State.Chain.values().length];
            f2867a = iArr;
            try {
                iArr[State.Chain.SPREAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2867a[State.Chain.SPREAD_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2867a[State.Chain.PACKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public VerticalChainReference(State state) {
        super(state, State.Helper.VERTICAL_CHAIN);
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        Iterator it = this.f2838e0.iterator();
        while (it.hasNext()) {
            this.f2836c0.constraints(it.next()).clearVertical();
        }
        Iterator it2 = this.f2838e0.iterator();
        ConstraintReference constraintReference = null;
        ConstraintReference constraintReference2 = null;
        while (it2.hasNext()) {
            ConstraintReference constraintReferenceConstraints = this.f2836c0.constraints(it2.next());
            if (constraintReference2 == null) {
                Object obj = this.R;
                if (obj != null) {
                    constraintReferenceConstraints.topToTop(obj).margin(this.f2815n).marginGone(this.f2821t);
                } else {
                    Object obj2 = this.S;
                    if (obj2 != null) {
                        constraintReferenceConstraints.topToBottom(obj2).margin(this.f2815n).marginGone(this.f2821t);
                    } else {
                        constraintReferenceConstraints.topToTop(State.PARENT);
                    }
                }
                constraintReference2 = constraintReferenceConstraints;
            }
            if (constraintReference != null) {
                constraintReference.bottomToTop(constraintReferenceConstraints.getKey());
                constraintReferenceConstraints.topToBottom(constraintReference.getKey());
            }
            constraintReference = constraintReferenceConstraints;
        }
        if (constraintReference != null) {
            Object obj3 = this.T;
            if (obj3 != null) {
                constraintReference.bottomToTop(obj3).margin(this.f2816o).marginGone(this.f2822u);
            } else {
                Object obj4 = this.U;
                if (obj4 != null) {
                    constraintReference.bottomToBottom(obj4).margin(this.f2816o).marginGone(this.f2822u);
                } else {
                    constraintReference.bottomToBottom(State.PARENT);
                }
            }
        }
        if (constraintReference2 == null) {
            return;
        }
        float f2 = this.f2863f0;
        if (f2 != 0.5f) {
            constraintReference2.verticalBias(f2);
        }
        int i2 = AnonymousClass1.f2867a[this.f2864g0.ordinal()];
        if (i2 == 1) {
            constraintReference2.setVerticalChainStyle(0);
        } else if (i2 == 2) {
            constraintReference2.setVerticalChainStyle(1);
        } else {
            if (i2 != 3) {
                return;
            }
            constraintReference2.setVerticalChainStyle(2);
        }
    }
}
