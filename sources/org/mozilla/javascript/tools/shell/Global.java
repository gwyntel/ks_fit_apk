package org.mozilla.javascript.tools.shell;

import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Synchronizer;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;
import org.mozilla.javascript.tools.ToolErrorReporter;

/* loaded from: classes5.dex */
public class Global extends ImporterTopLevel {
    static final long serialVersionUID = 4029130780977538005L;
    boolean attemptedJLineLoad;
    private ShellConsole console;
    private HashMap<String, String> doctestCanonicalizations;
    private PrintStream errStream;
    NativeArray history;
    private InputStream inStream;
    boolean initialized;
    private PrintStream outStream;
    private QuitAction quitAction;
    private boolean sealedStdLib = false;
    private String[] prompts = {"js> ", "  > "};

    public Global() {
    }

    public static void defineClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = getClass(objArr);
        if (!Scriptable.class.isAssignableFrom(cls)) {
            throw reportRuntimeError("msg.must.implement.Scriptable");
        }
        ScriptableObject.defineClass(scriptable, cls);
    }

    public static Object deserialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws ClassNotFoundException, IOException {
        if (objArr.length < 1) {
            throw Context.reportRuntimeError("Expected a filename to read the serialization from");
        }
        FileInputStream fileInputStream = new FileInputStream(Context.toString(objArr[0]));
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        ScriptableInputStream scriptableInputStream = new ScriptableInputStream(fileInputStream, topLevelScope);
        Object object = scriptableInputStream.readObject();
        scriptableInputStream.close();
        return Context.toObject(object, topLevelScope);
    }

    private static Object doPrint(Object[] objArr, Function function, boolean z2) {
        PrintStream out = getInstance(function).getOut();
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (i2 > 0) {
                out.print(" ");
            }
            out.print(Context.toString(objArr[i2]));
        }
        if (z2) {
            out.println();
        }
        return Context.getUndefinedValue();
    }

    public static Object doctest(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length == 0) {
            return Boolean.FALSE;
        }
        String string = Context.toString(objArr[0]);
        Global global = getInstance(function);
        return new Integer(global.runDoctest(context, global, string, null, 0));
    }

    private boolean doctestOutputMatches(String str, String str2) {
        String strTrim = str.trim();
        String strReplace = str2.trim().replace(IOUtils.LINE_SEPARATOR_WINDOWS, "\n");
        if (strTrim.equals(strReplace)) {
            return true;
        }
        for (Map.Entry<String, String> entry : this.doctestCanonicalizations.entrySet()) {
            strTrim = strTrim.replace(entry.getKey(), entry.getValue());
        }
        if (strTrim.equals(strReplace)) {
            return true;
        }
        Pattern patternCompile = Pattern.compile("@[0-9a-fA-F]+");
        Matcher matcher = patternCompile.matcher(strTrim);
        Matcher matcher2 = patternCompile.matcher(strReplace);
        while (matcher.find() && matcher2.find() && matcher2.start() == matcher.start()) {
            int iStart = matcher.start();
            if (!strTrim.substring(0, iStart).equals(strReplace.substring(0, iStart))) {
                return false;
            }
            String strGroup = matcher.group();
            String strGroup2 = matcher2.group();
            String str3 = this.doctestCanonicalizations.get(strGroup);
            if (str3 == null) {
                this.doctestCanonicalizations.put(strGroup, strGroup2);
                strTrim = strTrim.replace(strGroup, strGroup2);
            } else if (!strGroup2.equals(str3)) {
                return false;
            }
            if (strTrim.equals(strReplace)) {
                return true;
            }
        }
        return false;
    }

    public static void gc(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        System.gc();
    }

    private static String getCharCodingFromType(String str) {
        int iIndexOf = str.indexOf(59);
        if (iIndexOf < 0) {
            return null;
        }
        int length = str.length();
        do {
            iIndexOf++;
            if (iIndexOf == length) {
                break;
            }
        } while (str.charAt(iIndexOf) <= ' ');
        if (!"charset".regionMatches(true, 0, str, iIndexOf, 7)) {
            return null;
        }
        int i2 = iIndexOf + 7;
        while (i2 != length && str.charAt(i2) <= ' ') {
            i2++;
        }
        if (i2 == length || str.charAt(i2) != '=') {
            return null;
        }
        do {
            i2++;
            if (i2 == length) {
                break;
            }
        } while (str.charAt(i2) <= ' ');
        if (i2 == length) {
            return null;
        }
        while (str.charAt(length - 1) <= ' ') {
            length--;
        }
        return str.substring(i2, length);
    }

    private static Class<?> getClass(Object[] objArr) {
        if (objArr.length == 0) {
            throw reportRuntimeError("msg.expected.string.arg");
        }
        Object obj = objArr[0];
        if (obj instanceof Wrapper) {
            Object objUnwrap = ((Wrapper) obj).unwrap();
            if (objUnwrap instanceof Class) {
                return (Class) objUnwrap;
            }
        }
        String string = Context.toString(objArr[0]);
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException unused) {
            throw reportRuntimeError("msg.class.not.found", string);
        }
    }

    private static Global getInstance(Function function) {
        Scriptable parentScope = function.getParentScope();
        if (parentScope instanceof Global) {
            return (Global) parentScope;
        }
        throw reportRuntimeError("msg.bad.shell.function.scope", String.valueOf(parentScope));
    }

    public static void help(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        getInstance(function).getOut().println(ToolErrorReporter.getMessage("msg.help"));
    }

    public static void load(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        for (Object obj : objArr) {
            String string = Context.toString(obj);
            try {
                Main.processFile(context, scriptable, string);
            } catch (IOException e2) {
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.couldnt.read.source", string, e2.getMessage()));
            } catch (VirtualMachineError e3) {
                e3.printStackTrace();
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e3.toString()));
            }
        }
    }

    public static void loadClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException {
        Class<?> cls = getClass(objArr);
        if (!Script.class.isAssignableFrom(cls)) {
            throw reportRuntimeError("msg.must.implement.Script");
        }
        ((Script) cls.newInstance()).exec(context, scriptable);
    }

    private boolean loadJLine(Charset charset) {
        if (!this.attemptedJLineLoad) {
            this.attemptedJLineLoad = true;
            this.console = ShellConsole.getConsole(this, charset);
        }
        return this.console != null;
    }

    static void pipe(boolean z2, InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int i2 = !z2 ? inputStream.read(bArr, 0, 4096) : inputStream.read(bArr, 0, 4096);
                if (i2 >= 0) {
                    if (z2) {
                        outputStream.write(bArr, 0, i2);
                        outputStream.flush();
                    } else {
                        try {
                            outputStream.write(bArr, 0, i2);
                            outputStream.flush();
                        } catch (IOException unused) {
                        }
                    }
                }
                try {
                    break;
                } catch (IOException unused2) {
                    return;
                }
            }
        } finally {
            try {
                if (z2) {
                    inputStream.close();
                } else {
                    outputStream.close();
                }
            } catch (IOException unused3) {
            }
        }
    }

    public static Object print(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, true);
    }

    public static void quit(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Global global = getInstance(function);
        if (global.quitAction != null) {
            global.quitAction.quit(context, objArr.length != 0 ? ScriptRuntime.toInt32(objArr[0]) : 0);
        }
    }

    public static Object readFile(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length != 0) {
            return readUrl(ScriptRuntime.toString(objArr[0]), objArr.length >= 2 ? ScriptRuntime.toString(objArr[1]) : null, true);
        }
        throw reportRuntimeError("msg.shell.readFile.bad.args");
    }

    private static String readReader(Reader reader) throws IOException {
        return readReader(reader, 4096);
    }

    public static Object readUrl(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length != 0) {
            return readUrl(ScriptRuntime.toString(objArr[0]), objArr.length >= 2 ? ScriptRuntime.toString(objArr[1]) : null, false);
        }
        throw reportRuntimeError("msg.shell.readUrl.bad.args");
    }

    public static Object readline(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        Global global = getInstance(function);
        return objArr.length > 0 ? global.console.readLine(Context.toString(objArr[0])) : global.console.readLine();
    }

    static RuntimeException reportRuntimeError(String str) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e8  */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object runCommand(org.mozilla.javascript.Context r20, org.mozilla.javascript.Scriptable r21, java.lang.Object[] r22, org.mozilla.javascript.Function r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.runCommand(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[], org.mozilla.javascript.Function):java.lang.Object");
    }

    private static int runProcess(String[] strArr, String[] strArr2, File file, InputStream inputStream, OutputStream outputStream, OutputStream outputStream2) throws IOException {
        PipeThread pipeThread;
        PipeThread pipeThread2;
        PipeThread pipeThread3 = null;
        Process processExec = strArr2 == null ? Runtime.getRuntime().exec(strArr, (String[]) null, file) : Runtime.getRuntime().exec(strArr, strArr2, file);
        try {
            if (inputStream != null) {
                pipeThread = new PipeThread(false, inputStream, processExec.getOutputStream());
                pipeThread.start();
            } else {
                processExec.getOutputStream().close();
                pipeThread = null;
            }
            if (outputStream != null) {
                pipeThread2 = new PipeThread(true, processExec.getInputStream(), outputStream);
                pipeThread2.start();
            } else {
                processExec.getInputStream().close();
                pipeThread2 = null;
            }
            if (outputStream2 != null) {
                pipeThread3 = new PipeThread(true, processExec.getErrorStream(), outputStream2);
                pipeThread3.start();
            } else {
                processExec.getErrorStream().close();
            }
            while (true) {
                try {
                    processExec.waitFor();
                    if (pipeThread2 != null) {
                        pipeThread2.join();
                    }
                    if (pipeThread != null) {
                        pipeThread.join();
                    }
                    if (pipeThread3 == null) {
                        break;
                    }
                    pipeThread3.join();
                    break;
                } catch (InterruptedException unused) {
                }
            }
            int iExitValue = processExec.exitValue();
            processExec.destroy();
            return iExitValue;
        } catch (Throwable th) {
            processExec.destroy();
            throw th;
        }
    }

    public static void seal(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        for (int i2 = 0; i2 != objArr.length; i2++) {
            Object obj = objArr[i2];
            if (!(obj instanceof ScriptableObject) || obj == Undefined.instance) {
                if ((obj instanceof Scriptable) && obj != Undefined.instance) {
                    throw reportRuntimeError("msg.shell.seal.not.scriptable");
                }
                throw reportRuntimeError("msg.shell.seal.not.object");
            }
        }
        for (int i3 = 0; i3 != objArr.length; i3++) {
            ((ScriptableObject) objArr[i3]).sealObject();
        }
    }

    public static void serialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length < 2) {
            throw Context.reportRuntimeError("Expected an object to serialize and a filename to write the serialization to");
        }
        Object obj = objArr[0];
        ScriptableOutputStream scriptableOutputStream = new ScriptableOutputStream(new FileOutputStream(Context.toString(objArr[1])), ScriptableObject.getTopLevelScope(scriptable));
        scriptableOutputStream.writeObject(obj);
        scriptableOutputStream.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object spawn(org.mozilla.javascript.Context r2, org.mozilla.javascript.Scriptable r3, java.lang.Object[] r4, org.mozilla.javascript.Function r5) {
        /*
            org.mozilla.javascript.Scriptable r3 = r5.getParentScope()
            int r5 = r4.length
            r0 = 0
            if (r5 == 0) goto L2e
            r5 = r4[r0]
            boolean r5 = r5 instanceof org.mozilla.javascript.Function
            if (r5 == 0) goto L2e
            int r5 = r4.length
            r1 = 1
            if (r5 <= r1) goto L1f
            r5 = r4[r1]
            boolean r1 = r5 instanceof org.mozilla.javascript.Scriptable
            if (r1 == 0) goto L1f
            org.mozilla.javascript.Scriptable r5 = (org.mozilla.javascript.Scriptable) r5
            java.lang.Object[] r5 = r2.getElements(r5)
            goto L20
        L1f:
            r5 = 0
        L20:
            if (r5 != 0) goto L24
            java.lang.Object[] r5 = org.mozilla.javascript.ScriptRuntime.emptyArgs
        L24:
            org.mozilla.javascript.tools.shell.Runner r1 = new org.mozilla.javascript.tools.shell.Runner
            r4 = r4[r0]
            org.mozilla.javascript.Function r4 = (org.mozilla.javascript.Function) r4
            r1.<init>(r3, r4, r5)
            goto L3e
        L2e:
            int r5 = r4.length
            if (r5 == 0) goto L4d
            r4 = r4[r0]
            boolean r5 = r4 instanceof org.mozilla.javascript.Script
            if (r5 == 0) goto L4d
            org.mozilla.javascript.tools.shell.Runner r1 = new org.mozilla.javascript.tools.shell.Runner
            org.mozilla.javascript.Script r4 = (org.mozilla.javascript.Script) r4
            r1.<init>(r3, r4)
        L3e:
            org.mozilla.javascript.ContextFactory r2 = r2.getFactory()
            r1.factory = r2
            java.lang.Thread r2 = new java.lang.Thread
            r2.<init>(r1)
            r2.start()
            return r2
        L4d:
            java.lang.String r2 = "msg.spawn.args"
            java.lang.RuntimeException r2 = reportRuntimeError(r2)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.spawn(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[], org.mozilla.javascript.Function):java.lang.Object");
    }

    public static Object sync(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Object obj;
        if (objArr.length >= 1 && objArr.length <= 2) {
            Object obj2 = objArr[0];
            if (obj2 instanceof Function) {
                if (objArr.length != 2 || (obj = objArr[1]) == Undefined.instance) {
                    obj = null;
                }
                return new Synchronizer((Function) obj2, obj);
            }
        }
        throw reportRuntimeError("msg.sync.args");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.io.InputStream toInputStream(java.lang.Object r4) throws java.io.IOException {
        /*
            boolean r0 = r4 instanceof org.mozilla.javascript.Wrapper
            r1 = 0
            if (r0 == 0) goto L3c
            r0 = r4
            org.mozilla.javascript.Wrapper r0 = (org.mozilla.javascript.Wrapper) r0
            java.lang.Object r0 = r0.unwrap()
            boolean r2 = r0 instanceof java.io.InputStream
            if (r2 == 0) goto L16
            java.io.InputStream r0 = (java.io.InputStream) r0
            r3 = r1
            r1 = r0
            r0 = r3
            goto L3d
        L16:
            boolean r2 = r0 instanceof byte[]
            if (r2 == 0) goto L24
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            byte[] r0 = (byte[]) r0
            r2.<init>(r0)
            r0 = r1
            r1 = r2
            goto L3d
        L24:
            boolean r2 = r0 instanceof java.io.Reader
            if (r2 == 0) goto L2f
            java.io.Reader r0 = (java.io.Reader) r0
            java.lang.String r0 = readReader(r0)
            goto L3d
        L2f:
            boolean r2 = r0 instanceof char[]
            if (r2 == 0) goto L3c
            java.lang.String r2 = new java.lang.String
            char[] r0 = (char[]) r0
            r2.<init>(r0)
            r0 = r2
            goto L3d
        L3c:
            r0 = r1
        L3d:
            if (r1 != 0) goto L4e
            if (r0 != 0) goto L45
            java.lang.String r0 = org.mozilla.javascript.ScriptRuntime.toString(r4)
        L45:
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream
            byte[] r4 = r0.getBytes()
            r1.<init>(r4)
        L4e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.shell.Global.toInputStream(java.lang.Object):java.io.InputStream");
    }

    private static OutputStream toOutputStream(Object obj) {
        if (obj instanceof Wrapper) {
            Object objUnwrap = ((Wrapper) obj).unwrap();
            if (objUnwrap instanceof OutputStream) {
                return (OutputStream) objUnwrap;
            }
        }
        return null;
    }

    public static Object toint32(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Object obj = objArr.length != 0 ? objArr[0] : Undefined.instance;
        return obj instanceof Integer ? obj : ScriptRuntime.wrapInt(ScriptRuntime.toInt32(obj));
    }

    public static double version(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        double languageVersion = context.getLanguageVersion();
        if (objArr.length > 0) {
            context.setLanguageVersion((int) Context.toNumber(objArr[0]));
        }
        return languageVersion;
    }

    public static Object write(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, false);
    }

    public ShellConsole getConsole(Charset charset) {
        if (!loadJLine(charset)) {
            this.console = ShellConsole.getConsole(getIn(), getErr(), charset);
        }
        return this.console;
    }

    public PrintStream getErr() {
        PrintStream printStream = this.errStream;
        return printStream == null ? System.err : printStream;
    }

    public InputStream getIn() {
        if (this.inStream == null && !this.attemptedJLineLoad && loadJLine(Charset.defaultCharset())) {
            this.inStream = this.console.getIn();
        }
        InputStream inputStream = this.inStream;
        return inputStream == null ? System.in : inputStream;
    }

    public PrintStream getOut() {
        PrintStream printStream = this.outStream;
        return printStream == null ? System.out : printStream;
    }

    public String[] getPrompts(Context context) {
        if (ScriptableObject.hasProperty(this, "prompts")) {
            Object property = ScriptableObject.getProperty(this, "prompts");
            if (property instanceof Scriptable) {
                Scriptable scriptable = (Scriptable) property;
                if (ScriptableObject.hasProperty(scriptable, 0) && ScriptableObject.hasProperty(scriptable, 1)) {
                    Object property2 = ScriptableObject.getProperty(scriptable, 0);
                    if (property2 instanceof Function) {
                        property2 = ((Function) property2).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[0] = Context.toString(property2);
                    Object property3 = ScriptableObject.getProperty(scriptable, 1);
                    if (property3 instanceof Function) {
                        property3 = ((Function) property3).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[1] = Context.toString(property3);
                }
            }
        }
        return this.prompts;
    }

    public void init(ContextFactory contextFactory) {
        contextFactory.call(new ContextAction() { // from class: org.mozilla.javascript.tools.shell.Global.1
            @Override // org.mozilla.javascript.ContextAction
            public Object run(Context context) {
                Global.this.init(context);
                return null;
            }
        });
    }

    public void initQuitAction(QuitAction quitAction) {
        if (quitAction == null) {
            throw new IllegalArgumentException("quitAction is null");
        }
        if (this.quitAction != null) {
            throw new IllegalArgumentException("The method is once-call.");
        }
        this.quitAction = quitAction;
    }

    public Require installRequire(Context context, List<String> list, boolean z2) {
        RequireBuilder requireBuilder = new RequireBuilder();
        requireBuilder.setSandboxed(z2);
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (String str : list) {
                try {
                    URI uri = new URI(str);
                    if (!uri.isAbsolute()) {
                        uri = new File(str).toURI().resolve("");
                    }
                    if (!uri.toString().endsWith("/")) {
                        uri = new URI(uri + "/");
                    }
                    arrayList.add(uri);
                } catch (URISyntaxException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        requireBuilder.setModuleScriptProvider(new SoftCachingModuleScriptProvider(new UrlModuleSourceProvider(arrayList, null)));
        Require requireCreateRequire = requireBuilder.createRequire(context, this);
        requireCreateRequire.install(this);
        return requireCreateRequire;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 10 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 4 */
    public int runDoctest(Context context, Scriptable scriptable, String str, String str2, int i2) throws Throwable {
        PrintStream printStream;
        StringBuilder sb;
        this.doctestCanonicalizations = new HashMap<>();
        String[] strArrSplit = str.split("\r\n?|\n");
        String strTrim = this.prompts[0].trim();
        String strTrim2 = this.prompts[1].trim();
        int i3 = 0;
        while (i3 < strArrSplit.length && !strArrSplit[i3].trim().startsWith(strTrim)) {
            i3++;
        }
        int i4 = 0;
        while (i3 < strArrSplit.length) {
            String strSubstring = strArrSplit[i3].trim().substring(strTrim.length());
            int i5 = i3 + 1;
            String str3 = strSubstring + "\n";
            while (i5 < strArrSplit.length && strArrSplit[i5].trim().startsWith(strTrim2)) {
                str3 = (str3 + strArrSplit[i5].trim().substring(strTrim2.length())) + "\n";
                i5++;
            }
            int i6 = i5;
            String str4 = "";
            while (i6 < strArrSplit.length && !strArrSplit[i6].trim().startsWith(strTrim)) {
                str4 = str4 + strArrSplit[i6] + "\n";
                i6++;
            }
            PrintStream out = getOut();
            PrintStream err = getErr();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            setOut(new PrintStream(byteArrayOutputStream));
            setErr(new PrintStream(byteArrayOutputStream2));
            ErrorReporter errorReporter = context.getErrorReporter();
            String str5 = str4;
            context.setErrorReporter(new ToolErrorReporter(false, getErr()));
            int i7 = i4 + 1;
            int i8 = i6;
            String[] strArr = strArrSplit;
            String str6 = strTrim;
            String str7 = str3;
            try {
                Object objEvaluateString = context.evaluateString(scriptable, str3, "doctest input", 1, null);
                String string = (objEvaluateString == Context.getUndefinedValue() || ((objEvaluateString instanceof Function) && str7.trim().startsWith("function"))) ? "" : Context.toString(objEvaluateString);
                setOut(out);
                setErr(err);
                context.setErrorReporter(errorReporter);
                sb = new StringBuilder();
                sb.append(string);
            } catch (RhinoException e2) {
                printStream = out;
                try {
                    ToolErrorReporter.reportException(context.getErrorReporter(), e2);
                    setOut(printStream);
                    setErr(err);
                    context.setErrorReporter(errorReporter);
                    sb = new StringBuilder();
                    sb.append("");
                } catch (Throwable th) {
                    th = th;
                    setOut(printStream);
                    setErr(err);
                    context.setErrorReporter(errorReporter);
                    byteArrayOutputStream2.toString();
                    byteArrayOutputStream.toString();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                printStream = out;
                setOut(printStream);
                setErr(err);
                context.setErrorReporter(errorReporter);
                byteArrayOutputStream2.toString();
                byteArrayOutputStream.toString();
                throw th;
            }
            sb.append(byteArrayOutputStream2.toString());
            sb.append(byteArrayOutputStream.toString());
            String string2 = sb.toString();
            if (!doctestOutputMatches(str5, string2)) {
                String str8 = "doctest failure running:\n" + str7 + "expected: " + str5 + "actual: " + string2 + "\n";
                if (str2 != null) {
                    throw Context.reportRuntimeError(str8, str2, (i2 + i8) - 1, null, 0);
                }
                throw Context.reportRuntimeError(str8);
            }
            strTrim = str6;
            i3 = i8;
            strArrSplit = strArr;
            i4 = i7;
        }
        return i4;
    }

    public void setErr(PrintStream printStream) {
        this.errStream = printStream;
    }

    public void setIn(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public void setOut(PrintStream printStream) {
        this.outStream = printStream;
    }

    public void setSealedStdLib(boolean z2) {
        this.sealedStdLib = z2;
    }

    private static String readReader(Reader reader, int i2) throws IOException {
        char[] cArr = new char[i2];
        int i3 = 0;
        while (true) {
            int i4 = reader.read(cArr, i3, cArr.length - i3);
            if (i4 < 0) {
                return new String(cArr, 0, i3);
            }
            i3 += i4;
            if (i3 == cArr.length) {
                char[] cArr2 = new char[cArr.length * 2];
                System.arraycopy(cArr, 0, cArr2, 0, i3);
                cArr = cArr2;
            }
        }
    }

    public void init(Context context) {
        initStandardObjects(context, this.sealedStdLib);
        defineFunctionProperties(new String[]{"defineClass", "deserialize", "doctest", "gc", "help", "load", "loadClass", "print", "quit", "readline", "readFile", "readUrl", "runCommand", "seal", "serialize", "spawn", InvokeServiceData.CALL_TYPE_SYNC, "toint32", "version", "write"}, Global.class, 2);
        Environment.defineClass(this);
        defineProperty("environment", new Environment(this), 2);
        NativeArray nativeArray = (NativeArray) context.newArray(this, 0);
        this.history = nativeArray;
        defineProperty("history", nativeArray, 2);
        this.initialized = true;
    }

    static RuntimeException reportRuntimeError(String str, String str2) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str, str2));
    }

    public Global(Context context) {
        init(context);
    }

    private static String readUrl(String str, String str2, boolean z2) throws IOException {
        int contentLength;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        String contentType;
        InputStream inputStream2 = null;
        try {
            if (!z2) {
                URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
                inputStream = uRLConnectionOpenConnection.getInputStream();
                contentLength = uRLConnectionOpenConnection.getContentLength();
                if (contentLength <= 0) {
                    contentLength = 1024;
                }
                if (str2 == null && (contentType = uRLConnectionOpenConnection.getContentType()) != null) {
                    str2 = getCharCodingFromType(contentType);
                }
            } else {
                File file = new File(str);
                if (file.exists()) {
                    if (file.canRead()) {
                        long length = file.length();
                        int i2 = (int) length;
                        if (i2 != length) {
                            throw new IOException("Too big file size: " + length);
                        }
                        if (i2 == 0) {
                            return "";
                        }
                        FileInputStream fileInputStream = new FileInputStream(file);
                        contentLength = i2;
                        inputStream = fileInputStream;
                    } else {
                        throw new IOException("Cannot read file: " + str);
                    }
                } else {
                    throw new FileNotFoundException("File not found: " + str);
                }
            }
            if (str2 == null) {
                inputStreamReader = new InputStreamReader(inputStream);
            } else {
                inputStreamReader = new InputStreamReader(inputStream, str2);
            }
            String reader = readReader(inputStreamReader, contentLength);
            if (inputStream != null) {
                inputStream.close();
            }
            return reader;
        } catch (Throwable th) {
            if (0 != 0) {
                inputStream2.close();
            }
            throw th;
        }
    }
}
