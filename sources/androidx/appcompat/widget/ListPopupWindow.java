package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.AttrRes;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    private static final boolean DEBUG = false;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private static Method sGetMaxAvailableHeightMethod;
    private static Method sSetClipToWindowEnabledMethod;
    private static Method sSetEpicenterBoundsMethod;

    /* renamed from: a, reason: collision with root package name */
    DropDownListView f2270a;

    /* renamed from: b, reason: collision with root package name */
    int f2271b;

    /* renamed from: c, reason: collision with root package name */
    final ResizePopupRunnable f2272c;

    /* renamed from: d, reason: collision with root package name */
    final Handler f2273d;

    /* renamed from: e, reason: collision with root package name */
    PopupWindow f2274e;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    private final ListSelectorHider mHideSelector;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    private int mPromptPosition;
    private View mPromptView;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static int a(PopupWindow popupWindow, View view, int i2, boolean z2) {
            return popupWindow.getMaxAvailableHeight(view, i2, z2);
        }
    }

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static void a(PopupWindow popupWindow, Rect rect) {
            popupWindow.setEpicenterBounds(rect);
        }

        @DoNotInline
        static void b(PopupWindow popupWindow, boolean z2) {
            popupWindow.setIsClippedToScreen(z2);
        }
    }

    private class ListSelectorHider implements Runnable {
        ListSelectorHider() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver extends DataSetObserver {
        PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener implements AbsListView.OnScrollListener {
        PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i2, int i3, int i4) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (i2 != 1 || ListPopupWindow.this.isInputMethodNotNeeded() || ListPopupWindow.this.f2274e.getContentView() == null) {
                return;
            }
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            listPopupWindow.f2273d.removeCallbacks(listPopupWindow.f2272c);
            ListPopupWindow.this.f2272c.run();
        }
    }

    private class PopupTouchInterceptor implements View.OnTouchListener {
        PopupTouchInterceptor() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x2 = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = ListPopupWindow.this.f2274e) != null && popupWindow.isShowing() && x2 >= 0 && x2 < ListPopupWindow.this.f2274e.getWidth() && y2 >= 0 && y2 < ListPopupWindow.this.f2274e.getHeight()) {
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.f2273d.postDelayed(listPopupWindow.f2272c, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            ListPopupWindow listPopupWindow2 = ListPopupWindow.this;
            listPopupWindow2.f2273d.removeCallbacks(listPopupWindow2.f2272c);
            return false;
        }
    }

    private class ResizePopupRunnable implements Runnable {
        ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            DropDownListView dropDownListView = ListPopupWindow.this.f2270a;
            if (dropDownListView == null || !ViewCompat.isAttachedToWindow(dropDownListView) || ListPopupWindow.this.f2270a.getCount() <= ListPopupWindow.this.f2270a.getChildCount()) {
                return;
            }
            int childCount = ListPopupWindow.this.f2270a.getChildCount();
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            if (childCount <= listPopupWindow.f2271b) {
                listPopupWindow.f2274e.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                sSetClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException unused) {
                Log.i(TAG, "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            } catch (NoSuchMethodException unused2) {
                Log.i(TAG, "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            try {
                sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
            } catch (NoSuchMethodException unused3) {
                Log.i(TAG, "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
            }
        }
    }

    public ListPopupWindow(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    private int buildDropDown() {
        int measuredHeight;
        int i2;
        int iMakeMeasureSpec;
        int i3;
        if (this.f2270a == null) {
            Context context = this.mContext;
            this.mShowDropDownRunnable = new Runnable() { // from class: androidx.appcompat.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    View anchorView = ListPopupWindow.this.getAnchorView();
                    if (anchorView == null || anchorView.getWindowToken() == null) {
                        return;
                    }
                    ListPopupWindow.this.show();
                }
            };
            DropDownListView dropDownListViewA = a(context, !this.mModal);
            this.f2270a = dropDownListViewA;
            Drawable drawable = this.mDropDownListHighlight;
            if (drawable != null) {
                dropDownListViewA.setSelector(drawable);
            }
            this.f2270a.setAdapter(this.mAdapter);
            this.f2270a.setOnItemClickListener(this.mItemClickListener);
            this.f2270a.setFocusable(true);
            this.f2270a.setFocusableInTouchMode(true);
            this.f2270a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i4, long j2) {
                    DropDownListView dropDownListView;
                    if (i4 == -1 || (dropDownListView = ListPopupWindow.this.f2270a) == null) {
                        return;
                    }
                    dropDownListView.setListSelectionHidden(false);
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.f2270a.setOnScrollListener(this.mScrollListener);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.mItemSelectedListener;
            if (onItemSelectedListener != null) {
                this.f2270a.setOnItemSelectedListener(onItemSelectedListener);
            }
            View view = this.f2270a;
            View view2 = this.mPromptView;
            if (view2 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                int i4 = this.mPromptPosition;
                if (i4 == 0) {
                    linearLayout.addView(view2);
                    linearLayout.addView(view, layoutParams);
                } else if (i4 != 1) {
                    Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                } else {
                    linearLayout.addView(view, layoutParams);
                    linearLayout.addView(view2);
                }
                int i5 = this.mDropDownWidth;
                if (i5 >= 0) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i5 = 0;
                    i3 = 0;
                }
                view2.measure(View.MeasureSpec.makeMeasureSpec(i5, i3), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                measuredHeight = view2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view = linearLayout;
            } else {
                measuredHeight = 0;
            }
            this.f2274e.setContentView(view);
        } else {
            View view3 = this.mPromptView;
            if (view3 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                measuredHeight = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                measuredHeight = 0;
            }
        }
        Drawable background = this.f2274e.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            Rect rect = this.mTempRect;
            int i6 = rect.top;
            i2 = rect.bottom + i6;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -i6;
            }
        } else {
            this.mTempRect.setEmpty();
            i2 = 0;
        }
        int maxAvailableHeight = getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.f2274e.getInputMethodMode() == 2);
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return maxAvailableHeight + i2;
        }
        int i7 = this.mDropDownWidth;
        if (i7 == -2) {
            int i8 = this.mContext.getResources().getDisplayMetrics().widthPixels;
            Rect rect2 = this.mTempRect;
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8 - (rect2.left + rect2.right), Integer.MIN_VALUE);
        } else if (i7 != -1) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i7, 1073741824);
        } else {
            int i9 = this.mContext.getResources().getDisplayMetrics().widthPixels;
            Rect rect3 = this.mTempRect;
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i9 - (rect3.left + rect3.right), 1073741824);
        }
        int iMeasureHeightOfChildrenCompat = this.f2270a.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, maxAvailableHeight - measuredHeight, -1);
        if (iMeasureHeightOfChildrenCompat > 0) {
            measuredHeight += i2 + this.f2270a.getPaddingTop() + this.f2270a.getPaddingBottom();
        }
        return iMeasureHeightOfChildrenCompat + measuredHeight;
    }

    private int getMaxAvailableHeight(View view, int i2, boolean z2) {
        if (Build.VERSION.SDK_INT > 23) {
            return Api24Impl.a(this.f2274e, view, i2, z2);
        }
        Method method = sGetMaxAvailableHeightMethod;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.f2274e, view, Integer.valueOf(i2), Boolean.valueOf(z2))).intValue();
            } catch (Exception unused) {
                Log.i(TAG, "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.f2274e.getMaxAvailableHeight(view, i2);
    }

    private static boolean isConfirmKey(int i2) {
        return i2 == 66 || i2 == 23;
    }

    private void removePromptView() {
        View view = this.mPromptView;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mPromptView);
            }
        }
    }

    private void setPopupClipToScreenEnabled(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT > 28) {
            Api29Impl.b(this.f2274e, z2);
            return;
        }
        Method method = sSetClipToWindowEnabledMethod;
        if (method != null) {
            try {
                method.invoke(this.f2274e, Boolean.valueOf(z2));
            } catch (Exception unused) {
                Log.i(TAG, "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    DropDownListView a(Context context, boolean z2) {
        return new DropDownListView(context, z2);
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.f2270a;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.requestLayout();
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view) { // from class: androidx.appcompat.widget.ListPopupWindow.1
            @Override // androidx.appcompat.widget.ForwardingListener
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        this.f2274e.dismiss();
        removePromptView();
        this.f2274e.setContentView(null);
        this.f2270a = null;
        this.f2273d.removeCallbacks(this.f2272c);
    }

    @Nullable
    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.f2274e.getAnimationStyle();
    }

    @Nullable
    public Drawable getBackground() {
        return this.f2274e.getBackground();
    }

    @Nullable
    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public int getInputMethodMode() {
        return this.f2274e.getInputMethodMode();
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    @Nullable
    public ListView getListView() {
        return this.f2270a;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    @Nullable
    public Object getSelectedItem() {
        if (isShowing()) {
            return this.f2270a.getSelectedItem();
        }
        return null;
    }

    public long getSelectedItemId() {
        if (isShowing()) {
            return this.f2270a.getSelectedItemId();
        }
        return Long.MIN_VALUE;
    }

    public int getSelectedItemPosition() {
        if (isShowing()) {
            return this.f2270a.getSelectedItemPosition();
        }
        return -1;
    }

    @Nullable
    public View getSelectedView() {
        if (isShowing()) {
            return this.f2270a.getSelectedView();
        }
        return null;
    }

    public int getSoftInputMode() {
        return this.f2274e.getSoftInputMode();
    }

    public int getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) {
            return this.mDropDownVerticalOffset;
        }
        return 0;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public boolean isInputMethodNotNeeded() {
        return this.f2274e.getInputMethodMode() == 2;
    }

    public boolean isModal() {
        return this.mModal;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.f2274e.isShowing();
    }

    public boolean onKeyDown(int i2, @NonNull KeyEvent keyEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iLookForSelectablePosition;
        int count;
        if (isShowing() && i2 != 62 && (this.f2270a.getSelectedItemPosition() >= 0 || !isConfirmKey(i2))) {
            int selectedItemPosition = this.f2270a.getSelectedItemPosition();
            boolean z2 = !this.f2274e.isAboveAnchor();
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter != null) {
                boolean zAreAllItemsEnabled = listAdapter.areAllItemsEnabled();
                iLookForSelectablePosition = zAreAllItemsEnabled ? 0 : this.f2270a.lookForSelectablePosition(0, true);
                count = zAreAllItemsEnabled ? listAdapter.getCount() - 1 : this.f2270a.lookForSelectablePosition(listAdapter.getCount() - 1, false);
            } else {
                iLookForSelectablePosition = Integer.MAX_VALUE;
                count = Integer.MIN_VALUE;
            }
            if ((z2 && i2 == 19 && selectedItemPosition <= iLookForSelectablePosition) || (!z2 && i2 == 20 && selectedItemPosition >= count)) {
                clearListSelection();
                this.f2274e.setInputMethodMode(1);
                show();
                return true;
            }
            this.f2270a.setListSelectionHidden(false);
            if (this.f2270a.onKeyDown(i2, keyEvent)) {
                this.f2274e.setInputMethodMode(2);
                this.f2270a.requestFocusFromTouch();
                show();
                if (i2 == 19 || i2 == 20 || i2 == 23 || i2 == 66) {
                    return true;
                }
            } else if (z2 && i2 == 20) {
                if (selectedItemPosition == count) {
                    return true;
                }
            } else if (!z2 && i2 == 19 && selectedItemPosition == iLookForSelectablePosition) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyPreIme(int i2, @NonNull KeyEvent keyEvent) {
        if (i2 != 4 || !isShowing()) {
            return false;
        }
        View view = this.mDropDownAnchorView;
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            KeyEvent.DispatcherState keyDispatcherState = view.getKeyDispatcherState();
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        }
        if (keyEvent.getAction() != 1) {
            return false;
        }
        KeyEvent.DispatcherState keyDispatcherState2 = view.getKeyDispatcherState();
        if (keyDispatcherState2 != null) {
            keyDispatcherState2.handleUpEvent(keyEvent);
        }
        if (!keyEvent.isTracking() || keyEvent.isCanceled()) {
            return false;
        }
        dismiss();
        return true;
    }

    public boolean onKeyUp(int i2, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.f2270a.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean zOnKeyUp = this.f2270a.onKeyUp(i2, keyEvent);
        if (zOnKeyUp && isConfirmKey(i2)) {
            dismiss();
        }
        return zOnKeyUp;
    }

    public boolean performItemClick(int i2) {
        if (!isShowing()) {
            return false;
        }
        if (this.mItemClickListener == null) {
            return true;
        }
        DropDownListView dropDownListView = this.f2270a;
        this.mItemClickListener.onItemClick(dropDownListView, dropDownListView.getChildAt(i2 - dropDownListView.getFirstVisiblePosition()), i2, dropDownListView.getAdapter().getItemId(i2));
        return true;
    }

    public void postShow() {
        this.f2273d.post(this.mShowDropDownRunnable);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.mObserver;
        if (dataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else {
            ListAdapter listAdapter2 = this.mAdapter;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.mAdapter = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
        DropDownListView dropDownListView = this.f2270a;
        if (dropDownListView != null) {
            dropDownListView.setAdapter(this.mAdapter);
        }
    }

    public void setAnchorView(@Nullable View view) {
        this.mDropDownAnchorView = view;
    }

    public void setAnimationStyle(@StyleRes int i2) {
        this.f2274e.setAnimationStyle(i2);
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.f2274e.setBackgroundDrawable(drawable);
    }

    public void setContentWidth(int i2) {
        Drawable background = this.f2274e.getBackground();
        if (background == null) {
            setWidth(i2);
            return;
        }
        background.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        this.mDropDownWidth = rect.left + rect.right + i2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setDropDownAlwaysVisible(boolean z2) {
        this.mDropDownAlwaysVisible = z2;
    }

    public void setDropDownGravity(int i2) {
        this.mDropDownGravity = i2;
    }

    public void setEpicenterBounds(@Nullable Rect rect) {
        this.mEpicenterBounds = rect != null ? new Rect(rect) : null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setForceIgnoreOutsideTouch(boolean z2) {
        this.mForceIgnoreOutsideTouch = z2;
    }

    public void setHeight(int i2) {
        if (i2 < 0 && -2 != i2 && -1 != i2) {
            throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
        }
        this.mDropDownHeight = i2;
    }

    public void setHorizontalOffset(int i2) {
        this.mDropDownHorizontalOffset = i2;
    }

    public void setInputMethodMode(int i2) {
        this.f2274e.setInputMethodMode(i2);
    }

    public void setListSelector(Drawable drawable) {
        this.mDropDownListHighlight = drawable;
    }

    public void setModal(boolean z2) {
        this.mModal = z2;
        this.f2274e.setFocusable(z2);
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.f2274e.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOverlapAnchor(boolean z2) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = z2;
    }

    public void setPromptPosition(int i2) {
        this.mPromptPosition = i2;
    }

    public void setPromptView(@Nullable View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zIsShowing = isShowing();
        if (zIsShowing) {
            removePromptView();
        }
        this.mPromptView = view;
        if (zIsShowing) {
            show();
        }
    }

    public void setSelection(int i2) {
        DropDownListView dropDownListView = this.f2270a;
        if (!isShowing() || dropDownListView == null) {
            return;
        }
        dropDownListView.setListSelectionHidden(false);
        dropDownListView.setSelection(i2);
        if (dropDownListView.getChoiceMode() != 0) {
            dropDownListView.setItemChecked(i2, true);
        }
    }

    public void setSoftInputMode(int i2) {
        this.f2274e.setSoftInputMode(i2);
    }

    public void setVerticalOffset(int i2) {
        this.mDropDownVerticalOffset = i2;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int i2) {
        this.mDropDownWidth = i2;
    }

    public void setWindowLayoutType(int i2) {
        this.mDropDownWindowLayoutType = i2;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iBuildDropDown = buildDropDown();
        boolean zIsInputMethodNotNeeded = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.f2274e, this.mDropDownWindowLayoutType);
        if (this.f2274e.isShowing()) {
            if (ViewCompat.isAttachedToWindow(getAnchorView())) {
                int width = this.mDropDownWidth;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = getAnchorView().getWidth();
                }
                int i2 = this.mDropDownHeight;
                if (i2 == -1) {
                    if (!zIsInputMethodNotNeeded) {
                        iBuildDropDown = -1;
                    }
                    if (zIsInputMethodNotNeeded) {
                        this.f2274e.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        this.f2274e.setHeight(0);
                    } else {
                        this.f2274e.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        this.f2274e.setHeight(-1);
                    }
                } else if (i2 != -2) {
                    iBuildDropDown = i2;
                }
                this.f2274e.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
                this.f2274e.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, width < 0 ? -1 : width, iBuildDropDown < 0 ? -1 : iBuildDropDown);
                return;
            }
            return;
        }
        int width2 = this.mDropDownWidth;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = getAnchorView().getWidth();
        }
        int i3 = this.mDropDownHeight;
        if (i3 == -1) {
            iBuildDropDown = -1;
        } else if (i3 != -2) {
            iBuildDropDown = i3;
        }
        this.f2274e.setWidth(width2);
        this.f2274e.setHeight(iBuildDropDown);
        setPopupClipToScreenEnabled(true);
        this.f2274e.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
        this.f2274e.setTouchInterceptor(this.mTouchInterceptor);
        if (this.mOverlapAnchorSet) {
            PopupWindowCompat.setOverlapAnchor(this.f2274e, this.mOverlapAnchor);
        }
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = sSetEpicenterBoundsMethod;
            if (method != null) {
                try {
                    method.invoke(this.f2274e, this.mEpicenterBounds);
                } catch (Exception e2) {
                    Log.e(TAG, "Could not invoke setEpicenterBounds on PopupWindow", e2);
                }
            }
        } else {
            Api29Impl.a(this.f2274e, this.mEpicenterBounds);
        }
        PopupWindowCompat.showAsDropDown(this.f2274e, getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.f2270a.setSelection(-1);
        if (!this.mModal || this.f2270a.isInTouchMode()) {
            clearListSelection();
        }
        if (this.mModal) {
            return;
        }
        this.f2273d.post(this.mHideSelector);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        this(context, attributeSet, i2, 0);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2, @StyleRes int i3) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.f2271b = Integer.MAX_VALUE;
        this.mPromptPosition = 0;
        this.f2272c = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = context;
        this.f2273d = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i2, i3);
        this.mDropDownHorizontalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        this.mDropDownVerticalOffset = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        AppCompatPopupWindow appCompatPopupWindow = new AppCompatPopupWindow(context, attributeSet, i2, i3);
        this.f2274e = appCompatPopupWindow;
        appCompatPopupWindow.setInputMethodMode(1);
    }
}
