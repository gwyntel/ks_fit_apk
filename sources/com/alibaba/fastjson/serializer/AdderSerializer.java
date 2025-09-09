package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class AdderSerializer implements ObjectSerializer {
    public static final AdderSerializer instance = new AdderSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (a.a(obj)) {
            serializeWriter.writeFieldValue('{', "value", b.a(obj).longValue());
            serializeWriter.write(125);
        } else if (d.a(obj)) {
            serializeWriter.writeFieldValue('{', "value", e.a(obj).doubleValue());
            serializeWriter.write(125);
        }
    }
}
