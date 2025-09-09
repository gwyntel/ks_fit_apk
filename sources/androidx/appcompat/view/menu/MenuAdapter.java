package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuView;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class MenuAdapter extends BaseAdapter {

    /* renamed from: a, reason: collision with root package name */
    MenuBuilder f2156a;
    private int mExpandedIndex = -1;
    private boolean mForceShowIcon;
    private final LayoutInflater mInflater;
    private final int mItemLayoutRes;
    private final boolean mOverflowOnly;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean z2, int i2) {
        this.mOverflowOnly = z2;
        this.mInflater = layoutInflater;
        this.f2156a = menuBuilder;
        this.mItemLayoutRes = i2;
        a();
    }

    void a() {
        MenuItemImpl expandedItem = this.f2156a.getExpandedItem();
        if (expandedItem != null) {
            ArrayList<MenuItemImpl> nonActionItems = this.f2156a.getNonActionItems();
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

    public MenuBuilder getAdapterMenu() {
        return this.f2156a;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mExpandedIndex < 0 ? (this.mOverflowOnly ? this.f2156a.getNonActionItems() : this.f2156a.getVisibleItems()).size() : r0.size() - 1;
    }

    public boolean getForceShowIcon() {
        return this.mForceShowIcon;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(this.mItemLayoutRes, viewGroup, false);
        }
        int groupId = getItem(i2).getGroupId();
        int i3 = i2 - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        listMenuItemView.setGroupDividerEnabled(this.f2156a.isGroupDividerEnabled() && groupId != (i3 >= 0 ? getItem(i3).getGroupId() : groupId));
        MenuView.ItemView itemView = (MenuView.ItemView) view;
        if (this.mForceShowIcon) {
            listMenuItemView.setForceShowIcon(true);
        }
        itemView.initialize(getItem(i2), 0);
        return view;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }

    public void setForceShowIcon(boolean z2) {
        this.mForceShowIcon = z2;
    }

    @Override // android.widget.Adapter
    public MenuItemImpl getItem(int i2) {
        ArrayList<MenuItemImpl> nonActionItems = this.mOverflowOnly ? this.f2156a.getNonActionItems() : this.f2156a.getVisibleItems();
        int i3 = this.mExpandedIndex;
        if (i3 >= 0 && i2 >= i3) {
            i2++;
        }
        return nonActionItems.get(i2);
    }
}
