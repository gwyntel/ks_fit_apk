package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.sqlite.CursorWrapper;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@KeepForSdk
@KeepName
@SafeParcelable.Class(creator = "DataHolderCreator", validate = true)
/* loaded from: classes3.dex */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {

    @NonNull
    @KeepForSdk
    public static final Parcelable.Creator<DataHolder> CREATOR = new zaf();
    private static final Builder zaf = new zab(new String[0], null);

    /* renamed from: a, reason: collision with root package name */
    final int f12796a;

    /* renamed from: b, reason: collision with root package name */
    Bundle f12797b;

    /* renamed from: c, reason: collision with root package name */
    int[] f12798c;

    /* renamed from: d, reason: collision with root package name */
    int f12799d;

    /* renamed from: e, reason: collision with root package name */
    boolean f12800e;

    @SafeParcelable.Field(getter = "getColumns", id = 1)
    private final String[] zag;

    @SafeParcelable.Field(getter = "getWindows", id = 2)
    private final CursorWindow[] zah;

    @SafeParcelable.Field(getter = "getStatusCode", id = 3)
    private final int zai;

    @Nullable
    @SafeParcelable.Field(getter = "getMetadata", id = 4)
    private final Bundle zaj;
    private boolean zak;

    @KeepForSdk
    public static class Builder {
        private final String[] zaa;
        private final ArrayList zab = new ArrayList();
        private final HashMap zac = new HashMap();

        /* synthetic */ Builder(String[] strArr, String str, zac zacVar) {
            this.zaa = (String[]) Preconditions.checkNotNull(strArr);
        }

        @NonNull
        @KeepForSdk
        public DataHolder build(int i2) {
            return new DataHolder(this, i2);
        }

        @NonNull
        @CanIgnoreReturnValue
        @KeepForSdk
        public Builder withRow(@NonNull ContentValues contentValues) {
            Asserts.checkNotNull(contentValues);
            HashMap map = new HashMap(contentValues.size());
            for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            return zaa(map);
        }

        @NonNull
        @CanIgnoreReturnValue
        public Builder zaa(@NonNull HashMap map) {
            Asserts.checkNotNull(map);
            this.zab.add(map);
            return this;
        }

        @NonNull
        @KeepForSdk
        public DataHolder build(int i2, @NonNull Bundle bundle) {
            return new DataHolder(this, i2, bundle);
        }
    }

    @NonNull
    @KeepForSdk
    public static Builder builder(@NonNull String[] strArr) {
        return new Builder(strArr, null, 0 == true ? 1 : 0);
    }

    @NonNull
    @KeepForSdk
    public static DataHolder empty(int i2) {
        return new DataHolder(zaf, i2, (Bundle) null);
    }

    private final void zae(String str, int i2) {
        Bundle bundle = this.f12797b;
        if (bundle == null || !bundle.containsKey(str)) {
            throw new IllegalArgumentException("No such column: ".concat(String.valueOf(str)));
        }
        if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        }
        if (i2 < 0 || i2 >= this.f12799d) {
            throw new CursorIndexOutOfBoundsException(i2, this.f12799d);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0136, code lost:
    
        if (r5 != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0138, code lost:
    
        android.util.Log.d("DataHolder", "Couldn't populate window data for row " + r4 + " - allocating new window.");
        r2.freeLastRow();
        r2 = new android.database.CursorWindow(false);
        r2.setStartPosition(r4);
        r2.setNumColumns(r13.zaa.length);
        r3.add(r2);
        r4 = r4 - 1;
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x016a, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0174, code lost:
    
        throw new com.google.android.gms.common.data.zad("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.database.CursorWindow[] zaf(com.google.android.gms.common.data.DataHolder.Builder r13, int r14) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.zaf(com.google.android.gms.common.data.DataHolder$Builder, int):android.database.CursorWindow[]");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    @KeepForSdk
    public void close() {
        synchronized (this) {
            try {
                if (!this.f12800e) {
                    this.f12800e = true;
                    int i2 = 0;
                    while (true) {
                        CursorWindow[] cursorWindowArr = this.zah;
                        if (i2 >= cursorWindowArr.length) {
                            break;
                        }
                        cursorWindowArr[i2].close();
                        i2++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zak && this.zah.length > 0 && !isClosed()) {
                close();
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: " + toString() + ")");
            }
        } finally {
            super.finalize();
        }
    }

    @KeepForSdk
    public boolean getBoolean(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getLong(i2, this.f12797b.getInt(str)) == 1;
    }

    @NonNull
    @KeepForSdk
    public byte[] getByteArray(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getBlob(i2, this.f12797b.getInt(str));
    }

    @KeepForSdk
    public int getCount() {
        return this.f12799d;
    }

    @KeepForSdk
    public int getInteger(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getInt(i2, this.f12797b.getInt(str));
    }

    @KeepForSdk
    public long getLong(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getLong(i2, this.f12797b.getInt(str));
    }

    @Nullable
    @KeepForSdk
    public Bundle getMetadata() {
        return this.zaj;
    }

    @KeepForSdk
    public int getStatusCode() {
        return this.zai;
    }

    @NonNull
    @KeepForSdk
    public String getString(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getString(i2, this.f12797b.getInt(str));
    }

    @KeepForSdk
    public int getWindowIndex(int i2) {
        int length;
        int i3 = 0;
        Preconditions.checkState(i2 >= 0 && i2 < this.f12799d);
        while (true) {
            int[] iArr = this.f12798c;
            length = iArr.length;
            if (i3 >= length) {
                break;
            }
            if (i2 < iArr[i3]) {
                i3--;
                break;
            }
            i3++;
        }
        return i3 == length ? i3 - 1 : i3;
    }

    @KeepForSdk
    public boolean hasColumn(@NonNull String str) {
        return this.f12797b.containsKey(str);
    }

    @KeepForSdk
    public boolean hasNull(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].isNull(i2, this.f12797b.getInt(str));
    }

    @KeepForSdk
    public boolean isClosed() {
        boolean z2;
        synchronized (this) {
            z2 = this.f12800e;
        }
        return z2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        String[] strArr = this.zag;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, strArr, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zah, i2, false);
        SafeParcelWriter.writeInt(parcel, 3, getStatusCode());
        SafeParcelWriter.writeBundle(parcel, 4, getMetadata(), false);
        SafeParcelWriter.writeInt(parcel, 1000, this.f12796a);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        if ((i2 & 1) != 0) {
            close();
        }
    }

    public final double zaa(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getDouble(i2, this.f12797b.getInt(str));
    }

    public final float zab(@NonNull String str, int i2, int i3) {
        zae(str, i2);
        return this.zah[i3].getFloat(i2, this.f12797b.getInt(str));
    }

    public final void zac(@NonNull String str, int i2, int i3, @NonNull CharArrayBuffer charArrayBuffer) {
        zae(str, i2);
        this.zah[i3].copyStringToBuffer(i2, this.f12797b.getInt(str), charArrayBuffer);
    }

    public final void zad() {
        this.f12797b = new Bundle();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            String[] strArr = this.zag;
            if (i3 >= strArr.length) {
                break;
            }
            this.f12797b.putInt(strArr[i3], i3);
            i3++;
        }
        this.f12798c = new int[this.zah.length];
        int numRows = 0;
        while (true) {
            CursorWindow[] cursorWindowArr = this.zah;
            if (i2 >= cursorWindowArr.length) {
                this.f12799d = numRows;
                return;
            }
            this.f12798c[i2] = numRows;
            numRows += this.zah[i2].getNumRows() - (numRows - cursorWindowArr[i2].getStartPosition());
            i2++;
        }
    }

    DataHolder(int i2, String[] strArr, CursorWindow[] cursorWindowArr, int i3, Bundle bundle) {
        this.f12800e = false;
        this.zak = true;
        this.f12796a = i2;
        this.zag = strArr;
        this.zah = cursorWindowArr;
        this.zai = i3;
        this.zaj = bundle;
    }

    @KeepForSdk
    public DataHolder(@NonNull String[] strArr, @NonNull CursorWindow[] cursorWindowArr, int i2, @Nullable Bundle bundle) {
        this.f12800e = false;
        this.zak = true;
        this.f12796a = 1;
        this.zag = (String[]) Preconditions.checkNotNull(strArr);
        this.zah = (CursorWindow[]) Preconditions.checkNotNull(cursorWindowArr);
        this.zai = i2;
        this.zaj = bundle;
        zad();
    }

    /* JADX WARN: Finally extract failed */
    public DataHolder(@NonNull Cursor cursor, int i2, @Nullable Bundle bundle) {
        int startPosition;
        CursorWrapper cursorWrapper = new CursorWrapper(cursor);
        String[] columnNames = cursorWrapper.getColumnNames();
        ArrayList arrayList = new ArrayList();
        try {
            int count = cursorWrapper.getCount();
            CursorWindow window = cursorWrapper.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                startPosition = 0;
            } else {
                window.acquireReference();
                cursorWrapper.setWindow(null);
                arrayList.add(window);
                startPosition = window.getNumRows();
            }
            while (startPosition < count) {
                if (!cursorWrapper.moveToPosition(startPosition)) {
                    break;
                }
                CursorWindow window2 = cursorWrapper.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    cursorWrapper.setWindow(null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(startPosition);
                    cursorWrapper.fillWindow(startPosition, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                startPosition = window2.getStartPosition() + window2.getNumRows();
            }
            cursorWrapper.close();
            this(columnNames, (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]), i2, bundle);
        } catch (Throwable th) {
            cursorWrapper.close();
            throw th;
        }
    }

    private DataHolder(Builder builder, int i2, @Nullable Bundle bundle) {
        this(builder.zaa, zaf(builder, -1), i2, (Bundle) null);
    }
}
