package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/* loaded from: classes.dex */
public class SimpleCursorAdapter extends ResourceCursorAdapter {

    /* renamed from: j, reason: collision with root package name */
    protected int[] f3869j;

    /* renamed from: k, reason: collision with root package name */
    protected int[] f3870k;

    /* renamed from: l, reason: collision with root package name */
    String[] f3871l;
    private CursorToStringConverter mCursorToStringConverter;
    private int mStringConversionColumn;
    private ViewBinder mViewBinder;

    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i2);
    }

    @Deprecated
    public SimpleCursorAdapter(Context context, int i2, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i2, cursor);
        this.mStringConversionColumn = -1;
        this.f3870k = iArr;
        this.f3871l = strArr;
        findColumns(cursor, strArr);
    }

    private void findColumns(Cursor cursor, String[] strArr) {
        if (cursor == null) {
            this.f3869j = null;
            return;
        }
        int length = strArr.length;
        int[] iArr = this.f3869j;
        if (iArr == null || iArr.length != length) {
            this.f3869j = new int[length];
        }
        for (int i2 = 0; i2 < length; i2++) {
            this.f3869j[i2] = cursor.getColumnIndexOrThrow(strArr[i2]);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        ViewBinder viewBinder = this.mViewBinder;
        int[] iArr = this.f3870k;
        int length = iArr.length;
        int[] iArr2 = this.f3869j;
        for (int i2 = 0; i2 < length; i2++) {
            View viewFindViewById = view.findViewById(iArr[i2]);
            if (viewFindViewById != null) {
                if (viewBinder != null ? viewBinder.setViewValue(viewFindViewById, cursor, iArr2[i2]) : false) {
                    continue;
                } else {
                    String string = cursor.getString(iArr2[i2]);
                    if (string == null) {
                        string = "";
                    }
                    if (viewFindViewById instanceof TextView) {
                        setViewText((TextView) viewFindViewById, string);
                    } else {
                        if (!(viewFindViewById instanceof ImageView)) {
                            throw new IllegalStateException(viewFindViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter");
                        }
                        setViewImage((ImageView) viewFindViewById, string);
                    }
                }
            }
        }
    }

    public void changeCursorAndColumns(Cursor cursor, String[] strArr, int[] iArr) {
        this.f3871l = strArr;
        this.f3870k = iArr;
        findColumns(cursor, strArr);
        super.changeCursor(cursor);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.CursorFilter.CursorFilterClient
    public CharSequence convertToString(Cursor cursor) {
        CursorToStringConverter cursorToStringConverter = this.mCursorToStringConverter;
        if (cursorToStringConverter != null) {
            return cursorToStringConverter.convertToString(cursor);
        }
        int i2 = this.mStringConversionColumn;
        return i2 > -1 ? cursor.getString(i2) : super.convertToString(cursor);
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter;
    }

    public void setStringConversionColumn(int i2) {
        this.mStringConversionColumn = i2;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    public void setViewText(TextView textView, String str) {
        textView.setText(str);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public Cursor swapCursor(Cursor cursor) {
        findColumns(cursor, this.f3871l);
        return super.swapCursor(cursor);
    }

    public SimpleCursorAdapter(Context context, int i2, Cursor cursor, String[] strArr, int[] iArr, int i3) {
        super(context, i2, cursor, i3);
        this.mStringConversionColumn = -1;
        this.f3870k = iArr;
        this.f3871l = strArr;
        findColumns(cursor, strArr);
    }
}
