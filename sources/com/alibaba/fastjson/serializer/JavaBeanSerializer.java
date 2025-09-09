package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected final SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(Class<?> cls) {
        this(cls, (Map<String, String>) null);
    }

    static Map<String, String> createAliasMap(String... strArr) {
        HashMap map = new HashMap();
        for (String str : strArr) {
            map.put(str, str);
        }
        return map;
    }

    protected boolean applyLabel(JSONSerializer jSONSerializer, String str) {
        List<LabelFilter> list = jSONSerializer.labelFilters;
        if (list != null) {
            Iterator<LabelFilter> it = list.iterator();
            while (it.hasNext()) {
                if (!it.next().apply(str)) {
                    return false;
                }
            }
        }
        List<LabelFilter> list2 = this.labelFilters;
        if (list2 == null) {
            return true;
        }
        Iterator<LabelFilter> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!it2.next().apply(str)) {
                return false;
            }
        }
        return true;
    }

    protected BeanContext getBeanContext(int i2) {
        return this.sortedGetters[i2].fieldContext;
    }

    public Set<String> getFieldNames(Object obj) throws Exception {
        HashSet hashSet = new HashSet();
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                hashSet.add(fieldSerializer.fieldInfo.name);
            }
        }
        return hashSet;
    }

    public FieldSerializer getFieldSerializer(String str) {
        if (str == null) {
            return null;
        }
        int length = this.sortedGetters.length - 1;
        int i2 = 0;
        while (i2 <= length) {
            int i3 = (i2 + length) >>> 1;
            int iCompareTo = this.sortedGetters[i3].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i2 = i3 + 1;
            } else {
                if (iCompareTo <= 0) {
                    return this.sortedGetters[i3];
                }
                length = i3 - 1;
            }
        }
        return null;
    }

    protected Type getFieldType(int i2) {
        return this.sortedGetters[i2].fieldInfo.fieldType;
    }

    public Object getFieldValue(Object obj, String str) {
        FieldSerializer fieldSerializer = getFieldSerializer(str);
        if (fieldSerializer == null) {
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        } catch (InvocationTargetException e3) {
            throw new JSONException("getFieldValue error." + str, e3);
        }
    }

    public List<Object> getFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            arrayList.add(fieldSerializer.getPropertyValue(obj));
        }
        return arrayList;
    }

    public Map<String, Object> getFieldValuesMap(Object obj) throws Exception {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            boolean zIsEnabled = SerializerFeature.isEnabled(fieldSerializer.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = fieldSerializer.fieldInfo;
            if (!zIsEnabled || fieldInfo == null || !fieldInfo.fieldTransient) {
                if (fieldInfo.unwrapped) {
                    Object json = JSON.toJSON(fieldSerializer.getPropertyValue(obj));
                    if (json instanceof Map) {
                        linkedHashMap.putAll((Map) json);
                    } else {
                        linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                    }
                } else {
                    linkedHashMap.put(fieldInfo.name, fieldSerializer.getPropertyValue(obj));
                }
            }
        }
        return linkedHashMap;
    }

    public JSONType getJSONType() {
        return this.beanInfo.jsonType;
    }

    public List<Object> getObjectFieldValues(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            Class<?> cls = fieldSerializer.fieldInfo.fieldClass;
            if (!cls.isPrimitive() && !cls.getName().startsWith("java.lang.")) {
                arrayList.add(fieldSerializer.getPropertyValue(obj));
            }
        }
        return arrayList;
    }

    public int getSize(Object obj) throws Exception {
        int i2 = 0;
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            if (fieldSerializer.getPropertyValueDirect(obj) != null) {
                i2++;
            }
        }
        return i2;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return isWriteAsArray(jSONSerializer, 0);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2, false);
    }

    protected char writeAfter(JSONSerializer jSONSerializer, Object obj, char c2) {
        List<AfterFilter> list = jSONSerializer.afterFilters;
        if (list != null) {
            Iterator<AfterFilter> it = list.iterator();
            while (it.hasNext()) {
                c2 = it.next().writeAfter(jSONSerializer, obj, c2);
            }
        }
        List<AfterFilter> list2 = this.afterFilters;
        if (list2 != null) {
            Iterator<AfterFilter> it2 = list2.iterator();
            while (it2.hasNext()) {
                c2 = it2.next().writeAfter(jSONSerializer, obj, c2);
            }
        }
        return c2;
    }

    public void writeAsArray(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    protected char writeBefore(JSONSerializer jSONSerializer, Object obj, char c2) {
        List<BeforeFilter> list = jSONSerializer.beforeFilters;
        if (list != null) {
            Iterator<BeforeFilter> it = list.iterator();
            while (it.hasNext()) {
                c2 = it.next().writeBefore(jSONSerializer, obj, c2);
            }
        }
        List<BeforeFilter> list2 = this.beforeFilters;
        if (list2 != null) {
            Iterator<BeforeFilter> it2 = list2.iterator();
            while (it2.hasNext()) {
                c2 = it2.next().writeBefore(jSONSerializer, obj, c2);
            }
        }
        return c2;
    }

    protected void writeClassName(JSONSerializer jSONSerializer, String str, Object obj) {
        if (str == null) {
            str = jSONSerializer.config.typeKey;
        }
        jSONSerializer.out.writeFieldName(str, false);
        String name = this.beanInfo.typeName;
        if (name == null) {
            Class<?> superclass = obj.getClass();
            if (TypeUtils.isProxy(superclass)) {
                superclass = superclass.getSuperclass();
            }
            name = superclass.getName();
        }
        jSONSerializer.write(name);
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2);
    }

    public void writeNoneASM(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws Throwable {
        write(jSONSerializer, obj, obj2, type, i2, false);
    }

    public boolean writeReference(JSONSerializer jSONSerializer, Object obj, int i2) {
        IdentityHashMap<Object, SerialContext> identityHashMap;
        SerialContext serialContext = jSONSerializer.context;
        int i3 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & i3) != 0 || (i2 & i3) != 0 || (identityHashMap = jSONSerializer.references) == null || !identityHashMap.containsKey(obj)) {
            return false;
        }
        jSONSerializer.writeReference(obj);
        return true;
    }

    public JavaBeanSerializer(Class<?> cls, String... strArr) {
        this(cls, createAliasMap(strArr));
    }

    protected boolean isWriteAsArray(JSONSerializer jSONSerializer, int i2) {
        int i3 = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & i3) == 0 && !jSONSerializer.out.beanToArray && (i2 & i3) == 0) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02c9 A[Catch: all -> 0x01c5, Exception -> 0x01cc, TryCatch #20 {Exception -> 0x01cc, all -> 0x01c5, blocks: (B:115:0x01a7, B:118:0x01af, B:120:0x01bb, B:129:0x01d9, B:131:0x01e3, B:134:0x01ed, B:136:0x01f9, B:138:0x01fd, B:141:0x020b, B:143:0x020f, B:144:0x0213, B:146:0x0218, B:148:0x021b, B:150:0x0221, B:152:0x022d, B:154:0x0231, B:157:0x0237, B:160:0x023e, B:162:0x0243, B:165:0x0247, B:167:0x024f, B:169:0x025b, B:171:0x025f, B:174:0x0265, B:176:0x0269, B:177:0x026e, B:179:0x0273, B:181:0x0276, B:182:0x027b, B:184:0x0283, B:186:0x028f, B:188:0x0293, B:191:0x029a, B:193:0x029e, B:194:0x02a3, B:196:0x02a8, B:198:0x02ab, B:200:0x02b2, B:202:0x02b6, B:204:0x02c0, B:208:0x02c9, B:210:0x02cd, B:212:0x02d6, B:214:0x02dd, B:216:0x02e3, B:218:0x02e7, B:221:0x02f2, B:223:0x02f6, B:225:0x02fa, B:228:0x0305, B:230:0x0309, B:232:0x030d, B:235:0x0318, B:237:0x031c, B:239:0x0320, B:242:0x032f, B:244:0x0333, B:246:0x0337, B:249:0x0345, B:251:0x0349, B:253:0x034d, B:256:0x035c, B:258:0x0360, B:260:0x0364, B:264:0x0371, B:266:0x0375, B:268:0x0379, B:271:0x0384, B:273:0x0391, B:277:0x039b), top: B:442:0x01a7 }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02dd A[Catch: all -> 0x01c5, Exception -> 0x01cc, TryCatch #20 {Exception -> 0x01cc, all -> 0x01c5, blocks: (B:115:0x01a7, B:118:0x01af, B:120:0x01bb, B:129:0x01d9, B:131:0x01e3, B:134:0x01ed, B:136:0x01f9, B:138:0x01fd, B:141:0x020b, B:143:0x020f, B:144:0x0213, B:146:0x0218, B:148:0x021b, B:150:0x0221, B:152:0x022d, B:154:0x0231, B:157:0x0237, B:160:0x023e, B:162:0x0243, B:165:0x0247, B:167:0x024f, B:169:0x025b, B:171:0x025f, B:174:0x0265, B:176:0x0269, B:177:0x026e, B:179:0x0273, B:181:0x0276, B:182:0x027b, B:184:0x0283, B:186:0x028f, B:188:0x0293, B:191:0x029a, B:193:0x029e, B:194:0x02a3, B:196:0x02a8, B:198:0x02ab, B:200:0x02b2, B:202:0x02b6, B:204:0x02c0, B:208:0x02c9, B:210:0x02cd, B:212:0x02d6, B:214:0x02dd, B:216:0x02e3, B:218:0x02e7, B:221:0x02f2, B:223:0x02f6, B:225:0x02fa, B:228:0x0305, B:230:0x0309, B:232:0x030d, B:235:0x0318, B:237:0x031c, B:239:0x0320, B:242:0x032f, B:244:0x0333, B:246:0x0337, B:249:0x0345, B:251:0x0349, B:253:0x034d, B:256:0x035c, B:258:0x0360, B:260:0x0364, B:264:0x0371, B:266:0x0375, B:268:0x0379, B:271:0x0384, B:273:0x0391, B:277:0x039b), top: B:442:0x01a7 }] */
    /* JADX WARN: Removed duplicated region for block: B:263:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x049c A[EDGE_INSN: B:357:0x049c->B:340:0x0464 BREAK  A[LOOP:1: B:353:0x0490->B:452:?]] */
    /* JADX WARN: Removed duplicated region for block: B:395:0x053d A[Catch: all -> 0x0550, TRY_ENTER, TryCatch #9 {all -> 0x0550, blocks: (B:392:0x0519, B:395:0x053d, B:405:0x058d, B:407:0x0593, B:408:0x05ab, B:410:0x05af, B:414:0x05b8, B:415:0x05bd, B:399:0x0554, B:401:0x0558, B:403:0x055c, B:404:0x0577), top: B:428:0x0519 }] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0552  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0593 A[Catch: all -> 0x0550, TryCatch #9 {all -> 0x0550, blocks: (B:392:0x0519, B:395:0x053d, B:405:0x058d, B:407:0x0593, B:408:0x05ab, B:410:0x05af, B:414:0x05b8, B:415:0x05bd, B:399:0x0554, B:401:0x0558, B:403:0x055c, B:404:0x0577), top: B:428:0x0519 }] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x05af A[Catch: all -> 0x0550, TryCatch #9 {all -> 0x0550, blocks: (B:392:0x0519, B:395:0x053d, B:405:0x058d, B:407:0x0593, B:408:0x05ab, B:410:0x05af, B:414:0x05b8, B:415:0x05bd, B:399:0x0554, B:401:0x0558, B:403:0x055c, B:404:0x0577), top: B:428:0x0519 }] */
    /* JADX WARN: Removed duplicated region for block: B:412:0x05b5  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void write(com.alibaba.fastjson.serializer.JSONSerializer r32, java.lang.Object r33, java.lang.Object r34, java.lang.reflect.Type r35, int r36, boolean r37) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1473
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int, boolean):void");
    }

    public JavaBeanSerializer(Class<?> cls, Map<String, String> map) {
        this(TypeUtils.buildBeanInfo(cls, map, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo serializeBeanInfo) {
        FieldSerializer[] fieldSerializerArr;
        this.beanInfo = serializeBeanInfo;
        this.sortedGetters = new FieldSerializer[serializeBeanInfo.sortedFields.length];
        int i2 = 0;
        while (true) {
            fieldSerializerArr = this.sortedGetters;
            if (i2 >= fieldSerializerArr.length) {
                break;
            }
            fieldSerializerArr[i2] = new FieldSerializer(serializeBeanInfo.beanType, serializeBeanInfo.sortedFields[i2]);
            i2++;
        }
        FieldInfo[] fieldInfoArr = serializeBeanInfo.fields;
        if (fieldInfoArr == serializeBeanInfo.sortedFields) {
            this.getters = fieldSerializerArr;
        } else {
            this.getters = new FieldSerializer[fieldInfoArr.length];
            int i3 = 0;
            while (true) {
                if (i3 >= this.getters.length) {
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(serializeBeanInfo.fields[i3].name);
                if (fieldSerializer == null) {
                    FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                    System.arraycopy(fieldSerializerArr2, 0, this.getters, 0, fieldSerializerArr2.length);
                    break;
                } else {
                    this.getters[i3] = fieldSerializer;
                    i3++;
                }
            }
        }
        JSONType jSONType = serializeBeanInfo.jsonType;
        if (jSONType != null) {
            for (Class<? extends SerializeFilter> cls : jSONType.serialzeFilters()) {
                try {
                    addFilter(cls.getConstructor(null).newInstance(null));
                } catch (Exception unused) {
                }
            }
        }
    }

    public FieldSerializer getFieldSerializer(long j2) {
        PropertyNamingStrategy[] propertyNamingStrategyArrValues;
        int iBinarySearch;
        if (this.hashArray == null) {
            propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            long[] jArr = new long[this.sortedGetters.length * propertyNamingStrategyArrValues.length];
            int i2 = 0;
            int i3 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr = this.sortedGetters;
                if (i2 >= fieldSerializerArr.length) {
                    break;
                }
                String str = fieldSerializerArr[i2].fieldInfo.name;
                jArr[i3] = TypeUtils.fnv1a_64(str);
                i3++;
                for (PropertyNamingStrategy propertyNamingStrategy : propertyNamingStrategyArrValues) {
                    String strTranslate = propertyNamingStrategy.translate(str);
                    if (!str.equals(strTranslate)) {
                        jArr[i3] = TypeUtils.fnv1a_64(strTranslate);
                        i3++;
                    }
                }
                i2++;
            }
            Arrays.sort(jArr, 0, i3);
            this.hashArray = new long[i3];
            System.arraycopy(jArr, 0, this.hashArray, 0, i3);
        } else {
            propertyNamingStrategyArrValues = null;
        }
        int iBinarySearch2 = Arrays.binarySearch(this.hashArray, j2);
        if (iBinarySearch2 < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (propertyNamingStrategyArrValues == null) {
                propertyNamingStrategyArrValues = PropertyNamingStrategy.values();
            }
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            int i4 = 0;
            while (true) {
                FieldSerializer[] fieldSerializerArr2 = this.sortedGetters;
                if (i4 >= fieldSerializerArr2.length) {
                    break;
                }
                String str2 = fieldSerializerArr2[i4].fieldInfo.name;
                int iBinarySearch3 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(str2));
                if (iBinarySearch3 >= 0) {
                    sArr[iBinarySearch3] = (short) i4;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : propertyNamingStrategyArrValues) {
                    String strTranslate2 = propertyNamingStrategy2.translate(str2);
                    if (!str2.equals(strTranslate2) && (iBinarySearch = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(strTranslate2))) >= 0) {
                        sArr[iBinarySearch] = (short) i4;
                    }
                }
                i4++;
            }
            this.hashArrayMapping = sArr;
        }
        short s2 = this.hashArrayMapping[iBinarySearch2];
        if (s2 != -1) {
            return this.sortedGetters[s2];
        }
        return null;
    }

    public Object getFieldValue(Object obj, String str, long j2, boolean z2) {
        FieldSerializer fieldSerializer = getFieldSerializer(j2);
        if (fieldSerializer == null) {
            if (!z2) {
                return null;
            }
            throw new JSONException("field not found. " + str);
        }
        try {
            return fieldSerializer.getPropertyValue(obj);
        } catch (IllegalAccessException e2) {
            throw new JSONException("getFieldValue error." + str, e2);
        } catch (InvocationTargetException e3) {
            throw new JSONException("getFieldValue error." + str, e3);
        }
    }
}
