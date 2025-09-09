package com.tekartik.sqflite;

import android.database.Cursor;
import android.util.Log;
import com.tekartik.sqflite.dev.Debug;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class Utils {
    static Locale a(String str) {
        return Locale.forLanguageTag(str);
    }

    static Locale b(String str) {
        return a(str);
    }

    public static List<Object> cursorRowToList(Cursor cursor, int i2) {
        String name;
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            Object objCursorValue = cursorValue(cursor, i3);
            if (Debug.EXTRA_LOGV) {
                if (objCursorValue == null) {
                    name = null;
                } else if (objCursorValue.getClass().isArray()) {
                    name = "array(" + objCursorValue.getClass().getComponentType().getName() + ")";
                } else {
                    name = objCursorValue.getClass().getName();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("column ");
                sb.append(i3);
                sb.append(" ");
                sb.append(cursor.getType(i3));
                sb.append(": ");
                sb.append(objCursorValue);
                sb.append(name == null ? "" : " (" + name + ")");
                Log.d(Constant.TAG, sb.toString());
            }
            arrayList.add(objCursorValue);
        }
        return arrayList;
    }

    public static Object cursorValue(Cursor cursor, int i2) {
        int type = cursor.getType(i2);
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i2));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i2));
        }
        if (type == 3) {
            return cursor.getString(i2);
        }
        if (type != 4) {
            return null;
        }
        return cursor.getBlob(i2);
    }
}
