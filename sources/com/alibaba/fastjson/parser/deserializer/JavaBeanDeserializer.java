package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    private final ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls) {
        this(parserConfig, cls, cls);
    }

    private Object createFactoryInstance(ParserConfig parserConfig, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, obj);
    }

    protected static JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, String str) {
        JSONType jSONType = javaBeanInfo.jsonType;
        if (jSONType == null) {
            return null;
        }
        for (Class<?> cls : jSONType.seeAlso()) {
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                JavaBeanInfo javaBeanInfo2 = javaBeanDeserializer.beanInfo;
                if (javaBeanInfo2.typeName.equals(str)) {
                    return javaBeanDeserializer;
                }
                JavaBeanDeserializer seeAlso = getSeeAlso(parserConfig, javaBeanInfo2, str);
                if (seeAlso != null) {
                    return seeAlso;
                }
            }
        }
        return null;
    }

    static boolean isSetFlag(int i2, int[] iArr) {
        int i3;
        if (iArr != null && (i3 = i2 / 32) < iArr.length) {
            return ((1 << (i2 % 32)) & iArr[i3]) != 0;
        }
        return false;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        int i2 = jSONLexerBase.token();
        if (i2 == 8) {
            jSONLexerBase.nextToken(16);
            jSONLexerBase.token();
            return;
        }
        if (i2 != 14) {
            defaultJSONParser.throwException(i2);
        }
        if (jSONLexerBase.getCurrent() == '[') {
            jSONLexerBase.next();
            jSONLexerBase.setToken(14);
        } else {
            jSONLexerBase.nextToken(14);
        }
        if (jSONLexerBase.token() == 15) {
            jSONLexerBase.nextToken();
            return;
        }
        int i3 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, Integer.valueOf(i3)));
            i3++;
            if (jSONLexerBase.token() != 16) {
                break;
            }
            if (jSONLexerBase.getCurrent() == '[') {
                jSONLexerBase.next();
                jSONLexerBase.setToken(14);
            } else {
                jSONLexerBase.nextToken(14);
            }
        }
        int i4 = jSONLexerBase.token();
        if (i4 != 15) {
            defaultJSONParser.throwException(i4);
        }
        if (jSONLexerBase.getCurrent() != ',') {
            jSONLexerBase.nextToken(16);
        } else {
            jSONLexerBase.next();
            jSONLexerBase.setToken(16);
        }
    }

    protected void check(JSONLexer jSONLexer, int i2) {
        if (jSONLexer.token() != i2) {
            throw new JSONException("syntax error");
        }
    }

    public Object createInstance(DefaultJSONParser defaultJSONParser, Type type) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        JavaBeanInfo javaBeanInfo = this.beanInfo;
        Constructor<?> constructor = javaBeanInfo.defaultConstructor;
        Object obj = null;
        if (constructor == null && javaBeanInfo.factoryMethod == null) {
            return null;
        }
        Method method = javaBeanInfo.factoryMethod;
        if (method != null && javaBeanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            if (javaBeanInfo.defaultConstructorParameterSize == 0) {
                objNewInstance = constructor != null ? constructor.newInstance(null) : method.invoke(null, null);
            } else {
                ParseContext context = defaultJSONParser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (!(type instanceof Class)) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                String name = ((Class) type).getName();
                String strSubstring = name.substring(0, name.lastIndexOf(36));
                Object obj2 = context.object;
                String name2 = obj2.getClass().getName();
                if (!name2.equals(strSubstring)) {
                    ParseContext parseContext = context.parent;
                    if (parseContext == null || parseContext.object == null || !("java.util.ArrayList".equals(name2) || "java.util.List".equals(name2) || "java.util.Collection".equals(name2) || "java.util.Map".equals(name2) || "java.util.HashMap".equals(name2))) {
                        obj = obj2;
                    } else if (parseContext.object.getClass().getName().equals(strSubstring)) {
                        obj = parseContext.object;
                    }
                    obj2 = obj;
                }
                if (obj2 == null || ((obj2 instanceof Collection) && ((Collection) obj2).isEmpty())) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                objNewInstance = constructor.newInstance(obj2);
            }
            if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.fields) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(objNewInstance, "");
                        } catch (Exception e2) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e2);
                        }
                    }
                }
            }
            return objNewInstance;
        } catch (JSONException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e4);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        String strScanTypeName = jSONLexer.scanTypeName(defaultJSONParser.symbolTable);
        if (strScanTypeName != null) {
            ObjectDeserializer seeAlso = getSeeAlso(defaultJSONParser.getConfig(), this.beanInfo, strScanTypeName);
            if (seeAlso == null) {
                seeAlso = defaultJSONParser.getConfig().getDeserializer(defaultJSONParser.getConfig().checkAutoType(strScanTypeName, TypeUtils.getClass(type), jSONLexer.getFeatures()));
            }
            if (seeAlso instanceof JavaBeanDeserializer) {
                return (T) ((JavaBeanDeserializer) seeAlso).deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
            }
        }
        T t2 = (T) createInstance(defaultJSONParser, type);
        int length = this.sortedFieldDeserializers.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char c2 = i2 == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i2];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t2, jSONLexer.scanInt(c2));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t2, jSONLexer.scanString(c2));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t2, jSONLexer.scanLong(c2));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                fieldDeserializer.setValue(t2, (current == '\"' || current == 'n') ? jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c2) : (current < '0' || current > '9') ? scanEnum(jSONLexer, c2) : ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c2)));
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t2, jSONLexer.scanBoolean(c2));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t2, Float.valueOf(jSONLexer.scanFloat(c2)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t2, Double.valueOf(jSONLexer.scanDouble(c2)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t2, new Date(jSONLexer.scanLong(c2)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t2, jSONLexer.scanDecimal(c2));
            } else {
                jSONLexer.nextToken(14);
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                fieldDeserializer.setValue(t2, defaultJSONParser.parseObject(fieldInfo.fieldType, fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c2 == ']' ? 15 : 16);
            }
            i2++;
        }
        jSONLexer.nextToken(16);
        return t2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public FieldDeserializer getFieldDeserializer(String str) {
        return getFieldDeserializer(str, null);
    }

    public Type getFieldType(int i2) {
        return this.sortedFieldDeserializers[i2].fieldInfo.fieldType;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map) {
        return parseField(defaultJSONParser, str, obj, type, map, null);
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i2) {
        return parseRest(defaultJSONParser, type, obj, obj2, i2, new int[0]);
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c2) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    public FieldDeserializer smartMatch(String str) {
        return smartMatch(str, null);
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> cls, Type type) {
        this(parserConfig, JavaBeanInfo.build(cls, type, parserConfig.propertyNamingStrategy, parserConfig.fieldBased, parserConfig.compatibleWithJavaBean, parserConfig.isJacksonCompatible()));
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i2) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i2, null);
    }

    public FieldDeserializer getFieldDeserializer(String str, int[] iArr) {
        FieldDeserializer fieldDeserializer;
        if (str == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(str)) != null) {
            return fieldDeserializer;
        }
        int length = this.sortedFieldDeserializers.length - 1;
        int i2 = 0;
        while (i2 <= length) {
            int i3 = (i2 + length) >>> 1;
            int iCompareTo = this.sortedFieldDeserializers[i3].fieldInfo.name.compareTo(str);
            if (iCompareTo < 0) {
                i2 = i3 + 1;
            } else {
                if (iCompareTo <= 0) {
                    if (isSetFlag(i3, iArr)) {
                        return null;
                    }
                    return this.sortedFieldDeserializers[i3];
                }
                length = i3 - 1;
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(str);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x012d  */
    /* JADX WARN: Type inference failed for: r19v13 */
    /* JADX WARN: Type inference failed for: r19v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r19v5 */
    /* JADX WARN: Type inference failed for: r19v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean parseField(com.alibaba.fastjson.parser.DefaultJSONParser r22, java.lang.String r23, java.lang.Object r24, java.lang.reflect.Type r25, java.util.Map<java.lang.String, java.lang.Object> r26, int[] r27) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map, int[]):boolean");
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i2, int[] iArr) {
        return deserialze(defaultJSONParser, type, obj, obj2, i2, iArr);
    }

    protected Enum scanEnum(JSONLexerBase jSONLexerBase, char[] cArr, ObjectDeserializer objectDeserializer) {
        EnumDeserializer enumDeserializer = objectDeserializer instanceof EnumDeserializer ? (EnumDeserializer) objectDeserializer : null;
        if (enumDeserializer == null) {
            jSONLexerBase.matchStat = -1;
            return null;
        }
        long jScanEnumSymbol = jSONLexerBase.scanEnumSymbol(cArr);
        if (jSONLexerBase.matchStat <= 0) {
            return null;
        }
        Enum enumByHashCode = enumDeserializer.getEnumByHashCode(jScanEnumSymbol);
        if (enumByHashCode == null) {
            if (jScanEnumSymbol == TypeUtils.fnv1a_64_magic_hashcode) {
                return null;
            }
            if (jSONLexerBase.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return enumByHashCode;
    }

    public FieldDeserializer smartMatch(String str, int[] iArr) {
        boolean zStartsWith;
        if (str == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(str, iArr);
        if (fieldDeserializer == null) {
            int i2 = 0;
            if (this.smartMatchHashArray == null) {
                long[] jArr = new long[this.sortedFieldDeserializers.length];
                int i3 = 0;
                while (true) {
                    FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                    if (i3 >= fieldDeserializerArr.length) {
                        break;
                    }
                    jArr[i3] = fieldDeserializerArr[i3].fieldInfo.nameHashCode;
                    i3++;
                }
                Arrays.sort(jArr);
                this.smartMatchHashArray = jArr;
            }
            int iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(str));
            if (iBinarySearch < 0) {
                iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(str));
            }
            if (iBinarySearch < 0) {
                zStartsWith = str.startsWith("is");
                if (zStartsWith) {
                    iBinarySearch = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(str.substring(2)));
                }
            } else {
                zStartsWith = false;
            }
            if (iBinarySearch >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] sArr = new short[this.smartMatchHashArray.length];
                    Arrays.fill(sArr, (short) -1);
                    while (true) {
                        FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                        if (i2 >= fieldDeserializerArr2.length) {
                            break;
                        }
                        int iBinarySearch2 = Arrays.binarySearch(this.smartMatchHashArray, fieldDeserializerArr2[i2].fieldInfo.nameHashCode);
                        if (iBinarySearch2 >= 0) {
                            sArr[iBinarySearch2] = (short) i2;
                        }
                        i2++;
                    }
                    this.smartMatchHashArrayMapping = sArr;
                }
                short s2 = this.smartMatchHashArrayMapping[iBinarySearch];
                if (s2 != -1 && !isSetFlag(s2, iArr)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s2];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class<?> cls = fieldInfo.fieldClass;
                if (zStartsWith && cls != Boolean.TYPE && cls != Boolean.class) {
                    return null;
                }
            }
        }
        return fieldDeserializer;
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x01f3, code lost:
    
        if (r7 == (-2)) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0374, code lost:
    
        if (r7 == (-2)) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x039c, code lost:
    
        r5 = r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x0504, code lost:
    
        if (r2 != null) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0506, code lost:
    
        r1 = r6.checkAutoType(r0, r1, r11.getFeatures());
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x050f, code lost:
    
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x0518, code lost:
    
        r2 = r1;
        r1 = r34.getConfig().getDeserializer(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:394:0x051f, code lost:
    
        r2 = (T) r1.deserialze(r34, r2, r36);
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x0525, code lost:
    
        if ((r1 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x0527, code lost:
    
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x0529, code lost:
    
        if (r14 == null) goto L401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x052b, code lost:
    
        r1 = r1.getFieldDeserializer(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x052f, code lost:
    
        if (r1 == null) goto L401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0531, code lost:
    
        r1.setValue((java.lang.Object) r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x0534, code lost:
    
        if (r4 == null) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0536, code lost:
    
        r4.object = r27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x053a, code lost:
    
        r34.setContext(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x053d, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x054e, code lost:
    
        r29 = r5;
        r0 = r17;
        r13 = r30;
        r39 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:606:0x083b, code lost:
    
        if (r6[r3].fieldClass != r13) goto L630;
     */
    /* JADX WARN: Code restructure failed: missing block: B:607:0x083d, code lost:
    
        r3 = r5.kotlinDefaultConstructor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:608:0x083f, code lost:
    
        if (r3 == null) goto L630;
     */
    /* JADX WARN: Code restructure failed: missing block: B:609:0x0841, code lost:
    
        r3 = r3.newInstance(null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:610:0x0846, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:612:0x0848, code lost:
    
        if (r5 >= r1.length) goto L753;
     */
    /* JADX WARN: Code restructure failed: missing block: B:613:0x084a, code lost:
    
        r6 = r1[r5];
     */
    /* JADX WARN: Code restructure failed: missing block: B:614:0x084c, code lost:
    
        if (r6 == null) goto L754;
     */
    /* JADX WARN: Code restructure failed: missing block: B:615:0x084e, code lost:
    
        r7 = r33.beanInfo.fields;
     */
    /* JADX WARN: Code restructure failed: missing block: B:616:0x0852, code lost:
    
        if (r7 == null) goto L755;
     */
    /* JADX WARN: Code restructure failed: missing block: B:618:0x0855, code lost:
    
        if (r5 >= r7.length) goto L756;
     */
    /* JADX WARN: Code restructure failed: missing block: B:619:0x0857, code lost:
    
        r7[r5].set(r3, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:621:0x085d, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x085e, code lost:
    
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:623:0x0861, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:625:0x0863, code lost:
    
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:626:0x0866, code lost:
    
        r1 = (T) r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:627:0x0868, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:628:0x0869, code lost:
    
        r3 = r29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:640:0x08c7, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error, " + r2 + ", " + r33.beanInfo.creatorConstructor.toGenericString(), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:678:0x0968, code lost:
    
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r11.token()));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0599 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:435:0x05e0  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x05e7  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0650 A[Catch: all -> 0x0604, TryCatch #2 {all -> 0x0604, blocks: (B:439:0x05eb, B:444:0x060b, B:469:0x0650, B:471:0x065e, B:447:0x0615, B:449:0x0619, B:451:0x061d, B:453:0x0621, B:455:0x0625, B:457:0x0629, B:460:0x0631, B:462:0x0639, B:464:0x0640, B:466:0x0645, B:467:0x064b, B:477:0x0688), top: B:694:0x05eb }] */
    /* JADX WARN: Removed duplicated region for block: B:470:0x065d  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x0680  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x06df  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x06e2 A[Catch: all -> 0x06c0, TryCatch #0 {all -> 0x06c0, blocks: (B:676:0x0938, B:493:0x06d7, B:496:0x06e2, B:498:0x06e8, B:671:0x0925, B:673:0x092d, B:677:0x094a, B:678:0x0968, B:481:0x06b0, B:483:0x06b6, B:485:0x06bc, B:491:0x06cf, B:679:0x0969, B:680:0x0970), top: B:692:0x0938 }] */
    /* JADX WARN: Removed duplicated region for block: B:632:0x0879 A[Catch: all -> 0x019c, TRY_ENTER, TryCatch #16 {all -> 0x019c, blocks: (B:109:0x017c, B:111:0x018a, B:113:0x0190, B:504:0x06f9, B:632:0x0879, B:633:0x0881, B:635:0x0887, B:637:0x0899, B:652:0x08f8, B:654:0x08fd, B:661:0x090c, B:668:0x091a, B:669:0x0921, B:347:0x0450, B:349:0x0459, B:354:0x0469, B:355:0x0470), top: B:717:0x017c, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:686:0x097c  */
    /* JADX WARN: Removed duplicated region for block: B:736:0x0663 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [com.alibaba.fastjson.parser.ParseContext] */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r2v43, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /* JADX WARN: Type inference failed for: r34v0, types: [com.alibaba.fastjson.parser.DefaultJSONParser] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r34, java.lang.reflect.Type r35, java.lang.Object r36, java.lang.Object r37, int r38, int[] r39) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    public JavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) throws IllegalAccessException, InstantiationException {
        ParserConfig.AutoTypeCheckHandler autoTypeCheckHandlerNewInstance;
        this.clazz = javaBeanInfo.clazz;
        this.beanInfo = javaBeanInfo;
        JSONType jSONType = javaBeanInfo.jsonType;
        HashMap map = null;
        if (jSONType == null || jSONType.autoTypeCheckHandler() == ParserConfig.AutoTypeCheckHandler.class) {
            autoTypeCheckHandlerNewInstance = null;
        } else {
            try {
                autoTypeCheckHandlerNewInstance = javaBeanInfo.jsonType.autoTypeCheckHandler().newInstance();
            } catch (Exception unused) {
            }
        }
        this.autoTypeCheckHandler = autoTypeCheckHandlerNewInstance;
        FieldInfo[] fieldInfoArr = javaBeanInfo.sortedFields;
        this.sortedFieldDeserializers = new FieldDeserializer[fieldInfoArr.length];
        int length = fieldInfoArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            FieldInfo fieldInfo = javaBeanInfo.sortedFields[i2];
            FieldDeserializer fieldDeserializerCreateFieldDeserializer = parserConfig.createFieldDeserializer(parserConfig, javaBeanInfo, fieldInfo);
            this.sortedFieldDeserializers[i2] = fieldDeserializerCreateFieldDeserializer;
            if (length > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializerCreateFieldDeserializer);
            }
            for (String str : fieldInfo.alternateNames) {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(str, fieldDeserializerCreateFieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = map;
        FieldInfo[] fieldInfoArr2 = javaBeanInfo.fields;
        this.fieldDeserializers = new FieldDeserializer[fieldInfoArr2.length];
        int length2 = fieldInfoArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            this.fieldDeserializers[i3] = getFieldDeserializer(javaBeanInfo.fields[i3].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(long j2) {
        int i2 = 0;
        if (this.hashArray == null) {
            long[] jArr = new long[this.sortedFieldDeserializers.length];
            int i3 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i3 >= fieldDeserializerArr.length) {
                    break;
                }
                jArr[i3] = TypeUtils.fnv1a_64(fieldDeserializerArr[i3].fieldInfo.name);
                i3++;
            }
            Arrays.sort(jArr);
            this.hashArray = jArr;
        }
        int iBinarySearch = Arrays.binarySearch(this.hashArray, j2);
        if (iBinarySearch < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] sArr = new short[this.hashArray.length];
            Arrays.fill(sArr, (short) -1);
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr2.length) {
                    break;
                }
                int iBinarySearch2 = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i2].fieldInfo.name));
                if (iBinarySearch2 >= 0) {
                    sArr[iBinarySearch2] = (short) i2;
                }
                i2++;
            }
            this.hashArrayMapping = sArr;
        }
        short s2 = this.hashArrayMapping[iBinarySearch];
        if (s2 != -1) {
            return this.sortedFieldDeserializers[s2];
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object createInstance(java.util.Map<java.lang.String, java.lang.Object> r13, com.alibaba.fastjson.parser.ParserConfig r14) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ArrayIndexOutOfBoundsException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.createInstance(java.util.Map, com.alibaba.fastjson.parser.ParserConfig):java.lang.Object");
    }
}
