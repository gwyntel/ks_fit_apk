package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.mozilla.javascript.ES6Iterator;

/* loaded from: classes2.dex */
public class ASMDeserializerFactory implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    static class Context {
        static final int fieldName = 3;
        static final int parser = 1;
        static final int type = 2;
        private final JavaBeanInfo beanInfo;
        private final String className;
        private final Class<?> clazz;
        private FieldInfo[] fieldInfoList;
        private int variantIndex;
        private final Map<String, Integer> variants = new HashMap();

        public Context(String str, ParserConfig parserConfig, JavaBeanInfo javaBeanInfo, int i2) {
            this.variantIndex = -1;
            this.className = str;
            this.clazz = javaBeanInfo.clazz;
            this.variantIndex = i2;
            this.beanInfo = javaBeanInfo;
            this.fieldInfoList = javaBeanInfo.fields;
        }

        public String fieldDeserName(FieldInfo fieldInfo) {
            if (validIdent(fieldInfo.name)) {
                return fieldInfo.name + "_asm_deser__";
            }
            return "_asm_deser__" + TypeUtils.fnv1a_64_extract(fieldInfo.name);
        }

        public String fieldName(FieldInfo fieldInfo) {
            if (validIdent(fieldInfo.name)) {
                return fieldInfo.name + "_asm_prefix__";
            }
            return "asm_field_" + TypeUtils.fnv1a_64_extract(fieldInfo.name);
        }

        public Class<?> getInstClass() {
            Class<?> cls = this.beanInfo.builderClass;
            return cls == null ? this.clazz : cls;
        }

