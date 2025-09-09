package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.core.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.core.state.helpers.BarrierReference;
import androidx.constraintlayout.core.state.helpers.GuidelineReference;
import androidx.constraintlayout.core.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.core.state.helpers.VerticalChainReference;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class State {
    public static final Integer PARENT = 0;

    /* renamed from: a, reason: collision with root package name */
    protected HashMap f2839a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    protected HashMap f2840b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    HashMap f2841c = new HashMap();
    public final ConstraintReference mParent;
    private int numHelpers;

    /* renamed from: androidx.constraintlayout.core.state.State$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f2842a;

        static {
            int[] iArr = new int[Helper.values().length];
            f2842a = iArr;
            try {
                iArr[Helper.HORIZONTAL_CHAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2842a[Helper.VERTICAL_CHAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2842a[Helper.ALIGN_HORIZONTALLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2842a[Helper.ALIGN_VERTICALLY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f2842a[Helper.BARRIER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Chain {
        SPREAD,
        SPREAD_INSIDE,
        PACKED
    }

    public enum Constraint {
        LEFT_TO_LEFT,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        RIGHT_TO_RIGHT,
        START_TO_START,
        START_TO_END,
        END_TO_START,
        END_TO_END,
        TOP_TO_TOP,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        BOTTOM_TO_BOTTOM,
        BASELINE_TO_BASELINE,
        BASELINE_TO_TOP,
        BASELINE_TO_BOTTOM,
        CENTER_HORIZONTALLY,
        CENTER_VERTICALLY,
        CIRCULAR_CONSTRAINT
    }

    public enum Direction {
        LEFT,
        RIGHT,
        START,
        END,
        TOP,
        BOTTOM
    }

    public enum Helper {
        HORIZONTAL_CHAIN,
        VERTICAL_CHAIN,
        ALIGN_HORIZONTALLY,
        ALIGN_VERTICALLY,
        BARRIER,
        LAYER,
        FLOW
    }

    public State() {
        ConstraintReference constraintReference = new ConstraintReference(this);
        this.mParent = constraintReference;
        this.numHelpers = 0;
        this.f2839a.put(PARENT, constraintReference);
    }

    private String createHelperKey() {
        StringBuilder sb = new StringBuilder();
        sb.append("__HELPER_KEY_");
        int i2 = this.numHelpers;
        this.numHelpers = i2 + 1;
        sb.append(i2);
        sb.append("__");
        return sb.toString();
    }

    Reference a(Object obj) {
        return (Reference) this.f2839a.get(obj);
    }

    public void apply(ConstraintWidgetContainer constraintWidgetContainer) {
        HelperReference helperReference;
        HelperWidget helperWidget;
        HelperWidget helperWidget2;
        constraintWidgetContainer.removeAllChildren();
        this.mParent.getWidth().apply(this, constraintWidgetContainer, 0);
        this.mParent.getHeight().apply(this, constraintWidgetContainer, 1);
        for (Object obj : this.f2840b.keySet()) {
            HelperWidget helperWidget3 = ((HelperReference) this.f2840b.get(obj)).getHelperWidget();
            if (helperWidget3 != null) {
                Reference referenceConstraints = (Reference) this.f2839a.get(obj);
                if (referenceConstraints == null) {
                    referenceConstraints = constraints(obj);
                }
                referenceConstraints.setConstraintWidget(helperWidget3);
            }
        }
        for (Object obj2 : this.f2839a.keySet()) {
            Reference reference = (Reference) this.f2839a.get(obj2);
            if (reference != this.mParent && (reference.getFacade() instanceof HelperReference) && (helperWidget2 = ((HelperReference) reference.getFacade()).getHelperWidget()) != null) {
                Reference referenceConstraints2 = (Reference) this.f2839a.get(obj2);
                if (referenceConstraints2 == null) {
                    referenceConstraints2 = constraints(obj2);
                }
                referenceConstraints2.setConstraintWidget(helperWidget2);
            }
        }
        Iterator it = this.f2839a.keySet().iterator();
        while (it.hasNext()) {
            Reference reference2 = (Reference) this.f2839a.get(it.next());
            if (reference2 != this.mParent) {
                ConstraintWidget constraintWidget = reference2.getConstraintWidget();
                constraintWidget.setDebugName(reference2.getKey().toString());
                constraintWidget.setParent(null);
                if (reference2.getFacade() instanceof GuidelineReference) {
                    reference2.apply();
                }
                constraintWidgetContainer.add(constraintWidget);
            } else {
                reference2.setConstraintWidget(constraintWidgetContainer);
            }
        }
        Iterator it2 = this.f2840b.keySet().iterator();
        while (it2.hasNext()) {
            HelperReference helperReference2 = (HelperReference) this.f2840b.get(it2.next());
            if (helperReference2.getHelperWidget() != null) {
                Iterator it3 = helperReference2.f2838e0.iterator();
                while (it3.hasNext()) {
                    helperReference2.getHelperWidget().add(((Reference) this.f2839a.get(it3.next())).getConstraintWidget());
                }
                helperReference2.apply();
            } else {
                helperReference2.apply();
            }
        }
        Iterator it4 = this.f2839a.keySet().iterator();
        while (it4.hasNext()) {
            Reference reference3 = (Reference) this.f2839a.get(it4.next());
            if (reference3 != this.mParent && (reference3.getFacade() instanceof HelperReference) && (helperWidget = (helperReference = (HelperReference) reference3.getFacade()).getHelperWidget()) != null) {
                Iterator it5 = helperReference.f2838e0.iterator();
                while (it5.hasNext()) {
                    Object next = it5.next();
                    Reference reference4 = (Reference) this.f2839a.get(next);
                    if (reference4 != null) {
                        helperWidget.add(reference4.getConstraintWidget());
                    } else if (next instanceof Reference) {
                        helperWidget.add(((Reference) next).getConstraintWidget());
                    } else {
                        System.out.println("couldn't find reference for " + next);
                    }
                }
                reference3.apply();
            }
        }
        for (Object obj3 : this.f2839a.keySet()) {
            Reference reference5 = (Reference) this.f2839a.get(obj3);
            reference5.apply();
            ConstraintWidget constraintWidget2 = reference5.getConstraintWidget();
            if (constraintWidget2 != null && obj3 != null) {
                constraintWidget2.stringId = obj3.toString();
            }
        }
    }

    public BarrierReference barrier(Object obj, Direction direction) {
        ConstraintReference constraintReferenceConstraints = constraints(obj);
        if (constraintReferenceConstraints.getFacade() == null || !(constraintReferenceConstraints.getFacade() instanceof BarrierReference)) {
            BarrierReference barrierReference = new BarrierReference(this);
            barrierReference.setBarrierDirection(direction);
            constraintReferenceConstraints.setFacade(barrierReference);
        }
        return (BarrierReference) constraintReferenceConstraints.getFacade();
    }

    public AlignHorizontallyReference centerHorizontally(Object... objArr) {
        AlignHorizontallyReference alignHorizontallyReference = (AlignHorizontallyReference) helper(null, Helper.ALIGN_HORIZONTALLY);
        alignHorizontallyReference.add(objArr);
        return alignHorizontallyReference;
    }

    public AlignVerticallyReference centerVertically(Object... objArr) {
        AlignVerticallyReference alignVerticallyReference = (AlignVerticallyReference) helper(null, Helper.ALIGN_VERTICALLY);
        alignVerticallyReference.add(objArr);
        return alignVerticallyReference;
    }

    public ConstraintReference constraints(Object obj) {
        Reference referenceCreateConstraintReference = (Reference) this.f2839a.get(obj);
        if (referenceCreateConstraintReference == null) {
            referenceCreateConstraintReference = createConstraintReference(obj);
            this.f2839a.put(obj, referenceCreateConstraintReference);
            referenceCreateConstraintReference.setKey(obj);
        }
        if (referenceCreateConstraintReference instanceof ConstraintReference) {
            return (ConstraintReference) referenceCreateConstraintReference;
        }
        return null;
    }

    public int convertDimension(Object obj) {
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }

    public ConstraintReference createConstraintReference(Object obj) {
        return new ConstraintReference(this);
    }

    public void directMapping() {
        for (Object obj : this.f2839a.keySet()) {
            ConstraintReference constraintReferenceConstraints = constraints(obj);
            if (constraintReferenceConstraints instanceof ConstraintReference) {
                constraintReferenceConstraints.setView(obj);
            }
        }
    }

    public ArrayList<String> getIdsForTag(String str) {
        if (this.f2841c.containsKey(str)) {
            return (ArrayList) this.f2841c.get(str);
        }
        return null;
    }

    public GuidelineReference guideline(Object obj, int i2) {
        ConstraintReference constraintReferenceConstraints = constraints(obj);
        if (constraintReferenceConstraints.getFacade() == null || !(constraintReferenceConstraints.getFacade() instanceof GuidelineReference)) {
            GuidelineReference guidelineReference = new GuidelineReference(this);
            guidelineReference.setOrientation(i2);
            guidelineReference.setKey(obj);
            constraintReferenceConstraints.setFacade(guidelineReference);
        }
        return (GuidelineReference) constraintReferenceConstraints.getFacade();
    }

    public State height(Dimension dimension) {
        return setHeight(dimension);
    }

    public HelperReference helper(Object obj, Helper helper) {
        HelperReference horizontalChainReference;
        if (obj == null) {
            obj = createHelperKey();
        }
        HelperReference helperReference = (HelperReference) this.f2840b.get(obj);
        if (helperReference == null) {
            int i2 = AnonymousClass1.f2842a[helper.ordinal()];
            if (i2 == 1) {
                horizontalChainReference = new HorizontalChainReference(this);
            } else if (i2 == 2) {
                horizontalChainReference = new VerticalChainReference(this);
            } else if (i2 == 3) {
                horizontalChainReference = new AlignHorizontallyReference(this);
            } else if (i2 == 4) {
                horizontalChainReference = new AlignVerticallyReference(this);
            } else if (i2 != 5) {
                helperReference = new HelperReference(this, helper);
                helperReference.setKey(obj);
                this.f2840b.put(obj, helperReference);
            } else {
                horizontalChainReference = new BarrierReference(this);
            }
            helperReference = horizontalChainReference;
            helperReference.setKey(obj);
            this.f2840b.put(obj, helperReference);
        }
        return helperReference;
    }

    public HorizontalChainReference horizontalChain() {
        return (HorizontalChainReference) helper(null, Helper.HORIZONTAL_CHAIN);
    }

    public GuidelineReference horizontalGuideline(Object obj) {
        return guideline(obj, 0);
    }

    public void map(Object obj, Object obj2) {
        ConstraintReference constraintReferenceConstraints = constraints(obj);
        if (constraintReferenceConstraints instanceof ConstraintReference) {
            constraintReferenceConstraints.setView(obj2);
        }
    }

    public void reset() {
        this.f2840b.clear();
        this.f2841c.clear();
    }

    public boolean sameFixedHeight(int i2) {
        return this.mParent.getHeight().equalsFixedValue(i2);
    }

    public boolean sameFixedWidth(int i2) {
        return this.mParent.getWidth().equalsFixedValue(i2);
    }

    public State setHeight(Dimension dimension) {
        this.mParent.setHeight(dimension);
        return this;
    }

    public void setTag(String str, String str2) {
        ArrayList arrayList;
        ConstraintReference constraintReferenceConstraints = constraints(str);
        if (constraintReferenceConstraints instanceof ConstraintReference) {
            constraintReferenceConstraints.setTag(str2);
            if (this.f2841c.containsKey(str2)) {
                arrayList = (ArrayList) this.f2841c.get(str2);
            } else {
                arrayList = new ArrayList();
                this.f2841c.put(str2, arrayList);
            }
            arrayList.add(str);
        }
    }

    public State setWidth(Dimension dimension) {
        this.mParent.setWidth(dimension);
        return this;
    }

    public VerticalChainReference verticalChain() {
        return (VerticalChainReference) helper(null, Helper.VERTICAL_CHAIN);
    }

    public GuidelineReference verticalGuideline(Object obj) {
        return guideline(obj, 1);
    }

    public State width(Dimension dimension) {
        return setWidth(dimension);
    }

    public HorizontalChainReference horizontalChain(Object... objArr) {
        HorizontalChainReference horizontalChainReference = (HorizontalChainReference) helper(null, Helper.HORIZONTAL_CHAIN);
        horizontalChainReference.add(objArr);
        return horizontalChainReference;
    }

    public VerticalChainReference verticalChain(Object... objArr) {
        VerticalChainReference verticalChainReference = (VerticalChainReference) helper(null, Helper.VERTICAL_CHAIN);
        verticalChainReference.add(objArr);
        return verticalChainReference;
    }
}
