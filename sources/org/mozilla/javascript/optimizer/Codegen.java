package org.mozilla.javascript.optimizer;

import androidx.core.app.NotificationCompat;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.umeng.analytics.pro.bc;
import java.util.HashMap;
import java.util.List;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Evaluator;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.ScriptNode;

/* loaded from: classes5.dex */
public class Codegen implements Evaluator {
    static final String DEFAULT_MAIN_METHOD_CLASS = "org.mozilla.javascript.optimizer.OptRuntime";
    static final String FUNCTION_CONSTRUCTOR_SIGNATURE = "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;I)V";
    static final String FUNCTION_INIT_SIGNATURE = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)V";
    static final String ID_FIELD_NAME = "_id";
    static final String REGEXP_INIT_METHOD_NAME = "_reInit";
    static final String REGEXP_INIT_METHOD_SIGNATURE = "(Lorg/mozilla/javascript/Context;)V";
    private static final String SUPER_CLASS_NAME = "org.mozilla.javascript.NativeFunction";
    private static final Object globalLock = new Object();
    private static int globalSerialClassCounter;
    private CompilerEnvirons compilerEnv;
    private ObjArray directCallTargets;
    private double[] itsConstantList;
    private int itsConstantListSize;
    String mainClassName;
    String mainClassSignature;
    private String mainMethodClass = DEFAULT_MAIN_METHOD_CLASS;
    private ObjToIntMap scriptOrFnIndexes;
    ScriptNode[] scriptOrFnNodes;

    private static void addDoubleWrap(ClassFileWriter classFileWriter) {
        classFileWriter.addInvoke(184, "org/mozilla/javascript/optimizer/OptRuntime", "wrapDouble", "(D)Ljava/lang/Double;");
    }

    static RuntimeException badTree() {
        throw new RuntimeException("Bad tree in codegen");
    }

    private static void collectScriptNodes_r(ScriptNode scriptNode, ObjArray objArray) {
        objArray.add(scriptNode);
        int functionCount = scriptNode.getFunctionCount();
        for (int i2 = 0; i2 != functionCount; i2++) {
            collectScriptNodes_r(scriptNode.getFunctionNode(i2), objArray);
        }
    }

    private Class<?> defineClass(Object obj, Object obj2) {
        Object[] objArr = (Object[]) obj;
        String str = (String) objArr[0];
        byte[] bArr = (byte[]) objArr[1];
        GeneratedClassLoader generatedClassLoaderCreateLoader = SecurityController.createLoader(getClass().getClassLoader(), obj2);
        try {
            Class<?> clsDefineClass = generatedClassLoaderCreateLoader.defineClass(str, bArr);
            generatedClassLoaderCreateLoader.linkClass(clsDefineClass);
            return clsDefineClass;
        } catch (IllegalArgumentException | SecurityException e2) {
            throw new RuntimeException("Malformed optimizer package " + e2);
        }
    }

    private void emitConstantDudeInitializers(ClassFileWriter classFileWriter) throws RuntimeException {
        int i2 = this.itsConstantListSize;
        if (i2 == 0) {
            return;
        }
        classFileWriter.startMethod("<clinit>", "()V", (short) 24);
        double[] dArr = this.itsConstantList;
        for (int i3 = 0; i3 != i2; i3++) {
            double d2 = dArr[i3];
            String str = "_k" + i3;
            String staticConstantWrapperType = getStaticConstantWrapperType(d2);
            classFileWriter.addField(str, staticConstantWrapperType, (short) 10);
            int i4 = (int) d2;
            if (i4 == d2) {
                classFileWriter.addPush(i4);
                classFileWriter.addInvoke(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            } else {
                classFileWriter.addPush(d2);
                addDoubleWrap(classFileWriter);
            }
            classFileWriter.add(179, this.mainClassName, str, staticConstantWrapperType);
        }
        classFileWriter.add(177);
        classFileWriter.stopMethod((short) 0);
    }

    private void emitDirectConstructor(ClassFileWriter classFileWriter, OptFunctionNode optFunctionNode) throws RuntimeException {
        classFileWriter.startMethod(getDirectCtorName(optFunctionNode.fnode), getBodyMethodSignature(optFunctionNode.fnode), (short) 10);
        int paramCount = optFunctionNode.fnode.getParamCount();
        int i2 = paramCount * 3;
        int i3 = i2 + 4;
        int i4 = i2 + 5;
        classFileWriter.addALoad(0);
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addInvoke(182, "org/mozilla/javascript/BaseFunction", "createObject", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Lorg/mozilla/javascript/Scriptable;");
        classFileWriter.addAStore(i4);
        classFileWriter.addALoad(0);
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addALoad(i4);
        for (int i5 = 0; i5 < paramCount; i5++) {
            int i6 = i5 * 3;
            classFileWriter.addALoad(i6 + 4);
            classFileWriter.addDLoad(i6 + 5);
        }
        classFileWriter.addALoad(i3);
        classFileWriter.addInvoke(184, this.mainClassName, getBodyMethodName(optFunctionNode.fnode), getBodyMethodSignature(optFunctionNode.fnode));
        int iAcquireLabel = classFileWriter.acquireLabel();
        classFileWriter.add(89);
        classFileWriter.add(193, "org/mozilla/javascript/Scriptable");
        classFileWriter.add(153, iAcquireLabel);
        classFileWriter.add(192, "org/mozilla/javascript/Scriptable");
        classFileWriter.add(176);
        classFileWriter.markLabel(iAcquireLabel);
        classFileWriter.addALoad(i4);
        classFileWriter.add(176);
        classFileWriter.stopMethod((short) (i2 + 6));
    }

    private void emitRegExpInit(ClassFileWriter classFileWriter) throws RuntimeException {
        int i2 = 0;
        int i3 = 0;
        int regexpCount = 0;
        while (true) {
            ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
            if (i3 == scriptNodeArr.length) {
                break;
            }
            regexpCount += scriptNodeArr[i3].getRegexpCount();
            i3++;
        }
        if (regexpCount == 0) {
            return;
        }
        short s2 = 10;
        classFileWriter.startMethod(REGEXP_INIT_METHOD_NAME, REGEXP_INIT_METHOD_SIGNATURE, (short) 10);
        classFileWriter.addField("_reInitDone", "Z", (short) 74);
        classFileWriter.add(178, this.mainClassName, "_reInitDone", "Z");
        int iAcquireLabel = classFileWriter.acquireLabel();
        classFileWriter.add(153, iAcquireLabel);
        classFileWriter.add(177);
        classFileWriter.markLabel(iAcquireLabel);
        classFileWriter.addALoad(0);
        classFileWriter.addInvoke(184, "org/mozilla/javascript/ScriptRuntime", "checkRegExpProxy", "(Lorg/mozilla/javascript/Context;)Lorg/mozilla/javascript/RegExpProxy;");
        classFileWriter.addAStore(1);
        int i4 = 0;
        while (true) {
            ScriptNode[] scriptNodeArr2 = this.scriptOrFnNodes;
            if (i4 == scriptNodeArr2.length) {
                classFileWriter.addPush(1);
                classFileWriter.add(179, this.mainClassName, "_reInitDone", "Z");
                classFileWriter.add(177);
                classFileWriter.stopMethod((short) 2);
                return;
            }
            ScriptNode scriptNode = scriptNodeArr2[i4];
            int regexpCount2 = scriptNode.getRegexpCount();
            int i5 = i2;
            while (i5 != regexpCount2) {
                String compiledRegexpName = getCompiledRegexpName(scriptNode, i5);
                String regexpString = scriptNode.getRegexpString(i5);
                String regexpFlags = scriptNode.getRegexpFlags(i5);
                classFileWriter.addField(compiledRegexpName, "Ljava/lang/Object;", s2);
                classFileWriter.addALoad(1);
                classFileWriter.addALoad(i2);
                classFileWriter.addPush(regexpString);
                if (regexpFlags == null) {
                    classFileWriter.add(1);
                } else {
                    classFileWriter.addPush(regexpFlags);
                }
                classFileWriter.addInvoke(185, "org/mozilla/javascript/RegExpProxy", "compileRegExp", "(Lorg/mozilla/javascript/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
                classFileWriter.add(179, this.mainClassName, compiledRegexpName, "Ljava/lang/Object;");
                i5++;
                i2 = 0;
                s2 = 10;
            }
            i4++;
            i2 = 0;
            s2 = 10;
        }
    }

    private void generateCallMethod(ClassFileWriter classFileWriter, boolean z2) throws RuntimeException {
        int iAddTableSwitch;
        int paramCount;
        classFileWriter.startMethod(NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", (short) 17);
        int iAcquireLabel = classFileWriter.acquireLabel();
        classFileWriter.addALoad(1);
        classFileWriter.addInvoke(184, "org/mozilla/javascript/ScriptRuntime", "hasTopCall", "(Lorg/mozilla/javascript/Context;)Z");
        classFileWriter.add(154, iAcquireLabel);
        int i2 = 0;
        classFileWriter.addALoad(0);
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addALoad(3);
        classFileWriter.addALoad(4);
        classFileWriter.addPush(z2);
        classFileWriter.addInvoke(184, "org/mozilla/javascript/ScriptRuntime", "doTopCall", "(Lorg/mozilla/javascript/Callable;Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;Z)Ljava/lang/Object;");
        classFileWriter.add(176);
        classFileWriter.markLabel(iAcquireLabel);
        classFileWriter.addALoad(0);
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addALoad(3);
        classFileWriter.addALoad(4);
        int length = this.scriptOrFnNodes.length;
        boolean z3 = 2 <= length;
        if (z3) {
            classFileWriter.addLoadThis();
            classFileWriter.add(180, classFileWriter.getClassName(), "_id", "I");
            iAddTableSwitch = classFileWriter.addTableSwitch(1, length - 1);
        } else {
            iAddTableSwitch = 0;
        }
        int i3 = 0;
        short stackTop = 0;
        while (i3 != length) {
            ScriptNode scriptNode = this.scriptOrFnNodes[i3];
            if (z3) {
                if (i3 == 0) {
                    classFileWriter.markTableSwitchDefault(iAddTableSwitch);
                    stackTop = classFileWriter.getStackTop();
                } else {
                    classFileWriter.markTableSwitchCase(iAddTableSwitch, i3 - 1, stackTop);
                }
            }
            if (scriptNode.getType() == 110) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
                if (optFunctionNode.isTargetOfDirectCall() && (paramCount = optFunctionNode.fnode.getParamCount()) != 0) {
                    for (int i4 = i2; i4 != paramCount; i4++) {
                        classFileWriter.add(190);
                        classFileWriter.addPush(i4);
                        int iAcquireLabel2 = classFileWriter.acquireLabel();
                        int iAcquireLabel3 = classFileWriter.acquireLabel();
                        classFileWriter.add(164, iAcquireLabel2);
                        classFileWriter.addALoad(4);
                        classFileWriter.addPush(i4);
                        classFileWriter.add(50);
                        classFileWriter.add(167, iAcquireLabel3);
                        classFileWriter.markLabel(iAcquireLabel2);
                        pushUndefined(classFileWriter);
                        classFileWriter.markLabel(iAcquireLabel3);
                        classFileWriter.adjustStackTop(-1);
                        classFileWriter.addPush(0.0d);
                        classFileWriter.addALoad(4);
                    }
                }
            }
            classFileWriter.addInvoke(184, this.mainClassName, getBodyMethodName(scriptNode), getBodyMethodSignature(scriptNode));
            classFileWriter.add(176);
            i3++;
            i2 = 0;
        }
        classFileWriter.stopMethod((short) 5);
    }

    private byte[] generateCode(String str) throws RuntimeException {
        boolean z2 = true;
        boolean z3 = this.scriptOrFnNodes[0].getType() == 137;
        ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
        if (scriptNodeArr.length <= 1 && z3) {
            z2 = false;
        }
        boolean zIsInStrictMode = scriptNodeArr[0].isInStrictMode();
        ClassFileWriter classFileWriter = new ClassFileWriter(this.mainClassName, SUPER_CLASS_NAME, this.compilerEnv.isGenerateDebugInfo() ? this.scriptOrFnNodes[0].getSourceName() : null);
        classFileWriter.addField("_id", "I", (short) 2);
        if (z2) {
            generateFunctionConstructor(classFileWriter);
        }
        if (z3) {
            classFileWriter.addInterface("org/mozilla/javascript/Script");
            generateScriptCtor(classFileWriter);
            generateMain(classFileWriter);
            generateExecute(classFileWriter);
        }
        generateCallMethod(classFileWriter, zIsInStrictMode);
        generateResumeGenerator(classFileWriter);
        generateNativeFunctionOverrides(classFileWriter, str);
        int length = this.scriptOrFnNodes.length;
        for (int i2 = 0; i2 != length; i2++) {
            ScriptNode scriptNode = this.scriptOrFnNodes[i2];
            BodyCodegen bodyCodegen = new BodyCodegen();
            bodyCodegen.cfw = classFileWriter;
            bodyCodegen.codegen = this;
            bodyCodegen.compilerEnv = this.compilerEnv;
            bodyCodegen.scriptOrFn = scriptNode;
            bodyCodegen.scriptOrFnIndex = i2;
            try {
                bodyCodegen.generateBodyCode();
                if (scriptNode.getType() == 110) {
                    OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
                    generateFunctionInit(classFileWriter, optFunctionNode);
                    if (optFunctionNode.isTargetOfDirectCall()) {
                        emitDirectConstructor(classFileWriter, optFunctionNode);
                    }
                }
            } catch (ClassFileWriter.ClassFileFormatException e2) {
                throw reportClassFileFormatException(scriptNode, e2.getMessage());
            }
        }
        emitRegExpInit(classFileWriter);
        emitConstantDudeInitializers(classFileWriter);
        return classFileWriter.toByteArray();
    }

    private void generateExecute(ClassFileWriter classFileWriter) throws RuntimeException {
        classFileWriter.startMethod("exec", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;", (short) 17);
        classFileWriter.addLoadThis();
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.add(89);
        classFileWriter.add(1);
        classFileWriter.addInvoke(182, classFileWriter.getClassName(), NotificationCompat.CATEGORY_CALL, "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;");
        classFileWriter.add(176);
        classFileWriter.stopMethod((short) 3);
    }

    private void generateFunctionConstructor(ClassFileWriter classFileWriter) throws RuntimeException {
        int iAddTableSwitch;
        classFileWriter.startMethod("<init>", FUNCTION_CONSTRUCTOR_SIGNATURE, (short) 1);
        short stackTop = 0;
        classFileWriter.addALoad(0);
        classFileWriter.addInvoke(183, SUPER_CLASS_NAME, "<init>", "()V");
        classFileWriter.addLoadThis();
        classFileWriter.addILoad(3);
        classFileWriter.add(181, classFileWriter.getClassName(), "_id", "I");
        classFileWriter.addLoadThis();
        classFileWriter.addALoad(2);
        classFileWriter.addALoad(1);
        int i2 = this.scriptOrFnNodes[0].getType() == 137 ? 1 : 0;
        int length = this.scriptOrFnNodes.length;
        if (i2 == length) {
            throw badTree();
        }
        boolean z2 = 2 <= length - i2;
        if (z2) {
            classFileWriter.addILoad(3);
            iAddTableSwitch = classFileWriter.addTableSwitch(i2 + 1, length - 1);
        } else {
            iAddTableSwitch = 0;
        }
        for (int i3 = i2; i3 != length; i3++) {
            if (z2) {
                if (i3 == i2) {
                    classFileWriter.markTableSwitchDefault(iAddTableSwitch);
                    stackTop = classFileWriter.getStackTop();
                } else {
                    classFileWriter.markTableSwitchCase(iAddTableSwitch, (i3 - 1) - i2, stackTop);
                }
            }
            classFileWriter.addInvoke(183, this.mainClassName, getFunctionInitMethodName(OptFunctionNode.get(this.scriptOrFnNodes[i3])), FUNCTION_INIT_SIGNATURE);
            classFileWriter.add(177);
        }
        classFileWriter.stopMethod((short) 4);
    }

    private void generateFunctionInit(ClassFileWriter classFileWriter, OptFunctionNode optFunctionNode) throws RuntimeException {
        classFileWriter.startMethod(getFunctionInitMethodName(optFunctionNode), FUNCTION_INIT_SIGNATURE, (short) 18);
        classFileWriter.addLoadThis();
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addInvoke(182, "org/mozilla/javascript/NativeFunction", "initScriptFunction", FUNCTION_INIT_SIGNATURE);
        if (optFunctionNode.fnode.getRegexpCount() != 0) {
            classFileWriter.addALoad(1);
            classFileWriter.addInvoke(184, this.mainClassName, REGEXP_INIT_METHOD_NAME, REGEXP_INIT_METHOD_SIGNATURE);
        }
        classFileWriter.add(177);
        classFileWriter.stopMethod((short) 3);
    }

    private void generateMain(ClassFileWriter classFileWriter) throws RuntimeException {
        classFileWriter.startMethod("main", "([Ljava/lang/String;)V", (short) 9);
        classFileWriter.add(187, classFileWriter.getClassName());
        classFileWriter.add(89);
        classFileWriter.addInvoke(183, classFileWriter.getClassName(), "<init>", "()V");
        classFileWriter.add(42);
        classFileWriter.addInvoke(184, this.mainMethodClass, "main", "(Lorg/mozilla/javascript/Script;[Ljava/lang/String;)V");
        classFileWriter.add(177);
        classFileWriter.stopMethod((short) 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void generateNativeFunctionOverrides(org.mozilla.classfile.ClassFileWriter r19, java.lang.String r20) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 464
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.optimizer.Codegen.generateNativeFunctionOverrides(org.mozilla.classfile.ClassFileWriter, java.lang.String):void");
    }

    private void generateResumeGenerator(ClassFileWriter classFileWriter) throws RuntimeException {
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        while (true) {
            ScriptNode[] scriptNodeArr = this.scriptOrFnNodes;
            if (i3 >= scriptNodeArr.length) {
                break;
            }
            if (isGenerator(scriptNodeArr[i3])) {
                z2 = true;
            }
            i3++;
        }
        if (!z2) {
            return;
        }
        classFileWriter.startMethod("resumeGenerator", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;ILjava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", (short) 17);
        classFileWriter.addALoad(0);
        classFileWriter.addALoad(1);
        classFileWriter.addALoad(2);
        classFileWriter.addALoad(4);
        classFileWriter.addALoad(5);
        classFileWriter.addILoad(3);
        classFileWriter.addLoadThis();
        classFileWriter.add(180, classFileWriter.getClassName(), "_id", "I");
        int iAddTableSwitch = classFileWriter.addTableSwitch(0, this.scriptOrFnNodes.length - 1);
        classFileWriter.markTableSwitchDefault(iAddTableSwitch);
        int iAcquireLabel = classFileWriter.acquireLabel();
        while (true) {
            ScriptNode[] scriptNodeArr2 = this.scriptOrFnNodes;
            if (i2 >= scriptNodeArr2.length) {
                classFileWriter.markLabel(iAcquireLabel);
                pushUndefined(classFileWriter);
                classFileWriter.add(176);
                classFileWriter.stopMethod((short) 6);
                return;
            }
            ScriptNode scriptNode = scriptNodeArr2[i2];
            classFileWriter.markTableSwitchCase(iAddTableSwitch, i2, 6);
            if (isGenerator(scriptNode)) {
                String str = "(" + this.mainClassSignature + "Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;I)Ljava/lang/Object;";
                classFileWriter.addInvoke(184, this.mainClassName, getBodyMethodName(scriptNode) + "_gen", str);
                classFileWriter.add(176);
            } else {
                classFileWriter.add(167, iAcquireLabel);
            }
            i2++;
        }
    }

    private void generateScriptCtor(ClassFileWriter classFileWriter) throws RuntimeException {
        classFileWriter.startMethod("<init>", "()V", (short) 1);
        classFileWriter.addLoadThis();
        classFileWriter.addInvoke(183, SUPER_CLASS_NAME, "<init>", "()V");
        classFileWriter.addLoadThis();
        classFileWriter.addPush(0);
        classFileWriter.add(181, classFileWriter.getClassName(), "_id", "I");
        classFileWriter.add(177);
        classFileWriter.stopMethod((short) 1);
    }

    private static String getStaticConstantWrapperType(double d2) {
        return ((double) ((int) d2)) == d2 ? "Ljava/lang/Integer;" : "Ljava/lang/Double;";
    }

    private static void initOptFunctions_r(ScriptNode scriptNode) {
        int functionCount = scriptNode.getFunctionCount();
        for (int i2 = 0; i2 != functionCount; i2++) {
            FunctionNode functionNode = scriptNode.getFunctionNode(i2);
            new OptFunctionNode(functionNode);
            initOptFunctions_r(functionNode);
        }
    }

    private void initScriptNodesData(ScriptNode scriptNode) {
        ObjArray objArray = new ObjArray();
        collectScriptNodes_r(scriptNode, objArray);
        int size = objArray.size();
        ScriptNode[] scriptNodeArr = new ScriptNode[size];
        this.scriptOrFnNodes = scriptNodeArr;
        objArray.toArray(scriptNodeArr);
        this.scriptOrFnIndexes = new ObjToIntMap(size);
        for (int i2 = 0; i2 != size; i2++) {
            this.scriptOrFnIndexes.put(this.scriptOrFnNodes[i2], i2);
        }
    }

    static boolean isGenerator(ScriptNode scriptNode) {
        return scriptNode.getType() == 110 && ((FunctionNode) scriptNode).isGenerator();
    }

    static void pushUndefined(ClassFileWriter classFileWriter) {
        classFileWriter.add(178, "org/mozilla/javascript/Undefined", "instance", "Ljava/lang/Object;");
    }

    private RuntimeException reportClassFileFormatException(ScriptNode scriptNode, String str) {
        return Context.reportRuntimeError(scriptNode instanceof FunctionNode ? ScriptRuntime.getMessage2("msg.while.compiling.fn", ((FunctionNode) scriptNode).getFunctionName(), str) : ScriptRuntime.getMessage1("msg.while.compiling.script", str), scriptNode.getSourceName(), scriptNode.getLineno(), null, 0);
    }

    private void transform(ScriptNode scriptNode) {
        initOptFunctions_r(scriptNode);
        int optimizationLevel = this.compilerEnv.getOptimizationLevel();
        HashMap map = null;
        if (optimizationLevel > 0 && scriptNode.getType() == 137) {
            int functionCount = scriptNode.getFunctionCount();
            for (int i2 = 0; i2 != functionCount; i2++) {
                OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode, i2);
                if (optFunctionNode.fnode.getFunctionType() == 1) {
                    String name = optFunctionNode.fnode.getName();
                    if (name.length() != 0) {
                        if (map == null) {
                            map = new HashMap();
                        }
                        map.put(name, optFunctionNode);
                    }
                }
            }
        }
        if (map != null) {
            this.directCallTargets = new ObjArray();
        }
        new OptTransformer(map, this.directCallTargets).transform(scriptNode, this.compilerEnv);
        if (optimizationLevel > 0) {
            new Optimizer().optimize(scriptNode);
        }
    }

    @Override // org.mozilla.javascript.Evaluator
    public void captureStackInfo(RhinoException rhinoException) {
        throw new UnsupportedOperationException();
    }

    String cleanName(ScriptNode scriptNode) {
        if (!(scriptNode instanceof FunctionNode)) {
            return "script";
        }
        Name functionName = ((FunctionNode) scriptNode).getFunctionName();
        return functionName == null ? "anonymous" : functionName.getIdentifier();
    }

    @Override // org.mozilla.javascript.Evaluator
    public Object compile(CompilerEnvirons compilerEnvirons, ScriptNode scriptNode, String str, boolean z2) {
        int i2;
        synchronized (globalLock) {
            i2 = globalSerialClassCounter + 1;
            globalSerialClassCounter = i2;
        }
        String strReplaceAll = bc.aL;
        if (scriptNode.getSourceName().length() > 0) {
            strReplaceAll = scriptNode.getSourceName().replaceAll("\\W", OpenAccountUIConstants.UNDER_LINE);
            if (!Character.isJavaIdentifierStart(strReplaceAll.charAt(0))) {
                strReplaceAll = OpenAccountUIConstants.UNDER_LINE + strReplaceAll;
            }
        }
        String str2 = "org.mozilla.javascript.gen." + strReplaceAll + OpenAccountUIConstants.UNDER_LINE + i2;
        return new Object[]{str2, compileToClassFile(compilerEnvirons, str2, scriptNode, str, z2)};
    }

    public byte[] compileToClassFile(CompilerEnvirons compilerEnvirons, String str, ScriptNode scriptNode, String str2, boolean z2) {
        this.compilerEnv = compilerEnvirons;
        transform(scriptNode);
        if (z2) {
            scriptNode = scriptNode.getFunctionNode(0);
        }
        initScriptNodesData(scriptNode);
        this.mainClassName = str;
        this.mainClassSignature = ClassFileWriter.classNameToSignature(str);
        try {
            return generateCode(str2);
        } catch (ClassFileWriter.ClassFileFormatException e2) {
            throw reportClassFileFormatException(scriptNode, e2.getMessage());
        }
    }

    @Override // org.mozilla.javascript.Evaluator
    public Function createFunctionObject(Context context, Scriptable scriptable, Object obj, Object obj2) {
        try {
            return (NativeFunction) defineClass(obj, obj2).getConstructors()[0].newInstance(scriptable, context, 0);
        } catch (Exception e2) {
            throw new RuntimeException("Unable to instantiate compiled class:" + e2.toString());
        }
    }

    @Override // org.mozilla.javascript.Evaluator
    public Script createScriptObject(Object obj, Object obj2) {
        try {
            return (Script) defineClass(obj, obj2).newInstance();
        } catch (Exception e2) {
            throw new RuntimeException("Unable to instantiate compiled class:" + e2.toString());
        }
    }

    String getBodyMethodName(ScriptNode scriptNode) {
        return "_c_" + cleanName(scriptNode) + OpenAccountUIConstants.UNDER_LINE + getIndex(scriptNode);
    }

    String getBodyMethodSignature(ScriptNode scriptNode) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(this.mainClassSignature);
        sb.append("Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;");
        if (scriptNode.getType() == 110) {
            OptFunctionNode optFunctionNode = OptFunctionNode.get(scriptNode);
            if (optFunctionNode.isTargetOfDirectCall()) {
                int paramCount = optFunctionNode.fnode.getParamCount();
                for (int i2 = 0; i2 != paramCount; i2++) {
                    sb.append("Ljava/lang/Object;D");
                }
            }
        }
        sb.append("[Ljava/lang/Object;)Ljava/lang/Object;");
        return sb.toString();
    }

    String getCompiledRegexpName(ScriptNode scriptNode, int i2) {
        return "_re" + getIndex(scriptNode) + OpenAccountUIConstants.UNDER_LINE + i2;
    }

    String getDirectCtorName(ScriptNode scriptNode) {
        return "_n" + getIndex(scriptNode);
    }

    String getFunctionInitMethodName(OptFunctionNode optFunctionNode) {
        return "_i" + getIndex(optFunctionNode.fnode);
    }

    int getIndex(ScriptNode scriptNode) {
        return this.scriptOrFnIndexes.getExisting(scriptNode);
    }

    @Override // org.mozilla.javascript.Evaluator
    public String getPatchedStack(RhinoException rhinoException, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.mozilla.javascript.Evaluator
    public List<String> getScriptStack(RhinoException rhinoException) {
        throw new UnsupportedOperationException();
    }

    @Override // org.mozilla.javascript.Evaluator
    public String getSourcePositionFromStack(Context context, int[] iArr) {
        throw new UnsupportedOperationException();
    }

    void pushNumberAsObject(ClassFileWriter classFileWriter, double d2) throws RuntimeException {
        if (d2 == 0.0d) {
            if (1.0d / d2 > 0.0d) {
                classFileWriter.add(178, "org/mozilla/javascript/optimizer/OptRuntime", "zeroObj", "Ljava/lang/Double;");
                return;
            } else {
                classFileWriter.addPush(d2);
                addDoubleWrap(classFileWriter);
                return;
            }
        }
        if (d2 == 1.0d) {
            classFileWriter.add(178, "org/mozilla/javascript/optimizer/OptRuntime", "oneObj", "Ljava/lang/Double;");
            return;
        }
        if (d2 == -1.0d) {
            classFileWriter.add(178, "org/mozilla/javascript/optimizer/OptRuntime", "minusOneObj", "Ljava/lang/Double;");
            return;
        }
        if (d2 != d2) {
            classFileWriter.add(178, "org/mozilla/javascript/ScriptRuntime", "NaNobj", "Ljava/lang/Double;");
            return;
        }
        int i2 = this.itsConstantListSize;
        if (i2 >= 2000) {
            classFileWriter.addPush(d2);
            addDoubleWrap(classFileWriter);
            return;
        }
        int i3 = 0;
        if (i2 == 0) {
            this.itsConstantList = new double[64];
        } else {
            double[] dArr = this.itsConstantList;
            int i4 = 0;
            while (i4 != i2 && dArr[i4] != d2) {
                i4++;
            }
            if (i2 == dArr.length) {
                double[] dArr2 = new double[i2 * 2];
                System.arraycopy(this.itsConstantList, 0, dArr2, 0, i2);
                this.itsConstantList = dArr2;
            }
            i3 = i4;
        }
        if (i3 == i2) {
            this.itsConstantList[i2] = d2;
            this.itsConstantListSize = i2 + 1;
        }
        classFileWriter.add(178, this.mainClassName, "_k" + i3, getStaticConstantWrapperType(d2));
    }

    @Override // org.mozilla.javascript.Evaluator
    public void setEvalScriptFlag(Script script) {
        throw new UnsupportedOperationException();
    }

    public void setMainMethodClass(String str) {
        this.mainMethodClass = str;
    }
}
