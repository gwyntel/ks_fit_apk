package com.alibaba.fastjson.parser;

import anetwork.channel.util.RequestConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumCreatorDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONPDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessableDeserializer;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.AwtCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.ByteBufferCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharArrayCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.GuavaCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.JodaCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.support.moneta.MonetaCodec;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.Function;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.ModuleUtil;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;

/* loaded from: classes2.dex */
public class ParserConfig {
    public static final String AUTOTYPE_ACCEPT = "fastjson.parser.autoTypeAccept";
    private static final String[] AUTO_TYPE_ACCEPT_LIST;
    private static final long[] INTERNAL_WHITELIST_HASHCODES;
    private static boolean awtError;
    public static ParserConfig global;
    private static boolean guavaError;
    private static Function<Class<?>, Boolean> isPrimitiveFuncation;
    private static boolean jdk8Error;
    private static boolean jodaError;
    private long[] acceptHashCodes;
    private boolean asmEnable;
    protected ASMDeserializerFactory asmFactory;
    private volatile List<AutoTypeCheckHandler> autoTypeCheckHandlers;
    private boolean autoTypeSupport;
    public boolean compatibleWithJavaBean;
    protected ClassLoader defaultClassLoader;
    private long[] denyHashCodes;
    private final IdentityHashMap<Type, ObjectDeserializer> deserializers;
    public final boolean fieldBased;
    private final Callable<Void> initDeserializersWithJavaSql;
    private long[] internalDenyHashCodes;
    private boolean jacksonCompatible;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectDeserializer>> mixInDeserializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private boolean safeMode;
    public final SymbolTable symbolTable;
    private final ConcurrentMap<String, Class<?>> typeMapping;
    public static final String DENY_PROPERTY_INTERNAL = "fastjson.parser.deny.internal";
    public static final String[] DENYS_INTERNAL = splitItemsFormProperty(IOUtils.getStringProperty(DENY_PROPERTY_INTERNAL));
    public static final String DENY_PROPERTY = "fastjson.parser.deny";
    public static final String[] DENYS = splitItemsFormProperty(IOUtils.getStringProperty(DENY_PROPERTY));
    public static final String AUTOTYPE_SUPPORT_PROPERTY = "fastjson.parser.autoTypeSupport";
    public static final boolean AUTO_SUPPORT = "true".equals(IOUtils.getStringProperty(AUTOTYPE_SUPPORT_PROPERTY));
    public static final String SAFE_MODE_PROPERTY = "fastjson.parser.safeMode";
    public static final boolean SAFE_MODE = "true".equals(IOUtils.getStringProperty(SAFE_MODE_PROPERTY));

    public interface AutoTypeCheckHandler {
        Class<?> handler(String str, Class<?> cls, int i2);
    }

    static {
        String[] strArrSplitItemsFormProperty = splitItemsFormProperty(IOUtils.getStringProperty(AUTOTYPE_ACCEPT));
        if (strArrSplitItemsFormProperty == null) {
            strArrSplitItemsFormProperty = new String[0];
        }
        AUTO_TYPE_ACCEPT_LIST = strArrSplitItemsFormProperty;
        INTERNAL_WHITELIST_HASHCODES = new long[]{-9013707057526259810L, -8773806119481270567L, -8421588593326113468L, -8070393259084821111L, -7858127399773263546L, -7043543676283957292L, -6976602508726000783L, -6293031534589903644L, -6081111809668363619L, -5779433778261875721L, -5399450433995651784L, -4540135604787511831L, -4207865850564917696L, -3950343444501679205L, -3714900953609113456L, -3393714734093696063L, -3378497329992063044L, -2631228350337215662L, -2551988546877734201L, -2473987886800209058L, -2265617974881722705L, -1759511109484434299L, -1477946458560579955L, -816725787720647462L, -520183782617964618L, 59775428743665658L, 484499585846206473L, 532945107123976213L, 711449177569584898L, 829148494126372070L, 956883420092542580L, 1233162291719202522L, 1696465274354442213L, 1863557081881630420L, 2238472697200138595L, 2380202963256720577L, 2643099543618286743L, 2793877891138577121L, 3804572268889088203L, 4567982875926242015L, 4784070066737926537L, 4960004821520561233L, 5348524593377618456L, 5454920836284873808L, 5695987590363189151L, 6073645722991901167L, 6114875255374330593L, 6137737446243999215L, 6160752908990493848L, 6939315124833099497L, 7048426940343117278L, 7267793227937552092L, 8331868837379820532L, 8357451534615459155L, 8890227807433646566L, 9166532985682478006L, 9215131087512669423L};
        global = new ParserConfig();
        awtError = false;
        jdk8Error = false;
        jodaError = false;
        guavaError = false;
        isPrimitiveFuncation = new Function<Class<?>, Boolean>() { // from class: com.alibaba.fastjson.parser.ParserConfig.2
            @Override // com.alibaba.fastjson.util.Function
            public Boolean apply(Class<?> cls) {
                return Boolean.valueOf(cls == Date.class || cls == Time.class || cls == Timestamp.class);
            }
        };
    }

    public ParserConfig() {
        this(false);
    }

