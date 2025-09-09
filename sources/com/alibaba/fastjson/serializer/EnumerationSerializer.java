package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class EnumerationSerializer implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        int i3 = 0;
        Type type2 = (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && (type instanceof ParameterizedType)) ? ((ParameterizedType) type).getActualTypeArguments()[0] : null;
        Enumeration enumeration = (Enumeration) obj;
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            serializeWriter.append('[');
            while (enumeration.hasMoreElements()) {
                Object objNextElement = enumeration.nextElement();
                int i4 = i3 + 1;
                if (i3 != 0) {
                    serializeWriter.append(',');
                }
                if (objNextElement == null) {
                    serializeWriter.writeNull();
                } else {
                    jSONSerializer.getObjectWriter(objNextElement.getClass()).write(jSONSerializer, objNextElement, Integer.valueOf(i3), type2, 0);
                }
                i3 = i4;
            }
            serializeWriter.append(']');
            jSONSerializer.context = serialContext;
        } catch (Throwable th) {
            jSONSerializer.context = serialContext;
            throw th;
        }
    }
}
