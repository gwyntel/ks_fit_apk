package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses;
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext parseContext, String str) {
            this.context = parseContext;
            this.referenceValue = str;
        }
    }

    static {
        HashSet hashSet = new HashSet();
        primitiveClasses = hashSet;
        hashSet.addAll(Arrays.asList(Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class));
    }

    public DefaultJSONParser(String str) {
        this(str, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    private void addContext(ParseContext parseContext) {
        int i2 = this.contextArrayIndex;
        this.contextArrayIndex = i2 + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i2 >= parseContextArr.length) {
            ParseContext[] parseContextArr2 = new ParseContext[(parseContextArr.length * 3) / 2];
            System.arraycopy(parseContextArr, 0, parseContextArr2, 0, parseContextArr.length);
            this.contextArray = parseContextArr2;
        }
        this.contextArray[i2] = parseContext;
    }

    public final void accept(int i2) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i2) {
            jSONLexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(i2) + ", actual " + JSONToken.name(jSONLexer.token()));
    }

    public void acceptType(String str) {
        JSONLexer jSONLexer = this.lexer;
        jSONLexer.nextTokenWithColon();
        if (jSONLexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (!str.equals(jSONLexer.stringVal())) {
            throw new JSONException("type not match error");
        }
        jSONLexer.nextToken();
        if (jSONLexer.token() == 16) {
            jSONLexer.nextToken();
        }
    }

    public void addResolveTask(ResolveTask resolveTask) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(resolveTask);
    }

    public void checkListResolve(Collection collection) {
        if (this.resolveStatus == 1) {
            if (!(collection instanceof List)) {
                ResolveTask lastResolveTask = getLastResolveTask();
                lastResolveTask.fieldDeserializer = new ResolveFieldDeserializer(collection);
                lastResolveTask.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            int size = collection.size() - 1;
            ResolveTask lastResolveTask2 = getLastResolveTask();
            lastResolveTask2.fieldDeserializer = new ResolveFieldDeserializer(this, (List) collection, size);
            lastResolveTask2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map map, Object obj) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer resolveFieldDeserializer = new ResolveFieldDeserializer(map, obj);
            ResolveTask lastResolveTask = getLastResolveTask();
            lastResolveTask.fieldDeserializer = resolveFieldDeserializer;
            lastResolveTask.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer jSONLexer = this.lexer;
        try {
            if (jSONLexer.isEnabled(Feature.AutoCloseSource) && jSONLexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(jSONLexer.token()));
            }
        } finally {
            jSONLexer.close();
        }
    }

    public void config(Feature feature, boolean z2) {
        this.lexer.config(feature, z2);
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public ParseContext getContext() {
        return this.context;
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public String getInput() {
        Object obj = this.input;
        return obj instanceof char[] ? new String((char[]) obj) : obj.toString();
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(r0.size() - 1);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public Object getObject(String str) {
        for (int i2 = 0; i2 < this.contextArrayIndex; i2++) {
            if (str.equals(this.contextArray[i2].toString())) {
                return this.contextArray[i2].object;
            }
        }
        return null;
    }

    public ParseContext getOwnerContext() {
        return this.context.parent;
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public void handleResovleTask(Object obj) {
        Object objEval;
        ParseContext parseContext;
        FieldInfo fieldInfo;
        List<ResolveTask> list = this.resolveTaskList;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            ResolveTask resolveTask = this.resolveTaskList.get(i2);
            String str = resolveTask.referenceValue;
            ParseContext parseContext2 = resolveTask.ownerContext;
            Object obj2 = parseContext2 != null ? parseContext2.object : null;
            if (str.startsWith("$")) {
                objEval = getObject(str);
                if (objEval == null) {
                    try {
                        JSONPath jSONPath = new JSONPath(str, SerializeConfig.getGlobalInstance(), this.config, true);
                        if (jSONPath.isRef()) {
                            objEval = jSONPath.eval(obj);
                        }
                    } catch (JSONPathException unused) {
                    }
                }
            } else {
                objEval = resolveTask.context.object;
            }
            FieldDeserializer fieldDeserializer = resolveTask.fieldDeserializer;
            if (fieldDeserializer != null) {
                if (objEval != null && objEval.getClass() == JSONObject.class && (fieldInfo = fieldDeserializer.fieldInfo) != null && !Map.class.isAssignableFrom(fieldInfo.fieldClass)) {
                    Object obj3 = this.contextArray[0].object;
                    JSONPath jSONPathCompile = JSONPath.compile(str);
                    if (jSONPathCompile.isRef()) {
                        objEval = jSONPathCompile.eval(obj3);
                    }
                }
                if (fieldDeserializer.getOwnerClass() != null && !fieldDeserializer.getOwnerClass().isInstance(obj2) && (parseContext = resolveTask.ownerContext.parent) != null) {
                    while (true) {
                        if (parseContext == null) {
                            break;
                        }
                        if (fieldDeserializer.getOwnerClass().isInstance(parseContext.object)) {
                            obj2 = parseContext.object;
                            break;
                        }
                        parseContext = parseContext.parent;
                    }
                }
                fieldDeserializer.setValue(obj2, objEval);
            }
        }
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public Object parse() {
        return parse(null);
    }

    public <T> List<T> parseArray(Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        parseArray((Class<?>) cls, (Collection) arrayList);
        return arrayList;
    }

    public Object parseArrayWithType(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new JSONException("not support type " + type);
        }
        Type type2 = actualTypeArguments[0];
        if (type2 instanceof Class) {
            ArrayList arrayList = new ArrayList();
            parseArray((Class<?>) type2, (Collection) arrayList);
            return arrayList;
        }
        if (type2 instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type2;
            Type type3 = wildcardType.getUpperBounds()[0];
            if (!Object.class.equals(type3)) {
                ArrayList arrayList2 = new ArrayList();
                parseArray((Class<?>) type3, (Collection) arrayList2);
                return arrayList2;
            }
            if (wildcardType.getLowerBounds().length == 0) {
                return parse();
            }
            throw new JSONException("not support type : " + type);
        }
        if (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type type4 = bounds[0];
            if (type4 instanceof Class) {
                ArrayList arrayList3 = new ArrayList();
                parseArray((Class<?>) type4, (Collection) arrayList3);
                return arrayList3;
            }
        }
        if (type2 instanceof ParameterizedType) {
            ArrayList arrayList4 = new ArrayList();
            parseArray((ParameterizedType) type2, arrayList4);
            return arrayList4;
        }
        throw new JSONException("TODO : " + type);
    }

    public void parseExtra(Object obj, String str) {
        this.lexer.nextTokenWithColon();
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        Type extraType = null;
        if (list != null) {
            Iterator<ExtraTypeProvider> it = list.iterator();
            while (it.hasNext()) {
                extraType = it.next().getExtraType(obj, str);
            }
        }
        Object object = extraType == null ? parse() : parseObject(extraType);
        if (obj instanceof ExtraProcessable) {
            ((ExtraProcessable) obj).processExtra(str, object);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            Iterator<ExtraProcessor> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().processExtra(obj, str, object);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse(null);
        }
        String strStringVal = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return strStringVal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:149:0x02a6, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02b1, code lost:
    
        if (r5.token() != 13) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02b3, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02be, code lost:
    
        if ((r17.config.getDeserializer(r8) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02c0, code lost:
    
        r13 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r18, (java.lang.Class<java.lang.Object>) r8, r17.config);
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02c8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02ca, code lost:
    
        if (r13 != null) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02ce, code lost:
    
        if (r8 != java.lang.Cloneable.class) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02d0, code lost:
    
        r13 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02dc, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r7) == false) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02de, code lost:
    
        r13 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02e9, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r7) == false) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x02eb, code lost:
    
        r13 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x02f5, code lost:
    
        r13 = r8.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x02f9, code lost:
    
        setContext(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x02fc, code lost:
    
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0304, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0305, code lost:
    
        setResolveStatus(2);
        r3 = r17.context;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x030b, code lost:
    
        if (r3 == null) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x030d, code lost:
    
        if (r19 == null) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0311, code lost:
    
        if ((r19 instanceof java.lang.Integer) != false) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0317, code lost:
    
        if ((r3.fieldName instanceof java.lang.Integer) != false) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0319, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0320, code lost:
    
        if (r18.size() <= 0) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0322, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r18, (java.lang.Class<java.lang.Object>) r8, r17.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x032f, code lost:
    
        setContext(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0332, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0333, code lost:
    
        r0 = r17.config.getDeserializer(r8);
        r3 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0343, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r3) == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0347, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x034b, code lost:
    
        if (r3 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x034d, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0354, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0356, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x035a, code lost:
    
        r0 = r0.deserialze(r17, r8, r19);
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x035e, code lost:
    
        setContext(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0361, code lost:
    
        return r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:0x021e A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x029e A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0417  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0471 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0498 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:283:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x04f4 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x05f0  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x05f5 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0601 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:361:0x060d A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0622 A[Catch: all -> 0x0080, TRY_ENTER, TryCatch #0 {all -> 0x0080, blocks: (B:24:0x0074, B:26:0x0078, B:31:0x0085, B:34:0x0098, B:38:0x00ad, B:117:0x021e, B:118:0x0224, B:120:0x022f, B:122:0x0237, B:126:0x024d, B:128:0x025b, B:148:0x029e, B:149:0x02a6, B:151:0x02b3, B:152:0x02b6, B:154:0x02c0, B:160:0x02d0, B:161:0x02d6, B:163:0x02de, B:164:0x02e3, B:166:0x02eb, B:167:0x02f5, B:170:0x02fd, B:171:0x0304, B:172:0x0305, B:175:0x030f, B:177:0x0313, B:179:0x0319, B:180:0x031c, B:182:0x0322, B:185:0x0333, B:191:0x034d, B:195:0x035a, B:192:0x0352, B:194:0x0356, B:130:0x0261, B:133:0x026c, B:137:0x0278, B:139:0x027e, B:144:0x028b, B:145:0x028e, B:203:0x036b, B:207:0x0377, B:209:0x037f, B:211:0x0389, B:213:0x039a, B:215:0x03a4, B:217:0x03ac, B:219:0x03b0, B:221:0x03b6, B:224:0x03bb, B:226:0x03bf, B:249:0x0424, B:251:0x042c, B:254:0x0435, B:255:0x044f, B:228:0x03c5, B:230:0x03cd, B:233:0x03d3, B:234:0x03e0, B:237:0x03e9, B:240:0x03ef, B:243:0x03f4, B:244:0x0401, B:246:0x040b, B:248:0x0419, B:256:0x0450, B:257:0x046e, B:259:0x0471, B:261:0x0475, B:263:0x0479, B:266:0x047f, B:270:0x0488, B:276:0x0498, B:278:0x04a7, B:280:0x04b2, B:281:0x04ba, B:282:0x04bd, B:294:0x04e9, B:296:0x04f4, B:300:0x0501, B:303:0x0511, B:304:0x0531, B:289:0x04cd, B:291:0x04d7, B:293:0x04e6, B:292:0x04dc, B:307:0x0536, B:309:0x0540, B:311:0x0548, B:312:0x054b, B:314:0x0556, B:315:0x055a, B:317:0x0565, B:320:0x056c, B:323:0x0578, B:324:0x057d, B:327:0x0582, B:329:0x0587, B:333:0x0593, B:335:0x059b, B:337:0x05ae, B:341:0x05c9, B:343:0x05d1, B:346:0x05d7, B:348:0x05dd, B:350:0x05e5, B:353:0x05f5, B:356:0x05fd, B:358:0x0601, B:359:0x0608, B:361:0x060d, B:362:0x0610, B:364:0x0618, B:367:0x0622, B:370:0x062c, B:371:0x0631, B:372:0x0636, B:373:0x0650, B:338:0x05b7, B:339:0x05bc, B:374:0x0651, B:376:0x0663, B:379:0x066a, B:382:0x0678, B:383:0x0698, B:41:0x00c1, B:42:0x00df, B:45:0x00e4, B:47:0x00ef, B:49:0x00f3, B:51:0x00f7, B:54:0x00fd, B:61:0x010c, B:63:0x0114, B:66:0x0126, B:67:0x013e, B:68:0x013f, B:69:0x0144, B:80:0x0159, B:81:0x015f, B:83:0x0166, B:85:0x0171, B:92:0x0183, B:95:0x018d, B:96:0x01a5, B:90:0x017e, B:84:0x016b, B:97:0x01a6, B:98:0x01be, B:104:0x01c8, B:106:0x01d0, B:109:0x01e3, B:110:0x0203, B:111:0x0204, B:112:0x0209, B:113:0x020a, B:115:0x0214, B:384:0x0699, B:385:0x06a0, B:386:0x06a1, B:387:0x06a6, B:388:0x06a7, B:389:0x06ac), top: B:392:0x0074, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:406:0x02a6 A[EDGE_INSN: B:406:0x02a6->B:149:0x02a6 BREAK  A[LOOP:0: B:31:0x0085->B:422:0x0085], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x04fd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:416:0x0618 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object parseObject(java.util.Map r18, java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 1713
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        int i2 = this.contextArrayIndex;
        if (i2 <= 0) {
            return;
        }
        int i3 = i2 - 1;
        this.contextArrayIndex = i3;
        this.contextArray[i3] = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object resolveReference(java.lang.String r5) {
        /*
            r4 = this;
            com.alibaba.fastjson.parser.ParseContext[] r0 = r4.contextArray
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = 0
        L7:
            com.alibaba.fastjson.parser.ParseContext[] r2 = r4.contextArray
            int r3 = r2.length
            if (r0 >= r3) goto L22
            int r3 = r4.contextArrayIndex
            if (r0 >= r3) goto L22
            r2 = r2[r0]
            java.lang.String r3 = r2.toString()
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L1f
            java.lang.Object r5 = r2.object
            return r5
        L1f:
            int r0 = r0 + 1
            goto L7
        L22:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.resolveReference(java.lang.String):java.lang.Object");
    }

    public void setConfig(ParserConfig parserConfig) {
        this.config = parserConfig;
    }

    public void setContext(ParseContext parseContext) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = parseContext;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        setDateFormat(dateFormat);
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        this.dateFormat = null;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setResolveStatus(int i2) {
        this.resolveStatus = i2;
    }

    public void throwException(int i2) {
        throw new JSONException("syntax error, expect " + JSONToken.name(i2) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig) {
        this(str, new JSONScanner(str, JSON.DEFAULT_PARSER_FEATURE), parserConfig);
    }

    public Object parse(Object obj) {
        JSONLexer jSONLexer = this.lexer;
        int i2 = jSONLexer.token();
        if (i2 == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken();
            return numberIntegerValue;
        }
        if (i2 == 3) {
            Number numberDecimalValue = jSONLexer.decimalValue(jSONLexer.isEnabled(Feature.UseBigDecimal));
            jSONLexer.nextToken();
            return numberDecimalValue;
        }
        if (i2 == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                JSONScanner jSONScanner = new JSONScanner(strStringVal);
                try {
                    if (jSONScanner.scanISO8601DateIfMatch()) {
                        return jSONScanner.getCalendar().getTime();
                    }
                } finally {
                    jSONScanner.close();
                }
            }
            return strStringVal;
        }
        if (i2 == 12) {
            return parseObject(isEnabled(Feature.UseNativeJavaObject) ? jSONLexer.isEnabled(Feature.OrderedField) ? new HashMap() : new LinkedHashMap() : new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), obj);
        }
        if (i2 == 14) {
            Collection arrayList = isEnabled(Feature.UseNativeJavaObject) ? new ArrayList() : new JSONArray();
            parseArray(arrayList, obj);
            return jSONLexer.isEnabled(Feature.UseObjectArray) ? arrayList.toArray() : arrayList;
        }
        if (i2 == 18) {
            if ("NaN".equals(jSONLexer.stringVal())) {
                jSONLexer.nextToken();
                return null;
            }
            throw new JSONException("syntax error, " + jSONLexer.info());
        }
        if (i2 == 26) {
            byte[] bArrBytesValue = jSONLexer.bytesValue();
            jSONLexer.nextToken();
            return bArrBytesValue;
        }
        switch (i2) {
            case 6:
                jSONLexer.nextToken();
                return Boolean.TRUE;
            case 7:
                jSONLexer.nextToken();
                return Boolean.FALSE;
            case 8:
                jSONLexer.nextToken();
                return null;
            case 9:
                jSONLexer.nextToken(18);
                if (jSONLexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken(10);
                accept(10);
                long jLongValue = jSONLexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(jLongValue);
            default:
                switch (i2) {
                    case 20:
                        if (jSONLexer.isBlankInput()) {
                            return null;
                        }
                        throw new JSONException("unterminated json string, " + jSONLexer.info());
                    case 21:
                        jSONLexer.nextToken();
                        HashSet hashSet = new HashSet();
                        parseArray(hashSet, obj);
                        return hashSet;
                    case 22:
                        jSONLexer.nextToken();
                        TreeSet treeSet = new TreeSet();
                        parseArray(treeSet, obj);
                        return treeSet;
                    case 23:
                        jSONLexer.nextToken();
                        return null;
                    default:
                        throw new JSONException("syntax error, " + jSONLexer.info());
                }
        }
    }

    public DefaultJSONParser(String str, ParserConfig parserConfig, int i2) {
        this(str, new JSONScanner(str, i2), parserConfig);
    }

    public void parseArray(Class<?> cls, Collection collection) {
        parseArray((Type) cls, collection);
    }

    public ParseContext setContext(Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, obj, obj2);
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(char[] cArr, int i2, ParserConfig parserConfig, int i3) {
        this(cArr, new JSONScanner(cArr, i2, i3), parserConfig);
    }

    public void parseArray(Type type, Collection collection) {
        parseArray(type, collection, null);
    }

    public DefaultJSONParser(JSONLexer jSONLexer) {
        this(jSONLexer, ParserConfig.getGlobalInstance());
    }

    public void parseArray(Type type, Collection collection, Object obj) {
        ObjectDeserializer deserializer;
        int i2 = this.lexer.token();
        if (i2 == 21 || i2 == 22) {
            this.lexer.nextToken();
            i2 = this.lexer.token();
        }
        if (i2 == 14) {
            if (Integer.TYPE == type) {
                deserializer = IntegerCodec.instance;
                this.lexer.nextToken(2);
            } else if (String.class == type) {
                deserializer = StringCodec.instance;
                this.lexer.nextToken(4);
            } else {
                deserializer = this.config.getDeserializer(type);
                this.lexer.nextToken(deserializer.getFastMatchToken());
            }
            ParseContext parseContext = this.context;
            setContext(collection, obj);
            int i3 = 0;
            while (true) {
                try {
                    if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() == 15) {
                        setContext(parseContext);
                        this.lexer.nextToken(16);
                        return;
                    }
                    Object objDeserialze = null;
                    if (Integer.TYPE == type) {
                        collection.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            objDeserialze = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 != null) {
                                objDeserialze = obj2.toString();
                            }
                        }
                        collection.add(objDeserialze);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i3));
                        }
                        collection.add(objDeserialze);
                        checkListResolve(collection);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i3++;
                } catch (Throwable th) {
                    setContext(parseContext);
                    throw th;
                }
            }
        } else {
            throw new JSONException("field " + obj + " expect '[', but " + JSONToken.name(i2) + ", " + this.lexer.info());
        }
    }

    public ParseContext setContext(ParseContext parseContext, Object obj, Object obj2) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        ParseContext parseContext2 = new ParseContext(parseContext, obj, obj2);
        this.context = parseContext2;
        addContext(parseContext2);
        return this.context;
    }

    public DefaultJSONParser(JSONLexer jSONLexer, ParserConfig parserConfig) {
        this((Object) null, jSONLexer, parserConfig);
    }

    public final void accept(int i2, int i3) {
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == i2) {
            jSONLexer.nextToken(i3);
        } else {
            throwException(i2);
        }
    }

    public DefaultJSONParser(Object obj, JSONLexer jSONLexer, ParserConfig parserConfig) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = jSONLexer;
        this.input = obj;
        this.config = parserConfig;
        this.symbolTable = parserConfig.symbolTable;
        char current = jSONLexer.getCurrent();
        if (current == '{') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 12;
        } else if (current == '[') {
            jSONLexer.next();
            ((JSONLexerBase) jSONLexer).token = 14;
        } else {
            jSONLexer.nextToken();
        }
    }

    public Object[] parseArray(Type[] typeArr) {
        Object objCast;
        Class<?> componentType;
        boolean zIsArray;
        Class cls;
        int i2 = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i3 = 14;
        if (this.lexer.token() == 14) {
            Object[] objArr = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token() == 15) {
                    this.lexer.nextToken(16);
                    return new Object[0];
                }
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(2);
            int i4 = 0;
            while (i4 < typeArr.length) {
                if (this.lexer.token() == i2) {
                    this.lexer.nextToken(16);
                    objCast = null;
                } else {
                    Type type = typeArr[i4];
                    if (type != Integer.TYPE && type != Integer.class) {
                        if (type == String.class) {
                            if (this.lexer.token() == 4) {
                                objCast = this.lexer.stringVal();
                                this.lexer.nextToken(16);
                            } else {
                                objCast = TypeUtils.cast(parse(), type, this.config);
                            }
                        } else {
                            if (i4 == typeArr.length - 1 && (type instanceof Class) && (((cls = (Class) type) != byte[].class && cls != char[].class) || this.lexer.token() != 4)) {
                                zIsArray = cls.isArray();
                                componentType = cls.getComponentType();
                            } else {
                                componentType = null;
                                zIsArray = false;
                            }
                            if (zIsArray && this.lexer.token() != i3) {
                                ArrayList arrayList = new ArrayList();
                                ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                                int fastMatchToken = deserializer.getFastMatchToken();
                                if (this.lexer.token() != 15) {
                                    while (true) {
                                        arrayList.add(deserializer.deserialze(this, type, null));
                                        if (this.lexer.token() != 16) {
                                            break;
                                        }
                                        this.lexer.nextToken(fastMatchToken);
                                    }
                                    if (this.lexer.token() != 15) {
                                        throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                                    }
                                }
                                objCast = TypeUtils.cast(arrayList, type, this.config);
                            } else {
                                objCast = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i4));
                            }
                        }
                    } else if (this.lexer.token() == 2) {
                        objCast = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    } else {
                        objCast = TypeUtils.cast(parse(), type, this.config);
                    }
                }
                objArr[i4] = objCast;
                if (this.lexer.token() == 15) {
                    break;
                }
                if (this.lexer.token() == 16) {
                    if (i4 == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i4++;
                    i2 = 8;
                    i3 = 14;
                } else {
                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                }
            }
            if (this.lexer.token() == 15) {
                this.lexer.nextToken(16);
                return objArr;
            }
            throw new JSONException("syntax error");
        }
        throw new JSONException("syntax error : " + this.lexer.tokenName());
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x0239, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r11, java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 615
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }

    public final void parseArray(Collection collection) {
        parseArray(collection, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Object object;
        Number numberDecimalValue;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() == 14) {
            jSONLexer.nextToken(4);
            ParseContext parseContext = this.context;
            if (parseContext != null && parseContext.level > 512) {
                throw new JSONException("array level > 512");
            }
            setContext(collection, obj);
            int i2 = 0;
            while (true) {
                try {
                    try {
                        if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                            while (jSONLexer.token() == 16) {
                                jSONLexer.nextToken();
                            }
                        }
                        int i3 = jSONLexer.token();
                        if (i3 == 2) {
                            Number numberIntegerValue = jSONLexer.integerValue();
                            jSONLexer.nextToken(16);
                            object = numberIntegerValue;
                        } else if (i3 == 3) {
                            if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                                numberDecimalValue = jSONLexer.decimalValue(true);
                            } else {
                                numberDecimalValue = jSONLexer.decimalValue(false);
                            }
                            object = numberDecimalValue;
                            jSONLexer.nextToken(16);
                        } else if (i3 == 4) {
                            String strStringVal = jSONLexer.stringVal();
                            jSONLexer.nextToken(16);
                            object = strStringVal;
                            if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                                JSONScanner jSONScanner = new JSONScanner(strStringVal);
                                Object time = strStringVal;
                                if (jSONScanner.scanISO8601DateIfMatch()) {
                                    time = jSONScanner.getCalendar().getTime();
                                }
                                jSONScanner.close();
                                object = time;
                            }
                        } else if (i3 == 6) {
                            Boolean bool = Boolean.TRUE;
                            jSONLexer.nextToken(16);
                            object = bool;
                        } else if (i3 != 7) {
                            object = null;
                            object = null;
                            if (i3 == 8) {
                                jSONLexer.nextToken(4);
                            } else if (i3 == 12) {
                                object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i2));
                            } else {
                                if (i3 == 20) {
                                    throw new JSONException("unclosed jsonArray");
                                }
                                if (i3 == 23) {
                                    jSONLexer.nextToken(4);
                                } else if (i3 == 14) {
                                    JSONArray jSONArray = new JSONArray();
                                    parseArray(jSONArray, Integer.valueOf(i2));
                                    object = jSONArray;
                                    if (jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                        object = jSONArray.toArray();
                                    }
                                } else if (i3 != 15) {
                                    object = parse();
                                } else {
                                    jSONLexer.nextToken(16);
                                    setContext(parseContext);
                                    return;
                                }
                            }
                        } else {
                            Boolean bool2 = Boolean.FALSE;
                            jSONLexer.nextToken(16);
                            object = bool2;
                        }
                        collection.add(object);
                        checkListResolve(collection);
                        if (jSONLexer.token() == 16) {
                            jSONLexer.nextToken(4);
                        }
                        i2++;
                    } catch (ClassCastException e2) {
                        throw new JSONException("unkown error", e2);
                    }
                } catch (Throwable th) {
                    setContext(parseContext);
                    throw th;
                }
            }
        } else {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i2 = this.lexer.token();
        if (i2 == 8) {
            this.lexer.nextToken();
            return (T) TypeUtils.optionalEmpty(type);
        }
        if (i2 == 4) {
            if (type == byte[].class) {
                T t2 = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t2;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                if (this.lexer.token() != 12 && this.lexer.token() != 14) {
                    throw new JSONException("syntax error,expect start with { or [,but actually start with " + this.lexer.tokenName());
                }
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
            }
            return (T) deserializer.deserialze(this, type, obj);
        } catch (JSONException e2) {
            throw e2;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public void parseObject(Object obj) {
        Object objDeserialze;
        Class<?> cls = obj.getClass();
        ObjectDeserializer deserializer = this.config.getDeserializer(cls);
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String strScanSymbol = this.lexer.scanSymbol(this.symbolTable);
            if (strScanSymbol == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeserializer = javaBeanDeserializer != null ? javaBeanDeserializer.getFieldDeserializer(strScanSymbol) : null;
            if (fieldDeserializer == null) {
                if (this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    this.lexer.nextTokenWithColon();
                    parse();
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken();
                        return;
                    }
                } else {
                    throw new JSONException("setter not found, class " + cls.getName() + ", property " + strScanSymbol);
                }
            } else {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                Class<?> cls2 = fieldInfo.fieldClass;
                Type type = fieldInfo.fieldType;
                if (cls2 == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = IntegerCodec.instance.deserialze(this, type, null);
                } else if (cls2 == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    objDeserialze = StringCodec.deserialze(this);
                } else if (cls2 == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    objDeserialze = LongCodec.instance.deserialze(this, type, null);
                } else {
                    ObjectDeserializer deserializer2 = this.config.getDeserializer(cls2, type);
                    this.lexer.nextTokenWithColon(deserializer2.getFastMatchToken());
                    objDeserialze = deserializer2.deserialze(this, type, null);
                }
                fieldDeserializer.setValue(obj, objDeserialze);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseObject(Map map) {
        return parseObject(map, (Object) null);
    }

    public JSONObject parseObject() {
        Object object = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        if (object == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) object);
    }
}
