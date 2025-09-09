package com.alibaba.fastjson.serializer;

import java.util.IdentityHashMap;

/* loaded from: classes2.dex */
public abstract class AfterFilter implements SerializeFilter {
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final Character COMMA = ',';

    final char writeAfter(JSONSerializer jSONSerializer, Object obj, char c2) {
        ThreadLocal<JSONSerializer> threadLocal = serializerLocal;
        JSONSerializer jSONSerializer2 = threadLocal.get();
        threadLocal.set(jSONSerializer);
        ThreadLocal<Character> threadLocal2 = seperatorLocal;
        threadLocal2.set(Character.valueOf(c2));
        writeAfter(obj);
        threadLocal.set(jSONSerializer2);
        return threadLocal2.get().charValue();
    }

    public abstract void writeAfter(Object obj);

    protected final void writeKeyValue(String str, Object obj) {
        IdentityHashMap<Object, SerialContext> identityHashMap;
        JSONSerializer jSONSerializer = serializerLocal.get();
        ThreadLocal<Character> threadLocal = seperatorLocal;
        char cCharValue = threadLocal.get().charValue();
        boolean zContainsReference = jSONSerializer.containsReference(obj);
        jSONSerializer.writeKeyValue(cCharValue, str, obj);
        if (!zContainsReference && (identityHashMap = jSONSerializer.references) != null) {
            identityHashMap.remove(obj);
        }
        if (cCharValue != ',') {
            threadLocal.set(COMMA);
        }
    }
}
