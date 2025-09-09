package com.alibaba.fastjson.serializer;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class ArraySerializer implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> cls, ObjectSerializer objectSerializer) {
        this.componentType = cls;
        this.compObjectSerializer = objectSerializer;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Object[] objArr = (Object[]) obj;
        int length = objArr.length;
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            serializeWriter.append('[');
            for (int i3 = 0; i3 < length; i3++) {
                if (i3 != 0) {
                    serializeWriter.append(',');
                }
                Object obj3 = objArr[i3];
                if (obj3 == null) {
                    if (serializeWriter.isEnabled(SerializerFeature.WriteNullStringAsEmpty) && (obj instanceof String[])) {
                        serializeWriter.writeString("");
                    } else {
                        serializeWriter.append((CharSequence) TmpConstant.GROUP_ROLE_UNKNOWN);
                    }
                } else if (obj3.getClass() == this.componentType) {
                    this.compObjectSerializer.write(jSONSerializer, obj3, Integer.valueOf(i3), null, 0);
                } else {
                    jSONSerializer.getObjectWriter(obj3.getClass()).write(jSONSerializer, obj3, Integer.valueOf(i3), null, 0);
                }
            }
            serializeWriter.append(']');
            jSONSerializer.context = serialContext;
        } catch (Throwable th) {
            jSONSerializer.context = serialContext;
            throw th;
        }
    }
}
