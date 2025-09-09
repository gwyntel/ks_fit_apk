package org.mozilla.javascript.commonjs.module.provider;

import java.io.Serializable;

/* loaded from: classes5.dex */
public final class ParsedContentType implements Serializable {
    private static final long serialVersionUID = 1;
    private final String contentType;
    private final String encoding;

    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParsedContentType(java.lang.String r8) {
        /*
            r7 = this;
            r7.<init>()
            r0 = 0
            if (r8 == 0) goto L5e
            java.util.StringTokenizer r1 = new java.util.StringTokenizer
            java.lang.String r2 = ";"
            r1.<init>(r8, r2)
            boolean r8 = r1.hasMoreTokens()
            if (r8 == 0) goto L5e
            java.lang.String r8 = r1.nextToken()
            java.lang.String r8 = r8.trim()
        L1b:
            boolean r2 = r1.hasMoreTokens()
            if (r2 == 0) goto L5a
            java.lang.String r2 = r1.nextToken()
            java.lang.String r2 = r2.trim()
            java.lang.String r3 = "charset="
            boolean r3 = r2.startsWith(r3)
            if (r3 == 0) goto L1b
            r0 = 8
            java.lang.String r0 = r2.substring(r0)
            java.lang.String r0 = r0.trim()
            int r1 = r0.length()
            if (r1 <= 0) goto L5a
            r2 = 0
            char r3 = r0.charAt(r2)
            r4 = 1
            r5 = 34
            if (r3 != r5) goto L4f
            java.lang.String r0 = r0.substring(r4)
        L4f:
            int r1 = r1 - r4
            char r3 = r0.charAt(r1)
            if (r3 != r5) goto L5a
            java.lang.String r0 = r0.substring(r2, r1)
        L5a:
            r6 = r0
            r0 = r8
            r8 = r6
            goto L5f
        L5e:
            r8 = r0
        L5f:
            r7.contentType = r0
            r7.encoding = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.commonjs.module.provider.ParsedContentType.<init>(java.lang.String):void");
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getEncoding() {
        return this.encoding;
    }
}
