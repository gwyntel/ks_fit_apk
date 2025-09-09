package com.cicada.player.utils.ass;

import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class AssUtils {
    static {
        f.f();
    }

    public static void loadClass() {
    }

    private static native Object nParseAssDialogue(Object obj, String str);

    private static native Object nParseAssHeader(String str);

    public static AssDialogue parseAssDialogue(AssHeader assHeader, String str) {
        return !f.b() ? new AssDialogue() : (AssDialogue) nParseAssDialogue(assHeader, str);
    }

    public static AssHeader parseAssHeader(String str) {
        return !f.b() ? new AssHeader() : (AssHeader) nParseAssHeader(str);
    }
}
