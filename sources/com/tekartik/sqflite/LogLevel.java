package com.tekartik.sqflite;

import io.flutter.plugin.common.MethodCall;

/* loaded from: classes4.dex */
public class LogLevel {
    static Integer a(MethodCall methodCall) {
        return (Integer) methodCall.argument("logLevel");
    }

    static boolean b(int i2) {
        return i2 >= 1;
    }

    static boolean c(int i2) {
        return i2 >= 2;
    }
}
