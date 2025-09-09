package com.umeng.message.component;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import com.umeng.message.common.UPLog;
import com.umeng.message.proguard.ap;
import com.umeng.message.proguard.h;
import com.umeng.message.proguard.x;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes4.dex */
public class UmengMessageProvider extends ContentProvider {

    /* renamed from: a, reason: collision with root package name */
    private final UriMatcher f22617a = new UriMatcher(-1);

    /* renamed from: b, reason: collision with root package name */
    private volatile a f22618b;

    /* renamed from: c, reason: collision with root package name */
    private volatile b f22619c;

    static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "MessageStore.db", (SQLiteDatabase.CursorFactory) null, 5);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("create table if not exists %s (time long, type varchar default NULL, alias varchar default NULL, exclusive int, ttl long, PRIMARY KEY(time))", "MsgAlias"));
            } catch (Throwable th) {
                UPLog.e("MessageProvider", th);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            try {
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgAlias")) {
                    sQLiteDatabase.execSQL("drop table MsgAlias");
                }
                onCreate(sQLiteDatabase);
            } catch (Throwable th) {
                UPLog.e("MessageProvider", th);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            if (i2 <= 3) {
                try {
                    if (UmengMessageProvider.b(sQLiteDatabase, "MsgTemp")) {
                        sQLiteDatabase.execSQL("drop table MsgTemp");
                    }
                    if (UmengMessageProvider.b(sQLiteDatabase, "MessageStore")) {
                        sQLiteDatabase.execSQL("drop table MessageStore");
                    }
                } catch (Throwable th) {
                    UPLog.e("MessageProvider", th);
                    return;
                }
            }
            if (i2 <= 4 && UmengMessageProvider.b(sQLiteDatabase, "MsgAlias")) {
                sQLiteDatabase.execSQL("drop table MsgAlias");
            }
            onCreate(sQLiteDatabase);
        }
    }

    static class b extends SQLiteOpenHelper {
        public b(Context context) {
            super(context, "MsgLogStore.db", (SQLiteDatabase.CursorFactory) null, 8);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("create table if not exists MsgLogStore (MsgId varchar, ActionType Integer, Time long, %s varchar, PRIMARY KEY(MsgId, ActionType))", com.alipay.sdk.m.l.b.f9444k));
                sQLiteDatabase.execSQL("create table if not exists InAppLogStore (Time long, MsgId varchar, MsgType Integer, NumDisplay Integer, NumOpenFull Integer, NumOpenTop Integer, NumOpenBottom Integer, NumClose Integer, NumDuration Integer, NumCustom Integer, PRIMARY KEY(Time))");
            } catch (Throwable th) {
                UPLog.e("MessageProvider", th);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            try {
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgLogStore")) {
                    sQLiteDatabase.execSQL("drop table MsgLogStore");
                }
                if (UmengMessageProvider.b(sQLiteDatabase, "InAppLogStore")) {
                    sQLiteDatabase.execSQL("drop table InAppLogStore");
                }
                onCreate(sQLiteDatabase);
            } catch (Throwable th) {
                UPLog.e("MessageProvider", th);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            if (i2 <= 5) {
                try {
                    if (UmengMessageProvider.b(sQLiteDatabase, "MsgLogStore")) {
                        sQLiteDatabase.execSQL(String.format("ALTER TABLE MsgLogStore ADD COLUMN %s varchar", com.alipay.sdk.m.l.b.f9444k));
                    }
                } catch (Throwable th) {
                    UPLog.e("MessageProvider", th);
                    return;
                }
            }
            if (i2 <= 6 && UmengMessageProvider.b(sQLiteDatabase, "InAppLogStore")) {
                sQLiteDatabase.execSQL(String.format("ALTER TABLE InAppLogStore ADD COLUMN %s Integer", "NumCustom"));
            }
            if (i2 <= 7) {
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgLogStoreForAgoo")) {
                    sQLiteDatabase.execSQL("drop table MsgLogStoreForAgoo");
                }
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgLogIdTypeStore")) {
                    sQLiteDatabase.execSQL("drop table MsgLogIdTypeStore");
                }
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgLogIdTypeStoreForAgoo")) {
                    sQLiteDatabase.execSQL("drop table MsgLogIdTypeStoreForAgoo");
                }
                if (UmengMessageProvider.b(sQLiteDatabase, "MsgConfigInfo")) {
                    sQLiteDatabase.execSQL("drop table MsgConfigInfo");
                }
            }
            onCreate(sQLiteDatabase);
        }
    }

    private b b() {
        if (this.f22619c == null) {
            synchronized (b.class) {
                try {
                    if (this.f22619c == null) {
                        this.f22619c = new b(getContext());
                    }
                } finally {
                }
            }
        }
        return this.f22619c;
    }

    @Override // android.content.ContentProvider
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        SQLiteDatabase writableDatabase = b().getWritableDatabase();
        try {
            if (writableDatabase == null) {
                return super.applyBatch(arrayList);
            }
            try {
                writableDatabase.beginTransaction();
                ContentProviderResult[] contentProviderResultArrApplyBatch = super.applyBatch(arrayList);
                writableDatabase.setTransactionSuccessful();
                return contentProviderResultArrApplyBatch;
            } finally {
                writableDatabase.endTransaction();
            }
        } catch (Throwable unused) {
            return super.applyBatch(arrayList);
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        SQLiteDatabase writableDatabase;
        try {
            int iMatch = this.f22617a.match(uri);
            if (iMatch != 2) {
                if (iMatch == 3) {
                    SQLiteDatabase writableDatabase2 = a().getWritableDatabase();
                    if (writableDatabase2 != null) {
                        return writableDatabase2.delete("MsgAlias", str, strArr);
                    }
                } else if (iMatch == 4) {
                    SQLiteDatabase writableDatabase3 = a().getWritableDatabase();
                    if (writableDatabase3 != null) {
                        return writableDatabase3.delete("MsgAlias", null, null);
                    }
                } else if (iMatch == 5) {
                    SQLiteDatabase writableDatabase4 = b().getWritableDatabase();
                    if (writableDatabase4 != null) {
                        return writableDatabase4.delete("MsgLogStore", str, strArr);
                    }
                } else if (iMatch == 10 && (writableDatabase = b().getWritableDatabase()) != null) {
                    return writableDatabase.delete("InAppLogStore", str, strArr);
                }
            } else if (strArr != null && strArr.length != 0) {
                ap apVarA = ap.a();
                if (strArr.length == 0) {
                    return 0;
                }
                SharedPreferences.Editor editorEdit = apVarA.f22784a.edit();
                int i2 = 0;
                for (String str2 : strArr) {
                    if (apVarA.f22784a.contains(str2)) {
                        editorEdit.remove(str2);
                        i2++;
                    }
                }
                if (i2 > 0) {
                    editorEdit.apply();
                }
                return i2;
            }
        } catch (Throwable th) {
            UPLog.e("MessageProvider", th);
        }
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int iMatch = this.f22617a.match(uri);
        if (iMatch == 2 || iMatch == 3 || iMatch == 5) {
            return "vnd.android.cursor.dir/vnd.umeng.message";
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        try {
            int iMatch = this.f22617a.match(uri);
            if (iMatch != 2) {
                if (iMatch == 3) {
                    SQLiteDatabase writableDatabase = a().getWritableDatabase();
                    if (writableDatabase == null) {
                        return null;
                    }
                    writableDatabase.insertWithOnConflict("MsgAlias", null, contentValues, 5);
                } else if (iMatch == 5) {
                    SQLiteDatabase writableDatabase2 = b().getWritableDatabase();
                    if (writableDatabase2 == null) {
                        return null;
                    }
                    writableDatabase2.insertWithOnConflict("MsgLogStore", null, contentValues, 5);
                } else if (iMatch == 10) {
                    SQLiteDatabase writableDatabase3 = b().getWritableDatabase();
                    if (writableDatabase3 == null) {
                        return null;
                    }
                    writableDatabase3.insertWithOnConflict("InAppLogStore", null, contentValues, 5);
                }
            } else {
                if (contentValues == null) {
                    return null;
                }
                String asString = contentValues.getAsString("k");
                String asString2 = contentValues.getAsString("v");
                if (!TextUtils.isEmpty(asString)) {
                    ap.a().a(asString, asString2);
                }
            }
        } catch (Throwable th) {
            UPLog.e("MessageProvider", th);
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        try {
            Context context = getContext();
            x.a(context);
            Uri uriA = h.a(context);
            this.f22617a.addURI(uriA.getAuthority(), uriA.getPath(), 3);
            Uri uriB = h.b(context);
            this.f22617a.addURI(uriB.getAuthority(), uriB.getPath(), 4);
            Uri uriD = h.d(context);
            this.f22617a.addURI(uriD.getAuthority(), uriD.getPath(), 5);
            Uri uriE = h.e(context);
            this.f22617a.addURI(uriE.getAuthority(), uriE.getPath(), 10);
            Uri uriC = h.c(context);
            this.f22617a.addURI(uriC.getAuthority(), uriC.getPath(), 2);
            return true;
        } catch (Throwable th) {
            UPLog.e("MessageProvider", th);
            return true;
        }
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteDatabase readableDatabase;
        try {
            int iMatch = this.f22617a.match(uri);
            if (iMatch != 2) {
                if (iMatch == 3) {
                    SQLiteDatabase readableDatabase2 = a().getReadableDatabase();
                    if (readableDatabase2 == null) {
                        return null;
                    }
                    return readableDatabase2.query("MsgAlias", strArr, str, strArr2, null, null, str2);
                }
                if (iMatch != 5) {
                    if (iMatch == 10 && (readableDatabase = b().getReadableDatabase()) != null) {
                        return readableDatabase.query("InAppLogStore", strArr, str, strArr2, null, null, str2);
                    }
                    return null;
                }
                SQLiteDatabase readableDatabase3 = b().getReadableDatabase();
                if (readableDatabase3 == null) {
                    return null;
                }
                return readableDatabase3.query("MsgLogStore", strArr, str, strArr2, null, null, str2);
            }
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"k", "v"});
            Map<String, ?> all = ap.a().f22784a.getAll();
            if (strArr2 != null && strArr2.length != 0) {
                for (String str3 : strArr2) {
                    Object obj = all.get(str3);
                    if (obj instanceof String) {
                        matrixCursor.addRow(new Object[]{str3, obj});
                    }
                }
                return matrixCursor;
            }
            for (String str4 : all.keySet()) {
                Object obj2 = all.get(str4);
                if (obj2 instanceof String) {
                    matrixCursor.addRow(new Object[]{str4, obj2});
                }
            }
            return matrixCursor;
        } catch (Throwable th) {
            UPLog.e("MessageProvider", th);
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        SQLiteDatabase writableDatabase;
        try {
            int iMatch = this.f22617a.match(uri);
            if (iMatch != 2) {
                if (iMatch == 3) {
                    SQLiteDatabase writableDatabase2 = a().getWritableDatabase();
                    if (writableDatabase2 != null) {
                        return writableDatabase2.updateWithOnConflict("MsgAlias", contentValues, str, strArr, 5);
                    }
                } else if (iMatch == 10 && (writableDatabase = b().getWritableDatabase()) != null) {
                    return writableDatabase.updateWithOnConflict("InAppLogStore", contentValues, str, strArr, 5);
                }
            } else if (contentValues != null && strArr != null && strArr.length != 0) {
                String str2 = strArr[0];
                String asString = contentValues.getAsString("v");
                if (!TextUtils.isEmpty(str2)) {
                    ap.a().a(str2, asString);
                    return 1;
                }
            }
        } catch (Throwable th) {
            UPLog.e("MessageProvider", th);
        }
        return 0;
    }

    private a a() {
        if (this.f22618b == null) {
            synchronized (a.class) {
                try {
                    if (this.f22618b == null) {
                        this.f22618b = new a(getContext());
                    }
                } finally {
                }
            }
        }
        return this.f22618b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean b(android.database.sqlite.SQLiteDatabase r4, java.lang.String r5) {
        /*
            r0 = 1
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            r2 = 0
            if (r1 == 0) goto L9
            return r2
        L9:
            java.lang.String r1 = "select count(*) as c from sqlite_master where type = 'table' and name = '%s'"
            java.lang.String r5 = r5.trim()     // Catch: java.lang.Throwable -> L2b
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L2b
            r3[r2] = r5     // Catch: java.lang.Throwable -> L2b
            java.lang.String r5 = java.lang.String.format(r1, r3)     // Catch: java.lang.Throwable -> L2b
            r1 = 0
            android.database.Cursor r4 = r4.rawQuery(r5, r1)     // Catch: java.lang.Throwable -> L2b
            if (r4 == 0) goto L3a
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L2b
            if (r5 == 0) goto L2d
            int r5 = r4.getInt(r2)     // Catch: java.lang.Throwable -> L2b
            if (r5 <= 0) goto L2d
            goto L2e
        L2b:
            r4 = move-exception
            goto L35
        L2d:
            r0 = r2
        L2e:
            r4.close()     // Catch: java.lang.Throwable -> L33
            r2 = r0
            goto L3a
        L33:
            r4 = move-exception
            r2 = r0
        L35:
            java.lang.String r5 = "MessageProvider"
            com.umeng.message.common.UPLog.e(r5, r4)
        L3a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.component.UmengMessageProvider.b(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }
}
