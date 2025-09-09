package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener {
    private static final int ITEM_LAYOUT = R.layout.abc_cascading_menu_item_layout;

    /* renamed from: a, reason: collision with root package name */
    final Handler f2134a;

    /* renamed from: d, reason: collision with root package name */
    View f2137d;

    /* renamed from: e, reason: collision with root package name */
    ViewTreeObserver f2138e;

    /* renamed from: f, reason: collision with root package name */
    boolean f2139f;
    private View mAnchorView;
    private final Context mContext;
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private final int mMenuMaxWidth;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private boolean mShowTitle;
    private int mXOffset;
    private int mYOffset;
    private final List<MenuBuilder> mPendingMenus = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    final List f2135b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    final ViewTreeObserver.OnGlobalLayoutListener f2136c = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (!CascadingMenuPopup.this.isShowing() || CascadingMenuPopup.this.f2135b.size() <= 0 || ((CascadingMenuInfo) CascadingMenuPopup.this.f2135b.get(0)).window.isModal()) {
                return;
            }
            View view = CascadingMenuPopup.this.f2137d;
            if (view == null || !view.isShown()) {
                CascadingMenuPopup.this.dismiss();
                return;
            }
            Iterator it = CascadingMenuPopup.this.f2135b.iterator();
            while (it.hasNext()) {
                ((CascadingMenuInfo) it.next()).window.show();
            }
        }
    };
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = CascadingMenuPopup.this.f2138e;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.f2138e = view.getViewTreeObserver();
                }
                CascadingMenuPopup cascadingMenuPopup = CascadingMenuPopup.this;
                cascadingMenuPopup.f2138e.removeGlobalOnLayoutListener(cascadingMenuPopup.f2136c);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3
        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public void onItemHoverEnter(@NonNull final MenuBuilder menuBuilder, @NonNull final MenuItem menuItem) {
            CascadingMenuPopup.this.f2134a.removeCallbacksAndMessages(null);
            int size = CascadingMenuPopup.this.f2135b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (menuBuilder == ((CascadingMenuInfo) CascadingMenuPopup.this.f2135b.get(i2)).menu) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return;
            }
            int i3 = i2 + 1;
            final CascadingMenuInfo cascadingMenuInfo = i3 < CascadingMenuPopup.this.f2135b.size() ? (CascadingMenuInfo) CascadingMenuPopup.this.f2135b.get(i3) : null;
            CascadingMenuPopup.this.f2134a.postAtTime(new Runnable() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3.1
                @Override // java.lang.Runnable
                public void run() {
                    CascadingMenuInfo cascadingMenuInfo2 = cascadingMenuInfo;
                    if (cascadingMenuInfo2 != null) {
                        CascadingMenuPopup.this.f2139f = true;
                        cascadingMenuInfo2.menu.close(false);
                        CascadingMenuPopup.this.f2139f = false;
                    }
                    if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                        menuBuilder.performItemAction(menuItem, 4);
                    }
                }
            }, menuBuilder, SystemClock.uptimeMillis() + 200);
        }

        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.f2134a.removeCallbacksAndMessages(menuBuilder);
        }
    };
    private int mRawDropDownGravity = 0;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon = false;
    private int mLastPosition = getInitialMenuPosition();

    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int i2) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = i2;
        }

        public ListView getListView() {
            return this.window.getListView();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HorizPosition {
    }

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view, @AttrRes int i2, @StyleRes int i3, boolean z2) {
        this.mContext = context;
        this.mAnchorView = view;
        this.mPopupStyleAttr = i2;
        this.mPopupStyleRes = i3;
        this.mOverflowOnly = z2;
        Resources resources = context.getResources();
        this.mMenuMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.f2134a = new Handler();
    }

    private MenuPopupWindow createPopupWindow() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    private int findIndexOfAddedMenu(@NonNull MenuBuilder menuBuilder) {
        int size = this.f2135b.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (menuBuilder == ((CascadingMenuInfo) this.f2135b.get(i2)).menu) {
                return i2;
            }
        }
        return -1;
    }

    private MenuItem findMenuItemForSubmenu(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        int size = menuBuilder.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = menuBuilder.getItem(i2);
            if (item.hasSubMenu() && menuBuilder2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    @Nullable
    private View findParentViewForSubmenu(@NonNull CascadingMenuInfo cascadingMenuInfo, @NonNull MenuBuilder menuBuilder) {
        MenuAdapter menuAdapter;
        int headersCount;
        int firstVisiblePosition;
        MenuItem menuItemFindMenuItemForSubmenu = findMenuItemForSubmenu(cascadingMenuInfo.menu, menuBuilder);
        if (menuItemFindMenuItemForSubmenu == null) {
            return null;
        }
        ListView listView = cascadingMenuInfo.getListView();
        ListAdapter adapter = listView.getAdapter();
        int i2 = 0;
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            headersCount = headerViewListAdapter.getHeadersCount();
            menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
        } else {
            menuAdapter = (MenuAdapter) adapter;
            headersCount = 0;
        }
        int count = menuAdapter.getCount();
        while (true) {
            if (i2 >= count) {
                i2 = -1;
                break;
            }
            if (menuItemFindMenuItemForSubmenu == menuAdapter.getItem(i2)) {
                break;
            }
            i2++;
        }
        if (i2 != -1 && (firstVisiblePosition = (i2 + headersCount) - listView.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < listView.getChildCount()) {
            return listView.getChildAt(firstVisiblePosition);
        }
        return null;
    }

    private int getInitialMenuPosition() {
        return ViewCompat.getLayoutDirection(this.mAnchorView) == 1 ? 0 : 1;
    }

    private int getNextMenuPosition(int i2) {
        List list = this.f2135b;
        ListView listView = ((CascadingMenuInfo) list.get(list.size() - 1)).getListView();
        int[] iArr = new int[2];
        listView.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.f2137d.getWindowVisibleDisplayFrame(rect);
        return this.mLastPosition == 1 ? (iArr[0] + listView.getWidth()) + i2 > rect.right ? 0 : 1 : iArr[0] - i2 < 0 ? 1 : 0;
    }

    private void showMenu(@NonNull MenuBuilder menuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        CascadingMenuInfo cascadingMenuInfo;
        View viewFindParentViewForSubmenu;
        int i2;
        int i3;
        int i4;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mContext);
        MenuAdapter menuAdapter = new MenuAdapter(menuBuilder, layoutInflaterFrom, this.mOverflowOnly, ITEM_LAYOUT);
        if (!isShowing() && this.mForceShowIcon) {
            menuAdapter.setForceShowIcon(true);
        } else if (isShowing()) {
            menuAdapter.setForceShowIcon(MenuPopup.c(menuBuilder));
        }
        int iB = MenuPopup.b(menuAdapter, null, this.mContext, this.mMenuMaxWidth);
        MenuPopupWindow menuPopupWindowCreatePopupWindow = createPopupWindow();
        menuPopupWindowCreatePopupWindow.setAdapter(menuAdapter);
        menuPopupWindowCreatePopupWindow.setContentWidth(iB);
        menuPopupWindowCreatePopupWindow.setDropDownGravity(this.mDropDownGravity);
        if (this.f2135b.size() > 0) {
            List list = this.f2135b;
            cascadingMenuInfo = (CascadingMenuInfo) list.get(list.size() - 1);
            viewFindParentViewForSubmenu = findParentViewForSubmenu(cascadingMenuInfo, menuBuilder);
        } else {
            cascadingMenuInfo = null;
            viewFindParentViewForSubmenu = null;
        }
        if (viewFindParentViewForSubmenu != null) {
            menuPopupWindowCreatePopupWindow.setTouchModal(false);
            menuPopupWindowCreatePopupWindow.setEnterTransition(null);
            int nextMenuPosition = getNextMenuPosition(iB);
            boolean z2 = nextMenuPosition == 1;
            this.mLastPosition = nextMenuPosition;
            if (Build.VERSION.SDK_INT >= 26) {
                menuPopupWindowCreatePopupWindow.setAnchorView(viewFindParentViewForSubmenu);
                i3 = 0;
                i2 = 0;
            } else {
                int[] iArr = new int[2];
                this.mAnchorView.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                viewFindParentViewForSubmenu.getLocationOnScreen(iArr2);
                if ((this.mDropDownGravity & 7) == 5) {
                    iArr[0] = iArr[0] + this.mAnchorView.getWidth();
                    iArr2[0] = iArr2[0] + viewFindParentViewForSubmenu.getWidth();
                }
                i2 = iArr2[0] - iArr[0];
                i3 = iArr2[1] - iArr[1];
            }
            if ((this.mDropDownGravity & 5) == 5) {
                if (!z2) {
                    iB = viewFindParentViewForSubmenu.getWidth();
                    i4 = i2 - iB;
                }
                i4 = i2 + iB;
            } else {
                if (z2) {
                    iB = viewFindParentViewForSubmenu.getWidth();
                    i4 = i2 + iB;
                }
                i4 = i2 - iB;
            }
            menuPopupWindowCreatePopupWindow.setHorizontalOffset(i4);
            menuPopupWindowCreatePopupWindow.setOverlapAnchor(true);
            menuPopupWindowCreatePopupWindow.setVerticalOffset(i3);
        } else {
            if (this.mHasXOffset) {
                menuPopupWindowCreatePopupWindow.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                menuPopupWindowCreatePopupWindow.setVerticalOffset(this.mYOffset);
            }
            menuPopupWindowCreatePopupWindow.setEpicenterBounds(getEpicenterBounds());
        }
        this.f2135b.add(new CascadingMenuInfo(menuPopupWindowCreatePopupWindow, menuBuilder, this.mLastPosition));
        menuPopupWindowCreatePopupWindow.show();
        ListView listView = menuPopupWindowCreatePopupWindow.getListView();
        listView.setOnKeyListener(this);
        if (cascadingMenuInfo == null && this.mShowTitle && menuBuilder.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(menuBuilder.getHeaderTitle());
            listView.addHeaderView(frameLayout, null, false);
            menuPopupWindowCreatePopupWindow.show();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    protected boolean a() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void addMenu(MenuBuilder menuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (isShowing()) {
            showMenu(menuBuilder);
        } else {
            this.mPendingMenus.add(menuBuilder);
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        int size = this.f2135b.size();
        if (size > 0) {
            CascadingMenuInfo[] cascadingMenuInfoArr = (CascadingMenuInfo[]) this.f2135b.toArray(new CascadingMenuInfo[size]);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                CascadingMenuInfo cascadingMenuInfo = cascadingMenuInfoArr[i2];
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss();
                }
            }
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public ListView getListView() {
        if (this.f2135b.isEmpty()) {
            return null;
        }
        return ((CascadingMenuInfo) this.f2135b.get(r0.size() - 1)).getListView();
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.f2135b.size() > 0 && ((CascadingMenuInfo) this.f2135b.get(0)).window.isShowing();
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z2) {
        int iFindIndexOfAddedMenu = findIndexOfAddedMenu(menuBuilder);
        if (iFindIndexOfAddedMenu < 0) {
            return;
        }
        int i2 = iFindIndexOfAddedMenu + 1;
        if (i2 < this.f2135b.size()) {
            ((CascadingMenuInfo) this.f2135b.get(i2)).menu.close(false);
        }
        CascadingMenuInfo cascadingMenuInfo = (CascadingMenuInfo) this.f2135b.remove(iFindIndexOfAddedMenu);
        cascadingMenuInfo.menu.removeMenuPresenter(this);
        if (this.f2139f) {
            cascadingMenuInfo.window.setExitTransition(null);
            cascadingMenuInfo.window.setAnimationStyle(0);
        }
        cascadingMenuInfo.window.dismiss();
        int size = this.f2135b.size();
        if (size > 0) {
            this.mLastPosition = ((CascadingMenuInfo) this.f2135b.get(size - 1)).position;
        } else {
            this.mLastPosition = getInitialMenuPosition();
        }
        if (size != 0) {
            if (z2) {
                ((CascadingMenuInfo) this.f2135b.get(0)).menu.close(false);
                return;
            }
            return;
        }
        dismiss();
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, true);
        }
        ViewTreeObserver viewTreeObserver = this.f2138e;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.f2138e.removeGlobalOnLayoutListener(this.f2136c);
            }
            this.f2138e = null;
        }
        this.f2137d.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        this.mOnDismissListener.onDismiss();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        int size = this.f2135b.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                cascadingMenuInfo = null;
                break;
            }
            cascadingMenuInfo = (CascadingMenuInfo) this.f2135b.get(i2);
            if (!cascadingMenuInfo.window.isShowing()) {
                break;
            } else {
                i2++;
            }
        }
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (CascadingMenuInfo cascadingMenuInfo : this.f2135b) {
            if (subMenuBuilder == cascadingMenuInfo.menu) {
                cascadingMenuInfo.getListView().requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        addMenu(subMenuBuilder);
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setAnchorView(@NonNull View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(view));
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setForceShowIcon(boolean z2) {
        this.mForceShowIcon = z2;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setGravity(int i2) {
        if (this.mRawDropDownGravity != i2) {
            this.mRawDropDownGravity = i2;
            this.mDropDownGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this.mAnchorView));
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setHorizontalOffset(int i2) {
        this.mHasXOffset = true;
        this.mXOffset = i2;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setShowTitle(boolean z2) {
        this.mShowTitle = z2;
    }

    @Override // androidx.appcompat.view.menu.MenuPopup
    public void setVerticalOffset(int i2) {
        this.mHasYOffset = true;
        this.mYOffset = i2;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (isShowing()) {
            return;
        }
        Iterator<MenuBuilder> it = this.mPendingMenus.iterator();
        while (it.hasNext()) {
            showMenu(it.next());
        }
        this.mPendingMenus.clear();
        View view = this.mAnchorView;
        this.f2137d = view;
        if (view != null) {
            boolean z2 = this.f2138e == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.f2138e = viewTreeObserver;
            if (z2) {
                viewTreeObserver.addOnGlobalLayoutListener(this.f2136c);
            }
            this.f2137d.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z2) {
        Iterator it = this.f2135b.iterator();
        while (it.hasNext()) {
            MenuPopup.d(((CascadingMenuInfo) it.next()).getListView().getAdapter()).notifyDataSetChanged();
        }
    }
}
