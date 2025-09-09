package com.taobao.accs.utl;

import android.content.SharedPreferences;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;

/* loaded from: classes4.dex */
public class q {
    public static final int MAX_FAIL_TIMES = 3;

    public static void a() {
        try {
            int iC = c();
            if (iC > 0) {
                SharedPreferences.Editor editorEdit = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).edit();
                editorEdit.clear();
                editorEdit.apply();
                ALog.i("LoadSoFailUtil", "loadSoSuccess", "fail times", Integer.valueOf(iC));
            }
        } catch (Throwable th) {
            ALog.e("LoadSoFailUtil", "loadSoSuccess", th, new Object[0]);
        }
    }

    public static void b() {
        try {
            SharedPreferences sharedPreferences = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0);
            int i2 = sharedPreferences.getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0) + 1;
            if (i2 > 0) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putInt(Constants.SP_KEY_LOAD_SO_TIMES, i2);
                editorEdit.apply();
            }
            ALog.e("LoadSoFailUtil", "loadSoFail", Constants.KEY_TIMES, Integer.valueOf(i2));
        } catch (Throwable th) {
            ALog.e("LoadSoFailUtil", "loadSoFail", th, new Object[0]);
        }
    }

    public static int c() {
        int i2;
        try {
            i2 = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0);
        } catch (Throwable th) {
            th = th;
            i2 = 0;
        }
        try {
            ALog.i("LoadSoFailUtil", "getSoFailTimes", Constants.KEY_TIMES, Integer.valueOf(i2));
        } catch (Throwable th2) {
            th = th2;
            ALog.e("LoadSoFailUtil", "getSoFailTimes", th, new Object[0]);
            return i2;
        }
        return i2;
    }
}
