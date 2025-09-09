package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ScriptNode;

/* loaded from: classes5.dex */
public class ClassCompiler {
    private CompilerEnvirons compilerEnv;
    private String mainMethodClassName;
    private Class<?> targetExtends;
    private Class<?>[] targetImplements;

    public ClassCompiler(CompilerEnvirons compilerEnvirons) {
        if (compilerEnvirons == null) {
            throw new IllegalArgumentException();
        }
        this.compilerEnv = compilerEnvirons;
        this.mainMethodClassName = "org.mozilla.javascript.optimizer.OptRuntime";
    }

    public Object[] compileToClassFiles(String str, String str2, int i2, String str3) {
        ScriptNode scriptNodeTransformTree = new IRFactory(this.compilerEnv).transformTree(new Parser(this.compilerEnv).parse(str, str2, i2));
        Class<?> targetExtends = getTargetExtends();
        Class<?>[] targetImplements = getTargetImplements();
        boolean z2 = targetImplements == null && targetExtends == null;
        String strMakeAuxiliaryClassName = z2 ? str3 : makeAuxiliaryClassName(str3, "1");
        Codegen codegen = new Codegen();
        codegen.setMainMethodClass(this.mainMethodClassName);
        byte[] bArrCompileToClassFile = codegen.compileToClassFile(this.compilerEnv, strMakeAuxiliaryClassName, scriptNodeTransformTree, scriptNodeTransformTree.getEncodedSource(), false);
        if (z2) {
            return new Object[]{strMakeAuxiliaryClassName, bArrCompileToClassFile};
        }
        int functionCount = scriptNodeTransformTree.getFunctionCount();
        ObjToIntMap objToIntMap = new ObjToIntMap(functionCount);
        for (int i3 = 0; i3 != functionCount; i3++) {
            FunctionNode functionNode = scriptNodeTransformTree.getFunctionNode(i3);
            String name = functionNode.getName();
            if (name != null && name.length() != 0) {
                objToIntMap.put(name, functionNode.getParamCount());
            }
        }
        if (targetExtends == null) {
            targetExtends = ScriptRuntime.ObjectClass;
        }
        return new Object[]{str3, JavaAdapter.createAdapterCode(objToIntMap, str3, targetExtends, targetImplements, strMakeAuxiliaryClassName), strMakeAuxiliaryClassName, bArrCompileToClassFile};
    }

    public CompilerEnvirons getCompilerEnv() {
        return this.compilerEnv;
    }

    public String getMainMethodClass() {
        return this.mainMethodClassName;
    }

    public Class<?> getTargetExtends() {
        return this.targetExtends;
    }

    public Class<?>[] getTargetImplements() {
        Class<?>[] clsArr = this.targetImplements;
        if (clsArr == null) {
            return null;
        }
        return (Class[]) clsArr.clone();
    }

    protected String makeAuxiliaryClassName(String str, String str2) {
        return str + str2;
    }

    public void setMainMethodClass(String str) {
        this.mainMethodClassName = str;
    }

    public void setTargetExtends(Class<?> cls) {
        this.targetExtends = cls;
    }

    public void setTargetImplements(Class<?>[] clsArr) {
        this.targetImplements = clsArr == null ? null : (Class[]) clsArr.clone();
    }
}
