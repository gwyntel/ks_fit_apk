package org.mozilla.javascript.tools.shell;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.tekartik.sqflite.Constant;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;
import org.mozilla.javascript.commonjs.module.ModuleScope;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.tools.SourceReader;
import org.mozilla.javascript.tools.ToolErrorReporter;

/* loaded from: classes5.dex */
public class Main {
    private static final int EXITCODE_FILE_NOT_FOUND = 4;
    private static final int EXITCODE_RUNTIME_ERROR = 3;
    protected static ToolErrorReporter errorReporter;
    static String mainModule;
    static List<String> modulePath;
    static Require require;
    private static SecurityProxy securityImpl;
    public static ShellContextFactory shellContextFactory = new ShellContextFactory();
    public static Global global = new Global();
    protected static int exitCode = 0;
    static boolean processStdin = true;
    static List<String> fileList = new ArrayList();
    static boolean sandboxed = false;
    static boolean useRequire = false;
    private static final ScriptCache scriptCache = new ScriptCache(32);

    private static class IProxy implements ContextAction, QuitAction {
        private static final int EVAL_INLINE_SCRIPT = 2;
        private static final int PROCESS_FILES = 1;
        private static final int SYSTEM_EXIT = 3;
        String[] args;
        String scriptText;
        private int type;

        IProxy(int i2) {
            this.type = i2;
        }

        @Override // org.mozilla.javascript.tools.shell.QuitAction
        public void quit(Context context, int i2) {
            if (this.type != 3) {
                throw Kit.codeBug();
            }
            System.exit(i2);
        }

        @Override // org.mozilla.javascript.ContextAction
        public Object run(Context context) {
            if (Main.useRequire) {
                Main.require = Main.global.installRequire(context, Main.modulePath, Main.sandboxed);
            }
            int i2 = this.type;
            if (i2 == 1) {
                Main.processFiles(context, this.args);
                return null;
            }
            if (i2 != 2) {
                throw Kit.codeBug();
            }
            Main.evalInlineScript(context, this.scriptText);
            return null;
        }
    }

    static class ScriptCache extends LinkedHashMap<String, ScriptReference> {
        int capacity;
        ReferenceQueue<Script> queue;

        ScriptCache(int i2) {
            super(i2 + 1, 2.0f, true);
            this.capacity = i2;
            this.queue = new ReferenceQueue<>();
        }

        ScriptReference get(String str, byte[] bArr) {
            while (true) {
                ScriptReference scriptReference = (ScriptReference) this.queue.poll();
                if (scriptReference == null) {
                    break;
                }
                remove(scriptReference.path);
            }
            ScriptReference scriptReference2 = get(str);
            if (scriptReference2 == null || Arrays.equals(bArr, scriptReference2.digest)) {
                return scriptReference2;
            }
            remove(scriptReference2.path);
            return null;
        }

        void put(String str, byte[] bArr, Script script) {
            put(str, new ScriptReference(str, bArr, script, this.queue));
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, ScriptReference> entry) {
            return size() > this.capacity;
        }
    }

    static class ScriptReference extends SoftReference<Script> {
        byte[] digest;
        String path;

        ScriptReference(String str, byte[] bArr, Script script, ReferenceQueue<Script> referenceQueue) {
            super(script, referenceQueue);
            this.path = str;
            this.digest = bArr;
        }
    }

    static {
        global.initQuitAction(new IProxy(3));
    }

