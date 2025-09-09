package com.xiaomi.push.providers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.accs.common.Constants;
import com.umeng.analytics.pro.bc;
import com.xiaomi.channel.commonutils.logger.b;

/* loaded from: classes4.dex */
public class a extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static int f24410a = 1;

    /* renamed from: a, reason: collision with other field name */
    public static final Object f937a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f938a = {"package_name", "TEXT", "message_ts", " LONG DEFAULT 0 ", "bytes", " LONG DEFAULT 0 ", bc.T, " INT DEFAULT -1 ", "rcv", " INT DEFAULT -1 ", Constants.KEY_IMSI, "TEXT"};

    public a(Context context) {
        super(context, "traffic.db", (SQLiteDatabase.CursorFactory) null, f24410a);
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE traffic(_id INTEGER  PRIMARY KEY ,");
        int i2 = 0;
        while (true) {
            String[] strArr = f938a;
            if (i2 >= strArr.length - 1) {
                sb.append(");");
                sQLiteDatabase.execSQL(sb.toString());
                return;
            }
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(strArr[i2]);
            sb.append(" ");
            sb.append(strArr[i2 + 1]);
            i2 += 2;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        synchronized (f937a) {
            try {
                a(sQLiteDatabase);
            } catch (SQLException e2) {
                b.a(e2);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
