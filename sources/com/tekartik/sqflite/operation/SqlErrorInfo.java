package com.tekartik.sqflite.operation;

import com.tekartik.sqflite.Constant;
import com.tekartik.sqflite.SqlCommand;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class SqlErrorInfo {
    public static Map<String, Object> getMap(Operation operation) {
        SqlCommand sqlCommand = operation.getSqlCommand();
        if (sqlCommand == null) {
            return null;
        }
        HashMap map = new HashMap();
        map.put(Constant.PARAM_SQL, sqlCommand.getSql());
        map.put(Constant.PARAM_SQL_ARGUMENTS, sqlCommand.getRawSqlArguments());
        return map;
    }
}