    static void evalInlineScript(Context context, String str) {
        try {
            Script scriptCompileString = context.compileString(str, "<command>", 1, null);
            if (scriptCompileString != null) {
                scriptCompileString.exec(context, getShellScope());
            }
        } catch (VirtualMachineError e2) {
            e2.printStackTrace();
            Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e2.toString()));
            exitCode = 3;
        } catch (RhinoException e3) {
            ToolErrorReporter.reportException(context.getErrorReporter(), e3);
            exitCode = 3;
        }
    }

    public static int exec(String[] strArr) throws NumberFormatException {
        ToolErrorReporter toolErrorReporter = new ToolErrorReporter(false, global.getErr());
        errorReporter = toolErrorReporter;
        shellContextFactory.setErrorReporter(toolErrorReporter);
        String[] strArrProcessOptions = processOptions(strArr);
        int i2 = exitCode;
        if (i2 > 0) {
            return i2;
        }
        if (processStdin) {
            fileList.add(null);
        }
        Global global2 = global;
        if (!global2.initialized) {
            global2.init(shellContextFactory);
        }
        IProxy iProxy = new IProxy(1);
        iProxy.args = strArrProcessOptions;
        shellContextFactory.call(iProxy);
        return exitCode;
    }

    private static byte[] getDigest(Object obj) throws UnsupportedEncodingException {
        byte[] bytes;
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            try {
                bytes = ((String) obj).getBytes("UTF-8");
            } catch (UnsupportedEncodingException unused) {
                bytes = ((String) obj).getBytes();
            }
        } else {
            bytes = (byte[]) obj;
        }
        try {
            return MessageDigest.getInstance(Utils.MD5).digest(bytes);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static PrintStream getErr() {
        return getGlobal().getErr();
    }

    public static Global getGlobal() {
        return global;
    }

    public static InputStream getIn() {
        return getGlobal().getIn();
    }

    public static PrintStream getOut() {
        return getGlobal().getOut();
    }

    static Scriptable getScope(String str) {
        URI uri;
        if (!useRequire) {
            return global;
        }
        if (str == null) {
            uri = new File(System.getProperty("user.dir")).toURI();
        } else if (SourceReader.toUrl(str) != null) {
            try {
                uri = new URI(str);
            } catch (URISyntaxException unused) {
                uri = new File(str).toURI();
            }
        } else {
            uri = new File(str).toURI();
        }
        return new ModuleScope(global, uri, null);
    }

    static Scriptable getShellScope() {
        return getScope(null);
    }

    private static void initJavaPolicySecuritySupport() {
        try {
            SecurityProxy securityProxy = (SecurityProxy) JavaPolicySecurity.class.newInstance();
            securityImpl = securityProxy;
            SecurityController.initGlobal(securityProxy);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | LinkageError e2) {
            throw Kit.initCause(new IllegalStateException("Can not load security support: " + e2), e2);
        }
    }

    private static Script loadCompiledScript(Context context, String str, byte[] bArr, Object obj) throws FileNotFoundException {
        if (bArr == null) {
            throw new FileNotFoundException(str);
        }
        int iLastIndexOf = str.lastIndexOf(47);
        int i2 = iLastIndexOf < 0 ? 0 : iLastIndexOf + 1;
        int iLastIndexOf2 = str.lastIndexOf(46);
        if (iLastIndexOf2 < i2) {
            iLastIndexOf2 = str.length();
        }
        String strSubstring = str.substring(i2, iLastIndexOf2);
        try {
            GeneratedClassLoader generatedClassLoaderCreateLoader = SecurityController.createLoader(context.getApplicationClassLoader(), obj);
            Class<?> clsDefineClass = generatedClassLoaderCreateLoader.defineClass(strSubstring, bArr);
            generatedClassLoaderCreateLoader.linkClass(clsDefineClass);
            if (Script.class.isAssignableFrom(clsDefineClass)) {
                return (Script) clsDefineClass.newInstance();
            }
            throw Context.reportRuntimeError("msg.must.implement.Script");
        } catch (IllegalAccessException e2) {
            Context.reportError(e2.toString());
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            Context.reportError(e3.toString());
            throw new RuntimeException(e3);
        }
    }

    public static void main(String[] strArr) throws NumberFormatException {
        try {
            if (Boolean.getBoolean("rhino.use_java_policy_security")) {
                initJavaPolicySecuritySupport();
            }
        } catch (SecurityException e2) {
            e2.printStackTrace(System.err);
        }
        int iExec = exec(strArr);
        if (iExec != 0) {
            System.exit(iExec);
        }
    }

    public static void processFile(Context context, Scriptable scriptable, String str) throws IOException {
        SecurityProxy securityProxy = securityImpl;
        if (securityProxy == null) {
            processFileSecure(context, scriptable, str, null);
        } else {
            securityProxy.callProcessFileSecure(context, scriptable, str);
        }
    }

    public static void processFileNoThrow(Context context, Scriptable scriptable, String str) {
        try {
            processFile(context, scriptable, str);
        } catch (IOException e2) {
            Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.read.source", str, e2.getMessage()));
            exitCode = 4;
        } catch (VirtualMachineError e3) {
            e3.printStackTrace();
            Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e3.toString()));
            exitCode = 3;
        } catch (RhinoException e4) {
            ToolErrorReporter.reportException(context.getErrorReporter(), e4);
            exitCode = 3;
        }
    }

    static void processFileSecure(Context context, Scriptable scriptable, String str, Object obj) throws IOException {
        Script scriptCompileString;
        boolean zEndsWith = str.endsWith(".class");
        Object fileOrUrl = readFileOrUrl(str, !zEndsWith);
        byte[] digest = getDigest(fileOrUrl);
        String str2 = str + OpenAccountUIConstants.UNDER_LINE + context.getOptimizationLevel();
        ScriptReference scriptReference = scriptCache.get(str2, digest);
        Script script = scriptReference != null ? scriptReference.get() : null;
        if (script == null) {
            if (zEndsWith) {
                scriptCompileString = loadCompiledScript(context, str, (byte[]) fileOrUrl, obj);
            } else {
                String strSubstring = (String) fileOrUrl;
                if (strSubstring.length() > 0 && strSubstring.charAt(0) == '#') {
                    for (int i2 = 1; i2 != strSubstring.length(); i2++) {
                        char cCharAt = strSubstring.charAt(i2);
                        if (cCharAt == '\n' || cCharAt == '\r') {
                            strSubstring = strSubstring.substring(i2);
                            break;
                        }
                    }
                }
                scriptCompileString = context.compileString(strSubstring, str, 1, obj);
            }
            script = scriptCompileString;
            scriptCache.put(str2, digest, script);
        }
        if (script != null) {
            script.exec(context, scriptable);
        }
    }

    static void processFiles(Context context, String[] strArr) {
        Object[] objArr = new Object[strArr.length];
        System.arraycopy(strArr, 0, objArr, 0, strArr.length);
        global.defineProperty(Constant.PARAM_SQL_ARGUMENTS, context.newArray(global, objArr), 2);
        for (String str : fileList) {
            try {
                processSource(context, str);
            } catch (IOException e2) {
                Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.read.source", str, e2.getMessage()));
                exitCode = 4;
            } catch (VirtualMachineError e3) {
                e3.printStackTrace();
                Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e3.toString()));
                exitCode = 3;
            } catch (RhinoException e4) {
                ToolErrorReporter.reportException(context.getErrorReporter(), e4);
                exitCode = 3;
            }
        }
    }

    public static String[] processOptions(String[] strArr) throws NumberFormatException {
        int i2 = 0;
        while (i2 != strArr.length) {
            String str = strArr[i2];
            if (!str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
                processStdin = false;
                fileList.add(str);
                mainModule = str;
                String[] strArr2 = new String[(strArr.length - i2) - 1];
                System.arraycopy(strArr, i2 + 1, strArr2, 0, (strArr.length - i2) - 1);
                return strArr2;
            }
            if (str.equals("-version")) {
                i2++;
                if (i2 != strArr.length) {
                    try {
                        int i3 = Integer.parseInt(strArr[i2]);
                        if (Context.isValidLanguageVersion(i3)) {
                            shellContextFactory.setLanguageVersion(i3);
                            i2++;
                        } else {
                            str = strArr[i2];
                        }
                    } catch (NumberFormatException unused) {
                        str = strArr[i2];
                    }
                }
                global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                exitCode = 1;
                return null;
            }
            if (str.equals("-opt") || str.equals("-O")) {
                i2++;
                if (i2 != strArr.length) {
                    try {
                        int i4 = Integer.parseInt(strArr[i2]);
                        if (i4 == -2) {
                            i4 = -1;
                        } else if (!Context.isValidOptimizationLevel(i4)) {
                            str = strArr[i2];
                        }
                        shellContextFactory.setOptimizationLevel(i4);
                        i2++;
                    } catch (NumberFormatException unused2) {
                        str = strArr[i2];
                    }
                }
                global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                exitCode = 1;
                return null;
            }
            if (str.equals("-encoding")) {
                i2++;
                if (i2 == strArr.length) {
                    global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                    global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                    exitCode = 1;
                    return null;
                }
                shellContextFactory.setCharacterEncoding(strArr[i2]);
                i2++;
            } else {
                if (str.equals("-strict")) {
                    shellContextFactory.setStrictMode(true);
                    shellContextFactory.setAllowReservedKeywords(false);
                    errorReporter.setIsReportingWarnings(true);
                } else if (str.equals("-fatal-warnings")) {
                    shellContextFactory.setWarningAsError(true);
                } else if (str.equals("-e")) {
                    processStdin = false;
                    i2++;
                    if (i2 == strArr.length) {
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                        exitCode = 1;
                        return null;
                    }
                    Global global2 = global;
                    if (!global2.initialized) {
                        global2.init(shellContextFactory);
                    }
                    IProxy iProxy = new IProxy(2);
                    iProxy.scriptText = strArr[i2];
                    shellContextFactory.call(iProxy);
                } else if (str.equals("-require")) {
                    useRequire = true;
                } else if (str.equals("-sandbox")) {
                    sandboxed = true;
                    useRequire = true;
                } else if (str.equals("-modules")) {
                    i2++;
                    if (i2 == strArr.length) {
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                        exitCode = 1;
                        return null;
                    }
                    if (modulePath == null) {
                        modulePath = new ArrayList();
                    }
                    modulePath.add(strArr[i2]);
                    useRequire = true;
                } else if (str.equals("-w")) {
                    errorReporter.setIsReportingWarnings(true);
                } else if (str.equals("-f")) {
                    processStdin = false;
                    i2++;
                    if (i2 == strArr.length) {
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                        exitCode = 1;
                        return null;
                    }
                    if (strArr[i2].equals(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
                        fileList.add(null);
                    } else {
                        fileList.add(strArr[i2]);
                        mainModule = strArr[i2];
                    }
                } else if (str.equals("-sealedlib")) {
                    global.setSealedStdLib(true);
                } else {
                    if (!str.equals("-debug")) {
                        if (str.equals("-?") || str.equals("-help")) {
                            global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                            exitCode = 1;
                            return null;
                        }
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", str));
                        global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
                        exitCode = 1;
                        return null;
                    }
                    shellContextFactory.setGeneratingDebug(true);
                }
                i2++;
            }
        }
        return new String[0];
    }

    public static void processSource(Context context, String str) throws IOException {
        if (str != null && !str.equals(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
            if (useRequire && str.equals(mainModule)) {
                require.requireMain(context, str);
                return;
            } else {
                processFile(context, getScope(str), str);
                return;
            }
        }
        Scriptable shellScope = getShellScope();
        String characterEncoding = shellContextFactory.getCharacterEncoding();
        ShellConsole console = global.getConsole(characterEncoding != null ? Charset.forName(characterEncoding) : Charset.defaultCharset());
        if (str == null) {
            console.println(context.getImplementationVersion());
        }
        boolean z2 = false;
        int i2 = 1;
        while (!z2) {
            String[] prompts = global.getPrompts(context);
            String str2 = str == null ? prompts[0] : null;
            console.flush();
            String str3 = "";
            while (true) {
                try {
                    String line = console.readLine(str2);
                    if (line == null) {
                        z2 = true;
                        break;
                    }
                    str3 = str3 + line + "\n";
                    i2++;
                    if (context.stringIsCompilableUnit(str3)) {
                        break;
                    } else {
                        str2 = prompts[1];
                    }
                } catch (IOException e2) {
                    console.println(e2.toString());
                }
            }
            try {
                try {
                    Script scriptCompileString = context.compileString(str3, "<stdin>", i2, null);
                    if (scriptCompileString != null) {
                        Object objExec = scriptCompileString.exec(context, shellScope);
                        if (objExec != Context.getUndefinedValue() && (!(objExec instanceof Function) || !str3.trim().startsWith("function"))) {
                            try {
                                console.println(Context.toString(objExec));
                            } catch (RhinoException e3) {
                                ToolErrorReporter.reportException(context.getErrorReporter(), e3);
                            }
                        }
                        NativeArray nativeArray = global.history;
                        nativeArray.put((int) nativeArray.getLength(), nativeArray, str3);
                    }
                } catch (RhinoException e4) {
                    ToolErrorReporter.reportException(context.getErrorReporter(), e4);
                    exitCode = 3;
                }
            } catch (VirtualMachineError e5) {
                e5.printStackTrace();
                Context.reportError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e5.toString()));
                exitCode = 3;
            }
        }
        console.println();
        console.flush();
    }

    private static Object readFileOrUrl(String str, boolean z2) throws IOException {
        return SourceReader.readFileOrUrl(str, z2, shellContextFactory.getCharacterEncoding());
    }

    public static void setErr(PrintStream printStream) {
        getGlobal().setErr(printStream);
    }

    public static void setIn(InputStream inputStream) {
        getGlobal().setIn(inputStream);
    }

    public static void setOut(PrintStream printStream) {
        getGlobal().setOut(printStream);
    }
}
