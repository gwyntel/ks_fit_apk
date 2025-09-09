package com.tekartik.sqflite;

import android.database.sqlite.SQLiteProgram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class SqlCommand {
    private final List<Object> rawArguments;
    private final String sql;

    public SqlCommand(String str, List<Object> list) {
        this.sql = str;
        this.rawArguments = list == null ? new ArrayList<>() : list;
    }

    private Object[] getSqlArguments(List<Object> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator<Object> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toValue(it.next()));
            }
        }
        return arrayList.toArray(new Object[0]);
    }

    private static Object toValue(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            return obj;
        }
        List list = (List) obj;
        byte[] bArr = new byte[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            bArr[i2] = (byte) ((Integer) list.get(i2)).intValue();
        }
        return bArr;
    }

    public void bindTo(SQLiteProgram sQLiteProgram) {
        List<Object> list = this.rawArguments;
        if (list != null) {
            int size = list.size();
            int i2 = 0;
            while (i2 < size) {
                Object value = toValue(this.rawArguments.get(i2));
                int i3 = i2 + 1;
                if (value == null) {
                    sQLiteProgram.bindNull(i3);
                } else if (value instanceof byte[]) {
                    sQLiteProgram.bindBlob(i3, (byte[]) value);
                } else if (value instanceof Double) {
                    sQLiteProgram.bindDouble(i3, ((Double) value).doubleValue());
                } else if (value instanceof Integer) {
                    sQLiteProgram.bindLong(i3, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    sQLiteProgram.bindLong(i3, ((Long) value).longValue());
                } else if (value instanceof String) {
                    sQLiteProgram.bindString(i3, (String) value);
                } else {
                    if (!(value instanceof Boolean)) {
                        throw new IllegalArgumentException("Could not bind " + value + " from index " + i2 + ": Supported types are null, byte[], double, long, boolean and String");
                    }
                    sQLiteProgram.bindLong(i3, ((Boolean) value).booleanValue() ? 1L : 0L);
                }
                i2 = i3;
            }
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SqlCommand)) {
            return false;
        }
        SqlCommand sqlCommand = (SqlCommand) obj;
        String str = this.sql;
        if (str != null) {
            if (!str.equals(sqlCommand.sql)) {
                return false;
            }
        } else if (sqlCommand.sql != null) {
            return false;
        }
        if (this.rawArguments.size() != sqlCommand.rawArguments.size()) {
            return false;
        }
        for (int i2 = 0; i2 < this.rawArguments.size(); i2++) {
            if ((this.rawArguments.get(i2) instanceof byte[]) && (sqlCommand.rawArguments.get(i2) instanceof byte[])) {
                if (!Arrays.equals((byte[]) this.rawArguments.get(i2), (byte[]) sqlCommand.rawArguments.get(i2))) {
                    return false;
                }
            } else if (!this.rawArguments.get(i2).equals(sqlCommand.rawArguments.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public List<Object> getRawSqlArguments() {
        return this.rawArguments;
    }

    public String getSql() {
        return this.sql;
    }

    public int hashCode() {
        String str = this.sql;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.sql);
        List<Object> list = this.rawArguments;
        if (list == null || list.isEmpty()) {
            str = "";
        } else {
            str = " " + this.rawArguments;
        }
        sb.append(str);
        return sb.toString();
    }

    public Object[] getSqlArguments() {
        return getSqlArguments(this.rawArguments);
    }
}
