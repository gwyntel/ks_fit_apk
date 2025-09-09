package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ListMenuPresenter implements MenuPresenter, AdapterView.OnItemClickListener {
    private static final String TAG = "ListMenuPresenter";
    public static final String VIEWS_TAG = "android:menu:list";

    /* renamed from: a, reason: collision with root package name */
    Context f2147a;

    /* renamed from: b, reason: collision with root package name */
    LayoutInflater f2148b;

    /* renamed from: c, reason: collision with root package name */
    MenuBuilder f2149c;

    /* renamed from: d, reason: collision with root package name */
    ExpandedMenuView f2150d;

    /* renamed from: e, reason: collision with root package name */
    int f2151e;

    /* renamed from: f, reason: collision with root package name */
    int f2152f;

    /* renamed from: g, reason: collision with root package name */
    int f2153g;

    /* renamed from: h, reason: collision with root package name */
    MenuAdapter f2154h;
    private MenuPresenter.Callback mCallback;
    private int mId;

    private class MenuAdapter extends BaseAdapter {
        private int mExpandedIndex = -1;

        public MenuAdapter() {
            a();
        }

        void a() {
            MenuItemImpl expandedItem = ListMenuPresenter.this.f2149c.getExpandedItem();
            if (expandedItem != null) {
                ArrayList<MenuItemImpl> nonActionItems = ListMenuPresenter.this.f2149c.getNonActionItems();
                int size = nonActionItems.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (nonActionItems.get(i2) == expandedItem) {
                        this.mExpandedIndex = i2;
                        return;
                    }
                }
            }
            this.mExpandedIndex = -1;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int size = ListMenuPresenter.this.f2149c.getNonActionItems().size() - ListMenuPresenter.this.f2151e;
            return this.mExpandedIndex < 0 ? size : size - 1;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            if (view == null) {
                ListMenuPresenter listMenuPresenter = ListMenuPresenter.this;
                view = listMenuPresenter.f2148b.inflate(listMenuPresenter.f2153g, viewGroup, false);
            }
            ((MenuView.ItemView) view).initialize(getItem(i2), 0);
            return view;
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public MenuItemImpl getItem(int i2) {
            ArrayList<MenuItemImpl> nonActionItems = ListMenuPresenter.this.f2149c.getNonActionItems();
            int i3 = i2 + ListMenuPresenter.this.f2151e;
            int i4 = this.mExpandedIndex;
            if (i4 >= 0 && i3 >= i4) {
                i3++;
            }
            return nonActionItems.get(i3);
        }
    }

    public ListMenuPresenter(Context context, int i2) {
        this(i2, 0);
        this.f2147a = context;
        this.f2148b = LayoutInflater.from(context);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    public ListAdapter getAdapter() {
        if (this.f2154h == null) {
            this.f2154h = new MenuAdapter();
        }
        return this.f2154h;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.mId;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.f2150d == null) {
            this.f2150d = (ExpandedMenuView) this.f2148b.inflate(R.layout.abc_expanded_menu_layout, viewGroup, false);
            if (this.f2154h == null) {
                this.f2154h = new MenuAdapter();
            }
            this.f2150d.setAdapter((ListAdapter) this.f2154h);
            this.f2150d.setOnItemClickListener(this);
        }
        return this.f2150d;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        if (this.f2152f != 0) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, this.f2152f);
            this.f2147a = contextThemeWrapper;
            this.f2148b = LayoutInflater.from(contextThemeWrapper);
        } else if (this.f2147a != null) {
            this.f2147a = context;
            if (this.f2148b == null) {
                this.f2148b = LayoutInflater.from(context);
            }
        }
        this.f2149c = menuBuilder;
        MenuAdapter menuAdapter = this.f2154h;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z2) {
        MenuPresenter.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z2);
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        this.f2149c.performItemAction(this.f2154h.getItem(i2), this, 0);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        restoreHierarchyState((Bundle) parcelable);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        if (this.f2150d == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        saveHierarchyState(bundle);
        return bundle;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        new MenuDialogHelper(subMenuBuilder).show(null);
        MenuPresenter.Callback callback = this.mCallback;
        if (callback == null) {
            return true;
        }
        callback.onOpenSubMenu(subMenuBuilder);
        return true;
    }

    public void restoreHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            this.f2150d.restoreHierarchyState(sparseParcelableArray);
        }
    }

    public void saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        ExpandedMenuView expandedMenuView = this.f2150d;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setId(int i2) {
        this.mId = i2;
    }

    public void setItemIndexOffset(int i2) {
        this.f2151e = i2;
        if (this.f2150d != null) {
            updateMenuView(false);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z2) {
        MenuAdapter menuAdapter = this.f2154h;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    public ListMenuPresenter(int i2, int i3) {
        this.f2153g = i2;
        this.f2152f = i3;
    }
}
