package com.alibaba.ailabs.iot.aisbase.env;

/* loaded from: classes2.dex */
public class AppEnv {
    public static final boolean IS_GENIE_ENV = isGenieAppEnv();
    public static final String MTOP_CLASS_NAME = "mtopsdk.mtop.domain.MtopRequest";

    public static boolean isGenieAppEnv() throws ClassNotFoundException {
        try {
            Class.forName(MTOP_CLASS_NAME);
            return true;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
