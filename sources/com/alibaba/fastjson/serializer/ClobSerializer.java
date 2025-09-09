package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

/* loaded from: classes2.dex */
public class ClobSerializer implements ObjectSerializer {
    public static final ClobSerializer instance = new ClobSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws SQLException, IOException {
        try {
            if (obj == null) {
                jSONSerializer.writeNull();
                return;
            }
            Reader characterStream = ((Clob) obj).getCharacterStream();
            StringBuilder sb = new StringBuilder();
            try {
                char[] cArr = new char[2048];
                while (true) {
                    int i3 = characterStream.read(cArr, 0, 2048);
                    if (i3 < 0) {
                        String string = sb.toString();
                        characterStream.close();
                        jSONSerializer.write(string);
                        return;
                    }
                    sb.append(cArr, 0, i3);
                }
            } catch (Exception e2) {
                throw new JSONException("read string from reader error", e2);
            }
        } catch (SQLException e3) {
            throw new IOException("write clob error", e3);
        }
    }
}
