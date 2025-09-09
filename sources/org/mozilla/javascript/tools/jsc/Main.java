package org.mozilla.javascript.tools.jsc;

import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.optimizer.ClassCompiler;
import org.mozilla.javascript.tools.SourceReader;
import org.mozilla.javascript.tools.ToolErrorReporter;

/* loaded from: classes5.dex */
public class Main {
    private String characterEncoding;
    private ClassCompiler compiler;
    private CompilerEnvirons compilerEnv;
    private String destinationDir;
    private boolean printHelp;
    private ToolErrorReporter reporter = new ToolErrorReporter(true);
    private String targetName;
    private String targetPackage;

    public Main() {
        CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
        this.compilerEnv = compilerEnvirons;
        compilerEnvirons.setErrorReporter(this.reporter);
        this.compiler = new ClassCompiler(this.compilerEnv);
    }

    private void addError(String str, String str2) {
        addFormatedError(str2 == null ? ToolErrorReporter.getMessage(str) : ToolErrorReporter.getMessage(str, str2));
    }

    private void addFormatedError(String str) {
        this.reporter.error(str, null, -1, null, -1);
    }

    private static void badUsage(String str) {
        System.err.println(ToolErrorReporter.getMessage("msg.jsc.bad.usage", Main.class.getName(), str));
    }

    private File getOutputFile(File file, String str) {
        File file2 = new File(file, str.replace('.', File.separatorChar).concat(".class"));
        String parent = file2.getParent();
        if (parent != null) {
            File file3 = new File(parent);
            if (!file3.exists()) {
                file3.mkdirs();
            }
        }
        return file2;
    }

    public static void main(String[] strArr) throws IOException {
        Main main = new Main();
        String[] strArrProcessOptions = main.processOptions(strArr);
        if (strArrProcessOptions == null) {
            if (main.printHelp) {
                System.out.println(ToolErrorReporter.getMessage("msg.jsc.usage", Main.class.getName()));
                System.exit(0);
            }
            System.exit(1);
        }
        if (main.reporter.hasReportedError()) {
            return;
        }
        main.processSource(strArrProcessOptions);
    }

    private static void p(String str) {
        System.out.println(str);
    }

    private String readSource(File file) {
        String absolutePath = file.getAbsolutePath();
        if (!file.isFile()) {
            addError("msg.jsfile.not.found", absolutePath);
            return null;
        }
        try {
            return (String) SourceReader.readFileOrUrl(absolutePath, true, this.characterEncoding);
        } catch (FileNotFoundException unused) {
            addError("msg.couldnt.open", absolutePath);
            return null;
        } catch (IOException e2) {
            addFormatedError(e2.toString());
            return null;
        }
    }

    String getClassName(String str) {
        int i2 = 1;
        char[] cArr = new char[str.length() + 1];
        int i3 = 0;
        if (Character.isJavaIdentifierStart(str.charAt(0))) {
            i2 = 0;
        } else {
            cArr[0] = '_';
        }
        while (i3 < str.length()) {
            char cCharAt = str.charAt(i3);
            if (Character.isJavaIdentifierPart(cCharAt)) {
                cArr[i2] = cCharAt;
            } else {
                cArr[i2] = '_';
            }
            i3++;
            i2++;
        }
        return new String(cArr).trim();
    }