        boolean validIdent(String str) {
            for (int i2 = 0; i2 < str.length(); i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt == 0) {
                    if (!IOUtils.firstIdentifier(cCharAt)) {
                        return false;
                    }
                } else if (!IOUtils.isIdent(cCharAt)) {
                    return false;
                }
            }
            return true;
        }

        public int var(String str, int i2) {
            if (this.variants.get(str) == null) {
                this.variants.put(str, Integer.valueOf(this.variantIndex));
                this.variantIndex += i2;
            }
            return this.variants.get(str).intValue();
        }

        public int var_asm(FieldInfo fieldInfo) {
            return var(fieldInfo.name + "_asm");
        }

        public int var_asm(FieldInfo fieldInfo, int i2) {
            return var(fieldInfo.name + "_asm", i2);
        }

        public int var(String str) {
            if (this.variants.get(str) == null) {
                Map<String, Integer> map = this.variants;
                int i2 = this.variantIndex;
                this.variantIndex = i2 + 1;
                map.put(str, Integer.valueOf(i2));
            }
            return this.variants.get(str).intValue();
        }
    }

    public ASMDeserializerFactory(ClassLoader classLoader) {
        this.classLoader = classLoader instanceof ASMClassLoader ? (ASMClassLoader) classLoader : new ASMClassLoader(classLoader);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor) {
        _batchSet(context, methodVisitor, true);
    }

    private void _createInstance(Context context, MethodVisitor methodVisitor) {
        Constructor<?> constructor = context.beanInfo.defaultConstructor;
        if (Modifier.isPublic(constructor.getModifiers())) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(context.getInstClass()));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(constructor.getDeclaringClass()), "<init>", "()V");
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, ASMUtils.type(JavaBeanDeserializer.class), "clazz", "Ljava/lang/Class;");
            methodVisitor.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(192, ASMUtils.type(context.getInstClass()));
        }
        methodVisitor.visitVarInsn(58, context.var("instance"));
    }

    private void _deserObject(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls, int i2) {
        _getFieldDeser(context, methodVisitor, fieldInfo);
        Label label = new Label();
        Label label2 = new Label();
        if ((fieldInfo.parserFeatures & Feature.SupportArrayToBean.mask) != 0) {
            methodVisitor.visitInsn(89);
            methodVisitor.visitTypeInsn(193, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitJumpInsn(153, label);
            methodVisitor.visitTypeInsn(192, ASMUtils.type(JavaBeanDeserializer.class));
            methodVisitor.visitVarInsn(25, 1);
            if (fieldInfo.fieldType instanceof Class) {
                methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            } else {
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitLdcInsn(Integer.valueOf(i2));
                methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            methodVisitor.visitLdcInsn(fieldInfo.name);
            methodVisitor.visitLdcInsn(Integer.valueOf(fieldInfo.parserFeatures));
            methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            methodVisitor.visitVarInsn(58, context.var_asm(fieldInfo));
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label);
        }
        methodVisitor.visitVarInsn(25, 1);
        if (fieldInfo.fieldType instanceof Class) {
            methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i2));
            methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
        methodVisitor.visitVarInsn(58, context.var_asm(fieldInfo));
        methodVisitor.visitLabel(label2);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor methodVisitor, Label label) {
        methodVisitor.visitIntInsn(21, context.var("matchedCount"));
        methodVisitor.visitJumpInsn(158, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "token", "()I");
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitJumpInsn(160, label);
        _quickNextTokenComma(context, methodVisitor);
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x0ac5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void _deserialze(com.alibaba.fastjson.asm.ClassWriter r31, com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory.Context r32) {
        /*
            Method dump skipped, instructions count: 3268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory._deserialze(com.alibaba.fastjson.asm.ClassWriter, com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory$Context):void");
    }

    private void _deserialzeArrayMapping(ClassWriter classWriter, Context context) {
        int i2;
        Class<JavaBeanDeserializer> cls;
        int i3;
        char c2;
        char c3;
        boolean z2;
        char c4;
        int i4;
        int i5;
        char c5;
        StringBuilder sb = new StringBuilder();
        sb.append("(L");
        String str = DefaultJSONParser;
        sb.append(str);
        sb.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "deserialzeArrayMapping", sb.toString(), null, null);
        defineVarLexer(context, methodWriter);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(182, str, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
        methodWriter.visitMethodInsn(182, JSONLexerBase, "scanTypeName", "(" + ASMUtils.desc((Class<?>) SymbolTable.class) + ")Ljava/lang/String;");
        methodWriter.visitVarInsn(58, context.var("typeName"));
        Label label = new Label();
        methodWriter.visitVarInsn(25, context.var("typeName"));
        methodWriter.visitJumpInsn(198, label);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitMethodInsn(182, str, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodWriter.visitVarInsn(25, 0);
        Class<JavaBeanDeserializer> cls2 = JavaBeanDeserializer.class;
        methodWriter.visitFieldInsn(180, ASMUtils.type(cls2), "beanInfo", ASMUtils.desc((Class<?>) JavaBeanInfo.class));
        methodWriter.visitVarInsn(25, context.var("typeName"));
        methodWriter.visitMethodInsn(184, ASMUtils.type(cls2), "getSeeAlso", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + "Ljava/lang/String;)" + ASMUtils.desc(cls2));
        methodWriter.visitVarInsn(58, context.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, context.var("userTypeDeser"));
        methodWriter.visitTypeInsn(193, ASMUtils.type(cls2));
        methodWriter.visitJumpInsn(153, label);
        methodWriter.visitVarInsn(25, context.var("userTypeDeser"));
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitVarInsn(25, 3);
        methodWriter.visitVarInsn(25, 4);
        methodWriter.visitMethodInsn(182, ASMUtils.type(cls2), "deserialzeArrayMapping", "(L" + str + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        methodWriter.visitInsn(176);
        methodWriter.visitLabel(label);
        _createInstance(context, methodWriter);
        FieldInfo[] fieldInfoArr = context.beanInfo.sortedFields;
        int length = fieldInfoArr.length;
        int i6 = 0;
        while (i6 < length) {
            boolean z3 = i6 == length + (-1);
            int i7 = z3 ? 93 : 44;
            FieldInfo fieldInfo = fieldInfoArr[i6];
            Class<?> cls3 = fieldInfo.fieldClass;
            java.lang.reflect.Type type = fieldInfo.fieldType;
            int i8 = length;
            FieldInfo[] fieldInfoArr2 = fieldInfoArr;
            if (cls3 == Byte.TYPE || cls3 == Short.TYPE || cls3 == Integer.TYPE) {
                i2 = i6;
                cls = cls2;
                i3 = i8;
                c2 = 184;
                c3 = ':';
                z2 = true;
                c4 = 180;
                methodWriter.visitVarInsn(25, context.var("lexer"));
                methodWriter.visitVarInsn(16, i7);
                methodWriter.visitMethodInsn(182, JSONLexerBase, "scanInt", "(C)I");
                methodWriter.visitVarInsn(54, context.var_asm(fieldInfo));
            } else {
                boolean z4 = z3;
                int i9 = i6;
                if (cls3 == Byte.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str2 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str2, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label2 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str2, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label2);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label2);
                } else if (cls3 == Short.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str3 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str3, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label3 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str3, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label3);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label3);
                } else if (cls3 == Integer.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str4 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str4, "scanInt", "(C)I");
                    methodWriter.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label4 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str4, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label4);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label4);
                } else if (cls3 == Long.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    methodWriter.visitMethodInsn(182, JSONLexerBase, "scanLong", "(C)J");
                    methodWriter.visitVarInsn(55, context.var_asm(fieldInfo, 2));
                } else if (cls3 == Long.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str5 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str5, "scanLong", "(C)J");
                    methodWriter.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label5 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str5, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label5);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label5);
                } else if (cls3 == Boolean.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    methodWriter.visitMethodInsn(182, JSONLexerBase, "scanBoolean", "(C)Z");
                    methodWriter.visitVarInsn(54, context.var_asm(fieldInfo));
                } else if (cls3 == Float.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    methodWriter.visitMethodInsn(182, JSONLexerBase, "scanFloat", "(C)F");
                    methodWriter.visitVarInsn(56, context.var_asm(fieldInfo));
                } else if (cls3 == Float.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str6 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str6, "scanFloat", "(C)F");
                    methodWriter.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label6 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str6, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label6);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label6);
                } else if (cls3 == Double.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    methodWriter.visitMethodInsn(182, JSONLexerBase, "scanDouble", "(C)D");
                    methodWriter.visitVarInsn(57, context.var_asm(fieldInfo, 2));
                } else if (cls3 == Double.class) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    String str7 = JSONLexerBase;
                    methodWriter.visitMethodInsn(182, str7, "scanDouble", "(C)D");
                    methodWriter.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label label7 = new Label();
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitFieldInsn(180, str7, "matchStat", "I");
                    methodWriter.visitLdcInsn(5);
                    methodWriter.visitJumpInsn(160, label7);
                    methodWriter.visitInsn(1);
                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    methodWriter.visitLabel(label7);
                } else if (cls3 == Character.TYPE) {
                    methodWriter.visitVarInsn(25, context.var("lexer"));
                    methodWriter.visitVarInsn(16, i7);
                    methodWriter.visitMethodInsn(182, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                    methodWriter.visitInsn(3);
                    methodWriter.visitMethodInsn(182, "java/lang/String", "charAt", "(I)C");
                    methodWriter.visitVarInsn(54, context.var_asm(fieldInfo));
                } else {
                    if (cls3 == String.class) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                        c5 = ':';
                        methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    } else if (cls3 == BigDecimal.class) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, JSONLexerBase, "scanDecimal", "(C)Ljava/math/BigDecimal;");
                        c5 = ':';
                        methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    } else if (cls3 == Date.class) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, JSONLexerBase, "scanDate", "(C)Ljava/util/Date;");
                        c5 = ':';
                        methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    } else if (cls3 == UUID.class) {
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, JSONLexerBase, "scanUUID", "(C)Ljava/util/UUID;");
                        c5 = ':';
                        methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                    } else if (cls3.isEnum()) {
                        Label label8 = new Label();
                        Label label9 = new Label();
                        Label label10 = new Label();
                        Label label11 = new Label();
                        cls = cls2;
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        String str8 = JSONLexerBase;
                        methodWriter.visitMethodInsn(182, str8, "getCurrent", "()C");
                        methodWriter.visitInsn(89);
                        methodWriter.visitVarInsn(54, context.var("ch"));
                        methodWriter.visitLdcInsn(110);
                        methodWriter.visitJumpInsn(159, label11);
                        methodWriter.visitVarInsn(21, context.var("ch"));
                        methodWriter.visitLdcInsn(34);
                        methodWriter.visitJumpInsn(160, label8);
                        methodWriter.visitLabel(label11);
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                        methodWriter.visitVarInsn(25, 1);
                        methodWriter.visitMethodInsn(182, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, str8, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc((Class<?>) SymbolTable.class) + "C)Ljava/lang/Enum;");
                        methodWriter.visitJumpInsn(167, label10);
                        methodWriter.visitLabel(label8);
                        methodWriter.visitVarInsn(21, context.var("ch"));
                        methodWriter.visitLdcInsn(48);
                        methodWriter.visitJumpInsn(161, label9);
                        methodWriter.visitVarInsn(21, context.var("ch"));
                        methodWriter.visitLdcInsn(57);
                        methodWriter.visitJumpInsn(163, label9);
                        _getFieldDeser(context, methodWriter, fieldInfo);
                        methodWriter.visitTypeInsn(192, ASMUtils.type(EnumDeserializer.class));
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, str8, "scanInt", "(C)I");
                        methodWriter.visitMethodInsn(182, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                        methodWriter.visitJumpInsn(167, label10);
                        methodWriter.visitLabel(label9);
                        methodWriter.visitVarInsn(25, 0);
                        methodWriter.visitVarInsn(25, context.var("lexer"));
                        methodWriter.visitVarInsn(16, i7);
                        methodWriter.visitMethodInsn(182, ASMUtils.type(cls), "scanEnum", "(L" + str8 + ";C)Ljava/lang/Enum;");
                        methodWriter.visitLabel(label10);
                        methodWriter.visitTypeInsn(192, ASMUtils.type(cls3));
                        methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                        c3 = ':';
                        i3 = i8;
                        i2 = i9;
                        c2 = 184;
                        z2 = true;
                        c4 = 180;
                    } else {
                        cls = cls2;
                        if (Collection.class.isAssignableFrom(cls3)) {
                            Class<?> collectionItemClass = TypeUtils.getCollectionItemClass(type);
                            if (collectionItemClass == String.class) {
                                if (cls3 == List.class || cls3 == Collections.class || cls3 == ArrayList.class) {
                                    methodWriter.visitTypeInsn(187, ASMUtils.type(ArrayList.class));
                                    methodWriter.visitInsn(89);
                                    methodWriter.visitMethodInsn(183, ASMUtils.type(ArrayList.class), "<init>", "()V");
                                } else {
                                    methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(cls3)));
                                    methodWriter.visitMethodInsn(184, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                                }
                                methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitVarInsn(25, context.var_asm(fieldInfo));
                                methodWriter.visitVarInsn(16, i7);
                                String str9 = JSONLexerBase;
                                methodWriter.visitMethodInsn(182, str9, "scanStringArray", "(Ljava/util/Collection;C)V");
                                Label label12 = new Label();
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitFieldInsn(180, str9, "matchStat", "I");
                                methodWriter.visitLdcInsn(5);
                                methodWriter.visitJumpInsn(160, label12);
                                methodWriter.visitInsn(1);
                                methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                                methodWriter.visitLabel(label12);
                                i5 = i9;
                                c2 = 184;
                            } else {
                                Label label13 = new Label();
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                String str10 = JSONLexerBase;
                                methodWriter.visitMethodInsn(182, str10, "token", "()I");
                                methodWriter.visitVarInsn(54, context.var("token"));
                                methodWriter.visitVarInsn(21, context.var("token"));
                                int i10 = i9 == 0 ? 14 : 16;
                                methodWriter.visitLdcInsn(Integer.valueOf(i10));
                                methodWriter.visitJumpInsn(159, label13);
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitLdcInsn(Integer.valueOf(i10));
                                String str11 = DefaultJSONParser;
                                methodWriter.visitMethodInsn(182, str11, "throwException", "(I)V");
                                methodWriter.visitLabel(label13);
                                Label label14 = new Label();
                                Label label15 = new Label();
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitMethodInsn(182, str10, "getCurrent", "()C");
                                methodWriter.visitVarInsn(16, 91);
                                methodWriter.visitJumpInsn(160, label14);
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitMethodInsn(182, str10, ES6Iterator.NEXT_METHOD, "()C");
                                methodWriter.visitInsn(87);
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(182, str10, "setToken", "(I)V");
                                methodWriter.visitJumpInsn(167, label15);
                                methodWriter.visitLabel(label14);
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(182, str10, "nextToken", "(I)V");
                                methodWriter.visitLabel(label15);
                                i5 = i9;
                                _newCollection(methodWriter, cls3, i5, false);
                                methodWriter.visitInsn(89);
                                methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                                _getCollectionFieldItemDeser(context, methodWriter, fieldInfo, collectionItemClass);
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitLdcInsn(Type.getType(ASMUtils.desc(collectionItemClass)));
                                methodWriter.visitVarInsn(25, 3);
                                c2 = 184;
                                methodWriter.visitMethodInsn(184, ASMUtils.type(cls), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + "L" + str11 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                            }
                            i2 = i5;
                            i3 = i8;
                            c3 = ':';
                            z2 = true;
                            c4 = 180;
                        } else {
                            c2 = 184;
                            if (cls3.isArray()) {
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitLdcInsn(14);
                                methodWriter.visitMethodInsn(182, JSONLexerBase, "nextToken", "(I)V");
                                z2 = true;
                                methodWriter.visitVarInsn(25, 1);
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitLdcInsn(Integer.valueOf(i9));
                                methodWriter.visitMethodInsn(182, ASMUtils.type(cls), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                                methodWriter.visitMethodInsn(182, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                                methodWriter.visitTypeInsn(192, ASMUtils.type(cls3));
                                methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                                c3 = ':';
                                i2 = i9;
                                i3 = i8;
                                c4 = 180;
                            } else {
                                z2 = true;
                                Label label16 = new Label();
                                Label label17 = new Label();
                                if (cls3 == Date.class) {
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    String str12 = JSONLexerBase;
                                    methodWriter.visitMethodInsn(182, str12, "getCurrent", "()C");
                                    methodWriter.visitLdcInsn(49);
                                    methodWriter.visitJumpInsn(160, label16);
                                    methodWriter.visitTypeInsn(187, ASMUtils.type(Date.class));
                                    methodWriter.visitInsn(89);
                                    methodWriter.visitVarInsn(25, context.var("lexer"));
                                    i4 = 16;
                                    methodWriter.visitVarInsn(16, i7);
                                    methodWriter.visitMethodInsn(182, str12, "scanLong", "(C)J");
                                    methodWriter.visitMethodInsn(183, ASMUtils.type(Date.class), "<init>", "(J)V");
                                    c3 = ':';
                                    methodWriter.visitVarInsn(58, context.var_asm(fieldInfo));
                                    methodWriter.visitJumpInsn(167, label17);
                                } else {
                                    i4 = 16;
                                    c3 = ':';
                                }
                                methodWriter.visitLabel(label16);
                                _quickNextToken(context, methodWriter, 14);
                                i2 = i9;
                                i3 = i8;
                                c4 = 180;
                                _deserObject(context, methodWriter, fieldInfo, cls3, i2);
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                methodWriter.visitMethodInsn(182, JSONLexerBase, "token", "()I");
                                methodWriter.visitLdcInsn(15);
                                methodWriter.visitJumpInsn(159, label17);
                                methodWriter.visitVarInsn(25, 0);
                                methodWriter.visitVarInsn(25, context.var("lexer"));
                                if (z4) {
                                    methodWriter.visitLdcInsn(15);
                                } else {
                                    methodWriter.visitLdcInsn(Integer.valueOf(i4));
                                }
                                methodWriter.visitMethodInsn(183, ASMUtils.type(cls), "check", "(" + ASMUtils.desc((Class<?>) JSONLexer.class) + "I)V");
                                methodWriter.visitLabel(label17);
                            }
                        }
                    }
                    c3 = c5;
                    cls = cls2;
                    i3 = i8;
                    i2 = i9;
                    c2 = 184;
                    z2 = true;
                    c4 = 180;
                }
                cls = cls2;
                i3 = i8;
                i2 = i9;
                c2 = 184;
                c3 = ':';
                z2 = true;
                c4 = 180;
            }
            i6 = i2 + 1;
            length = i3;
            fieldInfoArr = fieldInfoArr2;
            cls2 = cls;
        }
        _batchSet(context, methodWriter, false);
        Label label18 = new Label();
        Label label19 = new Label();
        Label label20 = new Label();
        Label label21 = new Label();
        methodWriter.visitVarInsn(25, context.var("lexer"));
        String str13 = JSONLexerBase;
        methodWriter.visitMethodInsn(182, str13, "getCurrent", "()C");
        methodWriter.visitInsn(89);
        methodWriter.visitVarInsn(54, context.var("ch"));
        methodWriter.visitVarInsn(16, 44);
        methodWriter.visitJumpInsn(160, label19);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(182, str13, ES6Iterator.NEXT_METHOD, "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(182, str13, "setToken", "(I)V");
        methodWriter.visitJumpInsn(167, label21);
        methodWriter.visitLabel(label19);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 93);
        methodWriter.visitJumpInsn(160, label20);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(182, str13, ES6Iterator.NEXT_METHOD, "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(15);
        methodWriter.visitMethodInsn(182, str13, "setToken", "(I)V");
        methodWriter.visitJumpInsn(167, label21);
        methodWriter.visitLabel(label20);
        methodWriter.visitVarInsn(21, context.var("ch"));
        methodWriter.visitVarInsn(16, 26);
        methodWriter.visitJumpInsn(160, label18);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitMethodInsn(182, str13, ES6Iterator.NEXT_METHOD, "()C");
        methodWriter.visitInsn(87);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(20);
        methodWriter.visitMethodInsn(182, str13, "setToken", "(I)V");
        methodWriter.visitJumpInsn(167, label21);
        methodWriter.visitLabel(label18);
        methodWriter.visitVarInsn(25, context.var("lexer"));
        methodWriter.visitLdcInsn(16);
        methodWriter.visitMethodInsn(182, str13, "nextToken", "(I)V");
        methodWriter.visitLabel(label21);
        methodWriter.visitVarInsn(25, context.var("instance"));
        methodWriter.visitInsn(176);
        methodWriter.visitMaxs(5, context.variantIndex);
        methodWriter.visitEnd();
    }

    private void _deserialze_list_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, Class<?> cls2, int i2) {
        String str;
        String str2;
        String str3;
        String str4;
        Label label2;
        int i3;
        int i4;
        int i5;
        Label label3 = new Label();
        String str5 = JSONLexerBase;
        methodVisitor.visitMethodInsn(182, str5, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(153, label3);
        _setFlag(methodVisitor, context, i2);
        Label label4 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str5, "token", "()I");
        methodVisitor.visitLdcInsn(8);
        methodVisitor.visitJumpInsn(160, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(182, str5, "nextToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label4);
        Label label5 = new Label();
        Label label6 = new Label();
        Label label7 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str5, "token", "()I");
        methodVisitor.visitLdcInsn(21);
        methodVisitor.visitJumpInsn(160, label6);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitMethodInsn(182, str5, "nextToken", "(I)V");
        _newCollection(methodVisitor, cls, i2, true);
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label6);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str5, "token", "()I");
        methodVisitor.visitLdcInsn(14);
        methodVisitor.visitJumpInsn(159, label7);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str5, "token", "()I");
        methodVisitor.visitLdcInsn(12);
        methodVisitor.visitJumpInsn(160, label);
        _newCollection(methodVisitor, cls, i2, false);
        methodVisitor.visitVarInsn(58, context.var_asm(fieldInfo));
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitInsn(3);
        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        String strType = ASMUtils.type(ObjectDeserializer.class);
        StringBuilder sb = new StringBuilder();
        sb.append("(L");
        String str6 = DefaultJSONParser;
        sb.append(str6);
        sb.append(";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        methodVisitor.visitMethodInsn(185, strType, "deserialze", sb.toString());
        methodVisitor.visitVarInsn(58, context.var("list_item_value"));
        methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
        methodVisitor.visitVarInsn(25, context.var("list_item_value"));
        if (cls.isInterface()) {
            str = "list_item_value";
            methodVisitor.visitMethodInsn(185, ASMUtils.type(cls), TmpConstant.GROUP_OP_ADD, "(Ljava/lang/Object;)Z");
        } else {
            str = "list_item_value";
            methodVisitor.visitMethodInsn(182, ASMUtils.type(cls), TmpConstant.GROUP_OP_ADD, "(Ljava/lang/Object;)Z");
        }
        methodVisitor.visitInsn(87);
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label7);
        _newCollection(methodVisitor, cls, i2, false);
        methodVisitor.visitLabel(label5);
        methodVisitor.visitVarInsn(58, context.var_asm(fieldInfo));
        boolean zIsPrimitive2 = ParserConfig.isPrimitive2(fieldInfo.fieldClass);
        _getCollectionFieldItemDeser(context, methodVisitor, fieldInfo, cls2);
        if (zIsPrimitive2) {
            methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "getFastMatchToken", "()I");
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            str2 = str5;
            str3 = "nextToken";
            str4 = "(I)V";
            methodVisitor.visitMethodInsn(182, str2, str3, str4);
            label2 = label3;
        } else {
            str2 = str5;
            str3 = "nextToken";
            str4 = "(I)V";
            methodVisitor.visitInsn(87);
            methodVisitor.visitLdcInsn(12);
            label2 = label3;
            methodVisitor.visitVarInsn(54, context.var("fastMatchToken"));
            _quickNextToken(context, methodVisitor, 12);
        }
        methodVisitor.visitVarInsn(25, 1);
        String str7 = str4;
        methodVisitor.visitMethodInsn(182, str6, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitVarInsn(58, context.var("listContext"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(182, str6, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitInsn(87);
        Label label8 = new Label();
        Label label9 = new Label();
        methodVisitor.visitInsn(3);
        String str8 = str3;
        methodVisitor.visitVarInsn(54, context.var("i"));
        methodVisitor.visitLabel(label8);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str2, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(159, label9);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls2)));
        methodVisitor.visitVarInsn(21, context.var("i"));
        methodVisitor.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        methodVisitor.visitMethodInsn(185, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + str6 + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        String str9 = str;
        methodVisitor.visitVarInsn(58, context.var(str9));
        methodVisitor.visitIincInsn(context.var("i"), 1);
        methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
        methodVisitor.visitVarInsn(25, context.var(str9));
        if (cls.isInterface()) {
            methodVisitor.visitMethodInsn(185, ASMUtils.type(cls), TmpConstant.GROUP_OP_ADD, "(Ljava/lang/Object;)Z");
            i4 = 87;
            i3 = 182;
        } else {
            i3 = 182;
            methodVisitor.visitMethodInsn(182, ASMUtils.type(cls), TmpConstant.GROUP_OP_ADD, "(Ljava/lang/Object;)Z");
            i4 = 87;
        }
        methodVisitor.visitInsn(i4);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
        methodVisitor.visitMethodInsn(i3, str6, "checkListResolve", "(Ljava/util/Collection;)V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(i3, str2, "token", "()I");
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitJumpInsn(160, label8);
        if (zIsPrimitive2) {
            methodVisitor.visitVarInsn(25, context.var("lexer"));
            methodVisitor.visitVarInsn(21, context.var("fastMatchToken"));
            methodVisitor.visitMethodInsn(i3, str2, str8, str7);
            i5 = 167;
        } else {
            _quickNextToken(context, methodVisitor, 12);
            i5 = 167;
        }
        methodVisitor.visitJumpInsn(i5, label8);
        methodVisitor.visitLabel(label9);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var("listContext"));
        methodVisitor.visitMethodInsn(182, str6, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str2, "token", "()I");
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitJumpInsn(160, label);
        _quickNextTokenComma(context, methodVisitor);
        methodVisitor.visitLabel(label2);
    }

    private void _deserialze_obj(Context context, MethodVisitor methodVisitor, Label label, FieldInfo fieldInfo, Class<?> cls, int i2) {
        Label label2 = new Label();
        Label label3 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, context.fieldName(fieldInfo), "[C");
        methodVisitor.visitMethodInsn(182, JSONLexerBase, "matchField", "([C)Z");
        methodVisitor.visitJumpInsn(154, label2);
        methodVisitor.visitInsn(1);
        methodVisitor.visitVarInsn(58, context.var_asm(fieldInfo));
        methodVisitor.visitJumpInsn(167, label3);
        methodVisitor.visitLabel(label2);
        _setFlag(methodVisitor, context, i2);
        methodVisitor.visitVarInsn(21, context.var("matchedCount"));
        methodVisitor.visitInsn(4);
        methodVisitor.visitInsn(96);
        methodVisitor.visitVarInsn(54, context.var("matchedCount"));
        _deserObject(context, methodVisitor, fieldInfo, cls, i2);
        methodVisitor.visitVarInsn(25, 1);
        String str = DefaultJSONParser;
        methodVisitor.visitMethodInsn(182, str, "getResolveStatus", "()I");
        methodVisitor.visitLdcInsn(1);
        methodVisitor.visitJumpInsn(160, label3);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, str, "getLastResolveTask", "()" + ASMUtils.desc((Class<?>) DefaultJSONParser.ResolveTask.class));
        methodVisitor.visitVarInsn(58, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, str, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "ownerContext", ASMUtils.desc((Class<?>) ParseContext.class));
        methodVisitor.visitVarInsn(25, context.var("resolveTask"));
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitLdcInsn(fieldInfo.name);
        methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc((Class<?>) FieldDeserializer.class));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "fieldDeserializer", ASMUtils.desc((Class<?>) FieldDeserializer.class));
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitMethodInsn(182, str, "setResolveStatus", "(I)V");
        methodVisitor.visitLabel(label3);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo, Class<?> cls) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(cls)));
        methodVisitor.visitMethodInsn(182, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    private void _getFieldDeser(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Label label = new Label();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitJumpInsn(199, label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        methodVisitor.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        methodVisitor.visitMethodInsn(182, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitFieldInsn(181, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitFieldInsn(180, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    private void _init(ClassWriter classWriter, Context context) {
        int length = context.fieldInfoList.length;
        for (int i2 = 0; i2 < length; i2++) {
            new FieldWriter(classWriter, 1, context.fieldName(context.fieldInfoList[i2]), "[C").visitEnd();
        }
        int length2 = context.fieldInfoList.length;
        for (int i3 = 0; i3 < length2; i3++) {
            FieldInfo fieldInfo = context.fieldInfoList[i3];
            Class<?> cls = fieldInfo.fieldClass;
            if (!cls.isPrimitive()) {
                if (Collection.class.isAssignableFrom(cls)) {
                    new FieldWriter(classWriter, 1, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class)).visitEnd();
                } else {
                    new FieldWriter(classWriter, 1, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class)).visitEnd();
                }
            }
        }
        MethodWriter methodWriter = new MethodWriter(classWriter, 1, "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V", null, null);
        methodWriter.visitVarInsn(25, 0);
        methodWriter.visitVarInsn(25, 1);
        methodWriter.visitVarInsn(25, 2);
        methodWriter.visitMethodInsn(183, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V");
        int length3 = context.fieldInfoList.length;
        for (int i4 = 0; i4 < length3; i4++) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i4];
            methodWriter.visitVarInsn(25, 0);
            methodWriter.visitLdcInsn("\"" + fieldInfo2.name + "\":");
            methodWriter.visitMethodInsn(182, "java/lang/String", "toCharArray", "()[C");
            methodWriter.visitFieldInsn(181, context.className, context.fieldName(fieldInfo2), "[C");
        }
        methodWriter.visitInsn(177);
        methodWriter.visitMaxs(4, 4);
        methodWriter.visitEnd();
    }

    private void _isFlag(MethodVisitor methodVisitor, Context context, int i2, Label label) {
        methodVisitor.visitVarInsn(21, context.var("_asm_flag_" + (i2 / 32)));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i2));
        methodVisitor.visitInsn(126);
        methodVisitor.visitJumpInsn(153, label);
    }

    private void _loadAndSet(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Class<?> cls = fieldInfo.fieldClass;
        java.lang.reflect.Type type = fieldInfo.fieldType;
        if (cls == Boolean.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
            return;
        }
        if (cls == Byte.TYPE || cls == Short.TYPE || cls == Integer.TYPE || cls == Character.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(21, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
            return;
        }
        if (cls == Long.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(22, context.var_asm(fieldInfo, 2));
            if (fieldInfo.method == null) {
                methodVisitor.visitFieldInsn(181, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
                return;
            }
            methodVisitor.visitMethodInsn(182, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
            if (fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                return;
            }
            methodVisitor.visitInsn(87);
            return;
        }
        if (cls == Float.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(23, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
            return;
        }
        if (cls == Double.TYPE) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(24, context.var_asm(fieldInfo, 2));
            _set(context, methodVisitor, fieldInfo);
            return;
        }
        if (cls == String.class) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
            return;
        }
        if (cls.isEnum()) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
        } else if (!Collection.class.isAssignableFrom(cls)) {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, methodVisitor, fieldInfo);
        } else {
            methodVisitor.visitVarInsn(25, context.var("instance"));
            if (TypeUtils.getCollectionItemClass(type) == String.class) {
                methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
                methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
            } else {
                methodVisitor.visitVarInsn(25, context.var_asm(fieldInfo));
            }
            _set(context, methodVisitor, fieldInfo);
        }
    }

    private void _newCollection(MethodVisitor methodVisitor, Class<?> cls, int i2, boolean z2) {
        if (cls.isAssignableFrom(ArrayList.class) && !z2) {
            methodVisitor.visitTypeInsn(187, "java/util/ArrayList");
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, "java/util/ArrayList", "<init>", "()V");
        } else if (cls.isAssignableFrom(LinkedList.class) && !z2) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(LinkedList.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(LinkedList.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(HashSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(TreeSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(TreeSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(TreeSet.class), "<init>", "()V");
        } else if (cls.isAssignableFrom(LinkedHashSet.class)) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(LinkedHashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(LinkedHashSet.class), "<init>", "()V");
        } else if (z2) {
            methodVisitor.visitTypeInsn(187, ASMUtils.type(HashSet.class));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else {
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitLdcInsn(Integer.valueOf(i2));
            methodVisitor.visitMethodInsn(182, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            methodVisitor.visitMethodInsn(184, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
        }
        methodVisitor.visitTypeInsn(192, ASMUtils.type(cls));
    }

    private void _quickNextToken(Context context, MethodVisitor methodVisitor, int i2) {
        Label label = new Label();
        Label label2 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        String str = JSONLexerBase;
        methodVisitor.visitMethodInsn(182, str, "getCurrent", "()C");
        if (i2 == 12) {
            methodVisitor.visitVarInsn(16, 123);
        } else {
            if (i2 != 14) {
                throw new IllegalStateException();
            }
            methodVisitor.visitVarInsn(16, 91);
        }
        methodVisitor.visitJumpInsn(160, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str, ES6Iterator.NEXT_METHOD, "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i2));
        methodVisitor.visitMethodInsn(182, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label2);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(Integer.valueOf(i2));
        methodVisitor.visitMethodInsn(182, str, "nextToken", "(I)V");
        methodVisitor.visitLabel(label2);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor methodVisitor) {
        Label label = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        String str = JSONLexerBase;
        methodVisitor.visitMethodInsn(182, str, "getCurrent", "()C");
        methodVisitor.visitInsn(89);
        methodVisitor.visitVarInsn(54, context.var("ch"));
        methodVisitor.visitVarInsn(16, 44);
        methodVisitor.visitJumpInsn(160, label2);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str, ES6Iterator.NEXT_METHOD, "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(16);
        methodVisitor.visitMethodInsn(182, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 125);
        methodVisitor.visitJumpInsn(160, label3);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str, ES6Iterator.NEXT_METHOD, "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(13);
        methodVisitor.visitMethodInsn(182, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 93);
        methodVisitor.visitJumpInsn(160, label4);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str, ES6Iterator.NEXT_METHOD, "()C");
        methodVisitor.visitInsn(87);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(15);
        methodVisitor.visitMethodInsn(182, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitVarInsn(21, context.var("ch"));
        methodVisitor.visitVarInsn(16, 26);
        methodVisitor.visitJumpInsn(160, label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitLdcInsn(20);
        methodVisitor.visitMethodInsn(182, str, "setToken", "(I)V");
        methodVisitor.visitJumpInsn(167, label5);
        methodVisitor.visitLabel(label);
        methodVisitor.visitVarInsn(25, context.var("lexer"));
        methodVisitor.visitMethodInsn(182, str, "nextToken", "()V");
        methodVisitor.visitLabel(label5);
    }

    private void _set(Context context, MethodVisitor methodVisitor, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method == null) {
            methodVisitor.visitFieldInsn(181, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
            return;
        }
        methodVisitor.visitMethodInsn(method.getDeclaringClass().isInterface() ? 185 : 182, ASMUtils.type(fieldInfo.declaringClass), method.getName(), ASMUtils.desc(method));
        if (fieldInfo.method.getReturnType().equals(Void.TYPE)) {
            return;
        }
        methodVisitor.visitInsn(87);
    }

    private void _setContext(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitVarInsn(25, context.var(com.umeng.analytics.pro.f.X));
        methodVisitor.visitMethodInsn(182, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        Label label = new Label();
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitJumpInsn(198, label);
        methodVisitor.visitVarInsn(25, context.var("childContext"));
        methodVisitor.visitVarInsn(25, context.var("instance"));
        methodVisitor.visitFieldInsn(181, ASMUtils.type(ParseContext.class), "object", "Ljava/lang/Object;");
        methodVisitor.visitLabel(label);
    }

    private void _setFlag(MethodVisitor methodVisitor, Context context, int i2) {
        String str = "_asm_flag_" + (i2 / 32);
        methodVisitor.visitVarInsn(21, context.var(str));
        methodVisitor.visitLdcInsn(Integer.valueOf(1 << i2));
        methodVisitor.visitInsn(128);
        methodVisitor.visitVarInsn(54, context.var(str));
    }

    private void defineVarLexer(Context context, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(25, 1);
        methodVisitor.visitFieldInsn(180, DefaultJSONParser, "lexer", ASMUtils.desc((Class<?>) JSONLexer.class));
        methodVisitor.visitTypeInsn(192, JSONLexerBase);
        methodVisitor.visitVarInsn(58, context.var("lexer"));
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig parserConfig, JavaBeanInfo javaBeanInfo) throws Exception {
        String str;
        Class<?> cls = javaBeanInfo.clazz;
        if (cls.isPrimitive()) {
            throw new IllegalArgumentException("not support type :" + cls.getName());
        }
        String str2 = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + OpenAccountUIConstants.UNDER_LINE + cls.getSimpleName();
        Package r4 = ASMDeserializerFactory.class.getPackage();
        if (r4 != null) {
            String name = r4.getName();
            String str3 = name.replace('.', org.apache.commons.io.IOUtils.DIR_SEPARATOR_UNIX) + "/" + str2;
            str = name + "." + str2;
            str2 = str3;
        } else {
            str = str2;
        }
        ClassWriter classWriter = new ClassWriter();
        classWriter.visit(49, 33, str2, ASMUtils.type(JavaBeanDeserializer.class), null);
        _init(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
        _createInstance(classWriter, new Context(str2, parserConfig, javaBeanInfo, 3));
        _deserialze(classWriter, new Context(str2, parserConfig, javaBeanInfo, 5));
        _deserialzeArrayMapping(classWriter, new Context(str2, parserConfig, javaBeanInfo, 4));
        byte[] byteArray = classWriter.toByteArray();
        return (ObjectDeserializer) this.classLoader.defineClassPublic(str, byteArray, 0, byteArray.length).getConstructor(ParserConfig.class, JavaBeanInfo.class).newInstance(parserConfig, javaBeanInfo);
    }

    private void _batchSet(Context context, MethodVisitor methodVisitor, boolean z2) {
        int length = context.fieldInfoList.length;
        for (int i2 = 0; i2 < length; i2++) {
            Label label = new Label();
            if (z2) {
                _isFlag(methodVisitor, context, i2, label);
            }
            _loadAndSet(context, methodVisitor, context.fieldInfoList[i2]);
            if (z2) {
                methodVisitor.visitLabel(label);
            }
        }
    }

    private void _createInstance(ClassWriter classWriter, Context context) {
        if (Modifier.isPublic(context.beanInfo.defaultConstructor.getModifiers())) {
            MethodWriter methodWriter = new MethodWriter(classWriter, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
            methodWriter.visitTypeInsn(187, ASMUtils.type(context.getInstClass()));
            methodWriter.visitInsn(89);
            methodWriter.visitMethodInsn(183, ASMUtils.type(context.getInstClass()), "<init>", "()V");
            methodWriter.visitInsn(176);
            methodWriter.visitMaxs(3, 3);
            methodWriter.visitEnd();
        }
    }
}
