package org.android.agoo.message;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.LruCache;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.j;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.MsgDO;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MessageService {
    public static final String MSG_ACCS_NOTIFY_CLICK = "8";
    public static final String MSG_ACCS_NOTIFY_DISMISS = "9";
    public static final String MSG_ACCS_READY_REPORT = "4";
    public static final String MSG_DB_COMPLETE = "100";
    public static final String MSG_DB_NOTIFY_CLICK = "2";
    public static final String MSG_DB_NOTIFY_DISMISS = "3";
    public static final String MSG_DB_NOTIFY_REACHED = "1";
    public static final String MSG_DB_READY_REPORT = "0";

    /* renamed from: a, reason: collision with root package name */
    private static Context f26551a;

    /* renamed from: c, reason: collision with root package name */
    private static LruCache<String, Integer> f26552c;

    /* renamed from: b, reason: collision with root package name */
    private volatile SQLiteOpenHelper f26553b = null;

    private static class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "message_accs_db", (SQLiteDatabase.CursorFactory) null, 3);
        }

        private String a() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("create table accs_message");
            stringBuffer.append("(");
            stringBuffer.append("id text UNIQUE not null,");
            stringBuffer.append("state text,");
            stringBuffer.append("message text,");
            stringBuffer.append("create_time date");
            stringBuffer.append(");");
            return stringBuffer.toString();
        }

        private String b() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("create table message");
            stringBuffer.append("(");
            stringBuffer.append("id text UNIQUE not null,");
            stringBuffer.append("state integer,");
            stringBuffer.append("body_code integer,");
            stringBuffer.append("report long,");
            stringBuffer.append("target_time long,");
            stringBuffer.append("interval integer,");
            stringBuffer.append("type text,");
            stringBuffer.append("message text,");
            stringBuffer.append("notify integer,");
            stringBuffer.append("create_time date");
            stringBuffer.append(");");
            return stringBuffer.toString();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public SQLiteDatabase getWritableDatabase() {
            if (j.a(super.getWritableDatabase().getPath(), 102400)) {
                return super.getWritableDatabase();
            }
            return null;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL(b());
                    sQLiteDatabase.execSQL("CREATE INDEX id_index ON message(id)");
                    sQLiteDatabase.execSQL("CREATE INDEX body_code_index ON message(body_code)");
                    sQLiteDatabase.execSQL(a());
                } catch (Throwable th) {
                    ALog.e("MessageService", "messagedbhelper create", th, new Object[0]);
                }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL("delete from message where create_time< date('now','-7 day') and state=1");
                } catch (Throwable th) {
                    try {
                        ALog.e("MessageService", "messagedbhelper create", th, new Object[0]);
                        try {
                            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accs_message");
                            sQLiteDatabase.execSQL(a());
                            return;
                        } catch (Throwable th2) {
                            ALog.e("MessageService", "MessageService onUpgrade is error", th2, new Object[0]);
                            return;
                        }
                    } catch (Throwable th3) {
                        try {
                            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accs_message");
                            sQLiteDatabase.execSQL(a());
                        } catch (Throwable th4) {
                            ALog.e("MessageService", "MessageService onUpgrade is error", th4, new Object[0]);
                        }
                        throw th3;
                    }
                }
            }
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accs_message");
                sQLiteDatabase.execSQL(a());
            } catch (Throwable th5) {
                ALog.e("MessageService", "MessageService onUpgrade is error", th5, new Object[0]);
            }
        }
    }

    public void a(Context context) {
        f26552c = new LruCache<>(100);
        f26551a = context;
        this.f26553b = new a(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0146 A[Catch: all -> 0x015e, TRY_LEAVE, TryCatch #7 {all -> 0x015e, blocks: (B:59:0x013e, B:61:0x0146), top: B:101:0x013e }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x016b A[Catch: all -> 0x0167, TRY_LEAVE, TryCatch #4 {all -> 0x0167, blocks: (B:66:0x0163, B:70:0x016b), top: B:95:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0163 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<org.android.agoo.common.MsgDO> b() {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.b():java.util.ArrayList");
    }

    public void a(String str, String str2) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i("MessageService", "updateAccsMessage sqlite3--->[" + str + ",state=" + str2 + "]", new Object[0]);
        }
        SQLiteDatabase writableDatabase = null;
        try {
        } catch (Throwable th) {
            try {
                if (ALog.isPrintLog(ALog.Level.E)) {
                    ALog.e("MessageService", "updateAccsMessage error,e--->[" + th + "],ex=" + th.getStackTrace().toString(), new Object[0]);
                }
                UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "updateAccsMessageFailed", th.toString());
                if (0 == 0) {
                    return;
                }
            } catch (Throwable th2) {
                if (0 != 0) {
                    writableDatabase.close();
                }
                throw th2;
            }
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            writableDatabase = this.f26553b.getWritableDatabase();
            if (writableDatabase == null) {
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
            } else {
                if (TextUtils.equals(str2, "1")) {
                    writableDatabase.execSQL("UPDATE accs_message set state = ? where id = ? and state = ?", new Object[]{str2, str, "0"});
                } else {
                    writableDatabase.execSQL("UPDATE accs_message set state = ? where id = ?", new Object[]{str2, str});
                }
                writableDatabase.close();
            }
        }
    }

    public void a(String str, String str2, String str3) {
        Cursor cursor;
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i("MessageService", "addAccsMessage sqlite3--->[" + str + ",message=" + str2 + ",state=" + str3 + "]", new Object[0]);
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                SQLiteDatabase writableDatabase = this.f26553b.getWritableDatabase();
                if (writableDatabase == null) {
                    if (writableDatabase != null) {
                        writableDatabase.close();
                        return;
                    }
                    return;
                }
                try {
                    Cursor cursorRawQuery = writableDatabase.rawQuery("select count(1) from accs_message where id = ?", new String[]{str});
                    if (cursorRawQuery != null && cursorRawQuery.moveToFirst() && cursorRawQuery.getInt(0) > 0) {
                        cursorRawQuery.close();
                        cursorRawQuery.close();
                        writableDatabase.close();
                    } else {
                        writableDatabase.execSQL("INSERT INTO accs_message VALUES(?,?,?,date('now'))", new Object[]{str, str3, str2});
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        writableDatabase.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = null;
                    sQLiteDatabase = writableDatabase;
                    try {
                        if (ALog.isPrintLog(ALog.Level.E)) {
                            ALog.e("MessageService", "addAccsMessage error,e--->[" + th + "],ex=" + a(th), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "addAccsMessageFailed", th.toString());
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.close();
                        }
                    } catch (Throwable th2) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.close();
                        }
                        throw th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }

    private String a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                stringBuffer.append(stackTraceElement.toString());
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    private MsgDO b(String str, String str2) {
        boolean z2;
        ALog.Level level = ALog.Level.I;
        if (ALog.isPrintLog(level)) {
            ALog.i("MessageService", "msgRecevie,message--->[" + str + "],utdid=" + j.b(f26551a), new Object[0]);
        }
        String string = null;
        if (TextUtils.isEmpty(str)) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.dealMessage", j.b(f26551a), "message==null");
            if (ALog.isPrintLog(level)) {
                ALog.i("MessageService", "handleMessage message==null,utdid=" + j.b(f26551a), new Object[0]);
            }
            return null;
        }
        MsgDO msgDO = new MsgDO();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            new Bundle();
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (jSONObject != null) {
                    String string2 = jSONObject.getString("p");
                    String string3 = jSONObject.getString("i");
                    String string4 = jSONObject.getString("b");
                    long j2 = jSONObject.getLong("f");
                    if (!jSONObject.isNull("ext")) {
                        string = jSONObject.getString("ext");
                    }
                    msgDO.msgIds = string3;
                    msgDO.extData = string;
                    msgDO.messageSource = "accs";
                    msgDO.type = "cache";
                    if (TextUtils.isEmpty(string4)) {
                        msgDO.errorCode = AgooConstants.ACK_BODY_NULL;
                    } else if (TextUtils.isEmpty(string2)) {
                        msgDO.errorCode = AgooConstants.ACK_PACK_NULL;
                    } else if (j2 == -1) {
                        msgDO.errorCode = AgooConstants.ACK_FLAG_NULL;
                    } else if (!a(f26551a, string2)) {
                        ALog.d("MessageService", "ondata checkpackage is del,pack=" + string2, new Object[0]);
                        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.dealMessage", j.b(f26551a), "deletePack", string2);
                        msgDO.removePacks = string2;
                    } else {
                        String string5 = a(j2, msgDO).getString(AgooConstants.MESSAGE_ENCRYPTED);
                        if (!f26551a.getPackageName().equals(string2)) {
                            z2 = true;
                        } else if (TextUtils.equals(Integer.toString(0), string5) || TextUtils.equals(Integer.toString(4), string5)) {
                            z2 = false;
                        } else {
                            msgDO.errorCode = AgooConstants.ACK_PACK_ERROR;
                            ALog.e("MessageService", "error encrypted: " + string5, new Object[0]);
                        }
                        msgDO.agooFlag = z2;
                        if (!TextUtils.isEmpty(str2)) {
                            msgDO.msgStatus = str2;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.e("MessageService", "createMsg is error,e: " + th, new Object[0]);
            }
        }
        return msgDO;
    }

    public void a(String str, String str2, String str3, int i2) {
        a(str, str2, str3, 1, -1L, -1, i2);
    }

    private void a(String str, String str2, String str3, int i2, long j2, int i3, int i4) {
        Throwable th;
        int iHashCode;
        String str4;
        StringBuilder sb = new StringBuilder();
        sb.append("add sqlite3--->[");
        sb.append(str);
        sb.append("]");
        ALog.d("MessageService", sb.toString(), new Object[0]);
        SQLiteClosable sQLiteClosable = null;
        try {
            String str5 = "";
            if (TextUtils.isEmpty(str2)) {
                iHashCode = -1;
                str4 = "";
            } else {
                iHashCode = str2.hashCode();
                str4 = str2;
            }
            if (!TextUtils.isEmpty(str3)) {
                str5 = str3;
            }
            if (f26552c.get(str) == null) {
                f26552c.put(str, Integer.valueOf(iHashCode));
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.i("MessageService", "addMessage,messageId=" + str + ", mCache size:" + f26552c.size(), new Object[0]);
                }
            }
            try {
                SQLiteDatabase writableDatabase = this.f26553b.getWritableDatabase();
                if (writableDatabase == null) {
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.close();
                            return;
                        } catch (Throwable th2) {
                            if (ALog.isPrintLog(ALog.Level.E)) {
                                ALog.e("MessageService", "addMessage,db.close(),error,e--->[" + th2 + "]", new Object[0]);
                            }
                            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "addMessageDBcloseFailed", th2.toString());
                            return;
                        }
                    }
                    return;
                }
                writableDatabase.execSQL("INSERT INTO message VALUES(?,?,?,?,?,?,?,?,?,date('now'))", new Object[]{str, Integer.valueOf(i2), Integer.valueOf(iHashCode), 0, Long.valueOf(j2), Integer.valueOf(i3), str5, str4, Integer.valueOf(i4)});
                try {
                    writableDatabase.close();
                } catch (Throwable th3) {
                    th = th3;
                    if (ALog.isPrintLog(ALog.Level.E)) {
                        ALog.e("MessageService", "addMessage,db.close(),error,e--->[" + th + "]", new Object[0]);
                    }
                    UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "addMessageDBcloseFailed", th.toString());
                }
            } catch (Throwable th4) {
                th = th4;
                try {
                    if (ALog.isPrintLog(ALog.Level.E)) {
                        ALog.e("MessageService", "addMessage error,e--->[" + th + "]", new Object[0]);
                    }
                    UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "addMessageFailed", th.toString());
                    if (0 != 0) {
                        try {
                            sQLiteClosable.close();
                        } catch (Throwable th5) {
                            th = th5;
                            if (ALog.isPrintLog(ALog.Level.E)) {
                                ALog.e("MessageService", "addMessage,db.close(),error,e--->[" + th + "]", new Object[0]);
                            }
                            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", j.b(f26551a), "addMessageDBcloseFailed", th.toString());
                        }
                    }
                } finally {
                }
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public void a() {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = this.f26553b.getWritableDatabase();
        } catch (Throwable th) {
            try {
                ALog.e("MessageService", "deleteCacheMessage sql Throwable", th, new Object[0]);
                if (0 == 0) {
                    return;
                }
            } catch (Throwable th2) {
                if (0 != 0) {
                    try {
                        writableDatabase.close();
                    } catch (Throwable unused) {
                    }
                }
                throw th2;
            }
        }
        if (writableDatabase == null) {
            if (writableDatabase != null) {
                try {
                    writableDatabase.close();
                    return;
                } catch (Throwable unused2) {
                    return;
                }
            }
            return;
        }
        writableDatabase.execSQL("delete from message where create_time< date('now','-7 day') and state=1");
        writableDatabase.execSQL("delete from accs_message where create_time< date('now','-1 day') ");
        try {
            writableDatabase.close();
        } catch (Throwable unused3) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            android.util.LruCache<java.lang.String, java.lang.Integer> r2 = org.android.agoo.message.MessageService.f26552c     // Catch: java.lang.Throwable -> L65
            java.lang.Object r2 = r2.get(r7)     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Throwable -> L65
            r3 = 1
            if (r2 == 0) goto L2f
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E     // Catch: java.lang.Throwable -> L65
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch: java.lang.Throwable -> L65
            if (r2 == 0) goto L2d
            java.lang.String r2 = "MessageService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r4.<init>()     // Catch: java.lang.Throwable -> L65
            java.lang.String r5 = "hasMessageDuplicate,msgid="
            r4.append(r5)     // Catch: java.lang.Throwable -> L65
            r4.append(r7)     // Catch: java.lang.Throwable -> L65
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L65
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L65
            com.taobao.accs.utl.ALog.e(r2, r4, r5)     // Catch: java.lang.Throwable -> L65
        L2d:
            r2 = r3
            goto L30
        L2f:
            r2 = r1
        L30:
            android.database.sqlite.SQLiteOpenHelper r4 = r6.f26553b     // Catch: java.lang.Throwable -> L63
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch: java.lang.Throwable -> L63
            if (r4 != 0) goto L3e
            if (r4 == 0) goto L3d
            r4.close()     // Catch: java.lang.Throwable -> L3d
        L3d:
            return r2
        L3e:
            java.lang.String r5 = "select count(1) from message where id = ?"
            java.lang.String[] r7 = new java.lang.String[]{r7}     // Catch: java.lang.Throwable -> L57
            android.database.Cursor r0 = r4.rawQuery(r5, r7)     // Catch: java.lang.Throwable -> L57
            if (r0 == 0) goto L59
            boolean r7 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L57
            if (r7 == 0) goto L59
            int r7 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L57
            if (r7 <= 0) goto L59
            goto L5a
        L57:
            r1 = r2
            goto L66
        L59:
            r3 = r2
        L5a:
            if (r0 == 0) goto L5f
            r0.close()     // Catch: java.lang.Throwable -> L71
        L5f:
            r4.close()     // Catch: java.lang.Throwable -> L71
            goto L71
        L63:
            r4 = r0
            goto L57
        L65:
            r4 = r0
        L66:
            if (r0 == 0) goto L6b
            r0.close()     // Catch: java.lang.Throwable -> L70
        L6b:
            if (r4 == 0) goto L70
            r4.close()     // Catch: java.lang.Throwable -> L70
        L70:
            r3 = r1
        L71:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r9, int r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            android.util.LruCache<java.lang.String, java.lang.Integer> r2 = org.android.agoo.message.MessageService.f26552c     // Catch: java.lang.Throwable -> L7c
            java.lang.Object r2 = r2.get(r9)     // Catch: java.lang.Throwable -> L7c
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Throwable -> L7c
            r3 = 1
            if (r2 == 0) goto L35
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L7c
            if (r10 != r2) goto L35
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E     // Catch: java.lang.Throwable -> L7c
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch: java.lang.Throwable -> L7c
            if (r2 == 0) goto L33
            java.lang.String r2 = "MessageService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c
            r4.<init>()     // Catch: java.lang.Throwable -> L7c
            java.lang.String r5 = "hasMessageDuplicate,msgid="
            r4.append(r5)     // Catch: java.lang.Throwable -> L7c
            r4.append(r9)     // Catch: java.lang.Throwable -> L7c
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L7c
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L7c
            com.taobao.accs.utl.ALog.e(r2, r4, r5)     // Catch: java.lang.Throwable -> L7c
        L33:
            r2 = r3
            goto L36
        L35:
            r2 = r1
        L36:
            android.database.sqlite.SQLiteOpenHelper r4 = r8.f26553b     // Catch: java.lang.Throwable -> L7a
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch: java.lang.Throwable -> L7a
            if (r4 != 0) goto L44
            if (r4 == 0) goto L43
            r4.close()     // Catch: java.lang.Throwable -> L43
        L43:
            return r2
        L44:
            java.lang.String r5 = "select count(1) from message where id = ? and body_code=? create_time< date('now','-1 day')"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6e
            r6.<init>()     // Catch: java.lang.Throwable -> L6e
            java.lang.String r7 = ""
            r6.append(r7)     // Catch: java.lang.Throwable -> L6e
            r6.append(r10)     // Catch: java.lang.Throwable -> L6e
            java.lang.String r10 = r6.toString()     // Catch: java.lang.Throwable -> L6e
            java.lang.String[] r9 = new java.lang.String[]{r9, r10}     // Catch: java.lang.Throwable -> L6e
            android.database.Cursor r0 = r4.rawQuery(r5, r9)     // Catch: java.lang.Throwable -> L6e
            if (r0 == 0) goto L70
            boolean r9 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L6e
            if (r9 == 0) goto L70
            int r9 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L6e
            if (r9 <= 0) goto L70
            goto L71
        L6e:
            r1 = r2
            goto L7d
        L70:
            r3 = r2
        L71:
            if (r0 == 0) goto L76
            r0.close()     // Catch: java.lang.Throwable -> L88
        L76:
            r4.close()     // Catch: java.lang.Throwable -> L88
            goto L88
        L7a:
            r4 = r0
            goto L6e
        L7c:
            r4 = r0
        L7d:
            if (r0 == 0) goto L82
            r0.close()     // Catch: java.lang.Throwable -> L87
        L82:
            if (r4 == 0) goto L87
            r4.close()     // Catch: java.lang.Throwable -> L87
        L87:
            r3 = r1
        L88:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.a(java.lang.String, int):boolean");
    }

    public static final boolean a(Context context, String str) {
        return context.getPackageManager().getApplicationInfo(str, 0) != null;
    }

    private static Bundle a(long j2, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j2).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(Integer.parseInt("" + charArray[1] + charArray[2] + charArray[3] + charArray[4], 2));
                    bundle.putString(AgooConstants.MESSAGE_ENCRYPTED, sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_REPORT, "1");
                        msgDO.reportStr = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_NOTIFICATION, "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_HAS_TEST, "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_DUPLICATE, "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt(AgooConstants.MESSAGE_POPUP, 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }
}
