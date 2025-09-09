package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/* loaded from: classes2.dex */
public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        int i3;
        Object obj3;
        boolean z2;
        char c2;
        SerializeWriter serializeWriter = jSONSerializer.out;
        SerializerFeature serializerFeature = SerializerFeature.WriteClassName;
        boolean z3 = serializeWriter.isEnabled(serializerFeature) || SerializerFeature.isEnabled(i2, serializerFeature);
        SerializeWriter serializeWriter2 = jSONSerializer.out;
        Type collectionItemType = z3 ? TypeUtils.getCollectionItemType(type) : null;
        if (obj == null) {
            serializeWriter2.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        List list = (List) obj;
        if (list.size() == 0) {
            serializeWriter2.append((CharSequence) "[]");
            return;
        }
        SerialContext serialContext = jSONSerializer.context;
        jSONSerializer.setContext(serialContext, obj, obj2, 0);
        try {
            char c3 = ',';
            if (serializeWriter2.isEnabled(SerializerFeature.PrettyFormat)) {
                serializeWriter2.append('[');
                jSONSerializer.incrementIndent();
                int i4 = 0;
                for (Object obj4 : list) {
                    if (i4 != 0) {
                        serializeWriter2.append(c3);
                    }
                    jSONSerializer.println();
                    if (obj4 == null) {
                        c2 = c3;
                        jSONSerializer.out.writeNull();
                    } else if (jSONSerializer.containsReference(obj4)) {
                        jSONSerializer.writeReference(obj4);
                        c2 = c3;
                    } else {
                        ObjectSerializer objectWriter = jSONSerializer.getObjectWriter(obj4.getClass());
                        c2 = c3;
                        jSONSerializer.context = new SerialContext(serialContext, obj, obj2, 0, 0);
                        objectWriter.write(jSONSerializer, obj4, Integer.valueOf(i4), collectionItemType, i2);
                    }
                    i4++;
                    c3 = c2;
                }
                jSONSerializer.decrementIdent();
                jSONSerializer.println();
                serializeWriter2.append(']');
                jSONSerializer.context = serialContext;
                return;
            }
            char c4 = ',';
            serializeWriter2.append('[');
            int size = list.size();
            int i5 = 0;
            while (i5 < size) {
                Object obj5 = list.get(i5);
                if (i5 != 0) {
                    serializeWriter2.append(c4);
                }
                if (obj5 == null) {
                    serializeWriter2.append((CharSequence) TmpConstant.GROUP_ROLE_UNKNOWN);
                } else {
                    Class<?> cls = obj5.getClass();
                    if (cls == Integer.class) {
                        serializeWriter2.writeInt(((Integer) obj5).intValue());
                    } else if (cls == Long.class) {
                        long jLongValue = ((Long) obj5).longValue();
                        if (z3) {
                            serializeWriter2.writeLong(jLongValue);
                            serializeWriter2.write(76);
                        } else {
                            serializeWriter2.writeLong(jLongValue);
                        }
                    } else if ((SerializerFeature.DisableCircularReferenceDetect.mask & i2) != 0) {
                        i3 = i5;
                        jSONSerializer.getObjectWriter(obj5.getClass()).write(jSONSerializer, obj5, Integer.valueOf(i5), collectionItemType, i2);
                        z2 = z3;
                        i5 = i3 + 1;
                        z3 = z2;
                        c4 = ',';
                    } else {
                        i3 = i5;
                        if (serializeWriter2.disableCircularReferenceDetect) {
                            obj3 = obj5;
                            z2 = z3;
                        } else {
                            obj3 = obj5;
                            z2 = z3;
                            jSONSerializer.context = new SerialContext(serialContext, obj, obj2, 0, 0);
                        }
                        if (jSONSerializer.containsReference(obj3)) {
                            jSONSerializer.writeReference(obj3);
                        } else {
                            ObjectSerializer objectWriter2 = jSONSerializer.getObjectWriter(obj3.getClass());
                            if ((SerializerFeature.WriteClassName.mask & i2) == 0 || !(objectWriter2 instanceof JavaBeanSerializer)) {
                                objectWriter2.write(jSONSerializer, obj3, Integer.valueOf(i3), collectionItemType, i2);
                            } else {
                                ((JavaBeanSerializer) objectWriter2).writeNoneASM(jSONSerializer, obj3, Integer.valueOf(i3), collectionItemType, i2);
                            }
                        }
                        i5 = i3 + 1;
                        z3 = z2;
                        c4 = ',';
                    }
                }
                i3 = i5;
                z2 = z3;
                i5 = i3 + 1;
                z3 = z2;
                c4 = ',';
            }
            serializeWriter2.append(']');
            jSONSerializer.context = serialContext;
        } catch (Throwable th) {
            jSONSerializer.context = serialContext;
            throw th;
        }
    }
}
