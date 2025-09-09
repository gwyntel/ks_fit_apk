package com.umeng.analytics.process;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.umeng.analytics.process.DBFileTraversalUtil;
import com.umeng.analytics.process.a;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.utils.FileLockCallback;
import com.umeng.commonsdk.utils.FileLockUtil;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UMProcessDBHelper {
    private static UMProcessDBHelper mInstance;
    private Context mContext;
    private FileLockUtil mFileLock = new FileLockUtil();
    private InsertEventCallback ekvCallBack = new InsertEventCallback();

    private class InsertEventCallback implements FileLockCallback {
        private InsertEventCallback() {
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i2) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) throws Throwable {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            String str2 = com.umeng.analytics.process.a.f21976c;
            if (str.startsWith(str2)) {
                str = str.replaceFirst(str2, "");
            }
            UMProcessDBHelper.this.insertEvents(str.replace(com.umeng.analytics.process.a.f21977d, ""), (JSONArray) obj);
            return true;
        }
    }

    private class ProcessToMainCallback implements FileLockCallback {
        private ProcessToMainCallback() {
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(File file, int i2) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str, Object obj) {
            return false;
        }

        @Override // com.umeng.commonsdk.utils.FileLockCallback
        public boolean onFileLock(String str) throws Throwable {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            String str2 = com.umeng.analytics.process.a.f21976c;
            if (str.startsWith(str2)) {
                str = str.replaceFirst(str2, "");
            }
            UMProcessDBHelper.this.processToMain(str.replace(com.umeng.analytics.process.a.f21977d, ""));
            return true;
        }
    }

    private class a implements Serializable {

        /* renamed from: a, reason: collision with root package name */
        int f21965a;

        /* renamed from: b, reason: collision with root package name */
        String f21966b;

        /* renamed from: c, reason: collision with root package name */
        String f21967c;

        /* renamed from: d, reason: collision with root package name */
        String f21968d;

        /* renamed from: e, reason: collision with root package name */
        int f21969e;

        /* renamed from: f, reason: collision with root package name */
        String f21970f;

        /* renamed from: g, reason: collision with root package name */
        String f21971g;

        /* renamed from: h, reason: collision with root package name */
        String f21972h;

        private a() {
        }
    }

    private UMProcessDBHelper() {
    }

    private List<a> datasAdapter(String str, JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                a aVar = new a();
                aVar.f21967c = jSONObject.optString("id");
                aVar.f21971g = UMUtils.getAppVersionName(this.mContext);
                aVar.f21972h = UMUtils.getAppVersionCode(this.mContext);
                aVar.f21966b = jSONObject.optString("__i");
                aVar.f21969e = jSONObject.optInt("__t");
                aVar.f21970f = str;
                if (jSONObject.has("ds")) {
                    jSONObject.remove("ds");
                }
                jSONObject.put("ds", getDataSource());
                jSONObject.remove("__i");
                jSONObject.remove("__t");
                aVar.f21968d = com.umeng.common.a.a().a(jSONObject.toString());
                jSONObject.remove("ds");
                arrayList.add(aVar);
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    private boolean dbIsExists(String str) {
        try {
            return new File(b.b(this.mContext, str)).exists();
        } catch (Throwable unused) {
            return false;
        }
    }

    private int getDataSource() {
        return 0;
    }

    public static UMProcessDBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UMProcessDBHelper.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new UMProcessDBHelper(context);
                    }
                } finally {
                }
            }
        }
        UMProcessDBHelper uMProcessDBHelper = mInstance;
        uMProcessDBHelper.mContext = context;
        return uMProcessDBHelper;
    }

    private boolean insertEvents_(String str, List<a> list) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty()) {
            return true;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(str);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                sQLiteDatabaseA.beginTransaction();
                for (a aVar : list) {
                    try {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("__i", aVar.f21966b);
                        contentValues.put("__e", aVar.f21967c);
                        contentValues.put("__t", Integer.valueOf(aVar.f21969e));
                        contentValues.put(a.InterfaceC0175a.f21987f, aVar.f21970f);
                        contentValues.put("__av", aVar.f21971g);
                        contentValues.put("__vc", aVar.f21972h);
                        contentValues.put("__s", aVar.f21968d);
                        sQLiteDatabaseA.insert(a.InterfaceC0175a.f21982a, null, contentValues);
                    } catch (Exception unused2) {
                    }
                }
                sQLiteDatabaseA.setTransactionSuccessful();
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
                c.a(this.mContext).b(str);
                return true;
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabase = sQLiteDatabaseA;
                if (sQLiteDatabase != null) {
                    try {
                        sQLiteDatabase.endTransaction();
                    } catch (Throwable unused4) {
                    }
                }
                c.a(this.mContext).b(str);
                throw th;
            }
        } catch (Exception unused5) {
            sQLiteDatabase = sQLiteDatabaseA;
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Throwable unused6) {
                }
            }
            c.a(this.mContext).b(str);
            return false;
        }
    }

    private boolean processIsService(Context context) {
        return context.getPackageManager().getServiceInfo(new ComponentName(context, this.mContext.getClass()), 0) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processToMain(String str) throws Throwable {
        if (dbIsExists(str)) {
            List<a> eventByProcess = readEventByProcess(str);
            if (!eventByProcess.isEmpty() && insertEvents_(com.umeng.analytics.process.a.f21981h, eventByProcess)) {
                deleteEventDatas(str, null, eventByProcess);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c4 A[Catch: Exception -> 0x00c7, TRY_LEAVE, TryCatch #4 {Exception -> 0x00c7, blocks: (B:33:0x00bf, B:35:0x00c4), top: B:44:0x00bf }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.umeng.analytics.process.UMProcessDBHelper$1] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<com.umeng.analytics.process.UMProcessDBHelper.a> readEventByProcess(java.lang.String r13) throws java.lang.Throwable {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.content.Context r2 = r12.mContext     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            com.umeng.analytics.process.c r2 = com.umeng.analytics.process.c.a(r2)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            android.database.sqlite.SQLiteDatabase r2 = r2.a(r13)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r2.beginTransaction()     // Catch: java.lang.Throwable -> La3 java.lang.Exception -> La5
            java.lang.String r4 = "__et_p"
            r9 = 0
            r10 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r2
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> La3 java.lang.Exception -> La5
            if (r3 == 0) goto L91
        L22:
            boolean r4 = r3.moveToNext()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r4 == 0) goto L91
            com.umeng.analytics.process.UMProcessDBHelper$a r4 = new com.umeng.analytics.process.UMProcessDBHelper$a     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.<init>()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r5 = 0
            int r5 = r3.getInt(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21965a = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__i"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21966b = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__e"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21967c = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__s"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21968d = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__t"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            int r5 = r3.getInt(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21969e = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__pn"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21970f = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__av"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21971g = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = "__vc"
            int r5 = r3.getColumnIndex(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r4.f21972h = r5     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r0.add(r4)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            goto L22
        L8c:
            r0 = move-exception
            r1 = r3
            goto Lbd
        L8f:
            r1 = move-exception
            goto Lb1
        L91:
            if (r3 == 0) goto L96
            r3.close()     // Catch: java.lang.Exception -> L99
        L96:
            r2.endTransaction()     // Catch: java.lang.Exception -> L99
        L99:
            android.content.Context r1 = r12.mContext
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)
            r1.b(r13)
            goto Lbc
        La3:
            r0 = move-exception
            goto Lbd
        La5:
            r3 = move-exception
            r11 = r3
            r3 = r1
            r1 = r11
            goto Lb1
        Laa:
            r0 = move-exception
            r2 = r1
            goto Lbd
        Lad:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
        Lb1:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L8c
            if (r3 == 0) goto Lb9
            r3.close()     // Catch: java.lang.Exception -> L99
        Lb9:
            if (r2 == 0) goto L99
            goto L96
        Lbc:
            return r0
        Lbd:
            if (r1 == 0) goto Lc2
            r1.close()     // Catch: java.lang.Exception -> Lc7
        Lc2:
            if (r2 == 0) goto Lc7
            r2.endTransaction()     // Catch: java.lang.Exception -> Lc7
        Lc7:
            android.content.Context r1 = r12.mContext
            com.umeng.analytics.process.c r1 = com.umeng.analytics.process.c.a(r1)
            r1.b(r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readEventByProcess(java.lang.String):java.util.List");
    }

    public void createDBByProcess(String str) {
        try {
            c.a(this.mContext).a(str);
            c.a(this.mContext).b(str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void deleteEventDatas(String str, String str2, List<a> list) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        Throwable th;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(str);
        } catch (Exception unused) {
        } catch (Throwable th2) {
            sQLiteDatabaseA = null;
            th = th2;
        }
        try {
            sQLiteDatabaseA.beginTransaction();
            int size = list.size();
            if (size > 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    sQLiteDatabaseA.execSQL("delete from __et_p where rowid=" + list.get(i2).f21965a);
                }
            } else {
                sQLiteDatabaseA.delete(a.InterfaceC0175a.f21982a, null, null);
            }
            sQLiteDatabaseA.setTransactionSuccessful();
            sQLiteDatabaseA.endTransaction();
        } catch (Exception unused2) {
            sQLiteDatabase = sQLiteDatabaseA;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
            c.a(this.mContext).b(str);
        } catch (Throwable th3) {
            th = th3;
            if (sQLiteDatabaseA != null) {
                sQLiteDatabaseA.endTransaction();
            }
            c.a(this.mContext).b(str);
            throw th;
        }
        c.a(this.mContext).b(str);
    }

    public void deleteMainProcessEventDatasByIds(List<Integer> list) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            sQLiteDatabaseA = c.a(this.mContext).a(com.umeng.analytics.process.a.f21981h);
            sQLiteDatabaseA.beginTransaction();
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                sQLiteDatabaseA.delete(a.InterfaceC0175a.f21982a, "id=?", new String[]{String.valueOf(it.next())});
            }
            sQLiteDatabaseA.setTransactionSuccessful();
        } catch (Exception unused) {
            if (sQLiteDatabaseA != null) {
            }
        } catch (Throwable th) {
            if (sQLiteDatabaseA != null) {
                sQLiteDatabaseA.endTransaction();
            }
            c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
            throw th;
        }
        sQLiteDatabaseA.endTransaction();
        c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
    }

    public void insertEvents(String str, JSONArray jSONArray) throws Throwable {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            insertEvents_(str, datasAdapter(str, jSONArray));
        }
    }

    public void insertEventsInSubProcess(String str, JSONArray jSONArray) throws Throwable {
        if (AnalyticsConstants.SUB_PROCESS_EVENT && !TextUtils.isEmpty(str)) {
            File file = new File(b.b(this.mContext, str));
            if (file.exists()) {
                this.mFileLock.doFileOperateion(file, this.ekvCallBack, jSONArray);
            } else {
                insertEvents(str, jSONArray);
            }
        }
    }

    public void processDBToMain() {
        try {
            DBFileTraversalUtil.traverseDBFiles(b.a(this.mContext), new ProcessToMainCallback(), new DBFileTraversalUtil.a() { // from class: com.umeng.analytics.process.UMProcessDBHelper.1
                @Override // com.umeng.analytics.process.DBFileTraversalUtil.a
                public void a() {
                    if (AnalyticsConstants.SUB_PROCESS_EVENT) {
                        UMWorkDispatch.sendEvent(UMProcessDBHelper.this.mContext, UMProcessDBDatasSender.UM_PROCESS_CONSTRUCTMESSAGE, UMProcessDBDatasSender.getInstance(UMProcessDBHelper.this.mContext), null);
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x018b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject readMainEvents(long r20, java.util.List<java.lang.Integer> r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.process.UMProcessDBHelper.readMainEvents(long, java.util.List):org.json.JSONObject");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r3v6 */
    public JSONObject readVersionInfoFromColumId(Integer num) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        JSONObject jSONObject;
        ?? r3 = 0;
        jSONObject = null;
        JSONObject jSONObject2 = null;
        cursor = null;
        Cursor cursor = null;
        r3 = 0;
        try {
            try {
                sQLiteDatabaseA = c.a(this.mContext).a(com.umeng.analytics.process.a.f21981h);
                try {
                    sQLiteDatabaseA.beginTransaction();
                    Cursor cursorQuery = sQLiteDatabaseA.query(a.InterfaceC0175a.f21982a, null, "rowid=?", new String[]{String.valueOf(num)}, null, null, null);
                    if (cursorQuery != null) {
                        try {
                            try {
                                if (cursorQuery.moveToNext()) {
                                    jSONObject = new JSONObject();
                                    try {
                                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("__av"));
                                        String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("__vc"));
                                        if (!TextUtils.isEmpty(string)) {
                                            jSONObject.put("__av", string);
                                        }
                                        if (!TextUtils.isEmpty(string2)) {
                                            jSONObject.put("__vc", string2);
                                        }
                                        jSONObject2 = jSONObject;
                                    } catch (Exception e2) {
                                        e = e2;
                                        cursor = cursorQuery;
                                        e.printStackTrace();
                                        if (cursor != null) {
                                            try {
                                                cursor.close();
                                            } catch (Exception unused) {
                                                c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
                                                r3 = jSONObject;
                                                return r3;
                                            }
                                        }
                                        if (sQLiteDatabaseA != null) {
                                            sQLiteDatabaseA.endTransaction();
                                        }
                                        c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
                                        r3 = jSONObject;
                                        return r3;
                                    }
                                }
                            } catch (Exception e3) {
                                e = e3;
                                jSONObject = null;
                            }
                        } catch (Throwable th) {
                            th = th;
                            r3 = cursorQuery;
                            if (r3 != 0) {
                                try {
                                    r3.close();
                                } catch (Exception unused2) {
                                    c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
                                    throw th;
                                }
                            }
                            if (sQLiteDatabaseA != null) {
                                sQLiteDatabaseA.endTransaction();
                            }
                            c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
                            throw th;
                        }
                    }
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception unused3) {
                        }
                    }
                    sQLiteDatabaseA.endTransaction();
                    c.a(this.mContext).b(com.umeng.analytics.process.a.f21981h);
                    r3 = jSONObject2;
                } catch (Exception e4) {
                    e = e4;
                    jSONObject = null;
                }
            } catch (Exception e5) {
                e = e5;
                sQLiteDatabaseA = null;
                jSONObject = null;
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabaseA = null;
            }
            return r3;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private UMProcessDBHelper(Context context) {
        com.umeng.common.a.a().a(context);
    }
}