    public String[] processOptions(String[] strArr) {
        this.targetPackage = "";
        this.compilerEnv.setGenerateDebugInfo(false);
        int i2 = 0;
        while (i2 < strArr.length) {
            String str = strArr[i2];
            if (!str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
                int length = strArr.length - i2;
                String str2 = this.targetName;
                if (str2 != null && length > 1) {
                    addError("msg.multiple.js.to.file", str2);
                    return null;
                }
                String[] strArr2 = new String[length];
                for (int i3 = 0; i3 != length; i3++) {
                    strArr2[i3] = strArr[i2 + i3];
                }
                return strArr2;
            }
            if (str.equals("-help") || str.equals("-h") || str.equals("--help")) {
                this.printHelp = true;
                return null;
            }
            try {
                if (str.equals("-version") && (i2 = i2 + 1) < strArr.length) {
                    this.compilerEnv.setLanguageVersion(Integer.parseInt(strArr[i2]));
                } else if ((str.equals("-opt") || str.equals("-O")) && (i2 = i2 + 1) < strArr.length) {
                    this.compilerEnv.setOptimizationLevel(Integer.parseInt(strArr[i2]));
                } else if (str.equals("-nosource")) {
                    this.compilerEnv.setGeneratingSource(false);
                } else if (str.equals("-debug") || str.equals("-g")) {
                    this.compilerEnv.setGenerateDebugInfo(true);
                } else if (str.equals("-main-method-class") && (i2 = i2 + 1) < strArr.length) {
                    this.compiler.setMainMethodClass(strArr[i2]);
                } else if (str.equals("-encoding") && (i2 = i2 + 1) < strArr.length) {
                    this.characterEncoding = strArr[i2];
                } else if (!str.equals("-o") || (i2 = i2 + 1) >= strArr.length) {
                    if (str.equals("-observe-instruction-count")) {
                        this.compilerEnv.setGenerateObserverCount(true);
                    }
                    if (str.equals("-package") && (i2 = i2 + 1) < strArr.length) {
                        String str3 = strArr[i2];
                        int length2 = str3.length();
                        int i4 = 0;
                        while (i4 != length2) {
                            char cCharAt = str3.charAt(i4);
                            if (Character.isJavaIdentifierStart(cCharAt)) {
                                do {
                                    i4++;
                                    if (i4 == length2) {
                                        break;
                                    }
                                    cCharAt = str3.charAt(i4);
                                } while (Character.isJavaIdentifierPart(cCharAt));
                                if (i4 == length2) {
                                    break;
                                }
                                if (cCharAt == '.' && i4 != length2 - 1) {
                                    i4++;
                                }
                            }
                            addError("msg.package.name", this.targetPackage);
                            return null;
                        }
                        this.targetPackage = str3;
                    } else if (str.equals("-extends") && (i2 = i2 + 1) < strArr.length) {
                        try {
                            this.compiler.setTargetExtends(Class.forName(strArr[i2]));
                        } catch (ClassNotFoundException e2) {
                            throw new Error(e2.toString());
                        }
                    } else if (str.equals("-implements") && (i2 = i2 + 1) < strArr.length) {
                        StringTokenizer stringTokenizer = new StringTokenizer(strArr[i2], ",");
                        ArrayList arrayList = new ArrayList();
                        while (stringTokenizer.hasMoreTokens()) {
                            try {
                                arrayList.add(Class.forName(stringTokenizer.nextToken()));
                            } catch (ClassNotFoundException e3) {
                                throw new Error(e3.toString());
                            }
                        }
                        this.compiler.setTargetImplements((Class[]) arrayList.toArray(new Class[arrayList.size()]));
                    } else {
                        if (!str.equals("-d") || (i2 = i2 + 1) >= strArr.length) {
                            badUsage(str);
                            return null;
                        }
                        this.destinationDir = strArr[i2];
                    }
                } else {
                    String strSubstring = strArr[i2];
                    int length3 = strSubstring.length();
                    if (length3 == 0 || !Character.isJavaIdentifierStart(strSubstring.charAt(0))) {
                        addError("msg.invalid.classfile.name", strSubstring);
                    } else {
                        int i5 = 1;
                        while (true) {
                            if (i5 >= length3) {
                                break;
                            }
                            char cCharAt2 = strSubstring.charAt(i5);
                            if (Character.isJavaIdentifierPart(cCharAt2)) {
                                i5++;
                            } else if (cCharAt2 == '.' && i5 == length3 - 6 && strSubstring.endsWith(".class")) {
                                strSubstring = strSubstring.substring(0, i5);
                            } else {
                                addError("msg.invalid.classfile.name", strSubstring);
                            }
                        }
                        this.targetName = strSubstring;
                    }
                }
                i2++;
            } catch (NumberFormatException unused) {
                badUsage(strArr[i2]);
                return null;
            }
        }
        p(ToolErrorReporter.getMessage("msg.no.file"));
        return null;
    }

    public void processSource(String[] strArr) throws IOException {
        File file;
        for (int i2 = 0; i2 != strArr.length; i2++) {
            String str = strArr[i2];
            if (!str.endsWith(".js")) {
                addError("msg.extension.not.js", str);
                return;
            }
            File file2 = new File(str);
            String source = readSource(file2);
            if (source == null) {
                return;
            }
            String className = this.targetName;
            if (className == null) {
                className = getClassName(file2.getName().substring(0, r5.length() - 3));
            }
            if (this.targetPackage.length() != 0) {
                className = this.targetPackage + "." + className;
            }
            Object[] objArrCompileToClassFiles = this.compiler.compileToClassFiles(source, str, 1, className);
            if (objArrCompileToClassFiles == null || objArrCompileToClassFiles.length == 0) {
                return;
            }
            if (this.destinationDir != null) {
                file = new File(this.destinationDir);
            } else {
                String parent = file2.getParent();
                file = parent != null ? new File(parent) : null;
            }
            for (int i3 = 0; i3 != objArrCompileToClassFiles.length; i3 += 2) {
                String str2 = (String) objArrCompileToClassFiles[i3];
                byte[] bArr = (byte[]) objArrCompileToClassFiles[i3 + 1];
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(getOutputFile(file, str2));
                    try {
                        fileOutputStream.write(bArr);
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        fileOutputStream.close();
                        throw th;
                    }
                } catch (IOException e2) {
                    addFormatedError(e2.toString());
                }
            }
        }
    }
}
