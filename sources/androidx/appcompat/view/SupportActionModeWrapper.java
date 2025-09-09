package androidx.appcompat.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class SupportActionModeWrapper extends android.view.ActionMode {

    /* renamed from: a, reason: collision with root package name */
    final Context f2106a;

    /* renamed from: b, reason: collision with root package name */
    final ActionMode f2107b;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static class CallbackWrapper implements ActionMode.Callback {

        /* renamed from: a, reason: collision with root package name */
        final ActionMode.Callback f2108a;

        /* renamed from: b, reason: collision with root package name */
        final Context f2109b;

        /* renamed from: c, reason: collision with root package name */
        final ArrayList f2110c = new ArrayList();

        /* renamed from: d, reason: collision with root package name */
        final SimpleArrayMap f2111d = new SimpleArrayMap();

        public CallbackWrapper(Context context, ActionMode.Callback callback) {
            this.f2109b = context;
            this.f2108a = callback;
        }

        private Menu getMenuWrapper(Menu menu) {
            Menu menu2 = (Menu) this.f2111d.get(menu);
            if (menu2 != null) {
                return menu2;
            }
            MenuWrapperICS menuWrapperICS = new MenuWrapperICS(this.f2109b, (SupportMenu) menu);
            this.f2111d.put(menu, menuWrapperICS);
            return menuWrapperICS;
        }

        public android.view.ActionMode getActionModeWrapper(ActionMode actionMode) {
            int size = this.f2110c.size();
            for (int i2 = 0; i2 < size; i2++) {
                SupportActionModeWrapper supportActionModeWrapper = (SupportActionModeWrapper) this.f2110c.get(i2);
                if (supportActionModeWrapper != null && supportActionModeWrapper.f2107b == actionMode) {
                    return supportActionModeWrapper;
                }
            }
            SupportActionModeWrapper supportActionModeWrapper2 = new SupportActionModeWrapper(this.f2109b, actionMode);
            this.f2110c.add(supportActionModeWrapper2);
            return supportActionModeWrapper2;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.f2108a.onActionItemClicked(getActionModeWrapper(actionMode), new MenuItemWrapperICS(this.f2109b, (SupportMenuItem) menuItem));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.f2108a.onCreateActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menu));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.f2108a.onDestroyActionMode(getActionModeWrapper(actionMode));
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.f2108a.onPrepareActionMode(getActionModeWrapper(actionMode), getMenuWrapper(menu));
        }
    }

    public SupportActionModeWrapper(Context context, ActionMode actionMode) {
        this.f2106a = context;
        this.f2107b = actionMode;
    }

    @Override // android.view.ActionMode
    public void finish() {
        this.f2107b.finish();
    }

    @Override // android.view.ActionMode
    public View getCustomView() {
        return this.f2107b.getCustomView();
    }

    @Override // android.view.ActionMode
    public Menu getMenu() {
        return new MenuWrapperICS(this.f2106a, (SupportMenu) this.f2107b.getMenu());
    }

    @Override // android.view.ActionMode
    public MenuInflater getMenuInflater() {
        return this.f2107b.getMenuInflater();
    }

    @Override // android.view.ActionMode
    public CharSequence getSubtitle() {
        return this.f2107b.getSubtitle();
    }

    @Override // android.view.ActionMode
    public Object getTag() {
        return this.f2107b.getTag();
    }

    @Override // android.view.ActionMode
    public CharSequence getTitle() {
        return this.f2107b.getTitle();
    }

    @Override // android.view.ActionMode
    public boolean getTitleOptionalHint() {
        return this.f2107b.getTitleOptionalHint();
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.f2107b.invalidate();
    }

    @Override // android.view.ActionMode
    public boolean isTitleOptional() {
        return this.f2107b.isTitleOptional();
    }

    @Override // android.view.ActionMode
    public void setCustomView(View view) {
        this.f2107b.setCustomView(view);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.f2107b.setSubtitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setTag(Object obj) {
        this.f2107b.setTag(obj);
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.f2107b.setTitle(charSequence);
    }

    @Override // android.view.ActionMode
    public void setTitleOptionalHint(boolean z2) {
        this.f2107b.setTitleOptionalHint(z2);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i2) {
        this.f2107b.setSubtitle(i2);
    }

    @Override // android.view.ActionMode
    public void setTitle(int i2) {
        this.f2107b.setTitle(i2);
    }
}
