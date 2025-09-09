package a.a.a.a.b.d;

import a.a.a.a.b.m.j;
import android.os.Build;
import com.alibaba.ailabs.iot.aisbase.env.AppEnv;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static final boolean f1334a;

    /* renamed from: b, reason: collision with root package name */
    public static final boolean f1335b;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f1336c = false;

    static {
        boolean zA = a();
        f1334a = zA;
        f1335b = j.a("com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient") && !zA;
        if (AgooConstants.ACK_REMOVE_PACKAGE.equals(Build.VERSION.RELEASE) && "STK-AL00".equals(Build.MODEL) && "STK-AL00".equals(Build.BOARD)) {
            f1336c = true;
        }
    }

    public static boolean a() throws ClassNotFoundException {
        try {
            Class.forName(AppEnv.MTOP_CLASS_NAME);
            return true;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
