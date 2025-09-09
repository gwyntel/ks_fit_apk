package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class ConstraintHelper extends View {

    /* renamed from: a, reason: collision with root package name */
    protected int[] f3218a;

    /* renamed from: b, reason: collision with root package name */
    protected int f3219b;

    /* renamed from: c, reason: collision with root package name */
    protected Context f3220c;

    /* renamed from: d, reason: collision with root package name */
    protected Helper f3221d;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f3222e;

    /* renamed from: f, reason: collision with root package name */
    protected String f3223f;

    /* renamed from: g, reason: collision with root package name */
    protected String f3224g;

    /* renamed from: h, reason: collision with root package name */
    protected HashMap f3225h;
    private View[] mViews;

    public ConstraintHelper(Context context) {
        super(context);
        this.f3218a = new int[32];
        this.f3222e = false;
        this.mViews = null;
        this.f3225h = new HashMap();
        this.f3220c = context;
        e(null);
    }

    private void addID(String str) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        if (str == null || str.length() == 0 || this.f3220c == null) {
            return;
        }
        String strTrim = str.trim();
        if (getParent() instanceof ConstraintLayout) {
        }
        int iFindId = findId(strTrim);
        if (iFindId != 0) {
            this.f3225h.put(Integer.valueOf(iFindId), strTrim);
            addRscID(iFindId);
            return;
        }
        Log.w("ConstraintHelper", "Could not find id of \"" + strTrim + "\"");
    }

    private void addRscID(int i2) {
        if (i2 == getId()) {
            return;
        }
        int i3 = this.f3219b + 1;
        int[] iArr = this.f3218a;
        if (i3 > iArr.length) {
            this.f3218a = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.f3218a;
        int i4 = this.f3219b;
        iArr2[i4] = i2;
        this.f3219b = i4 + 1;
    }

    private void addTag(String str) {
        if (str == null || str.length() == 0 || this.f3220c == null) {
            return;
        }
        String strTrim = str.trim();
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        if (constraintLayout == null) {
            Log.w("ConstraintHelper", "Parent not a ConstraintLayout");
            return;
        }
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if ((layoutParams instanceof ConstraintLayout.LayoutParams) && strTrim.equals(((ConstraintLayout.LayoutParams) layoutParams).constraintTag)) {
                if (childAt.getId() == -1) {
                    Log.w("ConstraintHelper", "to use ConstraintTag view " + childAt.getClass().getSimpleName() + " must have an ID");
                } else {
                    addRscID(childAt.getId());
                }
            }
        }
    }

    private int[] convertReferenceString(View view, String str) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        String[] strArrSplit = str.split(",");
        view.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i2 = 0;
        for (String str2 : strArrSplit) {
            int iFindId = findId(str2.trim());
            if (iFindId != 0) {
                iArr[i2] = iFindId;
                i2++;
            }
        }
        return i2 != strArrSplit.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    private int findId(String str) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        ConstraintLayout constraintLayout = getParent() instanceof ConstraintLayout ? (ConstraintLayout) getParent() : null;
        int iFindId = 0;
        if (isInEditMode() && constraintLayout != null) {
            Object designInformation = constraintLayout.getDesignInformation(0, str);
            if (designInformation instanceof Integer) {
                iFindId = ((Integer) designInformation).intValue();
            }
        }
        if (iFindId == 0 && constraintLayout != null) {
            iFindId = findId(constraintLayout, str);
        }
        if (iFindId == 0) {
            try {
                iFindId = R.id.class.getField(str).getInt(null);
            } catch (Exception unused) {
            }
        }
        return iFindId == 0 ? this.f3220c.getResources().getIdentifier(str, "id", this.f3220c.getPackageName()) : iFindId;
    }

    protected void a() {
        ViewParent parent = getParent();
        if (parent == null || !(parent instanceof ConstraintLayout)) {
            return;
        }
        b((ConstraintLayout) parent);
    }

    public void addView(View view) {
        if (view == this) {
            return;
        }
        if (view.getId() == -1) {
            Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have an id");
        } else {
            if (view.getParent() == null) {
                Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have a parent");
                return;
            }
            this.f3223f = null;
            addRscID(view.getId());
            requestLayout();
        }
    }

    protected void b(ConstraintLayout constraintLayout) {
        int visibility = getVisibility();
        float elevation = getElevation();
        for (int i2 = 0; i2 < this.f3219b; i2++) {
            View viewById = constraintLayout.getViewById(this.f3218a[i2]);
            if (viewById != null) {
                viewById.setVisibility(visibility);
                if (elevation > 0.0f) {
                    viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                }
            }
        }
    }

    protected void c(ConstraintLayout constraintLayout) {
    }

    public boolean containsId(int i2) {
        for (int i3 : this.f3218a) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    protected View[] d(ConstraintLayout constraintLayout) {
        View[] viewArr = this.mViews;
        if (viewArr == null || viewArr.length != this.f3219b) {
            this.mViews = new View[this.f3219b];
        }
        for (int i2 = 0; i2 < this.f3219b; i2++) {
            this.mViews[i2] = constraintLayout.getViewById(this.f3218a[i2]);
        }
        return this.mViews;
    }

    protected void e(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.f3223f = string;
                    setIds(string);
                } else if (index == R.styleable.ConstraintLayout_Layout_constraint_referenced_tags) {
                    String string2 = typedArrayObtainStyledAttributes.getString(index);
                    this.f3224g = string2;
                    setReferenceTags(string2);
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.f3218a, this.f3219b);
    }

    public int indexFromId(int i2) {
        int i3 = -1;
        for (int i4 : this.f3218a) {
            i3++;
            if (i4 == i2) {
                return i3;
            }
        }
        return i3;
    }

    public void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        ConstraintSet.Layout layout = constraint.layout;
        int[] iArr = layout.mReferenceIds;
        if (iArr != null) {
            setReferencedIds(iArr);
        } else {
            String str = layout.mReferenceIdString;
            if (str != null) {
                if (str.length() > 0) {
                    ConstraintSet.Layout layout2 = constraint.layout;
                    layout2.mReferenceIds = convertReferenceString(this, layout2.mReferenceIdString);
                } else {
                    constraint.layout.mReferenceIds = null;
                }
            }
        }
        if (helperWidget == null) {
            return;
        }
        helperWidget.removeAllIds();
        if (constraint.layout.mReferenceIds == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            int[] iArr2 = constraint.layout.mReferenceIds;
            if (i2 >= iArr2.length) {
                return;
            }
            ConstraintWidget constraintWidget = sparseArray.get(iArr2[i2]);
            if (constraintWidget != null) {
                helperWidget.add(constraintWidget);
            }
            i2++;
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        super.onAttachedToWindow();
        String str = this.f3223f;
        if (str != null) {
            setIds(str);
        }
        String str2 = this.f3224g;
        if (str2 != null) {
            setReferenceTags(str2);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
    }

    @Override // android.view.View
    protected void onMeasure(int i2, int i3) {
        if (this.f3222e) {
            super.onMeasure(i2, i3);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public int removeView(View view) {
        int i2;
        int id = view.getId();
        int i3 = -1;
        if (id == -1) {
            return -1;
        }
        this.f3223f = null;
        int i4 = 0;
        while (true) {
            if (i4 >= this.f3219b) {
                break;
            }
            if (this.f3218a[i4] == id) {
                int i5 = i4;
                while (true) {
                    i2 = this.f3219b;
                    if (i5 >= i2 - 1) {
                        break;
                    }
                    int[] iArr = this.f3218a;
                    int i6 = i5 + 1;
                    iArr[i5] = iArr[i6];
                    i5 = i6;
                }
                this.f3218a[i2 - 1] = 0;
                this.f3219b = i2 - 1;
                i3 = i4;
            } else {
                i4++;
            }
        }
        requestLayout();
        return i3;
    }

    public void resolveRtl(ConstraintWidget constraintWidget, boolean z2) {
    }

    protected void setIds(String str) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        this.f3223f = str;
        if (str == null) {
            return;
        }
        int i2 = 0;
        this.f3219b = 0;
        while (true) {
            int iIndexOf = str.indexOf(44, i2);
            if (iIndexOf == -1) {
                addID(str.substring(i2));
                return;
            } else {
                addID(str.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    protected void setReferenceTags(String str) {
        this.f3224g = str;
        if (str == null) {
            return;
        }
        int i2 = 0;
        this.f3219b = 0;
        while (true) {
            int iIndexOf = str.indexOf(44, i2);
            if (iIndexOf == -1) {
                addTag(str.substring(i2));
                return;
            } else {
                addTag(str.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    public void setReferencedIds(int[] iArr) {
        this.f3223f = null;
        this.f3219b = 0;
        for (int i2 : iArr) {
            addRscID(i2);
        }
    }

    @Override // android.view.View
    public void setTag(int i2, Object obj) {
        super.setTag(i2, obj);
        if (obj == null && this.f3223f == null) {
            addRscID(i2);
        }
    }

    public void updatePostConstraints(ConstraintLayout constraintLayout) {
    }

    public void updatePostLayout(ConstraintLayout constraintLayout) {
    }

    public void updatePostMeasure(ConstraintLayout constraintLayout) {
    }

    public void updatePreDraw(ConstraintLayout constraintLayout) {
    }

    public void updatePreLayout(ConstraintLayout constraintLayout) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException {
        String str;
        int iFindId;
        if (isInEditMode()) {
            setIds(this.f3223f);
        }
        Helper helper = this.f3221d;
        if (helper == null) {
            return;
        }
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.f3219b; i2++) {
            int i3 = this.f3218a[i2];
            View viewById = constraintLayout.getViewById(i3);
            if (viewById == null && (iFindId = findId(constraintLayout, (str = (String) this.f3225h.get(Integer.valueOf(i3))))) != 0) {
                this.f3218a[i2] = iFindId;
                this.f3225h.put(Integer.valueOf(iFindId), str);
                viewById = constraintLayout.getViewById(iFindId);
            }
            if (viewById != null) {
                this.f3221d.add(constraintLayout.getViewWidget(viewById));
            }
        }
        this.f3221d.updateConstraints(constraintLayout.f3227b);
    }

    public void validateParams() {
        if (this.f3221d == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).f3257v = (ConstraintWidget) this.f3221d;
        }
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f3218a = new int[32];
        this.f3222e = false;
        this.mViews = null;
        this.f3225h = new HashMap();
        this.f3220c = context;
        e(attributeSet);
    }

    private int findId(ConstraintLayout constraintLayout, String str) throws Resources.NotFoundException {
        Resources resources;
        String resourceEntryName;
        if (str == null || constraintLayout == null || (resources = this.f3220c.getResources()) == null) {
            return 0;
        }
        int childCount = constraintLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = constraintLayout.getChildAt(i2);
            if (childAt.getId() != -1) {
                try {
                    resourceEntryName = resources.getResourceEntryName(childAt.getId());
                } catch (Resources.NotFoundException unused) {
                    resourceEntryName = null;
                }
                if (str.equals(resourceEntryName)) {
                    return childAt.getId();
                }
            }
        }
        return 0;
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f3218a = new int[32];
        this.f3222e = false;
        this.mViews = null;
        this.f3225h = new HashMap();
        this.f3220c = context;
        e(attributeSet);
    }

    public void updatePreLayout(ConstraintWidgetContainer constraintWidgetContainer, Helper helper, SparseArray<ConstraintWidget> sparseArray) {
        helper.removeAllIds();
        for (int i2 = 0; i2 < this.f3219b; i2++) {
            helper.add(sparseArray.get(this.f3218a[i2]));
        }
    }
}
