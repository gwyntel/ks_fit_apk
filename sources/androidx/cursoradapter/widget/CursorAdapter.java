package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import androidx.cursoradapter.widget.CursorFilter;
import com.umeng.analytics.pro.bg;

/* loaded from: classes.dex */
public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilter.CursorFilterClient {

    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;

    /* renamed from: a, reason: collision with root package name */
    protected boolean f3857a;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f3858b;

    /* renamed from: c, reason: collision with root package name */
    protected Cursor f3859c;

    /* renamed from: d, reason: collision with root package name */
    protected Context f3860d;

    /* renamed from: e, reason: collision with root package name */
    protected int f3861e;

    /* renamed from: f, reason: collision with root package name */
    protected ChangeObserver f3862f;

    /* renamed from: g, reason: collision with root package name */
    protected DataSetObserver f3863g;

    /* renamed from: h, reason: collision with root package name */
    protected CursorFilter f3864h;

    /* renamed from: i, reason: collision with root package name */
    protected FilterQueryProvider f3865i;

    private class ChangeObserver extends ContentObserver {
        ChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z2) {
            CursorAdapter.this.b();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {
        MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            CursorAdapter cursorAdapter = CursorAdapter.this;
            cursorAdapter.f3857a = true;
            cursorAdapter.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            CursorAdapter cursorAdapter = CursorAdapter.this;
            cursorAdapter.f3857a = false;
            cursorAdapter.notifyDataSetInvalidated();
        }
    }

    @Deprecated
    public CursorAdapter(Context context, Cursor cursor) {
        a(context, cursor, 1);
    }

    void a(Context context, Cursor cursor, int i2) {
        if ((i2 & 1) == 1) {
            i2 |= 2;
            this.f3858b = true;
        } else {
            this.f3858b = false;
        }
        boolean z2 = cursor != null;
        this.f3859c = cursor;
        this.f3857a = z2;
        this.f3860d = context;
        this.f3861e = z2 ? cursor.getColumnIndexOrThrow(bg.f21483d) : -1;
        if ((i2 & 2) == 2) {
            this.f3862f = new ChangeObserver();
            this.f3863g = new MyDataSetObserver();
        } else {
            this.f3862f = null;
            this.f3863g = null;
        }
        if (z2) {
            ChangeObserver changeObserver = this.f3862f;
            if (changeObserver != null) {
                cursor.registerContentObserver(changeObserver);
            }
            DataSetObserver dataSetObserver = this.f3863g;
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    protected void b() {
        Cursor cursor;
        if (!this.f3858b || (cursor = this.f3859c) == null || cursor.isClosed()) {
            return;
        }
        this.f3857a = this.f3859c.requery();
    }

    public abstract void bindView(View view, Context context, Cursor cursor);

    public void changeCursor(Cursor cursor) {
        Cursor cursorSwapCursor = swapCursor(cursor);
        if (cursorSwapCursor != null) {
            cursorSwapCursor.close();
        }
    }

    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        Cursor cursor;
        if (!this.f3857a || (cursor = this.f3859c) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override // androidx.cursoradapter.widget.CursorFilter.CursorFilterClient
    public Cursor getCursor() {
        return this.f3859c;
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i2, View view, ViewGroup viewGroup) {
        if (!this.f3857a) {
            return null;
        }
        this.f3859c.moveToPosition(i2);
        if (view == null) {
            view = newDropDownView(this.f3860d, this.f3859c, viewGroup);
        }
        bindView(view, this.f3860d, this.f3859c);
        return view;
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.f3864h == null) {
            this.f3864h = new CursorFilter(this);
        }
        return this.f3864h;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.f3865i;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i2) {
        Cursor cursor;
        if (!this.f3857a || (cursor = this.f3859c) == null) {
            return null;
        }
        cursor.moveToPosition(i2);
        return this.f3859c;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        Cursor cursor;
        if (this.f3857a && (cursor = this.f3859c) != null && cursor.moveToPosition(i2)) {
            return this.f3859c.getLong(this.f3861e);
        }
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        if (!this.f3857a) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (this.f3859c.moveToPosition(i2)) {
            if (view == null) {
                view = newView(this.f3860d, this.f3859c, viewGroup);
            }
            bindView(view, this.f3860d, this.f3859c);
            return view;
        }
        throw new IllegalStateException("couldn't move cursor to position " + i2);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return newView(context, cursor, viewGroup);
    }

    public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup);

    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        FilterQueryProvider filterQueryProvider = this.f3865i;
        return filterQueryProvider != null ? filterQueryProvider.runQuery(charSequence) : this.f3859c;
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.f3865i = filterQueryProvider;
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor cursor2 = this.f3859c;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            ChangeObserver changeObserver = this.f3862f;
            if (changeObserver != null) {
                cursor2.unregisterContentObserver(changeObserver);
            }
            DataSetObserver dataSetObserver = this.f3863g;
            if (dataSetObserver != null) {
                cursor2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f3859c = cursor;
        if (cursor != null) {
            ChangeObserver changeObserver2 = this.f3862f;
            if (changeObserver2 != null) {
                cursor.registerContentObserver(changeObserver2);
            }
            DataSetObserver dataSetObserver2 = this.f3863g;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            this.f3861e = cursor.getColumnIndexOrThrow(bg.f21483d);
            this.f3857a = true;
            notifyDataSetChanged();
        } else {
            this.f3861e = -1;
            this.f3857a = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    public CursorAdapter(Context context, Cursor cursor, boolean z2) {
        a(context, cursor, z2 ? 1 : 2);
    }

    public CursorAdapter(Context context, Cursor cursor, int i2) {
        a(context, cursor, i2);
    }
}