    private void addItemsToAccept(String[] strArr) {
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            addAccept(str);
        }
    }

    private void addItemsToDeny(String[] strArr) {
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            addDeny(str);
        }
    }

    private void addItemsToDeny0(String[] strArr) {
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            addDenyInternal(str);
        }
    }

    private static Method getEnumCreator(Class cls, Class cls2) throws SecurityException {
        for (Method method : cls.getMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == cls2 && method.getParameterTypes().length == 1 && ((JSONCreator) method.getAnnotation(JSONCreator.class)) != null) {
                return method;
            }
        }
        return null;
    }

    public static Field getFieldFromCache(String str, Map<String, Field> map) {
        Field field = map.get(str);
        if (field == null) {
            field = map.get(OpenAccountUIConstants.UNDER_LINE + str);
        }
        if (field == null) {
            field = map.get("m_" + str);
        }
        if (field != null) {
            return field;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt >= 'a' && cCharAt <= 'z') {
            char[] charArray = str.toCharArray();
            charArray[0] = (char) (charArray[0] - ' ');
            field = map.get(new String(charArray));
        }
        if (str.length() <= 2) {
            return field;
        }
        char cCharAt2 = str.charAt(1);
        if (cCharAt < 'a' || cCharAt > 'z' || cCharAt2 < 'A' || cCharAt2 > 'Z') {
            return field;
        }
        for (Map.Entry<String, Field> entry : map.entrySet()) {
            if (str.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return field;
    }

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    private void initDeserializers() {
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap = this.deserializers;
        MiscCodec miscCodec = MiscCodec.instance;
        identityHashMap.put(SimpleDateFormat.class, miscCodec);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap2 = this.deserializers;
        CalendarCodec calendarCodec = CalendarCodec.instance;
        identityHashMap2.put(Calendar.class, calendarCodec);
        this.deserializers.put(XMLGregorianCalendar.class, calendarCodec);
        this.deserializers.put(JSONObject.class, MapDeserializer.instance);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap3 = this.deserializers;
        CollectionCodec collectionCodec = CollectionCodec.instance;
        identityHashMap3.put(JSONArray.class, collectionCodec);
        this.deserializers.put(Map.class, MapDeserializer.instance);
        this.deserializers.put(HashMap.class, MapDeserializer.instance);
        this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
        this.deserializers.put(TreeMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
        this.deserializers.put(Collection.class, collectionCodec);
        this.deserializers.put(List.class, collectionCodec);
        this.deserializers.put(ArrayList.class, collectionCodec);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap4 = this.deserializers;
        JavaObjectDeserializer javaObjectDeserializer = JavaObjectDeserializer.instance;
        identityHashMap4.put(Object.class, javaObjectDeserializer);
        this.deserializers.put(String.class, StringCodec.instance);
        this.deserializers.put(StringBuffer.class, StringCodec.instance);
        this.deserializers.put(StringBuilder.class, StringCodec.instance);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap5 = this.deserializers;
        Class cls = Character.TYPE;
        CharacterCodec characterCodec = CharacterCodec.instance;
        identityHashMap5.put(cls, characterCodec);
        this.deserializers.put(Character.class, characterCodec);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap6 = this.deserializers;
        Class cls2 = Byte.TYPE;
        NumberDeserializer numberDeserializer = NumberDeserializer.instance;
        identityHashMap6.put(cls2, numberDeserializer);
        this.deserializers.put(Byte.class, numberDeserializer);
        this.deserializers.put(Short.TYPE, numberDeserializer);
        this.deserializers.put(Short.class, numberDeserializer);
        this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
        this.deserializers.put(Integer.class, IntegerCodec.instance);
        this.deserializers.put(Long.TYPE, LongCodec.instance);
        this.deserializers.put(Long.class, LongCodec.instance);
        this.deserializers.put(BigInteger.class, BigIntegerCodec.instance);
        this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.deserializers.put(Float.TYPE, FloatCodec.instance);
        this.deserializers.put(Float.class, FloatCodec.instance);
        this.deserializers.put(Double.TYPE, numberDeserializer);
        this.deserializers.put(Double.class, numberDeserializer);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap7 = this.deserializers;
        Class cls3 = Boolean.TYPE;
        BooleanCodec booleanCodec = BooleanCodec.instance;
        identityHashMap7.put(cls3, booleanCodec);
        this.deserializers.put(Boolean.class, booleanCodec);
        this.deserializers.put(Class.class, miscCodec);
        this.deserializers.put(char[].class, new CharArrayCodec());
        this.deserializers.put(AtomicBoolean.class, booleanCodec);
        this.deserializers.put(AtomicInteger.class, IntegerCodec.instance);
        this.deserializers.put(AtomicLong.class, LongCodec.instance);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap8 = this.deserializers;
        ReferenceCodec referenceCodec = ReferenceCodec.instance;
        identityHashMap8.put(AtomicReference.class, referenceCodec);
        this.deserializers.put(WeakReference.class, referenceCodec);
        this.deserializers.put(SoftReference.class, referenceCodec);
        this.deserializers.put(UUID.class, miscCodec);
        this.deserializers.put(TimeZone.class, miscCodec);
        this.deserializers.put(Locale.class, miscCodec);
        this.deserializers.put(Currency.class, miscCodec);
        this.deserializers.put(Inet4Address.class, miscCodec);
        this.deserializers.put(Inet6Address.class, miscCodec);
        this.deserializers.put(InetSocketAddress.class, miscCodec);
        this.deserializers.put(File.class, miscCodec);
        this.deserializers.put(URI.class, miscCodec);
        this.deserializers.put(URL.class, miscCodec);
        this.deserializers.put(Pattern.class, miscCodec);
        this.deserializers.put(Charset.class, miscCodec);
        this.deserializers.put(JSONPath.class, miscCodec);
        this.deserializers.put(Number.class, numberDeserializer);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap9 = this.deserializers;
        AtomicCodec atomicCodec = AtomicCodec.instance;
        identityHashMap9.put(AtomicIntegerArray.class, atomicCodec);
        this.deserializers.put(AtomicLongArray.class, atomicCodec);
        this.deserializers.put(StackTraceElement.class, StackTraceElementDeserializer.instance);
        this.deserializers.put(Serializable.class, javaObjectDeserializer);
        this.deserializers.put(Cloneable.class, javaObjectDeserializer);
        this.deserializers.put(Comparable.class, javaObjectDeserializer);
        this.deserializers.put(Closeable.class, javaObjectDeserializer);
        this.deserializers.put(JSONPObject.class, new JSONPDeserializer());
        ModuleUtil.callWhenHasJavaSql(this.initDeserializersWithJavaSql);
    }

    public static boolean isPrimitive2(Class<?> cls) {
        boolean z2 = cls.isPrimitive() || cls == Boolean.class || cls == Character.class || cls == Byte.class || cls == Short.class || cls == Integer.class || cls == Long.class || cls == Float.class || cls == Double.class || cls == BigInteger.class || cls == BigDecimal.class || cls == String.class || cls == java.util.Date.class || cls.isEnum();
        Boolean boolValueOf = Boolean.valueOf(z2);
        if (!z2) {
            boolValueOf = (Boolean) ModuleUtil.callWhenHasJavaSql(isPrimitiveFuncation, cls);
        }
        if (boolValueOf != null) {
            return boolValueOf.booleanValue();
        }
        return false;
    }

    public static void parserAllFieldToCache(Class<?> cls, Map<String, Field> map) {
        for (Field field : cls.getDeclaredFields()) {
            String name = field.getName();
            if (!map.containsKey(name)) {
                map.put(name, field);
            }
        }
        if (cls.getSuperclass() == null || cls.getSuperclass() == Object.class) {
            return;
        }
        parserAllFieldToCache(cls.getSuperclass(), map);
    }

    private static String[] splitItemsFormProperty(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        return str.split(",");
    }

    public void addAccept(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        long jFnv1a_64 = TypeUtils.fnv1a_64(str);
        if (Arrays.binarySearch(this.acceptHashCodes, jFnv1a_64) >= 0) {
            return;
        }
        long[] jArr = this.acceptHashCodes;
        int length = jArr.length;
        long[] jArr2 = new long[length + 1];
        jArr2[length] = jFnv1a_64;
        System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
        Arrays.sort(jArr2);
        this.acceptHashCodes = jArr2;
    }

    public void addAutoTypeCheckHandler(AutoTypeCheckHandler autoTypeCheckHandler) {
        List copyOnWriteArrayList = this.autoTypeCheckHandlers;
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList();
            this.autoTypeCheckHandlers = copyOnWriteArrayList;
        }
        copyOnWriteArrayList.add(autoTypeCheckHandler);
    }

    public void addDeny(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        long jFnv1a_64 = TypeUtils.fnv1a_64(str);
        if (Arrays.binarySearch(this.denyHashCodes, jFnv1a_64) >= 0) {
            return;
        }
        long[] jArr = this.denyHashCodes;
        int length = jArr.length;
        long[] jArr2 = new long[length + 1];
        jArr2[length] = jFnv1a_64;
        System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
        Arrays.sort(jArr2);
        this.denyHashCodes = jArr2;
    }

    public void addDenyInternal(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        long jFnv1a_64 = TypeUtils.fnv1a_64(str);
        long[] jArr = this.internalDenyHashCodes;
        if (jArr == null) {
            this.internalDenyHashCodes = new long[]{jFnv1a_64};
            return;
        }
        if (Arrays.binarySearch(jArr, jFnv1a_64) >= 0) {
            return;
        }
        long[] jArr2 = this.internalDenyHashCodes;
        int length = jArr2.length;
        long[] jArr3 = new long[1 + length];
        jArr3[length] = jFnv1a_64;
        System.arraycopy(jArr2, 0, jArr3, 0, jArr2.length);
        Arrays.sort(jArr3);
        this.internalDenyHashCodes = jArr3;
    }

    public Class<?> checkAutoType(Class cls) {
        return get(cls) != null ? cls : checkAutoType(cls.getName(), null, JSON.DEFAULT_PARSER_FEATURE);
    }

    public void clearDeserializers() {
        this.deserializers.clear();
        initDeserializers();
    }

    public void configFromPropety(Properties properties) {
        addItemsToDeny(splitItemsFormProperty(properties.getProperty(DENY_PROPERTY)));
        addItemsToAccept(splitItemsFormProperty(properties.getProperty(AUTOTYPE_ACCEPT)));
        String property = properties.getProperty(AUTOTYPE_SUPPORT_PROPERTY);
        if ("true".equals(property)) {
            this.autoTypeSupport = true;
        } else if (RequestConstant.FALSE.equals(property)) {
            this.autoTypeSupport = false;
        }
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, FieldInfo fieldInfo) {
        Class<?> clsDeserializeUsing;
        Class<?> cls = javaBeanInfo.clazz;
        Class<?> cls2 = fieldInfo.fieldClass;
        JSONField annotation = fieldInfo.getAnnotation();
        Class<?> cls3 = null;
        if (annotation != null && (clsDeserializeUsing = annotation.deserializeUsing()) != Void.class) {
            cls3 = clsDeserializeUsing;
        }
        return (cls3 == null && (cls2 == List.class || cls2 == ArrayList.class)) ? new ArrayListTypeFieldDeserializer(parserConfig, cls, fieldInfo) : new DefaultFieldDeserializer(parserConfig, cls, fieldInfo);
    }

    public ObjectDeserializer createJavaBeanDeserializer(Class<?> cls, Type type) {
        JSONField annotation;
        Method method;
        ASMDeserializerFactory aSMDeserializerFactory;
        boolean zCheckName = this.asmEnable & (!this.fieldBased);
        if (zCheckName) {
            JSONType jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
            if (jSONType != null) {
                Class<?> clsDeserializer = jSONType.deserializer();
                if (clsDeserializer != Void.class) {
                    try {
                        Object objNewInstance = clsDeserializer.newInstance();
                        if (objNewInstance instanceof ObjectDeserializer) {
                            return (ObjectDeserializer) objNewInstance;
                        }
                    } catch (Throwable unused) {
                    }
                }
                zCheckName = jSONType.asm() && jSONType.parseFeatures().length == 0;
            }
            if (zCheckName) {
                Class<?> builderClass = JavaBeanInfo.getBuilderClass(cls, jSONType);
                if (builderClass == null) {
                    builderClass = cls;
                }
                while (true) {
                    if (!Modifier.isPublic(builderClass.getModifiers())) {
                        zCheckName = false;
                        break;
                    }
                    builderClass = builderClass.getSuperclass();
                    if (builderClass == Object.class || builderClass == null) {
                        break;
                    }
                }
            }
        }
        if (cls.getTypeParameters().length != 0) {
            zCheckName = false;
        }
        if (zCheckName && (aSMDeserializerFactory = this.asmFactory) != null && aSMDeserializerFactory.classLoader.isExternalClass(cls)) {
            zCheckName = false;
        }
        if (zCheckName) {
            zCheckName = ASMUtils.checkName(cls.getSimpleName());
        }
        if (zCheckName) {
            if (cls.isInterface()) {
                zCheckName = false;
            }
            JavaBeanInfo javaBeanInfoBuild = JavaBeanInfo.build(cls, type, this.propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, this.jacksonCompatible);
            if (zCheckName && javaBeanInfoBuild.fields.length > 200) {
                zCheckName = false;
            }
            Constructor<?> constructor = javaBeanInfoBuild.defaultConstructor;
            if (zCheckName && constructor == null && !cls.isInterface()) {
                zCheckName = false;
            }
            for (FieldInfo fieldInfo : javaBeanInfoBuild.fields) {
                if (!fieldInfo.getOnly) {
                    Class<?> cls2 = fieldInfo.fieldClass;
                    if (Modifier.isPublic(cls2.getModifiers()) && ((!cls2.isMemberClass() || Modifier.isStatic(cls2.getModifiers())) && ((fieldInfo.getMember() == null || ASMUtils.checkName(fieldInfo.getMember().getName())) && (((annotation = fieldInfo.getAnnotation()) == null || (ASMUtils.checkName(annotation.name()) && annotation.format().length() == 0 && annotation.deserializeUsing() == Void.class && annotation.parseFeatures().length == 0 && !annotation.unwrapped())) && (((method = fieldInfo.method) == null || method.getParameterTypes().length <= 1) && (!cls2.isEnum() || (getDeserializer(cls2) instanceof EnumDeserializer))))))) {
                    }
                }
                zCheckName = false;
                break;
            }
        }
        if (zCheckName && cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())) {
            zCheckName = false;
        }
        if (!((zCheckName && TypeUtils.isXmlField(cls)) ? false : zCheckName)) {
            return new JavaBeanDeserializer(this, cls, type);
        }
        JavaBeanInfo javaBeanInfoBuild2 = JavaBeanInfo.build(cls, type, this.propertyNamingStrategy);
        try {
            return this.asmFactory.createJavaBeanDeserializer(this, javaBeanInfoBuild2);
        } catch (JSONException unused2) {
            return new JavaBeanDeserializer(this, javaBeanInfoBuild2);
        } catch (NoSuchMethodException unused3) {
            return new JavaBeanDeserializer(this, cls, type);
        } catch (Exception e2) {
            throw new JSONException("create asm deserializer error, " + cls.getName(), e2);
        }
    }

    public ObjectDeserializer get(Type type) {
        Type mixInAnnotations = JSON.getMixInAnnotations(type);
        if (mixInAnnotations == null) {
            return this.deserializers.get(type);
        }
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap = this.mixInDeserializers.get(type);
        if (identityHashMap == null) {
            return null;
        }
        return identityHashMap.get(mixInAnnotations);
    }

    public ClassLoader getDefaultClassLoader() {
        return this.defaultClassLoader;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDerializers() {
        return this.deserializers;
    }

    public ObjectDeserializer getDeserializer(Type type) {
        ObjectDeserializer objectDeserializer = get(type);
        if (objectDeserializer != null) {
            return objectDeserializer;
        }
        if (type instanceof Class) {
            return getDeserializer((Class) type, type);
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            return rawType instanceof Class ? getDeserializer((Class) rawType, type) : getDeserializer(rawType);
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getDeserializer(upperBounds[0]);
            }
        }
        return JavaObjectDeserializer.instance;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDeserializers() {
        return this.deserializers;
    }

    protected ObjectDeserializer getEnumDeserializer(Class<?> cls) {
        return new EnumDeserializer(cls);
    }

    public void initJavaBeanDeserializers(Class<?>... clsArr) {
        if (clsArr == null) {
            return;
        }
        for (Class<?> cls : clsArr) {
            if (cls != null) {
                putDeserializer(cls, createJavaBeanDeserializer(cls, cls));
            }
        }
    }

    public boolean isAsmEnable() {
        return this.asmEnable;
    }

    public boolean isAutoTypeSupport() {
        return this.autoTypeSupport;
    }

    public boolean isJacksonCompatible() {
        return this.jacksonCompatible;
    }

    public boolean isPrimitive(Class<?> cls) {
        return isPrimitive2(cls);
    }

    public boolean isSafeMode() {
        return this.safeMode;
    }

    public void putDeserializer(Type type, ObjectDeserializer objectDeserializer) {
        Type mixInAnnotations = JSON.getMixInAnnotations(type);
        if (mixInAnnotations == null) {
            this.deserializers.put(type, objectDeserializer);
            return;
        }
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap = this.mixInDeserializers.get(type);
        if (identityHashMap == null) {
            identityHashMap = new IdentityHashMap<>(4);
            this.mixInDeserializers.put(type, identityHashMap);
        }
        identityHashMap.put(mixInAnnotations, objectDeserializer);
    }

    public void register(String str, Class cls) {
        this.typeMapping.putIfAbsent(str, cls);
    }

    public void setAsmEnable(boolean z2) {
        this.asmEnable = z2;
    }

    public void setAutoTypeSupport(boolean z2) {
        this.autoTypeSupport = z2;
    }

    public void setDefaultClassLoader(ClassLoader classLoader) {
        this.defaultClassLoader = classLoader;
    }

    public void setJacksonCompatible(boolean z2) {
        this.jacksonCompatible = z2;
    }

    public void setSafeMode(boolean z2) {
        this.safeMode = z2;
    }

    public ParserConfig(boolean z2) {
        this(null, null, z2);
    }

    public void register(Module module) {
        this.modules.add(module);
    }

    public ParserConfig(ClassLoader classLoader) {
        this(null, classLoader, false);
    }

    public Class<?> checkAutoType(String str, Class<?> cls) {
        return checkAutoType(str, cls, JSON.DEFAULT_PARSER_FEATURE);
    }

    public ParserConfig(ASMDeserializerFactory aSMDeserializerFactory) {
        this(aSMDeserializerFactory, null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:161:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Class<?> checkAutoType(java.lang.String r25, java.lang.Class<?> r26, int r27) {
        /*
            Method dump skipped, instructions count: 1027
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.ParserConfig.checkAutoType(java.lang.String, java.lang.Class, int):java.lang.Class");
    }

    private ParserConfig(ASMDeserializerFactory aSMDeserializerFactory, ClassLoader classLoader, boolean z2) {
        this.deserializers = new IdentityHashMap<>();
        this.mixInDeserializers = new IdentityHashMap<>(16);
        this.typeMapping = new ConcurrentHashMap(16, 0.75f, 1);
        this.asmEnable = !ASMUtils.IS_ANDROID;
        this.symbolTable = new SymbolTable(4096);
        this.autoTypeSupport = AUTO_SUPPORT;
        this.jacksonCompatible = false;
        this.compatibleWithJavaBean = TypeUtils.compatibleWithJavaBean;
        this.modules = new ArrayList();
        this.safeMode = SAFE_MODE;
        this.denyHashCodes = new long[]{-9164606388214699518L, -8720046426850100497L, -8649961213709896794L, -8165637398350707645L, -8109300701639721088L, -7966123100503199569L, -7921218830998286408L, -7775351613326101303L, -7768608037458185275L, -7766605818834748097L, -6835437086156813536L, -6316154655839304624L, -6179589609550493385L, -6149130139291498841L, -6149093380703242441L, -6025144546313590215L, -5939269048541779808L, -5885964883385605994L, -5767141746063564198L, -5764804792063216819L, -5472097725414717105L, -5194641081268104286L, -5076846148177416215L, -4837536971810737970L, -4836620931940850535L, -4703320437989596122L, -4608341446948126581L, -4537258998789938600L, -4438775680185074100L, -4314457471973557243L, -4150995715611818742L, -4082057040235125754L, -3975378478825053783L, -3967588558552655563L, -3935185854875733362L, -3319207949486691020L, -3077205613010077203L, -3053747177772160511L, -2995060141064716555L, -2825378362173150292L, -2533039401923731906L, -2439930098895578154L, -2378990704010641148L, -2364987994247679115L, -2262244760619952081L, -2192804397019347313L, -2095516571388852610L, -1872417015366588117L, -1650485814983027158L, -1589194880214235129L, -965955008570215305L, -905177026366752536L, -831789045734283466L, -731978084025273882L, -666475508176557463L, -582813228520337988L, -254670111376247151L, -219577392946377768L, -190281065685395680L, -26639035867733124L, -9822483067882491L, 4750336058574309L, 33238344207745342L, 156405680656087946L, 218512992947536312L, 313864100207897507L, 386461436234701831L, 744602970950881621L, 823641066473609950L, 1073634739308289776L, 1153291637701043748L, 1203232727967308606L, 1214780596910349029L, 1459860845934817624L, 1502845958873959152L, 1534439610567445754L, 1698504441317515818L, 1818089308493370394L, 2078113382421334967L, 2164696723069287854L, 2622551729063269307L, 2653453629929770569L, 2660670623866180977L, 2731823439467737506L, 2836431254737891113L, 2930861374593775110L, 3085473968517218653L, 3089451460101527857L, 3114862868117605599L, 3129395579983849527L, 3256258368248066264L, 3452379460455804429L, 3547627781654598988L, 3637939656440441093L, 3688179072722109200L, 3718352661124136681L, 3730752432285826863L, 3794316665763266033L, 4000049462512838776L, 4046190361520671643L, 4147696707147271408L, 4193204392725694463L, 4241163808635564644L, 4254584350247334433L, 4814658433570175913L, 4841947709850912914L, 4904007817188630457L, 5100336081510080343L, 5274044858141538265L, 5347909877633654828L, 5450448828334921485L, 5474268165959054640L, 5545425291794704408L, 5596129856135573697L, 5688200883751798389L, 5751393439502795295L, 5944107969236155580L, 6007332606592876737L, 6280357960959217660L, 6456855723474196908L, 6511035576063254270L, 6534946468240507089L, 6584624952928234050L, 6734240326434096246L, 6742705432718011780L, 6800727078373023163L, 6854854816081053523L, 7045245923763966215L, 7123326897294507060L, 7179336928365889465L, 7240293012336844478L, 7347653049056829645L, 7375862386996623731L, 7442624256860549330L, 7617522210483516279L, 7658177784286215602L, 8055461369741094911L, 8064026652676081192L, 8389032537095247355L, 8409640769019589119L, 8488266005336625107L, 8537233257283452655L, 8838294710098435315L, 9140390920032557669L, 9140416208800006522L};
        long[] jArr = new long[AUTO_TYPE_ACCEPT_LIST.length];
        int i2 = 0;
        while (true) {
            String[] strArr = AUTO_TYPE_ACCEPT_LIST;
            if (i2 >= strArr.length) {
                break;
            }
            jArr[i2] = TypeUtils.fnv1a_64(strArr[i2]);
            i2++;
        }
        Arrays.sort(jArr);
        this.acceptHashCodes = jArr;
        this.initDeserializersWithJavaSql = new Callable<Void>() { // from class: com.alibaba.fastjson.parser.ParserConfig.1
            @Override // java.util.concurrent.Callable
            public Void call() {
                ParserConfig.this.deserializers.put(Timestamp.class, SqlDateDeserializer.instance_timestamp);
                ParserConfig.this.deserializers.put(Date.class, SqlDateDeserializer.instance);
                ParserConfig.this.deserializers.put(Time.class, TimeDeserializer.instance);
                ParserConfig.this.deserializers.put(java.util.Date.class, DateCodec.instance);
                return null;
            }
        };
        this.fieldBased = z2;
        if (aSMDeserializerFactory == null && !ASMUtils.IS_ANDROID) {
            try {
                if (classLoader == null) {
                    aSMDeserializerFactory = new ASMDeserializerFactory(new ASMClassLoader());
                } else {
                    aSMDeserializerFactory = new ASMDeserializerFactory(classLoader);
                }
            } catch (ExceptionInInitializerError | NoClassDefFoundError | AccessControlException unused) {
            }
        }
        this.asmFactory = aSMDeserializerFactory;
        if (aSMDeserializerFactory == null) {
            this.asmEnable = false;
        }
        initDeserializers();
        addItemsToDeny(DENYS);
        addItemsToDeny0(DENYS_INTERNAL);
        addItemsToAccept(AUTO_TYPE_ACCEPT_LIST);
    }

    public ObjectDeserializer getDeserializer(Class<?> cls, Type type) {
        ObjectDeserializer objectDeserializerCreateJavaBeanDeserializer;
        ObjectDeserializer propertyProcessableDeserializer;
        Method enumCreator;
        Class<?> clsMappingTo;
        Type type2 = type;
        ObjectDeserializer objectDeserializer = get(type2);
        if (objectDeserializer == null && (type2 instanceof ParameterizedTypeImpl)) {
            objectDeserializer = get(TypeReference.intern((ParameterizedTypeImpl) type2));
        }
        if (objectDeserializer != null) {
            return objectDeserializer;
        }
        if (type2 == null) {
            type2 = cls;
        }
        ObjectDeserializer objectDeserializerCreateDeserializer = get(type2);
        if (objectDeserializerCreateDeserializer != null) {
            return objectDeserializerCreateDeserializer;
        }
        JSONType jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
        if (jSONType != null && (clsMappingTo = jSONType.mappingTo()) != Void.class) {
            return getDeserializer(clsMappingTo, clsMappingTo);
        }
        if ((type2 instanceof WildcardType) || (type2 instanceof TypeVariable) || (type2 instanceof ParameterizedType)) {
            objectDeserializerCreateDeserializer = get(cls);
        }
        if (objectDeserializerCreateDeserializer != null) {
            return objectDeserializerCreateDeserializer;
        }
        Iterator<Module> it = this.modules.iterator();
        while (it.hasNext()) {
            objectDeserializerCreateDeserializer = it.next().createDeserializer(this, cls);
            if (objectDeserializerCreateDeserializer != null) {
                putDeserializer(type2, objectDeserializerCreateDeserializer);
                return objectDeserializerCreateDeserializer;
            }
        }
        String strReplace = cls.getName().replace('$', '.');
        if (strReplace.startsWith("java.awt.") && AwtCodec.support(cls) && !awtError) {
            String[] strArr = {"java.awt.Point", "java.awt.Font", "java.awt.Rectangle", "java.awt.Color"};
            for (int i2 = 0; i2 < 4; i2++) {
                try {
                    String str = strArr[i2];
                    if (str.equals(strReplace)) {
                        Type cls2 = Class.forName(str);
                        ObjectDeserializer objectDeserializer2 = AwtCodec.instance;
                        putDeserializer(cls2, objectDeserializer2);
                        return objectDeserializer2;
                    }
                } catch (Throwable unused) {
                    awtError = true;
                }
            }
            objectDeserializerCreateDeserializer = AwtCodec.instance;
        }
        if (!jdk8Error) {
            try {
                if (strReplace.startsWith("java.time.")) {
                    String[] strArr2 = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.ZoneId", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                    for (int i3 = 0; i3 < 12; i3++) {
                        String str2 = strArr2[i3];
                        if (str2.equals(strReplace)) {
                            Type cls3 = Class.forName(str2);
                            ObjectDeserializer objectDeserializer3 = Jdk8DateCodec.instance;
                            putDeserializer(cls3, objectDeserializer3);
                            return objectDeserializer3;
                        }
                    }
                } else if (strReplace.startsWith("java.util.Optional")) {
                    String[] strArr3 = {"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"};
                    for (int i4 = 0; i4 < 4; i4++) {
                        String str3 = strArr3[i4];
                        if (str3.equals(strReplace)) {
                            Type cls4 = Class.forName(str3);
                            ObjectDeserializer objectDeserializer4 = OptionalCodec.instance;
                            putDeserializer(cls4, objectDeserializer4);
                            return objectDeserializer4;
                        }
                    }
                }
            } catch (Throwable unused2) {
                jdk8Error = true;
            }
        }
        if (!jodaError) {
            try {
                if (strReplace.startsWith("org.joda.time.")) {
                    String[] strArr4 = {"org.joda.time.DateTime", "org.joda.time.LocalDate", "org.joda.time.LocalDateTime", "org.joda.time.LocalTime", "org.joda.time.Instant", "org.joda.time.Period", "org.joda.time.Duration", "org.joda.time.DateTimeZone", "org.joda.time.format.DateTimeFormatter"};
                    for (int i5 = 0; i5 < 9; i5++) {
                        String str4 = strArr4[i5];
                        if (str4.equals(strReplace)) {
                            Type cls5 = Class.forName(str4);
                            objectDeserializerCreateDeserializer = JodaCodec.instance;
                            putDeserializer(cls5, objectDeserializerCreateDeserializer);
                            return objectDeserializerCreateDeserializer;
                        }
                    }
                }
            } catch (Throwable unused3) {
                jodaError = true;
            }
        }
        if (!guavaError && strReplace.startsWith("com.google.common.collect.")) {
            try {
                String[] strArr5 = {"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.LinkedHashMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"};
                for (int i6 = 0; i6 < 5; i6++) {
                    String str5 = strArr5[i6];
                    if (str5.equals(strReplace)) {
                        Type cls6 = Class.forName(str5);
                        objectDeserializerCreateDeserializer = GuavaCodec.instance;
                        putDeserializer(cls6, objectDeserializerCreateDeserializer);
                        return objectDeserializerCreateDeserializer;
                    }
                }
            } catch (ClassNotFoundException unused4) {
                guavaError = true;
            }
        }
        if (strReplace.equals("java.nio.ByteBuffer")) {
            objectDeserializerCreateDeserializer = ByteBufferCodec.instance;
            putDeserializer(cls, objectDeserializerCreateDeserializer);
        }
        if (strReplace.equals("java.nio.file.Path")) {
            objectDeserializerCreateDeserializer = MiscCodec.instance;
            putDeserializer(cls, objectDeserializerCreateDeserializer);
        }
        if (cls == Map.Entry.class) {
            objectDeserializerCreateDeserializer = MiscCodec.instance;
            putDeserializer(cls, objectDeserializerCreateDeserializer);
        }
        if (strReplace.equals("org.javamoney.moneta.Money")) {
            objectDeserializerCreateDeserializer = MonetaCodec.instance;
            putDeserializer(cls, objectDeserializerCreateDeserializer);
        }
        try {
            for (AutowiredObjectDeserializer autowiredObjectDeserializer : ServiceLoader.load(AutowiredObjectDeserializer.class, Thread.currentThread().getContextClassLoader())) {
                Iterator<Type> it2 = autowiredObjectDeserializer.getAutowiredFor().iterator();
                while (it2.hasNext()) {
                    putDeserializer(it2.next(), autowiredObjectDeserializer);
                }
            }
        } catch (Exception unused5) {
        }
        if (objectDeserializerCreateDeserializer == null) {
            objectDeserializerCreateDeserializer = get(type2);
        }
        if (objectDeserializerCreateDeserializer != null) {
            return objectDeserializerCreateDeserializer;
        }
        if (cls.isEnum()) {
            if (this.jacksonCompatible) {
                for (Method method : cls.getMethods()) {
                    if (TypeUtils.isJacksonCreator(method)) {
                        ObjectDeserializer objectDeserializerCreateJavaBeanDeserializer2 = createJavaBeanDeserializer(cls, type2);
                        putDeserializer(type2, objectDeserializerCreateJavaBeanDeserializer2);
                        return objectDeserializerCreateJavaBeanDeserializer2;
                    }
                }
            }
            Class<?> cls7 = (Class) JSON.getMixInAnnotations(cls);
            JSONType jSONType2 = (JSONType) TypeUtils.getAnnotation(cls7 != null ? cls7 : cls, JSONType.class);
            if (jSONType2 != null) {
                try {
                    ObjectDeserializer objectDeserializer5 = (ObjectDeserializer) jSONType2.deserializer().newInstance();
                    putDeserializer(cls, objectDeserializer5);
                    return objectDeserializer5;
                } catch (Throwable unused6) {
                }
            }
            if (cls7 != null) {
                Method enumCreator2 = getEnumCreator(cls7, cls);
                if (enumCreator2 != null) {
                    try {
                        enumCreator = cls.getMethod(enumCreator2.getName(), enumCreator2.getParameterTypes());
                    } catch (Exception unused7) {
                    }
                } else {
                    enumCreator = null;
                }
            } else {
                enumCreator = getEnumCreator(cls, cls);
            }
            if (enumCreator != null) {
                ObjectDeserializer enumCreatorDeserializer = new EnumCreatorDeserializer(enumCreator);
                putDeserializer(cls, enumCreatorDeserializer);
                return enumCreatorDeserializer;
            }
            objectDeserializerCreateJavaBeanDeserializer = getEnumDeserializer(cls);
        } else if (cls.isArray()) {
            objectDeserializerCreateJavaBeanDeserializer = ObjectArrayCodec.instance;
        } else if (cls == Set.class || cls == HashSet.class || cls == Collection.class || cls == List.class || cls == ArrayList.class || Collection.class.isAssignableFrom(cls)) {
            objectDeserializerCreateJavaBeanDeserializer = CollectionCodec.instance;
        } else if (Map.class.isAssignableFrom(cls)) {
            objectDeserializerCreateJavaBeanDeserializer = MapDeserializer.instance;
        } else {
            if (Throwable.class.isAssignableFrom(cls)) {
                propertyProcessableDeserializer = new ThrowableDeserializer(this, cls);
            } else if (PropertyProcessable.class.isAssignableFrom(cls)) {
                propertyProcessableDeserializer = new PropertyProcessableDeserializer(cls);
            } else if (cls == InetAddress.class) {
                objectDeserializerCreateJavaBeanDeserializer = MiscCodec.instance;
            } else {
                objectDeserializerCreateJavaBeanDeserializer = createJavaBeanDeserializer(cls, type2);
            }
            objectDeserializerCreateJavaBeanDeserializer = propertyProcessableDeserializer;
        }
        putDeserializer(type2, objectDeserializerCreateJavaBeanDeserializer);
        return objectDeserializerCreateJavaBeanDeserializer;
    }

    public ObjectDeserializer getDeserializer(FieldInfo fieldInfo) {
        return getDeserializer(fieldInfo.fieldClass, fieldInfo.fieldType);
    }
}
