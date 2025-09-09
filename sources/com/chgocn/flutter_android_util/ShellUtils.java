package com.chgocn.flutter_android_util;

import java.util.List;

/* loaded from: classes3.dex */
public class ShellUtils {
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_SU = "su";

    private ShellUtils() {
        throw new AssertionError();
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static CommandResult execCommand(String str, boolean z2) {
        return execCommand(new String[]{str}, z2, true);
    }

    public static class CommandResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public CommandResult(int i2) {
            this.result = i2;
        }

        public String toString() {
            Integer numValueOf = Integer.valueOf(this.result);
            String str = this.successMsg;
            return String.format("{\"code\": %d,\"msg\": %s}", numValueOf, (str == null && (str = this.errorMsg) == null) ? "" : str.toString());
        }

        public CommandResult(int i2, String str, String str2) {
            this.result = i2;
            this.successMsg = str;
            this.errorMsg = str2;
        }
    }

    public static CommandResult execCommand(List<String> list, boolean z2) {
        return execCommand(list == null ? null : (String[]) list.toArray(new String[0]), z2, true);
    }

    public static CommandResult execCommand(String[] strArr, boolean z2) {
        return execCommand(strArr, z2, true);
    }

    public static CommandResult execCommand(String str, boolean z2, boolean z3) {
        return execCommand(new String[]{str}, z2, z3);
    }

    public static CommandResult execCommand(List<String> list, boolean z2, boolean z3) {
        return execCommand(list == null ? null : (String[]) list.toArray(new String[0]), z2, z3);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:168|7|(1:9)(1:21)|22|170|23|156|24|(3:26|(2:28|174)(2:29|173)|30)|172|38|(18:40|160|41|167|42|164|43|(3:162|44|(1:46)(1:175))|(2:59|(1:61)(0))|78|(1:80)|(1:84)|87|123|(1:125)(1:126)|(1:129)|130|131)(1:77)|154|78|(0)|(0)|87|123|(0)(0)|(0)|130|131) */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x010a, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0112, code lost:
    
        r2.printStackTrace();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0137 A[Catch: IOException -> 0x0133, TryCatch #3 {IOException -> 0x0133, blocks: (B:96:0x012f, B:100:0x0137, B:102:0x013c), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x013c A[Catch: IOException -> 0x0133, TRY_LEAVE, TryCatch #3 {IOException -> 0x0133, blocks: (B:96:0x012f, B:100:0x0137, B:102:0x013c), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0145 A[PHI: r1 r2 r3 r4 r5 r9 r10
      0x0145: PHI (r1v6 java.lang.Object) = (r1v5 java.lang.Object), (r1v9 java.lang.Object) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r2v4 int) = (r2v3 int), (r2v6 int) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r3v12 java.io.DataOutputStream) = (r3v11 java.io.DataOutputStream), (r3v14 java.io.DataOutputStream) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r4v9 java.io.BufferedReader) = (r4v8 java.io.BufferedReader), (r4v11 java.io.BufferedReader) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r5v6 ??) = (r5v5 ??), (r5v8 ??) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r9v12 java.lang.StringBuilder) = (r9v11 java.lang.StringBuilder), (r9v14 java.lang.StringBuilder) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0145: PHI (r10v10 java.lang.Process) = (r10v9 java.lang.Process), (r10v12 java.lang.Process) binds: [B:105:0x0143, B:121:0x016b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x015f A[Catch: IOException -> 0x015b, TryCatch #23 {IOException -> 0x015b, blocks: (B:112:0x0157, B:116:0x015f, B:118:0x0164), top: B:158:0x0157 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0164 A[Catch: IOException -> 0x015b, TRY_LEAVE, TryCatch #23 {IOException -> 0x015b, blocks: (B:112:0x0157, B:116:0x015f, B:118:0x0164), top: B:158:0x0157 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x018d A[Catch: IOException -> 0x0189, TryCatch #11 {IOException -> 0x0189, blocks: (B:133:0x0185, B:137:0x018d, B:139:0x0192), top: B:152:0x0185 }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0192 A[Catch: IOException -> 0x0189, TRY_LEAVE, TryCatch #11 {IOException -> 0x0189, blocks: (B:133:0x0185, B:137:0x018d, B:139:0x0192), top: B:152:0x0185 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x012f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0185 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0157 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0106 A[Catch: IOException -> 0x010a, TryCatch #12 {IOException -> 0x010a, blocks: (B:78:0x0101, B:80:0x0106, B:84:0x010e), top: B:154:0x0101 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x010e A[Catch: IOException -> 0x010a, TRY_LEAVE, TryCatch #12 {IOException -> 0x010a, blocks: (B:78:0x0101, B:80:0x0106, B:84:0x010e), top: B:154:0x0101 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v13, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v42 */
    /* JADX WARN: Type inference failed for: r4v43 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v28 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.chgocn.flutter_android_util.ShellUtils.CommandResult execCommand(java.lang.String[] r8, boolean r9, boolean r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chgocn.flutter_android_util.ShellUtils.execCommand(java.lang.String[], boolean, boolean):com.chgocn.flutter_android_util.ShellUtils$CommandResult");
    }
}
