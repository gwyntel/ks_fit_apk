package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes2.dex */
public class AnnotationSerializer implements ObjectSerializer {
    public static AnnotationSerializer instance = new AnnotationSerializer();
    private static volatile Class sun_AnnotationType = null;
    private static volatile boolean sun_AnnotationType_error = false;
    private static volatile Method sun_AnnotationType_getInstance;
    private static volatile Method sun_AnnotationType_members;

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        JSONException jSONException;
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
            Class<?> cls = interfaces[0];
            if (sun_AnnotationType == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType = Class.forName("sun.reflect.annotation.AnnotationType");
                } finally {
                }
            }
            if (sun_AnnotationType == null) {
                throw new JSONException("not support Type Annotation.");
            }
            if (sun_AnnotationType_getInstance == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_getInstance = sun_AnnotationType.getMethod("getInstance", Class.class);
                } finally {
                }
            }
            if (sun_AnnotationType_members == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_members = sun_AnnotationType.getMethod("members", null);
                } finally {
                }
            }
            if (sun_AnnotationType_getInstance == null || sun_AnnotationType_error) {
                throw new JSONException("not support Type Annotation.");
            }
            try {
                try {
                    Map map = (Map) sun_AnnotationType_members.invoke(sun_AnnotationType_getInstance.invoke(null, cls), null);
                    JSONObject jSONObject = new JSONObject(map.size());
                    Object objInvoke = null;
                    for (Map.Entry entry : map.entrySet()) {
                        try {
                            objInvoke = ((Method) entry.getValue()).invoke(obj, null);
                        } catch (IllegalAccessException | InvocationTargetException unused) {
                        }
                        jSONObject.put((String) entry.getKey(), JSON.toJSON(objInvoke));
                    }
                    jSONSerializer.write(jSONObject);
                } finally {
                }
            } finally {
            }
        }
    }
}
