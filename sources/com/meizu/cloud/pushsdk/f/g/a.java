package com.meizu.cloud.pushsdk.f.g;

import android.content.Context;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19672a = "a";

    public static Map a(String str, Context context) throws IOException {
        try {
            String str2 = f19672a;
            c.a(str2, "Attempting to retrieve map from: %s", str);
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(str));
            HashMap map = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            c.a(str2, " + Retrieved map from file: %s", map);
            return map;
        } catch (IOException | ClassNotFoundException e2) {
            c.b(f19672a, " + Exception getting vars map: %s", e2.getMessage());
            return null;
        }
    }

    public static boolean a(String str, Map map, Context context) throws IOException {
        try {
            String str2 = f19672a;
            c.a(str2, "Attempting to save: %s", map);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(str, 0));
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
            c.a(str2, " + Successfully saved KV Pairs to: %s", str);
            return true;
        } catch (IOException e2) {
            c.b(f19672a, " + Exception saving vars map: %s", e2.getMessage());
            return false;
        }
    }
}
